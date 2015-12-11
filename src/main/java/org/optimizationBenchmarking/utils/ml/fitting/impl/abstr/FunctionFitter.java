package org.optimizationBenchmarking.utils.ml.fitting.impl.abstr;

import org.optimizationBenchmarking.utils.ml.fitting.spec.IFunctionFitter;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** Create the fitter. */
public abstract class FunctionFitter extends Tool
    implements IFunctionFitter {

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
