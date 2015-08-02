package org.optimizationBenchmarking.utils.text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;

/** A set of simple, quick-to-use data formats */
public enum ESimpleDateFormat {

  /** the date in {@code yyyy-MMM-dd} format */
  DATE("yyyy-MM-dd"), //$NON-NLS-1$

  /** the date and time in {@code yyyy-MMM-dd hh:mm:ss} format */
  DATE_TIME("yyyy-MM-dd hh:mm:ss"); //$NON-NLS-1$

  /** the format string */
  private final String m_format;

  /**
   * create the simple date format
   *
   * @param format
   *          the format
   */
  ESimpleDateFormat(final String format) {
    this.m_format = format;
  }

  /**
   * Get the format string
   *
   * @return the format string
   */
  public final String getFormatString() {
    return this.m_format;
  }

  /**
   * Format a time stamp
   *
   * @param time
   *          the time, as obtained from
   *          {@link java.lang.System#currentTimeMillis()}
   * @return the formatter time string
   */
  public final String format(final long time) {
    final __Formatter formatter;

    formatter = this.__getFormatter();
    formatter.m_date.setTime(time);
    return formatter.m_formatter.format(formatter.m_date);
  }

  /**
   * Format the current time
   *
   * @return the formatted current time
   */
  public final String formatNow() {
    return this.format(System.currentTimeMillis());
  }

  /**
   * Get the formatter for this simple data format. This instance must not
   * be stored anywhere. It must only be used in the scope of the calling
   * function and in the current thread.
   *
   * @return the formatter for this simple data format
   */
  private final __Formatter __getFormatter() {
    final EnumMap<ESimpleDateFormat, __Formatter> map;
    __Formatter form;

    map = __ThreadLocal.INSTANCE.get();
    form = map.get(this);
    if (form == null) {
      form = new __Formatter(new SimpleDateFormat(this.m_format));
      map.put(this, form);
    }

    return form;
  }

  /** the internal thread local */
  private static final class __ThreadLocal extends
      ThreadLocal<EnumMap<ESimpleDateFormat, __Formatter>> {

    /** the thread local */
    static final __ThreadLocal INSTANCE = new __ThreadLocal();

    /** create */
    private __ThreadLocal() {
      super();
    }

    /**
     * create the enum map
     *
     * @return the initial enum map
     */
    @Override
    protected final EnumMap<ESimpleDateFormat, __Formatter> initialValue() {
      return new EnumMap<>(ESimpleDateFormat.class);
    }
  }

  /** the internal formatter class */
  private static final class __Formatter {
    /** the internal date record */
    final Date m_date;

    /** the formatter */
    final SimpleDateFormat m_formatter;

    /**
     * create
     *
     * @param formatter
     *          the formatter
     */
    __Formatter(final SimpleDateFormat formatter) {
      super();
      this.m_date = new Date();
      this.m_formatter = formatter;
    }

  }
}
