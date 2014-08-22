package org.optimizationBenchmarking.utils.io.structured;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for reader-based experiment I/O
 * 
 * @param <S>
 *          the store context
 * @param <L>
 *          the load context
 */
public abstract class TextIODriver<S, L> extends ByteStreamIODriver<S, L> {

  /** create */
  protected TextIODriver() {
    super();
  }

  /**
   * Load an object from a reader, while writing logging information to the
   * given logger.
   * 
   * @param loadContext
   *          the loading context
   * @param reader
   *          the reader
   * @param logger
   *          the logger for log output
   * @throws IOException
   *           if I/O fails
   */
  public final void loadReader(final L loadContext, final Reader reader,
      final Logger logger) throws IOException {
    try {
      if (reader instanceof BufferedReader) {
        this.doLoadReader(loadContext, ((BufferedReader) reader), logger);
      } else {
        try (final BufferedReader br = new BufferedReader(reader)) {
          this.doLoadReader(loadContext, br, logger);
        }
      }
    } catch (final IOException t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(//
            Level.SEVERE,//
            ("Error during doLoadReader of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + '.'),//
            t);
      }
      throw t;
    }
  }

  /**
   * Load an object from a reader.
   * 
   * @param loadContext
   *          the loading context
   * @param reader
   *          the reader
   * @throws IOException
   *           if I/O fails
   */
  public final void loadReader(final L loadContext, final Reader reader)
      throws IOException {
    this.loadReader(loadContext, reader, null);
  }

  /**
   * Store the given object in a writer, while writing log information to
   * the given logger.
   * 
   * @param data
   *          the object
   * @param dest
   *          the destination writer
   * @param logger
   *          the logger
   * @throws IOException
   *           if I/O fails
   */
  public final void storeWriter(final S data, final Writer dest,
      final Logger logger) throws IOException {
    try {
      this.doStoreWriter(data, null, dest, logger);
    } catch (final IOException t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(//
            Level.SEVERE,//
            ("Error during storeWriter of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + '.'),//
            t);
      }
      throw t;
    }
  }

  /**
   * Store the given object in a writer.
   * 
   * @param data
   *          the object
   * @param dest
   *          the destination writer
   * @throws IOException
   *           if I/O fails
   */
  public final void storeWriter(final S data, final Writer dest)
      throws IOException {
    this.storeWriter(data, dest, null);
  }

  /**
   * Store the given object in a
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   * text output} device, while writing log information to the given
   * logger.
   * 
   * @param data
   *          the object
   * @param dest
   *          the destination
   *          {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   *          text output} device
   * @param logger
   *          the logger
   */
  public final void storeText(final S data, final ITextOutput dest,
      final Logger logger) {
    try {
      this.doStoreText(data, dest, logger);
    } catch (final Throwable t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(//
            Level.SEVERE,//
            ("Error during storeText of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + '.'),//
            t);
      }
      ErrorUtils.throwAsRuntimeException(t);
    }
  }

  /**
   * Store the given object in a
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   * text output} device.
   * 
   * @param data
   *          the object
   * @param dest
   *          the destination
   *          {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   *          text output} device
   */
  public final void storeText(final S data, final ITextOutput dest) {
    this.storeText(data, dest, null);
  }
}
