package test.junit.org.optimizationBenchmarking.utils.text.numbers;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.NumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import test.junit.InstanceTest;

/**
 * A test for number appenders.
 */
@Ignore
public class NumberAppenderTest extends InstanceTest<NumberAppender> {

  /**
   * create the number appender test
   * 
   * @param instance
   *          the instance
   */
  protected NumberAppenderTest(final NumberAppender instance) {
    super(null, instance, true, false);
  }

  /** test the long output */
  @Test(timeout = 3600000)
  public void testLongDet() {
    final MemoryTextOutput m;
    final NumberAppender n;

    n = this.getInstance();
    m = new MemoryTextOutput();

    for (final ETextCase c : ETextCase.values()) {

      m.clear();
      n.appendTo(Long.MIN_VALUE, c, m);
      Assert.assertEquals(n.toString(Long.MIN_VALUE, c), m.toString());

      m.clear();
      n.appendTo(Long.MAX_VALUE, c, m);
      Assert.assertEquals(n.toString(Long.MAX_VALUE, c), m.toString());

      for (long l = -1000l; l <= 100l; l++) {
        m.clear();
        n.appendTo(l, c, m);
        Assert.assertEquals(n.toString(l, c), m.toString());
      }
    }
  }

  /** test the int output */
  @Test(timeout = 3600000)
  public void testIntDet() {
    final MemoryTextOutput m;
    final NumberAppender n;

    n = this.getInstance();
    m = new MemoryTextOutput();

    for (final ETextCase c : ETextCase.values()) {

      m.clear();
      n.appendTo(Integer.MIN_VALUE, c, m);
      Assert.assertEquals(n.toString(Integer.MIN_VALUE, c), m.toString());

      m.clear();
      n.appendTo(Integer.MAX_VALUE, c, m);
      Assert.assertEquals(n.toString(Integer.MAX_VALUE, c), m.toString());

      for (int l = -1000; l <= 100l; l++) {
        m.clear();
        n.appendTo(l, c, m);
        Assert.assertEquals(n.toString(l, c), m.toString());
      }
    }
  }

  /** test the double output */
  @Test(timeout = 3600000)
  public void testDoubleDet() {
    final MemoryTextOutput m;
    final NumberAppender n;
    double v;

    n = this.getInstance();
    m = new MemoryTextOutput();

    for (final ETextCase c : ETextCase.values()) {

      m.clear();
      n.appendTo(Double.MIN_VALUE, c, m);
      Assert.assertEquals(n.toString(Double.MIN_VALUE, c), m.toString());

      m.clear();
      n.appendTo(Double.MAX_VALUE, c, m);
      Assert.assertEquals(n.toString(Double.MAX_VALUE, c), m.toString());

      m.clear();
      n.appendTo(Double.POSITIVE_INFINITY, c, m);
      Assert.assertEquals(n.toString(Double.POSITIVE_INFINITY, c),
          m.toString());

      m.clear();
      n.appendTo(Double.NEGATIVE_INFINITY, c, m);
      Assert.assertEquals(n.toString(Double.NEGATIVE_INFINITY, c),
          m.toString());

      m.clear();
      n.appendTo(Double.NaN, c, m);
      Assert.assertEquals(n.toString(Double.NaN, c), m.toString());

      for (double l = -100; l <= 100; l++) {
        if (l >= 0) {
          m.clear();
          v = Math.sqrt(l);
          n.appendTo(v, c, m);
          Assert.assertEquals(n.toString(v, c), m.toString());

          m.clear();
          v = Math.log(l);
          n.appendTo(v, c, m);
          Assert.assertEquals(n.toString(v, c), m.toString());
        }

        m.clear();
        v = Math.exp(l);
        n.appendTo(v, c, m);
        Assert.assertEquals(n.toString(v, c), m.toString());

        for (double z = -100; z <= 100; z++) {
          m.clear();
          v = (l / z);
          n.appendTo(v, c, m);
          Assert.assertEquals(n.toString(v, c), m.toString());

          m.clear();
          v = Math.exp(l);
          n.appendTo(v, c, m);
          Assert.assertEquals(n.toString(v, c), m.toString());
        }
      }
    }
  }
}
