package org.optimizationBenchmarking.utils.math.units;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.math.Rational;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * Units of length and corresponding conversion factors.
 * </p>
 */
public enum ELength implements IUnit {

  /**
   * <a
   * href="http://en.wikipedia.org/wiki/Angstrom">&aring;ngstr&ouml;m</a>
   */
  ANGSTROM("\u00e5ngstr\u00f6m", "\u00c5"), //$NON-NLS-1$//$NON-NLS-2$
  /** <a href="http://en.wikipedia.org/wiki/Nanometre">nanometer</a> */
  NANOMETER("nanometer", "nm"), //$NON-NLS-1$//$NON-NLS-2$
  /** <a href="http://en.wikipedia.org/wiki/Micrometer">micrometer</a> */
  MICROMETER("micrometer", "\u00b5m"), //$NON-NLS-1$//$NON-NLS-2$
  /** <a href="http://en.wikipedia.org/wiki/Millimetre">millimeter</a> */
  MILLIMETER("millimeter", "mm"), //$NON-NLS-1$//$NON-NLS-2$
  /** <a href="http://en.wikipedia.org/wiki/Centimetre">centimeter</a> */
  CENTIMETER("centimeter", "cm"), //$NON-NLS-1$//$NON-NLS-2$
  /** <a href="http://en.wikipedia.org/wiki/Decimetre">decimeter</a> */
  DECIMETER("decimeter", "dm"), //$NON-NLS-1$//$NON-NLS-2$
  /** <a href="http://en.wikipedia.org/wiki/Metre">meter</a> */
  METER("meter", "m"), //$NON-NLS-1$//$NON-NLS-2$
  /** <a href="http://en.wikipedia.org/wiki/Kilometre">kilometer</a> */
  KILOMETER("kilometer", "km"), //$NON-NLS-1$//$NON-NLS-2$

  /**
   * <a href=
   * "http://en.wikipedia.org/wiki/Point_%28typography%29">point</a>
   * according to <a
   * href="http://en.wikipedia.org/wiki/PostScript">PostScript</a>
   * definition
   */
  POINT("point", "pt", "point (postscript)"), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  /**
   * <a href="http://www.giss.nasa.gov/tools/latex/ltx-86.html">Scaled
   * Point</a>
   */
  SCALED_POINT("scaled point", "sp"), //$NON-NLS-1$//$NON-NLS-2$
  /**
   * <a href="http://www.giss.nasa.gov/tools/latex/ltx-86.html">Big
   * point</a>
   */
  BIG_POINT("big point", "bp"), //$NON-NLS-1$//$NON-NLS-2$

  /** European/didot point */
  DIDOT("did\u00f4t", "dd", "point (European/Did\u00f4t)"), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  /** European/didot cicero */
  CICERO("c\u00eecero", "cc", "c\u00eecero (Europan/Didot)"), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  // /** pica */
  //  PICA("pica"), //$NON-NLS-1$
  /** postscript-pica */
  PICA("pica", "P\u0338", "pica (postscript)"), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  /** pixel */
  PIXEL("pixel", "px", "pixel (postscript)"), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  /** twip */
  TWIP("twip", null, "twip (postscript)"), //$NON-NLS-1$//$NON-NLS-2$

  /** micro inch */
  MICROINCH("microinch", "\u00b5in"), //$NON-NLS-1$//$NON-NLS-2$
  /** milliinch */
  MIL("mil"), //$NON-NLS-1$
  /** barleycorn */
  BARLEYCORN("barleycorn"), //$NON-NLS-1$
  /** inch */
  INCH("inch", "in"), //$NON-NLS-1$//$NON-NLS-2$
  /** nail */
  NAIL("nail"), //$NON-NLS-1$
  /** palm */
  PALM("palm"), //$NON-NLS-1$
  /** hand */
  HAND("hand"), //$NON-NLS-1$
  /** span */
  SPAN("span"), //$NON-NLS-1$
  /** line */
  LINE("line"), //$NON-NLS-1$
  /** foot */
  FOOT("foot", "ft"), //$NON-NLS-1$//$NON-NLS-2$
  /** cubit */
  CUBIT("cubit"), //$NON-NLS-1$
  /** yard */
  YARD("yard", "yd"), //$NON-NLS-1$//$NON-NLS-2$
  /** ell */
  ELL("ell"), //$NON-NLS-1$
  /** fathom */
  FATHOM("fathom"), //$NON-NLS-1$
  /** perch or rod */
  ROD("rod", "rd"), //$NON-NLS-1$//$NON-NLS-2$
  /** chain */
  CHAIN("chain"), //$NON-NLS-1$
  /** furlong */
  FURLONG("furlong"), //$NON-NLS-1$
  /** us survey foot */
  FOOT_US_SURVEY("survey foot", null, "US survey foot"), //$NON-NLS-1$//$NON-NLS-2$
  /** international mile */
  MILE_INTERNATIONAL("mile", null, "mile (international)"), //$NON-NLS-1$//$NON-NLS-2$
  /** league */
  LEAGUE("league"), //$NON-NLS-1$
  /** nautical mile */
  MILE_NAUTICAL("nautical mile"), //$NON-NLS-1$
  /** geographical mile */
  MILE_GEOGRAPHICAL("geographical mile", null, "German geographical mile"), //$NON-NLS-1$//$NON-NLS-2$

  /** the chinese li */
  LI_CHINESE("li", "\u5e02\u91cc"), //$NON-NLS-1$//$NON-NLS-2$
  /** the chinese yin */
  YIN_CHINESE("yin", "\u5f15"), //$NON-NLS-1$//$NON-NLS-2$
  /** the chinese zhang */
  ZHANG_CHINESE("zhang", "\u5e02\u4e08"), //$NON-NLS-1$//$NON-NLS-2$
  /** the chinese bu */
  BU_CHINESE("bu", "\u6b65"), //$NON-NLS-1$//$NON-NLS-2$
  /** the chinese chi */
  CHI_CHINESE("chi", "\u5e02\u5c3a"), //$NON-NLS-1$//$NON-NLS-2$
  /** the chinese cun */
  CUN_CHINESE("cun", "\u5e02\u5bf8"), //$NON-NLS-1$//$NON-NLS-2$
  /** the chinese fen */
  FEN_CHINESE("fen", "\u5e02\u5206"), //$NON-NLS-1$//$NON-NLS-2$
  /** the small chinese li */
  LI_CHINESE_SMALL("li(small)", "\u5e02\u5398", //$NON-NLS-1$//$NON-NLS-2$
      "small li"), //$NON-NLS-1$
  /** the chinese hao */
  HAO_CHINESE("hao", "\u6beb"), //$NON-NLS-1$//$NON-NLS-2$
  /** the chinese si */
  SI_CHINESE("si", "\u4e1d"), //$NON-NLS-1$//$NON-NLS-2$
  /** the chinese hu */
  HU_CHINESE("hu", "\u5ffd"), //$NON-NLS-1$//$NON-NLS-2$

  /** a light second */
  LIGHT_SECOND("light second"), //$NON-NLS-1$
  /** a light minute */
  LIGHT_MINUTE("light minute"), //$NON-NLS-1$
  /** an astronomical unit */
  ASTRONOMICAL_UNIT("astronomical unit", "AU"), //$NON-NLS-1$//$NON-NLS-2$
  /** a light year */
  LIGHT_YEAR("light year", "ly"), //$NON-NLS-1$//$NON-NLS-2$
  /** a parsec */
  PARSEC("parsec"), //$NON-NLS-1$
  /** red shift */
  RED_SHIFT("red shift", "z"), //$NON-NLS-1$//$NON-NLS-2$
  ;

  /** the nanometer */
  public static final ELength NM = NANOMETER;
  /** the micron */
  public static final ELength MICRON = MICROMETER;
  /** the micrometer */
  public static final ELength UM = MICROMETER;
  /** the millimeter */
  public static final ELength MM = MILLIMETER;
  /** the centimeter */
  public static final ELength CM = CENTIMETER;
  /** the deciimeter */
  public static final ELength DM = DECIMETER;
  /** the meter */
  public static final ELength M = METER;
  /** the kilometer */
  public static final ELength KM = KILOMETER;
  /** the inch */
  public static final ELength IN = INCH;
  /** the foot */
  public static final ELength FT = FOOT;
  /** the yard */
  public static final ELength YD = YARD;
  /** the points */
  public static final ELength PT = POINT;
  /** the scaled points */
  public static final ELength SP = SCALED_POINT;
  /** the big points */
  public static final ELength BP = BIG_POINT;
  /** the didot */
  public static final ELength DD = DIDOT;
  /** the cicero */
  public static final ELength CC = CICERO;
  /** the astronomical unit */
  public static final ELength AU = ASTRONOMICAL_UNIT;
  /** the light year */
  public static final ELength LY = LIGHT_YEAR;
  /** the perch */
  public static final ELength PERCH = ROD;
  /** the rod */
  public static final ELength RD = ROD;
  /** the diopter */
  public static final ELength DIOPTER = METER;
  /** the red shift */
  public static final ELength Z = RED_SHIFT;
  /** the ems/pica */
  public static final ELength EMS = PICA;

  /** the conversion table */
  private static final UnaryFunction[][] CONVERT;

  static {
    final int length;
    ConversionMatrixBuilder<ELength> comp;

    length = (RED_SHIFT.ordinal() + 1);
    comp = new ConversionMatrixBuilder<>(length);

    comp.setFactor(ELength.NANOMETER, ELength.ANGSTROM, 10L);
    comp.setFactor(ELength.KILOMETER, ELength.METER, 1000L);
    comp.setFactor(ELength.METER, ELength.DECIMETER, 10L);
    comp.setFactor(ELength.DECIMETER, ELength.CENTIMETER, 10L);
    comp.setFactor(ELength.CENTIMETER, ELength.MILLIMETER, 10L);
    comp.setFactor(ELength.MILLIMETER, ELength.MICROMETER, 1000L);
    comp.setFactor(ELength.MICROMETER, ELength.NANOMETER, 1000L);

    comp.setFactor(ELength.INCH, ELength.MIL, 1000L);
    comp.setFactor(ELength.LEAGUE, ELength.FURLONG, 24L);
    comp.setFactor(ELength.FATHOM, ELength.YARD, 2L);
    comp.setFactor(ELength.FURLONG, ELength.CHAIN, 10L);
    comp.setFactor(ELength.CHAIN, ELength.PERCH, 4L);
    comp.setFactor(ELength.PERCH, ELength.CUBIT, 11L);
    comp.setFactor(ELength.CUBIT, ELength.FOOT, Rational.valueOf(15L, 10L));
    comp.setFactor(ELength.ELL, ELength.FOOT, Rational.valueOf(375L, 100L));
    comp.setFactor(ELength.FOOT, ELength.INCH, 12L);
    comp.setFactor(ELength.FOOT, ELength.LINE, 144L);
    comp.setFactor(ELength.FOOT, ELength.BARLEYCORN, 36L);
    comp.setFactor(ELength.SPAN, ELength.NAIL, 4L);
    comp.setFactor(ELength.YARD, ELength.SPAN, 4L);
    comp.setFactor(ELength.FOOT, ELength.PALM, 4L);
    comp.setFactor(ELength.FOOT, ELength.HAND, 3L);
    comp.setFactor(ELength.YARD, ELength.FOOT, 3L);
    comp.setFactor(ELength.MIL, ELength.MICROINCH, 1000L);
    comp.setFactor(ELength.MILE_INTERNATIONAL, ELength.FURLONG, 8L);

    comp.setFactor(ELength.PICA, ELength.PIXEL, 16l);
    comp.setFactor(ELength.PICA, ELength.TWIP, 240L);
    comp.setFactor(ELength.CICERO, ELength.DIDOT, 12L);
    comp.setFactor(ELength.PICA, ELength.POINT, 12L);

    comp.setFactor(ELength.POINT, ELength.SCALED_POINT, 65536L);
    comp.setFactor(ELength.INCH, ELength.BIG_POINT, 72L);
    // comp.setFactor(ELength.DIDOT, ELength.POINT,
    // Rational.valueOf(1238L, 1157L));

    comp.setFactor(ELength.LIGHT_YEAR, ELength.PARSEC,
        Rational.valueOf(306601394L, 1000000000L));
    comp.setFactor(ELength.RED_SHIFT, ELength.PARSEC, 4222000000L);
    comp.setFactor(ELength.RED_SHIFT, ELength.LIGHT_YEAR, 13770000000L);

    comp.setFactor(ELength.LI_CHINESE, ELength.YIN_CHINESE, 15L);
    comp.setFactor(ELength.LI_CHINESE, ELength.ZHANG_CHINESE, 150L);
    comp.setFactor(ELength.LI_CHINESE, ELength.BU_CHINESE, 300L);
    comp.setFactor(ELength.LI_CHINESE, ELength.CHI_CHINESE, 1500L);
    comp.setFactor(ELength.LI_CHINESE, ELength.CUN_CHINESE, 15000L);
    comp.setFactor(ELength.LI_CHINESE, ELength.FEN_CHINESE, 150000L);
    comp.setFactor(ELength.LI_CHINESE, ELength.LI_CHINESE_SMALL, 1500000L);
    comp.setFactor(ELength.LI_CHINESE, ELength.HAO_CHINESE, 15000000L);
    comp.setFactor(ELength.LI_CHINESE, ELength.SI_CHINESE, 150000000L);
    comp.setFactor(ELength.LI_CHINESE, ELength.HU_CHINESE, 1500000000L);

    // comp.setFactor(ELength.POINT, ELength.MILLIMETER,
    // Rational.valueOf(35146L, 100000L));
    // comp.setFactor(ELength.INCH, ELength.POINT, 72);

    comp.setFactor(ELength.FOOT, ELength.METER,
        Rational.valueOf(3048L, 10000L));
    comp.setFactor(ELength.FOOT_US_SURVEY, ELength.METER,
        Rational.valueOf(3048006L, 10000000L));
    comp.setFactor(ELength.INCH, ELength.CENTIMETER,
        Rational.valueOf(254L, 100L));
    comp.setFactor(ELength.YARD, ELength.METER,
        Rational.valueOf(9144L, 10000L));
    //
    comp.setFactor(ELength.DIDOT, ELength.MILLIMETER,
        Rational.valueOf(376L, 1000L));
    comp.setFactor(ELength.INCH, ELength.POINT, 72);
    //
    comp.setFactor(ELength.MILE_INTERNATIONAL, ELength.KILOMETER,
        Rational.valueOf(1609344L, 1000000L));
    comp.setFactor(ELength.MILE_NAUTICAL, ELength.KILOMETER,
        Rational.valueOf(1852L, 1000L));
    comp.setFactor(ELength.MILE_GEOGRAPHICAL, ELength.KILOMETER,
        Rational.valueOf(742L, 100L));
    comp.setFactor(ELength.FATHOM, ELength.METER,
        Rational.valueOf(1828804L, 1000000L));
    //
    comp.setFactor(ELength.LIGHT_SECOND, ELength.ANGSTROM,
        2997924580000000000L);
    comp.setFactor(ELength.LIGHT_SECOND, ELength.NANOMETER,
        299792458000000000L);
    comp.setFactor(ELength.LIGHT_SECOND, ELength.MICROMETER,
        299792458000000L);
    comp.setFactor(ELength.LIGHT_SECOND, ELength.MILLIMETER, 299792458000L);
    comp.setFactor(ELength.LIGHT_SECOND, ELength.CENTIMETER, 29979245800L);
    comp.setFactor(ELength.LIGHT_SECOND, ELength.DECIMETER, 2997924580L);
    comp.setFactor(ELength.LIGHT_SECOND, ELength.METER, 299792458L);
    comp.setFactor(ELength.LIGHT_SECOND, ELength.KILOMETER,
        Rational.valueOf(299792458L, 1000L));
    comp.setFactor(ELength.LIGHT_MINUTE, ELength.LIGHT_SECOND, 60L);
    comp.setFactor(ELength.LIGHT_MINUTE, ELength.MICROMETER,
        17987547480000000L);
    comp.setFactor(ELength.LIGHT_MINUTE, ELength.MILLIMETER,
        17987547480000L);
    comp.setFactor(ELength.LIGHT_MINUTE, ELength.CENTIMETER,
        1798754748000L);
    comp.setFactor(ELength.LIGHT_MINUTE, ELength.DECIMETER, 179875474800L);
    comp.setFactor(ELength.LIGHT_MINUTE, ELength.METER, 17987547480L);
    comp.setFactor(ELength.LIGHT_MINUTE, ELength.KILOMETER,
        Rational.valueOf(17987547480L, 1000L));
    comp.setFactor(ELength.LIGHT_YEAR, ELength.CENTIMETER,
        946073047258080000L);
    comp.setFactor(ELength.LIGHT_YEAR, ELength.DECIMETER,
        94607304725808000L);
    comp.setFactor(ELength.LIGHT_YEAR, ELength.METER, 9460730472580800L);
    comp.setFactor(ELength.LIGHT_YEAR, ELength.KILOMETER,
        Rational.valueOf(94607304725808L, 10L));
    comp.setFactor(ELength.ASTRONOMICAL_UNIT, ELength.MICROMETER,
        149597870700000000L);
    comp.setFactor(ELength.ASTRONOMICAL_UNIT, ELength.MILLIMETER,
        149597870700000L);
    comp.setFactor(ELength.ASTRONOMICAL_UNIT, ELength.CENTIMETER,
        14959787070000L);
    comp.setFactor(ELength.ASTRONOMICAL_UNIT, ELength.DECIMETER,
        1495978707000L);
    comp.setFactor(ELength.ASTRONOMICAL_UNIT, ELength.METER, 149597870700L);
    comp.setFactor(ELength.ASTRONOMICAL_UNIT, ELength.KILOMETER,
        Rational.valueOf(149597870700L, 1000L));
    //
    comp.setFactor(ELength.METER, ELength.YIN_CHINESE,
        Rational.valueOf(3L, 100L));
    comp.setFactor(ELength.METER, ELength.ZHANG_CHINESE,
        Rational.valueOf(3L, 10L));
    comp.setFactor(ELength.METER, ELength.BU_CHINESE,
        Rational.valueOf(6L, 10L));
    comp.setFactor(ELength.METER, ELength.CHI_CHINESE, 3L);
    comp.setFactor(ELength.METER, ELength.CUN_CHINESE, 30L);
    comp.setFactor(ELength.METER, ELength.FEN_CHINESE, 300L);
    comp.setFactor(ELength.METER, ELength.LI_CHINESE_SMALL, 3000L);
    comp.setFactor(ELength.METER, ELength.HAO_CHINESE, 30000L);
    comp.setFactor(ELength.METER, ELength.SI_CHINESE, 300000L);
    comp.setFactor(ELength.METER, ELength.HU_CHINESE, 3000000L);

    CONVERT = comp.compile();
  }

  /** the instances */
  public static final ArraySetView<ELength> INSTANCES = new ArraySetView<>(
      ELength.values());

  /** the name */
  private final String m_name;
  /** the long name */
  private final String m_longName;
  /** the shortcut */
  private final String m_shortcut;

  /**
   * Create
   *
   * @param name
   *          the name
   * @param shortcut
   *          the shortcut
   * @param longName
   *          the long name
   */
  private ELength(final String name, final String shortcut,
      final String longName) {
    this.m_name = TextUtils.prepare(name);
    this.m_longName = TextUtils.prepare(longName);
    this.m_shortcut = TextUtils.prepare(shortcut);
  }

  /**
   * Create
   *
   * @param name
   *          the name
   * @param shortcut
   *          the shortcut
   */
  private ELength(final String name, final String shortcut) {
    this(name, shortcut, null);
  }

  /**
   * Create the name
   *
   * @param name
   *          the name
   */
  private ELength(final String name) {
    this(name, null);
  }

  /**
   * Get the conversion function
   *
   * @param other
   *          the other unit
   * @return the function
   */
  public final UnaryFunction getConversion(final ELength other) {
    if (other != null) {
      return ELength.CONVERT[this.ordinal()][other.ordinal()];
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getConversion(final IUnit other) {
    if (other instanceof ELength) {
      return ELength.CONVERT[this.ordinal()][((ELength) other).ordinal()];
    }
    return null;
  }

  /**
   * Convert a value
   *
   * @param value
   *          the value
   * @param other
   *          the other unit
   * @return the result
   */
  public final long convertTo(final long value, final ELength other) {
    UnaryFunction f;

    if (other != null) {
      f = ELength.CONVERT[this.ordinal()][other.ordinal()];
      if (f != null) {
        return f.computeAsLong(value);
      }
    }
    throw new IllegalArgumentException();
  }

  /** {@inheritDoc} */
  @Override
  public final long convertTo(final long value, final IUnit other) {
    if (other instanceof ELength) {
      return this.convertTo(value, ((ELength) other));
    }
    throw new IllegalArgumentException();
  }

  /**
   * Convert a value
   *
   * @param value
   *          the value
   * @param other
   *          the other unit
   * @return the result
   */
  public final double convertTo(final double value, final ELength other) {
    UnaryFunction f;

    if (other != null) {
      f = ELength.CONVERT[this.ordinal()][other.ordinal()];
      if (f != null) {
        return f.computeAsDouble(value);
      }
    }
    throw new IllegalArgumentException();
  }

  /** {@inheritDoc} */
  @Override
  public final double convertTo(final double value, final IUnit other) {
    if (other instanceof ELength) {
      return this.convertTo(value, ((ELength) other));
    }
    throw new IllegalArgumentException();
  }

  /** {@inheritDoc} */
  @Override
  public final String getLongName() {
    return ((this.m_longName != null) ? this.m_longName
        : ((this.m_name != null) ? this.m_name : this.m_shortcut));
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return ((this.m_name != null) ? this.m_name
        : ((this.m_longName != null) ? this.m_longName : this.m_shortcut));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.getName();
  }

  /** {@inheritDoc} */
  @Override
  public final String getShortcut() {
    return ((this.m_shortcut != null) ? this.m_shortcut
        : ((this.m_name != null) ? this.m_name : this.m_longName));
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensions getDimension() {
    return EDimensions.LENGTH;
  }
}
