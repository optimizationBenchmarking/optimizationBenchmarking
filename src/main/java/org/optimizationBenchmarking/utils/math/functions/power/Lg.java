package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The ln function
 */
public final class Lg extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Lg INSTANCE = new Lg();

  /** instantiate */
  private Lg() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x1) {
    return Log.INSTANCE.computeAsByte(((byte) (10)), x1);
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x1) {
    return Log.INSTANCE.computeAsShort(((short) (10)), x1);
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x1) {
    return Log.INSTANCE.computeAsInt((10), x1);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1) {
    return Log.INSTANCE.computeAsLong((10L), x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return Lg.__fastMathLog10(x1);
    }
    return Math.log10(x1);
  }

  /**
   * Compute {@code log1ß} with
   * {@link org.apache.commons.math3.util.FastMath}
   * 
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathLog10(final double x1) {
    return FastMath.log10(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Pow10 invertFor(final int index) {
    return Pow10.INSTANCE;
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
    return Lg.INSTANCE;
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
    return Lg.INSTANCE;
  }
}
