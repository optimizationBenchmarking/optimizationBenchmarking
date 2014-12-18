package org.optimizationBenchmarking.utils.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

/**
 * A buffered writer writing its output to a given stream, mainly used for
 * debug purposes.
 */
public final class DebugBufferedWriter extends BufferedWriter {

  /** the destination writer */
  private final BufferedWriter m_dest;

  /** the debug output */
  private final PrintStream m_debug;

  /**
   * Create the debug buffered writer
   * 
   * @param dest
   *          the destination
   * @param debug
   *          the debug stream
   */
  public DebugBufferedWriter(final BufferedWriter dest,
      final PrintStream debug) {
    super(dest);
    this.m_debug = ((debug != null) ? debug : System.out);
    this.m_dest = dest;
  }

  /**
   * Create the debug buffered writer
   * 
   * @param dest
   *          the destination
   */
  public DebugBufferedWriter(final BufferedWriter dest) {
    this(dest, null);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final int c) throws IOException {
    try {
      this.m_dest.write(c);
    } catch (final Throwable tt) {
      try {
        throw new IOException(((("Error when writing character '" //$NON-NLS-1$
            + ((char) c)) + "' (") + c + ')'), //$NON-NLS-1$
            tt);
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
    synchronized (this.m_debug) {
      this.m_debug.print((char) c);
      this.m_debug.flush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char[] cbuf, final int off, final int len)
      throws IOException {
    String string;

    try {
      this.m_dest.write(cbuf, off, len);
    } catch (final Throwable tt) {
      try {
        try {
          string = (('\'' + String.valueOf(cbuf, off, len)) + '\'');
        } catch (final Throwable t) {
          string = ((((((String.valueOf(cbuf) + ' ') + '[') + off) + ',') + len) + ']');
        }

        throw new IOException(("Error when writing characters " + //$NON-NLS-1$
            string), tt);

      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
    synchronized (this.m_debug) {
      this.m_debug.print(String.valueOf(cbuf, off, len));
      this.m_debug.flush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char[] cbuf) throws IOException {
    try {
      this.m_dest.write(cbuf);
    } catch (final Throwable tt) {
      try {
        throw new IOException(((("Error when writing characters " + //$NON-NLS-1$
            '\'') + String.valueOf(cbuf)) + '\''), tt);

      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
    synchronized (this.m_debug) {
      this.m_debug.print(cbuf);
      this.m_debug.flush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String s, final int off, final int len)
      throws IOException {
    String string;

    try {
      this.m_dest.write(s, off, len);
    } catch (final Throwable tt) {
      try {
        try {
          string = (('\'' + s.substring(off, (off + len))) + '\'');
        } catch (final Throwable t) {
          string = ((((((String.valueOf(s) + ' ') + '[') + off) + ',') + len) + ']');
        }

        throw new IOException(("Error when writing sub-string " + //$NON-NLS-1$
            string), tt);

      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }

    string = String.valueOf(s).substring(off, (off + len));

    synchronized (this.m_debug) {
      this.m_debug.print(s);
      this.m_debug.flush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final CharSequence csq) throws IOException {
    try {
      this.m_dest.append(csq);
    } catch (final Throwable tt) {
      try {
        throw new IOException((("Error when appending charsequence '" + //$NON-NLS-1$
            String.valueOf(csq)) + '\''), tt);

      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
    synchronized (this.m_debug) {
      this.m_debug.print(csq);
      this.m_debug.flush();
    }

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final CharSequence csq, final int start,
      final int end) throws IOException {
    String string;

    try {
      this.m_dest.append(csq, start, end);
    } catch (final Throwable tt) {
      try {
        try {
          string = (('\'' + csq.subSequence(start, end).toString()) + '\'');
        } catch (final Throwable t) {
          string = ((((((String.valueOf(csq) + ' ') + '[') + start) + ',') + end) + ']');
        }

        throw new IOException(("Error when appending sub-sequence " + //$NON-NLS-1$
            string), tt);

      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }

    string = String.valueOf(csq).substring(start, end);

    synchronized (this.m_debug) {
      this.m_debug.print(string);
      this.m_debug.flush();
    }

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final char c) throws IOException {
    try {
      this.m_dest.append(c);
    } catch (final Throwable tt) {
      try {
        throw new IOException(((("Error when appending character '" //$NON-NLS-1$
            + c) + "' (") + c + ')'), //$NON-NLS-1$
            tt);
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }
    synchronized (this.m_debug) {
      this.m_debug.print(c);
      this.m_debug.flush();
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String s) throws IOException {
    try {
      this.m_dest.write(s);
    } catch (final Throwable tt) {
      try {
        throw new IOException((("Error when writing string '" + //$NON-NLS-1$
            s) + '\''), tt);

      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }

    synchronized (this.m_debug) {
      this.m_debug.print(s);
      this.m_debug.flush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void newLine() throws IOException {
    try {
      this.m_dest.newLine();
    } catch (final Throwable tt) {
      try {
        throw new IOException("Error when writing a newline.", tt); //$NON-NLS-1$
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }

    synchronized (this.m_debug) {
      this.m_debug.println();
      this.m_debug.flush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() throws IOException {
    try {
      this.m_dest.flush();
    } catch (final Throwable tt) {
      try {
        throw new IOException("Error when flushing.", tt); //$NON-NLS-1$
      } catch (final IOException ioe) {
        synchronized (this.m_debug) {
          ioe.printStackTrace(this.m_debug);
          this.m_debug.flush();
        }
        throw ioe;
      }
    }

    synchronized (this.m_debug) {
      this.m_debug.flush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    try {
      try {
        super.close();
      } finally {
        this.m_dest.close();
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
