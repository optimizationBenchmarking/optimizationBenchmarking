package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An attribute which computes a function over a given data set.
 * 
 * @param <DT>
 *          the data type
 */
public abstract class FunctionAttribute<DT extends IElementSet> extends
    Attribute<DT, IMatrix> {

  /**
   * Create the function attribute
   * 
   * @param type
   *          the attribute type
   */
  protected FunctionAttribute(final EAttributeType type) {
    super(type);
  }

  /**
   * Obtain a suggestion for the path component of figures drawn based on
   * this function
   * 
   * @return a suggestion for the path component of figures drawn based on
   *         this function
   */
  public abstract String getPathComponentSuggestion();

  /**
   * Append the axis title of the abscissa axis (commonly referred to as
   * x-axis) to the given
   * {@linkplain org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   * text output} {@code textOut}. The provided {@code textOut} could
   * potentially be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText},
   * in which case the implementer may use all the capabilities of this
   * interface, foremost the ability to include mathematical formulas.
   * 
   * @param textOut
   *          the text output to append the axis title to, potentially an
   *          instance of
   *          {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   */
  public abstract void appendXAxisTitle(final ITextOutput textOut);

  /**
   * Append the axis title of the ordinate axis (commonly referred to as
   * y-axis) to the given
   * {@linkplain org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   * text output} {@code textOut}. The provided {@code textOut} could
   * potentially be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText},
   * in which case the implementer may use all the capabilities of this
   * interface, foremost the ability to include mathematical formulas.
   * 
   * @param textOut
   *          the text output to append the axis title to, potentially an
   *          instance of
   *          {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   */
  public abstract void appendYAxisTitle(final ITextOutput textOut);
}
