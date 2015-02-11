package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentModuleSetup;

/**
 * The base class for experiment module setups.
 * 
 * @param <J>
 *          the job type
 * @param <R>
 *          the return type of the setter methods
 */
public abstract class ExperimentModuleSetup<J extends ConfiguredExperimentModule, R extends ExperimentModuleSetup<J, R>>
    extends EvaluationModuleSetup<J, R> implements IExperimentModuleSetup {

  /** create the module setup */
  protected ExperimentModuleSetup() {
    super();
  }
}
