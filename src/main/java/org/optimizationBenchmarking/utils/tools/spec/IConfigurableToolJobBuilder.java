package org.optimizationBenchmarking.utils.tools.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.IConfigurable;

/**
 * A tool job builder whose parameters can be configured.
 */
public interface IConfigurableToolJobBuilder extends IToolJobBuilder,
    IConfigurable {

  /** {@inheritDoc} */
  @Override
  public abstract IConfigurableToolJobBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract void configure(final Configuration config);
}
