package org.optimizationBenchmarking.utils.math.mathEngine.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/** A tool providing access to a Math engine */
public interface IMathEngineTool extends ITool {

  /** {@inheritDoc} */
  @Override
  public abstract IMathEngineBuilder use();
}
