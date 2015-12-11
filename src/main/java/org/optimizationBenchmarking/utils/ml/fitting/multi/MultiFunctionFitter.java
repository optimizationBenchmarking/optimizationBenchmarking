package org.optimizationBenchmarking.utils.ml.fitting.multi;

import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * A function fitter which can apply multiple techniques and try to fit
 * multiple models at once.
 */
public final class MultiFunctionFitter extends Tool {

  /** create */
  MultiFunctionFitter() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final MultiFittingJobBuilder use() {
    return new MultiFittingJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Multi-Function Fitter"; //$NON-NLS-1$
  }

  /**
   * create the job
   *
   * @param builder
   *          the fitting job builder
   * @return the fitting job
   */
  final MultiFittingJob _create(final MultiFittingJobBuilder builder) {
    return new MultiFittingJob(builder);
  }

  /**
   * Get the globally shared instance of the multi-function fitter
   *
   * @return the globally shared instance of the multi-function fitter
   */
  public static final MultiFunctionFitter getInstance() {
    return __Holder.INSTANCE;
  }

  /** the instance holder */
  private static final class __Holder {

    /** the globally shared instance of the multi-fitter */
    static final MultiFunctionFitter INSTANCE = new MultiFunctionFitter();
  }
}
