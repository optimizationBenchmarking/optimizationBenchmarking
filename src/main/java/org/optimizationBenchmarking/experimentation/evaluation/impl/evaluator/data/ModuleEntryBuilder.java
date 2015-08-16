package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import org.optimizationBenchmarking.experimentation.evaluation.impl.EvaluationModuleParser;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The module entry builder.
 */
public final class ModuleEntryBuilder extends
    _ConfigEntryBuilder<ModuleEntry> {

  /** the flag for the module set */
  static final int FLAG_MODULE_SET = (_ConfigEntryBuilder.FLAG_CONFIG_BUILDER_CREATED << 1);

  /** the class */
  private IEvaluationModule m_module;

  /**
   * Create the module entry builder
   *
   * @param owner
   *          the owning fsm
   */
  ModuleEntryBuilder(final HierarchicalFSM owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    if (flagValue == ModuleEntryBuilder.FLAG_MODULE_SET) {
      append.append("moduleSet");//$NON-NLS-1$
    } else {
      super.fsmFlagsAppendName(flagValue, flagIndex, append);
    }
  }

  /**
   * Set the evaluation module
   *
   * @param module
   *          the module
   */
  public synchronized final void setModule(final IEvaluationModule module) {
    ModuleEntry._validateModule(module);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        ModuleEntryBuilder.FLAG_MODULE_SET,
        ModuleEntryBuilder.FLAG_MODULE_SET, FSM.FLAG_NOTHING);
    this.m_module = this.normalize(module);
  }

  /**
   * Set the evaluation module
   *
   * @param module
   *          the module
   */
  public final void setModule(final String module) {
    this.setModule(EvaluationModuleParser.getInstance()
        .parseString(module));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  final ConfigurationBuilder _createConfigurationBuilder() {
    final ConfigurationBuilder cb;
    final HierarchicalFSM owner;
    final Configuration config;

    cb = super._createConfigurationBuilder();

    owner = this.getOwner();
    if (owner instanceof EvaluationModulesBuilder) {
      config = ((EvaluationModulesBuilder) owner).m_config;
      if (config != null) {
        cb.setOwner(config);
      }
    }
    return cb;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {
    if (child instanceof ConfigurationBuilder) {
      this.setConfiguration(((ConfigurationBuilder) child).getResult());
    } else {
      this.throwChildNotAllowed(child);
    }
    super.afterChildClosed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected final ModuleEntry compile() {
    final Configuration config;
    final IEvaluationModule module;

    this.fsmFlagsAssertTrue(ModuleEntryBuilder.FLAG_MODULE_SET
        | _ConfigEntryBuilder.FLAG_CONFIG_SET);

    config = this.m_config;
    this.m_config = null;
    module = this.m_module;
    this.m_module = null;

    return new ModuleEntry(config, module);
  }
}
