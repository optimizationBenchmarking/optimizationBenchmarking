package org.optimizationBenchmarking.utils.text.tokenizers;

import org.optimizationBenchmarking.utils.EmptyUtils;

/** An iterator splitting a string at a specific character. */
public class SeparatorBasedStringIterator extends _StringIterator {

  /** the char */
  private final char m_ch;

  /** should we return empty strings as null? */
  private final boolean m_emptyAsNull;

  /**
   * Create the CSV iterator
   *
   * @param string
   *          the string to iterate over
   * @param split
   *          the character at which to split
   * @param emptyAsNull
   *          should we return empty strings as null?
   */
  public SeparatorBasedStringIterator(final String string,
      final char split, final boolean emptyAsNull) {
    super(string);

    this.m_ch = split;
    this.m_emptyAsNull = emptyAsNull;
    this.m_curEnd = (-1);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    final int len;
    final String s;
    final int oldEnd;
    int start, end, i;

    if (this.m_next == null) {
      s = this.m_string;
      if (s == null) {
        return false;
      }
      len = s.length();

      oldEnd = this.m_curEnd;

      if (oldEnd >= len) {
        return false;
      }

      trimmer1: for (i = oldEnd; (++i) < len;) {
        if (s.charAt(i) > ' ') {
          break trimmer1;
        }
      }
      start = i;

      finder: for (i = (start - 1); (++i) < len;) {
        if (s.charAt(i) == this.m_ch) {
          break finder;
        }
      }

      end = i;
      trimmer2: for (; (--i) >= 0;) {
        if (s.charAt(i) > ' ') {
          break trimmer2;
        }
      }

      this.m_curEnd = end;
      if (start <= i) {
        this.m_next = this.m_string.substring(start, (i + 1));
        return true;
      }
      if (((start <= 0) || ((oldEnd <= 0) && (start >= len)))
          && (end >= len) && (i < 0)) {
        return false;
      }

      this.m_next = EmptyUtils.EMPTY_STRING;
      return true;
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final String next() {
    final String ret;

    if (this.hasNext()) {
      ret = this.m_next;
      this.m_next = null;
      return (((ret == EmptyUtils.EMPTY_STRING) && this.m_emptyAsNull) ? null
          : ret);
    }

    return super.next(); // throw
  }
}
