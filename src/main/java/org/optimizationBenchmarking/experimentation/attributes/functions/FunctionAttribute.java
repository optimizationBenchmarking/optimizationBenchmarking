package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.ETextCase;
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
   * @param xAxisTransformation
   *          the transformation to be applied to the x-axis
   * @param yAxisInputTransformation
   *          the transformation to be applied to the data of the y-axis
   *          before being fed to the actual computation
   * @param yAxisOutputTransformation
   *          the transformation t
   */
  protected FunctionAttribute(final EAttributeType type,
      final DimensionTransformation xAxisTransformation,
      final DimensionTransformation yAxisInputTransformation,
      final NamedSourceTransformation yAxisOutputTransformation) {
    super(type);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    next = this.appendYAxisTitle(textOut, textCase);
    if (next == null) {
      next = ETextCase.IN_SENTENCE;
    }
    textOut.append(' ');
    next = next.appendWord("over", textOut); //$NON-NLS-1$
    textOut.append(' ');
    return this.appendXAxisTitle(textOut, next);
  }

  /**
   * Append a long version of the name of this function
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to use
   * @return the next text case
   */
  public ETextCase appendLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.appendName(textOut, textCase);
  }

  /**
   * Append the title of the x-axis to a text output device. Do the work of
   * {@link #appendXAxisTitle(ITextOutput, ETextCase)} in case its
   * parameter is a plain text output.
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to use
   * @return the next text case
   */
  protected abstract ETextCase appendXAxisTitlePlain(
      final ITextOutput textOut, final ETextCase textCase);

  /**
   * Append the axis title of the abscissa axis (commonly referred to as
   * x-axis) to the given
   * {@linkplain org.optimizationBenchmarking.utils.document.spec.IMath
   * mathematics output device} {@code math}. Do the work of
   * {@link #appendXAxisTitle(ITextOutput, ETextCase)} in case its
   * parameter is an instance of
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
   * @param textCase
   *          the text case to use
   * @return the next text case
   */
  public final ETextCase appendXAxisTitle(final ITextOutput textOut,
      final ETextCase textCase) {
    final ETextCase next;

    next = ETextCase.ensure(textCase);
    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.appendXAxisTitle(math);
      }
      return next.nextCase();
    }

    return this.appendXAxisTitlePlain(textOut, textCase);
  }

  /**
   * Append the title of the y-axis to a text output device. Do the work of
   * {@link #appendYAxisTitle(ITextOutput, ETextCase)} in case its
   * parameter is a plain text output.
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to use
   * @return the next text case
   */
  protected abstract ETextCase appendYAxisTitlePlain(
      final ITextOutput textOut, final ETextCase textCase);

  /**
   * Append the axis title of the ordinate axis (commonly referred to as
   * y-axis) to the given
   * {@linkplain org.optimizationBenchmarking.utils.document.spec.IMath
   * mathematics output device} {@code math}. Do the work of
   * {@link #appendYAxisTitle(ITextOutput, ETextCase)} in case its
   * parameter is an instance of
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
   * @param textCase
   *          the text case to use
   * @return the next text case
   */
  public final ETextCase appendYAxisTitle(final ITextOutput textOut,
      final ETextCase textCase) {
    final ETextCase next;

    next = ETextCase.ensure(textCase);
    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.appendYAxisTitle(math);
      }
      return next.nextCase();
    }

    return this.appendYAxisTitlePlain(textOut, textCase);
  }
}
