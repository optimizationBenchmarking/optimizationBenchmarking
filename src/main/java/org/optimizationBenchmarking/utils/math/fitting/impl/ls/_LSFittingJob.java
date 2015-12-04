package org.optimizationBenchmarking.utils.math.fitting.impl.ls;

import java.util.Random;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.fitting.leastsquares.GaussNewtonOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.SimpleValueChecker;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer.PopulationSize;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer.Sigma;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.util.Incrementor;
import org.optimizationBenchmarking.utils.math.BasicNumberWrapper;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJob;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StandardDeviationAggregate;

/**
 * A function fitting job which proceeds as follows:
 * <ol>
 * <li>repeat at most {@value #LOOP_OUTER_MAX_ITERATIONS} times
 * <ol>
 * <li>do {@value #LOOP_INNER_MAX_ITERATIONS} times
 * <ol>
 * <li>draw several guesses from the parameter guesser associated with the
 * function, keep the best guess, that is sufficiently different from
 * previous best guesses</li>
 * <li>use this guess as starting point for the Levenberg-Marquardt
 * algorithm (or Gauss-Newton if that fails)</li>
 * <li>use the result of the above as starting point for either BOBYQA or
 * CMA-ES</li>
 * </ol>
 * </li>
 * <li>if at least {@value #LOOP_MAX_IMPROVEMENTS} of the
 * {@value #LOOP_INNER_MAX_ITERATIONS} led to result improvements, continue
 * the outer loop</li>
 * <li>if the standard deviation of the result decision variables or result
 * qualities divided by their means has dropped below
 * {@value #LOOP_STDDEV_FRACTION} multiplied with the loop counter, stop.
 * </li>
 * </ol>
 * </ol>
 * </li>
 * </ol>
 * <p>
 * The goal is to obtain high-quality fittings by first using an ordinary
 * least-squares problem approach (Levenberg-Marquardt or Gauss-Newton
 * algorithm) and then to refine the result using direct, black-box
 * optimizers (BOBYQA, CMA-ES).
 * </p>
 * <p>
 * Besides getting high-quality results, having stable and reproducible
 * results is also very important. This is why we perform the above several
 * times, more often if results seem to be unstable.
 * </p>
 */
final class _LSFittingJob extends FittingJob
    implements MultivariateFunction, LeastSquaresProblem,
    ConvergenceChecker<Evaluation> {

  /** Relative tolerance threshold. */
  private static final double OPTIMIZER_RELATIVE_THRESHOLD = 1e-10d;

  /** the maximum number of iterations */
  private static final int OPTIMIZER_MAX_ITERATIONS = 768;

  /** the maximum number of inner loop iterations */
  private static final int LOOP_INNER_MAX_ITERATIONS = 7;
  /** the maximum number of outer loop iterations */
  private static final int LOOP_OUTER_MAX_ITERATIONS = 12;// 9;
  /**
   * the fraction of the standard deviation/arithmetic mean at which the
   * outer loop must be continued
   */
  private static final double LOOP_STDDEV_FRACTION = 2e-5d;
  /**
   * the number of improvements when the outer loop needs to be continued
   */
  private static final int LOOP_MAX_IMPROVEMENTS = //
  ((_LSFittingJob.LOOP_INNER_MAX_ITERATIONS >>> 1) + 1);

  /** the minimum required weighted distance between starting points */
  private static final double MIN_REQUIRED_DISTANCE = 1e-5d;

  /** the random number generator */
  private Random m_random;

  /** the evaluation counter */
  private Incrementor m_evaluationCounter;
  /** the iteration counter */
  private Incrementor m_iterationCounter;

  /** the start vector */
  private ArrayRealVector m_startVector;

  /** the Gauss-Newton optimizer */
  private GaussNewtonOptimizer m_gaussNewton;

  /** the Levenberg-Marquardt optimizer */
  private LevenbergMarquardtOptimizer m_levenbergMarquardt;

  /** the CMA-ES optimizer */
  private CMAESOptimizer m_cmaes;
  /** the objective function */
  private ObjectiveFunction m_objective;
  /** the bounds */
  private SimpleBounds m_bounds;
  /** the population size */
  private PopulationSize m_populationSize;
  /** the maximum evaluations */
  private MaxEval m_maxEval;

  /** the bobyqa optimizer */
  private BOBYQAOptimizer m_bobyqa;
  /** the maximum iterations */
  private MaxIter m_maxIter;

  /** the simplex optimizer */
  private SimplexOptimizer m_simplex;

  /**
   * create the fitting job
   *
   * @param builder
   *          the builder
   */
  protected _LSFittingJob(final FittingJobBuilder builder) {
    super(builder);
  }

  /** {@inheritDoc} */
  @Override
  public final RealVector getStart() {
    return this.m_startVector;
  }

  /** {@inheritDoc} */
  @Override
  public final int getObservationSize() {
    return this.m_data.m();
  }

  /** {@inheritDoc} */
  @Override
  public final int getParameterSize() {
    return this.m_function.getParameterCount();
  }

  /**
   * get the {@code double[]} associated with a real vector
   *
   * @param vec
   *          the vector
   * @return the {@code double[]}
   */
  private static final double[] __toArray(final RealVector vec) {
    return ((vec instanceof ArrayRealVector)//
        ? ((ArrayRealVector) vec).getDataRef() : vec.toArray());
  }

  /** {@inheritDoc} */
  @Override
  public final Evaluation evaluate(final RealVector point) {
    final double[][] jacobian;
    final double[] residuals;
    final double[] vector;
    final ParametricUnaryFunction func;
    final int numSamples, numParams;
    final IMatrix data;
    final double minInverseWeight;
    final StableSum sum;
    double[] jacobianRow;
    double x, expectedY, inverseWeight, residual, squareErrorSum, rms;
    int i, j;

    vector = _LSFittingJob.__toArray(point);

    func = this.m_function;
    data = this.m_data;
    minInverseWeight = this.m_minInverseWeight;

    numSamples = data.m();
    residuals = new double[numSamples];
    numParams = vector.length;// =func.getParameterCount();
    jacobian = new double[numSamples][numParams];
    sum = this.m_sum;

    sum.reset();
    for (i = numSamples; (--i) >= 0;) {
      x = data.getDouble(i, 0);
      expectedY = data.getDouble(i, 1);

      inverseWeight = Math.abs(expectedY);
      if (inverseWeight < minInverseWeight) {
        inverseWeight = minInverseWeight;
      }

      residuals[i] = residual = ((expectedY - func.value(x, vector))
          / inverseWeight);
      sum.append(residual * residual);

      jacobianRow = jacobian[i];
      func.gradient(x, vector, jacobianRow);
      for (j = numParams; (--j) >= 0;) {
        jacobianRow[j] /= inverseWeight;
      }
    }

    squareErrorSum = sum.doubleValue();
    rms = Math.sqrt(squareErrorSum / numSamples);
    this.register(rms, vector);

    return new _InternalEvaluation(//
        new Array2DRowRealMatrix(jacobian, false), //
        new ArrayRealVector(residuals, false), //
        point, //
        Math.sqrt(squareErrorSum), //
        rms);//
  }

  /**
   * refine the best known solution with the gauss-newton method
   *
   * @return {@code null} on failure, the result vector on success
   */
  private final double[] __refineStartWithGaussNewton() {
    try {
      this.m_iterationCounter = new Incrementor(
          _LSFittingJob.OPTIMIZER_MAX_ITERATIONS);
      this.m_evaluationCounter = new Incrementor(
          _LSFittingJob.OPTIMIZER_MAX_ITERATIONS
              * _LSFittingJob.OPTIMIZER_MAX_ITERATIONS);

      if (this.m_gaussNewton == null) {
        this.m_gaussNewton = new GaussNewtonOptimizer(
            GaussNewtonOptimizer.Decomposition.SVD);
      }

      return _LSFittingJob.__toArray(//
          this.m_gaussNewton.optimize(this).getPoint());
    } catch (@SuppressWarnings("unused") final Throwable error) {
      return null;
    } finally {
      this.m_evaluationCounter = null;
      this.m_iterationCounter = null;
    }
  }

  /**
   * register a solution
   *
   * @param pvp
   *          the solution
   * @param stddevs
   *          the standard deviations
   */
  private static final void __register(final PointValuePair pvp,
      final StandardDeviationAggregate[] stddevs) {
    int index;

    index = (-1);
    for (final double d : pvp.getPoint()) {
      stddevs[++index].append(d);
    }
    stddevs[++index].append(pvp.getValue().doubleValue());
  }

  /**
   * refine the current best solution using BOBYQA
   *
   * @param point
   *          the point to be refined
   * @param stddevs
   *          the standard deviation aggregates
   * @return {@code true} on success, {@code false} on failure
   */
  private final boolean __refineWithBOBYQA(final double[] point,
      final StandardDeviationAggregate[] stddevs) {
    final int dim;

    try {
      dim = point.length;

      if (this.m_bobyqa == null) {
        this.m_bobyqa = new BOBYQAOptimizer(dim + 3);
      }
      if (this.m_objective == null) {
        this.m_objective = new ObjectiveFunction(this);
      }
      if (this.m_bounds == null) {
        this.m_bounds = SimpleBounds.unbounded(dim);
      }

      _LSFittingJob.__register(//
          this.m_bobyqa.optimize(//
              new InitialGuess(point), //
              this.m_objective, //
              this.__getMaxEval(), //
              this.__getMaxIter(), //
              this.m_bounds, //
              GoalType.MINIMIZE),
          stddevs);
    } catch (@SuppressWarnings("unused") final Throwable error) {
      return false;
    }
    return true;
  }

  /**
   * refine the current best solution using Nelder-Mead
   *
   * @param point
   *          the point to be refined
   * @param stddevs
   *          the standard deviation aggregates
   * @return {@code true} on success, {@code false} on failure
   */
  private final boolean __refineWithNelderMead(final double[] point,
      final StandardDeviationAggregate[] stddevs) {
    try {
      if (this.m_simplex == null) {
        this.m_simplex = new SimplexOptimizer(1e-10d,
            Double.NEGATIVE_INFINITY);
      }
      if (this.m_objective == null) {
        this.m_objective = new ObjectiveFunction(this);
      }
      _LSFittingJob.__register(//
          this.m_simplex.optimize(//
              new NelderMeadSimplex(point), //
              new InitialGuess(point), //
              this.m_objective, //
              this.__getMaxEval(), //
              this.__getMaxIter(), //
              GoalType.MINIMIZE),
          stddevs);
    } catch (@SuppressWarnings("unused") final Throwable error) {
      return false;
    }
    return true;
  }

  /**
   * get the maximum evaluations
   *
   * @return the maximum evaluations
   */
  private final MaxEval __getMaxEval() {
    final int dim;
    if (this.m_maxEval == null) {
      dim = this.m_function.getParameterCount();
      this.m_maxEval = new MaxEval(dim * dim * 300);
    }
    return this.m_maxEval;
  }

  /**
   * get the maximum iterations
   *
   * @return the maximum iterations
   */
  private final MaxIter __getMaxIter() {
    if (this.m_maxIter == null) {
      this.m_maxIter = new MaxIter(this.__getMaxEval().getMaxEval());
    }
    return this.m_maxIter;
  }

  /**
   * refine the current best solution using CMAES
   *
   * @param point
   *          the point to be refined
   * @param stddevs
   *          the standard deviation aggregates
   * @return {@code true} on success, {@code false} on failure
   */
  private final boolean __refineWithCMAES(final double[] point,
      final StandardDeviationAggregate[] stddevs) {
    final double[] sigma;
    final int dim, ps, maxEval;
    int index;

    try {
      dim = point.length;

      ps = (dim * dim);
      maxEval = (300 * ps);

      if (this.m_cmaes == null) {
        this.m_cmaes = new CMAESOptimizer(maxEval, 0d, true, 20, 10,
            new JDKRandomGenerator(), false, new SimpleValueChecker(1e-14d,
                Double.NEGATIVE_INFINITY, maxEval));
      }

      if (this.m_objective == null) {
        this.m_objective = new ObjectiveFunction(this);
      }

      if (this.m_bounds == null) {
        this.m_bounds = SimpleBounds.unbounded(dim);
      }

      if (this.m_populationSize == null) {
        this.m_populationSize = new PopulationSize(ps);
      }

      sigma = point.clone();
      for (index = sigma.length; (--index) >= 0;) {
        sigma[index] = Math.max(1d, (0.5d * Math.abs(point[index])));
      }

      _LSFittingJob.__register(//
          this.m_cmaes.optimize(//
              new Sigma(sigma), // r
              new InitialGuess(point), //
              this.m_objective, //
              this.m_bounds, //
              this.m_populationSize, //
              this.__getMaxEval(), //
              GoalType.MINIMIZE),
          stddevs);
    } catch (@SuppressWarnings("unused") final Throwable error) {
      return false;
    }
    return true;
  }

  /**
   * refine the best known solution with the Levenberg-Marquardt method
   *
   * @return {@code null} on failure, the result vector on success
   */
  private final double[] __refineStartWithLevenbergMarquardt() {
    try {
      this.m_iterationCounter = new Incrementor(
          _LSFittingJob.OPTIMIZER_MAX_ITERATIONS);
      this.m_evaluationCounter = new Incrementor(
          _LSFittingJob.OPTIMIZER_MAX_ITERATIONS
              * _LSFittingJob.OPTIMIZER_MAX_ITERATIONS);

      if (this.m_levenbergMarquardt == null) {
        this.m_levenbergMarquardt = new LevenbergMarquardtOptimizer();
      }

      return _LSFittingJob.__toArray(//
          this.m_levenbergMarquardt.optimize(this).getPoint());
    } catch (@SuppressWarnings("unused") final Throwable error) {
      return null;
    } finally {
      this.m_evaluationCounter = null;
      this.m_iterationCounter = null;
    }
  }

  /**
   * compute the weighted distance between two vectors
   *
   * @param a
   *          the first vector
   * @param b
   *          the second vector
   * @return {@code true} if the distance is not large enough,
   *         {@code false} otherwise
   */
  private static final boolean __isNotUnique(final double[] a,
      final double[] b) {
    double dist, bi;
    int index;

    dist = 0d;
    index = (-1);
    for (final double ai : a) {
      bi = b[++index];
      if (ai != bi) {
        bi = ((ai - bi) / Math.max(Double.MIN_NORMAL, //
            Math.max(Math.abs(ai), Math.abs(bi))));
        dist += (bi * bi);
      }
    }

    return (dist <= (index * _LSFittingJob.MIN_REQUIRED_DISTANCE));
  }

  /**
   * check if a vector is sufficiently unique
   *
   * @param matrix
   *          the matrix (also containing the vector)
   * @param element
   *          the vector
   * @return {@code true} if the vector is sufficiently unique,
   *         {@code false} otherwise
   */
  private static final boolean __isUnique(final double[][] matrix,
      final double[] element) {
    for (final double[] vector : matrix) {
      if (vector == element) {
        return true;
      }
      if (_LSFittingJob.__isNotUnique(vector, element)) {
        return false;
      }
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  protected final void fit() {
    final int numParameters, maxStartPointSamples;
    final StandardDeviationAggregate[] stddevs;
    final BasicNumberWrapper[] means;
    final double[][] startPoints;
    final double[] bestStartGuess;
    double[] currentStartGuess, result1;
    IParameterGuesser guesser;
    boolean cmaesFirst;
    double best, current;
    int index, innerLoopIteration, improved, outerLoopIteration,
        totalInnerLoopIterations;

    // initialize and allocate all needed variables

    this.m_random = new Random();
    numParameters = this.m_function.getParameterCount();

    startPoints = new double[(_LSFittingJob.LOOP_OUTER_MAX_ITERATIONS
        * _LSFittingJob.LOOP_INNER_MAX_ITERATIONS) + 1][numParameters];
    bestStartGuess = startPoints[startPoints.length - 1];

    guesser = this.m_function.createParameterGuesser(this.m_data);
    this.m_startVector = new ArrayRealVector(bestStartGuess, false);

    maxStartPointSamples = Math.max(100,
        Math.min(10000, ((int) (Math.round(//
            2d * Math.pow(3d, numParameters))))));

    index = (numParameters + 1);
    stddevs = new StandardDeviationAggregate[index];
    means = new BasicNumberWrapper[index];
    for (; (--index) >= 0;) {
      means[index] = (stddevs[index] = //
      new StandardDeviationAggregate()).getArithmeticMean();
    }

    totalInnerLoopIterations = 0;

    // perform the main loop
    outerLoop: for (outerLoopIteration = 1; outerLoopIteration <= _LSFittingJob.LOOP_OUTER_MAX_ITERATIONS; outerLoopIteration++) {
      improved = 0;

      // inner loop: 1) generate initial guess, 2) use least-squares
      // approach to refine, 3) use sophisticated optimizer to refine
      innerLoop: for (innerLoopIteration = _LSFittingJob.LOOP_INNER_MAX_ITERATIONS; //
      (--innerLoopIteration) >= 0; ++totalInnerLoopIterations) {

        // find initial guess: we use the parameter guesser provided by the
        // model to create a few guesses and keep the best one
        currentStartGuess = startPoints[totalInnerLoopIterations];
        guesser.createRandomGuess(bestStartGuess, this.m_random);
        best = this.evaluate(bestStartGuess);

        for (index = maxStartPointSamples; (--index) >= 0;) {
          guesser.createRandomGuess(currentStartGuess, this.m_random);
          current = this.evaluate(currentStartGuess);
          if (current < best) {
            // try to get starting points which are, sort of, different
            if (_LSFittingJob.__isUnique(startPoints, currentStartGuess)) {
              System.arraycopy(currentStartGuess, 0, bestStartGuess, 0,
                  numParameters);
              best = current;
            }
          }
        }
        System.arraycopy(bestStartGuess, 0, currentStartGuess, 0,
            numParameters);

        best = this.m_result.getQuality();

        // Refine initial guess by using a least-squares solver for
        // traditional function fitting.
        result1 = this.__refineStartWithLevenbergMarquardt();
        if (result1 == null) { // Levenberg-Marquardt failed
          result1 = this.__refineStartWithGaussNewton();
          if (result1 == null) {
            continue innerLoop;
          }
        }

        // The least-squares method may get trapped in a local optimum. We
        // need to refine its results using a strong, numerical,
        // non-linear-capable optimizer. We choose to do this either with
        // BOBYQA or CMA-ES. BOBYQA is faster but CMA-ES more reliable..
        cmaesFirst = ((innerLoopIteration & 1) != 0);

        refineWithNumericalBlackBoxMethod: {
          if (cmaesFirst) {// use CMA-ES!
            if (this.__refineWithCMAES(result1, stddevs)) {
              break refineWithNumericalBlackBoxMethod;
            }
          }
          // either CMA-ES failed or we should use BOBYQA anyway
          if (this.__refineWithBOBYQA(result1, stddevs)) {
            break refineWithNumericalBlackBoxMethod;
          }
          if (!cmaesFirst) {
            // BOBYQA failed, but we did not yet try CMA-ES
            if (this.__refineWithCMAES(result1, stddevs)) {
              break refineWithNumericalBlackBoxMethod;
            }
          }

          // Both CMA-ES and BOBYQA failed, let's try Nelder-Mead
          this.__refineWithNelderMead(result1, stddevs);
        }

        // Finished refining, check
        current = this.m_result.getQuality();
        if (current < best) {
          ++improved;
          best = current;
        }
      }

      // If we improved the solution at least 4 out of 7 times, this may be
      // an indicator for many local optima and we better try again to be
      // sure.
      if (improved >= _LSFittingJob.LOOP_MAX_IMPROVEMENTS) {
        continue outerLoop;
      }

      // If the standard deviation of the discovered results over the seven
      // iterations in the inner loop is big in comparison to the mean,
      // then this, too, is an indicator of many local optima. Then we
      // better do some more runs, just in case. We also divide by the
      // number of main loop iterations to reduce the amount of main loop
      // iterations after each inner loop a bit.
      for (index = stddevs.length; (--index) >= 0;) {
        current = Math.abs(means[index].doubleValue());
        if (current <= Double.MIN_NORMAL) {
          current = Double.MIN_NORMAL;
        }
        current = (stddevs[index].doubleValue()
            / (current * outerLoopIteration));
        if (current >= _LSFittingJob.LOOP_STDDEV_FRACTION) {
          continue outerLoop;
        }
      }

      break outerLoop;
    }

    // Dispose all the variables.
    this.m_cmaes = null;
    this.m_bobyqa = null;
    this.m_gaussNewton = null;
    this.m_levenbergMarquardt = null;
    this.m_simplex = null;
    this.m_startVector = null;
    this.m_bounds = null;
    this.m_objective = null;
    this.m_maxEval = null;
    this.m_maxIter = null;
    this.m_random = null;
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double[] point) {
    return this.evaluate(point);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean converged(final int iteration,
      final Evaluation previous, final Evaluation current) {
    final RealVector pv, cv;
    final double[] p, c;
    double ci;
    int i;

    if (iteration >= _LSFittingJob.OPTIMIZER_MAX_ITERATIONS) {
      return true;
    }

    pv = previous.getPoint();
    cv = current.getPoint();

    p = _LSFittingJob.__toArray(pv);
    c = _LSFittingJob.__toArray(cv);

    i = (-1);
    for (final double ppi : p) {
      ci = c[++i];
      if (Math.abs(ppi - ci) > (_LSFittingJob.OPTIMIZER_RELATIVE_THRESHOLD
          * Math.max(Math.abs(ppi), Math.abs(ci)))) {
        return false;
      }
    }

    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final Incrementor getEvaluationCounter() {
    return this.m_evaluationCounter;
  }

  /** {@inheritDoc} */
  @Override
  public final Incrementor getIterationCounter() {
    return this.m_iterationCounter;
  }

  /** {@inheritDoc} */
  @Override
  public final ConvergenceChecker<Evaluation> getConvergenceChecker() {
    return this;
  }
}
