package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredExperimentModule;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * the base class for configured experiment modules
 */
public abstract class ConfiguredExperimentModule extends ConfiguredModule
    implements IConfiguredExperimentModule {
  /**
   * create the configured experiment module
   * 
   * @param logger
   *          the logger to use, or {@code null} if no logging information
   *          should be created
   */
  protected ConfiguredExperimentModule(final Logger logger) {
    super(logger);
  }

  /**
   * create the configured experiment module
   * 
   * @param setup
   *          the setup
   */
  protected ConfiguredExperimentModule(
      final ExperimentModuleSetup<?, ?> setup) {
    super(setup);
  }

  /** {@inheritDoc} */
  @Override
  public Runnable createMainJob(final Experiment data,
      final ISectionContainer sectionContainer) {
    return null;
  }
}
