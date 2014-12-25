package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/**
 * The evaluator is a tool.
 */
public interface IEvaluator extends ITool {

  /**
   * Create a builder for a new evaluation process
   * 
   * @return the evaluation process builder
   */
  @Override
  public abstract IEvaluationBuilder use();

}
