package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentStatistic;

/**
 * The experiment statistic base class.
 */
public abstract class ExperimentStatistic extends ExperimentModule
    implements IExperimentStatistic {

  /** create a experiment statistic module */
  protected ExperimentStatistic() {
    super();
  }

}
