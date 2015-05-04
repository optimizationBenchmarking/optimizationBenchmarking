package org.optimizationBenchmarking.utils.io;

import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.NullTextOutput;

/** A writer which writes its data to a logger */
public class LoggingWriter extends Writer {

  /** the destination logger */
  protected final Logger m_logger;

  /** the log level */
  protected final Level m_logLevel;

  /** the internal buffer */
  private AbstractTextOutput m_buffer;

  /**
   * Create the logging writer
   *
   * @param logger
   *          the logger, or {@code null} if none is used
   * @param level
   *          the log level: must not be {@code null} if
   *          {@code logger!=null}
   */
  public LoggingWriter(final Logger logger, final Level level) {
    super();

    this.m_logger = logger;
    this.m_logLevel = level;

    if (logger != null) {
      this.lock = logger;
      if (level == null) {
        throw new IllegalArgumentException(//
            "If logger is not null, log level must not be null either."); //$NON-NLS-1$
      }
      this.m_buffer = new MemoryTextOutput();
    } else {
      this.m_buffer = NullTextOutput.INSTANCE;
    }

  }

  /**
   * Get the internal buffer
   *
   * @return the internal buffer
   * @throws IllegalStateException
   *           if this writer is already closed
   */
  private final AbstractTextOutput __get() throws IllegalStateException {
    if (this.m_buffer == null) {
      throw new IllegalStateException("LoggingWriter already closed."); //$NON-NLS-1$
    }
    return this.m_buffer;
  }

  /**
   * check if we should flush
   *
   * @throws IllegalStateException
   *           if i/o fails
   */
  @SuppressWarnings("incomplete-switch")
  private final void __checkFlush() throws IllegalStateException {
    final int len;
    final MemoryTextOutput mto;

    if (this.m_buffer instanceof MemoryTextOutput) {
      mto = ((MemoryTextOutput) (this.m_buffer));
      len = mto.length();
      if (len > 1024) {
        switch (mto.charAt(len - 1)) {
          case 0:
          case '\r':
          case '\n': {
            this.flush();
          }
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final int c) throws IllegalStateException {
    synchronized (this.lock) {
      this.__get().append((char) c);
      this.__checkFlush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char[] cbuf) throws IllegalStateException {
    synchronized (this.lock) {
      this.__get().append(cbuf);
      this.__checkFlush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String str) throws IllegalStateException {
    synchronized (this.lock) {
      this.__get().append(str);
      this.__checkFlush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String str, final int off, final int len)
      throws IllegalStateException {
    synchronized (this.lock) {
      this.__get().append(str, off, (off + len));
      this.__checkFlush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final LoggingWriter append(final CharSequence csq)
      throws IllegalStateException {
    synchronized (this.lock) {
      this.__get().append(csq);
      this.__checkFlush();
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final LoggingWriter append(final CharSequence csq,
      final int start, final int end) throws IllegalStateException {
    synchronized (this.lock) {
      this.__get().append(csq, start, end);
      this.__checkFlush();
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final LoggingWriter append(final char c)
      throws IllegalStateException {
    synchronized (this.lock) {
      this.__get().append(c);
      this.__checkFlush();
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() throws IllegalStateException {
    final AbstractTextOutput buffer;
    final MemoryTextOutput mto;
    final String string;
    final int length;

    synchronized (this.lock) {
      buffer = this.__get();
      if (buffer instanceof MemoryTextOutput) {
        mto = ((MemoryTextOutput) buffer);
        try {
          if (this.m_logger != null) {

            length = mto.length();
            if (length > 0) {
              string = buffer.toString();
              mto.clear();
              this.m_logger.log(this.m_logLevel, string);
            }
          }
        } finally {
          mto.clear();
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IllegalStateException {
    synchronized (this.lock) {
      if (this.m_buffer != null) {
        try {
          this.flush();
        } finally {
          this.m_buffer = null;
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char[] cbuf, final int off, final int len)
      throws IllegalStateException {
    synchronized (this.lock) {
      this.__get().append(cbuf, off, (off + len));
      this.__checkFlush();
    }
  }
}
