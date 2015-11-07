package org.optimizationBenchmarking.utils.math;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/** Some mathematical utilities */
public final class MathUtils {

  /**
   * check whether a number is finite
   *
   * @param d
   *          the number
   * @return {@code true} if it is finite, {@code false} otherwise
   */
  public static final boolean isFinite(final double d) {
    return ((d > Double.NEGATIVE_INFINITY) && //
        (d < Double.POSITIVE_INFINITY) && //
        (d == d));
  }

  /**
   * The number of unique {@code double} values between {@code a} and
   * {@code b}.
   * 
   * @param a
   *          the first {@code double}
   * @param b
   *          the second {@code double}
   * @return the steps between them, or {@code -1} if either value is
   *         {@link Double#NaN} or both are infinities of different signs
   */
  public static final long difference(final double a, final double b) {
    final long bitsA;
    double useA, useB, temp;

    if ((a != a) || (b != b)) { // take are of NaN
      return -1L;
    }
    useA = (a + 0d);
    useB = (b + 0d);
    if (useA > useB) {
      temp = useB;
      useB = useA;
      useA = temp;
    }
    if (useA == useB) {
      return 0L;
    }
    if (useA <= Double.NEGATIVE_INFINITY) {
      return -1L;
    }
    if (useB >= Double.POSITIVE_INFINITY) {
      return -1L;
    }

    if (useA < 0d) {
      bitsA = Double.doubleToRawLongBits(-useA);
      if (useB < 0d) {
        return (bitsA - Double.doubleToRawLongBits(-useB));
      }
      return (bitsA + Double.doubleToRawLongBits(useB));
    }
    return (Double.doubleToRawLongBits(useB)
        - Double.doubleToRawLongBits(useA));
  }

  /** the forbidden constructor */
  private MathUtils() {
    ErrorUtils.doNotCall();
  }
}
