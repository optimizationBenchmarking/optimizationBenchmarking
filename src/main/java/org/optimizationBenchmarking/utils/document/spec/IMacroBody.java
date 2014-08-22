package org.optimizationBenchmarking.utils.document.spec;

/**
 * A macro body: This construct is used to define a macro
 */
public interface IMacroBody extends IDocumentPart {

  /**
   * Print the contents of the {@code i}<sup>th</sup> parameter
   * 
   * @param i
   *          the index of the macro parameter to access
   */
  public abstract void macroParam(final int i);

}
