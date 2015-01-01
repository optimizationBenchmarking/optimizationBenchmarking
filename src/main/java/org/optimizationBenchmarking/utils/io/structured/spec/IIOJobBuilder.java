package org.optimizationBenchmarking.utils.io.structured.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IConfigurableToolJobBuilder;

/**
 * A basic I/O job builder.
 */
public interface IIOJobBuilder extends IConfigurableToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IIOJobBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IIOJob create();
}
