package org.optimizationBenchmarking.utils.math.fitting.spec;

import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** Create the fitter. */
public abstract class FunctionFitter extends Tool {

  /** create */
  protected FunctionFitter() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public FittingJobBuilder use() {
    this.checkCanUse();
    return new FittingJobBuilder(this);
  }

  /**
   * create the job
   *
   * @param builder
   *          the fitting job builder
   * @return the fitting job
   */
  protected abstract FittingJob create(final FittingJobBuilder builder);
}
