package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/**
 * The {@code "*"} function
 */
public final class Mul extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Mul INSTANCE = new Mul();

  /** instantiate */
  private Mul() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1) {
    return ((byte) (x0 * x1));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1) {
    return ((short) (x0 * x1));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1) {
    return (x0 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1) {
    return (x0 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1) {
    final long l0, l1, res;

    if (NumericalTypes.isLong(x0) && NumericalTypes.isLong(x1)) {
      l0 = ((long) x0);
      if (l0 == 0L) {
        return 0f;
      }
      l1 = ((long) x1);
      if (l1 == 0L) {
        return 0f;
      }
      res = (l1 * l0);
      if ((res / l0) == l1) {
        return res;
      }
    }

    return (x0 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1) {
    final long l0, l1, res;

    if (NumericalTypes.isLong(x0) && NumericalTypes.isLong(x1)) {
      l0 = ((long) x0);
      if (l0 == 0L) {
        return 0d;
      }
      l1 = ((long) x1);
      if (l1 == 0L) {
        return 0d;
      }
      res = (l1 * l0);
      if ((res / l0) == l1) {
        return res;
      }
    }

    return (x0 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1) {
    final long res;

    if ((x0 == 0L) || (x1 == 0L)) {
      return 0d;
    }

    res = (x0 * x1);
    if ((res / x0) == x1) {
      return res;
    }
    return (((double) x0) * ((double) x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1) {
    return this.computeAsDouble(((long) x0), ((long) x1));
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction invertFor(final int index) {
    if (index == 0) {
      return Div.INSTANCE;
    }
    return null;
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
    return Mul.INSTANCE;
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
    return Mul.INSTANCE;
  }
}
