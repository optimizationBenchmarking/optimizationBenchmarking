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
    double bestError;

    bestError = PolynomialFitter.__computeDegree1(x0, y0, x1, y1, dest,
        Double.POSITIVE_INFINITY);
    if (MathUtils.isFinite(//
        PolynomialFitter.__computeDegree1(x1, y1, x0, y0, dest,
            bestError))) {
      return true;
    }

    dest[0] = dest[1] = Double.NaN;
    return false;
  }

  /**
   * Compute the parameters of a polynomial of
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
   * @param bestError
   *          the best error so far,
   *          {@link java.lang.Double#POSITIVE_INFINITY} if the problem has
   *          not yet been solved
   * @return the new error, {@link java.lang.Double#POSITIVE_INFINITY} if
   *         the problem has not been solved
   */
  private static final double __computeDegree1(final double x0,
      final double y0, final double x1, final double y1,
      final double[] dest, final double bestError) {
    final double a0, a1, error;
    if (MathUtils.isFinite(a1 = ((y1 - y0) / (x1 - x0)))) {
      if (MathUtils.isFinite(a0 = (y1 - (a1 * x1)))) {
        error = (Math.abs(y0 - (a0 + (a1 * x0)))
            + Math.abs(y1 - (a0 + (a1 * x1))));
        if (MathUtils.isFinite(error) && (error < bestError)) {
          dest[0] = a0;
          dest[1] = a1;
          return error;
        }
      }
    }
    return bestError;
  }

  /**
   * Compute the error of a quadratic fitting
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
   * @param a0
   *          the first coefficient
   * @param a1
   *          the second coefficient
   * @param a2
   *          the third coefficient
   * @return the error
   */
  private static final double __errorDegree2(final double x0,
      final double y0, final double x1, final double y1, final double x2,
      final double y2, final double a0, final double a1, final double a2) {
    return Add3.INSTANCE.computeAsDouble(//
        Math.abs(y0 - (a0 + (a1 * x0) + (a2 * x0 * x0))), //
        Math.abs(y1 - (a0 + (a1 * x1) + (a2 * x1 * x1))), //
        Math.abs(y2 - (a0 + (a1 * x2) + (a2 * x2 * x2))));//
  }

  /**
   * The first method to find the three coefficients of a polynomial of
   * degree 2, i.e., {@code y = f(x) = a0 + a1*x + a2*x^2}.
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
   * @param bestError
   *          the best error so far,
   *          {@link java.lang.Double#POSITIVE_INFINITY} if the problem has
   *          not yet been solved
   * @return the new error, {@link java.lang.Double#POSITIVE_INFINITY} if
   *         the problem has not been solved
   */
  private static final double __computeDegree2A(final double x0,
      final double y0, final double x1, final double y1, final double x2,
      final double y2, final double[] dest, final double bestError) {
    final double x0mx1, x0mx2, x1mx2, t0, t1, t2, error, a2, a1, a0;

    x0mx1 = (x0 - x1);
    x0mx2 = (x0 - x2);
    x1mx2 = (x1 - x2);

    t0 = (y0 / (x0mx1 * x0mx2));
    t1 = (y1 / (-x0mx1 * x1mx2));
    t2 = (y2 / (x0mx2 * x1mx2));

    if (MathUtils.isFinite(//
        a2 = Add3.INSTANCE.computeAsDouble(t0, t1, t2))) {
      if (MathUtils.isFinite(a1 = (-Add3.INSTANCE.computeAsDouble(//
          (t0 * (x1 + x2)), (t1 * (x0 + x2)), (t2 * (x0 + x1)))))) {
        if (MathUtils.isFinite(a0 = Add3.INSTANCE.computeAsDouble(//
            (t0 * x1 * x2), (t1 * x0 * x2), (t2 * x0 * x1)))) {
          error = PolynomialFitter.__errorDegree2(x0, y0, x1, y1, x2, y2,
              a0, a1, a2);
          if (MathUtils.isFinite(error) && (error < bestError)) {
            dest[0] = a0;
            dest[1] = a1;
            dest[2] = a2;
            return error;
          }
        }
      }
    }

    return bestError;
  }

  /**
   * The second method to find the three coefficients of a polynomial of
   * degree 2, i.e., {@code y = f(x) = a0 + a1*x + a2*x^2}.
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
   * @param bestError
   *          the best error so far,
   *          {@link java.lang.Double#POSITIVE_INFINITY} if the problem has
   *          not yet been solved
   * @return the new error, {@link java.lang.Double#POSITIVE_INFINITY} if
   *         the problem has not been solved
   */
  private static final double __computeDegree2B(final double x0,
      final double y0, final double x1, final double y1, final double x2,
      final double y2, final double[] dest, final double bestError) {
    final double x0mx1, x0mx2, error, a2, a1, a0;

    x0mx1 = (x0 - x1);
    x0mx2 = (x0 - x2);

    if (MathUtils.isFinite(//
        a2 = ((((y1 - y0) * (x0mx2)) + ((y2 - y0) * ((-x0mx1)))) / //
            (((x0mx2) * ((x1 * x1) - (x0 * x0)))
                + (((-x0mx1)) * ((x2 * x2) - (x0 * x0))))))) {
      if (MathUtils.isFinite(a1 = (//
      ((y1 - y0) - (a2 * ((x1 * x1) - (x0 * x0)))) / ((-x0mx1))))) {
        if (MathUtils.isFinite(a0 = y0 - (a2 * x0 * x0) - (a1 * x0))) {
          error = PolynomialFitter.__errorDegree2(x0, y0, x1, y1, x2, y2,
              a0, a1, a2);
          if (MathUtils.isFinite(error) && (error < bestError)) {
            dest[0] = a0;
            dest[1] = a1;
            dest[2] = a2;
            return error;
          }
        }
      }
    }

    return bestError;
  }

  /**
   * Find the three coefficients of a polynomial of degree 2, i.e.,
   * {@code y = f(x) = a0 + a1*x + a2*x^2}. This is done according to my
   * comment to my own question at
   * http://stackoverflow.com/questions/33445756.
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
    double bestError;

    bestError = PolynomialFitter.__computeDegree2A(x0, y0, x1, y1, x2, y2,
        dest, Double.POSITIVE_INFINITY);
    if (bestError == 0d) {
      return true;
    }
    bestError = PolynomialFitter.__computeDegree2B(x0, y0, x1, y1, x2, y2,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }

    bestError = PolynomialFitter.__computeDegree2A(x0, y0, x2, y2, x1, y1,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }
    bestError = PolynomialFitter.__computeDegree2B(x0, y0, x2, y2, x1, y1,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }

    bestError = PolynomialFitter.__computeDegree2A(x1, y1, x0, y0, x2, y2,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }
    bestError = PolynomialFitter.__computeDegree2B(x1, y1, x0, y0, x2, y2,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }

    bestError = PolynomialFitter.__computeDegree2A(x1, y1, x2, y2, x0, y0,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }
    bestError = PolynomialFitter.__computeDegree2B(x1, y1, x2, y2, x0, y0,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }

    bestError = PolynomialFitter.__computeDegree2A(x2, y2, x0, y0, x1, y1,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }
    bestError = PolynomialFitter.__computeDegree2B(x2, y2, x0, y0, x1, y1,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }

    bestError = PolynomialFitter.__computeDegree2A(x2, y2, x1, y1, x0, y0,
        dest, bestError);
    if (bestError == 0d) {
      return true;
    }
    bestError = PolynomialFitter.__computeDegree2B(x2, y2, x1, y1, x0, y0,
        dest, bestError);

    if (MathUtils.isFinite(bestError)) {
      return true;
    }

    dest[0] = dest[1] = dest[2] = Double.NaN;
    return false;
  }

  /** the forbidden constructor */
  private PolynomialFitter() {
    ErrorUtils.doNotCall();
  }
}
