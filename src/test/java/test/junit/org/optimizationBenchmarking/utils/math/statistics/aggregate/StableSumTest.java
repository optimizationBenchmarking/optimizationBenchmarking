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
    final Random rand;
    StableSum ss;
    long sum, valueToAdd, testResult, longVal;
    double doubleVal;
    int i, j;

    rand = new Random();

    for (j = 0; j < 10; j++) {
      ss = new StableSum();
      Assert.assertEquals(ss.getState(), BasicNumber.STATE_EMPTY);
      Assert.assertFalse(ss.isInteger());
      Assert.assertFalse(ss.isReal());

      sum = 0l;
      for (i = 0; i <= 2000000; i++) {

        innerLoop: for (;;) {
          switch (rand.nextInt(7)) {
            case 0: {
              valueToAdd = (rand.nextInt(11) - 5);
              break;
            }
            case 1: {
              valueToAdd = (rand.nextInt(100001) - 50000);
              break;
            }
            case 2: {
              valueToAdd = rand.nextInt();
              break;
            }
            case 3: {
              valueToAdd = (Long.MAX_VALUE - rand.nextInt(100000));
              break;
            }
            case 4: {
              valueToAdd = (Long.MIN_VALUE + rand.nextInt(100000));
              break;
            }
            default: {
              valueToAdd = rand.nextLong();
              break;
            }
          }

          testResult = (sum + valueToAdd);
          if (valueToAdd < 0L) {
            if (testResult < sum) {
              break innerLoop;
            }
          } else {
            if (valueToAdd == 0L) {
              if (testResult == sum) {
                break innerLoop;
              }
            } else {
              if (testResult > sum) {
                break innerLoop;
              }
            }
          }

          Assert.assertNotEquals(testResult,
              SaturatingAdd.INSTANCE.computeAsLong(sum, valueToAdd));
        }

      Assert.assertEquals(testResult,
          SaturatingAdd.INSTANCE.computeAsLong(sum, valueToAdd));

      sum = testResult;

      doubleVal = valueToAdd;
      longVal = ((long) doubleVal);
      if ((doubleVal == valueToAdd) && (longVal == valueToAdd)
          && (rand.nextBoolean())) {
        ss.append(doubleVal);
      } else {
        ss.append(valueToAdd);
      }

      Assert.assertTrue(ss.isInteger());
      Assert.assertTrue(ss.isReal());
      Assert.assertNotEquals(ss.getState(), BasicNumber.STATE_EMPTY);
      Assert.assertEquals(ss.longValue(), sum);
      Assert.assertEquals(sum, ss.doubleValue(), 0d);
      }
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

    for (i = 0; i < 10000000; i++) {
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
