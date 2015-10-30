package org.optimizationBenchmarking.utils.math;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;

/** A set of utility methods to solve polynomials */
public final class PolynomialFitter {

  /**
   * Find the one coefficient of a polynomial of degree 0, i.e.,
   * {@code y = f(x) = a0}.
   *
   * @param x0
   *          the {@code x}-coordinate of the first point
   * @param y0
   *          the {@code y}-coordinate of the first point
   * @param dest
   *          the destination array
   * @return {@code true} if there is a finite, exact solution,
   *         {@code false} otherwise
   */
  public static final boolean findCoefficientsDegree0(final double x0,
      final double y0, final double[] dest) {
    if (MathUtils.isFinite(y0)) {
      dest[0] = y0;
      return true;
    }
    dest[0] = Double.NaN;
    return false;
  }

  /**
   * Find the two coefficients of a polynomial of degree 1, i.e.,
   * {@code y = f(x) = a0 + a1*x}.
   *
   * @param x0
   *          the {@code x}-coordinate of the first point
   * @param y0
   *          the {@code y}-coordinate of the first point
   * @param x1
   *          the {@code x}-coordinate of the second point
   * @param y1
   *          the {@code y}-coordinate of the second point
   * @param dest
   *          the destination array
   * @return {@code true} if there is a finite, exact solution,
   *         {@code false} otherwise
   */
  public static final boolean findCoefficientsDegree1(final double x0,
      final double y0, final double x1, final double y1,
      final double[] dest) {
    double a1, a0;

    if (MathUtils.isFinite(a1 = ((y1 - y0) / (x1 - x0))) || //
        MathUtils.isFinite(a1 = ((y0 - y1) / (x0 - x1)))) {
      if (MathUtils.isFinite(a0 = (y1 - (a1 * x1))) || //
          MathUtils.isFinite(a0 = (y0 - (a1 * x0)))) {
        dest[0] = a0;
        dest[1] = a1;
        return true;
      }
    }

    dest[0] = dest[1] = Double.NaN;
    return false;
  }

  /**
   * Find the three coefficients of a polynomial of degree 2, i.e.,
   * {@code y = f(x) = a0 + a1*x + a2*x^2}.
   *
   * @param x0
   *          the {@code x}-coordinate of the first point
   * @param y0
   *          the {@code y}-coordinate of the first point
   * @param x1
   *          the {@code x}-coordinate of the second point
   * @param y1
   *          the {@code y}-coordinate of the second point
   * @param x2
   *          the {@code x}-coordinate of the third point
   * @param y2
   *          the {@code y}-coordinate of the third point
   * @param dest
   *          the destination array
   * @return {@code true} if there is a finite, exact solution,
   *         {@code false} otherwise
   */
  public static final boolean findCoefficientsDegree2(final double x0,
      final double y0, final double x1, final double y1, final double x2,
      final double y2, final double[] dest) {
    final double x0mx1, x0mx2, x1mx2, t0, t1, t2;
    double a2, a1, a0;

    x0mx1 = (x0 - x1);
    x0mx2 = (x0 - x2);
    x1mx2 = (x1 - x2);

    t0 = (y0 / (x0mx1 * x0mx2));
    t1 = (y1 / (-x0mx1 * x1mx2));
    t2 = (y2 / (x0mx2 * x1mx2));

    if (MathUtils.isFinite(a2 = //
    Add3.INSTANCE.computeAsDouble(t0, t1, t2)) || //
        MathUtils.isFinite(//
            a2 = ((((y1 - y0) * (x0mx2)) + ((y2 - y0) * ((-x0mx1)))) / //
                (((x0mx2) * ((x1 * x1) - (x0 * x0)))
                    + (((-x0mx1)) * ((x2 * x2) - (x0 * x0))))))) {
      if (MathUtils.isFinite(a1 = //
      (-Add3.INSTANCE.computeAsDouble(//
          (t0 * (x1 + x2)), (t1 * (x0 + x2)), (t2 * (x0 + x1))))) || //
          MathUtils.isFinite(a1 = (//
          ((y1 - y0) - (a2 * ((x1 * x1) - (x0 * x0)))) / ((-x0mx1))))) {
        if (MathUtils.isFinite(//
            a0 = Add3.INSTANCE.computeAsDouble(//
                (t0 * x1 * x2), (t1 * x0 * x2), (t2 * x0 * x1)))//
            || MathUtils.isFinite(a0 = y0 - (a2 * x0 * x0) - (a1 * x0))) {
          dest[0] = a0;
          dest[1] = a1;
          dest[2] = a2;
          return true;
        }
      }
    }

    dest[0] = dest[1] = dest[2] = Double.NaN;
    return false;
  }

  /** the forbidden constructor */
  private PolynomialFitter() {
    ErrorUtils.doNotCall();
  }
}
