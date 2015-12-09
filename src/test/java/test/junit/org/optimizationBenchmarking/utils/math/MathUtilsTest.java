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

  /** test step difference between two values */
  @Test(timeout = 3600000)
  public void testDifferenceBetweenTwoValues() {
    final Random random;
    double start, end;
    int starts, iteration;

    random = new Random();

    for (starts = 333; (--starts) >= 0;) {
      end = start = -(1d / Math.log(1d - random.nextDouble()));
      for (iteration = 0; iteration < 3333; iteration++) {
        Assert.assertEquals(iteration, MathUtils.difference(start, end));
        Assert.assertEquals(iteration, MathUtils.difference(end, start));
        end = Math.nextUp(end);
      }
    }
  }

  /**
   * test the "step" difference of two values, one of which is negative,
   * the other one being positive
   */
  @Test(timeout = 3600000)
  public void testDifferenceBetweenTwoValuesOfDifferentSign() {
    double start, end;
    int iteration;

    end = start = 0d;
    for (iteration = 0; iteration < 333333; iteration++) {
      Assert.assertEquals(
          (MathUtils.difference(start, 0d) + //
              MathUtils.difference(0d, end)),
          MathUtils.difference(start, end));
      Assert.assertEquals(
          (MathUtils.difference(start, 0d) + //
              MathUtils.difference(0d, end)),
          MathUtils.difference(end, start));
      start = Math.nextAfter(start, Double.NEGATIVE_INFINITY);
      end = Math.nextUp(end);
    }
  }

  /** test some basic cases of the step difference */
  @Test(timeout = 3600000)
  public void testDifferenceBetweenTwoValuesBasic() {
    Assert.assertEquals(MathUtils.difference(1d, Math.nextUp(1d)), 1L);
    Assert.assertEquals(MathUtils.difference(1d,
        Math.nextAfter(1d, Double.NEGATIVE_INFINITY)), 1L);
    Assert.assertEquals(MathUtils.difference(Math.nextUp(1d), 1d), 1L);
    Assert.assertEquals(MathUtils
        .difference(Math.nextAfter(1d, Double.NEGATIVE_INFINITY), 1d), 1L);
  }

  /** test the border cases of the step difference */
  @Test(timeout = 3600000)
  public void testDifferenceBetweenTwoValuesBorderCases() {
    Assert.assertEquals(0L, MathUtils.difference(0d, 0d));
    Assert.assertEquals(0L, MathUtils.difference(0d, -0d));
    Assert.assertEquals(0L, MathUtils.difference(-0d, 0d));
    Assert.assertEquals(0L, MathUtils.difference(-0d, -0d));

    Assert.assertEquals(1L, MathUtils.difference(0d, Double.MIN_VALUE));
    Assert.assertEquals(1L, MathUtils.difference(Double.MIN_VALUE, 0d));
    Assert.assertEquals(1L, MathUtils.difference(-0d, Double.MIN_VALUE));
    Assert.assertEquals(1L, MathUtils.difference(Double.MIN_VALUE, -0d));

    Assert.assertEquals(1L, MathUtils.difference(0d, -Double.MIN_VALUE));
    Assert.assertEquals(1L, MathUtils.difference(-Double.MIN_VALUE, 0d));
    Assert.assertEquals(1L, MathUtils.difference(-0d, -Double.MIN_VALUE));
    Assert.assertEquals(1L, MathUtils.difference(-Double.MIN_VALUE, -0d));

    Assert.assertEquals(2L,
        MathUtils.difference(Double.MIN_VALUE, -Double.MIN_VALUE));
    Assert.assertEquals(2L,
        MathUtils.difference(-Double.MIN_VALUE, Double.MIN_VALUE));

    Assert.assertEquals((1L << 52L),
        MathUtils.difference(0d, Double.MIN_NORMAL));
    Assert.assertEquals((1L << 52L),
        MathUtils.difference(Double.MIN_NORMAL, 0d));
    Assert.assertEquals((1L << 52L),
        MathUtils.difference(-0d, Double.MIN_NORMAL));
    Assert.assertEquals((1L << 52L),
        MathUtils.difference(Double.MIN_NORMAL, -0d));

    Assert.assertEquals((1L << 52L),
        MathUtils.difference(0d, -Double.MIN_NORMAL));
    Assert.assertEquals((1L << 52L),
        MathUtils.difference(-Double.MIN_NORMAL, 0d));
    Assert.assertEquals((1L << 52L),
        MathUtils.difference(-0d, -Double.MIN_NORMAL));
    Assert.assertEquals((1L << 52L),
        MathUtils.difference(-Double.MIN_NORMAL, -0d));

    Assert.assertEquals((2L << 52L),
        MathUtils.difference(Double.MIN_NORMAL, -Double.MIN_NORMAL));
    Assert.assertEquals((2L << 52L),
        MathUtils.difference(-Double.MIN_NORMAL, Double.MIN_NORMAL));

    Assert.assertEquals(0L, MathUtils.difference(Double.POSITIVE_INFINITY,
        Double.POSITIVE_INFINITY));
    Assert.assertEquals(0L, MathUtils.difference(Double.NEGATIVE_INFINITY,
        Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.difference(Double.POSITIVE_INFINITY,
        Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.difference(Double.NEGATIVE_INFINITY,
        Double.POSITIVE_INFINITY));

    Assert.assertEquals(-1L, MathUtils.difference(Double.NaN, Double.NaN));
    Assert.assertEquals(-1L,
        MathUtils.difference(Double.POSITIVE_INFINITY, Double.NaN));
    Assert.assertEquals(-1L,
        MathUtils.difference(Double.NEGATIVE_INFINITY, Double.NaN));
    Assert.assertEquals(-1L,
        MathUtils.difference(Double.NaN, Double.POSITIVE_INFINITY));
    Assert.assertEquals(-1L,
        MathUtils.difference(Double.NaN, Double.NEGATIVE_INFINITY));

    Assert.assertEquals(-1L,
        MathUtils.difference(0d, Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L,
        MathUtils.difference(0d, Double.POSITIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.difference(0d, Double.NaN));

    Assert.assertEquals(-1L,
        MathUtils.difference(Double.POSITIVE_INFINITY, 0d));
    Assert.assertEquals(-1L,
        MathUtils.difference(Double.NEGATIVE_INFINITY, 0d));
    Assert.assertEquals(-1L, MathUtils.difference(Double.NaN, 0d));

    Assert.assertEquals(-1L,
        MathUtils.difference(1d, Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L,
        MathUtils.difference(1d, Double.POSITIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.difference(1d, Double.NaN));

    Assert.assertEquals(-1L,
        MathUtils.difference(Double.POSITIVE_INFINITY, 1d));
    Assert.assertEquals(-1L,
        MathUtils.difference(Double.NEGATIVE_INFINITY, 1d));
    Assert.assertEquals(-1L, MathUtils.difference(Double.NaN, 1d));

    Assert.assertEquals(-1L,
        MathUtils.difference(-1d, Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L,
        MathUtils.difference(-1d, Double.POSITIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.difference(-1d, Double.NaN));

    Assert.assertEquals(-1L,
        MathUtils.difference(Double.POSITIVE_INFINITY, -1d));
    Assert.assertEquals(-1L,
        MathUtils.difference(Double.NEGATIVE_INFINITY, -1d));
    Assert.assertEquals(-1L, MathUtils.difference(Double.NaN, -1d));
  }
}
