package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;

/** a clustering example */
public final class ClusteringExampleDataset
    implements Comparable<ClusteringExampleDataset> {

  /** the name of the example */
  public final String name;

  /** the matrix */
  public final DoubleMatrix1D data;

  /** the number of anticipated clusters */
  public final int classes;

  /** the goal clusters */
  public final int[] clusters;

  /**
   * create the clustering example
   *
   * @param _name
   *          the example name
   * @param _data
   *          the data
   * @param _classes
   *          the goal clusters
   * @param _clusters
   *          the clusters
   */
  ClusteringExampleDataset(final String _name, final DoubleMatrix1D _data,
      final int _classes, final int[] _clusters) {
    super();
    this.data = _data;
    this.name = _name;
    this.classes = _classes;
    this.clusters = _clusters;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final ClusteringExampleDataset o) {
    return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.name);
  }
}
