package test.junit.org.optimizationBenchmarking.utils.math.units;

import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A test for length units.
 */
public class ELengthTest {

  /** the basic tests */
  private static final TestCase[] TESTS = {

      new TestCase(ELength.ANGSTROM, ELength.NANOMETER, 10L, 1L),
      new TestCase(ELength.KILOMETER, ELength.METER, 1L, 1000L),
      new TestCase(ELength.METER, ELength.DECIMETER, 1L, 10L),
      new TestCase(ELength.DECIMETER, ELength.CENTIMETER, 1L, 10L),
      new TestCase(ELength.CENTIMETER, ELength.MILLIMETER, 1L, 10L),
      new TestCase(ELength.MILLIMETER, ELength.MICROMETER, 1L, 1000L),
      new TestCase(ELength.MICROMETER, ELength.NANOMETER, 1L, 1000L),
      new TestCase(ELength.INCH, ELength.MIL, 1L, 1000L),
      new TestCase(ELength.LEAGUE, ELength.FURLONG, 1L, 24L),
      new TestCase(ELength.FATHOM, ELength.YARD, 1L, 2L),
      new TestCase(ELength.FURLONG, ELength.CHAIN, 1L, 10L),
      new TestCase(ELength.CHAIN, ELength.PERCH, 1L, 4L),
      new TestCase(ELength.PERCH, ELength.CUBIT, 1L, 11L),
      new TestCase(ELength.CUBIT, ELength.FOOT, 10L, 15L),
      new TestCase(ELength.ELL, ELength.FOOT, 100L, 375L),
      new TestCase(ELength.FOOT, ELength.INCH, 1L, 12L),
      new TestCase(ELength.FOOT, ELength.LINE, 1L, 144L),
      new TestCase(ELength.FOOT, ELength.BARLEYCORN, 1L, 36l),
      new TestCase(ELength.SPAN, ELength.NAIL, 1L, 4L),
      new TestCase(ELength.YARD, ELength.SPAN, 1L, 4L),
      new TestCase(ELength.FOOT, ELength.PALM, 1L, 4L),
      new TestCase(ELength.FOOT, ELength.HAND, 1L, 3L),
      new TestCase(ELength.YARD, ELength.FOOT, 1L, 3L),
      new TestCase(ELength.MIL, ELength.MICROINCH, 1L, 1000L),
      new TestCase(ELength.MILE_INTERNATIONAL, ELength.FURLONG, 1L, 8L),
      new TestCase(ELength.PICA, ELength.POINT, 1L, 12L),
      new TestCase(ELength.CICERO_EUROPEAN_DIDOT,
          ELength.POINT_EUROPEAN_DIDOT, 1L, 12L),
      new TestCase(ELength.LIGHT_YEAR, ELength.PARSEC, 1000000000L,
          306601394L),
      new TestCase(ELength.RED_SHIFT, ELength.PARSEC, 1L, 4222000000L),
      new TestCase(ELength.RED_SHIFT, ELength.LIGHT_YEAR, 1L, 13770000000L),
      new TestCase(ELength.LI_CHINESE, ELength.YIN_CHINESE, 1L, 15L),
      new TestCase(ELength.LI_CHINESE, ELength.ZHANG_CHINESE, 1L, 150L),
      new TestCase(ELength.LI_CHINESE, ELength.BU_CHINESE, 1L, 300L),
      new TestCase(ELength.LI_CHINESE, ELength.CHI_CHINESE, 1L, 1500L),
      new TestCase(ELength.LI_CHINESE, ELength.CUN_CHINESE, 1L, 15000L),
      new TestCase(ELength.LI_CHINESE, ELength.FEN_CHINESE, 1L, 150000L),
      new TestCase(ELength.LI_CHINESE, ELength.LI_CHINESE_SMALL, 1L,
          1500000L),
      new TestCase(ELength.LI_CHINESE, ELength.HAO_CHINESE, 1L, 15000000L),
      new TestCase(ELength.LI_CHINESE, ELength.SI_CHINESE, 1L, 150000000L),
      new TestCase(ELength.LI_CHINESE, ELength.HU_CHINESE, 1L, 1500000000L),
      // new TestCase(ELength.POINT, ELength.MILLIMETER, 100000L, 35146l),
      new TestCase(ELength.FOOT, ELength.METER, 10000L, 3048L),
      new TestCase(ELength.FOOT_US_SURVEY, ELength.METER, 10000000L,
          3048006l),
      new TestCase(ELength.INCH, ELength.CENTIMETER, 100L, 254L),
      new TestCase(ELength.YARD, ELength.METER, 10000L, 9144L),
      new TestCase(ELength.POINT_EUROPEAN_DIDOT, ELength.MILLIMETER,
          1000L, 376l),
      new TestCase(ELength.INCH, ELength.PICA, 1L, 6L),
      new TestCase(ELength.POINT, ELength.UM, 1000000000L, 352777777777L,
          false),

      new TestCase(ELength.MM, ELength.POINT, 100000000000000L,
          283464566929133L, false),

      new TestCase(ELength.MILE_INTERNATIONAL, ELength.KILOMETER,
          1000000L, 1609344L),
      new TestCase(ELength.MILE_NAUTICAL, ELength.KILOMETER, 1000L, 1852L),
      new TestCase(ELength.MILE_GEOGRAPHICAL, ELength.KILOMETER, 100L,
          742L),
      new TestCase(ELength.FATHOM, ELength.METER, 1000000L, 1828804L),
      new TestCase(ELength.LIGHT_SECOND, ELength.ANGSTROM, 1L,
          2997924580000000000L),
      new TestCase(ELength.LIGHT_SECOND, ELength.NANOMETER, 1L,
          299792458000000000L),
      new TestCase(ELength.LIGHT_SECOND, ELength.MICROMETER, 1L,
          299792458000000L),
      new TestCase(ELength.LIGHT_SECOND, ELength.MILLIMETER, 1L,
          299792458000L),
      new TestCase(ELength.LIGHT_SECOND, ELength.CENTIMETER, 1L,
          29979245800L),
      new TestCase(ELength.LIGHT_SECOND, ELength.DECIMETER, 1L,
          2997924580L),
      new TestCase(ELength.LIGHT_SECOND, ELength.METER, 1L, 299792458L),
      new TestCase(ELength.LIGHT_SECOND, ELength.KILOMETER, 1000L,
          299792458L),
      new TestCase(ELength.LIGHT_MINUTE, ELength.LIGHT_SECOND, 1L, 60L),
      new TestCase(ELength.LIGHT_MINUTE, ELength.MICROMETER, 1L,
          17987547480000000L),
      new TestCase(ELength.LIGHT_MINUTE, ELength.MILLIMETER, 1L,
          17987547480000L),
      new TestCase(ELength.LIGHT_MINUTE, ELength.CENTIMETER, 1L,
          1798754748000L),
      new TestCase(ELength.LIGHT_MINUTE, ELength.DECIMETER, 1L,
          179875474800L),
      new TestCase(ELength.LIGHT_MINUTE, ELength.METER, 1L, 17987547480L),
      new TestCase(ELength.LIGHT_MINUTE, ELength.KILOMETER, 1000L,
          17987547480L),
      new TestCase(ELength.LIGHT_YEAR, ELength.CENTIMETER, 1L,
          946073047258080000L),
      new TestCase(ELength.LIGHT_YEAR, ELength.DECIMETER, 1L,
          94607304725808000L),
      new TestCase(ELength.LIGHT_YEAR, ELength.METER, 1L,
          9460730472580800L),
      new TestCase(ELength.LIGHT_YEAR, ELength.KILOMETER, 10L,
          94607304725808L),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.MICROMETER, 1L,
          149597870700000000L),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.MILLIMETER, 1L,
          149597870700000L),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.CENTIMETER, 1L,
          14959787070000L),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.DECIMETER, 1L,
          1495978707000L),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.METER, 1L,
          149597870700L),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.KILOMETER, 1000L,
          149597870700L),
      new TestCase(ELength.METER, ELength.YIN_CHINESE, 100L, 3L),
      new TestCase(ELength.METER, ELength.ZHANG_CHINESE, 10L, 3L),
      new TestCase(ELength.METER, ELength.BU_CHINESE, 10L, 6l),
      new TestCase(ELength.METER, ELength.CHI_CHINESE, 1L, 3L),
      new TestCase(ELength.METER, ELength.CUN_CHINESE, 1L, 30L),
      new TestCase(ELength.METER, ELength.FEN_CHINESE, 1L, 300L),
      new TestCase(ELength.METER, ELength.LI_CHINESE_SMALL, 1L, 3000L),
      new TestCase(ELength.METER, ELength.HAO_CHINESE, 1L, 30000L),
      new TestCase(ELength.METER, ELength.SI_CHINESE, 1L, 300000L),
      new TestCase(ELength.METER, ELength.HU_CHINESE, 1L, 3000000L), };

  /** the constructor */
  public ELengthTest() {
    super();
  }

  /**
   * check if two values are different
   * 
   * @param should
   *          the expected result
   * @param is
   *          the obtained result
   * @return {@code true} if the expected result differs more than a
   *         reasonable amount from the real result
   */
  private static final boolean __isDifferent(final double should,
      final double is) {
    return ((should != is) && (Math.abs(should - is) > (1e-9 * Math
        .abs(should))));
  }

  /**
   * convert
   * 
   * @param from
   *          the from unit
   * @param to
   *          the to unit
   * @param fromValue
   *          the from value
   * @param toValue
   *          the to value
   * @param strict
   *          tests
   */
  private static final void __testConvert(final ELength from,
      final ELength to, final long fromValue, final long toValue,
      final boolean strict) {
    long res;
    double re;
    boolean calc;

    calc = false;
    try {
      res = from.convertTo(fromValue, to);
      calc = true;
    } catch (final Throwable xxx) {
      res = Long.MIN_VALUE;
    }
    if (res != toValue) {
      if (strict || ELengthTest.__isDifferent(toValue, res)) {
        throw new AssertionFailedError("Failed to convert '" + //$NON-NLS-1$
            fromValue + " " + from + //$NON-NLS-1$
            "' to '" + toValue + //$NON-NLS-1$
            " " + to + //$NON-NLS-1$
            (calc ? ("' - calculated '" + res + //$NON-NLS-1$
            "' instead.")//$NON-NLS-1$
                : ("got an error instead."))); //$NON-NLS-1$
      }
    }

    calc = false;
    try {
      re = from.convertTo(((double) fromValue), to);
      calc = true;
    } catch (final Throwable xxx) {
      re = Double.NaN;
    }
    if (ELengthTest.__isDifferent(toValue, re)) {
      throw new AssertionFailedError("Failed to convert '" + //$NON-NLS-1$
          ((double) fromValue) + " " + from + //$NON-NLS-1$
          "' to '" + ((double) toValue) + //$NON-NLS-1$
          " " + to + //$NON-NLS-1$
          (calc ? ("' - calculated '" + re + //$NON-NLS-1$
          "' instead.")//$NON-NLS-1$
              : ("got an error instead."))); //$NON-NLS-1$
    }
  }

  /** test the deterministic fraction creation */
  @Test(timeout = 3600000)
  public void testConversions() {
    int i, f;
    long fv, tv;

    for (final TestCase t : ELengthTest.TESTS) {
      ELengthTest.__testConvert(t.m_from, t.m_to, t.m_fromValue,
          t.m_toValue, t.m_strict);
      ELengthTest.__testConvert(t.m_to, t.m_from, t.m_toValue,
          t.m_fromValue, t.m_strict);

      for (i = 100001; (--i) >= 0;) {
        f = (i - 50000);

        if (f == 0) {
          fv = tv = 0L;
        } else {
          fv = (t.m_fromValue * f);
          if ((fv / f) != t.m_fromValue) {
            continue;
          }

          tv = (t.m_toValue * f);
          if ((tv / f) != t.m_toValue) {
            continue;
          }
        }

        ELengthTest.__testConvert(t.m_from, t.m_to, fv, tv, t.m_strict);
        ELengthTest.__testConvert(t.m_to, t.m_from, tv, fv, t.m_strict);
      }
    }
  }

  /** a test case */
  private static final class TestCase {
    /** from */
    final ELength m_from;
    /** to */
    final ELength m_to;
    /** the from value */
    final long m_fromValue;
    /** the to value */
    final long m_toValue;
    /** is this test case a strict one? */
    final boolean m_strict;

    /**
     * create
     * 
     * @param from
     *          from
     * @param fromValue
     *          from value
     * @param to
     *          to
     * @param toValue
     *          to value
     */
    TestCase(final ELength from, final ELength to, final long fromValue,
        final long toValue) {
      this(from, to, fromValue, toValue, true);
    }

    /**
     * create
     * 
     * @param from
     *          from
     * @param fromValue
     *          from value
     * @param to
     *          to
     * @param toValue
     *          to value
     * @param strict
     *          is this test case strict?
     */
    TestCase(final ELength from, final ELength to, final long fromValue,
        final long toValue, final boolean strict) {
      super();
      this.m_from = from;
      this.m_to = to;
      this.m_fromValue = fromValue;
      this.m_toValue = toValue;
      this.m_strict = strict;
    }
  }
}
