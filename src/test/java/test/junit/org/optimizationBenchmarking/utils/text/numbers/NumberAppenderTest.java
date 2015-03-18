package test.junit.org.optimizationBenchmarking.utils.text.numbers;

import java.util.Random;

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

  /** can we parse? */
  private final boolean m_canParse;

  /**
   * create the number appender test
   * 
   * @param instance
   *          the instance
   * @param canParse
   *          can we parse?
   */
  protected NumberAppenderTest(final NumberAppender instance,
      final boolean canParse) {
    super(null, instance, true, false);
    this.m_canParse = canParse;
  }

  /**
   * parse a string to a double
   * 
   * @param s
   *          the string
   * @return the double
   */
  protected double parseDouble(final String s) {
    return Double.parseDouble(s);
  }

  /**
   * parse a string to a long
   * 
   * @param s
   *          the string
   * @return the long
   */
  protected long parseLong(final String s) {
    return Long.parseLong(s);
  }

  /** test the long output */
  @Test(timeout = 3600000)
  public void testLongDet() {
    final MemoryTextOutput m;
    final NumberAppender n;
    long l;
    String s;

    n = this.getInstance();
    m = new MemoryTextOutput();

    for (final ETextCase c : ETextCase.values()) {

      m.clear();
      l = Long.MIN_VALUE;
      n.appendTo(l, c, m);
      Assert.assertEquals(n.toString(l, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(l, this.parseLong(s));
      }

      m.clear();
      l = Long.MAX_VALUE;
      n.appendTo(l, c, m);
      Assert.assertEquals(n.toString(l, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(l, this.parseLong(s));
      }

      for (long ll = -1000L; ll <= 1000L; ll++) {
        m.clear();
        n.appendTo(ll, c, m);
        Assert.assertEquals(n.toString(ll, c), s = m.toString());
        if (this.m_canParse) {
          Assert.assertEquals(ll, this.parseLong(s));
        }
      }
    }
  }

  /** test the int output */
  @Test(timeout = 3600000)
  public void testIntDet() {
    final MemoryTextOutput m;
    final NumberAppender n;
    String s;
    int i;

    n = this.getInstance();
    m = new MemoryTextOutput();

    for (final ETextCase c : ETextCase.values()) {

      m.clear();
      i = Integer.MIN_VALUE;
      n.appendTo(i, c, m);
      Assert.assertEquals(n.toString(i, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseLong(s), i);
      }

      m.clear();
      i = Integer.MAX_VALUE;
      n.appendTo(i, c, m);
      Assert.assertEquals(n.toString(i, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseLong(s), i);
      }

      for (int l = -1000; l <= 1000; l++) {
        m.clear();
        n.appendTo(l, c, m);
        Assert.assertEquals(n.toString(l, c), s = m.toString());
        if (this.m_canParse) {
          Assert.assertEquals(l, this.parseLong(s));
        }
      }
    }
  }

  /** test the double output */
  @Test(timeout = 3600000)
  public void testDoubleDet() {
    final MemoryTextOutput m;
    final NumberAppender n;
    double v;
    String s;

    n = this.getInstance();
    m = new MemoryTextOutput();

    for (final ETextCase c : ETextCase.values()) {

      m.clear();
      n.appendTo(v = Double.MIN_VALUE, c, m);
      Assert.assertEquals(n.toString(v, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
      }

      m.clear();
      n.appendTo(v = Double.MAX_VALUE, c, m);
      Assert.assertEquals(n.toString(v, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
      }

      m.clear();
      n.appendTo(v = Double.POSITIVE_INFINITY, c, m);
      Assert.assertEquals(n.toString(v, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
      }

      m.clear();
      n.appendTo(v = Double.NEGATIVE_INFINITY, c, m);
      Assert.assertEquals(n.toString(v, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
      }

      m.clear();
      n.appendTo(v = Double.NaN, c, m);
      Assert.assertEquals(n.toString(v, c), s = m.toString());
      if (this.m_canParse) {
        v = this.parseDouble(s);
        Assert.assertTrue(v != v);
      }

      for (double l = -100; l <= 100; l++) {
        if (l >= 0) {
          m.clear();
          v = Math.sqrt(l);
          n.appendTo(v, c, m);
          Assert.assertEquals(n.toString(v, c), s = m.toString());
          if (this.m_canParse) {
            Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
          }

          m.clear();
          v = Math.log(l);
          n.appendTo(v, c, m);
          Assert.assertEquals(n.toString(v, c), s = m.toString());
          if (this.m_canParse) {
            Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
          }
        }

        m.clear();
        v = Math.exp(l);
        n.appendTo(v, c, m);
        Assert.assertEquals(n.toString(v, c), s = m.toString());
        if (this.m_canParse) {
          Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
        }

        for (double z = -100; z <= 100; z++) {
          m.clear();
          v = (l / z);
          n.appendTo(v, c, m);
          Assert.assertEquals(n.toString(v, c), s = m.toString());
          if (this.m_canParse) {
            Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
          }

          m.clear();
          v = Math.exp(l);
          n.appendTo(v, c, m);
          Assert.assertEquals(n.toString(v, c), s = m.toString());
          if (this.m_canParse) {
            Assert.assertEquals(this.parseDouble(s), v, 1e-15d);
          }
        }
      }
    }
  }

  /**
   * Some {@code double}s have an overly long string representation in
   * Java. &quot;{@code -7.66eE22}&quot;, for instance, will be represented
   * as &quot;{@code -7.664000000000001E22}&quot; by
   * {@link java.lang.Double#toString(double)}. This method tests what
   * happens if we generate such {@code double}s and feed them to the
   * {@link org.optimizationBenchmarking.utils.text.numbers.NumberAppender#toString(double, ETextCase)}
   * method of the
   * {@link org.optimizationBenchmarking.utils.text.numbers.NumberAppender}
   * .
   */
  @Test(timeout = 3600000)
  public void testOverlyLongDoubleToText() {
    final Random r;
    final NumberAppender ap;
    int i;
    double a, b;

    if (!(this.m_canParse)) {
      return;
    }

    ap = this.getInstance();
    r = new Random();
    for (i = 1; i <= 1000; i++) {
      a = NumberAppenderTest.__makeDouble(r);
      b = Double.parseDouble(ap.toString(a, ETextCase.IN_SENTENCE));
      Assert.assertEquals(a, b, 1e-15d);
    }
  }

  /**
   * Create a double which has a long representation
   * 
   * @param r
   *          the random number generator
   * @return the double number
   */
  private static final double __makeDouble(final Random r) {
    final MemoryTextOutput sb;
    String s, t;
    double d;
    int dotIdx, i, l1, l2, i1, i2;

    sb = new MemoryTextOutput(32);

    for (i = 0; i < 100000; i++) {
      if (r.nextBoolean()) {
        sb.append('-');
      }
      while ((sb.length() <= 7) && r.nextBoolean()) {
        sb.append(r.nextInt(10));
      }

      sb.append(1 + r.nextInt(9));
      if (sb.length() < 10) {
        dotIdx = sb.length();
        sb.append('.');
        while ((sb.length() <= 10) && r.nextBoolean()) {
          sb.append(r.nextInt(10));
        }
        sb.append(1 + r.nextInt(9));
      } else {
        dotIdx = -1;
      }

      sb.append('E');
      if ((dotIdx == 1) && r.nextBoolean()) {
        sb.append('-');
      }
      sb.append(r.nextInt(300));

      s = sb.toString();
      sb.clear();
      d = Double.parseDouble(s);
      t = Double.toString(d);
      l1 = s.length();
      l2 = t.length();
      if ((l1 < l2) && (s.indexOf('.') == t.indexOf('.'))) {
        i1 = s.indexOf('E');
        i2 = t.indexOf('E');
        if (((i1 < 0) && (i2 < 0)) || //
            ((i2 > 0) && (i2 > 0) && ((l1 - i1) == (l2 - i2)))) {
          return d;
        }
      }
    }

    return r.nextDouble();
  }
}
