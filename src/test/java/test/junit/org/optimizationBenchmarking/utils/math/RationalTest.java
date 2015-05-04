package test.junit.org.optimizationBenchmarking.utils.math;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.Rational;

/**
 * A test for rational numbers.
 */
public class RationalTest {

  /** the constructor */
  public RationalTest() {
    super();
  }

  /** test the deterministic fraction creation */
  @Test(timeout = 3600000)
  public void testRationalFractionRandom() {
    int i;
    double d;
    Random r;
    Rational rat;

    r = new Random();
    for (i = 10000; (--i) >= 0;) {
      do {
        switch (r.nextInt(3)) {
          case 0: {
            d = 0d;
            break;
          }
          case 1: {
            d = (r.nextInt(100000) - 50000);
            break;
          }
          default: {
            d = r.nextGaussian();
          }
        }
        switch (r.nextInt(3)) {
          case 0: {
            break;
          }
          case 1: {
            d += r.nextDouble();
            break;
          }
          default: {
            d += r.nextGaussian();
          }
        }
      } while (d == 0d);
      switch (r.nextInt(4)) {
        case 0: {
          break;
        }
        case 1: {
          d *= r.nextDouble();
          break;
        }
        case 2: {
          d *= (1d + r.nextInt(100000));
          break;
        }
        default: {
          d *= r.nextGaussian();
        }
      }

      rat = Rational.valueOf(d);
      if (rat.getState() == BasicNumber.STATE_NAN) {
        continue;
      }
      Assert.assertEquals(rat.doubleValue(), d, (Math.abs(d) * 1e-15));
    }
  }

  /** test the deterministic fraction creation */
  @Test(timeout = 3600000)
  public void testRationalFractionDeterministic() {
    double a, b, c;
    Rational r;

    for (a = (-100); a <= 100; a++) {
      for (b = (-100); b <= 100; b++) {
        c = (a / b);
        r = Rational.valueOf(c);
        if (r.getState() == BasicNumber.STATE_NAN) {
          continue;
        }
        Assert.assertEquals(r.doubleValue(), c, (Math.abs(c) * 1e-15));
      }
    }
  }

  /** test the deterministic number creation */
  @Test(timeout = 3600000)
  public void testRationalMultiplyDeterministic() {
    long a, b, c, d;
    Rational x, y;
    double u, v;

    for (a = (-100); a <= 100; a++) {
      for (b = (-100); b <= 100; b++) {
        x = Rational.valueOf(a, b);

        u = (a / ((double) b));
        if (u == 0d) {
          u = 0d;
        }

        for (c = (-30); c <= 30; c++) {
          for (d = (-30); d <= 30; d++) {
            y = Rational.valueOf(c, d);

            v = (c / ((double) d));
            if (v == 0d) {
              v = 0d;
            }

            Assert.assertEquals(
                //
                (u * v), x.multiply(y).doubleValue(),
                (Math.abs(u * v) * 1e-15));

            Assert.assertEquals(
                //
                u / v, x.divide(y).doubleValue(),
                (Math.abs(u / v) * 1e-15));
          }
        }
      }
    }
  }

  /** test the deterministic number creation */
  @Test(timeout = 3600000)
  public void testRationalCreateDeterministic() {
    int i, j;

    for (i = (-100); i <= 100; i++) {
      for (j = (-100); j <= 100; j++) {
        Assert.assertEquals(//
            (((double) i) / ((double) j)), Rational.valueOf(i, j)
            .doubleValue(), 1e-9);

      }
    }
  }

  /** test the deterministic number creation */
  @Test(timeout = 3600000)
  public void testRationalCreateRandom() {
    final Random r;
    final long max;
    long a, b;
    double x, y, v;
    int i;

    max = ((1L << 56l) - 1L);

    r = new Random();
    for (i = 10000; (--i) >= 0;) {

      do {
        a = (r.nextLong() & max);
        if (r.nextBoolean()) {
          a = (-a);
        }
        x = a;
      } while (x != a);

      do {
        b = (r.nextLong() & max);
        if (r.nextBoolean()) {
          b = (-b);
        }
        y = b;
      } while (y != b);

      v = (x / y);
      if ((v <= Double.NEGATIVE_INFINITY)
          || (v >= Double.POSITIVE_INFINITY) || (v != v)) {
        continue;
      }
      Assert.assertEquals(v, Rational.valueOf(a, b).doubleValue(),
          (1e-10d + (3 * Math.ulp(v))));

    }
  }

}
