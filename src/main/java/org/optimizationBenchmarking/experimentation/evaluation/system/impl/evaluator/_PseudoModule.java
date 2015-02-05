package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.concurrent.ForkJoinTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.DataSet;
import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** a pseudo-module represents a hierarchical ordering of modules */
class _PseudoModule {

  /** the logger */
  final Logger m_logger;

  /** the child modules */
  _PseudoModule[] m_children;

  /**
   * create the pseudo module
   * 
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _PseudoModule(final Logger logger, final _PseudoModule[] children) {
    this(logger);
    this.m_children = children;
  }

  /**
   * create the pseudo module
   * 
   * @param logger
   *          the logger
   */
  _PseudoModule(final Logger logger) {
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
   * @param data
   *          the experiment set
   * @param document
   *          the document
   */
  void _doInitJobs(final ExperimentSet data, final IDocument document) {
    if (this.m_children != null) {
      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger
            .finest("Beginning to execute initialization jobs of sub-modules of " + //$NON-NLS-1$
                this._getName());
      }
      for (final _PseudoModule module : this.m_children) {
        module._doInitJobs(data, document);
      }
      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger
            .finest("Finished executing initialization jobs of sub-modules of " + //$NON-NLS-1$
                this._getName());
      }
    }
  }

  /**
   * add the summary jobs
   * 
   * @param data
   *          the experiment set
   * @param summary
   *          the summary
   */
  void _doSummaryJobs(final ExperimentSet data, final IPlainText summary) {
    if (this.m_children != null) {
      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger
            .finest("Beginning to execute summary jobs of sub-modules of " + //$NON-NLS-1$
                this._getName());
      }
      for (final _PseudoModule module : this.m_children) {
        module._doSummaryJobs(data, summary);
      }
      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINEST))) {
        this.m_logger
            .finest("Finished executing summary jobs of sub-modules of " + //$NON-NLS-1$
                this._getName());
      }
    }
  }

  /**
   * create an error to use and throw
   * 
   * @param data
   *          the data
   * @return the error
   */
  private final IllegalStateException __makeHostError(final DataSet<?> data) {
    return new IllegalStateException(
        "Error during parallel execution of task '"//$NON-NLS-1$
            + this.toString() + "' on " + //$NON-NLS-1$
            _PseudoModule.__dataString(data) + '.');
  }

  /**
   * get the data string
   * 
   * @param data
   *          the data
   * @return the string
   */
  private static final String __dataString(final DataSet<?> data) {
    if (data instanceof Experiment) {
      return ("experiment '" + //$NON-NLS-1$ 
          ((Experiment) data).getName() + '\'');
    }
    if (data == null) {
      return "null data";//$NON-NLS-1$
    }
    return TextUtils.className(data.getClass());
  }

  /**
   * Make a sub-error
   * 
   * @param error
   *          the caught error
   * @param data
   *          the data
   * @param taskName
   *          the name of the module/task
   * @return the exception
   */
  private final IllegalStateException __makeError(final Throwable error,
      final DataSet<?> data, final String taskName) {
    final String msg;

    msg = ("Error during execution of child task task '"//$NON-NLS-1$
        + taskName + "' of task '" + //$NON-NLS-1$
        this.toString() + "\' on " + //$NON-NLS-1$
        _PseudoModule.__dataString(data) + '.');

    return ((error != null) ? new IllegalStateException(msg, error)
        : new IllegalStateException(msg));
  }

  /**
   * do the jobs
   * 
   * @param data
   *          the data
   * @param dest
   *          the destination
   */
  void _doJobs(final DataSet<?> data, final ISectionContainer dest) {
    final _PseudoModule[] children;
    final int childCount;
    _DoJobsTask[] tasks;
    IllegalStateException throwError, newError;
    Throwable currentError;
    String name;
    int i;

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINEST))) {
      this.m_logger
          .finest("Beginning to execute main jobs of sub-modules of " + //$NON-NLS-1$
              this._getName());
    }

    if ((children = this.m_children) == null) {
      return;
    }

    if ((childCount = children.length) <= 0) {
      return;
    }

    if (ForkJoinTask.inForkJoinPool()) {
      throwError = null;
      tasks = new _DoJobsTask[childCount];
      i = 0;

      for (final _PseudoModule module : children) {
        tasks[i++] = new _DoJobsTask(module, data, dest);
      }

      // invoke all the tasks
      try {
        ForkJoinTask.invokeAll(tasks);
      } catch (final Throwable error) {// we did an oopsi
        throwError = this.__makeHostError(data);
        throwError.addSuppressed(error);
      }

      // See if anything went wrong
      for (final _DoJobsTask task : tasks) {
        newError = null;

        currentError = task.getException();
        if (currentError != null) {
          newError = this.__makeError(currentError, data, task.toString());
        } else {
          if (!(task.isCompletedNormally())) {
            name = task.toString();
            if (!(task.isDone())) {
              newError = this.__makeError(new IllegalStateException(
                  "Task '" + name + //$NON-NLS-1$
                      "' has not yet completed."), //$NON-NLS-1$
                  data, name);
            } else {
              newError = this.__makeError(null, data, name);
            }
          }
        }

        if (newError != null) {
          throwError = this.__makeHostError(data);
          throwError.addSuppressed(newError);
        }
      }

      tasks = null;

      if (throwError != null) {
        throw throwError;
      }

    } else {
      for (final _PseudoModule module : children) {
        try {
          module._doJobs(data, dest);
        } catch (final Throwable caught) {
          throw this.__makeError(caught, data, module.toString());
        }
      }
    }

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINEST))) {
      this.m_logger
          .finest("Finished executing main jobs of sub-modules of " + //$NON-NLS-1$
              this._getName());
    }
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return TextUtils.className(this.getClass());
  }
}
