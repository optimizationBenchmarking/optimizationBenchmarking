package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationOutput;
import org.optimizationBenchmarking.utils.document.spec.IDocument;

/**
 * An implementation of the
 * {@link org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationOutput}
 * interface which directly wraps around an instance of
 * {@link org.optimizationBenchmarking.utils.document.spec.IDocument}.
 */
public final class DocumentEvaluationOutput implements IEvaluationOutput {

  /** the wrapped document */
  private volatile IDocument m_doc;

  /**
   * create
   *
   * @param doc
   *          the document
   */
  public DocumentEvaluationOutput(final IDocument doc) {
    super();
    if (doc == null) {
      throw new IllegalArgumentException("Document cannot be null."); //$NON-NLS-1$
    }
    this.m_doc = doc;
  }

  /** {@inheritDoc} */
  @Override
  public final IDocument getDocument() {
    final IDocument doc;

    synchronized (this) {
      doc = this.m_doc;
      this.m_doc = null;

    }

    if (doc == null) {
      throw new IllegalStateException("Document already taken."); //$NON-NLS-1$
    }

    return doc;
  }

}
