package org.optimizationBenchmarking.utils.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** an iterator interface for the {@link java.io.BufferedReader} class */
public class BufferedReaderIterator extends BasicIterator<String> {

  /** the reader */
  private final BufferedReader m_reader;

  /** the next string */
  private String m_next;

  /**
   * Create the {@link java.io.BufferedReader}-based iterator
   * 
   * @param reader
   *          the reader
   */
  public BufferedReaderIterator(final BufferedReader reader) {
    super();
    this.m_reader = reader;
  }

  /**
   * Prepare a string for acccess
   * 
   * @param s
   *          the string
   * @return the prepared string
   */
  protected String prepare(final String s) {
    return TextUtils.prepare(s);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    String s;

    if (this.m_next == null) {
      for (;;) {
        try {
          s = this.m_reader.readLine();
        } catch (final IOException ioe) {
          ErrorUtils.throwAsRuntimeException(ioe);
          return false;
        }

        if (s == null) {
          return false;
        }
        s = this.prepare(s);
        if (s != null) {
          this.m_next = s;
          return true;
        }
      }
    }

    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final String next() {
    final String s;
    if (this.hasNext()) {
      s = this.m_next;
      this.m_next = null;
      return s;
    }
    throw new NoSuchElementException("End of stream reached."); //$NON-NLS-1$
  }
}
