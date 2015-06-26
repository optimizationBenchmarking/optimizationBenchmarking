package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.text.AbstractParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A transformation to be applied to values from a given dimension.
 */
public final class DimensionTransformation extends Transformation
    implements ISemanticMathComponent {

  /** the dimension */
  final IDimension m_dimension;

  /** the internal parameter renderer */
  private transient IParameterRenderer m_renderer;

  /**
   * Create the data transformation
   *
   * @param function
   *          the function to be applied
   * @param constants
   *          the constants
   * @param dimension
   *          the dimension
   */
  DimensionTransformation(final UnaryFunction function,
      final _DataBasedConstant[] constants, final IDimension dimension) {
    super(function, constants);

    if (dimension == null) {
      throw new IllegalArgumentException(//
          "Dimension for DimensionTransformation cannot be null."); //$NON-NLS-1$
    }
    this.m_dimension = dimension;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return (this.m_dimension.getDataType().isInteger() && //
    this.m_func.isLongArithmeticAccurate());
  }

  /**
   * Create the data transformation
   *
   * @param dimension
   *          the dimension
   */
  public DimensionTransformation(final IDimension dimension) {
    this(Identity.INSTANCE, null, dimension);
  }

  /**
   * The dimension to be transformed by this transformer
   *
   * @return dimension to be transformed by this transformer
   */
  public final IDimension getDimension() {
    return this.m_dimension;
  }

  /**
   * Get the parameter renderer for this dimension transformation
   *
   * @return the parameter renderer
   */
  final IParameterRenderer __getParameterRenderer() {
    if (this.m_renderer == null) {
      this.m_renderer = new __ParameterRenderer();
    }
    return this.m_renderer;
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    if (this.isIdentityTransformation()) {
      return this.m_dimension.printShortName(textOut, textCase);
    }

    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.mathRender(math, this.__getParameterRenderer());
      }
    } else {
      this.mathRender(textOut, this.__getParameterRenderer());
    }

    return ETextCase.ensure(textCase).nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    if (this.isIdentityTransformation()) {
      return this.m_dimension.printLongName(textOut, textCase);
    }

    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.mathRender(math, this.__getParameterRenderer());
      }
    } else {
      this.mathRender(textOut, this.__getParameterRenderer());
    }

    return ETextCase.ensure(textCase).nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase use;

    use = ETextCase.ensure(textCase).appendWords(
        "the values of dimension", //$NON-NLS-1$
        textOut);
    textOut.append(' ');
    use = SemanticComponentUtils.printLongAndShortNameIfDifferent(
        this.m_dimension, textOut, use);
    if (!(this.isIdentityTransformation())) {
      textOut.append(' ');
      use = use.appendWords("transformed according to", //$NON-NLS-1$
          textOut);
      textOut.append(' ');
      this.mathRender(textOut, this.__getParameterRenderer());
    }

    return use;
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    final MemoryTextOutput mto;
    mto = new MemoryTextOutput();
    this.mathRender(mto, this.__getParameterRenderer());
    return PathUtils.sanitizePathComponent(mto.toString());
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_func.mathRender(out, this.__getParameterRenderer());
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.m_func.mathRender(out, this.__getParameterRenderer());
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    this.mathRender(mto, DefaultParameterRenderer.INSTANCE);
    return mto.toString();
  }

  /** The internal parameter renderer class. */
  private final class __ParameterRenderer extends
      AbstractParameterRenderer {

    /** create */
    __ParameterRenderer() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      if (index == 0) {
        DimensionTransformation.this.m_dimension.mathRender(out, this);
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index, final IMath out) {
      if (index == 0) {
        DimensionTransformation.this.m_dimension.mathRender(out, this);
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return (7324651 ^ DimensionTransformation.this.hashCode());
    }

    /**
     * get the owning function attribute
     *
     * @return the owning function attribute
     */
    private final DimensionTransformation __getOwner() {
      return DimensionTransformation.this;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      return ((o == this) || //
      ((o instanceof DimensionTransformation.__ParameterRenderer) && //
      (DimensionTransformation.this.equals(((__ParameterRenderer) o)
          .__getOwner()))));
    }
  }
}
