package org.optimizationBenchmarking.experimentation.attributes.functions;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.text.AbstractParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A unary function which receives values from a given dimension as input
 * and transforms potentially them based on information obtain from
 * experiment parameters or instance features.
 * 
 * @param <RT>
 *          the renderable type
 */
class _Transformation<RT extends IMathRenderable> implements
    ISemanticMathComponent {

  /** the function to be applied to the input data */
  final UnaryFunction m_func;

  /** the internal property-based constants */
  private final _PropertyConstant[] m_constants;

  /** the marker that this transformation is blocked because it is in use */
  private volatile boolean m_isInUse;

  /** the internal name resolver */
  private final __Renderer m_renderer;

  /** the internal math renderable */
  final RT m_renderable;

  /** the hash code */
  private final int m_hashCode;

  /**
   * Create the data transformation
   * 
   * @param function
   *          the function to be applied
   * @param constants
   *          the constants
   * @param parameter
   *          the single parameter
   */
  _Transformation(final UnaryFunction function,
      final _PropertyConstant[] constants, final RT parameter) {
    super();

    if (parameter == null) {
      throw new IllegalArgumentException(//
          "The single parameter renderable of a transformation cannot be null."); //$NON-NLS-1$
    }
    if (function == null) {
      throw new IllegalArgumentException(//
          "The transformation function cannot be null."); //$NON-NLS-1$
    }

    this.m_renderable = parameter;
    this.m_func = function;
    this.m_constants = (((constants == null) || (constants.length <= 0)) ? null
        : constants);
    this.m_renderer = new __Renderer();

    this.m_hashCode = HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_func),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_constants),//
            HashUtils.hashCode(parameter)));
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(ITextOutput out, IParameterRenderer renderer) {
    this.m_func.mathRender(out, this.m_renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(IMath out, IParameterRenderer renderer) {
    this.m_func.mathRender(out, this.m_renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    final MemoryTextOutput mto;
    mto = new MemoryTextOutput();
    this.m_func.mathRender(mto, this.m_renderer);
    return PathUtils.sanitizePathComponent(mto.toString());
  }

  /**
   * Provide the data transformation function based on a given data element
   * 
   * @param element
   *          the data element
   * @return the transformation function
   */
  public synchronized final TransformationFunction use(
      final IDataElement element) {

    if (this.m_constants != null) {
      if (this.m_isInUse) {
        throw new IllegalStateException("The data transformation " + //$NON-NLS-1$
            this.m_func + " is already in use concurrently.."); //$NON-NLS-1$
      }

      for (_PropertyConstant constant : this.m_constants) {
        constant._update(element);
      }
    }

    this.m_isInUse = true;
    return new TransformationFunction(this);
  }

  /** notice that a function application has been finished */
  synchronized final void _endUse() {
    final boolean isInUse;

    isInUse = this.m_isInUse;
    this.m_isInUse = false;

    if (isInUse) {
      if (this.m_constants != null) {
        for (_PropertyConstant constant : this.m_constants) {
          constant._clear();
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(ITextOutput textOut,
      ETextCase textCase) {
    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.mathRender(math, DefaultParameterRenderer.INSTANCE);
      }
    } else {
      this.mathRender(textOut, DefaultParameterRenderer.INSTANCE);
    }
    return ETextCase.ensure(textCase).nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(ITextOutput textOut,
      ETextCase textCase) {
    return this.printShortName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printDescription(ITextOutput textOut, ETextCase textCase) {
    ETextCase next;

    next = ETextCase.ensure(textCase);
    next = next.appendWords("The values of dimension", textOut);//$NON-NLS-1$
    textOut.append(' ');

    if (this.m_renderable instanceof ISemanticComponent) {
      next = ((ISemanticComponent) (this.m_renderable)).printLongName(
          textOut, next);
    } else {
      if (textOut instanceof IComplexText) {
        try (final IMath math = ((IComplexText) textOut).inlineMath()) {
          this.m_renderable.mathRender(math, this.m_renderer);
        }
      } else {
        this.m_renderable.mathRender(textOut, this.m_renderer);
      }
      next = next.nextCase();
    }

    if (!(this.m_func instanceof Identity)) {
      textOut.append(' ');
      next = next.appendWords("transformed according to", textOut); //$NON-NLS-1$
      textOut.append(' ');
      if (textOut instanceof IComplexText) {
        try (final IMath math = ((IComplexText) textOut).inlineMath()) {
          this.mathRender(math, this.m_renderer);
        }
      } else {
        this.mathRender(textOut, this.m_renderer);
      }
    }

    textOut.append('.');
    return next;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_hashCode;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final _Transformation<?> other;
    if (o == this) {
      return true;
    }
    if (o instanceof _Transformation) {
      other = ((_Transformation<?>) o);
      return ((this.m_func.equals(other.m_func)) && //
      (Arrays.equals(this.m_constants, other.m_constants) && //
      this.m_renderable.equals(other.m_renderable)));
    }
    return true;
  }

  /** the internal renderer class */
  private final class __Renderer extends AbstractParameterRenderer {

    /** create the renderer */
    __Renderer() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(int index, ITextOutput out) {
      if (index == 0) {
        _Transformation.this.m_renderable.mathRender(out, this);
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(int index, IMath out) {
      if (index == 0) {
        _Transformation.this.m_renderable.mathRender(out, this);
      } else {
        AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
      }
    }
  }
}
