package org.optimizationBenchmarking.utils.math.fitting.impl.opti;

import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJob;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.FunctionFitter;

/** Create the optimization-based fitter. */
public abstract class OptiFunctionFitter extends FunctionFitter {

  /** create */
  protected OptiFunctionFitter() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public OptiFittingJobBuilder use() {
    this.checkCanUse();
    return new OptiFittingJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  protected final FittingJob create(final FittingJobBuilder builder) {
    return this.create((OptiFittingJobBuilder) builder);
  }

  /**
   * create the job
   *
   * @param builder
   *          the fitting job builder
   * @return the fitting job
   */
  protected abstract FittingJob create(final OptiFittingJobBuilder builder);
}
