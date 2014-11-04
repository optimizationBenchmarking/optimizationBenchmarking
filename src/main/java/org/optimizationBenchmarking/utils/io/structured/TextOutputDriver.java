package org.optimizationBenchmarking.utils.io.structured;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for text-based output
 * 
 * @param <S>
 *          the store context
 */
public abstract class TextOutputDriver<S> extends
    ByteStreamOutputDriver<S> {

  /** create */
  protected TextOutputDriver() {
    super();
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
