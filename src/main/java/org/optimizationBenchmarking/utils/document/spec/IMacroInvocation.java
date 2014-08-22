package org.optimizationBenchmarking.utils.document.spec;

/**
 * A macro invocation allows you to pass parameters to a macro.
 */
public interface IMacroInvocation extends IDocumentPart {

  /**
   * Define the value of a macro parameter
   * 
   * @return a context to write the macro parameter value to
   */
  public abstract IDocumentPart nextParameter();

}
