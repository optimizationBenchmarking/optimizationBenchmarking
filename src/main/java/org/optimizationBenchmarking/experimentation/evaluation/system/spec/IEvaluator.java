package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.utils.tools.spec.IConfigurableJobTool;

/**
 * The evaluator is a tool.
 */
public interface IEvaluator extends IConfigurableJobTool {

  /**
   * Create a builder for a new evaluation process
   * 
   * @return the evaluation process builder
   */
  @Override
  public abstract IEvaluationBuilder use();

}
