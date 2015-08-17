package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationOutput;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A wrapper for output via the document API based on a document builder
 * coming from a document driver
 */
public class DocumentDriverOutput implements IEvaluationOutput {

  /** the output document builder to use */
  private IDocumentBuilder m_builder;

  /**
   * create
   *
   * @param builder
   *          the document builder
   */
  public DocumentDriverOutput(final IDocumentBuilder builder) {
    super();

    if (builder == null) {
      throw new IllegalArgumentException(
          "IDocumentBuilder cannot be null."); //$NON-NLS-1$
    }
    this.m_builder = builder;
  }

  /**
   * Set the file producer listener for collecting the created output files
   *
   * @param listener
   *          the file producer listener for collecting the created output
   *          files
   */
  public synchronized final void setFileProducerListener(
      final IFileProducerListener listener) {
    if (this.m_builder == null) {
      throw new IllegalStateException(//
          "Document has already been created, cannot set file producer listener anymore."); //$NON-NLS-1$
    }
    this.m_builder.setFileProducerListener(listener);
  }

  /** {@inheritDoc} */
  @Override
  public final IDocument getDocument() {
    final IDocumentBuilder builder;

    synchronized (this) {
      builder = this.m_builder;
      this.m_builder = null;
    }

    if (builder == null) {
      throw new IllegalStateException("DocumentBuilder already used."); //$NON-NLS-1$
    }

    return builder.create();
  }

}
