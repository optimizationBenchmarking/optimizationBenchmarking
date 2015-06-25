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
   * semantic math component} representing the {@code x}-axis
   */
  private transient ISemanticMathComponent m_xAxisSemanticComponent;

  /**
   * the
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent
   * semantic math component} representing the {@code y}-axis
   */
  private transient ISemanticMathComponent m_yAxisSemanticComponent;

  /**
   * the
   * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
   * parameter renderer} for the {@code x}-axis
   */
  private transient IParameterRenderer m_xAxisParameterRenderer;
  /**
   * the
   * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
   * parameter renderer} for the {@code y}-axis function
   */
  private transient IParameterRenderer m_yAxisFunctionRenderer;
  /**
   * the
   * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
   * parameter renderer} for the {@code y}-axis
   */
  private transient IParameterRenderer m_yAxisParameterRenderer;

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
   * semantic math component} representing the {@code x}-axis
   *
   * @return the
   *         {@link org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent
   *         semantic math component} representing the {@code x}-axis
   */
  public final ISemanticMathComponent getXAxisSemanticComponent() {
    if (this.m_xAxisSemanticComponent == null) {
      this.m_xAxisSemanticComponent = new __FunctionXAxis();
    }
    return this.m_xAxisSemanticComponent;
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
    return ETextCase.ensure(textCase).nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return ETextCase.ensure(textCase).appendWords(this.getLongName(),
        textOut);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.yAxisPrintDescription(textOut, textCase);
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
   * get the internal parameter renderer for the {@code x}-axis
   *
   * @return the internal parameter renderer for the {@code x}-axis
   */
  private final IParameterRenderer __getXAxisParameterRenderer() {
    if (this.m_xAxisParameterRenderer == null) {
      this.m_xAxisParameterRenderer = new __XAxisParameterRenderer();
    }
    return this.m_xAxisParameterRenderer;
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printShortName(ITextOutput, ETextCase)}
   * for the {@code x}-axis
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  protected ETextCase xAxisPrintShortName(final ITextOutput textOut,
      final ETextCase textCase) {

    if (this.m_xAxisTransformation.isIdentityTransformation()) {
      return this.m_xAxisTransformation.m_dimension.printShortName(
          textOut, textCase);
    }

    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.xAxisMathRender(math, this.__getXAxisParameterRenderer());
      }
    } else {
      this.xAxisMathRender(textOut, this.__getXAxisParameterRenderer());
    }

    return ETextCase.ensure(textCase).nextCase();
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printLongName(ITextOutput, ETextCase)}
   * for the {@code x}-axis
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  protected ETextCase xAxisPrintLongName(final ITextOutput textOut,
      final ETextCase textCase) {

    if (this.m_xAxisTransformation.isIdentityTransformation()) {
      return this.m_xAxisTransformation.m_dimension.printLongName(textOut,
          textCase);
    }

    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.xAxisMathRender(math, this.__getXAxisParameterRenderer());
      }
    } else {
      this.xAxisMathRender(textOut, this.__getXAxisParameterRenderer());
    }

    return ETextCase.ensure(textCase).nextCase();
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printDescription(ITextOutput, ETextCase)}
   * for the {@code x}-axis
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  protected ETextCase xAxisPrintDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase use;

    use = ETextCase.ensure(textCase).appendWords(
        "the values of dimension", //$NON-NLS-1$
        textOut);
    textOut.append(' ');
    use = SemanticComponentUtils.printLongAndShortNameIfDifferent(
        this.m_xAxisTransformation.m_dimension, textOut, use);
    if (!(this.m_xAxisTransformation.isIdentityTransformation())) {
      textOut.append(' ');
      use = use.appendWords("transformed according to", //$NON-NLS-1$
          textOut);
      textOut.append(' ');
      this.xAxisMathRender(textOut, DefaultParameterRenderer.INSTANCE);
    }

    return use;
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#getPathComponentSuggestion()}
   * for the {@code x}-axis
   *
   * @return the path component suggestion
   */
  protected String xAxisGetPathComponentSuggestion() {
    return PathUtils.sanitizePathComponent(this.xAxisToString());
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable#mathRender(ITextOutput, IParameterRenderer)}
   * for the {@code x}-axis
   *
   * @param out
   *          the text output device
   * @param renderer
   *          the renderer
   */
  protected void xAxisMathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_xAxisTransformation.m_func.mathRender(out,
        this.__getXAxisParameterRenderer());
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable#mathRender(IMath, IParameterRenderer)}
   * for the {@code x}-axis
   *
   * @param out
   *          the math output device
   * @param renderer
   *          the renderer
   */
  protected void xAxisMathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.m_xAxisTransformation.m_func.mathRender(out,
        this.__getXAxisParameterRenderer());
  }

  /**
   * The method {@link java.lang.Object#toString()} for the {@code x}-axis
   *
   * @return the string
   */
  protected String xAxisToString() {
    final MemoryTextOutput mto;
    mto = new MemoryTextOutput();
    this.xAxisMathRender(mto, this.__getXAxisParameterRenderer());
    return mto.toString();
  }

  /**
   * get the internal parameter renderer for the {@code y}-axis
   *
   * @return the internal parameter renderer for the {@code y}-axis
   */
  final IParameterRenderer _getYAxisParameterRenderer() {
    if (this.m_yAxisParameterRenderer == null) {
      this.m_yAxisParameterRenderer = new __YAxisParameterRenderer();
    }
    return this.m_yAxisParameterRenderer;
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
    return ETextCase.ensure(textCase).nextCase();
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
    ETextCase use;

    use = ETextCase.ensure(textCase).appendWord("the", textOut); //$NON-NLS-1$
    textOut.append(' ');
    use = SemanticComponentUtils.printLongAndShortNameIfDifferent(
        FunctionAttribute.this, textOut, use);
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
      use = use.appendWords("in terms of", textOut);//$NON-NLS-1$
      textOut.append(' ');
      use = this.m_yAxisInputTransformation.m_dimension.printShortName(
          textOut, use);
    } else {
      use = use.appendWords("computed based on", textOut);//$NON-NLS-1$
      textOut.append(' ');
      if (textOut instanceof IComplexText) {
        try (final IMath math = ((IComplexText) textOut).inlineMath()) {
          this.m_yAxisInputTransformation.m_func.mathRender(math,
              this._getYAxisParameterRenderer());
        }
      } else {
        this.m_yAxisInputTransformation.m_func.mathRender(textOut,
            this._getYAxisParameterRenderer());
      }
      use = use.nextCase();
    }

    use = use.appendWord("over", textOut);//$NON-NLS-1$
    textOut.append(' ');
    use = this.xAxisPrintShortName(textOut, use);
    textOut.append('.');
    return use;
  }

  /**
   * The method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#getPathComponentSuggestion()}
   * for the {@code y}-axis
   *
   * @return the path component suggestion
   */
  protected String yAxisGetPathComponentSuggestion() {
    return PathUtils.sanitizePathComponent(this.yAxisToString());
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
   * @param renderer
   *          the parameter renderer
   */
  protected void yAxisRenderXAxisAsParameter(final IMath out,
      final IParameterRenderer renderer) {
    FunctionAttribute.this.m_xAxisTransformation.getDimension()
        .mathRender(out, renderer);
  }

  /**
   * Render the name of {@code x}-axis, as used in the first parameter of
   * the function computed as {@code y}-axis
   *
   * @param out
   *          the output destination
   * @param renderer
   *          the parameter renderer
   */
  protected void yAxisRenderXAxisAsParameter(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append(FunctionAttribute.this.m_xAxisTransformation.getDimension()
        .getName());
  }

  /**
   * Render the name of {@code y}-axis source, as used in the second
   * parameter of the function computed as {@code y}-axis
   *
   * @param out
   *          the output destination
   * @param renderer
   *          the parameter renderer
   */
  protected void yAxisRenderYAxisSourceAsParameter(final IMath out,
      final IParameterRenderer renderer) {
    FunctionAttribute.this.m_yAxisInputTransformation.m_func.mathRender(
        out, renderer);
  }

  /**
   * Render the name of {@code y}-axis source, as used in the second
   * parameter of the function computed as {@code y}-axis
   *
   * @param out
   *          the output destination
   * @param renderer
   *          the parameter renderer
   */
  protected void yAxisRenderYAxisSourceAsParameter(final ITextOutput out,
      final IParameterRenderer renderer) {
    FunctionAttribute.this.m_yAxisInputTransformation.m_func.mathRender(
        out, FunctionAttribute.this._getYAxisParameterRenderer());
  }

  /** The internal class representing the x-axis of a function */
  private final class __FunctionXAxis implements ISemanticMathComponent {

    /** create */
    __FunctionXAxis() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final ETextCase printShortName(final ITextOutput textOut,
        final ETextCase textCase) {
      return FunctionAttribute.this.xAxisPrintShortName(textOut, textCase);
    }

    /** {@inheritDoc} */
    @Override
    public final ETextCase printLongName(final ITextOutput textOut,
        final ETextCase textCase) {
      return FunctionAttribute.this.xAxisPrintLongName(textOut, textCase);
    }

    /** {@inheritDoc} */
    @Override
    public final ETextCase printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      return FunctionAttribute.this.xAxisPrintDescription(textOut,
          textCase);
    }

    /** {@inheritDoc} */
    @Override
    public final String getPathComponentSuggestion() {
      return FunctionAttribute.this.xAxisGetPathComponentSuggestion();
    }

    /** {@inheritDoc} */
    @Override
    public final void mathRender(final ITextOutput out,
        final IParameterRenderer renderer) {
      FunctionAttribute.this.xAxisMathRender(out, renderer);
    }

    /** {@inheritDoc} */
    @Override
    public final void mathRender(final IMath out,
        final IParameterRenderer renderer) {
      FunctionAttribute.this.xAxisMathRender(out, renderer);
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return (2434249 ^ FunctionAttribute.this.hashCode());
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
      ((o instanceof FunctionAttribute.__FunctionXAxis) && //
      (FunctionAttribute.this.equals(((__FunctionXAxis) o).__getOwner()))));
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return FunctionAttribute.this.xAxisToString();
    }
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
      final IParameterRenderer next;
      if (index == 0) {
        out.append(FunctionAttribute.this.getShortName());
        out.append('(');
        next = FunctionAttribute.this._getYAxisParameterRenderer();
        FunctionAttribute.this.yAxisRenderXAxisAsParameter(out, next);
        out.append(',');
        FunctionAttribute.this
            .yAxisRenderYAxisSourceAsParameter(out, next);
        out.append(')');
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index, final IMath out) {
      final IParameterRenderer next;

      if (index == 0) {
        next = FunctionAttribute.this._getYAxisParameterRenderer();
        try (final IMath function = out.nAryFunction(
            FunctionAttribute.this.getShortName(), 2, 2)) {
          FunctionAttribute.this.yAxisRenderXAxisAsParameter(function,
              next);
          FunctionAttribute.this.yAxisRenderYAxisSourceAsParameter(
              function, next);
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

  /** The renderer for the {@code y}-axis function */
  private final class __YAxisParameterRenderer extends
      AbstractParameterRenderer {

    /** create */
    __YAxisParameterRenderer() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      if (index == 0) {
        FunctionAttribute.this.m_yAxisInputTransformation.m_dimension
            .mathRender(out, this);
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index, final IMath out) {
      if (index == 0) {
        FunctionAttribute.this.m_yAxisInputTransformation.m_dimension
            .mathRender(out, this);
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return (7324651 ^ FunctionAttribute.this.hashCode());
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
      ((o instanceof FunctionAttribute.__YAxisParameterRenderer) && //
      (FunctionAttribute.this.equals(((__YAxisParameterRenderer) o)
          .__getOwner()))));
    }
  }

  /** The renderer for the {@code x}-axis function */
  private final class __XAxisParameterRenderer extends
      AbstractParameterRenderer {

    /** create */
    __XAxisParameterRenderer() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      if (index == 0) {
        FunctionAttribute.this.m_xAxisTransformation.m_dimension
            .mathRender(out, this);
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index, final IMath out) {
      if (index == 0) {
        FunctionAttribute.this.m_xAxisTransformation.m_dimension
            .mathRender(out, this);
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return (5564633 ^ FunctionAttribute.this.hashCode());
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
      ((o instanceof FunctionAttribute.__XAxisParameterRenderer) && //
      (FunctionAttribute.this.equals(((__XAxisParameterRenderer) o)
          .__getOwner()))));
    }
  }
}
