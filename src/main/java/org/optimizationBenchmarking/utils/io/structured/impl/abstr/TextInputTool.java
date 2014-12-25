package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

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
    this.beforeUse();
    return new _TextInputJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJobLog log, final S data, final _Location location)
      throws Throwable {

    if (location.m_location1 instanceof Reader) {
      if (log.canLog()) {
        log.log("Beginning input from Reader."); //$NON-NLS-1$
      }
      this.__reader(log, data, ((Reader) (location.m_location1)));
      if (log.canLog()) {
        log.log("Finished input from Reader."); //$NON-NLS-1$
      }
      return;
    }

    super._handle(log, data, location);
  }

  /**
   * Handle a reader
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be read
   * @param reader
   *          the reader
   * @throws Throwable
   *           if it must
   */
  private final void __reader(final IOJobLog log, final S data,
      final Reader reader) throws Throwable {
    if (reader instanceof BufferedReader) {
      this.reader(log, data, ((BufferedReader) reader));
    } else {
      try (final BufferedReader buffered = new BufferedReader(reader)) {
        this.reader(log, data, buffered);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void stream(final IOJobLog log, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    final Class<?> clazz;
    if ((encoding != null) && (encoding != StreamEncoding.UNKNOWN)
        && (encoding != StreamEncoding.TEXT)
        && (encoding != StreamEncoding.BINARY)
        && ((clazz = encoding.getInputClass()) != null)
        && (Reader.class.isAssignableFrom(clazz))) {
      if (log.canLog(IOTool.FINE_LOG_LEVEL)) {
        log.log(IOTool.FINE_LOG_LEVEL,
            "Using text encoding " + encoding.name()); //$NON-NLS-1$
      }
      try (final Reader reader = ((Reader) (encoding
          .wrapInputStream(stream)))) {
        this.__reader(log, data, reader);
      }
    } else {
      try (final InputStreamReader reader = new InputStreamReader(stream)) {
        this.__reader(log, data, reader);
      }
    }
  }

  /**
   * Handle a reader
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be read
   * @param reader
   *          the reader
   * @throws Throwable
   *           if it must
   */
  protected void reader(final IOJobLog log, final S data,
      final BufferedReader reader) throws Throwable {
    //
  }

}
