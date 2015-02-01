package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentSetStatistic;

/**
 * The experiment set statistic base class.
 */
public abstract class ExperimentSetStatistic extends ExperimentSetModule
    implements IExperimentSetStatistic {

  /** create a experiment set statistic module */
  protected ExperimentSetStatistic() {
    super();
  }

}
