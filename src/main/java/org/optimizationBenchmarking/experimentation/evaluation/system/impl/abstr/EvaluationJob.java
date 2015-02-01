package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/**
 * An evaluator job.
 * 
 * @param <M>
 *          the configured module type
 */
public abstract class EvaluationJob<M extends ConfiguredModule> extends
    ToolJob implements Runnable {

  /** the configured module */
  protected final M m_config;

  /**
   * create
   * 
   * @param config
   *          the configuration
   */
  protected EvaluationJob(final M config) {
    super(ConfiguredModule._getLogger(config));
    this.m_config = config;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return (TextUtils.className(this.getClass()) + '@' + //
    this.m_config.toString());
  }

  /** Execute this job. */
  protected abstract void execute();

  /** Do the job: log stuff and delegate to {@link #execute()} */
  @Override
  public final void run() {
    final Logger logger;
    final Level level;
    String msg;

    if ((logger = this.m_logger) != null) {
      if (logger.isLoggable(level = this._level())) {
        logger.log(level, ("Beginning job " + //$NON-NLS-1$
            this.toString() + '.'));
      }
    } else {
      level = null;
    }

    try {
      this.execute();
    } catch (final Throwable throwable) {
      if (throwable instanceof _HandledException) {
        throw ((_HandledException) throwable);
      }
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        msg = ("Severe error during execution of "//$NON-NLS-1$ 
        + this.toString());
        logger.log(Level.SEVERE, msg, throwable);
        throw new _HandledException(msg, throwable);
      }
      ErrorUtils.throwAsRuntimeException(throwable);
    }

    if ((logger != null) && (logger.isLoggable(level))) {
      logger.log(level, ("Finished job " + this.toString()//$NON-NLS-1$
      + " without error."));//$NON-NLS-1$
    }
  }

  /**
   * Get the log level of this job
   * 
   * @return the log level
   */
  Level _level() {
    return Level.FINEST;
  }

  /**
   * check the experiment set
   * 
   * @param data
   *          the data
   */
  static final void _checkExperimentSet(final ExperimentSet data) {
    if (data == null) {
      throw new IllegalArgumentException(
          "Experiment set must not be null."); //$NON-NLS-1$
    }
    if (data.getData().isEmpty()) {
      throw new IllegalArgumentException(
          "Experiment set must contain at least one experiment."); //$NON-NLS-1$
    }
  }

  /**
   * check the experiment
   * 
   * @param data
   *          the data
   */
  static final void _checkExperiment(final Experiment data) {
    if (data == null) {
      throw new IllegalArgumentException("Experiment must not be null."); //$NON-NLS-1$
    }
    if (data.getData().isEmpty()) {
      throw new IllegalArgumentException(
          "Experiment must contain at least one instance run set."); //$NON-NLS-1$
    }
  }

  /**
   * check the document
   * 
   * @param doc
   *          the document
   */
  static final void _checkDocument(final IDocument doc) {
    if (doc == null) {
      throw new IllegalArgumentException("IDocument must not be null.");//$NON-NLS-1$
    }
  }

  /**
   * check the plain text
   * 
   * @param txt
   *          the plain text
   */
  static final void _checkPlainText(final IPlainText txt) {
    if (txt == null) {
      throw new IllegalArgumentException("IPlainText must not be null.");//$NON-NLS-1$
    }
  }

  /**
   * check the section container
   * 
   * @param container
   *          the section container
   */
  static final void _checkSectionContainer(
      final ISectionContainer container) {
    if (container == null) {
      throw new IllegalArgumentException(
          "ISectionContainer must not be null.");//$NON-NLS-1$
    }
  }

}
