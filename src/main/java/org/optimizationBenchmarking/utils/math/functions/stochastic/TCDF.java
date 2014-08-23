package org.optimizationBenchmarking.utils.math.functions.stochastic;

import org.apache.commons.math3.distribution.TDistribution;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * <p>
 * The CDF of the t-distribution.
 */
public final class TCDF extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final TCDF INSTANCE = new TCDF();

  /**
   * the internal instances of
   * {@link org.apache.commons.math3.distribution.TDistribution}
   */
  private static final TDistribution[] TS = new TDistribution[128];

  /** instantiate */
  private TCDF() {
    super();
  }

  /**
   * get the T-distribution instance to use
   * 
   * @param dof
   *          the degrees of freedom
   * @return the distribution to use
   */
  static final TDistribution _distribution(final long dof) {
    final int df;
    TDistribution d;

    if ((dof >= 0l) && (dof < TCDF.TS.length)) {
      d = TCDF.TS[(df = (int) dof)];
      if (d != null) {
        return d;
      }
      return (TCDF.TS[df] = new TDistribution(null, dof));
    }

    return new TDistribution(null, dof);
  }

  /**
   * Returns the CDF of the t-distribution
   * 
   * @param dof
   *          degrees of freedom
   * @param x
   *          x-value
   * @return The sum of the first k terms of the t distribution.
   */

  public static final double tCDF(final long dof, final double x) {
    if (x <= Double.NEGATIVE_INFINITY) {
      return 0d;
    }
    if (x >= Double.POSITIVE_INFINITY) {
      return 1d;
    }
    if (x == 0d) {
      return 0.5d;
    }

    return TCDF._distribution(dof).cumulativeProbability(x);
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1, final double x2) {
    return TCDF.tCDF(Math.round(x1), x2);
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
