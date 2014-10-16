package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;

/**
 * The {@code "+"} function for three arguments, with the goal to compute
 * without overflow
 */
public final class Add3 extends TernaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the ternary adder */
  public static final Add3 INSTANCE = new Add3();

  /** instantiate */
  private Add3() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte compute(final byte x0, final byte x1, final byte x2) {
    return ((byte) (x0 + x1 + x2));
  }

  /** {@inheritDoc} */
  @Override
  public final short compute(final short x0, final short x1, final short x2) {
    return ((short) (x0 + x1 + x2));
  }

  /** {@inheritDoc} */
  @Override
  public final int compute(final int x0, final int x1, final int x2) {
    return (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final long compute(final long x0, final long x1, final long x2) {
    return (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final float compute(final float x0, final float x1, final float x2) {
    return ((float) (this.compute(((double) x0), ((double) x1),
        ((double) x2))));
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x0, final double x1,
      final double x2) {
    double s1, s2, s3, c1, c2, c3, y, t;
    boolean n, p, us1, us2, us3;

    us1 = us2 = us3 = n = p = false;

    // (x0 + x1) + x2
    c1 = 0d;
    s1 = x0;
    y = (x1 - c1);
    t = (s1 + y);
    c1 = ((t - s1) - y);
    s1 = t;
    y = (x2 - c1);
    t = (s1 + y);
    c1 = ((t - s1) - y);
    s1 = t;

    if (s1 <= Double.NEGATIVE_INFINITY) {
      n = true;
    } else {
      if (s1 >= Double.POSITIVE_INFINITY) {
        p = true;
      } else {
        if (s1 == s1) {
          us1 = true;
          c1 = Math.abs(c1);
        }
      }
    }

    // (x0 + x2) + x1
    c2 = 0d;
    s2 = x0;
    y = (x2 - c2);
    t = (s2 + y);
    c2 = ((t - s2) - y);
    s2 = t;
    y = (x1 - c2);
    t = (s2 + y);
    c2 = ((t - s2) - y);
    s2 = t;

    if (s2 <= Double.NEGATIVE_INFINITY) {
      n = true;
    } else {
      if (s2 >= Double.POSITIVE_INFINITY) {
        p = true;
      } else {
        if (s2 == s2) {
          us2 = true;
          c2 = Math.abs(c2);
        }
      }
    }

    // (x1 + x2) + x0
    c3 = 0d;
    s3 = x1;
    y = (x2 - c3);
    t = (s3 + y);
    c3 = ((t - s3) - y);
    s3 = t;
    y = (x0 - c2);
    t = (s3 + y);
    c3 = ((t - s3) - y);
    s3 = t;

    if (s3 <= Double.NEGATIVE_INFINITY) {
      n = true;
    } else {
      if (s3 >= Double.POSITIVE_INFINITY) {
        p = true;
      } else {
        if (s3 == s3) {
          us3 = true;
          c3 = Math.abs(c3);
        }
      }
    }

    if (us1) {
      if (us2 && (c2 < c1)) {
        s1 = s2;
        c1 = c2;
      }
      if (us3 && (c3 < c1)) {
        return s3;
      }
      return s1;
    }
    if (us2) {
      if (us3 && (c3 < c2)) {
        return s3;
      }
      return s2;
    }
    if (us3) {
      return s3;
    }

    if (n && (!p)) {
      return Double.NEGATIVE_INFINITY;
    }
    if (p && (!n)) {
      return Double.POSITIVE_INFINITY;
    }

    n = ((x0 <= Double.NEGATIVE_INFINITY)
        || (x1 <= Double.NEGATIVE_INFINITY) || (x2 <= Double.NEGATIVE_INFINITY));
    p = ((x0 >= Double.POSITIVE_INFINITY)
        || (x1 >= Double.POSITIVE_INFINITY) || (x2 >= Double.POSITIVE_INFINITY));
    if (n) {
      if (p) {
        return Double.NaN;
      }
      return Double.NEGATIVE_INFINITY;
    }
    if (p) {
      return Double.POSITIVE_INFINITY;
    }

    n = ((x0 < 0d) || (x1 < 0d) || (x2 < 0d));
    p = ((x0 > 0d) || (x1 > 0d) || (x2 > 0d));
    if (n) {
      if (p) {
        return Double.NaN;
      }
      return Double.NEGATIVE_INFINITY;
    }
    if (p) {
      return Double.POSITIVE_INFINITY;
    }

    return Double.NaN;
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
    return Absolute.INSTANCE;
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
    return Absolute.INSTANCE;
  }
}
