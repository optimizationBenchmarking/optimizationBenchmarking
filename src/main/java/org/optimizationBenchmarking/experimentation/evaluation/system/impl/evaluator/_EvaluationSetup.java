package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.DocumentEvaluationOutput;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentSetEvaluationInput;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationInput;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationOutput;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;

/** the data belonging to an evaluation process */
class _EvaluationSetup {

  /** the internal synchronization object */
  private final Object m_synch;

  /**
   * the own configuration passed in via
   * {@link #_setConfiguration(Configuration)}
   */
  private Configuration m_ownConfiguration;

  /**
   * the baseline configuration passed in via
   * {@link #_setBaseConfiguration(Configuration)}
   */
  private Configuration m_baseConfiguration;

  /** the output document provider */
  private IEvaluationOutput m_output;

  /** the list of requested modules */
  private ArrayList<_ModuleEntry> m_modules;

  /** the evaluation data input */
  private IEvaluationInput m_input;

  /** the authors of the document */
  private BibAuthors m_authors;

  /** the logger */
  private Logger m_logger;

  /** instantiate */
  _EvaluationSetup() {
    super();
    this.m_modules = new ArrayList<>();
    this.m_synch = this.m_modules;
  }

  /**
   * Assign this object to another one
   * 
   * @param copyFrom
   *          the object to copy from
   */
  void _assign(final _EvaluationSetup copyFrom) {
    synchronized (this.m_synch) {
      synchronized (copyFrom.m_synch) {
        this._setLogger(copyFrom._getLogger());
        this._setBaseConfiguration(copyFrom._getBaseConfiguration());
        this._setInput(copyFrom._getInput());
        this._setOutput(copyFrom._getOutput());
        this._setAuthors(copyFrom._getAuthors());
        for (final _ModuleEntry entry : copyFrom._getModules()) {
          this._addModule(entry);
        }
      }
    }
  }

  /**
   * Set the baseline configuration for all added modules which do not have
   * an own configuration.
   * 
   * @param config
   *          the configuration
   */
  final void _setBaseConfiguration(final Configuration config) {
    synchronized (this.m_synch) {
      if (this.m_ownConfiguration != null) {
        throw new IllegalStateException(//
            "Cannot set baseline configuration after calling the configure method."); //$NON-NLS-1$
      }
      this.__doSetBaseConfiguration(config);
    }
  }

  /**
   * Get the base configuration
   * 
   * @return the base configuration
   */
  final Configuration _getBaseConfiguration() {
    return this.m_baseConfiguration;
  }

  /**
   * do set the baseline configuration
   * 
   * @param config
   *          the configuration
   */
  private final void __doSetBaseConfiguration(final Configuration config) {
    _EvaluationSetup._checkConfiguration(config);

    synchronized (this.m_synch) {
      if (this.m_baseConfiguration != config) {
        if (this.m_baseConfiguration != null) {
          throw new IllegalStateException(//
              "Cannot set baseline configuration twice."); //$NON-NLS-1$
        }

        if (!(this.m_modules.isEmpty())) {
          throw new IllegalStateException(//
              "Cannot set baseline configuration after adding modules.");//$NON-NLS-1$
        }

        this.m_baseConfiguration = config;
      }
    }
  }

  /**
   * Check whether a configuration is valid
   * 
   * @param config
   *          the configuration
   */
  static final void _checkConfiguration(final Configuration config) {
    if (config == null) {
      throw new IllegalArgumentException(//
          "Configuration cannot be null.");//$NON-NLS-1$
    }
  }

  /**
   * Add a module to the setup
   * 
   * @param module
   *          the module to add
   * @param config
   *          the configuration of the module, or {@code null} for the
   *          baseline configuration
   */
  final void _addModule(final IEvaluationModule module,
      final Configuration config) {
    _EvaluationSetup._checkModule(module);
    synchronized (this.m_synch) {
      this._addModule(new _ModuleEntry(module, this
          ._getConfiguration(config)));
    }
  }

  /**
   * Add a module entry
   * 
   * @param module
   *          the module entry to add
   */
  final void _addModule(final _ModuleEntry module) {
    _EvaluationSetup._checkModuleEntry(module);
    synchronized (this.m_synch) {
      this.m_modules.add(module);
    }
  }

  /**
   * check a module entry
   * 
   * @param module
   *          the module entry
   */
  static final void _checkModuleEntry(final _ModuleEntry module) {
    if (module == null) {
      throw new IllegalArgumentException(//
          "Cannot add null module entry."); //$NON-NLS-1$
    }
    _EvaluationSetup._checkModule(module.m_module);
    _EvaluationSetup._checkConfiguration(module.m_config);
  }

  /**
   * Get the modules which have already been configured
   * 
   * @return the modules
   */
  final ArrayList<_ModuleEntry> _getModules() {
    return this.m_modules;
  }

  /**
   * take the configured list of modules
   * 
   * @return the modules
   */
  final ArrayList<_ModuleEntry> _takeModules() {
    final ArrayList<_ModuleEntry> modules;

    synchronized (this.m_synch) {
      modules = this.m_modules;
      this.m_modules = null;
    }

    if (modules == null) {
      throw new IllegalStateException("Modules already taken."); //$NON-NLS-1$
    }
    return modules;
  }

  /**
   * Get the proper configuration to use for a module to be added
   * 
   * @param config
   *          the configuration
   * @return the configuration to use
   */
  final Configuration _getConfiguration(final Configuration config) {
    Configuration retVal;

    retVal = config;
    if (retVal == null) {
      synchronized (this.m_synch) {
        retVal = this.m_baseConfiguration;
        if (retVal == null) {
          retVal = this.m_ownConfiguration;
          if (retVal == null) {
            retVal = Configuration.getRoot();
          }
        }
      }
    }
    _EvaluationSetup._checkConfiguration(retVal);
    return retVal;
  }

  /**
   * check whether an evaluator module can be used
   * 
   * @param module
   *          the module
   */
  static final void _checkModule(final IEvaluationModule module) {
    if (module == null) {
      throw new IllegalArgumentException(//
          "Cannot add null module to evaluation."); //$NON-NLS-1$
    }

    try {
      module.checkCanUse();
    } catch (final Throwable tt) {
      throw new IllegalArgumentException(("Cannot add module '" //$NON-NLS-1$
          + module.toString() + //
          "' to evaluation, since it cannot be used (see causing exception)."),//$NON-NLS-1$
          tt);
    }
  }

  /**
   * check whether the data to be evaluated can be used
   * 
   * @param data
   *          the data
   */
  static final void _checkData(final ExperimentSet data) {
    final int experimentCount, instanceCount;

    if (data == null) {
      throw new IllegalArgumentException("Data cannot be null.");//$NON-NLS-1$
    }
    if ((experimentCount = data.getData().size()) <= 0) {
      throw new IllegalArgumentException(
          "Data must contain at least one experiment.");//$NON-NLS-1$
    }
    if (data.getDimensions().getData().isEmpty()) {
      throw new IllegalArgumentException(
          "Data must contain at least one measurement dimension.");//$NON-NLS-1$
    }
    if ((experimentCount > 1)
        && (data.getParameters().getData().isEmpty())) {
      throw new IllegalArgumentException(
          "Data contains more than one experiment, so there also must be at least one parameter to distinguish the experiments.");//$NON-NLS-1$
    }
    if ((instanceCount = data.getInstances().getData().size()) <= 0) {
      throw new IllegalArgumentException(
          "Data must contain at least one benchmark instance.");//$NON-NLS-1$
    }
    if ((instanceCount > 1) && (data.getFeatures().getData().isEmpty())) {
      throw new IllegalArgumentException(
          "Data contains more than one benchmark instance, so there also must be at least one feature to distinguish the instances.");//$NON-NLS-1$
    }
  }

  /**
   * Set the experiment data to be evaluated
   * 
   * @param data
   *          the data
   */
  final void _setInputData(final ExperimentSet data) {
    _EvaluationSetup._checkData(data);
    this._setInput(new ExperimentSetEvaluationInput(data));
  }

  /**
   * Set the input for the experiment evaluation process
   * 
   * @param input
   *          the input
   */
  final void _setInput(final IEvaluationInput input) {
    _EvaluationSetup._checkEvaluationInput(input);
    synchronized (this.m_synch) {
      this.m_input = input;
    }
  }

  /**
   * Get the evaluation input
   * 
   * @return the evaluation input
   */
  final IEvaluationInput _getInput() {
    return this.m_input;
  }

  /**
   * Take the evaluation input
   * 
   * @return the evaluation input
   */
  final IEvaluationInput _takeInput() {
    final IEvaluationInput input;

    synchronized (this.m_synch) {
      input = this.m_input;
      this.m_input = null;
    }

    if (input == null) {
      throw new IllegalStateException(//
          "Evaluation input source either not set or already taken."); //$NON-NLS-1$
    }
    return input;
  }

  /**
   * check the evaluation input
   * 
   * @param input
   *          the input
   */
  static final void _checkEvaluationInput(final IEvaluationInput input) {
    if (input == null) {
      throw new IllegalArgumentException(//
          "IEvaluationInput cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * check whether a document can be used for output
   * 
   * @param doc
   *          the document
   */
  static final void _checkDocument(final IDocument doc) {
    if (doc == null) {
      throw new IllegalArgumentException("Output document cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * Set the document to write the evaluation result to
   * 
   * @param doc
   *          the document
   */
  final void _setOutputDocument(final IDocument doc) {
    _EvaluationSetup._checkDocument(doc);
    this._setOutput(new DocumentEvaluationOutput(doc));
  }

  /**
   * Set the output to store the evaluation result to
   * 
   * @param output
   *          the output
   */
  final void _setOutput(final IEvaluationOutput output) {
    _EvaluationSetup._checkEvaluationOutput(output);
    synchronized (this.m_synch) {
      this.m_output = output;
    }
  }

  /**
   * Get the evaluation output
   * 
   * @return the evaluation output
   */
  final IEvaluationOutput _getOutput() {
    return this.m_output;
  }

  /**
   * take the output
   * 
   * @return the output
   */
  final IEvaluationOutput _takeOutput() {
    final IEvaluationOutput output;

    synchronized (this.m_synch) {
      output = this.m_output;
      this.m_output = null;
    }

    if (output == null) {
      throw new IllegalStateException(//
          "Evaluation output either not set or already taken."); //$NON-NLS-1$
    }

    return output;
  }

  /**
   * Check the evaluation output
   * 
   * @param output
   *          the output
   */
  static final void _checkEvaluationOutput(final IEvaluationOutput output) {
    if (output == null) {
      throw new IllegalArgumentException(//
          "IEvaluationOutput cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * configure
   * 
   * @param config
   *          the configuration
   */
  final void _setConfiguration(final Configuration config) {
    _EvaluationSetup._checkConfiguration(config);

    synchronized (this.m_synch) {
      if (this.m_ownConfiguration != null) {
        throw new IllegalArgumentException(//
            "Cannot set configuration twice."); //$NON-NLS-1$
      }
      this.m_ownConfiguration = config;
    }
  }

  /**
   * Get the configuration
   * 
   * @return the configuration
   */
  final Configuration _getConfiguration() {
    return this.m_ownConfiguration;
  }

  /**
   * check an author set
   * 
   * @param authors
   *          the author set
   */
  static final void _checkAuthors(final BibAuthors authors) {
    if (authors == null) {
      throw new IllegalArgumentException("Author set cannot be null."); //$NON-NLS-1$
    }
    if (authors.isEmpty()) {
      throw new IllegalArgumentException("Author set cannot be empty."); //$NON-NLS-1$
    }
  }

  /**
   * Set the authors for the output document
   * 
   * @param authors
   *          the authors for the output document
   */
  final void _setAuthors(final BibAuthors authors) {
    _EvaluationSetup._checkAuthors(authors);
    synchronized (this.m_synch) {
      this.m_authors = authors;
    }
  }

  /**
   * Get the authors
   * 
   * @return the authors
   */
  final BibAuthors _getAuthors() {
    return this.m_authors;
  }

  /**
   * Take the authors
   * 
   * @return the authors
   */
  final BibAuthors _takeAuthors() {
    final BibAuthors authors;

    synchronized (this.m_synch) {
      authors = this.m_authors;
      this.m_authors = null;
    }

    return authors;
  }

  /**
   * Set the logger
   * 
   * @param logger
   *          the logger
   */
  final void _setLogger(final Logger logger) {
    synchronized (this.m_synch) {
      this.m_logger = logger;
    }
  }

  /**
   * Get the logger
   * 
   * @return the logger
   */
  final Logger _getLogger() {
    return this.m_logger;
  }
}
