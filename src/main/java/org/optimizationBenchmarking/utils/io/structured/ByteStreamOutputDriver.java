package org.optimizationBenchmarking.utils.io.structured;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for stream-based output
 * 
 * @param <S>
 *          the object to store
 */
public abstract class ByteStreamOutputDriver<S> extends
    FileOutputDriver<S> {

  /** create */
  protected ByteStreamOutputDriver() {
    super();
  }

  /**
   * Store the given object in a stream, while writing log information to
   * the given logger and using the specified {@code defaultEncoding} if
   * the file format allows to do so.
   * 
   * @param data
   *          the object
   * @param dest
   *          the destination stream
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding to be used by default
   * @throws IOException
   *           if I/O fails
   */
  public final void storeStream(final S data, final OutputStream dest,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    try {
      this.doStoreStream(data, dest, logger, defaultEncoding);
    } catch (final IOException t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(//
            Level.SEVERE,//
            ("Error during doStoreStream of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + '.'),//
            t);
      }
      throw t;
    }
  }

  /**
   * Store the given object in a stream.
   * 
   * @param data
   *          the object
   * @param dest
   *          the destination stream
   * @throws IOException
   *           if I/O fails
   */
  public final void storeStream(final S data, final OutputStream dest)
      throws IOException {
    this.storeStream(data, dest, null, StreamEncoding.UNKNOWN);
  }

  /**
   * Store the given object in a stream, while writing log information to
   * the given logger and using the specified {@code defaultEncoding} if
   * the file format allows to do so.
   * 
   * @param data
   *          the object
   * @param stream
   *          the destination stream
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding to be used by default
   * @throws IOException
   *           if I/O fails
   */
  protected void doStoreStream(final S data, final OutputStream stream,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    super.doStoreStream(data, null, stream, logger, defaultEncoding);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStoreStream(final S data, final Path file,
      final OutputStream stream, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    this.doStoreStream(data, stream, logger, defaultEncoding);
  }

  /**
   * Store the given object in a
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   * text output}, while writing log information to the given logger.
   * 
   * @param data
   *          the object
   * @param textOut
   *          the
   *          {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   *          text output}
   * @param logger
   *          the logger
   */
  protected void doStoreText(final S data, final ITextOutput textOut,
      final Logger logger) {
    super.doStoreText(data, null, textOut, logger);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStoreText(final S data, final Path file,
      final ITextOutput text, final Logger logger) {
    this.doStoreText(data, text, logger);
  }

}
