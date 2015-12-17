package org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.concurrent.Callable;

/** A builder for a distance matrix. */
public abstract class DoubleDistanceMatrix1DBuilder
    implements Callable<DoubleDistanceMatrix1D> {

  /** create */
  protected DoubleDistanceMatrix1DBuilder() {
    super();
  }

  /**
   * Get the number of data elements
   *
   * @return the number of data elements
   */
  protected abstract int getElementCount();

  /**
   * Get the distance between the element at index {@code i} and the
   * element at index {@code j}. Notice that this must be the same as the
   * distance between the elements at indices {@code j} and {@code i}.
   *
   * @param i
   *          the element at index {@code i}
   * @param j
   *          the element at index {@code j}
   * @return their distance
   */
  protected abstract double getDistance(final int i, final int j);

  /**
   * The element at index {@code i} is no longer needed and may be
   * released, nulled, or disposed.
   *
   * @param i
   *          the element index
   */
  protected void releaseElement(final int i) {
    // does nothing by default
  }

  /** {@inheritDoc} */
  @Override
  public final DoubleDistanceMatrix1D call() {
    final int m;
    final double[] data;
    double ij;
    int i, j, k;

    m = this.getElementCount();
    data = new double[(m * (m - 1)) >>> 1];

    k = (-1);
    for (i = 0; i < m; i++) {
      for (j = (i + 1); j < m; j++) {
        ij = this.getDistance(i, j);
        data[++k] = ((ij == 0d) ? 0d : ij);
      }
      this.releaseElement(i);
    }

    this.normalize(data);
    return new DoubleDistanceMatrix1D(data, m);
  }

  /**
   * Normalize the distance data, if necessary
   *
   * @param distances
   *          the distances
   */
  protected void normalize(final double[] distances) {
    // do nothing
  }
}
