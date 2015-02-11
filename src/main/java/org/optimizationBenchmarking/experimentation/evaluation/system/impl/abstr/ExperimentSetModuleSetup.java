package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentSetModuleSetup;

/**
 * The base class for experiment module setups.
 * 
 * @param <J>
 *          the job type
 * @param <R>
 *          the return type of the setter methods
 */
public abstract class ExperimentSetModuleSetup<J extends ConfiguredExperimentSetModule, R extends ExperimentSetModuleSetup<J, R>>
    extends EvaluationModuleSetup<J, R> implements
    IExperimentSetModuleSetup {

  /** create the module setup */
  protected ExperimentSetModuleSetup() {
    super();
  }
}
