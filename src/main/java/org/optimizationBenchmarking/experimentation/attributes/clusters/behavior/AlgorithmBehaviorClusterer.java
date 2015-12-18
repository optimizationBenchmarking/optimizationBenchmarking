package org.optimizationBenchmarking.experimentation.attributes.clusters.behavior;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A clusterer for clustering via algorithm behavior.
 */
public final class AlgorithmBehaviorClusterer extends
    _BehaviorClusterer<AlgorithmBehaviorCluster, AlgorithmBehaviorClustering> {

  /**
   * indicate that clustering should be performed by algorithm, based on
   * their runtime behavior
   */
  public static final String CHOICE_INSTANCES_BY_ALGORITHM_BEHAVIOR = "algorithms by behavior"; //$NON-NLS-1$

  /** the algorithm behavior clusterer instance */
  public static final AlgorithmBehaviorClusterer INSTANCE = new AlgorithmBehaviorClusterer();

  /** create the instance behavior clusterer */
  private AlgorithmBehaviorClusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final AlgorithmBehaviorClustering _create(final IExperimentSet data,
      final int[] clustering, final INamedElementSet source,
      final ArrayListView<? extends INamedElement> names) {
    return new AlgorithmBehaviorClustering(data, clustering, source,
        names);
  }

  /** {@inheritDoc} */
  @Override
  final INamedElementSet _getElementsToCluster(final IExperimentSet data) {
    return data;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "by algorithm performance"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final IInstanceRuns[] _getRunsPerElement(final IExperimentSet data,
      final INamedElement element, final String[] categories) {
    final ArrayListView<? extends IInstanceRuns> list;
    final IInstanceRuns[] runs;
    IInstanceRuns run;
    int index;

    list = ((IExperiment) element).getData();
    index = categories.length;
    runs = new IInstanceRuns[index];

    for (; (--index) >= 0;) {
      run = list.get(index);
      runs[Arrays.binarySearch(categories,
          run.getInstance().getName())] = run;
    }

    return runs;
  }

  /** {@inheritDoc} */
  @Override
  final String[] _getRunCategories(final IExperimentSet data) {
    final ArrayListView<? extends IInstance> instances;
    final String[] strings;
    int index;

    instances = data.getInstances().getData();
    index = instances.size();
    strings = new String[index];
    for (; (--index) >= 0;) {
      strings[index] = instances.get(index).getName();
    }
    return strings;
  }
}
