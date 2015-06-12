package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
 * 1-ary} which is composed of 4 single functions joined with a
 * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
 * 4-ary} function.
 */
final class _Compound4x1 extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * @serial The
   *         {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *         4-ary} function used to compute this function's result based
   *         on the results of the 4 child
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         4-ary} functions.
   */
  final QuaternaryFunction m_result;

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
   * @serial The third child
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         1-ary} function which contributes to the result.
   */
  final UnaryFunction m_child3;

  /**
   * @serial The fourth child
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         1-ary} function which contributes to the result.
   */
  final UnaryFunction m_child4;

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound4x1}
   * , a function which combines the result of 4 child
   * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   * 1-ary} functions by using an
   * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   * 4-ary} function.
   *
   * @param result
   *          The
   *          {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *          4-ary} function used to compute this function's result based
   *          on the results of the 4 child
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          4-ary} functions.
   * @param child1
   *          The first child
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          1-ary} function which contributes to the result.
   * @param child2
   *          The second child
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          1-ary} function which contributes to the result.
   * @param child3
   *          The third child
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          1-ary} function which contributes to the result.
   * @param child4
   *          The fourth child
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          1-ary} function which contributes to the result.
   * @throws IllegalArgumentException
   *           if any of the parameters is {@code null}
   */
  _Compound4x1(final QuaternaryFunction result,
      final UnaryFunction child1, final UnaryFunction child2,
      final UnaryFunction child3, final UnaryFunction child4) {
    super();
    if (result == null) {
      throw new IllegalArgumentException( //
          "Result function of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound4x1}, a function which combines the result of 4 child {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_result = result;
    if (child1 == null) {
      throw new IllegalArgumentException( //
          "Child function 1 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound4x1}, a function which combines the result of 4 child {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child1 = child1;
    if (child2 == null) {
      throw new IllegalArgumentException( //
          "Child function 2 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound4x1}, a function which combines the result of 4 child {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child2 = child2;
    if (child3 == null) {
      throw new IllegalArgumentException( //
          "Child function 3 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound4x1}, a function which combines the result of 4 child {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child3 = child3;
    if (child4 == null) {
      throw new IllegalArgumentException( //
          "Child function 4 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound4x1}, a function which combines the result of 4 child {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction 1-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child4 = child4;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0) {
    return this.m_result.computeAsByte(this.m_child1.computeAsByte(x0),
        this.m_child2.computeAsByte(x0), this.m_child3.computeAsByte(x0),
        this.m_child4.computeAsByte(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0) {
    return this.m_result
        .computeAsShort(this.m_child1.computeAsShort(x0),
            this.m_child2.computeAsShort(x0),
            this.m_child3.computeAsShort(x0),
            this.m_child4.computeAsShort(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0) {
    return this.m_result.computeAsInt(this.m_child1.computeAsInt(x0),
        this.m_child2.computeAsInt(x0), this.m_child3.computeAsInt(x0),
        this.m_child4.computeAsInt(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    return this.m_result.computeAsLong(this.m_child1.computeAsLong(x0),
        this.m_child2.computeAsLong(x0), this.m_child3.computeAsLong(x0),
        this.m_child4.computeAsLong(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0) {
    return this.m_result
        .computeAsFloat(this.m_child1.computeAsFloat(x0),
            this.m_child2.computeAsFloat(x0),
            this.m_child3.computeAsFloat(x0),
            this.m_child4.computeAsFloat(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0),
        this.m_child2.computeAsDouble(x0),
        this.m_child3.computeAsDouble(x0),
        this.m_child4.computeAsDouble(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0),
        this.m_child2.computeAsDouble(x0),
        this.m_child3.computeAsDouble(x0),
        this.m_child4.computeAsDouble(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0),
        this.m_child2.computeAsDouble(x0),
        this.m_child3.computeAsDouble(x0),
        this.m_child4.computeAsDouble(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return (this.m_result.isLongArithmeticAccurate()
        && this.m_child1.isLongArithmeticAccurate()
        && this.m_child2.isLongArithmeticAccurate()
        && this.m_child3.isLongArithmeticAccurate() && this.m_child4
        .isLongArithmeticAccurate());
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_result
    .render(out, new __Compound4x1ParameterRenderer(renderer));
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final IMath out,
      final IParameterRenderer renderer) {
    this.m_result
    .render(out, new __Compound4x1ParameterRenderer(renderer));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_result),
        HashUtils.combineHashes(HashUtils.hashCode(this.m_child1),
            HashUtils.combineHashes(HashUtils.hashCode(this.m_child2),
                HashUtils.combineHashes(HashUtils.hashCode(this.m_child3),
                    HashUtils.hashCode(this.m_child4)))));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final _Compound4x1 other;
    if (o == this) {
      return true;
    }
    if (o instanceof _Compound4x1) {
      other = ((_Compound4x1) o);
      return (this.m_result.equals(other.m_result)
          && this.m_child1.equals(other.m_child1)
          && this.m_child2.equals(other.m_child2)
          && this.m_child3.equals(other.m_child3) && this.m_child4
          .equals(other.m_child4));
    }
    return false;
  }

  /**
   * This is the automatically generated code for a
   * {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer
   * parameter renderer} for the {@link _Compound4x1 parameter renderer}
   * for the
   */
  private final class __Compound4x1ParameterRenderer implements
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
     * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound4x1}
     * , a function which returns a constant value.
     *
     * @param renderer
     *          the instance of
     *          {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer}
     *          to delegate to.
     * @throws IllegalArgumentException
     *           if {@code renderer} is {@code null}
     */
    __Compound4x1ParameterRenderer(final IParameterRenderer renderer) {
      super();
      if (renderer == null) {
        throw new IllegalArgumentException( //
            "Original parameter renderer of the the {@link org.optimizationBenchmarking.utils.math.functions.IParameterRenderer parameter renderer} of the {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound4x1}, a function which returns a constant value, cannot be null."); //$NON-NLS-1$
      }
      this.m_renderer = renderer;
    }

    /** {@inheritDoc} */
    @Override
    public final void renderParameter(final int index,
        final ITextOutput out) {
      switch (index) {
        case 0: {
          _Compound4x1.this.m_child1.render(out, this.m_renderer);
          return;
        }
        case 1: {
          _Compound4x1.this.m_child2.render(out, this.m_renderer);
          return;
        }
        case 2: {
          _Compound4x1.this.m_child3.render(out, this.m_renderer);
          return;
        }
        case 3: {
          _Compound4x1.this.m_child4.render(out, this.m_renderer);
          return;
        }
        default: {
          throw new IllegalArgumentException( //
              "Only parameter indexes from 0 to 3 are valid, but " //$NON-NLS-1$
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
          _Compound4x1.this.m_child1.render(out, this.m_renderer);
          return;
        }
        case 1: {
          _Compound4x1.this.m_child2.render(out, this.m_renderer);
          return;
        }
        case 2: {
          _Compound4x1.this.m_child3.render(out, this.m_renderer);
          return;
        }
        case 3: {
          _Compound4x1.this.m_child4.render(out, this.m_renderer);
          return;
        }
        default: {
          throw new IllegalArgumentException( //
              "Only parameter indexes from 0 to 3 are valid, but " //$NON-NLS-1$
              + index + " was provided." //$NON-NLS-1$
              );
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return HashUtils.combineHashes(HashUtils.hashCode(this.m_renderer),
          HashUtils.hashCode(_Compound4x1.this));
    }

    /**
     * Get the owning instance of this renderer.
     *
     * @return the owning instance of this renderer
     */
    private final _Compound4x1 __owner() {
      return _Compound4x1.this;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      final __Compound4x1ParameterRenderer other;
      if (o == this) {
        return true;
      }
      if (o instanceof __Compound4x1ParameterRenderer) {
        other = ((__Compound4x1ParameterRenderer) o);
        return ((this.m_renderer.equals(other.m_renderer) && _Compound4x1.this
            .equals(other.__owner())));
      }
      return false;
    }
  }
}
