package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Level;

import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * A job processing an experiment set and writing to a section container,
 * usually by creating a new section. This is one kind of main job.
 * 
 * @param <M>
 *          the configuration type
 */
public abstract class ExperimentSetJob<M extends ConfiguredExperimentSetModule>
    extends EvaluationJob<M> {

  /** the experiment set */
  protected final ExperimentSet m_experimentSet;

  /** the section container */
  protected final ISectionContainer m_container;

  /**
   * create
   * 
   * @param config
   *          the configuration
   * @param experimentSet
   *          the experiment set
   * @param container
   *          the section container
   */
  protected ExperimentSetJob(final M config,
      final ExperimentSet experimentSet, final ISectionContainer container) {
    super(config);
    EvaluationJob._checkExperimentSet(experimentSet);
    EvaluationJob._checkSectionContainer(container);
    this.m_experimentSet = experimentSet;
    this.m_container = container;
  }

  /** {@inheritDoc} */
  @Override
  final Level _level() {
    return Level.FINE;
  }
}
