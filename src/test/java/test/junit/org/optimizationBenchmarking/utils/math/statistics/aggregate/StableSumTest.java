package test.junit.org.optimizationBenchmarking.utils.math.statistics.aggregate;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingAdd;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;

/** A test of the stable sums */
public class StableSumTest {

  /** test the summation of longs */
  @Test(timeout = 3600000)
  public void testSumUpLongs() {
    final StableSum ss;
    final Random rand;
    long sum, val, tmp;
    double d;
    int i;

    ss = new StableSum();
    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    rand = new Random();

    sum = 0l;
    for (i = 0; i < 100000000; i++) {
      do {
        switch (rand.nextInt(3)) {
          case 0: {
            val = (rand.nextInt(11) - 5);
            break;
          }
          case 1: {
            val = rand.nextInt();
          }
          default: {
            val = rand.nextLong();
          }
        }
        tmp = SaturatingAdd.INSTANCE.computeAsLong(sum, val);
      } while ((tmp - val) != sum);
      sum = tmp;

      d = val;
      if ((((long) d) == val) && (rand.nextBoolean())) {
        ss.append(d);
      } else {
        ss.append(val);
      }

      Assert.assertTrue(ss.isInteger());
      Assert.assertTrue(ss.isReal());
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertEquals(ss.longValue(), sum);
      Assert.assertEquals(sum, ss.doubleValue(), 0d);
    }
  }

  /** test the summation of doubles */
  @Test(timeout = 3600000)
  public void testSumUpDoubles() {
    final StableSum ss;
    final Random rand;
    double s, val, os;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());
    s = 0d;

    for (i = 0; i < 100000000; i++) {
      val = rand.nextDouble();
      os = s;
      s += val;
      ss.append(val);
      Assert.assertEquals(s, ss.doubleValue(),
          (Math.ulp(os) + Math.ulp(s) + Math.abs((s - os) - val)));
      s = ss.doubleValue();
      Assert.assertTrue(ss.isReal());
    }
  }

  /** test the summation of negative (infinite) numbers */
  @Test(timeout = 3600000)
  public void testNegativeInfinity() {
    final StableSum ss;
    final Random rand;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
    }
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertTrue(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
      ss.append(Double.NEGATIVE_INFINITY);
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(ss.doubleValue() == Double.NEGATIVE_INFINITY);
      Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);
    }

    ss.append(Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(Double.isNaN(ss.doubleValue()));
    Assert.assertTrue(ss.longValue() == 0L);
  }

  /** test the summation of positive (infinite) numbers */
  @Test(timeout = 3600000)
  public void testPositiveInfinity() {
    final StableSum ss;
    final Random rand;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
    }
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertTrue(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
      ss.append(Double.POSITIVE_INFINITY);
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(ss.doubleValue() == Double.POSITIVE_INFINITY);
      Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);
    }

    ss.append(Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(Double.isNaN(ss.doubleValue()));
    Assert.assertTrue(ss.longValue() == 0L);
  }

  /** test the overflowing summation */
  @Test(timeout = 3600000)
  public void testPositiveOverflowAndSubtract() {
    final StableSum ss;
    final Random rand;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
    }
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertTrue(ss.isReal());

    ss.append(Double.MAX_VALUE);
    ss.append(Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    ss.append(Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append((rand.nextInt() & 0x0fffffff));
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
      Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);
    }

    ss.append(Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    ss.append(-Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    ss.append(-Double.MAX_VALUE);
    ss.append(-Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    ss.append(-Double.MAX_VALUE);
    ss.append(-Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(Double.isNaN(ss.doubleValue()));
    Assert.assertTrue(ss.longValue() == 0L);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(Double.isNaN(ss.doubleValue()));
      Assert.assertTrue(ss.longValue() == 0L);
    }
  }

  /** test the overflowing summation */
  @Test(timeout = 3600000)
  public void testPositiveOverflowAddPositiveInfinityAddNegativeInfinity() {
    final StableSum ss;
    final Random rand;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
    }
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertTrue(ss.isReal());

    ss.append(Double.MAX_VALUE);
    ss.append(Double.MAX_VALUE);
    ss.append(Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    ss.append(Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append((rand.nextInt() & 0x0fffffff));
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
      Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);
    }

    ss.append(-1d);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    ss.append(Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(Double.isNaN(ss.doubleValue()));
    Assert.assertTrue(ss.longValue() == 0L);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(Double.isNaN(ss.doubleValue()));
      Assert.assertTrue(ss.longValue() == 0L);
    }
  }

  /** test the overflowing summation */
  @Test(timeout = 3600000)
  public void testPositiveOverflowAddNegativeInfinityAddPositiveInfinity() {
    final StableSum ss;
    final Random rand;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
    }
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertTrue(ss.isReal());

    ss.append(Double.MAX_VALUE);
    ss.append(Double.MAX_VALUE);
    ss.append(Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    ss.append(Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append((rand.nextInt() & 0x0fffffff));
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
      Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);
    }

    ss.append(1d);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    ss.append(Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(Double.isNaN(ss.doubleValue()));
    Assert.assertTrue(ss.longValue() == 0L);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(Double.isNaN(ss.doubleValue()));
      Assert.assertTrue(ss.longValue() == 0L);
    }
  }

  /** test the overflowing summation */
  @Test(timeout = 3600000)
  public void testNegativeOverflowAndAdd() {
    final StableSum ss;
    final Random rand;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
    }
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertTrue(ss.isReal());

    ss.append(-Double.MAX_VALUE);
    ss.append(-Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    ss.append(-Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    ss.append(-Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    for (i = 0; i < 10; i++) {
      ss.append(-rand.nextDouble());
      ss.append(-(rand.nextInt() & 0x0fffffff));
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
      Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);
    }

    ss.append(Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    ss.append(Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    ss.append(Double.MAX_VALUE);
    ss.append(Double.MAX_VALUE);
    ss.append(Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(Double.isNaN(ss.doubleValue()));
    Assert.assertTrue(ss.longValue() == 0L);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(Double.isNaN(ss.doubleValue()));
      Assert.assertTrue(ss.longValue() == 0L);
    }
  }

  /** test the underflowing summation */
  @Test(timeout = 3600000)
  public void testNegativeOverflowAddPositiveInfinityAddNegativeInfinity() {
    final StableSum ss;
    final Random rand;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
    }
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertTrue(ss.isReal());

    ss.append(-Double.MAX_VALUE);
    ss.append(-Double.MAX_VALUE);
    ss.append(-Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    ss.append(Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    for (i = 0; i < 10; i++) {
      ss.append(-rand.nextDouble());
      ss.append(-(rand.nextInt() & 0x0fffffff));
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
      Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);
    }

    ss.append(1d);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() >= Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MAX_VALUE);

    ss.append(Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(Double.isNaN(ss.doubleValue()));
    Assert.assertTrue(ss.longValue() == 0L);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(Double.isNaN(ss.doubleValue()));
      Assert.assertTrue(ss.longValue() == 0L);
    }
  }

  /** test the underflowing summation */
  @Test(timeout = 3600000)
  public void testNegativeOverflowAddNegativeInfinityAddPositiveInfinity() {
    final StableSum ss;
    final Random rand;
    int i;

    ss = new StableSum();
    rand = new Random();

    Assert.assertFalse(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isInteger());
    Assert.assertFalse(ss.isReal());

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
    }
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertTrue(ss.isReal());

    ss.append(-Double.MAX_VALUE);
    ss.append(-Double.MAX_VALUE);
    ss.append(-Double.MAX_VALUE);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    ss.append(Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    for (i = 0; i < 10; i++) {
      ss.append(-rand.nextDouble());
      ss.append(-(rand.nextInt() & 0x0fffffff));
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
      Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);
    }

    ss.append(1d);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(ss.doubleValue() <= Double.NEGATIVE_INFINITY);
    Assert.assertTrue(ss.longValue() == Long.MIN_VALUE);

    ss.append(Double.POSITIVE_INFINITY);
    Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
    Assert.assertFalse(ss.isReal());
    Assert.assertFalse(ss.isInteger());
    Assert.assertTrue(Double.isNaN(ss.doubleValue()));
    Assert.assertTrue(ss.longValue() == 0L);

    for (i = 0; i < 10; i++) {
      ss.append(rand.nextDouble());
      ss.append(rand.nextInt());
      Assert.assertTrue(ss.getState() != BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isReal());
      Assert.assertFalse(ss.isInteger());
      Assert.assertTrue(Double.isNaN(ss.doubleValue()));
      Assert.assertTrue(ss.longValue() == 0L);
    }
  }
}
