package test.junit.org.optimizationBenchmarking.utils.math.statistics.aggregate;

import java.util.Random;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;

/** A test of the quantiles. */
public class QuantileTest {

  /**
   * Test {@code long}-based computation of the variance quantiles by
   * comparing with
   * {@link org.apache.commons.math3.stat.descriptive.rank.Percentile}.
   */
  @Test(timeout = 3600000)
  public final void testQuantileLongs() {
    final long[] ldata;
    final double[] ddata;
    final QuantileAggregate[] quant;
    final Percentile[] perc;
    final Random rand;
    double a, b;
    int i, j;

    rand = new Random();
    ldata = new long[1000];
    ddata = new double[ldata.length];
    quant = new QuantileAggregate[20];
    perc = new Percentile[quant.length];

    // quant[0] = new QuantileAggregate(0d);
    quant[0] = new QuantileAggregate(1d);
    quant[1] = new QuantileAggregate(0.5d);
    quant[2] = new QuantileAggregate(0.75d);
    quant[3] = new QuantileAggregate(0.25d);
    quant[4] = new QuantileAggregate(0.975d);
    quant[5] = new QuantileAggregate(0.025d);
    quant[6] = new QuantileAggregate(Double.MIN_NORMAL);
    for (i = quant.length; (--i) > 6;) {
      quant[i] = new QuantileAggregate(rand.nextDouble());
    }
    for (i = quant.length; (--i) >= 0;) {
      perc[i] = new Percentile(100 * quant[i].getQuantile());
    }

    for (i = 0; i < ldata.length; i++) {
      ddata[i] = ldata[i] = (rand.nextInt(1000001) - 500000);

      for (j = quant.length; (--j) >= 0;) {
        perc[j].setData(ddata, 0, (i + 1));
        quant[j].append(ldata[i]);

        a = perc[j].evaluate();
        b = quant[j].doubleValue();
        Assert.assertEquals(a, b,
            (Math.max(Math.abs(a), Math.abs(b)) * 1e-9d));
      }
    }
  }

  /**
   * Test {@code double}-based computation of the variance quantiles by
   * comparing with
   * {@link org.apache.commons.math3.stat.descriptive.rank.Percentile}.
   */
  @Test(timeout = 3600000)
  public final void testQuantileDouble() {
    final double[] ddata;
    final QuantileAggregate[] quant;
    final Percentile[] perc;
    final Random rand;
    double a, b;
    int i, j;

    rand = new Random();
    ddata = new double[1000];
    quant = new QuantileAggregate[20];
    perc = new Percentile[quant.length];

    // quant[0] = new QuantileAggregate(0d);
    quant[0] = new QuantileAggregate(1d);
    quant[1] = new QuantileAggregate(0.5d);
    quant[2] = new QuantileAggregate(0.75d);
    quant[3] = new QuantileAggregate(0.25d);
    quant[4] = new QuantileAggregate(0.975d);
    quant[5] = new QuantileAggregate(0.025d);
    quant[6] = new QuantileAggregate(Double.MIN_NORMAL);
    for (i = quant.length; (--i) > 6;) {
      quant[i] = new QuantileAggregate(rand.nextDouble());
    }
    for (i = quant.length; (--i) >= 0;) {
      perc[i] = new Percentile(100 * quant[i].getQuantile());
    }

    for (i = 0; i < ddata.length; i++) {
      ddata[i] = (rand.nextDouble() * Math.exp(20 * rand.nextGaussian()));

      for (j = quant.length; (--j) >= 0;) {
        perc[j].setData(ddata, 0, (i + 1));
        quant[j].append(ddata[i]);

        a = perc[j].evaluate();
        b = quant[j].doubleValue();
        Assert.assertEquals(a, b,
            (Math.max(Math.abs(a), Math.abs(b)) * 1e-9d));
      }
    }
  }
}