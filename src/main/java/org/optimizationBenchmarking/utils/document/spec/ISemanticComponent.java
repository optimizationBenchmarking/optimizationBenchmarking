package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A semantic component is an entity with a description which may also have
 * associated figures or tables. As such, it has a name, a long name, and,
 * potentially, a description.
 */
public interface ISemanticComponent {

  /**
   * <p>
   * Append the "short name" of this component to the given text output
   * device. This device may actually be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   * or something similar. In this case, the implementation of this
   * function may make use of all the capabilities of this object, foremost
   * including the abilities to
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText#inlineMath()
   * display mathematical} or
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText#emphasize()
   * emphasized} text.
   * </p>
   * <p>
   * A short name might be something like "ECDF".
   * </p>
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to be used
   * @return the next text case
   * @see #printLongName(ITextOutput, ETextCase)
   */
  public abstract ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase);

  /**
   * <p>
   * Append the "long name" of this component to the given text output
   * device. This device may actually be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   * or something similar. In this case, the implementation of this
   * function may make use of all the capabilities of this object, foremost
   * including the abilities to
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText#inlineMath()
   * display mathematical} or
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText#emphasize()
   * emphasized} text.
   * </p>
   * <p>
   * A long name might be something like
   * "Empirical Cumulative Distribution Function"
   * </p>
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to be used
   * @return the next text case
   * @see #printShortName(ITextOutput, ETextCase)
   */
  public abstract ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase);

  /**
   * Print the description of this semantic component. The description is
   * usually at least one sentence of text.
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to be used
   * @return the next text case
   */
  public abstract ETextCase printDescription(final ITextOutput textOut,
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
