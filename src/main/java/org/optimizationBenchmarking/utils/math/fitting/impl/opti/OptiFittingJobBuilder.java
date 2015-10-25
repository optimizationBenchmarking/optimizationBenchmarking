package org.optimizationBenchmarking.utils.math.fitting.impl.opti;

import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The optimization-based fitting job builder */
public final class OptiFittingJobBuilder extends FittingJobBuilder {

  /** the minimum number of critical points */
  private int m_minCriticalPoints;

  /**
   * create
   *
   * @param owner
   *          the owning tool
   */
  protected OptiFittingJobBuilder(final OptiFunctionFitter owner) {
    super(owner);
    this.m_minCriticalPoints = 1;
  }

  /**
   * Validate the minimum number of critical points
   *
   * @param min
   *          the minimum number of critical points
   */
  static final void _validateMinCriticalPoints(final int min) {
    if (min < 1) {
      throw new IllegalArgumentException(//
          "The minimum number of critical points cannot be less than 1, but was set to " //$NON-NLS-1$
              + min);
    }
  }

  /**
   * Set the minimum number of critical points. If the data array is
   * composed of {@code n} independent runs/experiments/sources, we may
   * want to have more than {@code 2n} such points, to accommodate outliers
   * in the start and end of the series.
   *
   * @param min
   *          the minimum number of critical points
   * @return this builder
   */
  public OptiFittingJobBuilder setMinCriticalPoints(final int min) {
    OptiFittingJobBuilder._validateMinCriticalPoints(min);
    this.m_minCriticalPoints = min;
    return this;
  }

  /**
   * Get the minimum number of critical points
   *
   * @return the minimum number of critical points
   */
  public final int getMinCriticalPoints() {
    return this.m_minCriticalPoints;
  }

  /** {@inheritDoc} */
  @Override
  public OptiFittingJobBuilder setPoints(final IMatrix points) {
    super.setPoints(points);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public OptiFittingJobBuilder setFunctionToFit(
      final ParametricUnaryFunction func) {
    super.setFunctionToFit(func);
    return this;
  }
}
