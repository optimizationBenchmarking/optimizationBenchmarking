package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** a job wrapper */
final class _JobWrapper extends _PseudoJob {

  /** the actual job to be executed */
  final IEvaluationJob m_job;

  /**
   * create the pseudo job
   * 
   * @param logger
   *          the logger
   * @param job
   *          the job
   * @param children
   *          the children
   */
  _JobWrapper(final Logger logger, final IEvaluationJob[] children,
      final IEvaluationJob job) {
    super(logger, children);
    if (job == null) {
      throw new IllegalArgumentException("Module must not be null."); //$NON-NLS-1$
    }
    this.m_job = job;
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return ("Job " + //$NON-NLS-1$
    TextUtils.className(this.m_job.getClass()));
  }

  /** {@inheritDoc} */
  @Override
  public final void initialize(final IDocument document) {
    this.m_job.initialize(document);
    super.initialize(document);
  }

  /** {@inheritDoc} */
  @Override
  public final void summary(final IPlainText summary) {
    this.m_job.summary(summary);
    super.summary(summary);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void main(final ISectionContainer dest) {
    final _DelayedSectionContainer delay;

    if (this.m_children == null) {
      this.m_job.main(dest);
    } else {
      delay = new _DelayedSectionContainer(dest);
      try {
        this.m_job.main(dest);
        super.main(delay._getContainerForSubSections());
      } finally {
        delay._close();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return (TextUtils.className(this.m_job.getClass()) + " (" + //$NON-NLS-1$
        this.m_job.toString() + ')');
  }
}
