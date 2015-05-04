package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.ITextInputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.ITextInputTool;

/**
 * A tool for reading text input
 *
 * @param <S>
 *          the destination type
 */
public class TextInputTool<S> extends StreamInputTool<S> implements
    ITextInputTool<S> {

  /** create */
  protected TextInputTool() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ITextInputJobBuilder<S> use() {
    this.checkCanUse();
    return new _TextInputJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJob job, final S data, final _Location location)
      throws Throwable {
    final Logger logger;

    if (location.m_location1 instanceof Reader) {
      logger = job.getLogger();
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Beginning input from Reader.")); //$NON-NLS-1$
      }
      this.__reader(job, data, ((Reader) (location.m_location1)));
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Finished input from Reader.")); //$NON-NLS-1$
      }
      return;
    }

    super._handle(job, data, location);
  }

  /**
   * Handle a reader
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param reader
   *          the reader
   * @throws Throwable
   *           if it must
   */
  private final void __reader(final IOJob job, final S data,
      final Reader reader) throws Throwable {
    if (reader instanceof BufferedReader) {
      this.reader(job, data, ((BufferedReader) reader));
    } else {
      try (final BufferedReader buffered = new BufferedReader(reader)) {
        this.reader(job, data, buffered);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void stream(final IOJob job, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    final Logger logger;
    final Class<?> clazz;

    if ((encoding != null) && (encoding != StreamEncoding.UNKNOWN)
        && (encoding != StreamEncoding.TEXT)
        && (encoding != StreamEncoding.BINARY)
        && ((clazz = encoding.getInputClass()) != null)
        && (Reader.class.isAssignableFrom(clazz))) {

      logger = job.getLogger();
      if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
        logger.log(IOTool.FINE_LOG_LEVEL,//
            ("Using text encoding " + //$NON-NLS-1$
            encoding.name()));
      }
      try (final Reader reader = ((Reader) (encoding
          .wrapInputStream(stream)))) {
        this.__reader(job, data, reader);
      }
    } else {
      try (final InputStreamReader reader = new InputStreamReader(stream)) {
        this.__reader(job, data, reader);
      }
    }
  }

  /**
   * Handle a reader
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param reader
   *          the reader
   * @throws Throwable
   *           if it must
   */
  protected void reader(final IOJob job, final S data,
      final BufferedReader reader) throws Throwable {
    //
  }

}
