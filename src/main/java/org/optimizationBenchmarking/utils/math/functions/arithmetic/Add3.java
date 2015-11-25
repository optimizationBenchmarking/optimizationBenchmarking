package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;

/**
 * The code of an addition ({@code +}) of 3 arguments designed for
 * highest-precision output.
 */
public final class Add3 extends TernaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of this high-precision addition operator */
  public static final int PRECEDENCE_PRIORITY = Add.PRECEDENCE_PRIORITY;

  /**
   * the globally shared instance of the high-precision addition operator
   */
  public static final Add3 INSTANCE = new Add3();

  /** hidden constructor, use {@link #INSTANCE} */
  private Add3() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2) {
    return (byte) (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2) {
    return (short) (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2) {
    return (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2) {
    return (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Add3.PRECEDENCE_PRIORITY;
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
    return Add3.INSTANCE;
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
    return Add3.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2) {
    return (((((long) x0)) + x1) + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2) {
    double lx;
    return AddN._destructiveSum(new double[] { //
        lx = x0, //
        (x0 - ((long) lx)), //
        lx = x1, //
        (x1 - ((long) lx)), //
        lx = x2, //
        (x2 - ((long) lx)), //
    });
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2) {
    return AddN._destructiveSum(new double[] { x0, x1, x2 });
  }
}