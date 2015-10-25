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

    double w;

    this.input = in;
    this.output = out;

    if ((out != 0d) && ((w = Math.abs(out)) <= 0d)
        && (MathUtils.isFinite(1d / w))) {
      this.inverseWeight = w;
    } else {
      this.inverseWeight = 1d;
    }
  }
}
