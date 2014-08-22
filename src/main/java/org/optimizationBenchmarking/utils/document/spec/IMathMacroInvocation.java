package org.optimizationBenchmarking.utils.document.spec;

/**
 * A math macro invocation allows you to pass parameters to a math macro.
 */
public interface IMathMacroInvocation extends IMacroInvocation {

  /**
   * Define the value of a math macro parameter
   * 
   * @return a context to write the math macro parameter value to
   */
  @Override
  public abstract IMath nextParameter();

}
