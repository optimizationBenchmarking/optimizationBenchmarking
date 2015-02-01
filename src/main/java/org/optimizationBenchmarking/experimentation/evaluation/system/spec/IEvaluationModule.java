package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/**
 * This is the basic interface for evaluator modules.
 */
public interface IEvaluationModule extends ITool {

  /**
   * Get a list of other modules which must also be executed if this module
   * is executed.
   * 
   * @return an {@link java.lang.Iterable} with the required modules (may
   *         be {@code null} if none are required)
   */
  public abstract Iterable<Class<? extends IEvaluationModule>> getRequiredModules();

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationModuleSetup use();
}
