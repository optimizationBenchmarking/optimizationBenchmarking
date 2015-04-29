package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A semantic component can provide some additional description about
 * itself.
 */
public interface ISemanticComponent {

  /**
   * Append the "name" of this component to the given
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath
   * mathematics context}.
   * 
   * @param math
   *          the mathematics output device
   */
  public abstract void appendName(final IMath math);

  /**
   * Append the "name" of this component to the given text output device.
   * This device may actually be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   * or something similar. In this case, the implementation of this
   * function may make use of all the capabilities of this object, foremost
   * including the ability to
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText#inlineMath()
   * display mathematical text}.
   * 
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to be used
   * @return the next text case
   */
  public abstract ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase);

  /**
   * Obtain a suggestion for the path component of figures drawn based on
   * this component.
   * 
   * @return a suggestion for the path component of figures drawn based on
   *         this component.
   */
  public abstract String getPathComponentSuggestion();
}
