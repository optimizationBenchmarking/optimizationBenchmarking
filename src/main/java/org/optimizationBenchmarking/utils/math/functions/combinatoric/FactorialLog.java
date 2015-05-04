package org.optimizationBenchmarking.utils.math.functions.combinatoric;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** The logarithm of the factorial function */
public final class FactorialLog extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final FactorialLog INSTANCE = new FactorialLog();

  /** the factorials logarithms */
  static final double[] FACTORIAL_LOGS;

  static {
    int i;
    long d;

    i = Factorial.FACTORIALS.length;
    FACTORIAL_LOGS = new double[i];
    for (; (--i) >= 0;) {
      d = Factorial.FACTORIALS[i];
      if (d > 1L) {
        FactorialLog.FACTORIAL_LOGS[i] = Math.log(d);
      } else {
        FactorialLog.FACTORIAL_LOGS[i] = 0d;
      }
    }

  }

  /** instantiate */
  private FactorialLog() {
    super();
  }

  /**
   * precisely compute the factorial log
   *
   * @param start
   *          the start
   * @param end
   *          the end
   * @return the value
   */
  private static final double factorialLogI(final int start, final int end) {
    int mid;

    if (start >= end) {
      return Math.log(start);
    }

    mid = ((start + end) >>> 1);
    return (FactorialLog.factorialLogI(start, mid) + //
    FactorialLog.factorialLogI(mid + 1, end));
  }

  /**
   * precisely compute the factorial log
   *
   * @param start
   *          the start
   * @param end
   *          the end
   * @return the value
   */
  private static final double factorialLogL(final long start,
      final long end) {
    long mid;

    if (start >= end) {
      return Math.log(start);
    }

    mid = ((start + end) >>> 1);
    return (FactorialLog.factorialLogL(start, mid) + //
    FactorialLog.factorialLogL(mid + 1, end));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long i) {
    double d;
    int v;

    if (i <= 1L) {
      return 0d;
    }
    if (i < FactorialLog.FACTORIAL_LOGS.length) {
      return FactorialLog.FACTORIAL_LOGS[(int) i];
    }

    if (i > Integer.MAX_VALUE) {
      d = FactorialLog.factorialLogL((Integer.MAX_VALUE + 1L), i);
      v = Integer.MAX_VALUE;
    } else {
      d = 0d;
      v = ((int) i);
    }

    return (d + FactorialLog.factorialLogI(2, v));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (x1 <= 1d) {
      return 0d;
    }
    if (x1 > Long.MAX_VALUE) {
      return Double.POSITIVE_INFINITY;
    }
    return this.computeAsDouble(Math.round(x1));
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
    return FactorialLog.INSTANCE;
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
    return FactorialLog.INSTANCE;
  }
}
