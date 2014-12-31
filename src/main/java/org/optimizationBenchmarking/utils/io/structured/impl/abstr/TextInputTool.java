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
  void _handle(final IOJob job, final S data, final _Location location)
      throws Throwable {

    if (location.m_location1 instanceof Reader) {
      if (job.canLog()) {
        job.log("Beginning input from Reader."); //$NON-NLS-1$
      }
      this.__reader(job, data, ((Reader) (location.m_location1)));
      if (job.canLog()) {
        job.log("Finished input from Reader."); //$NON-NLS-1$
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
    final Class<?> clazz;
    if ((encoding != null) && (encoding != StreamEncoding.UNKNOWN)
        && (encoding != StreamEncoding.TEXT)
        && (encoding != StreamEncoding.BINARY)
        && ((clazz = encoding.getInputClass()) != null)
        && (Reader.class.isAssignableFrom(clazz))) {
      if (job.canLog(IOJob.FINE_LOG_LEVEL)) {
        job.log(IOJob.FINE_LOG_LEVEL,
            "Using text encoding " + encoding.name()); //$NON-NLS-1$
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
