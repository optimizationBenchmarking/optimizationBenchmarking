package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;

/**
 * A job processing an experiment set and writing to a plain text. This is
 * the kind of job used in the summary routines.
 * 
 * @param <M>
 *          the configuration type
 */
public abstract class SummaryJob<M extends ConfiguredModule> extends
    EvaluationJob<M> {

  /** the experiment set */
  protected final ExperimentSet m_experimentSet;

  /** the summary */
  protected final IPlainText m_summary;

  /**
   * create
   * 
   * @param config
   *          the configuration
   * @param experimentSet
   *          the experiment set
   * @param summary
   *          the summary
   */
  protected SummaryJob(final M config, final ExperimentSet experimentSet,
      final IPlainText summary) {
    super(config);
    EvaluationJob._checkExperimentSet(experimentSet);
    EvaluationJob._checkPlainText(summary);
    this.m_experimentSet = experimentSet;
    this.m_summary = summary;
  }
}
