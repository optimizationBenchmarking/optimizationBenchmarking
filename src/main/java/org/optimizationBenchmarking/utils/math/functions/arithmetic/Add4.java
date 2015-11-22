package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;

/**
 * The {@code "+"} function for four arguments, implemented for maximum
 * numerical stability. The current code is a placeholder.
 */
public final class Add4 extends QuaternaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the ternary add operator */
  public static final int PRECEDENCE_PRIORITY = Add.PRECEDENCE_PRIORITY;

  /** the globally shared instance of the ternary adder */
  public static final Add4 INSTANCE = new Add4();

  /** instantiate */
  private Add4() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2, final byte x3) {
    return ((byte) (x0 + x1 + x2 + x3));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2, final short x3) {
    return ((short) (x0 + x1 + x2 + x3));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2,
      final int x3) {
    return (x0 + x1 + x2 + x3);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2, final long x3) {
    return (x0 + x1 + x2 + x3);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2, final double x3) {
    double a1, a2, a3, a4, b1, b2, b3, b4, t, r1, r2;
    boolean s1, s2, s3, s4;

    a1 = x0;
    b1 = Math.abs(a1);
    a2 = x1;
    b2 = Math.abs(a2);
    a3 = x2;
    b3 = Math.abs(a3);
    a4 = x3;
    b4 = Math.abs(a4);

    // sort in increasing order by absolute value
    if (b3 < b1) {
      t = a1;
      a1 = a3;
      a3 = t;
      t = b1;
      b1 = b3;
      b3 = t;
    }

    if (b4 < b2) {
      t = a2;
      a2 = a4;
      a4 = t;
      t = b2;
      b2 = b4;
      b4 = t;
    }

    if (b2 < b1) {
      t = a1;
      a1 = a2;
      a2 = t;
      t = b1;
      b1 = b2;
      b2 = t;
    }

    if (b4 < b3) {
      t = a3;
      a3 = a4;
      a4 = t;
      t = b3;
      b3 = b4;
      b4 = t;
    }

    if (b3 < b2) {
      t = a3;
      a3 = a2;
      a2 = t;
      t = b3;
      b3 = b2;
      b2 = t;
    }

    // try to achieve alternating signs to avoid overflow
    s1 = (a1 < 0d);
    s2 = (a2 < 0d);
    s3 = (a3 < 0d);
    s4 = (a4 < 0d);
    r1 = Add3.INSTANCE.computeAsDouble(a1, a2, a3);
    if ((s1 && s2 && (!s3)) || //
        ((!s1) && (!s2) && s3) || //
        (s2 && (!s3) && (!s4)) || //
        ((!s2) && s3 && s4)) {

      r2 = Add3.INSTANCE.computeAsDouble(a1, a3, a2);
      if (Math.abs(((r1 - a3) - a2) - a1) >= //
      Math.abs(((r2 - a2) - a3) - a1)) {
        t = a2;
        a2 = a3;
        a3 = t;
        r1 = r2;
      }
    }

    return Add.INSTANCE.computeAsDouble(r1, a4);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2, final long x3) {
    long a1, a2, a3, a4, b1, b2, b3, b4, t;
    boolean s1, s2, s3, s4;

    a1 = x0;
    a2 = x1;
    a3 = x2;
    a4 = x3;
    if ((a1 > Long.MIN_VALUE) && (a2 > Long.MIN_VALUE)
        && (a3 > Long.MIN_VALUE) && (a4 > Long.MIN_VALUE)) {
      b1 = Math.abs(a1);
      b2 = Math.abs(a2);
      b3 = Math.abs(a3);
      b4 = Math.abs(a4);

      // sort in increasing order by absolute value
      if (b3 < b1) {
        t = a1;
        a1 = a3;
        a3 = t;
        t = b1;
        b1 = b3;
        b3 = t;
      }

      if (b4 < b2) {
        t = a2;
        a2 = a4;
        a4 = t;
        t = b2;
        b2 = b4;
        b4 = t;
      }

      if (b2 < b1) {
        t = a1;
        a1 = a2;
        a2 = t;
        t = b1;
        b1 = b2;
        b2 = t;
      }

      if (b4 < b3) {
        t = a3;
        a3 = a4;
        a4 = t;
        t = b3;
        b3 = b4;
        b4 = t;
      }

      if (b3 < b2) {
        t = a3;
        a3 = a2;
        a2 = t;
        t = b3;
        b3 = b2;
        b2 = t;
      }

      // try to achieve alternating signs to avoid overflow
      s1 = (a1 < 0L);
      s2 = (a2 < 0L);
      s3 = (a3 < 0L);
      s4 = (a4 < 0L);
      if ((s1 && (!s2) && (!s3)) || //
          ((!s1) && s2 && s3)) {
        t = a1;
        a1 = a2;
        a2 = t;
        s2 = s1;// !!
      }

      if ((s1 && s2 && (!s3)) || //
          ((!s1) && (!s2) && s3) || //
          (s2 && (!s3) && (!s4)) || //
          ((!s2) && s3 && s4)) {
        t = a2;
        a2 = a3;
        a3 = t;
      }
    }

    return Add.INSTANCE.computeAsDouble(//
        Add3.INSTANCE.computeAsDouble(a1, a2, a3), a4);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2, final int x3) {
    return this.computeAsDouble(((long) x0), ((long) x1), ((long) x2),
        ((long) x3));
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Add4.PRECEDENCE_PRIORITY;
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
    return Add4.INSTANCE;
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
    return Add4.INSTANCE;
  }
}
