package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

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
    if (location.m_location1 instanceof XMLBase) {
      if (job.canLog()) {
        job.log("Beginning output to XMLBase."); //$NON-NLS-1$
      }
      this.xml(job, data, ((XMLBase) (location.m_location1)));
      if (job.canLog()) {
        job.log("Finished output to XMLBase."); //$NON-NLS-1$
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
    this.beforeUse();
    return new _XMLOutputJobBuilder(this);
  }
}
