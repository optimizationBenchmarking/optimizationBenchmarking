package org.optimizationBenchmarking.utils.text.tokenizers;

import org.optimizationBenchmarking.utils.EmptyUtils;

/**
 * An iterator iterating over lines in a string. A string may consist of
 * multiple lines, separated by {@code '\n'} or {@code '\r'} or
 * combinations thereof. This iterator provides successive access to these
 * lines.
 */
public final class LineIterator extends _StringIterator {

  /** the should we trim lines? */
  private final boolean m_trimLines;

  /** should we return empty strings as null? */
  private final boolean m_emptyAsNull;

  /**
   * Create the CSV iterator
   *
   * @param string
   *          the string to iterate over
   * @param trimLines
   *          the should we trim lines?
   * @param emptyAsNull
   *          should we return empty strings as null?
   */
  public LineIterator(final String string, final boolean trimLines,
      final boolean emptyAsNull) {
    super(string);

    this.m_trimLines = trimLines;
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

      if (this.m_trimLines) {
        trimmer1: for (i = oldEnd; (++i) < len;) {
          if (s.charAt(i) > ' ') {
            break trimmer1;
          }
        }
      } else {
        trimmer3: for (i = oldEnd; (++i) < len;) {
          if (s.charAt(i) != '\r') {
            break trimmer3;
          }
        }
      }
      start = i;

      finder: for (i = (start - 1); (++i) < len;) {
        if (s.charAt(i) == '\n') {
          break finder;
        }
      }

      end = i;
      if (this.m_trimLines) {
        trimmer2: for (; (--i) >= 0;) {
          if (s.charAt(i) > ' ') {
            break trimmer2;
          }
        }
      } else {
        trimmer4: for (; (--i) >= 0;) {
          if (s.charAt(i) != '\r') {
            break trimmer4;
          }
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