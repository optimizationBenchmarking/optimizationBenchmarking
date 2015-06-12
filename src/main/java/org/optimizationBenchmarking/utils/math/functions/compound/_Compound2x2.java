package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
 * 2-ary} which is composed of 2 single functions joined with a
 * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
 * 2-ary} function.
 */
final class _Compound2x2 extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * @serial The
   *         {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *         2-ary} function used to compute this function's result based
   *         on the results of the 2 child
   *         {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *         2-ary} functions.
   */
  final BinaryFunction m_result;

  /**
   * @serial The first child
   *         {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *         2-ary} function which contributes to the result.
   */
  final BinaryFunction m_child1;

  /**
   * @serial The second child
   *         {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *         2-ary} function which contributes to the result.
   */
  final BinaryFunction m_child2;

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x2}
   * , a function which combines the result of 2 child
   * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   * 2-ary} functions by using an
   * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   * 2-ary} function.
   *
   * @param result
   *          The
   *          {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *          2-ary} function used to compute this function's result based
   *          on the results of the 2 child
   *          {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *          2-ary} functions.
   * @param child1
   *          The first child
   *          {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *          2-ary} function which contributes to the result.
   * @param child2
   *          The second child
   *          {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *          2-ary} function which contributes to the result.
   * @throws IllegalArgumentException
   *           if any of the parameters is {@code null}
   */
  _Compound2x2(final BinaryFunction result, final BinaryFunction child1,
      final BinaryFunction child2) {
    super();
    if (result == null) {
      throw new IllegalArgumentException( //
          "Result function of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x2}, a function which combines the result of 2 child {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_result = result;
    if (child1 == null) {
      throw new IllegalArgumentException( //
          "Child function 1 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x2}, a function which combines the result of 2 child {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child1 = child1;
    if (child2 == null) {
      throw new IllegalArgumentException( //
          "Child function 2 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x2}, a function which combines the result of 2 child {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction 2-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child2 = child2;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1) {
    return this.m_result.computeAsByte(
        this.m_child1.computeAsByte(x0, x1),
        this.m_child2.computeAsByte(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1) {
    return this.m_result.computeAsShort(
        this.m_child1.computeAsShort(x0, x1),
        this.m_child2.computeAsShort(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1) {
    return this.m_result.computeAsInt(this.m_child1.computeAsInt(x0, x1),
        this.m_child2.computeAsInt(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1) {
    return this.m_result.computeAsLong(
        this.m_child1.computeAsLong(x0, x1),
        this.m_child2.computeAsLong(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1) {
    return this.m_result.computeAsFloat(
        this.m_child1.computeAsFloat(x0, x1),
        this.m_child2.computeAsFloat(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0, x1),
        this.m_child2.computeAsDouble(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0, x1),
        this.m_child2.computeAsDouble(x0, x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0, x1),
        this.m_child2.computeAsDouble(x0, x1));
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
  public final void render(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_result
    .render(out, new __Compound2x2ParameterRenderer(renderer));
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final IMath out,
      final IParameterRenderer renderer) {
    this.m_result
    .render(out, new __Compound2x2ParameterRenderer(renderer));
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
    final _Compound2x2 other;
    if (o == this) {
      return true;
    }
    if (o instanceof _Compound2x2) {
      other = ((_Compound2x2) o);
      return (this.m_result.equals(other.m_result)
          && this.m_child1.equals(other.m_child1) && this.m_child2
          .equals(other.m_child2));
    }
    return false;
  }

  /**
   * This is the automatically generated code for a
   * {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer
   * parameter renderer} for the {@link _Compound2x2 parameter renderer}
   * for the
   */
  private final class __Compound2x2ParameterRenderer implements
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
     * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x2}
     * , a function which returns a constant value.
     *
     * @param renderer
     *          the instance of
     *          {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer}
     *          to delegate to.
     * @throws IllegalArgumentException
     *           if {@code renderer} is {@code null}
     */
    __Compound2x2ParameterRenderer(final IParameterRenderer renderer) {
      super();
      if (renderer == null) {
        throw new IllegalArgumentException( //
            "Original parameter renderer of the the {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer parameter renderer} of the {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound2x2}, a function which returns a constant value, cannot be null."); //$NON-NLS-1$
      }
      this.m_renderer = renderer;
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      switch (index) {
        case 0: {
          _Compound2x2.this.m_child1.render(out, this.m_renderer);
          return;
        }
        case 1: {
          _Compound2x2.this.m_child2.render(out, this.m_renderer);
          return;
        }
        default: {
          throw new IllegalArgumentException( //
              "Only parameter indexes from 0 to 1 are valid, but " //$NON-NLS-1$
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
          _Compound2x2.this.m_child1.render(out, this.m_renderer);
          return;
        }
        case 1: {
          _Compound2x2.this.m_child2.render(out, this.m_renderer);
          return;
        }
        default: {
          throw new IllegalArgumentException( //
              "Only parameter indexes from 0 to 1 are valid, but " //$NON-NLS-1$
              + index + " was provided." //$NON-NLS-1$
              );
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return HashUtils.combineHashes(HashUtils.hashCode(this.m_renderer),
          HashUtils.hashCode(_Compound2x2.this));
    }

    /**
     * Get the owning instance of this renderer.
     *
     * @return the owning instance of this renderer
     */
    private final _Compound2x2 __owner() {
      return _Compound2x2.this;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      final __Compound2x2ParameterRenderer other;
      if (o == this) {
        return true;
      }
      if (o instanceof __Compound2x2ParameterRenderer) {
        other = ((__Compound2x2ParameterRenderer) o);
        return ((this.m_renderer.equals(other.m_renderer) && _Compound2x2.this
            .equals(other.__owner())));
      }
      return false;
    }
  }
}
