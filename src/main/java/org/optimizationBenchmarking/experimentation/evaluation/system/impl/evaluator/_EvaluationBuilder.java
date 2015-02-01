package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IAppendix;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredExperimentModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredExperimentSetModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IDescription;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluation;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModuleSetup;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentModuleSetup;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentSetStatistic;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentStatistic;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ConfigurableToolJobBuilder;

/** The evaluation builder */
final class _EvaluationBuilder extends
    ConfigurableToolJobBuilder<IEvaluation, _EvaluationBuilder> implements
    IEvaluationBuilder {

  /** the own configuration passed in via {@link #configure(Configuration)} */
  private Configuration m_config;

  /**
   * the baseline configuration passed in via
   * {@link #setBaseConfiguration(Configuration)}
   */
  private Configuration m_base;

  /** the document */
  private IDocument m_doc;

  /** the list of requested modules */
  private ArrayList<_ModuleEntry> m_modules;

  /** the data */
  private ExperimentSet m_data;

  /** the authors of the document */
  private BibAuthors m_authors;

  /** build the evaluation procedure */
  _EvaluationBuilder() {
    super();
    this.m_modules = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public final IEvaluationBuilder setBaseConfiguration(
      final Configuration config) {

    if (this.m_config != null) {
      throw new IllegalStateException(//
          "Cannot set basline configuration after calling configure."); //$NON-NLS-1$
    }
    this.__doSetBaseConfiguration(config);
    return this;
  }

  /**
   * do set the baseline configuration
   * 
   * @param config
   *          the configuration
   */
  private final void __doSetBaseConfiguration(final Configuration config) {
    if (this.m_base != null) {
      throw new IllegalStateException(//
          "Cannot set baseline configuration twice."); //$NON-NLS-1$
    }
    if (!(this.m_modules.isEmpty())) {
      throw new IllegalStateException(//
          "Cannot set baseline configuration after adding modules.");//$NON-NLS-1$
    }
    _EvaluationBuilder._checkConfig(config);
    this.m_base = config;
  }

  /**
   * Check whether a configuration is valid
   * 
   * @param config
   *          the configuration
   */
  static final void _checkConfig(final Configuration config) {
    if (config == null) {
      throw new IllegalArgumentException(//
          "Configuration cannot be null.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IEvaluationBuilder addModule(
      final IEvaluationModule module, final Configuration config) {
    _EvaluationBuilder._checkModule(module);
    this.m_modules.add(new _ModuleEntry(module, this.__getConfig(config)));

    return this;
  }

  /**
   * Get the proper configuration to use
   * 
   * @param config
   *          the configuration
   * @return the configuration to use
   */
  private final Configuration __getConfig(final Configuration config) {
    final Configuration ret;
    if (config != null) {
      ret = config;
    } else {
      if (this.m_base != null) {
        ret = this.m_base;
      } else {
        if (this.m_config != null) {
          ret = this.m_config;
        } else {
          ret = Configuration.getRoot();
        }
      }
    }
    _EvaluationBuilder._checkConfig(ret);
    return ret;
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

  /** {@inheritDoc} */
  @Override
  public final IEvaluationBuilder setData(final ExperimentSet data) {
    _EvaluationBuilder._checkData(data);
    this.m_data = data;
    return this;
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

  /** {@inheritDoc} */
  @Override
  public final IEvaluationBuilder setDocument(final IDocument doc) {
    _EvaluationBuilder._checkDocument(doc);
    this.m_doc = doc;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected final void validate() {
    super.validate();
    if (this.m_modules.isEmpty()) {
      throw new IllegalStateException(//
          "At least one evaluation module must be defined."); //$NON-NLS-1$
    }
    _EvaluationBuilder._checkData(this.m_data);
    _EvaluationBuilder._checkDocument(this.m_doc);
  }

  /**
   * Obtain the complete list of all modules which need to be configured
   * and executed. This method resolves all module requirements. The other
   * modules required by one module will be inserted before that module
   * into the list, unless they already are in the list.
   * 
   * @return the list
   * @throws IllegalArgumentException
   *           if some modules are invalid
   */
  private final ArrayList<_ModuleEntry> __compileModuleList()
      throws IllegalArgumentException {
    ArrayList<_ModuleEntry> modules;
    IEvaluationModule module;
    _ModuleEntry entry;
    ReflectiveOperationException except;
    Iterable<Class<? extends IEvaluationModule>> requiredIt;
    int index;
    String s;

    modules = this.m_modules;
    this.m_modules = null;

    for (index = 0; index < modules.size();) {
      entry = modules.get(index);
      requiredIt = entry.m_module.getRequiredModules();
      if (requiredIt != null) {

        requirements: for (final Class<? extends IEvaluationModule> required : requiredIt) {

          for (final _ModuleEntry have : modules) {
            if (required.isAssignableFrom(have.m_module.getClass())) {
              continue requirements;
            }
          }

          except = null;
          module = null;
          try {
            module = ReflectionUtils.getInstance(IEvaluationModule.class,
                required);
          } catch (final ReflectiveOperationException refError) {
            except = refError;
            module = null;
          }

          if (module == null) {
            s = ("Could not obtain instance of module class "//$NON-NLS-1$ 
            + required);
            if (except != null) {
              throw new IllegalArgumentException(s, except);
            }
            throw new IllegalArgumentException(s);
          }

          modules.add(index,
              new _ModuleEntry(module, this.__getConfig(entry.m_config)));
        }
      }
    }

    return modules;
  }

  /**
   * Configure a compiled list of modules
   * 
   * @param entries
   *          the entries
   * @param hasMoreThanOneExperiment
   *          is there more than a single experiment?
   * @return an array of length 4, containing the description modules, the
   *         single-experiment modules, the experiment-set modules, and the
   *         appendix modules.
   */
  @SuppressWarnings("unchecked")
  private final ArrayList<IConfiguredModule>[] __configureModules(
      final boolean hasMoreThanOneExperiment,
      final ArrayList<_ModuleEntry> entries) {
    final ArrayList<IConfiguredModule>[] res;
    IConfiguredModule configured;
    IEvaluationModuleSetup setup;
    int type;

    res = new ArrayList[4];

    for (final _ModuleEntry entry : entries) {
      setup = entry.m_module.use();
      if (setup == null) {
        throw new IllegalArgumentException(//
            "Module setup object cannot be null, but use() of module '" //$NON-NLS-1$
                + entry.m_module + "' returned null.");//$NON-NLS-1$
      }
      if (this.m_logger != null) {
        setup.setLogger(this.m_logger);
      }
      setup.configure(entry.m_config);
      configured = setup.create();
      if (configured == null) {
        throw new IllegalArgumentException(//
            "Configured module job cannot be null, but create() of use() of module '" //$NON-NLS-1$
                + entry.m_module + "' returned null.");//$NON-NLS-1$
      }

      if (entry.m_module instanceof IDescription) {
        type = 0;
      } else {
        if (entry.m_module instanceof IExperimentStatistic) {
          type = 1;
        } else {
          if (entry.m_module instanceof IExperimentSetStatistic) {
            type = 2;
          } else {
            if (entry.m_module instanceof IAppendix) {
              type = 3;
            } else {
              if (setup instanceof IExperimentModuleSetup) {
                type = 1;
              } else {
                if (configured instanceof IConfiguredExperimentModule) {
                  type = 1;
                } else {
                  if (configured instanceof IConfiguredExperimentSetModule) {
                    type = 2;
                  } else {
                    throw new IllegalArgumentException(//
                        "Module job object of module '" //$NON-NLS-1$
                            + entry.m_module + "' not recognized.");//$NON-NLS-1$
                  }
                }
              }
            }
          }
        }
      }

      if ((type != 2) || hasMoreThanOneExperiment) {
        if (res[type] == null) {
          res[type] = new ArrayList<>();
        }
        res[type].add(configured);
      }
    }

    _EvaluationBuilder.__checkConfigured(res);

    return res;
  }

  /**
   * Check the configured elements
   * 
   * @param configured
   *          the configured elements
   */
  private static final void __checkConfigured(
      final ArrayList<IConfiguredModule>[] configured) {
    if (configured == null) {
      throw new IllegalArgumentException(//
          "Set of configured modules cannot be null."); //$NON-NLS-1$
    }

    if (configured.length != 4) {
      throw new IllegalArgumentException(//
          "Set of configured modules must have dimension 4."); //$NON-NLS-1$
    }

    if (((configured[1] == null) || (configured[1].isEmpty()))
        && ((configured[2] == null) || (configured[2].isEmpty()))) {
      _EvaluationBuilder._noModuleError();
    }
  }

  /**
   * Call this if there is no module for computing statistics
   */
  static final void _noModuleError() {
    throw new IllegalArgumentException(//
        "There must be at least one module job which actually calculates a statistic, i.e., works on single experiments or experiment sets. Maybe you only provided experiment set statistics but the experiment set only contained a single experiment? Or maybe you just provided description and appendix modules?"); //$NON-NLS-1$
  }

  /**
   * Build the containment hierarchy of a list of modules
   * 
   * @param configured
   *          the configured modules
   * @return the containment hierarchy
   */
  @SuppressWarnings("incomplete-switch")
  private static final _ModuleWrapper[] __buildWrappers(
      final IConfiguredModule[] configured) {
    final int length;
    final int[][] containment, order;
    final _ModuleWrapper[] res;
    final boolean[] done;
    IConfiguredModule a, b;
    int i, j;

    if ((configured == null) || ((length = configured.length) <= 0)) {
      return null;
    }

    containment = new int[length][];
    order = new int[length][];

    // allocate
    for (i = 0; i < length; i++) {
      order[i] = new int[i];
      containment[i] = new int[i];
    }

    // find the hard-coded relationships
    for (i = 0; i < length; i++) {
      a = configured[i];
      for (j = length; (--j) >= 0;) {
        if (i == j) {
          continue;
        }
        b = configured[j];
        switch (a.getRelationship(b)) {
          case EXECUTE_BEFORE: {
            if (i < j) {
              if (order[j][i] < 0) {
                _EvaluationBuilder.__orderError(a, b);
              }
              order[j][i] = 1;
            } else {
              if (order[i][j] > 0) {
                _EvaluationBuilder.__orderError(a, b);
              }
              order[i][j] = (-1);
            }
            break;
          }
          case EXECUTE_AFTER: {
            if (i < j) {
              if (order[j][i] > 0) {
                _EvaluationBuilder.__orderError(a, b);
              }
              order[j][i] = (-1);
            } else {
              if (order[i][j] < 0) {
                _EvaluationBuilder.__orderError(a, b);
              }
              order[i][j] = 1;
            }
            break;
          }

          case CONTAINS: {
            if (i < j) {
              if (containment[j][i] < 0) {
                _EvaluationBuilder.__containmentError(a, b);
              }
              containment[j][i] = 1;
            } else {
              if (containment[i][j] > 0) {
                _EvaluationBuilder.__containmentError(a, b);
              }
              containment[i][j] = (-1);
            }
            break;
          }
          case CONTAINED_IN: {
            if (i < j) {
              if (containment[j][i] > 0) {
                _EvaluationBuilder.__containmentError(a, b);
              }
              containment[j][i] = (-1);
            } else {
              if (containment[i][j] < 0) {
                _EvaluationBuilder.__containmentError(a, b);
              }
              containment[i][j] = 1;
            }
            break;
          }
        }

      }
    }

    // we now have loaded all hard-coded order and containment hierarchies
    _EvaluationBuilder.__computeTransitiveRelations(order, configured,
        true);
    _EvaluationBuilder.__computeTransitiveRelations(containment,
        configured, false);

    done = new boolean[length];
    res = _EvaluationBuilder.__buildHierarchy(configured, -1, containment,
        order, done);

    for (i = done.length; (--i) >= 0;) {
      if (!(done[i])) {
        throw new IllegalStateException("Module '" + configured[i] //$NON-NLS-1$
            + "' could not be placed into hierarchy?!"); //$NON-NLS-1$
      }
    }

    return res;
  }

  /**
   * create the hierarchy of modules
   * 
   * @param modules
   *          the modules
   * @param containedIn
   *          the index of the owning element, or {@code -1} for none
   * @param containment
   *          the containment
   * @param order
   *          the order
   * @param done
   *          the done wrappers
   * @return the wrapper array
   */
  @SuppressWarnings("incomplete-switch")
  private static final _ModuleWrapper[] __buildHierarchy(
      final IConfiguredModule[] modules, final int containedIn,
      final int[][] containment, final int[][] order, final boolean[] done) {
    final int length;
    ArrayList<_ModuleWrapper> temp;
    IConfiguredModule cmp;
    int i, j, k;

    temp = null;
    length = containment.length;
    find: for (i = 0; i < containment.length; i++) {

      // the module has already been processed
      if (done[i]) {
        continue;
      }

      // check if the module is contained inside the expected module
      if (containedIn >= 0) {
        if (i == containedIn) {
          continue;
        }
        if (i > containedIn) {
          if (containment[i][containedIn] != 1) {
            continue find;
          }
        } else {
          if (containment[containedIn][i] != (-1)) {
            continue find;
          }
        }
      }

      // now check if we are in a deeper hierarchical nesting
      checkerA: for (j = i; (--j) >= 0;) {
        switch (containment[i][j]) {
          case (-1): {
            continue checkerA;
          }
          case 1: {
            if (!done[j]) {
              continue find;
            }
          }
        }
      }

      checkerB: for (j = i; (++j) < length;) {
        switch (containment[j][i]) {
          case (-1): {
            if (!done[j]) {
              continue find;
            }
          }
          case 1: {
            continue checkerB;
          }
        }
      }

      // If we get here, then the module is contained in the other module
      // and can be added to the list.
      done[i] = true;

      // so let's find out where to insert it
      if (temp == null) {
        temp = new ArrayList<>();
        j = -1;
      } else {
        findIndex: for (j = temp.size(); (--j) >= 0;) {
          cmp = temp.get(j).m_module;
          inner: for (k = i; (--k) >= 0;) {
            if (modules[k] == cmp) {
              break inner;
            }
            if (order[i][k] >= 0) {
              break findIndex;
            }
          }
        }
      }

      temp.add(
          (j + 1),
          new _ModuleWrapper(_EvaluationBuilder.__buildHierarchy(modules,
              i, containment, order, done), modules[i]));
    }

    return ((temp == null) ? null : //
        temp.toArray(new _ModuleWrapper[temp.size()]));
  }

  /**
   * Compute all the transitive relations
   * 
   * @param relations
   *          the relations array
   * @param modules
   *          the modules
   * @param isOrder
   *          are we doing orders (or containments?)
   */
  private static final void __computeTransitiveRelations(
      final int[][] relations, final IConfiguredModule[] modules,
      final boolean isOrder) {
    final int length;
    int i, j, k, r1, r2, r3;
    boolean change;

    length = relations.length;
    do {
      change = false;

      for (i = length; (--i) > 1;) {
        for (j = i; (--j) > 0;) {
          r1 = relations[i][j];
          for (k = j; (--k) >= 0;) {
            r2 = relations[j][k];

            if ((r1 == r2) && (r1 != 0)) {
              r3 = relations[i][k];
              if (r3 != r1) {
                if (r3 != 0) {
                  if (isOrder) {
                    _EvaluationBuilder
                        .__orderError(modules[i], modules[k]);
                  } else {
                    _EvaluationBuilder.__containmentError(modules[i],
                        modules[k]);
                  }
                } else {
                  relations[i][k] = r2;
                  change = true;
                }
              }
            }

          }
        }
      }

    } while (change);
  }

  /**
   * An order error has occurred
   * 
   * @param a
   *          the first module
   * @param b
   *          the second module
   */
  private static final void __orderError(final IConfiguredModule a,
      final IConfiguredModule b) {
    throw new IllegalStateException(
        "The execution order of the modules is inconsistent: Module '" + a //$NON-NLS-1$
            + "' comes both before and after module '" + b + //$NON-NLS-1$
            "'.");//$NON-NLS-1$
  }

  /**
   * A containment error has occurred
   * 
   * @param a
   *          the first module
   * @param b
   *          the second module
   */
  private static final void __containmentError(final IConfiguredModule a,
      final IConfiguredModule b) {
    throw new IllegalStateException(
        "The containment hierarchy of the modules is inconsistent: Module '" + a //$NON-NLS-1$
            + "' is both contained inside and also contains module '" + b + //$NON-NLS-1$
            "'.");//$NON-NLS-1$
  }

  /**
   * Build the module hierarchy
   * 
   * @param configured
   *          the configured module sets
   * @return the modules object
   */
  private static final _Modules __makeModules(
      final ArrayList<IConfiguredModule>[] configured) {
    final _Descriptions desc;
    final _ExperimentStatistics experimentStatistics;
    final _ExperimentSetStatistics experimentSetStatistics;
    final _Appendices appendices;
    int size;
    _ModuleWrapper[] wrappers;

    if ((configured[0] != null) && ((size = configured[0].size()) > 0)) {
      wrappers = _EvaluationBuilder.__buildWrappers(configured[0]
          .toArray(new IConfiguredModule[size]));
      configured[0] = null;
      if ((wrappers == null) || (wrappers.length <= 0)) {
        throw new IllegalStateException("Description modules lost?");//$NON-NLS-1$
      }
      desc = new _Descriptions(wrappers);
    } else {
      desc = null;
    }

    if ((configured[1] != null) && ((size = configured[1].size()) > 0)) {
      wrappers = _EvaluationBuilder.__buildWrappers(configured[1]
          .toArray(new IConfiguredModule[size]));
      configured[1] = null;
      if ((wrappers == null) || (wrappers.length <= 0)) {
        throw new IllegalStateException(
            "Experiment statistic modules lost?");//$NON-NLS-1$
      }
      experimentStatistics = new _ExperimentStatistics(wrappers);
    } else {
      experimentStatistics = null;
    }

    if ((configured[2] != null) && ((size = configured[2].size()) > 0)) {
      wrappers = _EvaluationBuilder.__buildWrappers(configured[2]
          .toArray(new IConfiguredModule[size]));
      configured[2] = null;
      if ((wrappers == null) || (wrappers.length <= 0)) {
        throw new IllegalStateException(
            "Experiment set statistic modules lost?");//$NON-NLS-1$
      }
      experimentSetStatistics = new _ExperimentSetStatistics(wrappers);
    } else {
      experimentSetStatistics = null;
    }

    if ((configured[3] != null) && ((size = configured[3].size()) > 0)) {
      wrappers = _EvaluationBuilder.__buildWrappers(configured[3]
          .toArray(new IConfiguredModule[size]));
      configured[3] = null;
      if ((wrappers == null) || (wrappers.length <= 0)) {
        throw new IllegalStateException("Appendix modules lost?");//$NON-NLS-1$
      }
      appendices = new _Appendices(wrappers);
    } else {
      appendices = null;
    }

    if ((experimentStatistics == null)
        && (experimentSetStatistics == null)) {
      _EvaluationBuilder._noModuleError();
    }

    return new _Modules(desc, experimentStatistics,
        experimentSetStatistics, appendices);
  }

  /**
   * validate the authors
   * 
   * @param authors
   *          the authors
   */
  static final void _checkAuthors(final BibAuthors authors) {
    if (authors == null) {
      throw new IllegalArgumentException("Authors cannot be null.");//$NON-NLS-1$
    }
    if (authors.size() <= 0) {
      throw new IllegalArgumentException("Authors cannot be empty."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IEvaluationBuilder setAuthors(final BibAuthors authors) {
    _EvaluationBuilder._checkAuthors(authors);
    this.m_authors = authors;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final IEvaluation create() {
    ArrayList<_ModuleEntry> all;
    ArrayList<IConfiguredModule>[] configured;
    _Modules modules;
    BibAuthors authors;

    this.validate();

    all = this.__compileModuleList();
    this.m_modules = null;
    if (all.isEmpty()) {
      throw new IllegalArgumentException(//
          "There must be at least one evaluation module."); //$NON-NLS-1$
    }
    configured = this.__configureModules(
        (this.m_data.getData().size() > 1), all);
    _EvaluationBuilder.__checkConfigured(configured);

    modules = _EvaluationBuilder.__makeModules(configured);
    configured = null;

    authors = this.m_authors;
    if (authors == null) {
      try (final BibAuthorsBuilder builder = new BibAuthorsBuilder()) {
        try (final BibAuthorBuilder author = builder.author()) {
          author.setFamilyName("Anonymous");//$NON-NLS-1$
          author.setPersonalName("Anton");//$NON-NLS-1$
        }
        authors = builder.getResult();
      }
    }

    return new _Evaluation(this.m_doc, this.m_data, modules, authors);
  }
}
