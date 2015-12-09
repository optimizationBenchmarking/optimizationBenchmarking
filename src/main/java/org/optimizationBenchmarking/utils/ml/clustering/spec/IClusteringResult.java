package org.optimizationBenchmarking.utils.ml.clustering.spec;

/** The result of a clustering process. */
public interface IClusteringResult {

  /**
   * The quality of the clustering result. Smaller values are better. Only
   * qualities coming from the same clusterer are comparable.
   *
   * @return the clustering quality
   */
  public abstract double getQuality();

  /**
   * Obtain the cluster assignments. If an {@code m*n} matrix was input of
   * the clusterer, each of its {@code m} rows will be assigned to one
   * cluster. Thus, this method returns an {@code int[m]} containing these
   * assignments. The returned array is a reference to the one stored in
   * this record, not a copy. If you modify it, this object will be changed
   * too - handle with care.
   *
   * @return the reference to the clustering results.
   */
  public abstract int[] getClustersRef();
}
