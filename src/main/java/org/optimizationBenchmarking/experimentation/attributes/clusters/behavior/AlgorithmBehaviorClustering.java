package org.optimizationBenchmarking.experimentation.attributes.clusters.behavior;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * The algorithm behavior clustering contains a division of experiments or
 * instances according to the runtime behavior of the algorithms on them.
 */
public final class AlgorithmBehaviorClustering
    extends _BehaviorClustering<AlgorithmBehaviorCluster> {

  /**
   * create the algorithm behavior clustering
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
  AlgorithmBehaviorClustering(final IExperimentSet owner,
      final int[] clusters, final INamedElementSet source,
      final ArrayListView<? extends INamedElement> names) {
    super(owner, clusters, source, names);
  }

  /** {@inheritDoc} */
  @Override
  final AlgorithmBehaviorCluster _create(final String name,
      final DataSelection selection) {
    return new AlgorithmBehaviorCluster(this, name, selection);
  }
}
