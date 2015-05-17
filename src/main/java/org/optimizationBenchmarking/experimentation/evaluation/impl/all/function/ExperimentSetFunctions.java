package org.optimizationBenchmarking.experimentation.evaluation.impl.all.function;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** The functions for a given experiment set */
public final class ExperimentSetFunctions {
  /** the experiment set (or ICluster) */
  private final IExperimentSet m_set;
  /**
   * the corresponding cluster, or {@code null} if the experiment set is
   * not a cluster
   */
  private final ICluster m_cluster;
  /** the functions */
  private final ArrayListView<ExperimentFunction> m_functions;

  /**
   * create the experiment set functions
   *
   * @param set
   *          the experiment set
   * @param cluster
   *          the corresponding cluster, or {@code null} if the experiment
   *          set is not a cluster
   * @param functions
   *          the functions
   */
  ExperimentSetFunctions(final IExperimentSet set, final ICluster cluster,
      final ExperimentFunction[] functions) {
    super();
    this.m_set = set;
    this.m_cluster = cluster;
    this.m_functions = new ArrayListView<>(functions);
  }

  /**
   * Obtain the experiment set
   *
   * @return the experiment set
   */
  public final IExperimentSet getExperimentSet() {
    return this.m_set;
  }

  /**
   * In case that the {@link #getExperimentSet() experiment set}
   * corresponds to a cluster, obtain the cluster &ndash; otherwise, return
   * {@code null}
   *
   * @return the cluster corresponding to the experiment set, or
   *         {@code null} if it does not correspond to a cluster
   */
  public final ICluster getCluster() {
    return this.m_cluster;
  }

  /**
   * Get the experiment functions. The returned list is guaranteed to
   * contain at least one element. Furthermore, the functions of the
   * elements in the returned list have at least two different {@code x}
   * and two different {@code y} coordinates.
   *
   * @return the experiment functions
   */
  public final ArrayListView<ExperimentFunction> getData() {
    return this.m_functions;
  }
}
