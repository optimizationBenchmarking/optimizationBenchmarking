package org.optimizationBenchmarking.utils.bibliography.data;

import java.util.Calendar;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A bibliography date whose {@link #hashCode() hash codes} also represent
 * the absolute date value in form of an integer number.
 */
public class BibDate extends _BibElement<BibDate> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the year
   *
   * @serial positive integer
   */
  final int m_year;

  /**
   * the bibliography month
   *
   * @serial serial field
   */
  final EBibMonth m_month;

  /**
   * the quarter
   *
   * @serial serial field
   */
  final EBibQuarter m_quarter;

  /**
   * the day
   *
   * @serial integer &ge; 0
   */
  final int m_day;

  /**
   * Create a new bib date
   *
   * @param year
   *          the year, -1 if undefined
   * @param month
   *          the month, {@code null} if undefined
   * @param quarter
   *          the quarter, {@code null} if undefined
   * @param day
   *          the day, -1 if undefined
   */
  BibDate(final int year, final EBibMonth month,
      final EBibQuarter quarter, final int day) {
    super();

    if (year <= 0) {
      throw new IllegalArgumentException(//
          "Year must not be less or equal to 0."); //$NON-NLS-1$
    }

    if ((month != null) && (quarter != null)) {
      throw new IllegalArgumentException(//
          "Month and quarter cannot be defined both."); //$NON-NLS-1$
    }

    if (day > 0) {
      if (month == null) {
        throw new IllegalArgumentException(//
            "If day is defined, month must also be defined."); //$NON-NLS-1$
      }
      if (day > month.m_days) {
        throw new IllegalArgumentException(//
            "Day is out of valid range."); //$NON-NLS-1$
      }
    }

    this.m_year = year;
    this.m_month = month;
    this.m_quarter = quarter;
    this.m_day = ((day > 0) ? day : (-1));

  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    int x;

    x = (397 * this.m_year);

    if (this.m_month != null) {
      x++;
      x += (33 * this.m_month.ordinal());
      if (this.m_day > 0) {
        x++;
        x += this.m_day;
      }
    } else {
      if (this.m_quarter != null) {
        x++;
        x += (99 * this.m_quarter.ordinal());
      }
    }

    return x;
  }

  /**
   * Create a bib date
   *
   * @param year
   *          the year, -1 if undefined
   * @param month
   *          the month, {@code null} if undefined
   * @param day
   *          the day, -1 if undefined
   */
  public BibDate(final int year, final EBibMonth month, final int day) {
    this(year, month, null, day);
  }

  /**
   * Create a bib date
   *
   * @param year
   *          the year, -1 if undefined
   * @param month
   *          the month, {@code null} if undefined
   */
  public BibDate(final int year, final EBibMonth month) {
    this(year, month, -1);
  }

  /**
   * Create a bib date
   *
   * @param year
   *          the year, -1 if undefined
   * @param quarter
   *          the quarter, {@code null} if undefined
   */
  public BibDate(final int year, final EBibQuarter quarter) {
    this(year, null, quarter, -1);
  }

  /**
   * Create a bib date
   *
   * @param year
   *          the year, -1 if undefined
   */
  public BibDate(final int year) {
    this(year, ((EBibQuarter) null));
  }

  /**
   * Store this date's info into a calendar object
   *
   * @param cal
   *          the calendar object to fill with this date's info
   */
  public final void toCalendar(final Calendar cal) {
    cal.clear();
    if (this.m_year > 0) {
      cal.set(Calendar.YEAR, this.m_year);

      if (this.m_month != null) {
        cal.set(Calendar.MONTH, this.m_month.ordinal());
        if (this.m_day > 0) {
          cal.set(Calendar.DAY_OF_MONTH, this.m_day);
        }
      } else {
        if (this.m_quarter != null) {
          cal.set(Calendar.MONTH, this.m_quarter.ordinal() * 3);
        }
      }
    }
  }

  /**
   * Get the year
   *
   * @return the year
   */
  public final int getYear() {
    return this.m_year;
  }

  /**
   * Get the month
   *
   * @return the month, {@code null} if undefined
   */
  public final EBibMonth getMonth() {
    return this.m_month;
  }

  /**
   * Get the quarter
   *
   * @return the quarter, {@code null} if undefined
   */
  public final EBibQuarter getQuarter() {
    return this.m_quarter;
  }

  /**
   * Get the day
   *
   * @return the day, {@code -1} if undefined
   */
  public final int getDay() {
    return this.m_day;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final BibDate x;

    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof BibDate) {
      x = ((BibDate) o);
      return ((this.m_year == x.m_year) && //
          EComparison.equals(this.m_quarter, x.m_quarter) && //
          EComparison.equals(this.m_month, x.m_month) && //
          (this.m_day == x.m_day));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final BibDate o) {
    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }
    return Integer.compare(this.hashCode(), o.hashCode());
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    final int i;

    textOut.append(this.m_year);
    if (this.m_month != null) {
      textOut.append('-');
      i = (this.m_month.ordinal() + 1);
      if (i < 10) {
        textOut.append('0');
      }
      textOut.append(i);
      if (this.m_day > 0) {
        textOut.append('-');
        textOut.append(this.m_day);
      }
    } else {
      if (this.m_quarter != null) {
        textOut.append('-');
        textOut.append(this.m_quarter.m_short);
      }
    }
  }
}
