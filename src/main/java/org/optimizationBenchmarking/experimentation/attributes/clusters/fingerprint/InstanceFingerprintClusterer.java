package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A clusterer for clustering via instance fingerprints.
 */
public final class InstanceFingerprintClusterer extends
    _FingerprintClusterer<InstanceFingerprintCluster, InstanceFingerprintClustering> {

  /**
   * indicate that clustering should be performed based on instance
   * performance
   */
  public static final String CHOICE_INSTANCES_BY_PERFORMANCE = "instances by experimental results"; //$NON-NLS-1$

  /** create the instance finger print */
  public static final InstanceFingerprintClusterer INSTANCE = new InstanceFingerprintClusterer();

  /** create the instance fingerprint clusterer */
  private InstanceFingerprintClusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final InstanceFingerprintClustering _create(final IExperimentSet data,
      final int[] clustering, final INamedElementSet source,
      final ArrayListView<? extends INamedElement> names) {
    return new InstanceFingerprintClustering(data, clustering, source,
        names);
  }

  /** {@inheritDoc} */
  @Override
  final INamedElementSet _getClusterSource(final IExperimentSet data) {
    return data.getInstances();
  }

  /** {@inhertitDoc} */
  @Override
  public final String toString() {
    return "by instances according to algorithm performance"; //$NON-NLS-1$
  }
}
