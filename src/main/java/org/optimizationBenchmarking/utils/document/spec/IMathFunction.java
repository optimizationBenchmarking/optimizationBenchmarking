package org.optimizationBenchmarking.utils.document.spec;

/**
 * A mathematical function
 */
public interface IMathFunction extends IDocumentPart {

  /**
   * Returns a
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath math
   * output device} to write the next argument to.
   * 
   * @return the
   *         {@link org.optimizationBenchmarking.utils.document.spec.IMath
   *         math output device} to write the next argument of this
   *         function to.
   * @throws IllegalArgumentException
   *           if there are no more arguments to be written
   */
  public abstract IMath nextArgument();

}
