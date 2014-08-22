package org.optimizationBenchmarking.utils.bibliography.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.parsers.IntParser;
import org.optimizationBenchmarking.utils.parsers.StringParser;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for date objects. */
public final class BibDateBuilder extends _BibBuilder<BibDate> {

  /** the year has been set */
  private static final int FLAG_YEAR_SET = (_BibBuilder.FLAG_FINALIZED << 1);
  /** the month has been set */
  private static final int FLAG_MONTH_SET = (BibDateBuilder.FLAG_YEAR_SET << 1);
  /** the quarter has been set */
  private static final int FLAG_QUARTER_SET = (BibDateBuilder.FLAG_MONTH_SET << 1);
  /** the day has been set */
  private static final int FLAG_DAY_SET = (BibDateBuilder.FLAG_QUARTER_SET << 1);

  /** the year */
  private int m_year;

  /** the bibliography month */
  private EBibMonth m_month;

  /** the quarter */
  private EBibQuarter m_quarter;

  /** the day */
  private int m_day;

  /** a mode indicating start or end dates */
  final int m_tag;

  /** create the date builder */
  public BibDateBuilder() {
    this(null);
  }

  /**
   * create the author builder
   * 
   * @param owner
   *          the owner
   */
  public BibDateBuilder(final HierarchicalFSM owner) {
    this(owner, 0);
  }

  /**
   * create the author builder
   * 
   * @param owner
   *          the owner
   * @param tag
   *          a tag
   */
  BibDateBuilder(final HierarchicalFSM owner, final int tag) {
    super(owner);
    this.m_year = (-1);
    this.m_day = (-1);
    this.m_tag = tag;
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_YEAR_SET: {
        append.append("yearSet");//$NON-NLS-1$
        return;
      }
      case FLAG_MONTH_SET: {
        append.append("monthSet");//$NON-NLS-1$
        return;
      }
      case FLAG_QUARTER_SET: {
        append.append("quarterSet");//$NON-NLS-1$
        return;
      }
      case FLAG_DAY_SET: {
        append.append("daySet");//$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Set the year
   * 
   * @param year
   *          the year
   */
  public synchronized final void setYear(final int year) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibDateBuilder.FLAG_YEAR_SET | _BibBuilder.FLAG_FINALIZED),
        BibDateBuilder.FLAG_YEAR_SET, FSM.FLAG_NOTHING);
    if (year <= 0) {
      throw new IllegalArgumentException("Year value " + year + //$NON-NLS-1$
          " is invalid, since years must be positive."); //$NON-NLS-1$
    }
    this.m_year = year;
  }

  /**
   * Set the year
   * 
   * @param year
   *          the year
   */
  public final void setYear(final String year) {
    this.setYear(IntParser.INSTANCE.parseInt(year));
  }

  /**
   * Set the month
   * 
   * @param month
   *          the month
   */
  public synchronized final void setMonth(final EBibMonth month) {
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (BibDateBuilder.FLAG_MONTH_SET | BibDateBuilder.FLAG_QUARTER_SET | _BibBuilder.FLAG_FINALIZED),
        BibDateBuilder.FLAG_MONTH_SET, FSM.FLAG_NOTHING);
    if (month == null) {
      throw new IllegalArgumentException(
          "Months must not be set to null. Maybe don't set it at all?"); //$NON-NLS-1$
    }
    this.m_month = month;
  }

  /**
   * Set the month
   * 
   * @param month
   *          the month
   */
  public final void setMonth(final String month) {
    final String m;
    EBibMonth res;

    m = StringParser.INSTANCE.parseString(month);
    if (m == null) {
      throw new IllegalArgumentException(//
          "Month strings must not be null or empty, but '" //$NON-NLS-1$
              + month + "' is.");//$NON-NLS-1$
    }

    res = null;
    finder: {
      for (final EBibMonth mon : EBibMonth.MONTHS) {
        if (m.equalsIgnoreCase(mon.m_short)) {
          res = mon;
          break finder;
        }
        if (m.equalsIgnoreCase(mon.m_full)) {
          res = mon;
          break finder;
        }
      }

      try {
        res = EBibMonth.MONTHS[IntParser.INSTANCE.parseInt(month)];
        break finder;
      } catch (final Throwable ignore) {
        // ignore
      }

      res = EBibMonth.valueOf(m);
    }

    this.setMonth(res);
  }

  /**
   * Set the quarter
   * 
   * @param quarter
   *          the quarter
   */
  public synchronized final void setQuarter(final EBibQuarter quarter) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibDateBuilder.FLAG_MONTH_SET | BibDateBuilder.FLAG_QUARTER_SET
            | BibDateBuilder.FLAG_DAY_SET | _BibBuilder.FLAG_FINALIZED),
        BibDateBuilder.FLAG_QUARTER_SET, FSM.FLAG_NOTHING);
    if (quarter == null) {
      throw new IllegalArgumentException(
          "Quarters must not be set to null. Maybe don't set it at all?"); //$NON-NLS-1$
    }
    this.m_quarter = quarter;
  }

  /**
   * Set the quarter
   * 
   * @param quarter
   *          the quarter
   */
  public final void setQuarter(final String quarter) {
    final String m;
    EBibQuarter res;

    m = StringParser.INSTANCE.parseString(quarter);
    if (m == null) {
      throw new IllegalArgumentException(//
          "Quarter strings must not be null or empty, but '" //$NON-NLS-1$
              + quarter + "' is.");//$NON-NLS-1$
    }

    res = null;
    finder: {
      for (final EBibQuarter mon : EBibQuarter.QUARTERS) {
        if (m.equalsIgnoreCase(mon.m_short)) {
          res = mon;
          break finder;
        }
        if (m.equalsIgnoreCase(mon.m_full)) {
          res = mon;
          break finder;
        }
      }

      try {
        res = EBibQuarter.QUARTERS[IntParser.INSTANCE.parseInt(quarter)];
        break finder;
      } catch (final Throwable ignore) {
        // ignore
      }

      res = EBibQuarter.valueOf(m);
    }

    this.setQuarter(res);
  }

  /**
   * Set the day
   * 
   * @param day
   *          the day
   */
  public synchronized final void setDay(final int day) {
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (BibDateBuilder.FLAG_DAY_SET | BibDateBuilder.FLAG_QUARTER_SET | _BibBuilder.FLAG_FINALIZED),
        BibDateBuilder.FLAG_DAY_SET, FSM.FLAG_NOTHING);
    if (day <= 0) {
      throw new IllegalArgumentException("Day value " + day + //$NON-NLS-1$
          " is invalid, since days must be positive."); //$NON-NLS-1$
    }
    this.m_day = day;
  }

  /**
   * Set the day
   * 
   * @param day
   *          the day
   */
  public final void setDay(final String day) {
    this.setDay(IntParser.INSTANCE.parseInt(day));
  }

  /**
   * Set the whole data from a given date object
   * 
   * @param date
   *          the date object
   */
  public final void fromDate(final Date date) {
    this.fromTime(date.getTime());
  }

  /**
   * Set the fields from a time stamp
   * 
   * @param time
   *          a time stamp
   */
  public final void fromTime(final long time) {
    final Calendar cal;

    cal = new GregorianCalendar();
    cal.setTimeInMillis(time);
    this.fromCalendar(cal);
  }

  /**
   * Initialize the date object with the current date
   */
  public final void fromNow() {
    this.fromTime(System.currentTimeMillis());
  }

  /**
   * Set the whole data from a given calendar
   * 
   * @param cal
   *          the calendar
   */
  public synchronized final void fromCalendar(final Calendar cal) {
    if (cal.isSet(Calendar.YEAR)) {
      this.setYear(cal.get(Calendar.YEAR));
    }
    if (cal.isSet(Calendar.MONTH)) {
      this.setMonth(EBibMonth.MONTHS[cal.get(Calendar.MONTH)]);
    }
    if (cal.isSet(Calendar.DAY_OF_MONTH)) {
      this.setDay(Calendar.DAY_OF_MONTH);
    }
  }

  /** {@inheritDoc} */
  @Override
  final BibDate _compile() {
    this.fsmFlagsAssertTrue(BibDateBuilder.FLAG_YEAR_SET);
    if (this.m_day > 0) {
      this.fsmFlagsAssertTrue(BibDateBuilder.FLAG_MONTH_SET);
    }

    return new BibDate(this.m_year, this.m_month, this.m_quarter,
        this.m_day);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);
    this.throwChildNotAllowed(child);
  }
}
