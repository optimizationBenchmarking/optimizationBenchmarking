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
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJob;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;

/** A function fitting job based on differential evolution */
final class _LSFittingJob extends FittingJob
    implements MultivariateFunction, LeastSquaresProblem,
    ConvergenceChecker<Evaluation> {

  /** Relative tolerance threshold. */
  private static final double RELATIVE_THRESHOLD = 1e-10d;

  /** the maximum number of iterations */
  private static final int MAX_ITERATIONS = 768;

  /** the random number generator */
  private final Random m_random;

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

    this.m_random = new Random();
  }

  /** {@inheritDoc} */
  @Override
  public final RealVector getStart() {
    return this.m_startVector;
  }

  // /** create the initial guess */
  // private final void __createInitialGuess() {
  // final int dim;
  // final double[] temp,start;
  // IParameterGuesser guesser;
  // int index;
  //
  // dim = this.m_function.getParameterCount();
  // temp =this.m_temp;
  // start=this.m_start;
  //
  // guesser = this.m_function.createParameterGuesser(this.m_data);
  // for (index = Math.max(100, Math.min(1000000, ((int) (Math.round(//
  // 30d * Math.pow(3d, dim)))))); (--index) >= 0;) {
  // guesser.createRandomGuess(dest, this.m_random);
  // this.evaluate(dest);
  // }
  // }

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
          _LSFittingJob.MAX_ITERATIONS);
      this.m_evaluationCounter = new Incrementor(
          _LSFittingJob.MAX_ITERATIONS * _LSFittingJob.MAX_ITERATIONS);

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
   * refine the current best solution using BOBYQA
   *
   * @param point
   *          the point to be refined
   * @return {@code true} on success, {@code false} on failure
   */
  private final boolean __refineWithBOBYQA(final double[] point) {
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

      this.m_bobyqa.optimize(//
          new InitialGuess(point), //
          this.m_objective, //
          this.__getMaxEval(), //
          this.__getMaxIter(), //
          this.m_bounds, //
          GoalType.MINIMIZE).getPoint();
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
   * @return {@code true} on success, {@code false} on failure
   */
  private final boolean __refineWithNelderMead(final double[] point) {
    try {
      if (this.m_simplex == null) {
        this.m_simplex = new SimplexOptimizer(1e-10d,
            Double.NEGATIVE_INFINITY);
      }
      if (this.m_objective == null) {
        this.m_objective = new ObjectiveFunction(this);
      }
      this.m_simplex.optimize(//
          new NelderMeadSimplex(point), //
          new InitialGuess(point), //
          this.m_objective, //
          this.__getMaxEval(), //
          this.__getMaxIter(), //
          GoalType.MINIMIZE).getPoint();
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
   * @return {@code true} on success, {@code false} on failure
   */
  private final boolean __refineWithCMAES(final double[] point) {
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

      this.m_cmaes.optimize(//
          new Sigma(sigma), // r
          new InitialGuess(point), //
          this.m_objective, //
          this.m_bounds, //
          this.m_populationSize, //
          this.__getMaxEval(), //
          GoalType.MINIMIZE).getPoint();
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
          _LSFittingJob.MAX_ITERATIONS);
      this.m_evaluationCounter = new Incrementor(
          _LSFittingJob.MAX_ITERATIONS * _LSFittingJob.MAX_ITERATIONS);

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

  /** {@inheritDoc} */
  @Override
  protected final void fit() {
    final int dim, maxPoints;
    double[] start, temp, result1;// , result2;
    IParameterGuesser guesser;
    boolean cmaesFirst;
    double best, current;
    int index, looper;

    dim = this.m_function.getParameterCount();
    start = new double[dim];
    temp = new double[dim];
    guesser = this.m_function.createParameterGuesser(this.m_data);
    this.m_startVector = new ArrayRealVector(start, false);

    maxPoints = Math.max(100, Math.min(10000, ((int) (Math.round(//
        2d * Math.pow(3d, dim))))));

    mainLoop: for (looper = 7; (--looper) >= 0;) {

      best = Double.POSITIVE_INFINITY;
      for (index = maxPoints; (--index) >= 0;) {
        guesser.createRandomGuess(temp, this.m_random);
        current = this.evaluate(temp);
        if (current < best) {
          System.arraycopy(temp, 0, start, 0, dim);
          best = current;
        }
      }

      result1 = this.__refineStartWithLevenbergMarquardt();
      if (result1 == null) {
        result1 = this.__refineStartWithGaussNewton();
        if (result1 == null) {
          continue mainLoop;
        }
      }

      cmaesFirst = ((looper & 1) != 0);

      if (cmaesFirst) {
        if (this.__refineWithCMAES(result1)) {
          continue;
        }
      }
      if (this.__refineWithBOBYQA(result1)) {
        continue;
      }
      if (!cmaesFirst) {
        if (this.__refineWithCMAES(result1)) {
          continue;
        }
      }
      this.__refineWithNelderMead(result1);
    }

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

    if (iteration >= _LSFittingJob.MAX_ITERATIONS) {
      return true;
    }

    pv = previous.getPoint();
    cv = current.getPoint();

    p = _LSFittingJob.__toArray(pv);
    c = _LSFittingJob.__toArray(cv);

    i = (-1);
    for (final double ppi : p) {
      ci = c[++i];
      if (Math.abs(ppi - ci) > (_LSFittingJob.RELATIVE_THRESHOLD
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
