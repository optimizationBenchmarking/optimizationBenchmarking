package org.optimizationBenchmarking.experimentation.attributes.clusters.behavior;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.attributes.modeling.DimensionRelationshipData;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleDistanceMatrix1DBuilder;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;

/** the distance matrix builder. */
final class _DistanceBuilder extends DoubleDistanceMatrix1DBuilder {

  /** the data matrix */
  private final DimensionRelationshipData[][][] m_data;

  /** the sum aggregate */
  private final StableSum m_sum;

  /**
   * create the distance builder
   *
   * @param data
   *          the data
   */
  _DistanceBuilder(final DimensionRelationshipData[][][] data) {
    super();
    this.m_data = data;
    this.m_sum = new StableSum();
  }

  /** {@inheritDoc} */
  @Override
  protected final int getElementCount() {
    return this.m_data.length;
  }

  /**
   * Compute the distance between two dimension relationships.
   *
   * @param a
   *          the first one
   * @param b
   *          the second one
   * @return their distance
   */
  private static final double __dist(final DimensionRelationshipData a,
      final DimensionRelationshipData b) {
    double dist, orig;

    dist = a.measure.evaluate(b.fitting.getFittedFunction(),
        b.fitting.getFittedParametersRef());

    if (dist == 0d) {
      return 0d;
    }

    orig = a.fitting.getQuality();
    if (dist <= orig) {
      return 0d;
    }
    dist -= orig;
    if (dist <= 0d) {
      return 0d;
    }

    if (orig <= 0d) {
      orig = Double.MIN_NORMAL;
    }
    return Math.max(0d, (dist / orig));
  }

  /** {@inheritDoc} */
  @Override
  protected final double getDistance(final int i, final int j) {
    final StableSum sum;
    DimensionRelationshipData[] dataB;
    DimensionRelationshipData b;
    int index1, index2;

    sum = this.m_sum;
    sum.reset();
    index1 = (-1);
    for (final DimensionRelationshipData[] dataA : this.m_data[i]) {
      dataB = this.m_data[j][++index1];
      index2 = (-1);
      for (final DimensionRelationshipData a : dataA) {
        b = dataB[++index2];
        sum.append(Math.min(_DistanceBuilder.__dist(a, b),
            _DistanceBuilder.__dist(b, a)));
      }
    }

    return sum.doubleValue();
  }

  // /** {@inheritDoc} */
  // @Override
  // protected final double getDistance(int i, int j) {
  // DimensionRelationshipData[] dataB;
  // DimensionRelationshipData b;
  // double max, current;
  // int index1, index2;
  //
  // max = Double.NEGATIVE_INFINITY;
  // index1 = (-1);
  // for (DimensionRelationshipData[] dataA : this.m_data[i]) {
  // dataB = this.m_data[j][++index1];
  // index2 = (-1);
  // for (DimensionRelationshipData a : dataA) {
  // b = dataB[++index2];
  // current = Math.min(__dist(a, b), __dist(b, a));
  // if (current > max) {
  // max = current;
  // }
  // }
  // }
  //
  // return max;
  // }

  /** {@inheritDoc} */
  @Override
  protected final void releaseElement(final int i) {
    DimensionRelationshipData[][] element;
    int index;

    element = this.m_data[i];
    for (index = element.length; (--index) >= 0;) {
      Arrays.fill(element[index], null);
      element[index] = null;
    }
    this.m_data[i] = null;
  }
}
