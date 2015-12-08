package org.optimizationBenchmarking.utils.ml.fitting.impl.lssimplex;

import org.optimizationBenchmarking.utils.ml.fitting.impl.ref.FittingJob;
import org.optimizationBenchmarking.utils.ml.fitting.impl.ref.FittingJobBuilder;
import org.optimizationBenchmarking.utils.ml.fitting.impl.ref.FunctionFitter;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/**
 * This curve fitter uses a combination of least-squares solvers and
 * several local search methods to fit a function.
 */
public final class LSSimplexFitter extends FunctionFitter {

  /** the error */
  private final Throwable m_error;

  /** create */
  LSSimplexFitter() {
    super();

    Throwable cannot;

    cannot = null;
    try {

      ReflectionUtils.ensureClassesAreLoaded(
          "org.apache.commons.math3.analysis.MultivariateFunction", //$NON-NLS-1$
          // "org.apache.commons.math3.fitting.leastsquares.GaussNewtonOptimizer",
          // //$NON-NLS-1$
          "org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder", //$NON-NLS-1$
          "org.apache.commons.math3.fitting.leastsquares.LeastSquaresFactory", //$NON-NLS-1$
          "org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer", //$NON-NLS-1$
          "org.apache.commons.math3.linear.ArrayRealVector", //$NON-NLS-1$
          "org.apache.commons.math3.linear.DiagonalMatrix", //$NON-NLS-1$
          "org.apache.commons.math3.optim.InitialGuess", //$NON-NLS-1$
          "org.apache.commons.math3.optim.MaxEval", //$NON-NLS-1$
          "org.apache.commons.math3.optim.MaxIter", //$NON-NLS-1$
          "org.apache.commons.math3.optim.SimpleBounds", //$NON-NLS-1$
          "org.apache.commons.math3.optim.SimpleValueChecker", //$NON-NLS-1$
          "org.apache.commons.math3.optim.SimpleVectorValueChecker", //$NON-NLS-1$
          "org.apache.commons.math3.optim.nonlinear.scalar.GoalType", //$NON-NLS-1$
          "org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction", //$NON-NLS-1$
          // "org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer",
          // //$NON-NLS-1$
          "org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer", //$NON-NLS-1$
          "org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex", //$NON-NLS-1$
          "org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer", //$NON-NLS-1$
          "org.apache.commons.math3.random.JDKRandomGenerator" //$NON-NLS-1$
      );

    } catch (final Throwable error) {
      cannot = error;
    }

    this.m_error = cannot;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (this.m_error == null);
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    if (this.m_error != null) {
      throw new UnsupportedOperationException(//
          "LSSimplexFitter driver cannot be used.", //$NON-NLS-1$
          this.m_error);
    }
    super.checkCanUse();
  }

  /** {@inheritDoc} */
  @Override
  protected final FittingJob create(final FittingJobBuilder builder) {
    return new LSSimplexFittingJob(builder);
  }

  /**
   * Get the globally shared instance of the Opti-based curve fitter
   *
   * @return the instance of the Opti-based curve fitter
   */
  public static final LSSimplexFitter getInstance() {
    return _DECurveFitterHolder.INSTANCE;
  }

  /** the instance holder */
  private static final class _DECurveFitterHolder {
    /** the shared instance */
    static final LSSimplexFitter INSTANCE = new LSSimplexFitter();
  }
}
