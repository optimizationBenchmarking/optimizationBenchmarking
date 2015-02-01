package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentModule;

/**
 * The experiment module base class.
 */
public abstract class ExperimentModule extends EvaluationModule implements
    IExperimentModule {

  /** create an experiment module */
  protected ExperimentModule() {
    super();
  }

}
