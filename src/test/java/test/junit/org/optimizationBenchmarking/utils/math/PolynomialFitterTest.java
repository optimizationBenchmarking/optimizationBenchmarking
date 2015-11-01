package test.junit.org.optimizationBenchmarking.utils.math;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.PolynomialFitter;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;

/**
 * A test for the simple polynomial fitter
 */
public class PolynomialFitterTest {

  /** the constructor */
  public PolynomialFitterTest() {
    super();
  }

  /**
   * compute a random coefficient
   *
   * @param rand
   *          the random number generator
   * @return the random coefficient
   */
  private static final double __random(final Random rand) {
    switch (rand.nextInt(3)) {
      case 0: {
        return rand.nextDouble();
      }
      case 1: {
        return rand.nextGaussian();
      }
      default: {
        return (rand.nextInt(10001) - 5000);
      }
    }
  }

  /**
   * compute the polynomical of degree 1
   *
   * @param a0
   *          the first coefficient
   * @param a1
   *          the second coefficient
   * @param x
   *          the x-coordinate
   * @return the y value
   */
  private static final double __poly1(final double a0, final double a1,
      final double x) {
    return (a0 + (a1 * x));
  }

  /** test random polynomials of degree 1 */
  @Test(timeout = 3600000)
  public void testRandom1() {
    final Random rand;
    final double[] res;
    int i;
    double a0, a1, x0, x1, delta;

    rand = new Random();
    res = new double[2];
    for (i = 100000; (--i) >= 0;) {
      a0 = PolynomialFitterTest.__random(rand);
      a1 = PolynomialFitterTest.__random(rand);

      x0 = PolynomialFitterTest.__random(rand);
      do {
        x1 = PolynomialFitterTest.__random(rand);
      } while (PolynomialFitterTest.__similar(x1, x0));

      PolynomialFitter.findCoefficientsDegree1(//
          x0, PolynomialFitterTest.__poly1(a0, a1, x0), //
          x1, PolynomialFitterTest.__poly1(a0, a1, x1), //
          res);

      delta = (Math.max(Math.abs(a0), Math.abs(a1)) * 1e-8d);
      Assert.assertEquals(a0, res[0], delta);
      Assert.assertEquals(a1, res[1], delta);
    }
  }

  /**
   * check if two values are similar
   *
   * @param a
   *          the first value
   * @param b
   *          the second value
   * @return {@code true} if they are similar, {@code false} otherwise
   */
  private static final boolean __similar(final double a, final double b) {
    double minA, maxA, minB, maxB, t;
    if (a == b) {
      return true;
    }

    minA = (0.95d * a);
    maxA = (a / 0.95d);
    if (minA > maxA) {
      t = maxA;
      maxA = minA;
      minA = t;
    }

    minB = (0.95d * b);
    maxB = (b / 0.95d);
    if (minB > maxB) {
      t = maxB;
      maxB = minB;
      minB = t;
    }

    return ((minA <= maxB) && (maxA >= minB));
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
        Math.abs(y0 - PolynomialFitterTest.__poly2(a0, a1, a2, x0)), //
        Math.abs(y1 - PolynomialFitterTest.__poly2(a0, a1, a2, x1)), //
        Math.abs(y2 - PolynomialFitterTest.__poly2(a0, a1, a2, x2)));//
  }

  /**
   * compute a polynomial
   *
   * @param a0
   *          the first coefficient
   * @param a1
   *          the second coefficient
   * @param a2
   *          the third coefficient
   * @param x
   *          x-coordinate
   * @return the polynomial's value
   */
  private static final double __poly2(final double a0, final double a1,
      final double a2, final double x) {
    return (a0 + (a1 * x) + (a2 * x * x));
  }

  /** test random polynomials of degree 2 */
  @Test(timeout = 3600000)
  public void testRandom2() {
    final Random rand;
    final double[] res;
    int i;
    double a0, a1, a2, x0, y0, x1, y1, x2, y2;

    rand = new Random();
    res = new double[3];
    for (i = 100000; (--i) >= 0;) {
      a0 = PolynomialFitterTest.__random(rand);
      a1 = PolynomialFitterTest.__random(rand);
      a2 = PolynomialFitterTest.__random(rand);

      x0 = PolynomialFitterTest.__random(rand);
      do {
        x1 = PolynomialFitterTest.__random(rand);
      } while (PolynomialFitterTest.__similar(x1, x0));
      do {
        x2 = PolynomialFitterTest.__random(rand);
      } while (PolynomialFitterTest.__similar(x2, x0)
          || PolynomialFitterTest.__similar(x2, x1));

      PolynomialFitter.findCoefficientsDegree2(//
          x0, y0 = PolynomialFitterTest.__poly2(a0, a1, a2, x0), //
          x1, y1 = PolynomialFitterTest.__poly2(a0, a1, a2, x1), //
          x2, y2 = PolynomialFitterTest.__poly2(a0, a1, a2, x2), //
          res);

      Assert.assertTrue(//
          PolynomialFitterTest.__errorDegree2(x0, y0, x1, y1, x2, y2,
              res[0], res[1], res[2]) < //
              (1e-9d * Math.max(Math.abs(y0),
                  Math.max(Math.abs(y1), Math.abs(y2)))));
    }
  }

}
