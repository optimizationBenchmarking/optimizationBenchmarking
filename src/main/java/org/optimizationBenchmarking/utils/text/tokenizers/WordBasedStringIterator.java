package org.optimizationBenchmarking.utils.text.tokenizers;

/** An iterator splitting a string at a white space characters. */
public class WordBasedStringIterator extends _StringIterator {

  /**
   * Create the word-based iterator
   *
   * @param string
   *          the string to iterate over
   */
  public WordBasedStringIterator(final String string) {
    super(string);
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
        if (s.charAt(i) <= ' ') {
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
      return false;
      // if (((start <= 0) || ((oldEnd <= 0) && (start >= len)))
      // && (end >= len) && (i < 0)) {
      // return false;
      // }
      //
      // this.m_next = EmptyUtils.EMPTY_STRING;
      // return true;
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
      return ret;
    }

    return super.next(); // throw
  }
}
