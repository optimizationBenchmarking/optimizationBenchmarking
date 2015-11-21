package org.optimizationBenchmarking.utils.math.fitting.impl.ls;

import java.util.Random;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.fitting.leastsquares.GaussNewtonOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresFactory;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.SimpleValueChecker;
import org.apache.commons.math3.optim.SimpleVectorValueChecker;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer.PopulationSize;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer.Sigma;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.impl.opti.DataPoint;
import org.optimizationBenchmarking.utils.math.fitting.impl.opti.OptiFittingJob;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJobBuilder;

/** A function fitting job based on differential evolution */
public class LSFittingJob extends OptiFittingJob
    implements MultivariateFunction {

  /** the random number generator */
  private final Random m_random;

  /**
   * create the fitting job
   *
   * @param builder
   *          the builder
   */
  protected LSFittingJob(final FittingJobBuilder builder) {
    super(builder);
    this.m_random = new Random();
  }

  /**
   * crease a set of random solutions, uniformly distributed in the search
   * space
   *
   * @return the best generated solution
   */
  private final double[] __createRandomSolutions() {
    final double[] dest;
    final int dim;
    int index;

    dim = this.m_function.getParameterCount();
    dest = new double[dim];
    for (index = Math.max(100, Math.min(10000, ((int) (Math.round(//
        100d * Math.pow(1.75d, dim)))))); (--index) >= 0;) {
      this.m_guesser.createRandomGuess(dest, this.m_random);
      this.evaluate(dest);
    }

    System.arraycopy(this.m_result.getFittedParameters(), 0, dest, 0,
        dest.length);
    this.m_guesser = null;
    return dest;
  }

  /**
   * refine the best known solution with the gauss-newton method
   *
   * @return {@code true} if the minimization process was successful,
   *         {@code false} otherwise
   */
  @SuppressWarnings("unused")
  private final boolean __refineBestWithGaussNewton() {
    final LeastSquaresBuilder builder;
    final DataPoint[] dpoints;
    final double[] x, y, weights, res;
    final double oldBest, newResult;
    int index;

    oldBest = this.m_result.getQuality();
    try {
      builder = new LeastSquaresBuilder();

      dpoints = this.m_points;
      index = dpoints.length;
      x = new double[index];
      y = new double[index];
      weights = new double[index];
      for (; (--index) >= 0;) {
        x[index] = dpoints[index].input;
        y[index] = dpoints[index].output;
        weights[index] = (1d / dpoints[index].inverseWeight);
      }

      builder.checker(//
          LeastSquaresFactory.evaluationChecker(//
              new SimpleVectorValueChecker(1e-12, Double.NEGATIVE_INFINITY,
                  768)));

      builder.maxEvaluations(20000);
      builder.maxIterations(20000);

      builder.weight(new DiagonalMatrix(weights, false));
      builder.model(new _MultivariateFunction(x, this.m_function));
      builder.target(new ArrayRealVector(y, false));
      builder.start(this.m_result.getFittedParameters());

      res = new GaussNewtonOptimizer(
          GaussNewtonOptimizer.Decomposition.SVD)
              .optimize(//
                  builder.build())
              .getPoint().toArray();
    } catch (final Throwable error) {
      return false;
    }

    newResult = this.evaluate(res);
    return (MathUtils.isFinite(newResult) && (newResult < oldBest));
  }

  /**
   * refine the current best solution using BOBYQA
   *
   * @return {@code true} if the minimization process was successful,
   *         {@code false} otherwise
   */
  @SuppressWarnings("unused")
  private final boolean __refineBestWithBOBYQA() {
    final double[] best, res;
    final int dim;
    final double oldBest, newResult;

    oldBest = this.m_result.getQuality();

    try {
      best = this.m_result.getFittedParameters();
      dim = best.length;

      res = new BOBYQAOptimizer(dim + 3).optimize(//
          new InitialGuess(best), //
          new ObjectiveFunction(this), //
          new MaxEval(5000 * dim * dim), //
          new MaxIter(5000 * dim * dim), //
          SimpleBounds.unbounded(best.length), //
          GoalType.MINIMIZE).getKey();
    } catch (final Throwable error) {
      return false;
    }

    newResult = this.evaluate(res);
    return (MathUtils.isFinite(newResult) && (newResult < oldBest));
  }

  /**
   * refine the current best solution using Nelder-Mead
   *
   * @return {@code true} if the minimization process was successful,
   *         {@code false} otherwise
   */
  @SuppressWarnings("unused")
  private final boolean __refineBestWithNelderMead() {
    final double[] best, result;
    final int dim;
    final double oldBest, newResult;

    oldBest = this.m_result.getQuality();

    try {
      best = this.m_result.getFittedParameters();
      dim = best.length;

      result = new SimplexOptimizer(1e-10d, Double.NEGATIVE_INFINITY)
          .optimize(//
              new NelderMeadSimplex(dim), //
              new InitialGuess(best), //
              new ObjectiveFunction(this), //
              new MaxEval(5000 * dim * dim), //
              new MaxIter(5000 * dim * dim), //
              GoalType.MINIMIZE)
          .getKey();
    } catch (final Throwable error) {
      return false;
    }

    newResult = this.evaluate(result);
    return (MathUtils.isFinite(newResult) && (newResult < oldBest));
  }

  /**
   * refine the current best solution using CMAES
   *
   * @param initial
   *          the initial solution
   * @return {@code true} if the minimization process was successful,
   *         {@code false} otherwise
   */
  @SuppressWarnings("unused")
  private final boolean __refineBestWithCMAES(final double[] initial) {
    final double[] best, sigma, res;
    final int ps, maxEval;
    final double oldBest, newResult;
    int index;

    oldBest = this.m_result.getQuality();

    try {
      best = this.m_result.getFittedParameters();

      ps = (best.length * best.length);
      maxEval = (300 * ps);

      sigma = best.clone();
      for (index = sigma.length; (--index) >= 0;) {
        sigma[index] = Math.max(1e-10,
            Math.max(//
                (Math.abs(initial[index] - best[index])), //
                (0.1d * Math.abs(best[index]))));
      }

      res = new CMAESOptimizer(maxEval, 0d, true, 20, 10,
          new JDKRandomGenerator(), false,
          new SimpleValueChecker(1e-14d, Double.NEGATIVE_INFINITY,
              maxEval))
                  .optimize(//
                      new Sigma(sigma), // r
                      new InitialGuess(best), //
                      new ObjectiveFunction(this), //
                      SimpleBounds.unbounded(best.length), //
                      new PopulationSize(ps), //
                      new MaxEval(maxEval), //
                      GoalType.MINIMIZE)
                  .getKey();
    } catch (final Throwable error) {
      return false;
    }

    newResult = this.evaluate(res);
    return (MathUtils.isFinite(newResult) && (newResult < oldBest));
  }

  /**
   * refine the best known solution with the Levenberg-Marquardt method
   *
   * @return {@code true} if the minimization process was successful,
   *         {@code false} otherwise
   */
  @SuppressWarnings("unused")
  private final boolean __refineBestWithLevenbergMarquardt() {
    final LeastSquaresBuilder builder;
    final DataPoint[] dpoints;
    final double[] x, y, weights, res;
    final double oldBest, newResult;
    int index;

    oldBest = this.m_result.getQuality();

    try {
      builder = new LeastSquaresBuilder();

      dpoints = this.m_points;
      index = dpoints.length;
      x = new double[index];
      y = new double[index];
      weights = new double[index];
      for (; (--index) >= 0;) {
        x[index] = dpoints[index].input;
        y[index] = dpoints[index].output;
        weights[index] = (1d / dpoints[index].inverseWeight);
      }

      builder.checker(//
          LeastSquaresFactory.evaluationChecker(//
              new SimpleVectorValueChecker(1e-12, Double.NEGATIVE_INFINITY,
                  768)));

      builder.maxEvaluations(20000);
      builder.maxIterations(20000);

      builder.weight(new DiagonalMatrix(weights, false));
      builder.model(new _MultivariateFunction(x, this.m_function));
      builder.target(new ArrayRealVector(y, false));
      builder.start(this.m_result.getFittedParameters());
      res = new LevenbergMarquardtOptimizer().optimize(builder.build())
          .getPoint().toArray();
    } catch (final Throwable error) {
      return false;
    }
    newResult = this.evaluate(res);
    return (MathUtils.isFinite(newResult) && (newResult < oldBest));
  }

  /** {@inheritDoc} */
  @Override
  protected final void fit() {
    final boolean notYetBOBYQA;
    double[] initial;

    initial = this.__createRandomSolutions();

    if (!(this.__refineBestWithLevenbergMarquardt())) {
      this.__refineBestWithGaussNewton();
    }
    if (!(this.__refineBestWithNelderMead())) {
      this.__refineBestWithBOBYQA();
      notYetBOBYQA = false;
    } else {
      notYetBOBYQA = true;
    }
    if (!(this.__refineBestWithCMAES(initial))) {
      if (notYetBOBYQA) {
        this.__refineBestWithBOBYQA();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double[] point) {
    return this.evaluate(point);
  }
}
