package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.tools.spec.IConfigurableToolJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * The base class for tool job builders
 *
 * @param <J>
 *          the job type
 * @param <R>
 *          the return type of the setter methods
 */
public abstract class ConfigurableToolJobBuilder<J extends IToolJob, R extends ConfigurableToolJobBuilder<J, R>>
    extends ToolJobBuilder<J, R> implements IConfigurableToolJobBuilder {

  /** create the tool job builder */
  protected ConfigurableToolJobBuilder() {
    super();
  }

  /**
   * Get a parameter prefix
   *
   * @return the parameter prefix, or {@code null} if none is needed
   */
  protected String getParameterPrefix() {
    return null;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public R configure(final Configuration config) {
    final String prefix;
    Logger defau;

    prefix = this.getParameterPrefix();
    if (prefix == null) {
      this.setLogger(config.getLogger(Configuration.PARAM_LOGGER,
          this.m_logger));
    } else {
      defau = this.m_logger;
      if (defau == null) {
        defau = config.getLogger(Configuration.PARAM_LOGGER, null);
      }
      this.setLogger(config.getLogger(
          (prefix + Configuration.PARAM_LOGGER), defau));
    }

    return ((R) this);
  }
}
