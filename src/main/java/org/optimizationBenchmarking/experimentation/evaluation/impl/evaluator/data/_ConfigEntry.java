package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.hash.HashObject;

/**
 * An entry with a configuration attached
 */
class _ConfigEntry extends HashObject {

  /** the configuration. */
  final Configuration m_config;

  /**
   * Create the module entry
   *
   * @param config
   *          the configuration
   */
  _ConfigEntry(final Configuration config) {
    super();
    _ConfigEntry._validateConfig(config);
    this.m_config = config;
  }

  /**
   * Validate the configuration.
   *
   * @param config
   *          the configuration
   */
  static final void _validateConfig(final Configuration config) {
    if (config == null) {
      throw new IllegalArgumentException("Configuration cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * Get the configuration.
   *
   * @return the configuration.
   */
  public final Configuration getConfiguration() {
    return this.m_config;
  }
}
