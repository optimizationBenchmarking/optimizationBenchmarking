package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.OnlySharedInstances;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased.RBasedClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;

/**
 * Cluster experimental data based on performance fingerprints. It can
 * group either experiments by their performance or instances by the
 * performance of the algorithms on them. This clusterer proceeds as
 * follows:
 * <ul>
 * <li>First, we compute the
 * {@link org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint.Fingerprint}
 * s of the elements to be clustered.</li>
 * </ul>
 *
 * @param <CT>
 *          the cluster type
 * @param <CCT>
 *          the clustering type
 */
abstract class _FingerprintClusterer<CT extends _FingerprintCluster<CCT>, CCT extends _FingerprintClustering<CT>>
    extends Attribute<IExperimentSet, CCT> {

  /** create the clusterer */
  _FingerprintClusterer() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /**
   * Get the source of the data to be clustered
   *
   * @param data
   *          the data
   * @return the source of the data to be clustered
   */
  abstract INamedElementSet _getClusterSource(final IExperimentSet data);

  /**
   * Create the clustering
   *
   * @param data
   *          the data
   * @param clustering
   *          the clustering decisions
   * @param source
   *          the source set of named elements to pick from
   * @param names
   *          the element names
   * @return the clustering
   */
  abstract CCT _create(final IExperimentSet data, final int[] clustering,
      final INamedElementSet source,
      final ArrayListView<? extends INamedElement> names);

  /** {@inheritDoc} */
  @Override
  protected final CCT compute(final IExperimentSet data,
      final Logger logger) {
    final INamedElementSet names;
    final CCT result;
    IClusteringJob job;
    String what;
    IMatrix matrix;
    int[] res;

    names = this._getClusterSource(OnlySharedInstances.INSTANCE.get(//
        data, logger));

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      what = ((" the set of " + names.getData().size()) + //$NON-NLS-1$
          ((names instanceof IInstanceSet)//
              ? " instances" //$NON-NLS-1$
              : ((names instanceof IExperimentSet)//
                  ? " experiments" //$NON-NLS-1$
                  : "?i am confused?")));//$NON-NLS-1$
      logger.finer("Beginning to cluster" + what + //$NON-NLS-1$
          " based on algorithm performance fingerprints.");//$NON-NLS-1$
    } else {
      what = null;
    }

    matrix = Fingerprint.INSTANCE.get(names, logger);
    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest("Now launching R to cluster the obtained "//$NON-NLS-1$
          + matrix.m() + '*' + matrix.n() + " fingerprint matrix.");//$NON-NLS-1$
    }

    job = __Engine.ENGINE.use().setLogger(logger).setData(matrix).create();
    matrix = null;
    res = job.call().getClustersRef();
    job = null;

    result = this._create(data, res, this._getClusterSource(data),
        names.getData());

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      if (what == null) {
        what = ((" the set of " + names.getData().size()) + //$NON-NLS-1$
            ((names instanceof IInstanceSet)//
                ? " instances" //$NON-NLS-1$
                : ((names instanceof IExperimentSet)//
                    ? " experiments" //$NON-NLS-1$
                    : "?i am confused?")));//$NON-NLS-1$
      }
      logger.finer("Finished clustering" + what + //$NON-NLS-1$
          " based on algorithm performance fingerprints, obtained "//$NON-NLS-1$
          + result.getData().size() + " clusters.");//$NON-NLS-1$
    }

    return result;
  }

  /** the clustering engine to use */
  private static final class __Engine {
    /** the clustering engine */
    static final IClusterer ENGINE;

    static {
      ENGINE = RBasedClusterer.getInstance();
    }
  }
}
