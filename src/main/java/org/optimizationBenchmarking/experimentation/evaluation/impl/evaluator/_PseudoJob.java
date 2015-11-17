package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** a pseudo-module represents a hierarchical ordering of modules */
class _PseudoJob implements IEvaluationJob {

  /** the logger */
  final Logger m_logger;

  /** the child modules */
  IEvaluationJob[] m_children;

  /**
   * create the pseudo module
   *
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _PseudoJob(final Logger logger, final IEvaluationJob[] children) {
    this(logger);
    this.m_children = children;
  }

  /**
   * create the pseudo module
   *
   * @param logger
   *          the logger
   */
  _PseudoJob(final Logger logger) {
    super();
    this.m_logger = logger;
  }

  /**
   * get this module's name
   *
   * @return the module's name
   */
  String _getName() {
    return this.getClass().getSimpleName();
  }

  /**
   * do the initialization jobs
   *
   * @param document
   *          the document
   */
  @Override
  public void initialize(final IDocument document) {
    if (this.m_children != null) {
      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger.finest(//
            "Beginning to initialize the sub-tasks of task " + //$NON-NLS-1$
                this._getName());
      }

      for (final IEvaluationJob module : this.m_children) {
        module.initialize(document);
      }

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger.finest(//
            "Finished initializing the sub-tasks of task " + //$NON-NLS-1$
                this._getName());
      }
    }
  }

  /**
   * add the summary jobs
   *
   * @param summary
   *          the summary
   */
  @Override
  public void summary(final IPlainText summary) {
    if (this.m_children != null) {
      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger.finest(//
            "Beginning to invoke the summary rountines of the sub-tasks of task " //$NON-NLS-1$
                + this._getName());
      }

      for (final IEvaluationJob module : this.m_children) {
        module.summary(summary);
      }

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger.finest(//
            "Finished invoking the summary rountines of the sub-tasks of task " //$NON-NLS-1$
                + this._getName());
      }
    }
  }

  /**
   * do the main jobs
   *
   * @param dest
   *          the destination
   */
  @Override
  public void main(final ISectionContainer dest) {
    this._runSubJobs(dest);
  }

  /**
   * Execute all sub-jobs
   *
   * @param dest
   *          the destination
   */
  final void _runSubJobs(final ISectionContainer dest) {
    final IEvaluationJob[] children;

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINEST))) {
      this.m_logger.finest(//
          "Beginning to execute main routines of sub-tasks of task " //$NON-NLS-1$
              + this._getName());
    }

    if ((children = this.m_children) == null) {
      return;
    }

    if (children.length <= 0) {
      return;
    }

    for (final IEvaluationJob job : children) {
      try {
        job.main(dest);
      } catch (final Throwable caught) {
        RethrowMode.AS_ILLEGAL_STATE_EXCEPTION.rethrow((//
            "Error in evaluation job "//$NON-NLS-1$
                + String.valueOf(job)),
            false, caught);
        return;
      }
    }

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINEST))) {
      this.m_logger.finest(//
          "Finished executing main routines of sub-tasks of task " + //$NON-NLS-1$
              this._getName());
    }
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return TextUtils.className(this.getClass());
  }
}
