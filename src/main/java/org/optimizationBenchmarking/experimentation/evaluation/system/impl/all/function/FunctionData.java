package org.optimizationBenchmarking.experimentation.evaluation.system.impl.all.function;

import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** The overall data of the function job */
public final class FunctionData {
  /** the clustering, or {@code null} if no clustering is defined */
  private final IClustering m_clustering;
  /** the functions */
  private final ArrayListView<ExperimentSetFunctions> m_functions;

  /**
   * Create the data
   * 
   * @param clustering
   *          the clustering
   * @param functions
   *          the functions
   */
  FunctionData(final IClustering clustering,
      final ExperimentSetFunctions[] functions) {
    super();
    this.m_clustering = clustering;
    this.m_functions = new ArrayListView<>(functions);
  }

  /**
   * Obtain the clustering, if any was defined, or {@code null} otherwise
   * 
   * @return the clustering, if any was defined, or {@code null} otherwise
   */
  public final IClustering getClustering() {
    return this.m_clustering;
  }

  /**
   * Obtain the experiment set functions. The returned list is guaranteed
   * to contain at least one element.
   * 
   * @return the data
   */
  public final ArrayListView<ExperimentSetFunctions> getData() {
    return this.m_functions;
  }
}
