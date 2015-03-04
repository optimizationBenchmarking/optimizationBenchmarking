package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * A configured module dealing with single experiments.
 */
public interface IConfiguredExperimentModule extends IConfiguredModule {

  /**
   * Process a given experiment.
   * 
   * @param data
   *          the data
   * @param sectionContainer
   *          the section container
   */
  public abstract void process(final Experiment data,
      final ISectionContainer sectionContainer);
}
