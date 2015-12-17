package org.optimizationBenchmarking.experimentation.attributes.clusters.behavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.OnlySharedInstances;
import org.optimizationBenchmarking.experimentation.attributes.modeling.DimensionRelationshipAndData;
import org.optimizationBenchmarking.experimentation.attributes.modeling.DimensionRelationshipData;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleDistanceMatrix1D;
import org.optimizationBenchmarking.utils.ml.clustering.impl.DefaultClusterer;
import org.optimizationBenchmarking.utils.parallel.Execute;

/**
 * Cluster experimental data based on the runtime behavior of algorithms.
 *
 * @param <CT>
 *          the cluster type
 * @param <CCT>
 *          the clustering type
 */
abstract class _BehaviorClusterer<CT extends _BehaviorCluster<CCT>, CCT extends _BehaviorClustering<CT>>
    extends Attribute<IExperimentSet, CCT> {

  /** create the clusterer */
  _BehaviorClusterer() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /**
   * Get the source of the data to be clustered
   *
   * @param data
   *          the data
   * @return the source of the data to be clustered
   */
  abstract INamedElementSet _getElementsToCluster(
      final IExperimentSet data);

  /**
   * Get the categories of the runs
   *
   * @param data
   *          the data
   * @return the categories
   */
  abstract String[] _getRunCategories(final IExperimentSet data);

  /**
   * Get the runs belonging to each element
   *
   * @param data
   *          the data
   * @param element
   *          the element
   * @param categories
   *          the run categories
   * @return the runs
   */
  abstract IInstanceRuns[] _getRunsPerElement(final IExperimentSet data,
      final INamedElement element, final String[] categories);

  /**
   * Get the attributes for the fitting.
   *
   * @param data
   *          the experiment data
   * @return the attributes
   */
  private static final DimensionRelationshipAndData[] __getFittingAttributes(
      final IExperimentSet data) {
    final ArrayListView<? extends IDimension> dims;
    int index, index2;
    ArrayList<DimensionRelationshipAndData> list;
    IDimension dimA, dimB, useDimA;
    boolean dimAIsTime, dimBIsTime;

    dims = data.getDimensions().getData();
    index = dims.size();
    list = new ArrayList<>((index * (index - 1)) >>> 1);
    for (; (--index) > 0;) {
      dimA = dims.get(index);
      dimAIsTime = dimA.getDimensionType().isTimeMeasure();
      for (index2 = index; (--index2) >= 0;) {
        dimB = dims.get(index2);
        dimBIsTime = dimB.getDimensionType().isTimeMeasure();

        if (dimBIsTime != dimAIsTime) {

          if (dimBIsTime) {
            useDimA = dimB;
            dimB = dimA;
          } else {
            useDimA = dimA;
          }

          list.add(new DimensionRelationshipAndData(useDimA, dimB));
        }
      }
    }

    return list.toArray(new DimensionRelationshipAndData[list.size()]);
  }

  /**
   * Create the clustering
   *
   * @param owner
   *          the owning experiment set
   * @param clusters
   *          the discovered clusterings
   * @param source
   *          the source data structure
   * @param names
   *          the named elements used as basic for clustering (might be
   *          less than in {@code source})
   * @return the result
   */
  abstract CCT _create(final IExperimentSet owner, final int[] clusters,
      final INamedElementSet source,
      final ArrayListView<? extends INamedElement> names);

  /**
   * Compute the result.
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the result
   */
  @SuppressWarnings("unchecked")
  private final CCT __compute(final IExperimentSet data,
      final Logger logger) {
    final INamedElementSet names;
    final ArrayListView<? extends INamedElement> elements;
    final int size;
    final String what;
    final CCT result;
    String[] categories;
    IExperimentSet shared;
    DoubleDistanceMatrix1D distances;
    int index;
    Future<DimensionRelationshipData[][]>[] fittingsFutures;
    DimensionRelationshipData[][][] fittings;
    DimensionRelationshipAndData[] attrs;
    int[] clusters;

    shared = OnlySharedInstances.INSTANCE.get(data, logger);
    names = this._getElementsToCluster(shared);
    categories = this._getRunCategories(shared);
    Arrays.sort(categories);
    shared = null;

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      what = ((" the set of " + names.getData().size()) + //$NON-NLS-1$
          ((names instanceof IInstanceSet)//
              ? " instances" //$NON-NLS-1$
              : ((names instanceof IExperimentSet)//
                  ? " experiments" //$NON-NLS-1$
                  : "? i am confused?")));//$NON-NLS-1$
      logger.finer("Beginning to cluster" + what + //$NON-NLS-1$
          " based on algorithm runtime behavior.");//$NON-NLS-1$
      if (logger.isLoggable(Level.FINER)) {
        logger.finer(//
            "First, we model the runtime behavior of each algorithm on each benchmark instance for each runtime/objective dimension pair.");//$NON-NLS-1$
      }
    } else {
      what = null;
    }

    attrs = _BehaviorClusterer.__getFittingAttributes(data);

    elements = names.getData();
    size = elements.size();
    fittingsFutures = new Future[size];
    for (index = size; (--index) >= 0;) {
      fittingsFutures[index] = Execute.parallel(new _ElementFittingsJob(
          this._getRunsPerElement(data, elements.get(index), categories),
          attrs, logger));
    }
    categories = null;
    attrs = null;
    fittings = new DimensionRelationshipData[size][][];
    Execute.join(fittingsFutures, fittings, 0, false);
    fittingsFutures = null;

    if ((logger != null) && (what != null)
        && (logger.isLoggable(Level.FINER))) {
      logger.finer(//
          "Modeling completed, now computing a distance matrix which represents how well models for one instance runs set can represent the data from another one.");//$NON-NLS-1$
    }

    distances = new _DistanceBuilder(fittings).call();
    fittings = null;

    if ((logger != null) && (what != null)
        && logger.isLoggable(Level.FINER)) {
      logger.finer(//
          "Distance matrix computed, now we cluster" + //$NON-NLS-1$
              what + " based on this matrix.");//$NON-NLS-1$
    }

    clusters = DefaultClusterer.getDistanceInstance().use()//
        .setLogger(logger).setDistanceMatrix(distances)//
        .create().call().getClustersRef();
    distances = null;

    if ((logger != null) && (what != null)
        && logger.isLoggable(Level.FINER)) {
      logger.finer(//
          "Clustering completed, now we group" + //$NON-NLS-1$
              what + " based on the discovered clusters.");//$NON-NLS-1$
    }

    result = this._create(data, clusters, this._getElementsToCluster(data),
        elements);
    clusters = null;
    if ((logger != null) && (what != null)
        && (logger.isLoggable(Level.FINE))) {
      logger.finer("Finished clustering" + what + //$NON-NLS-1$
          " based on algorithm performance fingerprints, obtained "//$NON-NLS-1$
          + result.getData().size() + " clusters.");//$NON-NLS-1$
    }

    return result;
  }

  /** {@inheritDoc} */
  @Override
  protected final CCT compute(final IExperimentSet data,
      final Logger logger) {
    final CCT result;
    result = this.__compute(data, logger);
    MemoryUtils.fullGC();
    return result;
  }
}
