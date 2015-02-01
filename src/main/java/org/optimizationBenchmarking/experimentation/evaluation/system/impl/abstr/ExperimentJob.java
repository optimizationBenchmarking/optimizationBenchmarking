package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Level;

import org.optimizationBenchmarking.experimentation.evaluation.data.Experiment;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * A job processing an experiment and writing to a section container,
 * usually by creating a new section. This is one kind of main job.
 * 
 * @param <M>
 *          the configuration type
 */
public abstract class ExperimentJob<M extends ConfiguredExperimentModule>
    extends EvaluationJob<M> {

  /** the experiment */
  protected final Experiment m_experiment;

  /** the section container */
  protected final ISectionContainer m_container;

  /**
   * create
   * 
   * @param config
   *          the configuration
   * @param experiment
   *          the experiment
   * @param container
   *          the section container
   */
  protected ExperimentJob(final M config, final Experiment experiment,
      final ISectionContainer container) {
    super(config);
    EvaluationJob._checkExperiment(experiment);
    EvaluationJob._checkSectionContainer(container);
    this.m_experiment = experiment;
    this.m_container = container;
  }

  /** {@inheritDoc} */
  @Override
  final Level _level() {
    return Level.FINE;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return (super.toString() + '[' + this.m_experiment.getName() + ']');
  }
}
