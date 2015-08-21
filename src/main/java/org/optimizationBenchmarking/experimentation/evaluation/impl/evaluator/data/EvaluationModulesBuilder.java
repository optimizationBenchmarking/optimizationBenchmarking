package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** The builder for evaluation setups. */
public final class EvaluationModulesBuilder extends
    _ConfigEntryBuilder<EvaluationModules> {
  /** the flag for the root configuration set */
  private static final int FLAG_ROOT_CONFIG_SET = (_ConfigEntryBuilder.FLAG_CONFIG_BUILDER_CREATED << 1);

  /** a module has been added */
  private static final int FLAG_MODULE_ADDED = (EvaluationModulesBuilder.FLAG_ROOT_CONFIG_SET << 1);

  /** Set the root configuration */
  private Configuration m_root;

  /** the entries */
  private ArrayList<ModuleEntry> m_entries;

  /** create the modules builder */
  public EvaluationModulesBuilder() {
    this(null);
  }

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

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_ROOT_CONFIG_SET: {
        append.append("rootConfigSet");//$NON-NLS-1$
        return;
      }
      case FLAG_MODULE_ADDED: {
        append.append("moduleAdded");//$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
        return;
      }
    }
  }

  /**
   * Set the root configuration to be used if the configuration of this
   * builder is created via the {@link #setConfiguration() builder-based
   * approach}.
   *
   * @param root
   *          the root configuration
   * @see #setConfiguration(Configuration)
   */
  public synchronized final void setRootConfiguration(
      final Configuration root) {
    _ConfigEntry._validateConfig(root);
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (EvaluationModulesBuilder.FLAG_ROOT_CONFIG_SET
            | _ConfigEntryBuilder.FLAG_CONFIG_SET
            | _ConfigEntryBuilder.FLAG_CONFIG_BUILDER_CREATED | EvaluationModulesBuilder.FLAG_MODULE_ADDED),
        EvaluationModulesBuilder.FLAG_ROOT_CONFIG_SET, FSM.FLAG_NOTHING);
    this.m_root = root;
  }

  /** {@inheritDoc} */
  @Override
  final int _forbiddenFlagsAtConfig() {
    return (_ConfigEntryBuilder.FLAG_CONFIG_SET | EvaluationModulesBuilder.FLAG_MODULE_ADDED);
  }

  /** {@inheritDoc} */
  @Override
  final ConfigurationBuilder _createConfigurationBuilder() {
    final ConfigurationBuilder cb;
    final Configuration root;

    cb = super._createConfigurationBuilder();

    root = this.m_root;
    this.m_root = null;
    if (root != null) {
      cb.setOwner(root);
    }

    return cb;
  }

  /**
   * Add a module entry
   *
   * @param entry
   *          the module entry
   */
  public synchronized final void addModule(final ModuleEntry entry) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, FSM.FLAG_NOTHING,
        EvaluationModulesBuilder.FLAG_MODULE_ADDED, FSM.FLAG_NOTHING);
    this.m_entries.add(entry);
  }

  /**
   * Add a module entry
   *
   * @return the entry builder
   */
  public synchronized final ModuleEntryBuilder addModule() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, FSM.FLAG_NOTHING,
        EvaluationModulesBuilder.FLAG_MODULE_ADDED, FSM.FLAG_NOTHING);
    return new ModuleEntryBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    if (child instanceof ConfigurationBuilder) {
      this._setConfigurationFromBuilder(((ConfigurationBuilder) child)
          .getResult());
    } else {
      if (child instanceof ModuleEntryBuilder) {
        this.addModule(((ModuleEntryBuilder) child).getResult());
      } else {
        this.throwChildNotAllowed(child);
      }
    }
    super.afterChildClosed(child);
  }

  /**
   * Get the configuration
   *
   * @return the configuration
   */
  synchronized final Configuration _getConfiguration() {
    Configuration config;

    config = this.m_config;
    if (config != null) {
      return config;
    }

    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (_ConfigEntryBuilder.FLAG_CONFIG_BUILDER_CREATED | _ConfigEntryBuilder.FLAG_CONFIG_SET),
        _ConfigEntryBuilder.FLAG_CONFIG_SET, FSM.FLAG_NOTHING);

    config = this.m_root;
    if (config == null) {
      config = Configuration.createEmpty();
    }

    this.m_config = config;
    return config;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected final EvaluationModules compile() {
    final Configuration config;
    ArrayList<ModuleEntry> list;
    ArrayListView<ModuleEntry> view;

    config = this._getConfiguration();
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
