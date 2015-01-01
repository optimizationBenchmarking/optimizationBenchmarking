package org.optimizationBenchmarking.utils.io.structured.spec;

import org.optimizationBenchmarking.utils.tools.spec.IConfigurableJobTool;

/**
 * A tool for performing I/O
 */
public interface IIOTool extends IConfigurableJobTool {

  /** {@inheritDoc} */
  @Override
  public abstract IIOJobBuilder use();
}
