package org.optimizationBenchmarking.utils.math.mathEngine.spec;

/**
 * The assignment.
 */
public interface IAssignment extends IMathEngineScope {

  /**
   * get the variable which is assigned
   * 
   * @return the variable
   */
  public abstract IVariable getVariable();

  /**
   * get the expression to set the variable's value
   * 
   * @return the expression to set the value
   */
  public abstract IExpression value();
}
