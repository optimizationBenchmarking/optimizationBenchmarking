package org.optimizationBenchmarking.utils.math.units;

import org.optimizationBenchmarking.utils.math.Rational;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Negate;

/** a conversion function */
class _ConversionFunction extends UnaryFunction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** the inverse conversation */
  UnaryFunction m_inverse;

  /** create */
  _ConversionFunction() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte compute(final byte x0) {
    return ((byte) (Math.max(Byte.MIN_VALUE,
        Math.min(Byte.MAX_VALUE, this.compute((long) x0)))));
  }

  /** {@inheritDoc} */
  @Override
  public final short compute(final short x0) {
    return ((short) (Math.max(Short.MIN_VALUE,
        Math.min(Short.MAX_VALUE, this.compute((long) x0)))));
  }

  /** {@inheritDoc} */
  @Override
  public final int compute(final int x0) {
    return ((int) (Math.max(Integer.MIN_VALUE,
        Math.min(Integer.MAX_VALUE, this.compute((long) x0)))));
  }

  /** {@inheritDoc} */
  @Override
  public long compute(final long x0) {
    return ((long) (Math.max(Long.MIN_VALUE,//
        Math.min(Long.MAX_VALUE,//
            this.compute((double) x0)))));
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction invertFor(final int index) {
    if (index == 0) {
      return this.m_inverse;
    }
    throw new IllegalArgumentException();
  }

  /**
   * Get a conversion function with the given factor.
   *
   * @param n
   *          the number
   * @return the function
   */
  static final UnaryFunction _get(final Number n) {
    long l;
    double d;
    Rational r, inv;

    if (n instanceof Long) {
      l = n.longValue();
      if (l == 1L) {
        return Identity.INSTANCE;
      }
      if (l == (-1L)) {
        return Negate.INSTANCE;
      }
      return new _ConvertLongMul(l);
    }

    if (n instanceof Rational) {
      r = ((Rational) n);
      inv = r.inverse();
      if (inv.isInteger()) {
        l = inv.longValue();
        if (l == 1L) {
          return Identity.INSTANCE;
        }
        if (l == (-1L)) {
          return Negate.INSTANCE;
        }
        return new _ConvertLongDiv(l);
      }
      return new _ConvertRationalMul(r);
    }

    if (n != null) {
      d = n.doubleValue();
      if (d == 1d) {
        return Identity.INSTANCE;
      }
      if (d == (-1d)) {
        return Negate.INSTANCE;
      }
      return new _ConvertDoubleMul(d);
    }

    return null;
  }
}
