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
   * Check whether two double numbers are not more than {@code steps}
   * smallest increases or decreases away from each other
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @param steps
   *          the number of permitted steps
   * @return {@code true} if the numbers are within that range of each
   *         other, {@code false} otherwise
   */
  public static final boolean isWithinSteps(final double a,
      final double b, final int steps) {
    double lowerA, upperA, lowerB, upperB;
    int upA, lowA, upB, lowB;

    if (a != a) {
      return (b != b);
    }
    if (b != b) {
      return false;
    }

    lowerA = upperA = a;
    lowerB = upperB = b;

    upA = lowA = steps;
    upB = lowB = 0;

    increaseA: while ((upA > 0) && (upperA > Double.NEGATIVE_INFINITY)
        && (upperA < Double.POSITIVE_INFINITY)) {
      upperA = Math.nextUp(upperA);
      upA--;
      if ((upperA >= Double.POSITIVE_INFINITY) || (upperA != upperA)) {
        break increaseA;
      }
    }
    lowB += upA;

    decreaseA: while ((lowA > 0) && (lowerA > Double.NEGATIVE_INFINITY)
        && (lowerA < Double.POSITIVE_INFINITY)) {
      lowerA = Math.nextAfter(lowerA, Double.NEGATIVE_INFINITY);
      lowA--;
      if ((lowerA <= Double.NEGATIVE_INFINITY) || (lowerA != lowerA)) {
        break decreaseA;
      }
    }
    upB += lowA;

    increaseB: while ((upB > 0) && (upperB > Double.NEGATIVE_INFINITY)
        && (upperB < Double.POSITIVE_INFINITY)) {
      upperB = Math.nextUp(upperB);
      upB--;
      if ((upperB >= Double.POSITIVE_INFINITY) || (upperB != upperB)) {
        break increaseB;
      }
    }

    decreaseB: while ((lowB > 0) && (lowerB > Double.NEGATIVE_INFINITY)
        && (lowerB < Double.POSITIVE_INFINITY)) {
      lowerB = Math.nextAfter(lowerB, Double.NEGATIVE_INFINITY);
      lowB--;
      if ((lowerB <= Double.NEGATIVE_INFINITY) || (lowerB != lowerB)) {
        break decreaseB;
      }
    }

    return ((lowerA <= upperB) && (upperA >= lowerB));
  }

  /** the forbidden constructor */
  private MathUtils() {
    ErrorUtils.doNotCall();
  }
}
