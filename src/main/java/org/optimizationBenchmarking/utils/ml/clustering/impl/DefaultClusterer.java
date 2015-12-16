package org.optimizationBenchmarking.utils.ml.clustering.impl;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased.RBasedDataClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased.RBasedDistanceClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceClusterer;

/** The default clusterers. */
public final class DefaultClusterer {

  /** the forbidden constructor */
  private DefaultClusterer() {
    ErrorUtils.doNotCall();
  }

  /** No useable data clusterer was found. */
  private static final void __noDataInstance() {
    throw new IllegalStateException("No useable data clusterer detected."); //$NON-NLS-1$
  }

  /**
   * Get the default data clusterer
   *
   * @return the default data clusterer
   */
  public static final IDataClusterer getDataInstance() {
    final IDataClusterer inst;
    inst = __DefaultDataClusterer.INSTANCE;
    if (inst == null) {
      DefaultClusterer.__noDataInstance();
    }
    return inst;
  }

  /**
   * Get all available data clusterers
   *
   * @return all available data clusterers
   */
  public static final ArrayListView<IDataClusterer> getAllDataInstances() {
    final ArrayListView<IDataClusterer> insts;
    insts = __AllDataClusterers.INSTANCES;
    if (insts == null) {
      DefaultClusterer.__noDataInstance();
    }
    return insts;
  }

  /** No useable distance clusterer was found. */
  private static final void __noDistanceInstance() {
    throw new IllegalStateException(
        "No useable distance clusterer detected."); //$NON-NLS-1$
  }

  /**
   * Get the default distance clusterer
   *
   * @return the default distance clusterer
   */
  public static final IDistanceClusterer getDistanceInstance() {
    final IDistanceClusterer inst;
    inst = __DefaultDistanceClusterer.INSTANCE;
    if (inst == null) {
      DefaultClusterer.__noDistanceInstance();
    }
    return inst;
  }

  /**
   * Get all available distance clusterers
   *
   * @return all available distance clusterers
   */
  public static final ArrayListView<IDistanceClusterer> getAllDistanceInstances() {
    final ArrayListView<IDistanceClusterer> insts;
    insts = __AllDistanceClusterers.INSTANCES;
    if (insts == null) {
      DefaultClusterer.__noDistanceInstance();
    }
    return insts;
  }

  /** the default data clusterer */
  private static final class __DefaultDataClusterer {
    /** the instance */
    static final IDataClusterer INSTANCE;

    static {
      IDataClusterer inst;
      inst = RBasedDistanceClusterer.getInstance();
      if (inst.canUse()) {
        INSTANCE = inst;
      } else {
        inst = RBasedDataClusterer.getInstance();
        if (inst.canUse()) {
          INSTANCE = inst;
        } else {
          INSTANCE = null;
        }
      }
    }
  }

  /** the all data clusterer */
  private static final class __AllDataClusterers {
    /** all data clusterer instnaces */
    static final ArrayListView<IDataClusterer> INSTANCES;

    static {
      LinkedHashSet<IDataClusterer> insts;
      IDataClusterer inst;

      inst = __DefaultDataClusterer.INSTANCE;
      if (inst != null) {
        insts = new LinkedHashSet<>();
        insts.add(inst);
        inst = RBasedDistanceClusterer.getInstance();
        if (inst.canUse()) {
          insts.add(inst);
        }
        inst = RBasedDataClusterer.getInstance();
        if (inst.canUse()) {
          insts.add(inst);
        }
        INSTANCES = ArrayListView.collectionToView(insts);
      } else {
        INSTANCES = null;
      }
    }
  }

  /** the default distance clusterer */
  private static final class __DefaultDistanceClusterer {
    /** the instance */
    static final IDistanceClusterer INSTANCE;

    static {
      IDistanceClusterer inst;
      inst = RBasedDistanceClusterer.getInstance();
      if (inst.canUse()) {
        INSTANCE = inst;
      } else {
        INSTANCE = null;
      }
    }
  }

  /** the all distance clusterer */
  private static final class __AllDistanceClusterers {
    /** all distance clusterer instnaces */
    static final ArrayListView<IDistanceClusterer> INSTANCES;

    static {
      LinkedHashSet<IDistanceClusterer> insts;
      IDistanceClusterer inst;

      inst = __DefaultDistanceClusterer.INSTANCE;
      if (inst != null) {
        insts = new LinkedHashSet<>();
        insts.add(inst);
        inst = RBasedDistanceClusterer.getInstance();
        if (inst.canUse()) {
          insts.add(inst);
        }
        INSTANCES = ArrayListView.collectionToView(insts);
      } else {
        INSTANCES = null;
      }
    }
  }

}
