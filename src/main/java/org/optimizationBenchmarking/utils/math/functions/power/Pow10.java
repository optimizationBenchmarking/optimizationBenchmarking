package org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The 10^x function
 */
public final class Pow10 extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the base-10 power operator */
  public static final int PRECEDENCE_PRIORITY = Pow.PRECEDENCE_PRIORITY;

  /** the globally shared instance */
  public static final Pow10 INSTANCE = new Pow10();

  /** instantiate */
  private Pow10() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Pow10.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x1) {
    if (x1 < 0) {
      return 0;
    }
    if (x1 < 10) {
      return ((int) (Lg.TABLE[x1]));
    }
    return Integer.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1) {
    if (x1 < 0) {
      return 0;
    }
    if (x1 < Lg.TABLE.length) {
      return Lg.TABLE[(int) x1];
    }
    return Long.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    final int i1;

    if ((x1 > (-Lg.TABLE.length)) && (x1 < Lg.TABLE.length)) {
      i1 = ((int) x1);
      if (i1 == x1) {
        if (i1 >= 0) {
          return Lg.TABLE[i1];
        }
        return (1d / Lg.TABLE[-i1]);
      }
    }

    return Pow.INSTANCE.computeAsDouble(10d, x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Lg invertFor(final int index) {
    return Lg.INSTANCE;
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object writeReplace() {
    return Pow10.INSTANCE;
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
    return Pow10.INSTANCE;
  }
}
