package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/** an entry for modules */
final class _ModuleEntry {

  /** the module */
  final IEvaluationModule m_module;

  /** the configuration */
  final Configuration m_config;

  /**
   * create the module entry
   * 
   * @param module
   *          the module
   * @param config
   *          the configuration
   */
  _ModuleEntry(final IEvaluationModule module, final Configuration config) {
    super();

    _EvaluationBuilder._checkModule(module);
    _EvaluationBuilder._checkConfig(config);
    this.m_module = module;
    this.m_config = config;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return (this.m_module.toString() + ':' + this.m_config.toString());
  }
}
