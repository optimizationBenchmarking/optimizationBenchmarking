package org.optimizationBenchmarking.utils.text.charset;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** The set of defined special characters. */
public final class Characters extends ArraySetView<Char> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** null */
  public static final _EscapedChar CONTROL_NULL = new _EscapedChar(0x00,
      '0'); //
  /** start of heading */
  public static final Char CONTROL_START_OF_HEADING = new Char(0x01);
  /** start of text */
  public static final Char CONTROL_START_OF_TEXT = new Char(0x02);
  /** start of text */
  public static final Char CONTROL_END_OF_TEXT = new Char(0x03);
  /** start of transmission */
  public static final Char CONTROL_END_OF_TRANSMISSION = new Char(0x04);
  /** enquiry */
  public static final Char CONTROL_ENQUIRY = new Char(0x05);
  /** acknowledge */
  public static final Char CONTROL_ACKNOWLEDGE = new Char(0x06);
  /** bell */
  public static final _EscapedChar CONTROL_BELL = new _EscapedChar(0x07,
      'a');//
  /** backspace */
  public static final _EscapedChar CONTROL_BACKSPACE = new _EscapedChar(
      0x08, 'b'); //
  /** tab */
  public static final _EscapedChar CONTROL_HORIZONTAL_TAB = new _EscapedChar(
      0x09, 't');//
  /** line feed */
  public static final _EscapedChar CONTROL_LINE_FEED = new _EscapedChar(
      0x0a, 'n'); //
  /** vertical tab */
  public static final _EscapedChar CONTROL_VERTICAL_TAB = new _EscapedChar(
      0x0b, 'v'); //
  /** form feed */
  public static final _EscapedChar CONTROL_FORM_FEED = new _EscapedChar(
      0x0c, 'f');
  /** carriage return */
  public static final _EscapedChar CONTROL_CARRIAGE_RETURN = new _EscapedChar(
      0x0d, 'r');
  /** shift out */
  public static final Char CONTROL_SHIFT_OUT = new Char(0x0e); //
  /** shift in */
  public static final Char CONTROL_SHIFT_IN = new Char(0x0f); //
  /** data link escape */
  public static final Char CONTROL_DATA_LINK_ESCAPE = new Char(0x10); //
  /** device control 1 */
  public static final Char CONTROL_DEVICE_CONTROL_1 = new Char(0x11); //
  /** device control 2 */
  public static final Char CONTROL_DEVICE_CONTROL_2 = new Char(0x12); //
  /** device control 3 */
  public static final Char CONTROL_DEVICE_CONTROL_3 = new Char(0x13); //
  /** device control 4 */
  public static final Char CONTROL_DEVICE_CONTROL_4 = new Char(0x14); //
  /** negative acknowledge */
  public static final Char CONTROL_NEGATIVE_ACKNOWLEDGE = new Char(0x15); //
  /** synchronous idle */
  public static final Char CONTROL_SYNCHRONOUS_IDLE = new Char(0x16); //
  /** end of transmission block */
  public static final Char CONTROL_END_OF_TRANSMISSION_BLOCK = new Char(
      0x17); //
  /** cancel */
  public static final Char CONTROL_CANCEL = new Char(0x18);
  /** end of medium */
  public static final Char CONTROL_END_OF_MEDIUM = new Char(0x19); //
  /** substitute */
  public static final Char CONTROL_SUBSTITUTE = new Char(0x1a); //
  /** escape */
  public static final Char CONTROL_ESCAPE = new _EscapedChar(0x1b, 'e');//
  /** file separator */
  public static final Char CONTROL_FILE_SEPARATOR = new Char(0x1c); //
  /** group separator */
  public static final Char CONTROL_GROUP_SEPARATOR = new Char(0x1d); //
  /** record separator */
  public static final Char CONTROL_RECORD_SEPARATOR = new Char(0x1e); //
  /** unit separator */
  public static final Char CONTROL_UNIT_SEPARATOR = new Char(0x1f); //
  /** space character */
  public static final Char SPACE = new Char(0x20); //

  /** primitive double quotes */
  public static final QuotationMark QUOTE_PRIMITIVE_DOUBLE = new _EscapedQuotationMark(
      0x22, true, true, '"');
  /** primitive single quotes */
  public static final QuotationMark QUOTE_PRIMITIVE_SINGLE = new _EscapedQuotationMark(
      0x27, true, true, '\'');
  /** parentheses a */
  public static final Brace BRACE_PARENTHESES_OPEN = new Brace(0x28, true,
      false);
  /** parentheses b */
  public static final Brace BRACE_PARENTHESES_CLOSE = new Brace(0x29,
      false, true);

  /** primitive angle single quotes/parentheses start */
  public static final Brace BRACE_ANGLE_PRIMITIVE_SINGLE_OPEN = new Brace(
      0x3c, true, false);
  /** primitive angle single quotes/parentheses end */
  public static final Brace BRACE_ANGLE_PRIMITIVE_SINGLE_CLOSE = new Brace(
      0x3e, false, true);
  /** backslash */
  public static final _EscapedChar QUESTION_MARK = new _EscapedChar(0x3f,
      '?');

  /** brackets a */
  public static final Brace BRACE_BRACKET_OPEN = new Brace(0x5b, true,
      false);

  /** backslash */
  public static final _EscapedChar BACKSLASH = new _EscapedChar(0x5c, '\\');
  /** brackets b */
  public static final Brace BRACE_BRACKET_CLOSE = new Brace(0x5d, false,
      true);

  /** accent single quotes start */
  public static final QuotationMark QUOTE_ACCENT_SINGLE_OPEN = new QuotationMark(
      0x60, true, false);

  /** braces a */
  public static final Brace BRACE_BRACES_OPEN = new Brace(0x7b, true,
      false);
  /** pipe */
  public static final Brace BRACE_PIPE = new Brace(0x7c, true, true);
  /** braces b */
  public static final Brace BRACE_BRACES_CLOSE = new Brace(0x7d, false,
      true);

  /** angle double quotes/parentheses start */
  public static final QuotationMark QUOTE_ANGLE_DOUBLE_OPEN = new QuotationMark(
      0xab, true, false);
  /** accent single quotes end */
  public static final QuotationMark QUOTE_ACCENT_SINGLE_CLOSE = new QuotationMark(
      0xb4, false, true);
  /** angle double quotes/parentheses end */
  public static final QuotationMark QUOTE_ANGLE_DOUBLE_CLOSE = new QuotationMark(
      0xbb, false, true);
  /** double pipe */
  public static final Brace BRACE_DOUBLE_PIPE = new Brace(0x2016, true,
      true);

  /** default single quotes start */
  public static final QuotationMark QUOTE_DEFAULT_SINGLE_OPEN = new QuotationMark(
      0x2018, true, false);
  /** default single quotes end */
  public static final QuotationMark QUOTE_DEFAULT_SINGLE_CLOSE = new QuotationMark(
      0x2019, false, true);

  /** single bottom-top quotes */
  public static final QuotationMark QUOTE_SINGLE_BOTTOM_OPEN = new QuotationMark(
      0x201a, true, false);
  /** single bottom-top quotes */
  public static final QuotationMark QUOTE_SINGLE_TOP_OPEN = new QuotationMark(
      0x201b, true, false);
  /** default double quotes start */
  public static final QuotationMark QUOTE_DEFAULT_DOUBLE_OPEN = new QuotationMark(
      0x201c, true, false);
  /** default double quotes end */
  public static final QuotationMark QUOTE_DEFAULT_DOUBLE_CLOSE = new QuotationMark(
      0x201d, false, true);
  /** double bottom-top quotes */
  public static final QuotationMark QUOTE_DOUBLE_BOTTOM_OPEN = new QuotationMark(
      0x201e, true, false);
  /** double top quotes */
  public static final QuotationMark QUOTE_DOUBLE_TOP_OPEN = new QuotationMark(
      0x201f, true, false);

  /** alternative single quotes end */
  public static final QuotationMark QUOTE_ALT_SINGLE_CLOSE = new QuotationMark(
      0x2032, false, true);
  /** alternative double quotes start */
  public static final QuotationMark QUOTE_ALT_DOUBLE_CLOSE = new QuotationMark(
      0x2033, false, true);
  /** default triple quotes end */
  public static final QuotationMark QUOTE_DEFAULT_TRIPLE_CLOSE = new QuotationMark(
      0x2034, false, true);
  /** alternative single quotes start */
  public static final QuotationMark QUOTE_ALT_SINGLE_OPEN = new QuotationMark(
      0x2035, true, false);
  /** default triple quotes start */
  public static final QuotationMark QUOTE_ALT_DOUBLE_OPEN = new QuotationMark(
      0x2036, true, false);
  /** alternative double quotes end */
  public static final QuotationMark QUOTE_DEFAULT_TRIPLE_OPEN = new QuotationMark(
      0x2037, true, false);

  /** angle single quotes/parentheses start */
  public static final QuotationMark QUOTE_ANGLE_SINGLE_OPEN = new QuotationMark(
      0x2039, true, false);
  /** angle single quotes/parentheses end */
  public static final QuotationMark QUOTE_ANGLE_SINGLE_CLOSE = new QuotationMark(
      0x203a, false, true);

  /** vertical a */
  public static final Brace BRACE_VERTICAL_OPEN = new Brace(0x203f, true,
      false);

  /** vertical b */
  public static final Brace BRACE_VERTICAL_CLOSE = new Brace(0x2040,
      false, true);

  /** brackets with dash a */
  public static final Brace BRACE_BRACKET_WITH_DASH_OPEN = new Brace(
      0x2045, true, false);
  /** brackets with dash b */
  public static final Brace BRACE_BRACKET_WITH_DASH_CLOSE = new Brace(
      0x2046, false, true);

  /** raised parentheses a */
  public static final Brace BRACE_RAISED_PARENTHESES_OPEN = new Brace(
      0x207d, true, false);
  /** raised parentheses b */
  public static final Brace BRACE_RAISED_PARENTHESES_CLOSE = new Brace(
      0x207e, false, true);

  /** sunk parentheses a */
  public static final Brace BRACE_SUNK_PARENTHESES_OPEN = new Brace(
      0x208d, true, false);
  /** sunk parentheses b */
  public static final Brace BRACE_SUNK_PARENTHESES_CLOSE = new Brace(
      0x208e, false, true);

  /** double angle brace open */
  public static final Brace BRACE_DOUBLE_ANGLE_OPEN = new Brace(0x226a,
      true, false);
  /** double angle brace close */
  public static final Brace BRACE_DOUBLE_ANGLE_CLOSE = new Brace(0x226b,
      false, true);

  /** triple angle brace open */
  public static final Brace BRACE_TRIPLE_ANGLE_OPEN = new Brace(0x22d8,
      true, false);
  /** triple angle brace close */
  public static final Brace BRACE_TRIPLE_ANGLE_CLOSE = new Brace(0x22d9,
      false, true);
  /** triple ceiling brace open */
  public static final Brace BRACE_CEILING_OPEN = new Brace(0x2308, true,
      false);
  /** triple ceiling brace close */
  public static final Brace BRACE_CEILING_CLOSE = new Brace(0x2309, false,
      true);
  /** triple floor brace open */
  public static final Brace BRACE_FLOOR_OPEN = new Brace(0x230a, true,
      false);
  /** triple floor brace close */
  public static final Brace BRACE_FLOOR_CLOSE = new Brace(0x230b, false,
      true);

  /** digbat single quotes start */
  public static final QuotationMark QUOTE_DINGBAT_SINGLE_OPEN = new QuotationMark(
      0x275b, true, false);
  /** digbat single quotes end */
  public static final QuotationMark QUOTE_DINGBAT_SINGLE_CLOSE = new QuotationMark(
      0x275c, false, true);
  /** digbat double quotes start */
  public static final QuotationMark QUOTE_DINGBAT_DOUBLE_OPEN = new QuotationMark(
      0x275d, true, false);
  /** digbat double quotes end */
  public static final QuotationMark QUOTE_DINGBAT_DOUBLE_CLOSE = new QuotationMark(
      0x275e, false, true);

  /** checvron a */
  public static final Brace BRACE_CHEVRON_OPEN = new Brace(0x27e8, true,
      false);
  /** checvron b */
  public static final Brace BRACE_CHEVRON_CLOSE = new Brace(0x27e9, false,
      true);

  /** Chinese angle single quotes/parentheses start */
  public static final QuotationMark QUOTE_ANGLE_CHINESE_SINGLE_OPEN = new QuotationMark(
      0x3008, true, false);
  /** Chinese angle single quotes/parentheses end */
  public static final QuotationMark QUOTE_ANGLE_CHINESE_SINGLE_CLOSE = new QuotationMark(
      0x3009, false, true);
  /** Chinese angle double quotes start */
  public static final QuotationMark QUOTE_ANGLE_CHINESE_DOUBLE_OPEN = new QuotationMark(
      0x300a, true, false);
  /** Chinese angle double quotes end */
  public static final QuotationMark QUOTE_ANGLE_CHINESE_DOUBLE_CLOSE = new QuotationMark(
      0x300b, false, true);
  /** Chinese bracket single quotes start */
  public static final QuotationMark QUOTE_BRACKET_CHINESE_SINGLE_OPEN = new QuotationMark(
      0x300c, true, false);
  /** Chinese bracket single quotes end */
  public static final QuotationMark QUOTE_BRACKET_CHINESE_SINGLE_CLOSE = new QuotationMark(
      0x300d, false, true);
  /** Chinese bracket double quotes start */
  public static final QuotationMark QUOTE_BRACKET_CHINESE_DOUBLE_OPEN = new QuotationMark(
      0x300e, true, false);
  /** Chinese bracket double quotes end */
  public static final QuotationMark QUOTE_BRACKET_CHINESE_DOUBLE_CLOSE = new QuotationMark(
      0x300f, false, true);
  /** chinese black parentheses a */
  public static final Brace BRACE_CHINESE_BLACK_PARENTHESES_OPEN = new Brace(
      0x3010, true, false);
  /** chinese black parentheses b */
  public static final Brace BRACE_CHINESE_BLACK_PARENTHESES_CLOSE = new Brace(
      0x3011, false, true);

  /** chinese convex parentheses a */
  public static final Brace BRACE_CHINESE_CONVEX_OPEN = new Brace(0x3014,
      true, false);
  /** chinese convex parentheses b */
  public static final Brace BRACE_CHINESE_CONVEX_CLOSE = new Brace(0x3015,
      false, true);
  /** chinese white parentheses a */
  public static final Brace BRACE_CHINESE_WHITE_PARENTHESES_OPEN = new Brace(
      0x3016, true, false);
  /** chinese white parentheses b */
  public static final Brace BRACE_CHINESE_WHITE_PARENTHESES_CLOSE = new Brace(
      0x3017, false, true);
  /** chinese double convex parentheses a */
  public static final Brace BRACE_CHINESE_DOUBLE_CONVEX_OPEN = new Brace(
      0x3018, true, false);
  /** chinese double convex parentheses b */
  public static final Brace BRACE_CHINESE_DOUBLE_CONVEX_CLOSE = new Brace(
      0x3019, false, true);
  /** chinese double braces a */
  public static final Brace BRACE_CHINESE_DOUBLE_BRACES_OPEN = new Brace(
      0x301a, true, false);
  /** chinese double braces b */
  public static final Brace BRACE_CHINESE_DOUBLE_BRACES_CLOSE = new Brace(
      0x301b, false, true);

  /** Chinese double quotes start */
  public static final QuotationMark QUOTE_CHINESE_DOUBLE_OPEN = new QuotationMark(
      0x301d, true, false);
  /** Chinese double quotes end */
  public static final QuotationMark QUOTE_CHINESE_DOUBLE_CLOSE = new QuotationMark(
      0x301e, false, true);
  /** Chinese double quotes end */
  public static final QuotationMark QUOTE_CHINESE_DOUBLE_OPEN_BOTTOM = new QuotationMark(
      0x301f, true, false);

  /** the set of characters */
  public static final Characters CHARACTERS;

  static {
    final Char[] data;
    data = new Char[] { Characters.CONTROL_NULL,
        Characters.CONTROL_START_OF_HEADING,
        Characters.CONTROL_START_OF_TEXT, Characters.CONTROL_END_OF_TEXT,
        Characters.CONTROL_END_OF_TRANSMISSION,
        Characters.CONTROL_ENQUIRY, Characters.CONTROL_ACKNOWLEDGE,
        Characters.CONTROL_BELL, Characters.CONTROL_BACKSPACE,
        Characters.CONTROL_HORIZONTAL_TAB, Characters.CONTROL_LINE_FEED,
        Characters.CONTROL_VERTICAL_TAB, Characters.CONTROL_FORM_FEED,
        Characters.CONTROL_CARRIAGE_RETURN, Characters.CONTROL_SHIFT_OUT,
        Characters.CONTROL_SHIFT_IN, Characters.CONTROL_DATA_LINK_ESCAPE,
        Characters.CONTROL_DEVICE_CONTROL_1,
        Characters.CONTROL_DEVICE_CONTROL_2,
        Characters.CONTROL_DEVICE_CONTROL_3,
        Characters.CONTROL_DEVICE_CONTROL_4,
        Characters.CONTROL_NEGATIVE_ACKNOWLEDGE,
        Characters.CONTROL_SYNCHRONOUS_IDLE,
        Characters.CONTROL_END_OF_TRANSMISSION_BLOCK,
        Characters.CONTROL_CANCEL, Characters.CONTROL_END_OF_MEDIUM,
        Characters.CONTROL_SUBSTITUTE, Characters.CONTROL_ESCAPE,
        Characters.CONTROL_FILE_SEPARATOR,
        Characters.CONTROL_GROUP_SEPARATOR,
        Characters.CONTROL_RECORD_SEPARATOR,
        Characters.CONTROL_UNIT_SEPARATOR, Characters.SPACE,
        Characters.QUOTE_PRIMITIVE_DOUBLE,
        Characters.QUOTE_PRIMITIVE_SINGLE,
        Characters.BRACE_PARENTHESES_OPEN,
        Characters.BRACE_PARENTHESES_CLOSE,
        Characters.BRACE_ANGLE_PRIMITIVE_SINGLE_OPEN,
        Characters.BRACE_ANGLE_PRIMITIVE_SINGLE_CLOSE,
        Characters.QUESTION_MARK, Characters.BRACE_BRACKET_OPEN,
        Characters.BACKSLASH, Characters.BRACE_BRACKET_CLOSE,
        Characters.QUOTE_ACCENT_SINGLE_OPEN, Characters.BRACE_BRACES_OPEN,
        Characters.BRACE_PIPE, Characters.BRACE_BRACES_CLOSE,
        Characters.QUOTE_ANGLE_DOUBLE_OPEN,
        Characters.QUOTE_ACCENT_SINGLE_CLOSE,
        Characters.QUOTE_ANGLE_DOUBLE_CLOSE, Characters.BRACE_DOUBLE_PIPE,
        Characters.QUOTE_DEFAULT_SINGLE_OPEN,
        Characters.QUOTE_DEFAULT_SINGLE_CLOSE,
        Characters.QUOTE_SINGLE_BOTTOM_OPEN,
        Characters.QUOTE_SINGLE_TOP_OPEN,
        Characters.QUOTE_DEFAULT_DOUBLE_OPEN,
        Characters.QUOTE_DEFAULT_DOUBLE_CLOSE,
        Characters.QUOTE_DOUBLE_BOTTOM_OPEN,
        Characters.QUOTE_DOUBLE_TOP_OPEN,
        Characters.QUOTE_ALT_SINGLE_CLOSE,
        Characters.QUOTE_ALT_DOUBLE_CLOSE,
        Characters.QUOTE_DEFAULT_TRIPLE_CLOSE,
        Characters.QUOTE_ALT_SINGLE_OPEN,
        Characters.QUOTE_ALT_DOUBLE_OPEN,
        Characters.QUOTE_DEFAULT_TRIPLE_OPEN,
        Characters.QUOTE_ANGLE_SINGLE_OPEN,
        Characters.QUOTE_ANGLE_SINGLE_CLOSE,
        Characters.BRACE_VERTICAL_OPEN, Characters.BRACE_VERTICAL_CLOSE,
        Characters.BRACE_BRACKET_WITH_DASH_OPEN,
        Characters.BRACE_BRACKET_WITH_DASH_CLOSE,
        Characters.BRACE_RAISED_PARENTHESES_OPEN,
        Characters.BRACE_RAISED_PARENTHESES_CLOSE,
        Characters.BRACE_SUNK_PARENTHESES_OPEN,
        Characters.BRACE_SUNK_PARENTHESES_CLOSE,
        Characters.BRACE_DOUBLE_ANGLE_OPEN,
        Characters.BRACE_DOUBLE_ANGLE_CLOSE,
        Characters.BRACE_TRIPLE_ANGLE_OPEN,
        Characters.BRACE_TRIPLE_ANGLE_CLOSE,
        Characters.BRACE_CEILING_OPEN, Characters.BRACE_CEILING_CLOSE,
        Characters.BRACE_FLOOR_OPEN, Characters.BRACE_FLOOR_CLOSE,
        Characters.QUOTE_DINGBAT_SINGLE_OPEN,
        Characters.QUOTE_DINGBAT_SINGLE_CLOSE,
        Characters.QUOTE_DINGBAT_DOUBLE_OPEN,
        Characters.QUOTE_DINGBAT_DOUBLE_CLOSE,
        Characters.BRACE_CHEVRON_OPEN, Characters.BRACE_CHEVRON_CLOSE,
        Characters.QUOTE_ANGLE_CHINESE_SINGLE_OPEN,
        Characters.QUOTE_ANGLE_CHINESE_SINGLE_CLOSE,
        Characters.QUOTE_ANGLE_CHINESE_DOUBLE_OPEN,
        Characters.QUOTE_ANGLE_CHINESE_DOUBLE_CLOSE,
        Characters.QUOTE_BRACKET_CHINESE_SINGLE_OPEN,
        Characters.QUOTE_BRACKET_CHINESE_SINGLE_CLOSE,
        Characters.QUOTE_BRACKET_CHINESE_DOUBLE_OPEN,
        Characters.QUOTE_BRACKET_CHINESE_DOUBLE_CLOSE,
        Characters.BRACE_CHINESE_BLACK_PARENTHESES_OPEN,
        Characters.BRACE_CHINESE_BLACK_PARENTHESES_CLOSE,
        Characters.BRACE_CHINESE_CONVEX_OPEN,
        Characters.BRACE_CHINESE_CONVEX_CLOSE,
        Characters.BRACE_CHINESE_WHITE_PARENTHESES_OPEN,
        Characters.BRACE_CHINESE_WHITE_PARENTHESES_CLOSE,
        Characters.BRACE_CHINESE_DOUBLE_CONVEX_OPEN,
        Characters.BRACE_CHINESE_DOUBLE_CONVEX_CLOSE,
        Characters.BRACE_CHINESE_DOUBLE_BRACES_OPEN,
        Characters.BRACE_CHINESE_DOUBLE_BRACES_CLOSE,
        Characters.QUOTE_CHINESE_DOUBLE_OPEN,
        Characters.QUOTE_CHINESE_DOUBLE_CLOSE,
        Characters.QUOTE_CHINESE_DOUBLE_OPEN_BOTTOM, };

    CHARACTERS = new Characters(data);

    // ensure that the braces and quotation marks are initialized as well
    if (Braces.BRACES != null) {
      Braces.BRACES.isEmpty();
    }

    if (QuotationMarks.QUOTATION_MARKS != null) {
      QuotationMarks.QUOTATION_MARKS.isEmpty();
    }
  }

  /** the known escape sequences */
  private final transient Char[] m_escapes;

  /**
   * create
   *
   * @param data
   *          the data
   */
  private Characters(final Char[] data) {
    super(data);

    int max;

    max = 0;
    for (final Char ch : data) {
      if (ch instanceof _EscapedChar) {
        max = Math.max(max, ((_EscapedChar) ch).m_afterBackslash);
      } else {
        if (ch instanceof _EscapedQuotationMark) {
          max = Math.max(max,
              ((_EscapedQuotationMark) ch).m_afterBackslash);
        }
      }
    }

    this.m_escapes = new Char[max + 1];
    for (final Char ch : data) {
      if (ch instanceof _EscapedChar) {
        this.m_escapes[((_EscapedChar) ch).m_afterBackslash] = ch;
      } else {
        if (ch instanceof _EscapedQuotationMark) {
          this.m_escapes[((_EscapedQuotationMark) ch).m_afterBackslash] = ch;
        }
      }
    }

  }

  /**
   * Get the char constant fitting to a given character
   *
   * @param character
   *          the character
   * @return the char constant fitting to {@code character}, or
   *         {@code null} if none was found
   */
  public final Char getChar(final int character) {
    final Char[] data;
    Char midCh;
    int low, high, mid, midVal;

    data = this.m_data;

    if (character <= 0x20) {
      return data[character];
    }
    low = (0x21);
    high = (data.length - 1);

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = ((midCh = data[mid]).m_char - character);

      if (midVal < 0) {
        low = mid + 1;
      } else {
        if (midVal > 0) {
          high = mid - 1;
        } else {
          return midCh;
        }
      }
    }
    return null;
  }

  /**
   * Get the escaped char constant fitting to a given string
   *
   * @param escape
   *          the escape sequence
   * @return the escaped char constant fitting to {@code escape}, or
   *         {@code null} if none was found
   */
  public final Char unescape(final String escape) {
    if ((escape == null) || (escape.length() != 2)) {
      return null;
    }
    return this.getEscapedChar(escape.charAt(1));
  }

  /**
   * Get the escaped character
   *
   * @param afterBackslash
   *          the char following a backslash
   * @return the escaped char, or {@code null} if none was found
   */
  public final Char getEscapedChar(final char afterBackslash) {
    Char ch;
    char l;

    if (afterBackslash >= 0) {

      if (afterBackslash < this.m_escapes.length) {
        ch = this.m_escapes[afterBackslash];
        if (ch != null) {
          return ch;
        }
      }

      l = TextUtils.toLowerCase(afterBackslash);
      if ((l != afterBackslash) && (l < this.m_escapes.length)) {
        return this.m_escapes[l];
      }

      if (Character.digit(afterBackslash, 10) == 0) {
        return Characters.CONTROL_NULL;
      }
    }
    return null;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return _CharactersSerial.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return Characters.CHARACTERS;
  }
}
