package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import java.util.Collection;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * An evaluation setup.
 */
public final class EvaluationModules extends _ConfigEntry {

  /** the modules */
  private final ArrayListView<ModuleEntry> m_modules;

  /**
   * Create the evaluation setup
   *
   * @param config
   *          the configuration
   * @param modules
   *          the modules
   */
  EvaluationModules(final Configuration config,
      final ArrayListView<ModuleEntry> modules) {
    super(config);
    EvaluationModules._validateModules(modules);

    this.m_modules = modules;
  }

  /**
   * Verify whether the module list is OK
   *
   * @param modules
   *          the module list
   */
  static final void _validateModules(final Collection<ModuleEntry> modules) {
    if (modules == null) {
      throw new IllegalArgumentException("Module list cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * Get the module entries.
   *
   * @return the module entries.
   */
  public final ArrayListView<ModuleEntry> getEntries() {
    return this.m_modules;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_modules),
        HashUtils.hashCode(this.m_config));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final EvaluationModules me;
    if (o == this) {
      return true;
    }
    if (o instanceof EvaluationModules) {
      me = ((EvaluationModules) o);
      return (EComparison.equals(me.m_config, this.m_modules) && //
      EComparison.equals(me.m_config, this.m_config));
    }

    return false;
  }
}
