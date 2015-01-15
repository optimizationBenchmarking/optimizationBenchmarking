package org.optimizationBenchmarking.utils.math.units;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.math.Rational;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * Units of time and corresponding conversion factors according.
 * </p>
 * <@citations/>
 */
public enum ETime implements IUnit {

  /** planck time unit */
  PLANCK_TIME_UNIT("Planck time unit", "tp"), //$NON-NLS-1$//$NON-NLS-2$
  /** yoctosecond */
  YOCTOSECOND("yoctosecond", "ys"), //$NON-NLS-1$//$NON-NLS-2$
  /** jiffy */
  JIFFY_PHYSICS("jiffy (physics)"), //$NON-NLS-1$
  /** femtosecond */
  ZEPTOSECOND("zeptosecond", "zs"), //$NON-NLS-1$//$NON-NLS-2$
  /** attosecond */
  ATTOSECOND("attosecond", "as"), //$NON-NLS-1$//$NON-NLS-2$
  /** femtosecond */
  FEMTOSECOND("femtosecond", "fs"), //$NON-NLS-1$//$NON-NLS-2$
  /** picosecond */
  PICOSECOND("picosecond", "ps"), //$NON-NLS-1$//$NON-NLS-2$
  /** nanosecond */
  NANOSECOND("nanosecond", "ns"), //$NON-NLS-1$//$NON-NLS-2$
  /** microsecond */
  MICROSECOND("microsecond", "µs"), //$NON-NLS-1$//$NON-NLS-2$
  /** shake */
  SHAKE("shake"), //$NON-NLS-1$
  /** fourth */
  FOURTH("fourth"), //$NON-NLS-1$
  /** millisecond */
  MILLISECOND("millisecond", "ms"), //$NON-NLS-1$//$NON-NLS-2$
  /** centisecond */
  CENTISECOND("centisecond", "cs"), //$NON-NLS-1$//$NON-NLS-2$
  /** third */
  THIRD("thid"), //$NON-NLS-1$
  /** decisecond */
  DECISECOND("decisecond", "ds"), //$NON-NLS-1$//$NON-NLS-2$
  /** second */
  SECOND("second", "s"), //$NON-NLS-1$//$NON-NLS-2$
  /** dekasecond */
  DEKASECOND("dekasecond", "Ds"), //$NON-NLS-1$//$NON-NLS-2$
  /** minute */
  MINUTE("minute", "min"), //$NON-NLS-1$//$NON-NLS-2$
  /** moment */
  MOMENT("moment"), //$NON-NLS-1$
  /** hectosecond */
  HECTOSECOND("hectosecond", "hs"), //$NON-NLS-1$//$NON-NLS-2$
  /** ke */
  KE("ke"), //$NON-NLS-1$
  /** kilosecond */
  KILOSECOND("kilosecond", "ks"), //$NON-NLS-1$//$NON-NLS-2$
  /** ke */
  KE_CHINESE_MODERN("ke", "\u523B"), //$NON-NLS-1$//$NON-NLS-2$
  /** ke */
  KE_CHINESE_TRADITIONAL("ke (traditional)"), //$NON-NLS-1$
  /** hour */
  HOUR("hour", "h"), //$NON-NLS-1$//$NON-NLS-2$
  /** shichen */
  SHICHEN_CHINESE("shichen", "\u65F6\u8FB0"), //$NON-NLS-1$//$NON-NLS-2$
  /** day */
  DAY("day", "d"), //$NON-NLS-1$//$NON-NLS-2$
  /** week */
  WEEK("week", "wk"), //$NON-NLS-1$//$NON-NLS-2$
  /** fortnight */
  FORTNIGHT("fortnight"), //$NON-NLS-1$

  /** SI year year */
  YEAR_ANNUS("annus", "a"), //$NON-NLS-1$//$NON-NLS-2$
  /** default (Julian) year */
  YEAR_JULIAN("year", "aj", "Julian Year"), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$

  /** sideral year */
  YEAR_SIDERAL("sideral year"), //$NON-NLS-1$

  /** tropical year */
  YEAR_TROPICAL("tropical year", "at"), //$NON-NLS-1$//$NON-NLS-2$

  /** anomalistic year */
  YEAR_ANOMALISTIC("sideral year"), //$NON-NLS-1$
  /** draconic year */
  YEAR_DRACONIC("draconic year"), //$NON-NLS-1$
  /** full moon cycle */
  FULL_MOON_CYCLE("full moon cycle"), //$NON-NLS-1$
  /** moon year */
  YEAR_LUNAR("lunar year"), //$NON-NLS-1$
  /** Gaussian year */
  YEAR_GAUSSIAN("Gaussian year"), //$NON-NLS-1$

  /** 2 years */
  BIENNIUM("biennium"), //$NON-NLS-1$
  /** 3 years */
  TRIENNIUM("triennium"), //$NON-NLS-1$
  /** 4 years */
  OLYMPIADE("Olympiade"), //$NON-NLS-1$
  /** 5 years */
  LUSTRUM("lustrum"), //$NON-NLS-1$
  /** 10 years */
  DECADE("decade"), //$NON-NLS-1$
  /** 15 years */
  INDICTION("indiction"), //$NON-NLS-1$
  /** 20 years */
  SCORE("score"), //$NON-NLS-1$
  /** 50 years */
  JUBILEE("jubilee"), //$NON-NLS-1$
  /** 100 years */
  CENTURY("century"), //$NON-NLS-1$
  /** 1000 years */
  MILLENIUM("millenium"), //$NON-NLS-1$

  /** 1000 years */
  KILOANNUS("kiloannus", "ka"), //$NON-NLS-1$//$NON-NLS-2$
  /** 1000000 years */
  MEGAANNUS("megaannus", "Ma"), //$NON-NLS-1$//$NON-NLS-2$
  /** 1000000000 years */
  GIGAANNUS("gigaannus", "Ga"), //$NON-NLS-1$//$NON-NLS-2$
  /** 1000000000000 years */
  TERRAANNUS("terraannus", "Ta"), //$NON-NLS-1$//$NON-NLS-2$
  /** 1000000000000000 years */
  PETAANNUS("petaannus", "Pa"), //$NON-NLS-1$//$NON-NLS-2$
  /** 1000000000000000000 years */
  EXAANNUS("exaannus", "Ea"), //$NON-NLS-1$//$NON-NLS-2$

  /** time since moon landing in days */
  TIME_SINCE_MOON_LANDING("time since moon landing"), //$NON-NLS-1$
  /** time since the death of julius ceasar */
  TIME_SINCE_DEATH_OF_CEASAR("time since death of ceasar"), //$NON-NLS-1$
  /** time since the cheop's pyramid was built */
  TIME_SINCE_BUILDING_OF_CHEOPS_PYRAMID(
      "time since building of cheop's pyramid"), //$NON-NLS-1$
  /** time since the big bang */
  TIME_SINCE_BIG_BANG("time since the big bang"), //$NON-NLS-1$
  /** time since the extinction of the dinosaurs */
  TIME_SINCE_EXTINCTION_OF_DINOSAURS(
      "time since the extinction of the dinosaurs"), //$NON-NLS-1$
  ;

  /** the default year */
  public static final ETime YEAR = YEAR_JULIAN;
  /** the SI year */
  public static final ETime YEAR_SI = YEAR_ANNUS;
  /** the annus year */
  public static final ETime ANNUS = YEAR_ANNUS;

  /** the conversion table */
  private static final UnaryFunction[][] CONVERT;

  static {
    final int length;
    final long time, msPerDay;
    ConversionMatrixBuilder<ETime> comp;
    Calendar cal;

    length = (TIME_SINCE_EXTINCTION_OF_DINOSAURS.ordinal() + 1);
    comp = new ConversionMatrixBuilder<>(length);

    comp.setFactor(ETime.PLANCK_TIME_UNIT, ETime.SECOND, 5.39e-44d);
    comp.setFactor(ETime.YOCTOSECOND, ETime.JIFFY_PHYSICS, 3L);
    comp.setFactor(ETime.ZEPTOSECOND, ETime.YOCTOSECOND, 1000L);
    comp.setFactor(ETime.ATTOSECOND, ETime.ZEPTOSECOND, 1000L);
    comp.setFactor(ETime.FEMTOSECOND, ETime.ATTOSECOND, 1000L);
    comp.setFactor(ETime.PICOSECOND, ETime.FEMTOSECOND, 1000L);
    comp.setFactor(ETime.NANOSECOND, ETime.PICOSECOND, 1000L);
    comp.setFactor(ETime.SHAKE, ETime.NANOSECOND, 10L);
    comp.setFactor(ETime.MICROSECOND, ETime.NANOSECOND, 1000L);
    comp.setFactor(ETime.SECOND, ETime.FOURTH, 3600L);
    comp.setFactor(ETime.DEKASECOND, ETime.SECOND, 10L);
    comp.setFactor(ETime.MILLISECOND, ETime.MICROSECOND, 1000L);
    comp.setFactor(ETime.SECOND, ETime.CENTISECOND, 100L);
    comp.setFactor(ETime.SECOND, ETime.DECISECOND, 10L);
    comp.setFactor(ETime.SECOND, ETime.THIRD, 60L);
    comp.setFactor(ETime.SECOND, ETime.MILLISECOND, 1000L);
    comp.setFactor(ETime.MINUTE, ETime.SECOND, 60L);
    comp.setFactor(ETime.MOMENT, ETime.SECOND, 90L);
    comp.setFactor(ETime.HECTOSECOND, ETime.SECOND, 100L);
    comp.setFactor(ETime.KE, ETime.SECOND, ((14L * 60L) + 24L));
    comp.setFactor(ETime.KILOSECOND, ETime.SECOND, 1000L);
    comp.setFactor(ETime.KE_CHINESE_MODERN, ETime.MINUTE, 15L);
    comp.setFactor(ETime.KE_CHINESE_TRADITIONAL, ETime.MINUTE, 30L);
    comp.setFactor(ETime.HOUR, ETime.MINUTE, 60L);
    comp.setFactor(ETime.SHICHEN_CHINESE, ETime.HOUR, 2L);
    comp.setFactor(ETime.DAY, ETime.HOUR, 24L);
    comp.setFactor(ETime.DAY, ETime.MILLISECOND,//
        (msPerDay = (24L * 60L * 60L * 1000L)));
    comp.setFactor(ETime.WEEK, ETime.DAY, 7L);
    comp.setFactor(ETime.FORTNIGHT, ETime.WEEK, 2L);

    comp.setFactor(ETime.YEAR_JULIAN, ETime.SECOND, 31557600L);

    comp.setFactor(ETime.YEAR_SIDERAL, ETime.DAY,
        Rational.valueOf(365256363004L, 1000000000L));
    comp.setFactor(ETime.YEAR_TROPICAL, ETime.DAY,
        Rational.valueOf(36524219L, 100000L));
    comp.setFactor(ETime.YEAR_ANOMALISTIC, ETime.DAY,
        Rational.valueOf(365259636L, 1000000L));
    comp.setFactor(ETime.YEAR_DRACONIC, ETime.DAY,
        Rational.valueOf(346620075883L, 1000000000L));
    comp.setFactor(ETime.FULL_MOON_CYCLE, ETime.DAY,
        Rational.valueOf(41178443029L, 100000000L));
    comp.setFactor(ETime.YEAR_LUNAR, ETime.DAY,
        Rational.valueOf(35437L, 100L));
    comp.setFactor(ETime.YEAR_GAUSSIAN, ETime.DAY,
        Rational.valueOf(3652568983L, 10000000L));
    comp.setFactor(ETime.YEAR_ANNUS, ETime.DAY,
        Rational.valueOf(36524219265L, 100000000L));
    comp.setFactor(ETime.YEAR_ANNUS, ETime.SECOND,
        Rational.valueOf(31556925445L, 1000L));

    comp.setFactor(ETime.BIENNIUM, ETime.YEAR_JULIAN, 2L);
    comp.setFactor(ETime.TRIENNIUM, ETime.YEAR_JULIAN, 3L);
    comp.setFactor(ETime.OLYMPIADE, ETime.YEAR_JULIAN, 4L);
    comp.setFactor(ETime.LUSTRUM, ETime.YEAR_JULIAN, 5L);
    comp.setFactor(ETime.DECADE, ETime.YEAR_JULIAN, 10L);
    comp.setFactor(ETime.INDICTION, ETime.YEAR_JULIAN, 15L);
    comp.setFactor(ETime.SCORE, ETime.YEAR_JULIAN, 20L);
    comp.setFactor(ETime.JUBILEE, ETime.YEAR_JULIAN, 50L);
    comp.setFactor(ETime.CENTURY, ETime.YEAR_JULIAN, 100L);
    comp.setFactor(ETime.MILLENIUM, ETime.YEAR_JULIAN, 1000L);

    comp.setFactor(ETime.KILOANNUS, ETime.YEAR_ANNUS, 1000L);
    comp.setFactor(ETime.MEGAANNUS, ETime.KILOANNUS, 1000L);
    comp.setFactor(ETime.GIGAANNUS, ETime.MEGAANNUS, 1000L);
    comp.setFactor(ETime.TERRAANNUS, ETime.GIGAANNUS, 1000L);
    comp.setFactor(ETime.PETAANNUS, ETime.TERRAANNUS, 1000L);
    comp.setFactor(ETime.EXAANNUS, ETime.PETAANNUS, 1000L);

    time = System.currentTimeMillis();
    cal = new GregorianCalendar(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
    cal.set(Calendar.YEAR, 1969);
    cal.set(Calendar.MONTH, Calendar.JULY);
    cal.set(Calendar.DAY_OF_MONTH, 20);
    cal.set(Calendar.HOUR, 20);
    cal.set(Calendar.MINUTE, 17);
    cal.set(Calendar.SECOND, 40);

    comp.setFactor(ETime.TIME_SINCE_MOON_LANDING, ETime.DAY,//
        Rational.valueOf((time - cal.getTimeInMillis()), msPerDay));

    cal.clear();
    cal.set(Calendar.YEAR, -44);
    cal.set(Calendar.MONTH, Calendar.MARCH);
    cal.set(Calendar.DAY_OF_MONTH, 15);
    comp.setFactor(ETime.TIME_SINCE_DEATH_OF_CEASAR, ETime.DAY,//
        Rational.valueOf((time - cal.getTimeInMillis()), msPerDay));

    cal.clear();
    cal.setTimeInMillis(time);
    comp.setFactor(ETime.TIME_SINCE_BUILDING_OF_CHEOPS_PYRAMID,
        ETime.YEAR_JULIAN,//
        cal.get(Calendar.YEAR) + 2540);

    comp.setFactor(ETime.TIME_SINCE_BIG_BANG, ETime.ANNUS, 13820000000L);

    comp.setFactor(ETime.TIME_SINCE_EXTINCTION_OF_DINOSAURS, ETime.ANNUS,
        66043000L);

    CONVERT = comp.compile();
  }

  /** the instances */
  public static final ArraySetView<ETime> INSTANCES = new ArraySetView<>(
      ETime.values());

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
  private ETime(final String name, final String shortcut,
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
  private ETime(final String name, final String shortcut) {
    this(name, shortcut, null);
  }

  /**
   * Create the name
   * 
   * @param name
   *          the name
   */
  private ETime(final String name) {
    this(name, null);
  }

  /**
   * Get the conversion function
   * 
   * @param other
   *          the other unit
   * @return the function
   */
  public final UnaryFunction getConversion(final ETime other) {
    if (other != null) {
      return ETime.CONVERT[this.ordinal()][other.ordinal()];
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getConversion(final IUnit other) {
    if (other instanceof ETime) {
      return ETime.CONVERT[this.ordinal()][((ETime) other).ordinal()];
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
  public final long convertTo(final long value, final ETime other) {
    UnaryFunction f;

    if (other != null) {
      f = ETime.CONVERT[this.ordinal()][other.ordinal()];
      if (f != null) {
        return f.computeAsLong(value);
      }
    }
    throw new IllegalArgumentException();
  }

  /** {@inheritDoc} */
  @Override
  public final long convertTo(final long value, final IUnit other) {
    if (other instanceof ETime) {
      return this.convertTo(value, ((ETime) other));
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
  public final double convertTo(final double value, final ETime other) {
    UnaryFunction f;

    if (other != null) {
      f = ETime.CONVERT[this.ordinal()][other.ordinal()];
      if (f != null) {
        return f.computeAsDouble(value);
      }
    }
    throw new IllegalArgumentException();
  }

  /** {@inheritDoc} */
  @Override
  public final double convertTo(final double value, final IUnit other) {
    if (other instanceof ETime) {
      return this.convertTo(value, ((ETime) other));
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
    return EDimensions.TIME;
  }

  /**
   * Return the java time unit belonging to this time unit, if any
   * 
   * @return the corresponding java time unit, or {@code null} if none
   *         found
   */
  public final TimeUnit toJavaTimeUnit() {
    switch (this) {
      case NANOSECOND: {
        return TimeUnit.NANOSECONDS;
      }
      case MICROSECOND: {
        return TimeUnit.MICROSECONDS;
      }
      case MILLISECOND: {
        return TimeUnit.MILLISECONDS;
      }
      case SECOND: {
        return TimeUnit.SECONDS;
      }
      case MINUTE: {
        return TimeUnit.MINUTES;
      }
      case HOUR: {
        return TimeUnit.HOURS;
      }
      case DAY: {
        return TimeUnit.DAYS;
      }
      default: {
        return null;
      }
    }
  }
}
