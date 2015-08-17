package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The builder for entries
 *
 * @param <T>
 *          the result type
 */
abstract class _ConfigEntryBuilder<T> extends BuilderFSM<T> {

  /** the flag for the configuration */
  static final int FLAG_CONFIG_SET = 1;
  /** the flag for the configuration builder */
  static final int FLAG_CONFIG_BUILDER_CREATED = (_ConfigEntryBuilder.FLAG_CONFIG_SET << 1);

  /** the base configuration */
  Configuration m_config;

  /**
   * create the entry builder
   *
   * @param owner
   *          the owning FSM, or {@code null} if none was specified
   */
  public _ConfigEntryBuilder(final HierarchicalFSM owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_CONFIG_SET: {
        append.append("configSet");//$NON-NLS-1$
        return;
      }
      case FLAG_CONFIG_BUILDER_CREATED: {
        append.append("configBuilderSet");//$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
        return;
      }
    }
  }

  /**
   * the forbidden flags when creating a configuration
   *
   * @return the flags
   */
  int _forbiddenFlagsAtConfig() {
    return _ConfigEntryBuilder.FLAG_CONFIG_SET;
  }

  /**
   * Set the configuration
   *
   * @param config
   *          the configuration
   */
  public synchronized final void setConfiguration(
      final Configuration config) {
    _ConfigEntry._validateConfig(config);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (this._forbiddenFlagsAtConfig() | _ConfigEntryBuilder.FLAG_CONFIG_BUILDER_CREATED),
        _ConfigEntryBuilder.FLAG_CONFIG_SET, FSM.FLAG_NOTHING);
    this.m_config = this.normalize(config);
  }

  /**
   * Set the configuration
   *
   * @param config
   *          the configuration
   */
  synchronized final void _setConfigurationFromBuilder(
      final Configuration config) {
    _ConfigEntry._validateConfig(config);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertAndUpdate(
        _ConfigEntryBuilder.FLAG_CONFIG_BUILDER_CREATED,
        this._forbiddenFlagsAtConfig(),
        _ConfigEntryBuilder.FLAG_CONFIG_SET, FSM.FLAG_NOTHING);
    this.m_config = this.normalize(config);
  }

  /**
   * Create the configuration builder
   *
   * @return the configuration builder
   */
  ConfigurationBuilder _createConfigurationBuilder() {
    return new ConfigurationBuilder(this);
  }

  /**
   * Set the configuration by building it
   *
   * @return the configuration builder
   */
  public synchronized final ConfigurationBuilder setConfiguration() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (this._forbiddenFlagsAtConfig() | _ConfigEntryBuilder.FLAG_CONFIG_BUILDER_CREATED),
        _ConfigEntryBuilder.FLAG_CONFIG_BUILDER_CREATED, FSM.FLAG_NOTHING);
    return this._createConfigurationBuilder();
  }
}
