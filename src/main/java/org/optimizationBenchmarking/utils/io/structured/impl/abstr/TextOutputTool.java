package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.ITextOutputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.ITextOutputTool;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A tool for generating text output
 *
 * @param <S>
 *          the source type
 */
public class TextOutputTool<S> extends StreamOutputTool<S> implements
    ITextOutputTool<S> {

  /** create */
  protected TextOutputTool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJob job, final S data, final _Location location)
      throws Throwable {
    final Logger logger;

    logger = job.getLogger();
    if (location.m_location1 instanceof ITextOutput) {
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Beginning output to ITextOutput.")); //$NON-NLS-1$
      }
      this.text(job, data, ((ITextOutput) (location.m_location1)));
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Finished output to ITextOutput.")); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof Writer) {
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Beginning output to Writer.")); //$NON-NLS-1$
      }
      this.__writer(job, data, ((Writer) (location.m_location1)));
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Finished output to Writer.")); //$NON-NLS-1$
      }
      return;
    }

    super._handle(job, data, location);
  }

  /**
   * Store the data element to a writer
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be written
   * @param writer
   *          the writer to write to
   * @throws Throwable
   *           if I/O fails
   */
  private void __writer(final IOJob job, final S data, final Writer writer)
      throws Throwable {

    if (writer instanceof BufferedWriter) {
      this.text(job, data, AbstractTextOutput.wrap(writer));
    } else {
      try (final BufferedWriter buffered = new BufferedWriter(writer)) {
        this.text(job, data, AbstractTextOutput.wrap(buffered));
      }
    }
  }

  /**
   * Store the data element to a text output device
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be written
   * @param textOut
   *          the ITextOutput to write to
   * @throws Throwable
   *           if I/O fails
   */
  protected void text(final IOJob job, final S data,
      final ITextOutput textOut) throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected void stream(final IOJob job, final S data,
      final OutputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    final Logger logger;
    Class<?> clazz;

    if ((encoding != null) && (encoding != StreamEncoding.UNKNOWN)
        && (encoding != StreamEncoding.TEXT)
        && (encoding != StreamEncoding.BINARY)
        && ((clazz = encoding.getOutputClass()) != null)
        && (Writer.class.isAssignableFrom(clazz))) {

      logger = job.getLogger();
      if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
        logger.log(IOTool.FINE_LOG_LEVEL,//
            ("Using text encoding " //$NON-NLS-1$
            + encoding.name()));
      }
      try (final Writer writer = ((Writer) (encoding
          .wrapOutputStream(stream)))) {
        this.__writer(job, data, writer);
      }
    } else {
      if (stream instanceof Appendable) {
        this.text(job, data, AbstractTextOutput.wrap((Appendable) stream));
      } else {
        try (final OutputStreamWriter writer = new OutputStreamWriter(
            stream)) {
          this.__writer(job, data, writer);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ITextOutputJobBuilder<S> use() {
    this.checkCanUse();
    return new _TextOutputJobBuilder(this);
  }
}
