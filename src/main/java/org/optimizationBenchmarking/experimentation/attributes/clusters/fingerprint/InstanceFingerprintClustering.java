package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * The performance fingerprint clustering contains a division of
 * experiments or instances according to their performance fingerprints.
 */
public final class InstanceFingerprintClustering
    extends _FingerprintClustering<InstanceFingerprintCluster> {

  /**
   * create the performance fingerprint clustering
   *
   * @param owner
   *          the owner
   * @param clusters
   *          the matrix of clusters
   * @param source
   *          the source where to draw the named elements from
   * @param names
   *          the names
   */
  InstanceFingerprintClustering(final IExperimentSet owner,
      final int[] clusters, final INamedElementSet source,
      final ArrayListView<? extends INamedElement> names) {
    super(owner, clusters, source, names);
  }

  /** {@inheritDoc} */
  @Override
  final InstanceFingerprintCluster _create(final String name,
      final DataSelection selection) {
    return new InstanceFingerprintCluster(this, name, selection);
  }
}
