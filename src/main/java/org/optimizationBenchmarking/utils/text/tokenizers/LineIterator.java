package org.optimizationBenchmarking.utils.text.tokenizers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;

/**
 * <p>
 * An iterator iterating over lines in a string. A string may consist of
 * multiple lines, separated by {@code '\n'} or {@code '\r'} or
 * combinations thereof. This iterator provides successive access to these
 * lines.
 * </p>
 * <p>
 * TODO: This is a preliminary, quick and dirty implementation. It is based
 * on the ideas provided at http://stackoverflow.com/questions/9259411,
 * i.e., on using a {@link java.io.BufferedReader} plugged on top of a
 * {@code java.io.StringReader}. This wastes memory and time and should be
 * replaced with an implementation working on the string directly.
 * </p>
 */
public final class LineIterator extends BasicIterator<String> {

  /** the reader */
  private final BufferedReader m_br;

  /** the next string */
  private String m_next;

  /**
   * create the line iterator
   *
   * @param source
   *          the source string
   */
  public LineIterator(final String source) {
    super();
    this.m_br = new BufferedReader(new StringReader(source));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    if (this.m_next == null) {
      try {
        this.m_next = this.m_br.readLine();
      } catch (final IOException ignore) {
        // well, ignore
      }
    }
    return (this.m_next != null);
  }

  /** {@inheritDoc} */
  @Override
  public final String next() {
    final String next;

    if (this.hasNext()) {
      next = this.m_next;
      this.m_next = null;
      return next;
    }

    return super.next();
  }
}
