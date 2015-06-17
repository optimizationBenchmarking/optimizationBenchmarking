package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.AbstractParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
 * 1-ary} which is composed of 2 single functions joined with a
 * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
 * 2-ary} function.
 */
final class _Compound2x1 extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * @serial The
   *         {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *         2-ary} function used to compute this function's result based
   *         on the results of the 2 child
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         2-ary} functions.
   */
  final BinaryFunction m_result;

  /**
   * @serial The first child
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         1-ary} function which contributes to the result.
   */
  final UnaryFunction m_child1;

  /**
   * @serial The second child
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         1-ary} function which contributes to the result.
   */
  final UnaryFunction m_child2;

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x1}
   * , a function which combines the result of 2 child
   * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   * 1-ary} functions by using an
   * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   * 2-ary} function.
   *
   * @param result
   *          The
   *          {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *          2-ary} function used to compute this function's result based
   *          on the results of the 2 child
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          2-ary} functions.
   * @param child1
   *          The first child
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          1-ary} function which contributes to the result.
   * @param child2
   *          The second child
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          1-ary} function which contributes to the result.
   * @throws IllegalArgumentException
   *           if any of the parameters is {@code null}
   */
  _Compound2x1(final BinaryFunction result, final UnaryFunction child1,
      final UnaryFunction child2) {
    super();
    if (result == null) {
      throw new IllegalArgumentException( //
          "Result function of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x1}, a function which combines the result of 2 child {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_result = result;
    if (child1 == null) {
      throw new IllegalArgumentException( //
          "Child function 1 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x1}, a function which combines the result of 2 child {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child1 = child1;
    if (child2 == null) {
      throw new IllegalArgumentException( //
          "Child function 2 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x1}, a function which combines the result of 2 child {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child2 = child2;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0) {
    return this.m_result.computeAsByte(this.m_child1.computeAsByte(x0),
        this.m_child2.computeAsByte(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0) {
    return this.m_result.computeAsShort(this.m_child1.computeAsShort(x0),
        this.m_child2.computeAsShort(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0) {
    return this.m_result.computeAsInt(this.m_child1.computeAsInt(x0),
        this.m_child2.computeAsInt(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    return this.m_result.computeAsLong(this.m_child1.computeAsLong(x0),
        this.m_child2.computeAsLong(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0) {
    return this.m_result.computeAsFloat(this.m_child1.computeAsFloat(x0),
        this.m_child2.computeAsFloat(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0),
        this.m_child2.computeAsDouble(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0),
        this.m_child2.computeAsDouble(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0),
        this.m_child2.computeAsDouble(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return (this.m_result.isLongArithmeticAccurate()
        && this.m_child1.isLongArithmeticAccurate() && this.m_child2
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
        new __Compound2x1ParameterRenderer(renderer));
    this.m_result.mathRender(internalMath, internalMath.m_renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_result.mathRender(out, new __Compound2x1ParameterRenderer(
        renderer));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_result),
        HashUtils.combineHashes(HashUtils.hashCode(this.m_child1),
            HashUtils.hashCode(this.m_child2)));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final _Compound2x1 other;
    if (o == this) {
      return true;
    }
    if (o instanceof _Compound2x1) {
      other = ((_Compound2x1) o);
      return (this.m_result.equals(other.m_result)
          && this.m_child1.equals(other.m_child1) && this.m_child2
          .equals(other.m_child2));
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
   * parameter renderer} of the {@link _Compound2x1}.
   */
  private final class __Compound2x1ParameterRenderer extends
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
     * parameter renderer} of the {@link _Compound2x1}
     *
     * @param renderer
     *          the
     *          {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer
     *          parameter renderer} to bridge to
     * @throws IllegalArgumentException
     *           if {@code renderer} is {@code null}
     */
    __Compound2x1ParameterRenderer(final IParameterRenderer renderer) {
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
              || (_Compound2x1.this.m_child1.getPrecedencePriority() > _Compound2x1.this.m_result
                  .getPrecedencePriority())) {
            _Compound2x1.this.m_child1.mathRender(out, this.m_renderer);
          } else {
            try (final IMath braces = out.inBraces()) {
              _Compound2x1.this.m_child1.mathRender(braces,
                  this.m_renderer);
            }
          }
          return;
        }
        case 1: {
          if (this.m_bracesNotNeeded
              || (_Compound2x1.this.m_child2.getPrecedencePriority() >= _Compound2x1.this.m_result
              .getPrecedencePriority())) {
            _Compound2x1.this.m_child2.mathRender(out, this.m_renderer);
          } else {
            try (final IMath braces = out.inBraces()) {
              _Compound2x1.this.m_child2.mathRender(braces,
                  this.m_renderer);
            }
          }
          return;
        }
        default: {
          AbstractParameterRenderer.throwInvalidParameterIndex(index, 1);
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
          braces = (_Compound2x1.this.m_child1.getPrecedencePriority() <= _Compound2x1.this.m_result
              .getPrecedencePriority());
          if (braces) {
            out.append('(');
          }
          _Compound2x1.this.m_child1.mathRender(out, this.m_renderer);
          if (braces) {
            out.append(')');
          }
          return;
        }
        case 1: {
          braces = (_Compound2x1.this.m_child2.getPrecedencePriority() < _Compound2x1.this.m_result
              .getPrecedencePriority());
          if (braces) {
            out.append('(');
          }
          _Compound2x1.this.m_child2.mathRender(out, this.m_renderer);
          if (braces) {
            out.append(')');
          }
          return;
        }
        default: {
          AbstractParameterRenderer.throwInvalidParameterIndex(index, 1);
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return HashUtils.combineHashes(HashUtils.hashCode(this.m_renderer),
          _Compound2x1.this.hashCode());
    }

    /**
     * the internal owner getter
     *
     * @return the owning {@link _Compound2x1} instance
     */
    private final _Compound2x1 __getOwner() {
      return _Compound2x1.this;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      final __Compound2x1ParameterRenderer other;
      if (o == this) {
        return true;
      }
      if (o instanceof __Compound2x1ParameterRenderer) {
        other = ((__Compound2x1ParameterRenderer) o);
        return ((this.m_renderer.equals(other.m_renderer)) && (_Compound2x1.this
            .equals(other.__getOwner())));
      }
      return false;
    }
  }
}
