package org.optimizationBenchmarking.utils.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * A buffered reader which writes all the stuff you read to another stream.
 * This is mainly for debug purposes.
 */
public class DebugBufferedReader extends BufferedReader {
  /** the end-of-file mark */
  private static final char[] EOF = { '<', 'E', 'O', 'F', '>' };

  /** the source reader */
  private final BufferedReader m_source;

  /** the debug output */
  private final PrintStream m_debug;

  /**
   * Create the debug buffered reader
   * 
   * @param source
   *          the source reader
   * @param debug
   *          the debug output stream
   */
  public DebugBufferedReader(final BufferedReader source,
      final PrintStream debug) {
    super(source);
    this.m_source = source;
    this.m_debug = ((debug != null) ? debug : System.out);
  }

  /**
   * Create the debug buffered reader
   * 
   * @param source
   *          the source reader
   */
  public DebugBufferedReader(final BufferedReader source) {
    this(source, null);
  }

  /** {@inheritDoc} */
  @Override
  public final int read() throws IOException {
    final int result;

    try {
      result = this.m_source.read();
    } catch (final Throwable tt) {
      try {
        throw new IOException("Error when reading single character.", tt); //$NON-NLS-1$            
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }

    synchronized (this.m_debug) {
      if (result >= 0) {
        this.m_debug.print((char) result);
      } else {
        this.m_debug.print(DebugBufferedReader.EOF);
      }
      this.m_debug.flush();
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public final int read(final char[] cbuf, final int off, final int len)
      throws IOException {
    final int result;

    try {
      result = this.m_source.read(cbuf, off, len);
    } catch (final Throwable tt) {
      try {
        throw new IOException(
            (((("Error when reading " + len) + //$NON-NLS-1$  
                " characters into array ")//$NON-NLS-1$
                + ((cbuf != null) ? cbuf.toString() : String.valueOf(null)) + " starting at index ") + off), tt); //$NON-NLS-1$            
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }

    synchronized (this.m_debug) {
      if (result >= 0) {
        this.m_debug.print(String.valueOf(cbuf, off, result));
      } else {
        this.m_debug.print(DebugBufferedReader.EOF);
      }
      this.m_debug.flush();
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public final String readLine() throws IOException {
    final String result;

    try {
      result = this.m_source.readLine();
    } catch (final Throwable tt) {
      try {
        throw new IOException("Error when reading line.", tt);//$NON-NLS-1$            
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }

    synchronized (this.m_debug) {
      if (result != null) {
        this.m_debug.println(result);
      } else {
        this.m_debug.println(DebugBufferedReader.EOF);
      }
      this.m_debug.flush();
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public final long skip(final long n) throws IOException {
    try {
      return this.m_source.skip(n);
    } catch (final Throwable tt) {
      try {
        throw new IOException("Error when skipping " + n + //$NON-NLS-1$
            " chars.", tt);//$NON-NLS-1$            
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean ready() throws IOException {
    try {
      return this.m_source.ready();
    } catch (final Throwable tt) {
      try {
        throw new IOException(
            "Error when checking whether data is ready to be read.", tt);//$NON-NLS-1$                   
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean markSupported() {
    try {
      return this.m_source.markSupported();
    } catch (final Throwable tt) {
      try {
        throw new IOException(
            "Error when checking whether whether marking is supported.", tt);//$NON-NLS-1$                   
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        ErrorUtils.throwRuntimeException(null, ioe);
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void mark(final int readAheadLimit) throws IOException {
    try {
      this.m_source.mark(readAheadLimit);
    } catch (final Throwable tt) {
      try {
        throw new IOException("Error when marking with read-ahead-limit "//$NON-NLS-1$
            + readAheadLimit, tt);
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void reset() throws IOException {
    try {
      this.m_source.reset();
    } catch (final Throwable tt) {
      try {
        throw new IOException("Error when resetting.", tt);//$NON-NLS-1$                   
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    try {
      try {
        super.close();
      } finally {
        this.m_source.close();
      }
    } catch (final Throwable tt) {
      try {
        throw new IOException("Error when closing.", tt); //$NON-NLS-1$
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
  }

}
