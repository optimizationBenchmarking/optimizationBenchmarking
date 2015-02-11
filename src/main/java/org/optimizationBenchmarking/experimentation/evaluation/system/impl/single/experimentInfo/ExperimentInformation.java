package org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.experimentInfo;

import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentStatistic;

/**
 * The experiment information module.
 */
public class ExperimentInformation extends ExperimentStatistic {

  /** create the experiment information tool */
  ExperimentInformation() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public ExperimentInformationSetup use() {
    return new ExperimentInformationSetup();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return ExperimentInformation.class.getSimpleName();
  }

  /**
   * Get the globally shared instance of the experiment information module
   * 
   * @return the globally shared instance of the experiment information
   *         module
   */
  public static final ExperimentInformation getInstance() {
    return __ExperimentInformationLoader.INSTANCE;
  }

  /** the experiment information loader */
  private static final class __ExperimentInformationLoader {
    /** the shared instance */
    static final ExperimentInformation INSTANCE = new ExperimentInformation();
  }
}
