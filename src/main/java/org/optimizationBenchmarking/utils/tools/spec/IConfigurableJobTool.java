package org.optimizationBenchmarking.utils.tools.spec;

/**
 * A tool whose job builders can be configured.
 */
public interface IConfigurableJobTool extends ITool {

  /** {@inheritDoc} */
  @Override
  public abstract IConfigurableToolJobBuilder use();

}
