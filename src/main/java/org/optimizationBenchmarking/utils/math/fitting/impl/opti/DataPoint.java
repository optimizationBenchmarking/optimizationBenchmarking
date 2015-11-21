package org.optimizationBenchmarking.utils.math.fitting.impl.opti;

import org.optimizationBenchmarking.utils.math.MathUtils;

/** a data point in the fitting process */
public final class DataPoint {
  /** the input value of the function to fit */
  public final double input;
  /** the expected output */
  public final double output;
  /** the weight */
  public final double inverseWeight;

  /** the error */
  double m_error;

  /**
   * create the data point
   *
   * @param in
   *          the input
   * @param out
   *          the output
   */
  DataPoint(final double in, final double out) {
    super();

    this.input = in;
    this.output = out;
    this.inverseWeight = DataPoint.computeInverseWeight(out);
  }

  /**
   * Compute the inverse weight of a data point based on its output value.
   *
   * @param out
   *          the output value
   * @return the inverse weight
   */
  public static final double computeInverseWeight(final double out) {
    final double w;
    if ((out != 0d) && ((w = Math.abs(out)) > 0d)
        && (MathUtils.isFinite(1d / w))) {
      return w;
    }
    return 1d;
  }
}
