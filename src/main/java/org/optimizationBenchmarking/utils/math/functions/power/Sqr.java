package org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The sqr function
 */
public final class Sqr extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the sqr operator */
  public static final int PRECEDENCE_PRIORITY = //
  (int) ((((long) (Integer.MAX_VALUE)) + //
  ((long) (Math.max(Exp.PRECEDENCE_PRIORITY,//
      Math.max(Sqrt.PRECEDENCE_PRIORITY, Pow.PRECEDENCE_PRIORITY))))) / 2);

  /** the square root of {@value java.lang.Long#MAX_VALUE}: {@value} */
  static final long SQRT_LONG_MAX_VALUE = 3037000499L;

  /** the globally shared instance */
  public static final Sqr INSTANCE = new Sqr();

  /** instantiate */
  private Sqr() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Sqr.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x1) {
    int res;

    res = x1;
    res *= res;
    if (res >= Byte.MAX_VALUE) {
      return Byte.MAX_VALUE;
    }
    return ((byte) res);
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x1) {
    long res;

    res = x1;
    res *= res;
    if (res >= Short.MAX_VALUE) {
      return Short.MAX_VALUE;
    }
    return ((short) res);
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x1) {
    long res;

    res = x1;
    if ((res >= (-Sqr.SQRT_LONG_MAX_VALUE))
        && (res <= Sqr.SQRT_LONG_MAX_VALUE)) {
      res *= res;
      if (res < Integer.MAX_VALUE) {
        return ((int) res);
      }
    }

    return Integer.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1) {
    if ((x1 >= (-Sqr.SQRT_LONG_MAX_VALUE))
        && (x1 <= Sqr.SQRT_LONG_MAX_VALUE)) {
      return (x1 * x1);
    }

    return Long.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x1) {
    final long l1;

    if ((x1 >= (-Sqr.SQRT_LONG_MAX_VALUE))
        && (x1 <= Sqr.SQRT_LONG_MAX_VALUE)) {
      l1 = ((long) x1);
      if (l1 == x1) {
        return (l1 * l1);
      }
    }

    return (x1 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    final long l1;

    if ((x1 >= (-Sqr.SQRT_LONG_MAX_VALUE))
        && (x1 <= Sqr.SQRT_LONG_MAX_VALUE)) {
      l1 = ((long) x1);
      if (l1 == x1) {
        return (l1 * l1);
      }
    }

    return (x1 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Sqrt invertFor(final int index) {
    return Sqrt.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    renderer.renderParameter(0, out);
    out.append((char) 0xb2);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (final IMath sqr = out.pow()) {
      renderer.renderParameter(0, sqr);
      try (final IText num = sqr.number()) {
        num.append(2);
      }
    }
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
    return Sqr.INSTANCE;
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
    return Sqr.INSTANCE;
  }
}
