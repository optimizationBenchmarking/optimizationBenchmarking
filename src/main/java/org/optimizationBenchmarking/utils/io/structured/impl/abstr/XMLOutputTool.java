package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.structured.spec.IXMLOutputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IXMLOutputTool;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLDocument;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A tool for generating XML output
 *
 * @param <S>
 *          the source type
 */
public class XMLOutputTool<S> extends TextOutputTool<S> implements
    IXMLOutputTool<S> {

  /** create */
  protected XMLOutputTool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJob job, final S data, final _Location location)
      throws Throwable {
    final Logger logger;

    if (location.m_location1 instanceof XMLBase) {
      logger = job.getLogger();
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Beginning output to XMLBase.")); //$NON-NLS-1$
      }
      this.xml(job, data, ((XMLBase) (location.m_location1)));
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Finished output to XMLBase.")); //$NON-NLS-1$
      }
      return;
    }
    super._handle(job, data, location);
  }

  /**
   * Store the data element to an XML document or document fragment
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be written
   * @param xmlBase
   *          the XMLBase to write to
   * @throws Throwable
   *           if I/O fails
   */
  protected void xml(final IOJob job, final S data, final XMLBase xmlBase)
      throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected void text(final IOJob job, final S data,
      final ITextOutput textOut) throws Throwable {
    try (final XMLBase xmlBase = new XMLDocument(textOut)) {
      this.xml(job, data, xmlBase);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IXMLOutputJobBuilder<S> use() {
    this.checkCanUse();
    return new _XMLOutputJobBuilder(this);
  }
}
