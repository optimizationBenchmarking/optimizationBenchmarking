package org.optimizationBenchmarking.utils.math.functions.stochastic;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * <p>
 * Compute the chi square distribution.
 * </p>
 */
public final class ChiSquareCDF extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final ChiSquareCDF INSTANCE = new ChiSquareCDF();

  /** the instances of the internally used gamma distribution */
  private static final GammaDistribution[] GAMMAS = new GammaDistribution[128];

  /** instantiate */
  private ChiSquareCDF() {
    super();
  }

  /**
   * create the gamma distribution for internal use
   * 
   * @param dof
   *          the degrees of freedom
   * @return the distribution for internal use
   */
  static final GammaDistribution _distribution(final int dof) {
    GammaDistribution d;

    if ((dof >= 0) && (dof < ChiSquareCDF.GAMMAS.length)) {
      d = ChiSquareCDF.GAMMAS[dof];
      if (d != null) {
        return d;
      }
      return (ChiSquareCDF.GAMMAS[dof] = new GammaDistribution(null,
          (dof / 2d), 2d));
    }
    return new GammaDistribution(null, (dof / 2d), 2d);
  }

  /**
   * Computes the cumulative of the chi square distribution which is the
   * probability of d.
   * 
   * @param x
   *          the x-value
   * @param dof
   *          the degrees of freedom
   * @return the probability of x TODO: CHECK
   */
  public static final double chiSquareCDF(final double x, final int dof) {
    return ChiSquareCDF._distribution(dof).cumulativeProbability(x);
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1, final double x2) {
    return ChiSquareCDF.chiSquareCDF(x1, ((int) (Math.round(x2))));
  }

  /** {@inheritDoc} */
  @Override
  public final ChiSquareInvCDF invertFor(final int index) {
    return ChiSquareInvCDF.INSTANCE;
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
