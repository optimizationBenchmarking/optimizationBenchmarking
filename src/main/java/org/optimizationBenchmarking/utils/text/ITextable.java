package org.optimizationBenchmarking.utils.text;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This interface is common to all objects that have a textual
 * representation which can be written to an instance of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput} .
 */
public interface ITextable {

  /**
   * Write this object's textual representation to an instance of
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * .
   *
   * @param textOut
   *          the
   *          {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   *          instance to which the textual representation should be
   *          written
   */
  public abstract void toText(final ITextOutput textOut);

}
