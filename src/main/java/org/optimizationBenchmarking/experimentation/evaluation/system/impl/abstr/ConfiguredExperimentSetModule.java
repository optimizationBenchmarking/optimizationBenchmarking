package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredExperimentSetModule;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * the base class for configured experiment set modules
 */
public abstract class ConfiguredExperimentSetModule extends
    ConfiguredModule implements IConfiguredExperimentSetModule {
  /**
   * create the configured experiment set module
   * 
   * @param logger
   *          the logger to use, or {@code null} if no logging information
   *          should be created
   */
  protected ConfiguredExperimentSetModule(final Logger logger) {
    super(logger);
  }

  /**
   * create the configured experiment set module
   * 
   * @param setup
   *          the setup
   */
  protected ConfiguredExperimentSetModule(
      final ExperimentSetModuleSetup<?, ?> setup) {
    super(setup);
  }

  /** {@inheritDoc} */
  @Override
  public void process(final ExperimentSet data,
      final ISectionContainer sectionContainer) {
    //
  }
}
