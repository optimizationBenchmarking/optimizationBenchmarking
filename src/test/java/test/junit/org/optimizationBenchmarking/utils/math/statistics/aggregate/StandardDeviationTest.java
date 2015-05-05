package test.junit.org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StandardDeviationAggregate;

/** A test of the arithmetic means */
public class StandardDeviationTest {

  /**
   * Test {@code long}-based computation of the variance and compare with
   * Math.sqrt of the results obtained with VAR.S of Microsoft Excel 2013.
   */
  @Test(timeout = 3600000)
  public final void testStandardDeviationLongs() {
    StandardDeviationAggregate agg;
    double a, b;
    int i;

    agg = new StandardDeviationAggregate();
    for (i = 0; i < VarianceTest.VARIANCE_DATA_1.length; i++) {
      agg.append(VarianceTest.VARIANCE_DATA_1[i]);
      if (i > 0) {
        a = Math.sqrt(VarianceTest.VARIANCE_RESULTS_1[i]);
        b = agg.doubleValue();
        Assert.assertEquals(a, b, (Math.max(a, b) * 1e-14));
      }
    }
  }

  /**
   * Test {@code double}-based computation of the variance and compare with
   * Math.sqrt of the results obtained with VAR.S of Microsoft Excel 2013.
   */
  @Test(timeout = 3600000)
  public final void testStandardDeviationDoubles() {
    StandardDeviationAggregate agg;
    double a, b;
    int i;

    agg = new StandardDeviationAggregate();
    for (i = 0; i < VarianceTest.VARIANCE_DATA_2.length; i++) {
      agg.append(VarianceTest.VARIANCE_DATA_2[i]);
      if (i > 0) {
        a = Math.sqrt(VarianceTest.VARIANCE_RESULTS_2[i]);
        b = agg.doubleValue();
        Assert.assertEquals(a, b, (Math.max(a, b) * 1e-14));
      }
    }
  }
}