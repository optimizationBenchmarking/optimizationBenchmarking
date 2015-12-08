package org.optimizationBenchmarking.utils.ml.fitting.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/**
 * The basic interface for function fitters, i.e., software components
 * which can fit a model to a given data set.
 */
public interface IFunctionFitter extends ITool {

  /** {@inheritDoc} */
  @Override
  public abstract IFittingJobBuilder use();
}
