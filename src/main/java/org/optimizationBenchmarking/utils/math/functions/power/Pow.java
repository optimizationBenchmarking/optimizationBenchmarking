package org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * The pow function: raise {@code x1} to the power {@code x2}
 */
public final class Pow extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Pow INSTANCE = new Pow();

  /** instantiate */
  private Pow() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x1, final int x2) {
    int result, exp, base;

    if (x2 <= (1)) {
      if ((x2 <= (0)) && (x1 == (0))) {
        throw new java.lang.ArithmeticException("0 to a power <= 0"); //$NON-NLS-1$
      }
      if (x2 < (0)) {
        return (0);
      }
      if (x2 == (1)) {
        return x1;
      }
      return (1);
    }

    if (x1 <= (2)) {
      if (x1 == (0)) {
        return (0);
      }
      if (x1 == (1)) {
        return (1);
      }
      if (x1 == (2)) {
        result = ((1) << x2);
        if (result <= (0)) {
          return (java.lang.Integer.MAX_VALUE);
        }
        return result;
      }
    }

    result = (1);
    exp = x2;
    base = x1;

    for (;;) {
      if ((exp & (1)) != (0)) {
        if ((result *= base) <= (0)) {
          if ((x1 < (0)) && ((x2 & (1)) != (0))) {
            return (java.lang.Integer.MIN_VALUE);
          }
          return (java.lang.Integer.MAX_VALUE);
        }
      }

      if ((exp >>>= (1)) <= (0)) {
        return result;
      }
      base *= base;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1, final long x2) {
    long result, exp, base;

    if (x2 <= (1L)) {
      if ((x2 <= (0L)) && (x1 == (0L))) {
        throw new java.lang.ArithmeticException("0 to a power <= 0"); //$NON-NLS-1$
      }
      if (x2 < (0L)) {
        return (0L);
      }
      if (x2 == (1L)) {
        return x1;
      }
      return (1L);
    }

    if (x1 <= (2L)) {
      if (x1 == (0L)) {
        return (0L);
      }
      if (x1 == (1L)) {
        return (1L);
      }
      if (x1 == (2L)) {
        result = ((1L) << x2);
        if (result <= (0L)) {
          return (java.lang.Long.MAX_VALUE);
        }
        return result;
      }
    }

    result = (1L);
    exp = x2;
    base = x1;

    for (;;) {
      if ((exp & (1L)) != (0L)) {
        if ((result *= base) <= (0L)) {
          if ((x1 < (0L)) && ((x2 & (1L)) != (0L))) {
            return (java.lang.Long.MIN_VALUE);
          }
          return (java.lang.Long.MAX_VALUE);
        }
      }

      if ((exp >>>= (1L)) <= (0L)) {
        return result;
      }
      base *= base;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x1, final float x2) {
    return ((float) (Math.pow(x1, x2)));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2) {
    return (Math.pow(x1, x2));
  }

  /** {@inheritDoc} */
  @Override
  public final Log invertFor(final int index) {
    if (index == 1) {
      return Log.INSTANCE;
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
