package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.concurrent.ForkJoinTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
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
            "Beginning to invoke the summary rountines of the sub-tasks of task " + //$NON-NLS-1$
                this._getName());
      }

      for (final IEvaluationJob module : this.m_children) {
        module.summary(summary);
      }

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger.finest(//
            "Finished invoking the summary rountines of the sub-tasks of task " + //$NON-NLS-1$
                this._getName());
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
    final int childCount;
    _MainRoutineTask[] tasks;
    Throwable newError;
    Object error;
    String name;
    int i;

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINEST))) {
      this.m_logger.finest(//
          "Beginning to execute main routines of sub-tasks of task " //$NON-NLS-1$
              + this._getName());
    }

    if ((children = this.m_children) == null) {
      return;
    }

    if ((childCount = children.length) <= 0) {
      return;
    }

    error = null;
    if (ForkJoinTask.inForkJoinPool()) {
      tasks = new _MainRoutineTask[childCount];
      i = 0;

      for (final IEvaluationJob module : children) {
        tasks[i++] = new _MainRoutineTask(module, dest);
      }

      // invoke all the tasks
      try {
        ForkJoinTask.invokeAll(tasks);
      } catch (final Throwable caught) {// we did an oopsi
        error = ErrorUtils.aggregateError(error, caught);
      }

      // See if anything went wrong
      for (final _MainRoutineTask task : tasks) {
        newError = task.getException();
        if (newError != null) {
          error = ErrorUtils.aggregateError(error, newError);
        } else {
          if (!(task.isCompletedNormally())) {
            name = task.toString();
            if (!(task.isDone())) {
              error = ErrorUtils.aggregateError(error,
                  new IllegalStateException("Task '" + name + //$NON-NLS-1$
                      "' has not yet completed, but did not generate an error (odd!)." //$NON-NLS-1$
                  ));
            } else {
              error = ErrorUtils.aggregateError(error,
                  new IllegalStateException("Task '" + name + //$NON-NLS-1$
                      "' has not completed normally, but did not generate an error (odd!)." //$NON-NLS-1$
                  ));
            }
          }
        }
      }

      tasks = null;

      if (error != null) {
        RethrowMode.AS_ILLEGAL_STATE_EXCEPTION.rethrow((((//
            "Error while executing a sub-job of evaluation jog '" //$NON-NLS-1$
            + this._getName()) + '\'') + '.'), false, error);
        return;
      }

    } else {
      for (final IEvaluationJob module : children) {
        try {
          module.main(dest);
        } catch (final Throwable caught) {
          RethrowMode.AS_ILLEGAL_STATE_EXCEPTION.rethrow((//
              "Error in evaluation job "//$NON-NLS-1$
              + module.toString()), false, caught);
          return;
        }
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
