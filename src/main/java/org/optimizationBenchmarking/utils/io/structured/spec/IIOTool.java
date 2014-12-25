package org.optimizationBenchmarking.utils.io.structured.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/**
 * A tool for performing I/O
 */
public interface IIOTool extends ITool {

  /** {@inheritDoc} */
  @Override
  public abstract IIOJobBuilder use();
}
