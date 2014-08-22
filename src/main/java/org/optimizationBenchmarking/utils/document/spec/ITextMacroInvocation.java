package org.optimizationBenchmarking.utils.document.spec;

/**
 * A math macro invocation allows you to pass parameters to a text macro.
 */
public interface ITextMacroInvocation extends IMacroInvocation {

  /**
   * Define the value of a text macro parameter
   * 
   * @return a context to write the text macro parameter value to
   */
  @Override
  public abstract IComplexText nextParameter();

}
