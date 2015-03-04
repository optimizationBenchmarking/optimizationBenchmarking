package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * A configured module dealing with the whole experiment set.
 */
public interface IConfiguredExperimentSetModule extends IConfiguredModule {

  /**
   * Process a given experiment set.
   * 
   * @param data
   *          the data
   * @param sectionContainer
   *          the section container
   */
  public abstract void process(final ExperimentSet data,
      final ISectionContainer sectionContainer);

}
