package org.optimizationBenchmarking.utils.text.charset;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** A collection of quotes. */
public final class QuotationMarks extends _Enclosure<QuotationMark> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  static {
    // make sure that the character set is initialized first
    if (Characters.CHARACTERS != null) {
      Characters.CHARACTERS.isEmpty();
    }
  }

  /** double quotes */
  public static final QuotationMarks DEFAULT_DOUBLE = new QuotationMarks(
      Characters.QUOTE_DEFAULT_DOUBLE_OPEN,
      Characters.QUOTE_DEFAULT_DOUBLE_CLOSE, 2);

  /** single quotes */
  public static final QuotationMarks DEFAULT_SINGLE = new QuotationMarks(
      Characters.QUOTE_DEFAULT_SINGLE_OPEN,
      Characters.QUOTE_DEFAULT_SINGLE_CLOSE, 1);

  /** triple quotes */
  public static final QuotationMarks DEFAULT_TRIPLE = new QuotationMarks(
      Characters.QUOTE_DEFAULT_TRIPLE_OPEN,
      Characters.QUOTE_DEFAULT_TRIPLE_CLOSE, 3);

  /** alternative single quotes */
  public static final QuotationMarks ALT_SINGLE = new QuotationMarks(
      Characters.QUOTE_ALT_SINGLE_OPEN, Characters.QUOTE_ALT_SINGLE_CLOSE,
      1);

  /** alternative double quotes */
  public static final QuotationMarks ALT_DOUBLE = new QuotationMarks(
      Characters.QUOTE_ALT_DOUBLE_OPEN, Characters.QUOTE_ALT_DOUBLE_CLOSE,
      2);

  /** primitive double quotes */
  public static final QuotationMarks PRIMITIVE_DOUBLE = new QuotationMarks(
      Characters.QUOTE_PRIMITIVE_DOUBLE,
      Characters.QUOTE_PRIMITIVE_DOUBLE, 2);

  /** primitive single quotes */
  public static final QuotationMarks PRIMITIVE_SINGLE = new QuotationMarks(
      Characters.QUOTE_PRIMITIVE_SINGLE,
      Characters.QUOTE_PRIMITIVE_SINGLE, 1);

  /** double angle quotes */
  public static final QuotationMarks ANGLE_DOUBLE = new QuotationMarks(
      Characters.QUOTE_ANGLE_DOUBLE_OPEN,
      Characters.QUOTE_ANGLE_DOUBLE_CLOSE, 2);

  /** single angle quotes */
  public static final QuotationMarks ANGLE_SINGLE = new QuotationMarks(
      Characters.QUOTE_ANGLE_SINGLE_OPEN,
      Characters.QUOTE_ANGLE_SINGLE_CLOSE, 1);

  /** single Chinese angle quotes */
  public static final QuotationMarks ANGLE_CHINESE_SINGLE = new QuotationMarks(
      Characters.QUOTE_ANGLE_CHINESE_SINGLE_OPEN,
      Characters.QUOTE_ANGLE_CHINESE_SINGLE_CLOSE, 1);

  /** double Chinese angle quotes */
  public static final QuotationMarks ANGLE_CHINESE_DOUBLE = new QuotationMarks(
      Characters.QUOTE_ANGLE_CHINESE_DOUBLE_OPEN,
      Characters.QUOTE_ANGLE_CHINESE_DOUBLE_CLOSE, 2);

  /** single Chinese bracket quotes */
  public static final QuotationMarks BRACKET_CHINESE_SINGLE = new QuotationMarks(
      Characters.QUOTE_BRACKET_CHINESE_SINGLE_OPEN,
      Characters.QUOTE_BRACKET_CHINESE_SINGLE_CLOSE, 1);

  /** double Chinese bracket quotes */
  public static final QuotationMarks BRACKET_CHINESE_DOUBLE = new QuotationMarks(
      Characters.QUOTE_BRACKET_CHINESE_DOUBLE_OPEN,
      Characters.QUOTE_BRACKET_CHINESE_DOUBLE_CLOSE, 2);

  /** chinese double quotes */
  public static final QuotationMarks CHINESE_DOUBLE = new QuotationMarks(
      Characters.QUOTE_CHINESE_DOUBLE_OPEN,
      Characters.QUOTE_CHINESE_DOUBLE_CLOSE, 2);

  /** chinese double quotes bottom-top */
  public static final QuotationMarks CHINESE_DOUBLE_BOTTOM_TOP = new QuotationMarks(
      Characters.QUOTE_CHINESE_DOUBLE_OPEN_BOTTOM,
      Characters.QUOTE_CHINESE_DOUBLE_CLOSE, 2);

  /** accents misused as single quotes */
  public static final QuotationMarks ACCENT_SINGLE = new QuotationMarks(
      Characters.QUOTE_ACCENT_SINGLE_OPEN,
      Characters.QUOTE_ACCENT_SINGLE_CLOSE, 2);

  /** single bottom-top */
  public static final QuotationMarks BOTTOM_TOP_SINGLE = new QuotationMarks(
      Characters.QUOTE_SINGLE_BOTTOM_OPEN,
      Characters.QUOTE_DEFAULT_SINGLE_CLOSE, 1);

  /** single top quote */
  public static final QuotationMarks TOP_SINGLE = new QuotationMarks(
      Characters.QUOTE_SINGLE_TOP_OPEN,
      Characters.QUOTE_DEFAULT_SINGLE_CLOSE, 1);

  /** double bottom-top */
  public static final QuotationMarks BOTTOM_TOP_DOUBLE = new QuotationMarks(
      Characters.QUOTE_DOUBLE_BOTTOM_OPEN,
      Characters.QUOTE_DEFAULT_DOUBLE_CLOSE, 2);

  /** double bottom-top */
  public static final QuotationMarks TOP_DOUBLE = new QuotationMarks(
      Characters.QUOTE_DOUBLE_TOP_OPEN,
      Characters.QUOTE_DEFAULT_DOUBLE_CLOSE, 2);

  /** single dingbat quotes */
  public static final QuotationMarks DINGBAT_SINGLE = new QuotationMarks(
      Characters.QUOTE_DINGBAT_SINGLE_OPEN,
      Characters.QUOTE_DINGBAT_SINGLE_CLOSE, 1);

  /** double dingbat quotes */
  public static final QuotationMarks DINGBAT_DOUBLE = new QuotationMarks(
      Characters.QUOTE_DINGBAT_DOUBLE_OPEN,
      Characters.QUOTE_DINGBAT_DOUBLE_CLOSE, 2);

  /** the default quotes to use */
  public static final QuotationMarks DEFAULT = QuotationMarks.DEFAULT_DOUBLE;

  /** the set of all quotation marks */
  public static final ArraySetView<QuotationMarks> QUOTATION_MARKS;

  static {
    final QuotationMarks[] data;
    int i;

    data = new QuotationMarks[] { QuotationMarks.DEFAULT_DOUBLE,
        QuotationMarks.DEFAULT_SINGLE, QuotationMarks.DEFAULT_TRIPLE,
        QuotationMarks.ALT_SINGLE, QuotationMarks.ALT_DOUBLE,
        QuotationMarks.PRIMITIVE_DOUBLE, QuotationMarks.PRIMITIVE_SINGLE,
        QuotationMarks.ANGLE_DOUBLE, QuotationMarks.ANGLE_SINGLE,
        QuotationMarks.ANGLE_CHINESE_SINGLE,
        QuotationMarks.ANGLE_CHINESE_DOUBLE,
        QuotationMarks.BRACKET_CHINESE_SINGLE,
        QuotationMarks.BRACKET_CHINESE_DOUBLE,
        QuotationMarks.CHINESE_DOUBLE, QuotationMarks.ACCENT_SINGLE,
        QuotationMarks.BOTTOM_TOP_SINGLE, QuotationMarks.TOP_SINGLE,
        QuotationMarks.BOTTOM_TOP_DOUBLE, QuotationMarks.TOP_DOUBLE,
        QuotationMarks.DINGBAT_SINGLE, QuotationMarks.DINGBAT_DOUBLE };

    i = 0;
    for (final QuotationMarks q : data) {
      q.m_id = i++;
    }
    QUOTATION_MARKS = new _QuotationMarks(data);
  }

  /** the dash count of the quote */
  transient final int m_dashCount;

  /**
   * Create
   * 
   * @param begin
   *          the beginning character
   * @param end
   *          the ending character
   * @param dashCount
   *          the dash count of the quote
   */
  private QuotationMarks(final QuotationMark begin,
      final QuotationMark end, final int dashCount) {
    super(begin, end);
    this.m_dashCount = dashCount;
  }

  /**
   * Get the dash count of the quote
   * 
   * @return the dash count of the quote
   */
  public final int getDashCount() {
    return this.m_dashCount;
  }

  /** {@inheritDoc} */
  @Override
  public final ArraySetView<QuotationMarks> getSet() {
    return QuotationMarks.QUOTATION_MARKS;
  }
}
