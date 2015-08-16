package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * An entry for an evaluation module.
 */
public final class ModuleEntry extends _ConfigEntry {

  /** The module */
  private final IEvaluationModule m_module;

  /**
   * Create the module entry
   *
   * @param config
   *          the configuration
   * @param module
   *          the module
   */
  ModuleEntry(final Configuration config, final IEvaluationModule module) {
    super(config);
    ModuleEntry._validateModule(module);
    this.m_module = module;
  }

  /**
   * Validate the module .
   *
   * @param module
   *          the entry class
   */
  static final void _validateModule(final IEvaluationModule module) {
    if (module == null) {
      throw new IllegalArgumentException("Module cannot be null."); //$NON-NLS-1$
    }
    module.checkCanUse();
  }

  /**
   * Get the module.
   *
   * @return the module.
   */
  public final IEvaluationModule getModule() {
    return this.m_module;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_module),
        HashUtils.hashCode(this.m_config));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final ModuleEntry me;
    if (o == this) {
      return true;
    }
    if (o instanceof ModuleEntry) {
      me = ((ModuleEntry) o);
      return (EComparison.equals(me.m_module, this.m_module) && //
      EComparison.equals(me.m_config, this.m_config));
    }

    return false;
  }
}
