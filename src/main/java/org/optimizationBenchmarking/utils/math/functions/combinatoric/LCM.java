package org.optimizationBenchmarking.utils.math.functions.combinatoric;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/**
 * The lcm (least common multiple) function
 */
public final class LCM extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final LCM INSTANCE = new LCM();

  /** instantiate */
  private LCM() {
    super();
  }

  /**
   * compute the least common multiple
   * 
   * @param a
   *          the n
   * @param b
   *          the k
   * @return the coefficient
   */
  @Override
  public final int computeAsInt(final int a, final int b) {
    int x, y, t;

    x = Math.abs(a);
    y = Math.abs(b);

    if (x > y) {
      t = x;
      x = y;
      y = t;
    }

    return Math.abs((x / GCD.INSTANCE.computeAsInt(x, y)) * y);
  }

  /**
   * compute the least common multiple
   * 
   * @param a
   *          the n
   * @param b
   *          the k
   * @return the coefficient
   */
  @Override
  public final long computeAsLong(final long a, final long b) {
    long x, y, t;

    x = Math.abs(a);
    y = Math.abs(b);

    if (x > y) {
      t = x;
      x = y;
      y = t;
    }

    return Math.abs((x / GCD.INSTANCE.computeAsLong(x, y)) * y);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2) {
    return LCM.INSTANCE.computeAsLong(Math.round(x1), Math.round(x2));
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
    return LCM.INSTANCE;
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
    return LCM.INSTANCE;
  }
}
