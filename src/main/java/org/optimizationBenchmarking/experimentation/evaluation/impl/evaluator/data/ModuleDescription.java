package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Definition;
import org.optimizationBenchmarking.utils.config.DefinitionElement;
import org.optimizationBenchmarking.utils.config.DefinitionXMLInput;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A module description.
 */
public final class ModuleDescription extends DefinitionElement {

  /** the class */
  private final Class<? extends IEvaluationModule> m_class;

  /** the parameter description */
  private Definition m_params;

  /**
   * Create the module description
   *
   * @param name
   *          the module name
   * @param clazz
   *          the module class
   * @param description
   *          the module description
   */
  ModuleDescription(final String name,
      final Class<? extends IEvaluationModule> clazz,
      final String description) {
    super(name, description);
    if (clazz == null) {
      throw new IllegalArgumentException("Module class cannot be null."); //$NON-NLS-1$
    }
    if (!(IEvaluationModule.class.isAssignableFrom(clazz))) {
      throw new IllegalArgumentException(//
          "Module class invalid (not an a sub-class of IEvaluationModule)."); //$NON-NLS-1$
    }
    this.m_class = clazz;
  }

  /**
   * Get the module class.
   *
   * @return the module class
   */
  public final Class<? extends IEvaluationModule> getModuleClass() {
    return this.m_class;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(super.calcHashCode(),//
        HashUtils.hashCode(this.m_class));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    ModuleDescription other;
    if (o == this) {
      return true;
    }

    if (o instanceof ModuleDescription) {
      other = ((ModuleDescription) o);
      return (EComparison.equals(this.m_class, other.m_class) && //
          EComparison.equals(this.getName(), other.getName()) && //
      EComparison.equals(this.getDescription(), other.getDescription()));
    }
    return false;
  }

  /**
   * Get the parameter definition of this module.
   *
   * @return the parameter definition of this module
   */
  public synchronized final Definition getParameters() {
    if (this.m_params == null) {
      this.m_params = DefinitionXMLInput.getInstance().forClass(
          this.m_class, null);
    }
    return this.m_params;
  }
}
