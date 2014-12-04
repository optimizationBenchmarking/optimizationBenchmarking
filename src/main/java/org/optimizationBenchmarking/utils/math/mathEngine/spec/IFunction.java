package org.optimizationBenchmarking.utils.math.mathEngine.spec;

/** An interface for function scopes */
public interface IFunction extends IMathEngineScope {

  /**
   * Create the next (nameless) parameter
   * 
   * @return an expression
   */
  public abstract IExpression parameter();

  /**
   * Create the next named parameter
   * 
   * @param name
   *          the name of the parameter
   * @return an expression
   */
  public abstract IExpression parameter(final String name);

}
