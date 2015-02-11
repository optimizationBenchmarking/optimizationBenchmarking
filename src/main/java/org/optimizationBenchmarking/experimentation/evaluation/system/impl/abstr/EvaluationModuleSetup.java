package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModuleSetup;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ConfigurableToolJobBuilder;

/**
 * The base class for evaluation module setups.
 * 
 * @param <J>
 *          the job type
 * @param <R>
 *          the return type of the setter methods
 */
public abstract class EvaluationModuleSetup<J extends ConfiguredModule, R extends EvaluationModuleSetup<J, R>>
    extends ConfigurableToolJobBuilder<J, R> implements
    IEvaluationModuleSetup {

  /** create the module setup */
  protected EvaluationModuleSetup() {
    super();
  }
}
