package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.functions.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
 * 3-ary} which returns the firstof its input values.
 */
final class _Select1of3 extends TernaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /**
   * The globally shared instance of the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Select1of3}
   * , a
   * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   * 3-ary} function which returns the value of the first one of its
   * parameters
   */
  static final _Select1of3 INSTANCE = new _Select1of3();

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Select1of3}
   * , a
   * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   * 3-ary} function which returns the value of the first one of its
   * parameters.
   */
  private _Select1of3() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1,
      final float x2) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final IMath out,
      final IParameterRenderer renderer) {
    renderer.renderParameter(0, out);
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final ITextOutput out,
      final IParameterRenderer renderer) {
    renderer.renderParameter(0, out);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return 644245099;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o instanceof _Select1of3);
  }

  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object writeReplace() {
    return _Select1of3.INSTANCE;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} after serialization,
   * i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   *
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object readResolve() {
    return _Select1of3.INSTANCE;
  }
}
