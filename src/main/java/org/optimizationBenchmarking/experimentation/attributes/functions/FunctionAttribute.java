package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.text.AbstractParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * An attribute which computes a function over a given data set.
 *
 * @param <DT>
 *          the data type
 */
public abstract class FunctionAttribute<DT extends IElementSet> extends
    Attribute<DT, IMatrix> implements ISemanticComponent {

  /**
   * The default parameter for the {@code x}-axis (see
   * {@link #getYAxisOutputTransformation()}: This is intended for use in
   * conjunction with a
   * {@link org.optimizationBenchmarking.utils.config.Configuration}.
   */
  public static final String X_AXIS_PARAM = "xAxis"; //$NON-NLS-1$

  /**
   * The default parameter for the {@code y}-axis input transformation (see
   * {@link #getYAxisInputTransformation()}): This is intended for use in
   * conjunction with a
   * {@link org.optimizationBenchmarking.utils.config.Configuration}.
   */
  public static final String Y_INPUT_AXIS_PARAM = "yAxis"; //$NON-NLS-1$

  /**
   * The default parameter for the {@code y}-axis result transformation
   * (see {@link #getYAxisOutputTransformation()}): This is intended for
   * use in conjunction with a
   * {@link org.optimizationBenchmarking.utils.config.Configuration}.
   */
  public static final String Y_AXIS_OUTPUT_PARAM = "yAxisTransformation"; //$NON-NLS-1$

  /** the transformation of the {@code x}-axis */
  final DimensionTransformation m_xAxisTransformation;

  /**
   * the transformation to be applied to the data of the {@code y}-axis
   * before being fed to the actual computation
   */
  final DimensionTransformation m_yAxisInputTransformation;

  /**
   * the transformation of the result of the function applied to the data
   * on the {@code y}-axis
   */
  final Transformation m_yAxisOutputTransformation;

  /**
   * the
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent
   * semantic math component} representing the {@code y}-axis
   */
  private transient ISemanticMathComponent m_yAxisSemanticComponent;

  /**
   * the
   * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
   * parameter renderer} for the {@code y}-axis function
   */
  private transient IParameterRenderer m_yAxisFunctionRenderer;
  /**
   * the
   * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
   * parameter renderer} for the {@code y}-axis function in path component
   * suggestions
   */
  private transient IParameterRenderer m_yAxisPathComponentRenderer;

  /**
   * Create the function attribute
   *
   * @param type
   *          the attribute type
   * @param xAxisTransformation
   *          the transformation to be applied to the {@code x}-axis
   * @param yAxisInputTransformation
   *          the transformation to be applied to the data of the {@code y}
   *          -axis before being fed to the actual computation
   * @param yAxisOutputTransformation
   *          the transformation of the result of the function applied to
   *          the data on the {@code y}-axis.
   */
  protected FunctionAttribute(final EAttributeType type,
      final DimensionTransformation xAxisTransformation,
      final DimensionTransformation yAxisInputTransformation,
      final Transformation yAxisOutputTransformation) {
    super(type);

    if (xAxisTransformation == null) {
      throw new IllegalArgumentException(//
          "The transformation to be applied to the x-axis cannot be null when creating an instance of " + //$NON-NLS-1$
              this.getClass().getSimpleName());
    }

    if (yAxisInputTransformation == null) {
      throw new IllegalArgumentException(//
          "The transformation to be applied to the data of the y-axis before being fed to the actual computation cannot be null when creating an instance of " + //$NON-NLS-1$
              this.getClass().getSimpleName());
    }

    if (yAxisOutputTransformation == null) {
      throw new IllegalArgumentException(//
          "The transformation of the result of the function applied to the data on the y-axis cannot be null when creating an instance of " + //$NON-NLS-1$
              this.getClass().getSimpleName());
    }

    this.m_xAxisTransformation = xAxisTransformation;
    this.m_yAxisInputTransformation = yAxisInputTransformation;
    this.m_yAxisOutputTransformation = yAxisOutputTransformation;
  }

  /**
   * Obtain the transformation to be applied to the {@code x}-axis.
   *
   * @return the transformation to be applied to the {@code x}-axis
   */
  public final DimensionTransformation getXAxisTransformation() {
    return this.m_xAxisTransformation;
  }

  /**
   * Obtain the transformation to be applied to the data of the {@code y}
   * -axis before being fed to the actual computation
   *
   * @return the transformation to be applied to the data of the {@code y}
   *         -axis before being fed to the actual computation
   */
  public final DimensionTransformation getYAxisInputTransformation() {
    return this.m_yAxisInputTransformation;
  }

  /**
   * Obtain the transformation of the result of the function applied to the
   * data on the {@code y}-axis.
   *
   * @return the transformation of the result of the function applied to
   *         the data on the {@code y}-axis
   */
  public final Transformation getYAxisOutputTransformation() {
    return this.m_yAxisOutputTransformation;
  }

  /**
   * Get the
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent
   * semantic math component} representing the {@code y}-axis
   *
   * @return the
   *         {@link org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent
   *         semantic math component} representing the {@code y}-axis
   */
  public final ISemanticMathComponent getYAxisSemanticComponent() {
    if (this.m_yAxisSemanticComponent == null) {
      this.m_yAxisSemanticComponent = new __FunctionYAxis();
    }
    return this.m_yAxisSemanticComponent;
  }

  /**
   * Obtain the short name of the function to be computed (which is plotted
   * along the {@code y}-axis, of course). This might be something like
   * {@code "ECDF"}.
   *
   * @return the name of the function to be computed
   */
  protected String getShortName() {
    return this.getClass().getSimpleName().toLowerCase();
  }

  /**
   * Obtain the name of the function to be computed (which is plotted along
   * the {@code y}-axis, of course). This might be something like
   * {@code "Empirical Cumulative Distribution Function"}.
   *
   * @return the name of the function to be computed
   */
  protected String getLongName() {
    return this.getShortName();
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        try (final IText name = math.name()) {
          name.append(this.getShortName());
        }
      }
    } else {
      textOut.append(this.getShortName());
    }
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(this.getLongName(), textOut);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.__printDescription(textOut, textCase, false);
  }

  /** {@inheritDoc} */
  @Override
  public String getPathComponentSuggestion() {
    return this.yAxisGetPathComponentSuggestion();
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getClass()),//
            HashUtils.hashCode(this.m_xAxisTransformation)),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_yAxisInputTransformation),//
            HashUtils.hashCode(this.m_yAxisOutputTransformation)));
  }

  /**
   * Check whether the other object is equal to this one.
   *
   * @param other
   *          the other object, will be an instance of the same class as
   *          this object
   * @return {@code true} if {@code other} is equal to {@code this}.
   */
  protected abstract boolean isEqual(final FunctionAttribute<DT> other);

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final boolean equals(final Object o) {
    final FunctionAttribute<DT> other;
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (EComparison.equals(o.getClass(), this.getClass())) {
      other = ((FunctionAttribute<DT>) o);
      return ((this.m_xAxisTransformation.equals(//
          other.m_xAxisTransformation)) && //
          (this.m_yAxisInputTransformation.equals(//
              other.m_yAxisInputTransformation)) && //
          (this.m_yAxisOutputTransformation.equals(//
              other.m_yAxisOutputTransformation)) && //
      this.isEqual(other));
    }
    return false;
  }

  /**
   * get the internal function renderer for the {@code y}-axis
   *
   * @return the internal function renderer for the {@code y}-axis
   */
  private final IParameterRenderer __getYAxisFunctionRenderer() {
    if (this.m_yAxisFunctionRenderer == null) {
      this.m_yAxisFunctionRenderer = new __YAxisFunctionRenderer();
    }
    return this.m_yAxisFunctionRenderer;
  }

  /**
   * get the internal function renderer for the {@code y}-axis for
   * {@link #yAxisGetPathComponentSuggestion()}
   *
   * @return the internal function renderer for the {@code y}-axis for
   *         {@link #yAxisGetPathComponentSuggestion()}
   */
  private final IParameterRenderer __getYAxisPathComponentRenderer() {
    if (this.m_yAxisPathComponentRenderer == null) {
      this.m_yAxisPathComponentRenderer = new __YAxisPathComponentRenderer();
    }
    return this.m_yAxisPathComponentRenderer;
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printShortName(ITextOutput, ETextCase)}
   * for the {@code y}-axis
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  protected ETextCase yAxisPrintShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    this.yAxisMathRender(textOut, this.__getYAxisFunctionRenderer());
    return textCase.nextCase();
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printLongName(ITextOutput, ETextCase)}
   * for the {@code y}-axis
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  protected ETextCase yAxisPrintLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.yAxisPrintShortName(textOut, textCase);
  }

  /**
   * Print the name of this function inside a description.
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @param fromYAxisSemanticComponent
   *          is this method called from
   *          {@link #yAxisPrintDescription(ITextOutput, ETextCase)} (
   *          {@code true}) or
   *          {@link #printDescription(ITextOutput, ETextCase)} (
   *          {@code false})
   * @return the next text case
   */
  protected ETextCase printNameInDescription(final ITextOutput textOut,
      final ETextCase textCase, final boolean fromYAxisSemanticComponent) {
    return SemanticComponentUtils.printLongAndShortNameIfDifferent(this,
        textOut, textCase);
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printDescription(ITextOutput, ETextCase)}
   * for the {@code y}-axis
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @param fromYAxisSemanticComponent
   *          is this method called from
   *          {@link #yAxisPrintDescription(ITextOutput, ETextCase)} (
   *          {@code true}) or
   *          {@link #printDescription(ITextOutput, ETextCase)} (
   *          {@code false})
   * @return the next text case
   */
  private final ETextCase __printDescription(final ITextOutput textOut,
      final ETextCase textCase, final boolean fromYAxisSemanticComponent) {
    ETextCase use;

    use = textCase.appendWord("the", textOut); //$NON-NLS-1$
    textOut.append(' ');
    use = this.printNameInDescription(textOut, use,
        fromYAxisSemanticComponent);
    textOut.append(' ');

    if (!(this.m_yAxisOutputTransformation.isIdentityTransformation())) {
      use = use.appendWords("transformed according to", textOut);//$NON-NLS-1$
      textOut.append(' ');
      if (textOut instanceof IComplexText) {
        try (final IMath math = ((IComplexText) textOut).inlineMath()) {
          this.m_yAxisOutputTransformation.m_func.mathRender(math,
              this.__getYAxisFunctionRenderer());
        }
      } else {
        this.m_yAxisOutputTransformation.m_func.mathRender(textOut,
            this.__getYAxisFunctionRenderer());
      }
      use = use.nextCase();
      textOut.append(' ');
    }

    if (this.m_yAxisInputTransformation.isIdentityTransformation()) {
      use = use.appendWords("of", textOut);//$NON-NLS-1$
      textOut.append(' ');
      use = this.m_yAxisInputTransformation.m_dimension.printShortName(
          textOut, use);
    } else {
      use = use.appendWords("computed based on", textOut);//$NON-NLS-1$
      textOut.append(' ');
      if (textOut instanceof IComplexText) {
        try (final IMath math = ((IComplexText) textOut).inlineMath()) {
          this.m_yAxisInputTransformation.mathRender(math,
              DefaultParameterRenderer.INSTANCE);
        }
      } else {
        this.m_yAxisInputTransformation.mathRender(textOut,
            DefaultParameterRenderer.INSTANCE);
      }
      use = use.nextCase();
    }

    textOut.append(' ');
    use = use.appendWord("over", textOut);//$NON-NLS-1$
    textOut.append(' ');
    use = this.m_xAxisTransformation.printShortName(textOut, use);
    textOut.append('.');
    return use.nextAfterSentenceEnd();
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printDescription(ITextOutput, ETextCase)}
   * for the {@code y}-axis
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  protected ETextCase yAxisPrintDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.__printDescription(textOut, textCase, true);
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#getPathComponentSuggestion()}
   * for the {@code y}-axis
   *
   * @return the path component suggestion
   */
  protected String yAxisGetPathComponentSuggestion() {
    final MemoryTextOutput mto;
    mto = new MemoryTextOutput();
    this.m_yAxisOutputTransformation.m_func.mathRender(mto,
        this.__getYAxisPathComponentRenderer());
    return PathUtils.sanitizePathComponent(mto.toString());
  }

  /**
   * The method {@link java.lang.Object#toString()} for the {@code y}-axis
   *
   * @return the string
   */
  protected String yAxisToString() {
    final MemoryTextOutput mto;
    mto = new MemoryTextOutput();
    this.yAxisMathRender(mto, this.__getYAxisFunctionRenderer());
    return mto.toString();
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable#mathRender(ITextOutput, IParameterRenderer)}
   * for the {@code y}-axis
   *
   * @param out
   *          the text output device
   * @param renderer
   *          the renderer
   */
  protected void yAxisMathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_yAxisOutputTransformation.m_func.mathRender(out,
        this.__getYAxisFunctionRenderer());
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable#mathRender(IMath, IParameterRenderer)}
   * for the {@code y}-axis
   *
   * @param out
   *          the math output device
   * @param renderer
   *          the renderer
   */
  protected void yAxisMathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.m_yAxisOutputTransformation.m_func.mathRender(out,
        this.__getYAxisFunctionRenderer());
  }

  /**
   * Render the name of {@code x}-axis, as used in the first parameter of
   * the function computed as {@code y}-axis
   *
   * @param out
   *          the output destination
   */
  protected void yAxisRenderXAxisAsParameter(final IMath out) {
    this.m_xAxisTransformation.getDimension().mathRender(out,
        DefaultParameterRenderer.INSTANCE);
  }

  /**
   * Render the name of {@code x}-axis, as used in the first parameter of
   * the function computed as {@code y}-axis
   *
   * @param out
   *          the output destination
   */
  protected void yAxisRenderXAxisAsParameter(final ITextOutput out) {
    out.append(this.m_xAxisTransformation.getDimension().getName());
  }

  /**
   * Render the name of {@code y}-axis source, as used in the second
   * parameter of the function computed as {@code y}-axis
   *
   * @param out
   *          the output destination
   */
  protected void yAxisRenderYAxisSourceAsParameter(final IMath out) {
    this.m_yAxisInputTransformation.mathRender(out,
        DefaultParameterRenderer.INSTANCE);
  }

  /**
   * Render the name of {@code y}-axis source, as used in the second
   * parameter of the function computed as {@code y}-axis
   *
   * @param out
   *          the output destination
   */
  protected void yAxisRenderYAxisSourceAsParameter(final ITextOutput out) {
    this.m_yAxisInputTransformation.mathRender(out,
        DefaultParameterRenderer.INSTANCE);
  }

  /** The internal class representing the y-axis of a function */
  private final class __FunctionYAxis implements ISemanticMathComponent {

    /** create */
    __FunctionYAxis() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final ETextCase printShortName(final ITextOutput textOut,
        final ETextCase textCase) {
      return FunctionAttribute.this.yAxisPrintShortName(textOut, textCase);
    }

    /** {@inheritDoc} */
    @Override
    public final ETextCase printLongName(final ITextOutput textOut,
        final ETextCase textCase) {
      return FunctionAttribute.this.yAxisPrintLongName(textOut, textCase);
    }

    /** {@inheritDoc} */
    @Override
    public final ETextCase printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      return FunctionAttribute.this.yAxisPrintDescription(textOut,
          textCase);
    }

    /** {@inheritDoc} */
    @Override
    public final String getPathComponentSuggestion() {
      return FunctionAttribute.this.yAxisGetPathComponentSuggestion();
    }

    /** {@inheritDoc} */
    @Override
    public final void mathRender(final ITextOutput out,
        final IParameterRenderer renderer) {
      FunctionAttribute.this.yAxisMathRender(out, renderer);
    }

    /** {@inheritDoc} */
    @Override
    public final void mathRender(final IMath out,
        final IParameterRenderer renderer) {
      FunctionAttribute.this.yAxisMathRender(out, renderer);
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return (5434243 ^ FunctionAttribute.this.hashCode());
    }

    /**
     * get the owning function attribute
     *
     * @return the owning function attribute
     */
    private final FunctionAttribute<?> __getOwner() {
      return FunctionAttribute.this;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public final boolean equals(final Object o) {
      return ((o == this) || //
      ((o instanceof FunctionAttribute.__FunctionYAxis) && //
      (FunctionAttribute.this.equals(((__FunctionYAxis) o).__getOwner()))));
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return FunctionAttribute.this.yAxisToString();
    }
  }

  /** The renderer for the {@code y}-axis function */
  private final class __YAxisFunctionRenderer extends
      AbstractParameterRenderer {

    /** create */
    __YAxisFunctionRenderer() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      if (index == 0) {
        out.append(FunctionAttribute.this.getShortName());
        out.append('(');
        FunctionAttribute.this.yAxisRenderXAxisAsParameter(out);
        out.append(',');
        FunctionAttribute.this.yAxisRenderYAxisSourceAsParameter(out);
        out.append(')');
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index, final IMath out) {

      if (index == 0) {
        try (final IMath function = out.nAryFunction(
            FunctionAttribute.this.getShortName(), 2, 2)) {
          FunctionAttribute.this.yAxisRenderXAxisAsParameter(function);
          FunctionAttribute.this
              .yAxisRenderYAxisSourceAsParameter(function);
        }
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return (9739559 ^ FunctionAttribute.this.hashCode());
    }

    /**
     * get the owning function attribute
     *
     * @return the owning function attribute
     */
    private final FunctionAttribute<?> __getOwner() {
      return FunctionAttribute.this;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public final boolean equals(final Object o) {
      return ((o == this) || //
      ((o instanceof FunctionAttribute.__YAxisFunctionRenderer) && //
      (FunctionAttribute.this.equals(((__YAxisFunctionRenderer) o)
          .__getOwner()))));
    }
  }

  /**
   * The renderer for the {@code y}-axis function for making the path
   * component
   */
  private final class __YAxisPathComponentRenderer extends
      AbstractParameterRenderer {

    /** create */
    __YAxisPathComponentRenderer() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      if (index == 0) {
        out.append(FunctionAttribute.this.getShortName());
        out.append('(');
        FunctionAttribute.this.m_xAxisTransformation.mathRender(out,
            DefaultParameterRenderer.INSTANCE);
        out.append(',');
        FunctionAttribute.this.yAxisRenderYAxisSourceAsParameter(out);
        out.append(')');
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index, final IMath out) {

      if (index == 0) {
        try (final IMath function = out.nAryFunction(
            FunctionAttribute.this.getShortName(), 2, 2)) {
          FunctionAttribute.this.m_xAxisTransformation.mathRender(out,
              DefaultParameterRenderer.INSTANCE);
          FunctionAttribute.this
              .yAxisRenderYAxisSourceAsParameter(function);
        }
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return (7764707 ^ FunctionAttribute.this.hashCode());
    }

    /**
     * get the owning function attribute
     *
     * @return the owning function attribute
     */
    private final FunctionAttribute<?> __getOwner() {
      return FunctionAttribute.this;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public final boolean equals(final Object o) {
      return ((o == this) || //
      ((o instanceof FunctionAttribute.__YAxisPathComponentRenderer) && //
      (FunctionAttribute.this.equals(((__YAxisPathComponentRenderer) o)
          .__getOwner()))));
    }
  }

}
