package org.optimizationBenchmarking.utils.ml.clustering.impl.dist;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceMeasure;

/** The euclidean distance measure. */
public final class EuclideanDistance implements IDistanceMeasure {

  /** the sum to use for distance computations */
  private final StableSum m_sum;

  /** create */
  public EuclideanDistance() {
    super();
    this.m_sum = new StableSum();
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final IMatrix matrixA, final int rowA,
      final IMatrix matrixB, final int rowB) {
    final StableSum sum;
    int i;
    double d;

    sum = this.m_sum;
    sum.reset();
    for (i = matrixA.n(); (--i) >= 0;) {
      d = (matrixA.getDouble(rowA, i) - matrixB.getDouble(rowB, i));
      sum.append(d * d);
    }
    return Math.sqrt(sum.doubleValue());
  }
}
