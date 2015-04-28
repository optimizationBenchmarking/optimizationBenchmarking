package org.optimizationBenchmarking.experimentation.attributes.clusters;

import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A clustering is an element set containing clusters.
 */
public interface IClustering extends IElementSet {

  /**
   * Obtain the clusters
   * 
   * @return the list of clusters
   */
  @Override
  public abstract ArrayListView<? extends ICluster> getData();

  /**
   * Obtain a suggestion for the path component of figures drawn based on
   * this clustering
   * 
   * @return a suggestion for the path component of figures drawn based on
   *         this clustering
   */
  public abstract String getPathComponentSuggestion();
}
