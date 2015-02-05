package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationOutput;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;

/**
 * A wrapper for output via the document API based on a driver
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
