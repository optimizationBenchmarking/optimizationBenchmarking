package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
 * 3-ary} which is composed of 1 single functions joined with a
 * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
 * 1-ary} function.
 */
final class _Compound1x3 extends TernaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * @serial The
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         1-ary} function used to compute this function's result based
   *         on the results of the 1 child
   *         {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   *         1-ary} functions.
   */
  final UnaryFunction m_result;

  /**
   * @serial The first child
   *         {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   *         3-ary} function which contributes to the result.
   */
  final TernaryFunction m_child1;

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound1x3}
   * , a function which combines the result of 1 child
   * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   * 3-ary} functions by using an
   * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   * 1-ary} function.
   *
   * @param result
   *          The
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          1-ary} function used to compute this function's result based
   *          on the results of the 1 child
   *          {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   *          1-ary} functions.
   * @param child1
   *          The first child
   *          {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   *          3-ary} function which contributes to the result.
   * @throws IllegalArgumentException
   *           if any of the parameters is {@code null}
   */
  _Compound1x3(final UnaryFunction result, final TernaryFunction child1) {
    super();
    if (result == null) {
      throw new IllegalArgumentException( //
          "Result function of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound1x3}, a function which combines the result of 1 child {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction 3-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_result = result;
    if (child1 == null) {
      throw new IllegalArgumentException( //
          "Child function 1 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound1x3}, a function which combines the result of 1 child {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction 3-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child1 = child1;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2) {
    return this.m_result.computeAsByte(this.m_child1.computeAsByte(x0, x1,
        x2));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2) {
    return this.m_result.computeAsShort(this.m_child1.computeAsShort(x0,
        x1, x2));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2) {
    return this.m_result.computeAsInt(this.m_child1.computeAsInt(x0, x1,
        x2));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2) {
    return this.m_result.computeAsLong(this.m_child1.computeAsLong(x0, x1,
        x2));
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1,
      final float x2) {
    return this.m_result.computeAsFloat(this.m_child1.computeAsFloat(x0,
        x1, x2));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2) {
    return this.m_result.computeAsDouble(this.m_child1.computeAsDouble(x0,
        x1, x2));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2) {
    return this.m_result.computeAsDouble(this.m_child1.computeAsDouble(x0,
        x1, x2));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2) {
    return this.m_result.computeAsDouble(this.m_child1.computeAsDouble(x0,
        x1, x2));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return (this.m_result.isLongArithmeticAccurate() && this.m_child1
        .isLongArithmeticAccurate());
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_result
    .render(out, new __Compound1x3ParameterRenderer(renderer));
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final IMath out,
      final IParameterRenderer renderer) {
    this.m_result
    .render(out, new __Compound1x3ParameterRenderer(renderer));
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
    final _Compound1x3 other;
    if (o == this) {
      return true;
    }
    if (o instanceof _Compound1x3) {
      other = ((_Compound1x3) o);
      return (this.m_result.equals(other.m_result) && this.m_child1
          .equals(other.m_child1));
    }
    return false;
  }

  /**
   * This is the automatically generated code for a
   * {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer
   * parameter renderer} for the {@link _Compound1x3 parameter renderer}
   * for the
   */
  private final class __Compound1x3ParameterRenderer implements
  IParameterRenderer {

    /**
     * @serial the instance of
     *         {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer}
     *         to delegate to.
     */
    private final IParameterRenderer m_renderer;

    /**
     * Create the the
     * {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer
     * parameter renderer} of the
     * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound1x3}
     * , a function which returns a constant value.
     *
     * @param renderer
     *          the instance of
     *          {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer}
     *          to delegate to.
     * @throws IllegalArgumentException
     *           if {@code renderer} is {@code null}
     */
    __Compound1x3ParameterRenderer(final IParameterRenderer renderer) {
      super();
      if (renderer == null) {
        throw new IllegalArgumentException( //
            "Original parameter renderer of the the {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer parameter renderer} of the {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound1x3}, a function which returns a constant value, cannot be null."); //$NON-NLS-1$
      }
      this.m_renderer = renderer;
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      switch (index) {
        case 0: {
          _Compound1x3.this.m_child1.render(out, this.m_renderer);
          return;
        }
        default: {
          throw new IllegalArgumentException( //
              "Only parameter indexes from 0 to 0 are valid, but " //$NON-NLS-1$
              + index + " was provided." //$NON-NLS-1$
              );
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index, final IMath out) {
      switch (index) {
        case 0: {
          _Compound1x3.this.m_child1.render(out, this.m_renderer);
          return;
        }
        default: {
          throw new IllegalArgumentException( //
              "Only parameter indexes from 0 to 0 are valid, but " //$NON-NLS-1$
              + index + " was provided." //$NON-NLS-1$
              );
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return HashUtils.combineHashes(HashUtils.hashCode(this.m_renderer),
          HashUtils.hashCode(_Compound1x3.this));
    }

    /**
     * Get the owning instance of this renderer.
     *
     * @return the owning instance of this renderer
     */
    private final _Compound1x3 __owner() {
      return _Compound1x3.this;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      final __Compound1x3ParameterRenderer other;
      if (o == this) {
        return true;
      }
      if (o instanceof __Compound1x3ParameterRenderer) {
        other = ((__Compound1x3ParameterRenderer) o);
        return ((this.m_renderer.equals(other.m_renderer) && _Compound1x3.this
            .equals(other.__owner())));
      }
      return false;
    }
  }
}
