package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IAppendix;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredExperimentModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredExperimentSetModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IDescription;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModuleSetup;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentModuleSetup;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentSetStatistic;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentStatistic;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** In this class, we group the code for building modules */
final class _ModulesBuilder {

  /** a description module */
  private static final int MODULE_TYPE_DESCRIPTION = 0;
  /** a single-experiment module */
  private static final int MODULE_TYPE_SINGLE_EXPERIMENT = (_ModulesBuilder.MODULE_TYPE_DESCRIPTION + 1);
  /** an experiment set module */
  private static final int MODULE_TYPE_EXPERIMENT_SET = (_ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT + 1);
  /** an appendix module */
  private static final int MODULE_TYPE_APPENDIX = (_ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET + 1);

  /**
   * Build the module hierarchy
   * 
   * @param setup
   *          the setup
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the configured module hierarchy
   */
  static final _Modules _buildModules(final _EvaluationSetup setup,
      final ExperimentSet data, final Logger logger) {
    ArrayList<_ModuleEntry> allModules;
    ArrayList<IConfiguredModule>[] allConfiguredModules;
    _Modules modules;

    allModules = _ModulesBuilder.__compileModuleList(setup, logger);
    _ModulesBuilder._checkModules(allModules, null);
    allConfiguredModules = _ModulesBuilder.__configureModules(allModules,
        data, logger);
    allModules = null;
    _ModulesBuilder.__checkConfigured(allConfiguredModules);

    modules = _ModulesBuilder.__makeModules(allConfiguredModules, logger);
    allConfiguredModules = null;

    if (modules == null) {
      throw new IllegalStateException("Root module cannot be null."); //$NON-NLS-1$
    }

    return modules;
  }

  /**
   * check whether the modules are OK
   * 
   * @param list
   *          the list of modules
   * @param textOut
   *          the destination to write module information to
   */
  static final void _checkModules(final ArrayList<_ModuleEntry> list,
      final MemoryTextOutput textOut) {
    if ((list == null) || (list.isEmpty())) {
      throw new IllegalStateException(
          "There must be at least one module which can be applied to the current data, but no such module was defined."); //$NON-NLS-1$
    }
    _ModulesBuilder.__checkModuleList(list, textOut);
  }

  /**
   * check the single module
   * 
   * @param modules
   *          the list of modules to check
   * @param textOut
   *          the text output destination to write to
   */
  private static final void __checkModuleList(final ArrayList<?> modules,
      final MemoryTextOutput textOut) {
    int size;
    _ModuleEntry entry;

    if (modules != null) {
      size = modules.size();
    } else {
      size = 0;
    }

    if (textOut != null) {
      switch (size) {
        case 0: {
          textOut.append("no modules");//$NON-NLS-1$
          return;
        }
        case 1: {
          textOut.append("1 module"); //$NON-NLS-1$
          break;
        }
        default: {
          textOut.append(size);
          textOut.append(" modules"); //$NON-NLS-1$
        }
      }
    }

    if (size <= 0) {
      return;
    }

    size = 0;
    for (final Object o : modules) {
      size++;
      if (o == null) {
        throw new IllegalArgumentException("Module at index " //$NON-NLS-1$
            + size + " is null."); //$NON-NLS-1$        
      }

      if (textOut != null) {
        textOut.append((size <= 1) ? ':' : ',');
        textOut.append(' ');
      }
      if (o instanceof _ModuleEntry) {
        entry = ((_ModuleEntry) o);
        _EvaluationSetup._checkModuleEntry(entry);
        if (textOut != null) {
          textOut.append(TextUtils.className(entry.m_module.getClass()));
        }
      } else {
        if (textOut != null) {
          textOut.append(TextUtils.className(o.getClass()));
        }
      }
    }
  }

  /**
   * Call this if there is no module for computing statistics
   */
  static final void _noStatisticModuleError() {
    throw new IllegalArgumentException(//
        "There must be at least one module job which actually calculates a statistic, i.e., works on single experiments or experiment sets. Maybe you only provided experiment set statistics but the experiment set only contained a single experiment? Or maybe you just provided description and appendix modules?"); //$NON-NLS-1$
  }

  /**
   * Obtain the complete list of all modules which need to be configured
   * and executed. This method resolves all module requirements. The other
   * modules required by one module will be inserted before that module
   * into the list, unless they already are in the list.
   * 
   * @param setup
   *          the setup
   * @param logger
   *          the logger to use
   * @return the list
   */
  private static final ArrayList<_ModuleEntry> __compileModuleList(
      final _EvaluationSetup setup, final Logger logger) {
    final MemoryTextOutput mto;
    final ArrayList<_ModuleEntry> modules;
    IEvaluationModule module;
    _ModuleEntry entry;
    ReflectiveOperationException except;
    Iterable<Class<? extends IEvaluationModule>> requiredIt;
    int index;
    String s;
    boolean canInc;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer(//
          "Now beginning to compile module list by resolving dependencies."); //$NON-NLS-1$
    }

    modules = setup._takeModules();

    try {
      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        mto = new MemoryTextOutput();
        mto.append("Original list of modules contains ");//$NON-NLS-1$
      } else {
        mto = null;
      }
      _ModulesBuilder._checkModules(modules, mto);

      if ((mto != null) && (logger != null)
          && (logger.isLoggable(Level.FINER))) {
        mto.append('.');
        logger.finer(mto.toString());
        mto.clear();
      }

      for (index = 0; index < modules.size();) {
        canInc = true;
        entry = modules.get(index);

        _EvaluationSetup._checkModuleEntry(entry);

        requiredIt = entry.m_module.getRequiredModules();
        if (requiredIt != null) {

          requirements: for (final Class<? extends IEvaluationModule> required : requiredIt) {

            for (final _ModuleEntry have : modules) {
              if (required.isAssignableFrom(have.m_module.getClass())) {
                continue requirements;
              }
            }

            canInc = false;
            except = null;
            module = null;
            try {
              module = ReflectionUtils.getInstance(
                  IEvaluationModule.class, required, null);
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

            modules.add(
                index,
                new _ModuleEntry(module, setup
                    ._getConfiguration(entry.m_config)));
          }
        }

        if (canInc) {
          index++;
        }
      }

    } catch (final RuntimeException re) {
      ErrorUtils
          .logError(
              logger,
              "Unrecoverable error during compiliation of module list. Maybe there are unresolved dependencies.", //$NON-NLS-1$
              re, false, RethrowMode.AS_RUNTIME_EXCEPTION);
      return null; // never reached
    }

    _ModulesBuilder._checkModules(modules, mto);
    if ((mto != null) && (logger != null)
        && (logger.isLoggable(Level.FINER))) {
      mto.append("Finished compiling list of modules, now contains ");//$NON-NLS-1$      
      logger.finer(mto.toString());
    }

    return modules;
  }

  /**
   * Configure a compiled list of modules
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return an array of length 4, containing the description modules, the
   *         single-experiment modules, the experiment-set modules, and the
   *         appendix modules.
   */
  @SuppressWarnings("unchecked")
  private static final ArrayList<IConfiguredModule>[] __configureModules(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) {
    final ArrayList<IConfiguredModule>[] res;
    final MemoryTextOutput mto;
    final boolean hasMoreThanOneExperiment;
    IConfiguredModule configured;
    IEvaluationModuleSetup setup;
    int type;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer(//
          "The " + entries.size() + //$NON-NLS-1$
              " modules will now be configured and divided into description, experiment, experiment set, and appendix modules."); //$NON-NLS-1$
    }

    res = new ArrayList[4];
    hasMoreThanOneExperiment = (data.getData().size() > 1);
    try {
      for (final _ModuleEntry entry : entries) {
        _EvaluationSetup._checkModuleEntry(entry);
        setup = entry.m_module.use();
        if (setup == null) {
          throw new IllegalArgumentException(//
              "Module setup object cannot be null, but use() of module '" //$NON-NLS-1$
                  + entry.m_module + "' returned null.");//$NON-NLS-1$
        }
        if (logger != null) {
          setup.setLogger(logger);
        }
        setup.configure(entry.m_config);
        configured = setup.create();
        if (configured == null) {
          throw new IllegalArgumentException(//
              "Configured module job cannot be null, but create() of use() of module '" //$NON-NLS-1$
                  + entry.m_module + "' returned null.");//$NON-NLS-1$
        }

        if (entry.m_module instanceof IDescription) {
          type = _ModulesBuilder.MODULE_TYPE_DESCRIPTION;
        } else {
          if (entry.m_module instanceof IExperimentStatistic) {
            type = _ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT;
          } else {
            if (entry.m_module instanceof IExperimentSetStatistic) {
              type = _ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET;
            } else {
              if (entry.m_module instanceof IAppendix) {
                type = _ModulesBuilder.MODULE_TYPE_APPENDIX;
              } else {
                if (setup instanceof IExperimentModuleSetup) {
                  type = _ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT;
                } else {
                  if (configured instanceof IConfiguredExperimentModule) {
                    type = _ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT;
                  } else {
                    if (configured instanceof IConfiguredExperimentSetModule) {
                      type = _ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET;
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

        if ((type != _ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET)
            || hasMoreThanOneExperiment) {
          if (res[type] == null) {
            res[type] = new ArrayList<>();
          }
          res[type].add(configured);
        }
      }

      _ModulesBuilder.__checkConfigured(res);
    } catch (final RuntimeException re) {
      ErrorUtils
          .logError(
              logger,
              "Unrecoverable during module configuration process. Maybe you did not specify modules that can actually compute a statistic over the provided data.", //$NON-NLS-1$
              re, false, RethrowMode.AS_RUNTIME_EXCEPTION);
      return null;// never reached
    }

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      mto = new MemoryTextOutput();
      mto.append("The "); //$NON-NLS-1$
      mto.append(entries.size());
      mto.append(//
      " modules have been configured. The list of configured description modules contains ");//$NON-NLS-1$
      _ModulesBuilder.__checkModuleList(
          res[_ModulesBuilder.MODULE_TYPE_DESCRIPTION], mto);
      mto.append(//
      ". The list of configured single-experiment modules contains ");//$NON-NLS-1$
      _ModulesBuilder.__checkModuleList(
          res[_ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT], mto);
      mto.append(//
      ". The list of configured experiment-set modules contains ");//$NON-NLS-1$
      _ModulesBuilder.__checkModuleList(
          res[_ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET], mto);
      mto.append(//
      ". The list of configured appendix modules contains ");//$NON-NLS-1$
      _ModulesBuilder.__checkModuleList(
          res[_ModulesBuilder.MODULE_TYPE_APPENDIX], mto);
      mto.append('.');
      logger.finer(mto.toString());
    }

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
          "Set of configured modules must have dimension 4 (description modules, single-experiment modules, experiment set modules, appendix modules)."); //$NON-NLS-1$
    }

    if (((configured[_ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT] == null) || (configured[_ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT]
        .isEmpty()))
        && ((configured[_ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET] == null) || (configured[_ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET]
            .isEmpty()))) {
      _ModulesBuilder._noStatisticModuleError();
    }
  }

  /**
   * Build the containment hierarchy of a list of modules
   * 
   * @param configured
   *          the configured modules
   * @param logger
   *          the logger
   * @return the containment hierarchy
   */
  @SuppressWarnings("incomplete-switch")
  private static final _ModuleWrapper[] __buildWrappers(
      final IConfiguredModule[] configured, final Logger logger) {
    final int length;
    final int[][] containment, order;
    final _ModuleWrapper[] res;
    final boolean[] done;
    IConfiguredModule a, b;
    int i, j;

    if ((configured == null) || ((length = configured.length) <= 0)) {
      return null;
    }
    try {
      containment = new int[length][];
      order = new int[length][];

      // allocate
      for (i = 0; i < length; i++) {
        order[i] = new int[i];
        containment[i] = new int[i];
      }

      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Obtaining order and containment preferences."); //$NON-NLS-1$
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
                  _ModulesBuilder.__orderError(a, b);
                }
                order[j][i] = 1;
              } else {
                if (order[i][j] > 0) {
                  _ModulesBuilder.__orderError(a, b);
                }
                order[i][j] = (-1);
              }
              break;
            }
            case EXECUTE_AFTER: {
              if (i < j) {
                if (order[j][i] > 0) {
                  _ModulesBuilder.__orderError(a, b);
                }
                order[j][i] = (-1);
              } else {
                if (order[i][j] < 0) {
                  _ModulesBuilder.__orderError(a, b);
                }
                order[i][j] = 1;
              }
              break;
            }

            case CONTAINS: {
              if (i < j) {
                if (containment[j][i] < 0) {
                  _ModulesBuilder.__containmentError(a, b);
                }
                containment[j][i] = 1;
              } else {
                if (containment[i][j] > 0) {
                  _ModulesBuilder.__containmentError(a, b);
                }
                containment[i][j] = (-1);
              }
              break;
            }
            case CONTAINED_IN: {
              if (i < j) {
                if (containment[j][i] > 0) {
                  _ModulesBuilder.__containmentError(a, b);
                }
                containment[j][i] = (-1);
              } else {
                if (containment[i][j] < 0) {
                  _ModulesBuilder.__containmentError(a, b);
                }
                containment[i][j] = 1;
              }
              break;
            }
          }

        }
      }

      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Computing transitive relationship."); //$NON-NLS-1$
      }

      // we now have loaded all hard-coded order and containment
      // hierarchies
      _ModulesBuilder
          .__computeTransitiveRelations(order, configured, true);
      _ModulesBuilder.__computeTransitiveRelations(containment,
          configured, false);

      done = new boolean[length];
      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Building module hierarchy recursively."); //$NON-NLS-1$
      }
      res = _ModulesBuilder.__buildHierarchy(logger, configured, -1,
          containment, order, done);

      for (i = done.length; (--i) >= 0;) {
        if (!(done[i])) {
          throw new IllegalStateException("Module '" + configured[i] //$NON-NLS-1$
              + "' could not be placed into hierarchy?!"); //$NON-NLS-1$
        }
      }
    } catch (final RuntimeException re) {
      ErrorUtils.logError(logger, //
          "Unrecoverable error while building array of module wrappers.",//$NON-NLS-1$
          re, false, RethrowMode.AS_RUNTIME_EXCEPTION);
      return null;// never reached
    }

    return res;
  }

  /**
   * create the hierarchy of modules
   * 
   * @param logger
   *          the logger
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
      final Logger logger, final IConfiguredModule[] modules,
      final int containedIn, final int[][] containment,
      final int[][] order, final boolean[] done) {
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
          new _ModuleWrapper(logger, _ModulesBuilder.__buildHierarchy(
              logger, modules, i, containment, order, done), modules[i]));
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
                    _ModulesBuilder.__orderError(modules[i], modules[k]);
                  } else {
                    _ModulesBuilder.__containmentError(modules[i],
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
   * @param logger
   *          the logger
   * @return the modules object
   */
  private static final _Modules __makeModules(
      final ArrayList<IConfiguredModule>[] configured, final Logger logger) {
    final _Descriptions desc;
    final _ExperimentStatistics experimentStatistics;
    final _ExperimentSetStatistics experimentSetStatistics;
    final _Appendices appendices;
    ArrayList<IConfiguredModule> list;
    int size;
    _ModuleWrapper[] wrappers;

    try {
      if (((list = configured[_ModulesBuilder.MODULE_TYPE_DESCRIPTION]) != null)
          && ((size = list.size()) > 0)) {
        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          logger.finer("Building hierarchy and wrappers for "//$NON-NLS-1$
              + size + " description modules.");//$NON-NLS-1$
        }

        wrappers = _ModulesBuilder.__buildWrappers(
            list.toArray(new IConfiguredModule[size]), logger);
        configured[_ModulesBuilder.MODULE_TYPE_DESCRIPTION] = null;
        if ((wrappers == null) || (wrappers.length <= 0)) {
          throw new IllegalStateException("Description modules lost?");//$NON-NLS-1$
        }
        desc = new _Descriptions(logger, wrappers);
      } else {
        desc = null;
      }

      if (((list = configured[_ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT]) != null)
          && ((size = list.size()) > 0)) {
        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          logger.finer("Building hierarchy and wrappers for "//$NON-NLS-1$
              + size + " single-experiment modules.");//$NON-NLS-1$
        }

        wrappers = _ModulesBuilder.__buildWrappers(
            list.toArray(new IConfiguredModule[size]), logger);
        configured[_ModulesBuilder.MODULE_TYPE_SINGLE_EXPERIMENT] = null;
        if ((wrappers == null) || (wrappers.length <= 0)) {
          throw new IllegalStateException(
              "Experiment statistic modules lost?");//$NON-NLS-1$
        }
        experimentStatistics = new _ExperimentStatistics(logger, wrappers);
      } else {
        experimentStatistics = null;
      }

      if (((list = configured[_ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET]) != null)
          && ((size = list.size()) > 0)) {
        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          logger.finer("Building hierarchy and wrappers for "//$NON-NLS-1$
              + size + " experiment set modules.");//$NON-NLS-1$
        }
        wrappers = _ModulesBuilder.__buildWrappers(
            list.toArray(new IConfiguredModule[size]), logger);
        configured[_ModulesBuilder.MODULE_TYPE_EXPERIMENT_SET] = null;
        if ((wrappers == null) || (wrappers.length <= 0)) {
          throw new IllegalStateException(
              "Experiment set statistic modules lost?");//$NON-NLS-1$
        }
        experimentSetStatistics = new _ExperimentSetStatistics(logger,
            wrappers);
      } else {
        experimentSetStatistics = null;
      }

      if (((list = configured[_ModulesBuilder.MODULE_TYPE_APPENDIX]) != null)
          && ((size = list.size()) > 0)) {
        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          logger.finer("Building hierarchy and wrappers for "//$NON-NLS-1$
              + size + " appendix modules.");//$NON-NLS-1$
        }
        wrappers = _ModulesBuilder.__buildWrappers(
            list.toArray(new IConfiguredModule[size]), logger);
        configured[_ModulesBuilder.MODULE_TYPE_APPENDIX] = null;
        if ((wrappers == null) || (wrappers.length <= 0)) {
          throw new IllegalStateException("Appendix modules lost?");//$NON-NLS-1$
        }
        appendices = new _Appendices(logger, wrappers);
      } else {
        appendices = null;
      }

      if ((experimentStatistics == null)
          && (experimentSetStatistics == null)) {
        _ModulesBuilder._noStatisticModuleError();
      }
    } catch (final RuntimeException re) {
      ErrorUtils
          .logError(
              logger,
              "Unrecoverable error while building module hierarchy and wrappers.",//$NON-NLS-1$
              re, false, RethrowMode.AS_RUNTIME_EXCEPTION);
      return null;// never reached
    }

    return new _Modules(logger, desc, experimentStatistics,
        experimentSetStatistics, appendices);
  }

  /** the forbidden constructor */
  private _ModulesBuilder() {
    ErrorUtils.doNotCall();
  }
}
