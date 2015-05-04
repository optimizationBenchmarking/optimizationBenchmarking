package test.junit.org.optimizationBenchmarking.utils.math.statistics.aggregate;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingAdd;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ArithmeticMeanAggregate;

/** A test of the arithmetic means */
public class ArithmeticMeanTest {

  /** test the longs */
  @Test(timeout = 3600000)
  public void testAverageLongs() {
    final Random rand;
    ArithmeticMeanAggregate ss;
    long sum, valueToAdd, testResult;
    int i, j;

    rand = new Random();

    for (j = 0; j < 10; j++) {
      ss = new ArithmeticMeanAggregate();
      Assert.assertEquals(BasicNumber.STATE_EMPTY, ss.getState());
      Assert.assertFalse(ss.isInteger());
      Assert.assertFalse(ss.isReal());

      sum = 0l;
      for (i = 1; i <= 2000; i++) {

        innerLoop: for (;;) {
          valueToAdd = (rand.nextInt(100001) - 50000);

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
        }

      ss.append(valueToAdd);
      sum = SaturatingAdd.INSTANCE.computeAsLong(sum, valueToAdd);

      if ((sum % i) == 0) {
        Assert.assertEquals(ss.getState(), BasicNumber.STATE_INTEGER);
        Assert.assertEquals((sum / i), ss.longValue());
      } else {
        Assert.assertEquals(ss.getState(), BasicNumber.STATE_DOUBLE);
        Assert.assertEquals((sum / ((double) i)), ss.doubleValue(),
            1e-10);
      }
      }
    }
  }

  /** test the doubles */
  @Test(timeout = 3600000)
  public void testAverageDoubles() {
    final Random rand;
    ArithmeticMeanAggregate ss;
    double sum, valueToAdd;
    int i, j;

    rand = new Random();

    for (j = 0; j < 10; j++) {
      ss = new ArithmeticMeanAggregate();
      Assert.assertEquals(BasicNumber.STATE_EMPTY, ss.getState());
      Assert.assertFalse(ss.isInteger());
      Assert.assertFalse(ss.isReal());

      sum = 0l;
      for (i = 1; i <= 2000; i++) {

        valueToAdd = (rand.nextInt(1001) - 500) * rand.nextDouble()
            * Math.exp(rand.nextInt(11) - 5);

        ss.append(valueToAdd);
        sum = Add.INSTANCE.computeAsDouble(sum, valueToAdd);

        Assert.assertTrue(//
            (ss.getState() == BasicNumber.STATE_DOUBLE) || //
            (ss.getState() == BasicNumber.STATE_INTEGER));
        Assert.assertEquals((sum / i), ss.doubleValue(), 1e-10);

      }
    }
  }
}