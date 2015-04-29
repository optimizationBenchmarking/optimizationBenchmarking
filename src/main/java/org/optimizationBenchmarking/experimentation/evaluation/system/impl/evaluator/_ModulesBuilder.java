package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;

/** In this class, we group the code for building modules */
final class _ModulesBuilder {

  /** the module types */
  static final String[] MODULE_TYPES;

  static {
    MODULE_TYPES = new String[_EvaluationSetup.TYPE_COUNT];
    _ModulesBuilder.MODULE_TYPES[_EvaluationSetup.TYPE_DESCRIPTION] = "description"; //$NON-NLS-1$
    _ModulesBuilder.MODULE_TYPES[_EvaluationSetup.TYPE_EXPERIMENT] = "experiment"; //$NON-NLS-1$
    _ModulesBuilder.MODULE_TYPES[_EvaluationSetup.TYPE_EXPERIMENT_SET] = "experiment set"; //$NON-NLS-1$
    _ModulesBuilder.MODULE_TYPES[_EvaluationSetup.TYPE_APPENDIX] = "appendix"; //$NON-NLS-1$
  }

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
  static final _MainJob _buildModules(final _EvaluationSetup setup,
      final IExperimentSet data, final Logger logger) {
    final ArrayList<_ModuleEntry>[] allModules;
    final _DescriptionJobs descriptionJobs;
    final _ExperimentJobs experimentJobs;
    final _ExperimentSetJobs experimentSetJobs;
    final _AppendixJobs appendixJobs;
    ArrayList<_ModuleEntry> list, hierarchy;
    int[][][] orderAndContainment;
    int i, size, after;
    boolean hasModules;

    // Obtain the (four) lists of modules.
    allModules = setup._takeModules();

    // Check whether the lists are OK.
    if (allModules == null) {
      throw new IllegalArgumentException(//
          "Module list array cannot be null.");//$NON-NLS-1$
    }
    if (allModules.length != _EvaluationSetup.TYPE_COUNT) {
      throw new IllegalArgumentException(//
          "Module list array length is " + //$NON-NLS-1$
              allModules.length + " but should be "//$NON-NLS-1$
              + _EvaluationSetup.TYPE_COUNT);
    }

    // Check whether we have at least one module.
    hasModules = false;
    for (i = _EvaluationSetup.TYPE_COUNT; (--i) >= 0;) {
      list = allModules[i];
      if (list == null) {
        throw new IllegalArgumentException(
            "List of " + _ModulesBuilder.MODULE_TYPES[i] + //$NON-NLS-1$
                " is null.");//$NON-NLS-1$
      }
      if (list.isEmpty()) {
        allModules[i] = null;
      } else {
        hasModules = true;
      }
    }
    if (!(hasModules)) {
      throw new IllegalStateException(//
          "The list of evaluation modules is empty."); //$NON-NLS-1$
    }

    // For each of the four lists, we now build the module hierarchy.
    for (i = 0; i < _EvaluationSetup.TYPE_COUNT; i++) {
      list = allModules[i];
      if (list == null) {
        // list == null -> list is empty, skip
        continue;
      }

      // Obtain the order and containment hierarchies.
      orderAndContainment = _ModulesBuilder.__computeRelations(list);

      // Build the hierarchy of module entries.
      size = list.size();
      allModules[i] = hierarchy = new ArrayList<>(size);
      _ModulesBuilder.__buildHierarchy(logger, list, hierarchy, null,
          orderAndContainment[0], orderAndContainment[1],
          new boolean[size]);
      orderAndContainment = null;
      list = null;

      // Check if we lost a module? (should never happen...)
      after = _ModuleEntry._hierarchySize(hierarchy);

      if (after != size) {
        throw new IllegalStateException(((((//
            size + //
            " modules are expected in the hierarchy of ")//$NON-NLS-1$
            + _ModulesBuilder.MODULE_TYPES[i]) + //
            " modules but ") //$NON-NLS-1$
            + after)
            + //
            " were found."); //$NON-NLS-1$
      }
    }

    // now let's assemble the _MainJob

    try {
      descriptionJobs = _ModuleEntry._makeDescriptionJobs(allModules[0],
          data, logger);
    } catch (final Throwable error) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while assembling description jobs.", //$NON-NLS-1$
          false, error);
      return null;
    }

    try {
      experimentJobs = _ModuleEntry._makeExperimentJobs(allModules[1],
          data, logger);
    } catch (final Throwable error) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while assembling experiment jobs.", //$NON-NLS-1$
          false, error);
      return null;
    }

    try {
      experimentSetJobs = _ModuleEntry._makeExperimentSetJobs(
          allModules[2], data, logger);
    } catch (final Throwable error) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while assembling experiment set jobs.", //$NON-NLS-1$
          false, error);
      return null;
    }

    try {
      appendixJobs = _ModuleEntry._makeAppendixJobs(allModules[3], data,
          logger);
    } catch (final Throwable error) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while assembling appendix jobs.", //$NON-NLS-1$
          false, error);
      return null;
    }
    return new _MainJob(logger, descriptionJobs, experimentJobs,
        experimentSetJobs, appendixJobs);

  }

  /**
   * Build the containment and order hierarchy of a list of modules
   * 
   * @param entries
   *          the module entries
   * @return the order and containment hierarchies
   */
  @SuppressWarnings("incomplete-switch")
  private static final int[][][] __computeRelations(
      final ArrayList<_ModuleEntry> entries) {
    final int length;
    final int[][] containment, order;
    _ModuleEntry a, b;
    int i, j;

    if ((entries == null) || ((length = entries.size()) <= 0)) {
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
      a = entries.get(i);
      for (j = length; (--j) >= 0;) {
        if (i == j) {
          continue;
        }
        b = entries.get(j);
        switch (a.m_module.getRelationship(b.m_module)) {
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

    // we now have loaded all hard-coded order and containment
    // hierarchies
    _ModulesBuilder.__computeTransitiveRelations(order, entries, true);
    _ModulesBuilder.__computeTransitiveRelations(containment, entries,
        false);

    return new int[][][] { order, containment };
  }

  /**
   * create the hierarchy of modules
   * 
   * @param logger
   *          the logger
   * @param modules
   *          the modules
   * @param dest
   *          the destination
   * @param containedIn
   *          the index of the owning element, or {@code -1} for none
   * @param containment
   *          the containment
   * @param order
   *          the order
   * @param done
   *          the done wrappers
   */
  private static final void __buildHierarchy(final Logger logger,
      final ArrayList<_ModuleEntry> modules,
      final ArrayList<_ModuleEntry> dest, final _ModuleEntry containedIn,
      final int[][] order, final int[][] containment, final boolean[] done) {
    final int length;
    ArrayList<_ModuleEntry> useDest;
    _ModuleEntry current;
    int index, currentID, otherID, destIndex;

    length = containment.length;
    find: for (index = 0; index < modules.size();) {
      current = modules.get(index);
      currentID = current.m_id;

      if (done[currentID]) {
        throw new IllegalStateException(//
            "Module cannot be in module list anymore, but is."); //$NON-NLS-1$
      }

      // check if the module is contained inside the expected module
      if (containedIn != null) {
        if ((current == containedIn) || (currentID == containedIn.m_id)) {
          throw new IllegalStateException(//
              "Module cannot be in module list anymore, but is."); //$NON-NLS-1$
        }
        if (currentID > containedIn.m_id) {
          if (containment[currentID][containedIn.m_id] != 1) {
            index++;
            continue find;
          }
        } else {
          if (containment[containedIn.m_id][currentID] != (-1)) {
            index++;
            continue find;
          }
        }
      }

      // now check if we are in a deeper hierarchical nesting: is the
      // current module contained in another, not-yet-processed one?
      for (otherID = currentID; (--otherID) >= 0;) {
        if (containment[currentID][otherID] == 1) {
          if (!done[otherID]) {
            index++;
            continue find;
          }
        }
      }

      for (otherID = currentID; (++otherID) < length;) {
        if (containment[otherID][currentID] == (-1)) {
          if (!done[otherID]) {
            index++;
            continue find;
          }
        }
      }

      // If we get here, then the module is contained in the other module
      // (or is another root module) and can be added to the destination
      // list (after removing it from the source list).
      done[currentID] = true;
      modules.remove(index);

      // Get the destination list.
      if (containedIn == null) {
        useDest = dest; // use original destination array
      } else {
        useDest = containedIn.m_children;
        if (useDest == null) {
          // create new destination array if necessary
          containedIn.m_children = useDest = new ArrayList<>();
        }
      }

      // Find the right index: The new element is added behind all
      // smaller-or-equal elements in the destination list.
      findIndex: for (destIndex = useDest.size(); (--destIndex) >= 0;) {
        otherID = dest.get(destIndex).m_id;
        if (otherID == currentID) {
          throw new IllegalStateException("Two modules with same ID?"); //$NON-NLS-1$
        }
        if (currentID > otherID) {
          if (order[currentID][otherID] >= 0) {
            break findIndex;
          }
        } else {
          if (order[otherID][currentID] <= 0) {
            break findIndex;
          }
        }
      }

      useDest.add((destIndex + 1), current);

      // Recurse: Find all elements to be inserted into the current module
      // record.
      _ModulesBuilder.__buildHierarchy(logger, modules, null, current,
          order, containment, done);
    }
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
      final int[][] relations, final ArrayList<_ModuleEntry> modules,
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
                    _ModulesBuilder.__orderError(modules.get(i),
                        modules.get(k));
                  } else {
                    _ModulesBuilder.__containmentError(modules.get(i),
                        modules.get(k));
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
  private static final void __orderError(final _ModuleEntry a,
      final _ModuleEntry b) {
    throw new IllegalStateException(((((//
        "The execution order of the modules is inconsistent: Module '" //$NON-NLS-1$
        + a.m_module) + //
        "' comes both before and after module '") //$NON-NLS-1$
        + b.m_module) + '\'') + '.');
  }

  /**
   * A containment error has occurred
   * 
   * @param a
   *          the first module
   * @param b
   *          the second module
   */
  private static final void __containmentError(final _ModuleEntry a,
      final _ModuleEntry b) {
    throw new IllegalStateException(((((//
        "The execution order of the modules is inconsistent: Module '" //$NON-NLS-1$
        + a.m_module) + //
        "' both contains and is contained in module '") //$NON-NLS-1$
        + b.m_module) + '\'') + '.');
  }

  /** the forbidden constructor */
  private _ModulesBuilder() {
    ErrorUtils.doNotCall();
  }
}
