package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A style that can be applied to text in a document or graphics in a
 * figure.
 */
public interface IStyle {

  /**
   * Print a description of the style to the provided complex text.
   * Depending on the type of {@code out}, the description can either be
   * purely textual or, if {@code out} is an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText},
   * maybe even styled in this style. In any case, it can be inserted into
   * sentences like <em>"xxx text denotes blablabla"</em> or
   * <em>"Bablabla is marked with xxx text."</em>
   * 
   * @param out
   *          the text where the style should be described to.
   * @param textCase
   *          the text case
   */
  public abstract void describe(final ETextCase textCase,
      final ITextOutput out);

}
