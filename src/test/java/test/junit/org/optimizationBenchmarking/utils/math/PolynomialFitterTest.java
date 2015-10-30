package test.junit.org.optimizationBenchmarking.utils.math;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.PolynomialFitter;

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
      } while (x1 == x0);

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
   * compute the polynomical of degree 2
   *
   * @param a0
   *          the first coefficient
   * @param a1
   *          the second coefficient
   * @param a2
   *          the third coefficient
   * @param x
   *          the x-coordinate
   * @return the y value
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
    double a0, a1, a2, x0, x1, x2, delta;

    rand = new Random();
    res = new double[3];
    for (i = 100000; (--i) >= 0;) {
      a0 = PolynomialFitterTest.__random(rand);
      a1 = PolynomialFitterTest.__random(rand);
      a2 = PolynomialFitterTest.__random(rand);

      x0 = PolynomialFitterTest.__random(rand);
      do {
        x1 = PolynomialFitterTest.__random(rand);
      } while (x1 == x0);
      do {
        x2 = PolynomialFitterTest.__random(rand);
      } while ((x2 == x0) || (x2 == x1));

      PolynomialFitter.findCoefficientsDegree2(//
          x0, PolynomialFitterTest.__poly2(a0, a1, a2, x0), //
          x1, PolynomialFitterTest.__poly2(a0, a1, a2, x1), //
          x2, PolynomialFitterTest.__poly2(a0, a1, a2, x2), //
          res);

      delta = (Math.max(Math.abs(a0), Math.max(Math.abs(a1), Math.abs(a2)))
          * 1e-5d);
      Assert.assertEquals(a0, res[0], delta);
      Assert.assertEquals(a1, res[1], delta);
      Assert.assertEquals(a2, res[2], delta);
    }
  }

}
