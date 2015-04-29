package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An attribute which computes a function over a given data set.
 * 
 * @param <DT>
 *          the data type
 */
public abstract class FunctionAttribute<DT extends IElementSet> extends
    Attribute<DT, IMatrix> implements ISemanticComponent {

  /**
   * Create the function attribute
   * 
   * @param type
   *          the attribute type
   */
  protected FunctionAttribute(final EAttributeType type) {
    super(type);
  }

  /** {@inheritDoc} */
  @Override
  public void appendName(final IMath math) {
    this.appendYAxisTitle(math);
    try (final IText text = math.text()) {
      text.append(" over ");//$NON-NLS-1$
    }
    this.appendXAxisTitle(math);
  }

  /** {@inheritDoc} */
  @Override
  public void appendName(final ITextOutput textOut) {
    this.appendYAxisTitle(textOut);
    textOut.append(" over "); //$NON-NLS-1$
    this.appendXAxisTitle(textOut);
  }

  /**
   * Append the title of the x-axis to a text output device. Do the work of
   * {@link #appendXAxisTitle(ITextOutput)} in case its parameter is a
   * plain text output.
   * 
   * @param textOut
   *          the text output device
   */
  protected abstract void appendXAxisTitlePlain(final ITextOutput textOut);

  /**
   * Append the axis title of the abscissa axis (commonly referred to as
   * x-axis) to the given
   * {@linkplain org.optimizationBenchmarking.utils.document.spec.IMath
   * mathematics output device} {@code math}. Do the work of
   * {@link #appendXAxisTitle(ITextOutput)} in case its parameter is an
   * instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath}.
   * 
   * @param math
   *          the mathematics output device
   */
  public abstract void appendXAxisTitle(final IMath math);

  /**
   * Append the axis title of the abscissa axis (commonly referred to as
   * x-axis) to the given
   * {@linkplain org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   * text output} {@code textOut}. The provided {@code textOut} could
   * potentially be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText},
   * in which case this function may use all the capabilities of this
   * interface, foremost the ability to include mathematical formulas.
   * 
   * @param textOut
   *          the text output to append the axis title to, potentially an
   *          instance of
   *          {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   */
  public final void appendXAxisTitle(final ITextOutput textOut) {
    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.appendXAxisTitle(math);
      }
    } else {
      this.appendXAxisTitlePlain(textOut);
    }
  }

  /**
   * Append the title of the y-axis to a text output device. Do the work of
   * {@link #appendYAxisTitle(ITextOutput)} in case its parameter is a
   * plain text output.
   * 
   * @param textOut
   *          the text output device
   */
  protected abstract void appendYAxisTitlePlain(final ITextOutput textOut);

  /**
   * Append the axis title of the ordinate axis (commonly referred to as
   * y-axis) to the given
   * {@linkplain org.optimizationBenchmarking.utils.document.spec.IMath
   * mathematics output device} {@code math}. Do the work of
   * {@link #appendYAxisTitle(ITextOutput)} in case its parameter is an
   * instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath}.
   * 
   * @param math
   *          the mathematics output device
   */
  public abstract void appendYAxisTitle(final IMath math);

  /**
   * Append the axis title of the ordinate axis (commonly referred to as
   * y-axis) to the given
   * {@linkplain org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   * text output} {@code textOut}. The provided {@code textOut} could
   * potentially be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText},
   * in which case this function may use all the capabilities of this
   * interface, foremost the ability to include mathematical formulas.
   * 
   * @param textOut
   *          the text output to append the axis title to, potentially an
   *          instance of
   *          {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   */
  public final void appendYAxisTitle(final ITextOutput textOut) {
    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.appendXAxisTitle(math);
      }
    } else {
      this.appendXAxisTitlePlain(textOut);
    }
  }
}
