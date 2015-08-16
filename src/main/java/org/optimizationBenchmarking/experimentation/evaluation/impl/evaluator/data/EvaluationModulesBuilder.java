package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** The builder for evaluation setups. */
public final class EvaluationModulesBuilder extends
    _ConfigEntryBuilder<EvaluationModules> {

  /** the entries */
  private ArrayList<ModuleEntry> m_entries;

  /**
   * create the modules builder
   *
   * @param owner
   *          the owning FSM, or {@code null} if none was specified
   */
  public EvaluationModulesBuilder(final HierarchicalFSM owner) {
    super(owner);
    this.m_entries = new ArrayList<>();
    this.open();
  }

  /**
   * Add a module entry
   *
   * @param entry
   *          the module entry
   */
  public synchronized final void addModule(final ModuleEntry entry) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertFalse(_ConfigEntryBuilder.FLAG_CONFIG_SET);
    this.m_entries.add(entry);
  }

  /**
   * Add a module entry
   *
   * @return the entry builder
   */
  public synchronized final ModuleEntryBuilder addModule() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertFalse(_ConfigEntryBuilder.FLAG_CONFIG_SET);
    return new ModuleEntryBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {
    if (child instanceof ConfigurationBuilder) {
      this.setConfiguration(((ConfigurationBuilder) child).getResult());
    } else {
      if (child instanceof ModuleEntryBuilder) {
        this.addModule(((ModuleEntryBuilder) child).getResult());
      } else {
        this.throwChildNotAllowed(child);
      }
    }
    super.afterChildClosed(child);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected final EvaluationModules compile() {
    final Configuration config;
    ArrayList<ModuleEntry> list;
    ArrayListView<ModuleEntry> view;

    this.fsmFlagsAssertTrue(_ConfigEntryBuilder.FLAG_CONFIG_SET);

    config = this.m_config;
    this.m_config = null;
    list = this.m_entries;
    this.m_entries = null;

    EvaluationModules._validateModules(list);

    if (list.isEmpty()) {
      view = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    } else {
      view = ArrayListView.collectionToView(list);
    }
    list = null;

    return new EvaluationModules(config, view);
  }
}
