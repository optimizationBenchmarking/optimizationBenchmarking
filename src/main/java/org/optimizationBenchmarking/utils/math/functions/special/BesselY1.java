package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * <p>
 * Compute the Bessel function of the second kind, of order 1 of the
 * argument.
 * </p>
 */
public final class BesselY1 extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BesselY1 INSTANCE = new BesselY1();

  /** instantiate */
  private BesselY1() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    double y, z, xx;

    if (x1 < 8.0d) {
      y = (x1 * x1);
      return ((x1 * (-0.4900604943e13d + (y * (0.1275274390e13d + (y * (-0.5153438139e11d + (y * (0.7349264551e9d + (y * (-0.4237922726e7d + (y * 0.8511937935e4d))))))))))) / (0.2499580570e14d + (y * (0.4244419664e12d + (y * (0.3733650367e10d + (y * (0.2245904002e8d + (y * (0.1020426050e6d + (y * (0.3549632885e3d + y))))))))))))
          + (0.636619772d * ((BesselJ1.INSTANCE.computeAsDouble(x1) * Math
              .log(x1)) - (1.0 / x1)));
    }

    z = 8.0d / x1;
    y = z * z;
    xx = x1 - 2.356194491d;

    return Math.sqrt(0.636619772d / x1)
        * ((Math.sin(xx) * (1.0d + (y * (0.183105e-2d + (y * (-0.3516396496e-4d + (y * (0.2457520174e-5d + (y * (-0.240337019e-6d)))))))))) + (z
            * Math.cos(xx) * (0.04687499995d + (y * (-0.2002690873e-3d + (y * (0.8449199096e-5d + (y * (-0.88228987e-6d + (y * 0.105787412e-6d))))))))));
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
    return BesselY1.INSTANCE;
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
    return BesselY1.INSTANCE;
  }
}
