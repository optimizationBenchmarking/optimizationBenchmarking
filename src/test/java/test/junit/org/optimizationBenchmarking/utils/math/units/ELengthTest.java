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

      new TestCase(ELength.ANGSTROM, ELength.NANOMETER, 10l, 1l),
      new TestCase(ELength.KILOMETER, ELength.METER, 1l, 1000l),
      new TestCase(ELength.METER, ELength.DECIMETER, 1l, 10l),
      new TestCase(ELength.DECIMETER, ELength.CENTIMETER, 1l, 10l),
      new TestCase(ELength.CENTIMETER, ELength.MILLIMETER, 1l, 10l),
      new TestCase(ELength.MILLIMETER, ELength.MICROMETER, 1l, 1000l),
      new TestCase(ELength.MICROMETER, ELength.NANOMETER, 1l, 1000l),
      new TestCase(ELength.INCH, ELength.MIL, 1l, 1000l),
      new TestCase(ELength.LEAGUE, ELength.FURLONG, 1l, 24l),
      new TestCase(ELength.FATHOM, ELength.YARD, 1l, 2l),
      new TestCase(ELength.FURLONG, ELength.CHAIN, 1l, 10l),
      new TestCase(ELength.CHAIN, ELength.PERCH, 1l, 4l),
      new TestCase(ELength.PERCH, ELength.CUBIT, 1l, 11l),
      new TestCase(ELength.CUBIT, ELength.FOOT, 10l, 15l),
      new TestCase(ELength.ELL, ELength.FOOT, 100l, 375l),
      new TestCase(ELength.FOOT, ELength.INCH, 1l, 12l),
      new TestCase(ELength.FOOT, ELength.LINE, 1l, 144l),
      new TestCase(ELength.FOOT, ELength.BARLEYCORN, 1l, 36l),
      new TestCase(ELength.SPAN, ELength.NAIL, 1l, 4l),
      new TestCase(ELength.YARD, ELength.SPAN, 1l, 4l),
      new TestCase(ELength.FOOT, ELength.PALM, 1l, 4l),
      new TestCase(ELength.FOOT, ELength.HAND, 1l, 3l),
      new TestCase(ELength.YARD, ELength.FOOT, 1l, 3l),
      new TestCase(ELength.MIL, ELength.MICROINCH, 1l, 1000l),
      new TestCase(ELength.MILE_INTERNATIONAL, ELength.FURLONG, 1l, 8l),
      new TestCase(ELength.PICA, ELength.POINT, 1l, 12l),
      new TestCase(ELength.PICA, ELength.PIXEL, 1l, 16l),
      new TestCase(ELength.PICA, ELength.TWIP, 1l, 240l),
      new TestCase(ELength.CICERO_EUROPEAN_DIDOT,
          ELength.POINT_EUROPEAN_DIDOT, 1l, 12l),
      new TestCase(ELength.PICA_POSTSCRIPT, ELength.POINT_POSTSCRIPT, 1l,
          12l),
      new TestCase(ELength.LIGHT_YEAR, ELength.PARSEC, 1000000000l,
          306601394l),
      new TestCase(ELength.RED_SHIFT, ELength.PARSEC, 1l, 4222000000l),
      new TestCase(ELength.RED_SHIFT, ELength.LIGHT_YEAR, 1l, 13770000000l),
      new TestCase(ELength.LI_CHINESE, ELength.YIN_CHINESE, 1l, 15l),
      new TestCase(ELength.LI_CHINESE, ELength.ZHANG_CHINESE, 1l, 150l),
      new TestCase(ELength.LI_CHINESE, ELength.BU_CHINESE, 1l, 300l),
      new TestCase(ELength.LI_CHINESE, ELength.CHI_CHINESE, 1l, 1500l),
      new TestCase(ELength.LI_CHINESE, ELength.CUN_CHINESE, 1l, 15000l),
      new TestCase(ELength.LI_CHINESE, ELength.FEN_CHINESE, 1l, 150000l),
      new TestCase(ELength.LI_CHINESE, ELength.LI_CHINESE_SMALL, 1l,
          1500000l),
      new TestCase(ELength.LI_CHINESE, ELength.HAO_CHINESE, 1l, 15000000l),
      new TestCase(ELength.LI_CHINESE, ELength.SI_CHINESE, 1l, 150000000l),
      new TestCase(ELength.LI_CHINESE, ELength.HU_CHINESE, 1l, 1500000000l),
      new TestCase(ELength.POINT, ELength.MILLIMETER, 100000l, 35146l),
      new TestCase(ELength.FOOT, ELength.METER, 10000l, 3048l),
      new TestCase(ELength.FOOT_US_SURVEY, ELength.METER, 10000000l,
          3048006l),
      new TestCase(ELength.INCH, ELength.CENTIMETER, 100l, 254l),
      new TestCase(ELength.YARD, ELength.METER, 10000l, 9144l),
      new TestCase(ELength.POINT_EUROPEAN_DIDOT, ELength.MILLIMETER,
          1000l, 376l),
      new TestCase(ELength.PICA, ELength.MILLIMETER, 1000l, 4233l),
      new TestCase(ELength.PICA_POSTSCRIPT, ELength.MILLIMETER, 1000l,
          4233l),
      new TestCase(ELength.MILE_INTERNATIONAL, ELength.KILOMETER,
          1000000l, 1609344l),
      new TestCase(ELength.MILE_NAUTICAL, ELength.KILOMETER, 1000l, 1852l),
      new TestCase(ELength.MILE_GEOGRAPHICAL, ELength.KILOMETER, 100l,
          742l),
      new TestCase(ELength.FATHOM, ELength.METER, 1000000l, 1828804l),
      new TestCase(ELength.LIGHT_SECOND, ELength.ANGSTROM, 1l,
          2997924580000000000l),
      new TestCase(ELength.LIGHT_SECOND, ELength.NANOMETER, 1l,
          299792458000000000l),
      new TestCase(ELength.LIGHT_SECOND, ELength.MICROMETER, 1l,
          299792458000000l),
      new TestCase(ELength.LIGHT_SECOND, ELength.MILLIMETER, 1l,
          299792458000l),
      new TestCase(ELength.LIGHT_SECOND, ELength.CENTIMETER, 1l,
          29979245800l),
      new TestCase(ELength.LIGHT_SECOND, ELength.DECIMETER, 1l,
          2997924580l),
      new TestCase(ELength.LIGHT_SECOND, ELength.METER, 1l, 299792458l),
      new TestCase(ELength.LIGHT_SECOND, ELength.KILOMETER, 1000l,
          299792458l),
      new TestCase(ELength.LIGHT_MINUTE, ELength.LIGHT_SECOND, 1l, 60l),
      new TestCase(ELength.LIGHT_MINUTE, ELength.MICROMETER, 1l,
          17987547480000000l),
      new TestCase(ELength.LIGHT_MINUTE, ELength.MILLIMETER, 1l,
          17987547480000l),
      new TestCase(ELength.LIGHT_MINUTE, ELength.CENTIMETER, 1l,
          1798754748000l),
      new TestCase(ELength.LIGHT_MINUTE, ELength.DECIMETER, 1l,
          179875474800l),
      new TestCase(ELength.LIGHT_MINUTE, ELength.METER, 1l, 17987547480l),
      new TestCase(ELength.LIGHT_MINUTE, ELength.KILOMETER, 1000l,
          17987547480l),
      new TestCase(ELength.LIGHT_YEAR, ELength.CENTIMETER, 1l,
          946073047258080000l),
      new TestCase(ELength.LIGHT_YEAR, ELength.DECIMETER, 1l,
          94607304725808000l),
      new TestCase(ELength.LIGHT_YEAR, ELength.METER, 1l,
          9460730472580800l),
      new TestCase(ELength.LIGHT_YEAR, ELength.KILOMETER, 10l,
          94607304725808l),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.MICROMETER, 1l,
          149597870700000000l),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.MILLIMETER, 1l,
          149597870700000l),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.CENTIMETER, 1l,
          14959787070000l),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.DECIMETER, 1l,
          1495978707000l),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.METER, 1l,
          149597870700l),
      new TestCase(ELength.ASTRONOMICAL_UNIT, ELength.KILOMETER, 1000l,
          149597870700l),
      new TestCase(ELength.METER, ELength.YIN_CHINESE, 100l, 3l),
      new TestCase(ELength.METER, ELength.ZHANG_CHINESE, 10l, 3l),
      new TestCase(ELength.METER, ELength.BU_CHINESE, 10l, 6l),
      new TestCase(ELength.METER, ELength.CHI_CHINESE, 1l, 3l),
      new TestCase(ELength.METER, ELength.CUN_CHINESE, 1l, 30l),
      new TestCase(ELength.METER, ELength.FEN_CHINESE, 1l, 300l),
      new TestCase(ELength.METER, ELength.LI_CHINESE_SMALL, 1l, 3000l),
      new TestCase(ELength.METER, ELength.HAO_CHINESE, 1l, 30000l),
      new TestCase(ELength.METER, ELength.SI_CHINESE, 1l, 300000l),
      new TestCase(ELength.METER, ELength.HU_CHINESE, 1l, 3000000l), };

  /** the constructor */
  public ELengthTest() {
    super();
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
   */
  private static final void __testConvert(final ELength from,
      final ELength to, final long fromValue, final long toValue) {
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
      throw new AssertionFailedError("Failed to convert '" + //$NON-NLS-1$
          fromValue + " " + from + //$NON-NLS-1$
          "' to '" + toValue + //$NON-NLS-1$
          " " + to + //$NON-NLS-1$
          (calc ? ("' - calculated '" + res + //$NON-NLS-1$
          "' instead.")//$NON-NLS-1$
              : ("got an error instead."))); //$NON-NLS-1$
    }

    calc = false;
    try {
      re = from.convertTo(((double) fromValue), to);
      calc = true;
    } catch (final Throwable xxx) {
      re = Double.NaN;
    }
    if (Math.abs(re - toValue) > 1e-9) {
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
          t.m_toValue);
      ELengthTest.__testConvert(t.m_to, t.m_from, t.m_toValue,
          t.m_fromValue);

      for (i = 100001; (--i) >= 0;) {
        f = (i - 50000);

        if (f == 0) {
          fv = tv = 0l;
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

        ELengthTest.__testConvert(t.m_from, t.m_to, fv, tv);
        ELengthTest.__testConvert(t.m_to, t.m_from, tv, fv);
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
      super();
      this.m_from = from;
      this.m_to = to;
      this.m_fromValue = fromValue;
      this.m_toValue = toValue;
    }
  }
}
