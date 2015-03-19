package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * <p>
 * The Bessel function j0.
 * </p>
 */
public final class BesselJ0 extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BesselJ0 INSTANCE = new BesselJ0();

  /** instantiate */
  private BesselJ0() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    double ax1, y, z, x1x1;

    if ((ax1 = Math.abs(x1)) < 8.0d) {
      y = (x1 * x1);

      return (57568490574.0d + (y * (-13362590354.0d + (y * (651619640.7d + (y * (-11214424.18 + (y * (77392.33017 + (y * (-184.9052456)))))))))))
          / (57568490411.0d + (y * (1029532985.0d + (y * (9494680.718d + (y * (59272.64853d + (y * (267.8532712d + (y * 1.0))))))))));
    }

    z = 8.0d / ax1;
    y = z * z;
    x1x1 = ax1 - 0.785398164d;

    return Math.sqrt(0.636619772d / ax1)
        * ((Math.cos(x1x1) * (1.0d + (y * (-0.1098628627e-2d + (y * (0.2734510407e-4d + (y * (-0.2073370639e-5d + (y * 0.2093887211e-6d))))))))) - (z
            * Math.sin(x1x1) * (-0.1562499995e-1d + (y * (0.1430488765e-3d + (y * (-0.6911147651e-5d + (y * (0.7621095161e-6d - (y * 0.934935152e-7d))))))))));
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
    return BesselJ0.INSTANCE;
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
    return BesselJ0.INSTANCE;
  }
}
