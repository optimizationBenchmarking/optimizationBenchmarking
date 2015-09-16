package org.optimizationBenchmarking.utils.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.optimizationBenchmarking.utils.collections.iterators.IterableIterator;
import org.optimizationBenchmarking.utils.io.nul.NullBufferedReader;

/**
 * An interator which iterates over the lines from a reader.
 */
public class StreamLineIterator extends IterableIterator<String> implements
    Closeable {

  /** the reader to read from */
  private BufferedReader m_reader;

  /** the next line */
  private String m_next;

  /** the io exception */
  private IOException m_caught;

  /**
   * Create the line iterator from a buffered reader
   *
   * @param reader
   *          the buffered reader to read from
   */
  public StreamLineIterator(final BufferedReader reader) {
    super();
    this.m_reader = ((reader != null) ? reader
        : NullBufferedReader.INSTANCE);
  }

  /**
   * Create the line iterator from a reader
   *
   * @param reader
   *          the reader to read from
   */
  public StreamLineIterator(final Reader reader) {
    super();

    this.m_reader = ((reader != null) ? new BufferedReader(reader)
        : NullBufferedReader.INSTANCE);
  }

  /**
   * Create the line iterator from a stream
   *
   * @param stream
   *          the stream to read from
   */
  public StreamLineIterator(final InputStream stream) {
    super();

    this.m_reader = ((stream != null) ? //
    new BufferedReader(new InputStreamReader(stream))
        : NullBufferedReader.INSTANCE);
  }

  /**
   * Create the line iterator from a resource stream
   *
   * @param clazz
   *          the class
   * @param resource
   *          the resource
   */
  public StreamLineIterator(final Class<?> clazz, final String resource) {
    this(clazz.getResourceAsStream(resource));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    if (this.m_next != null) {
      return true;
    }
    if (this.m_reader == null) {
      return false;
    }
    try {
      this.m_next = this.m_reader.readLine();
    } catch (final IOException error) {
      this.m_caught = error;
      return false;
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
    return super.next();// throw error
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    final BufferedReader br;
    final IOException ioe;

    br = this.m_reader;
    this.m_reader = null;
    this.m_next = null;
    ioe = this.m_caught;
    this.m_caught = null;

    if (br != null) {
      try {
        br.close();
      } catch (final IOException ioe2) {
        if (ioe != null) {
          ioe2.addSuppressed(ioe);
        }
        throw ioe2;
      }
    }

    if (ioe != null) {
      throw ioe;
    }
  }
}
