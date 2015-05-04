package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * <p>
 * The Bessel function y0.
 * </p>
 */
public final class BesselY0 extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BesselY0 INSTANCE = new BesselY0();

  /** instantiate */
  private BesselY0() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    double y, z, x1x1;

    if (x1 < 8.0d) {
      y = (x1 * x1);
      return ((-2957821389.0d + (y * (7062834065.0d + (y * (-512359803.6d + (y * (10879881.29d + (y * (-86327.92757d + (y * 228.4622733)))))))))) / (40076544269.0d + (y * (745249964.8d + (y * (7189466.438d + (y * (47447.26470d + (y * (226.1030244d + (y * 1.0d)))))))))))
          + (0.636619772d * BesselJ0.INSTANCE.computeAsDouble(x1) * Math
              .log(x1));
    }

    z = 8.0d / x1;
    y = z * z;
    x1x1 = x1 - 0.785398164d;

    return Math.sqrt(0.636619772d / x1)
        * ((Math.sin(x1x1) * (1.0d + (y * (-0.1098628627e-2d + (y * (0.2734510407e-4d + (y * (-0.2073370639e-5d + (y * 0.2093887211e-6d))))))))) + (z
            * Math.cos(x1x1) * (-0.1562499995e-1d + (y * (0.1430488765e-3d + (y * (-0.6911147651e-5d + (y * (0.7621095161e-6d + (y * (-0.934945152e-7d)))))))))));
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
    return BesselY0.INSTANCE;
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
    return BesselY0.INSTANCE;
  }
}
