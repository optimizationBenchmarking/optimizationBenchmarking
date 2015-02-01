package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.experimentation.evaluation.data.Experiment;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * A configured module dealing with single experiments.
 */
public interface IConfiguredExperimentModule extends IConfiguredModule {

  /**
   * Create the main job.
   * 
   * @param data
   *          the data
   * @param sectionContainer
   *          the section container: the module can create one section in
   *          this container and write its output to that section
   * @return the job, or {@code null} if no computation needs to be
   *         performed.
   */
  public abstract Runnable createMainJob(final Experiment data,
      final ISectionContainer sectionContainer);

}
