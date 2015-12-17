package org.optimizationBenchmarking.utils.ml.clustering.impl.dist;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleDistanceMatrix1DBuilder;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceMeasure;

/**
 * A builder for symmetric distance matrices based on a distance measure.
 */
public final class MeasureBasedDistanceMatrixBuilder
    extends DoubleDistanceMatrix1DBuilder {

  /** the data matrix */
  private final IMatrix m_matrix;

  /** the measure */
  private final IDistanceMeasure m_measure;

  /**
   * Create the measure-based distance matrix builder
   *
   * @param matrix
   *          the matrix
   * @param measure
   *          the measure
   */
  public MeasureBasedDistanceMatrixBuilder(final IMatrix matrix,
      final IDistanceMeasure measure) {
    super();
    this.m_matrix = matrix;
    this.m_measure = measure;
  }

  /** {@inheritDoc} */
  @Override
  protected final int getElementCount() {
    return this.m_matrix.m();
  }

  /** {@inheritDoc} */
  @Override
  protected final double getDistance(final int i, final int j) {
    return this.m_measure.compute(this.m_matrix, i, this.m_matrix, j);
  }
}
