package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * <p>
 * The Bessel function j1.
 * </p>
 */
public final class BesselJ1 extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BesselJ1 INSTANCE = new BesselJ1();

  /** instantiate */
  private BesselJ1() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    double ax1, y, z, x1x1;

    if ((ax1 = Math.abs(x1)) < 8.0d) {
      y = (x1 * x1);

      return (x1 * (72362614232.0d + (y * (-7895059235.0d + (y * (242396853.1d + (y * (-2972611.439d + (y * (15704.48260d + (y * (-30.16036606d))))))))))))
          / (144725228442.0d + (y * (2300535178.0d + (y * (18583304.74d + (y * (99447.43394d + (y * (376.9991397d + (y * 1.0d))))))))));
    }

    z = 8.0d / ax1;
    x1x1 = ax1 - 2.356194491d;
    y = z * z;

    y = Math.sqrt(0.636619772 / ax1)
        * ((Math.cos(x1x1) * ((1.0d + (y * (0.183105e-2d + (y * (-0.3516396496e-4d + (y * (0.2457520174e-5d + (y * (-0.240337019e-6d))))))))))) - (z
            * Math.sin(x1x1) * (0.04687499995d + (y * (-0.2002690873e-3d + (y * (0.8449199096e-5d + (y * (-0.88228987e-6d + (y * 0.105787412e-6d))))))))));

    if (x1 < 0.0d) {
      return -y;
    }
    return y;
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
