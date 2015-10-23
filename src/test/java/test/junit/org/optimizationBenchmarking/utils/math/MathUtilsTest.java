package test.junit.org.optimizationBenchmarking.utils.math;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.MathUtils;

/**
 * A test for math utils
 */
public class MathUtilsTest {

  /** the constructor */
  public MathUtilsTest() {
    super();
  }

  /** test the is-finite method */
  @Test(timeout = 3600000)
  public void testIsFinite() {
    Assert.assertTrue(MathUtils.isFinite(1d));
    Assert.assertTrue(MathUtils.isFinite(0d));
    Assert.assertTrue(MathUtils.isFinite(-1d));
    Assert.assertTrue(MathUtils.isFinite(Double.MAX_VALUE));
    Assert.assertTrue(MathUtils.isFinite(Double.MIN_NORMAL));
    Assert.assertTrue(MathUtils.isFinite(Double.MIN_NORMAL));
    Assert.assertFalse(MathUtils.isFinite(Double.NaN));
    Assert.assertFalse(MathUtils.isFinite(Double.NEGATIVE_INFINITY));
    Assert.assertFalse(MathUtils.isFinite(Double.POSITIVE_INFINITY));
    Assert.assertFalse(MathUtils.isFinite(1d / 0d));
    Assert.assertFalse(MathUtils.isFinite(-1d / 0d));
    Assert.assertFalse(MathUtils.isFinite(0d / 0d));
  }

  /** test is-in-range method for finite input */
  @Test(timeout = 3600000)
  public void testIsInRangeFinite() {
    Random rand;
    double a, amin, amax;
    int i, j, k;

    rand = new Random();
    for (i = 100; (--i) >= 0;) {
      a = rand.nextDouble();

      for (j = 200; (--j) >= 0;) {
        amin = amax = a;
        for (k = 0; k <= j; k++) {
          Assert.assertTrue(MathUtils.isWithinSteps(a, amin, j));
          Assert.assertTrue(MathUtils.isWithinSteps(a, amax, j));
          amax = Math.nextUp(amax);
          amin = Math.nextAfter(amin, Double.NEGATIVE_INFINITY);
        }
        Assert.assertFalse(MathUtils.isWithinSteps(a, amin, j));
        Assert.assertFalse(MathUtils.isWithinSteps(a, amax, j));
      }
    }
  }

  /** test is-in-range method for non-finite input */
  @Test(timeout = 3600000)
  public void testIsInRangeNonFinite() {
    Assert.assertFalse(MathUtils.isWithinSteps(Double.NEGATIVE_INFINITY,
        Double.POSITIVE_INFINITY, 1000));

    Assert.assertTrue(MathUtils.isWithinSteps(Double.NEGATIVE_INFINITY,
        Double.NEGATIVE_INFINITY, 0));
    Assert.assertTrue(MathUtils.isWithinSteps(Double.NEGATIVE_INFINITY,
        Double.NEGATIVE_INFINITY, 1));

    Assert.assertFalse(MathUtils.isWithinSteps(-Double.MAX_VALUE,
        Double.NEGATIVE_INFINITY, 0));
    Assert.assertTrue(MathUtils.isWithinSteps(-Double.MAX_VALUE,
        Double.NEGATIVE_INFINITY, 1));

    Assert.assertFalse(MathUtils.isWithinSteps(Double.NEGATIVE_INFINITY,
        -Double.MAX_VALUE, 0));
    Assert.assertTrue(MathUtils.isWithinSteps(Double.NEGATIVE_INFINITY,
        -Double.MAX_VALUE, 1));

    Assert.assertFalse(MathUtils.isWithinSteps(Double.MAX_VALUE,
        Double.POSITIVE_INFINITY, 0));
    Assert.assertTrue(MathUtils.isWithinSteps(Double.MAX_VALUE,
        Double.POSITIVE_INFINITY, 1));

    Assert.assertFalse(MathUtils.isWithinSteps(Double.POSITIVE_INFINITY,
        Double.MAX_VALUE, 0));
    Assert.assertTrue(MathUtils.isWithinSteps(Double.POSITIVE_INFINITY,
        Double.MAX_VALUE, 1));
  }

}
