package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * Appends numbers in by transforming them to base-26 and all-alphabetic.
 */
public final class AlphabeticNumberAppender extends NumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the globally shared instance of the base-26, all-alphabetic number
   * appender always using lower case characters
   */
  public static final AlphabeticNumberAppender LOWER_CASE_INSTANCE = new AlphabeticNumberAppender(
      true);

  /**
   * the globally shared instance of the base-26, all-alphabetic number
   * appender always using upper case characters
   */
  public static final AlphabeticNumberAppender UPPER_CASE_INSTANCE = new AlphabeticNumberAppender(
      false);

  /** use lower case numbers? */
  private final boolean m_lower;

  /**
   * create
   *
   * @param lower
   *          should we use lower case or upper case numbers?
   */
  private AlphabeticNumberAppender(final boolean lower) {
    super();
    this.m_lower = lower;
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final long v, final ETextCase textCase,
      final ITextOutput textOut) {
    final char[] buf;
    final ETextCase use;
    long x;
    int addend, its, plus, len;

    x = v;
    if (v < 0) {
      its = 13;
      plus = (this.m_lower ? 0x7b : 0x5b);
    } else {
      its = 0;
      plus = (this.m_lower ? 0x61 : 0x41);
    }

    buf = new char[14];
    len = 14;

    do {
      addend = ((int) (x % 26l));
      buf[--len] = ((char) (addend + plus));
      x /= 26L;
      its--;
    } while ((x != 0L) || (its >= 0));

    use = textCase;
    buf[len] = use.adjustCaseOfFirstCharInWord(buf[len]);

    if (len > 0) {
      textOut.append(buf, len, buf.length);
    } else {
      textOut.append(buf);
    }

    return use.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final int v, final ETextCase textCase,
      final ITextOutput textOut) {
    final ETextCase use;
    final char[] buf;
    int x, addend, its, plus, len;

    x = v;
    if (v < 0) {
      its = 6;
      plus = (this.m_lower ? 0x7b : 0x5b);
    } else {
      its = 0;
      plus = (this.m_lower ? 0x61 : 0x41);
    }

    buf = new char[7];
    len = 7;
    do {
      addend = (x % 26);
      buf[--len] = ((char) (addend + plus));
      x /= 26;
      its--;
    } while ((x != 0) || (its >= 0));

    use = textCase;
    buf[len] = use.adjustCaseOfFirstCharInWord(buf[len]);
    if (len > 0) {
      textOut.append(buf, len, buf.length);
    } else {
      textOut.append(buf);
    }

    return use.nextCase();
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return (this.m_lower ? AlphabeticNumberAppender.LOWER_CASE_INSTANCE
        : AlphabeticNumberAppender.UPPER_CASE_INSTANCE);
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return (this.m_lower ? AlphabeticNumberAppender.LOWER_CASE_INSTANCE
        : AlphabeticNumberAppender.UPPER_CASE_INSTANCE);
  }
}