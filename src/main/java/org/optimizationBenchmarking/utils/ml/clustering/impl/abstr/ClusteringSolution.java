package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;
import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** This record represents a solution to a clustering problem. */
public class ClusteringSolution extends Textable
    implements IClusteringResult, Comparable<ClusteringSolution> {

  /** the assignment of data rows to clusters */
  public final int[] assignment;

  /** the clustering quality */
  public double quality;

  /**
   * Create the solution
   *
   * @param m
   *          the number of rows
   */
  public ClusteringSolution(final int m) {
    super();
    this.assignment = new int[m];
    this.quality = Double.POSITIVE_INFINITY;
  }

  /**
   * Create the solution
   *
   * @param _assignment
   *          the assignment
   * @param _quality
   *          the quality
   */
  public ClusteringSolution(final int[] _assignment,
      final double _quality) {
    super();
    this.assignment = _assignment;
    this.quality = _quality;
  }

  /** {@inheritDoc} */
  @Override
  public final double getQuality() {
    return this.quality;
  }

  /** {@inheritDoc} */
  @Override
  public final int[] getClustersRef() {
    return this.assignment;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final ClusteringSolution o) {
    int res, index;

    res = EComparison.compareDoubles(this.quality, o.quality);
    if (res != 0) {
      return res;
    }

    index = (-1);
    for (final int a : this.assignment) {
      res = o.assignment[++index];
      if (res < a) {
        return 1;
      }
      if (res > a) {
        return (-1);
      }
    }
    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    char x;
    textOut.append(this.quality);
    textOut.append(':');
    x = '[';
    for (final int d : this.assignment) {
      textOut.append(x);
      textOut.append(d);
      x = ',';
    }
    textOut.append(']');
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final ClusteringSolution s;
    if (o instanceof ClusteringSolution) {
      s = ((ClusteringSolution) o);
      return ((EComparison.EQUAL.compare(this.quality, s.quality))
          && (Arrays.equals(this.assignment, s.assignment)));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(//
        Arrays.hashCode(this.assignment), //
        HashUtils.hashCode(this.quality));
  }
}
