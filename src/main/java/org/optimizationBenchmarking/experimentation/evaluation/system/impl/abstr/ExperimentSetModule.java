package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentSetModule;

/**
 * The experiment set module base class.
 */
public abstract class ExperimentSetModule extends EvaluationModule
    implements IExperimentSetModule {

  /** create an experiment set module */
  protected ExperimentSetModule() {
    super();
  }

}
