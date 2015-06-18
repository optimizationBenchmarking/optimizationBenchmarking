package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;
import org.optimizationBenchmarking.utils.math.text.AbstractParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
 * 2-ary} which is composed of 1 single functions joined with a
 * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
 * 1-ary} function.
 */
final class _Compound1x2 extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * @serial The
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         1-ary} function used to compute this function's result based
   *         on the results of the 1 child
   *         {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *         1-ary} functions.
   */
  final UnaryFunction m_result;

  /**
   * @serial The first child
   *         {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *         2-ary} function which contributes to the result.
   */
  final BinaryFunction m_child1;

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound1x2}
   * , a function which combines the result of 1 child
   * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   * 2-ary} functions by using an
   * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   * 1-ary} function.
   *
   * @param result
   *          The
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          1-ary} function used to compute this function's result based
   *          on the results of the 1 child
   *          {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *          1-ary} functions.
   * @param child1
   *          The first child
   *          {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *          2-ary} function which contributes to the result.
   * @throws IllegalArgumentException
   *           if any of the parameters is {@code null}
   */
  _Compound1x2(final UnaryFunction result, final BinaryFunction child1) {
    super();
    if (result == null) {
      throw new IllegalArgumentException( //
          "Result function of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound1x2}, a function which combines the result of 1 child {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_result = result;
    if (child1 == null) {
      throw new IllegalArgumentException( //
          "Child function 1 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound1x2}, a function which combines the result of 1 child {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child1 = child1;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1) {
    return this.m_result
        .computeAsByte(this.m_child1.computeAsByte(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1) {
    return this.m_result.computeAsShort(this.m_child1.computeAsShort(x0,
        x1));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1) {
    return this.m_result.computeAsInt(this.m_child1.computeAsInt(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1) {
    return this.m_result
        .computeAsLong(this.m_child1.computeAsLong(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1) {
    return this.m_result.computeAsFloat(this.m_child1.computeAsFloat(x0,
        x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1) {
    return this.m_result.computeAsDouble(this.m_child1.computeAsDouble(x0,
        x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1) {
    return this.m_result.computeAsDouble(this.m_child1.computeAsDouble(x0,
        x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1) {
    return this.m_result.computeAsDouble(this.m_child1.computeAsDouble(x0,
        x1));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return (this.m_result.isLongArithmeticAccurate() && this.m_child1
        .isLongArithmeticAccurate());
  }

  /** {@inheritDoc} */
  @Override
  public int getPrecedencePriority() {
    return this.m_result.getPrecedencePriority();
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    final _InternalMath internalMath;
    internalMath = new _InternalMath(out,
        new __Compound1x2ParameterRenderer(renderer));
    this.m_result.mathRender(internalMath, internalMath.m_renderer);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    final __Compound1x2ParameterRenderer subRenderer;
    final MemoryTextOutput memoryTextOut;
    final int index;
    subRenderer = new __Compound1x2ParameterRenderer(renderer);
    memoryTextOut = new MemoryTextOutput();
    this.m_result.mathRender(memoryTextOut,
        DefaultParameterRenderer.INSTANCE);
    if ((index = memoryTextOut
        .indexOf(DefaultParameterRenderer.PARAMETER_ENCLOSING_CHAR)) > 0) {
      switch (memoryTextOut.charAt(index - 1)) {
        case '(':
        case '[':
        case '{': {
          subRenderer.m_bracesNotNeeded = true;
        }
      }
    }
    this.m_result.mathRender(out, subRenderer);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_result),
        HashUtils.hashCode(this.m_child1));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final _Compound1x2 other;
    if (o == this) {
      return true;
    }
    if (o instanceof _Compound1x2) {
      other = ((_Compound1x2) o);
      return (this.m_result.equals(other.m_result) && this.m_child1
          .equals(other.m_child1));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput output;
    output = new MemoryTextOutput();
    this.mathRender(output, DefaultParameterRenderer.INSTANCE);
    return output.toString();
  }

  /**
   * This is the automatically generated code of the
   * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
   * parameter renderer} of the {@link _Compound1x2}.
   */
  private final class __Compound1x2ParameterRenderer extends
  _CompoundParameterRendererBase {
    /**
     * the
     * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
     * parameter renderer} to bridge to
     */
    private final IParameterRenderer m_renderer;

    /**
     * Create the
     * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
     * parameter renderer} of the {@link _Compound1x2}
     *
     * @param renderer
     *          the
     *          {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
     *          parameter renderer} to bridge to
     * @throws IllegalArgumentException
     *           if {@code renderer} is {@code null}
     */
    __Compound1x2ParameterRenderer(final IParameterRenderer renderer) {
      super();
      if (renderer == null) {
        throw new IllegalArgumentException( //
            "The parameter renderer to bridge to cannot be null."); //$NON-NLS-1$
      }
      this.m_renderer = renderer;
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index, final IMath out) {
      switch (index) {
        case 0: {
          if (this.m_bracesNotNeeded
              || (_Compound1x2.this.m_child1.getPrecedencePriority() > _Compound1x2.this.m_result
                  .getPrecedencePriority())
                  || (_Compound1x2.this.m_result instanceof Absolute)) {
            _Compound1x2.this.m_child1.mathRender(out, this.m_renderer);
          } else {
            try (final IMath braces = out.inBraces()) {
              _Compound1x2.this.m_child1.mathRender(braces,
                  this.m_renderer);
            }
          }
          return;
        }
        default: {
          AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      final boolean braces;
      switch (index) {
        case 0: {
          braces = ((_Compound1x2.this.m_child1.getPrecedencePriority() <= _Compound1x2.this.m_result
              .getPrecedencePriority())
              && (!(_Compound1x2.this.m_result instanceof Absolute)) && (!(this.m_bracesNotNeeded)));
          if (braces) {
            out.append('(');
          }
          _Compound1x2.this.m_child1.mathRender(out, this.m_renderer);
          if (braces) {
            out.append(')');
          }
          return;
        }
        default: {
          AbstractParameterRenderer.throwInvalidParameterIndex(index, 0);
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return HashUtils.combineHashes(HashUtils.hashCode(this.m_renderer),
          _Compound1x2.this.hashCode());
    }

    /**
     * the internal owner getter
     *
     * @return the owning {@link _Compound1x2} instance
     */
    private final _Compound1x2 __getOwner() {
      return _Compound1x2.this;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      final __Compound1x2ParameterRenderer other;
      if (o == this) {
        return true;
      }
      if (o instanceof __Compound1x2ParameterRenderer) {
        other = ((__Compound1x2ParameterRenderer) o);
        return ((this.m_renderer.equals(other.m_renderer)) && (_Compound1x2.this
            .equals(other.__getOwner())));
      }
      return false;
    }
  }
}
