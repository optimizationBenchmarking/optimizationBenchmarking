package org.optimizationBenchmarking.utils.math.functions.stochastic;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;
import org.optimizationBenchmarking.utils.math.functions.special.GammaRegularizedQ;

/**
 * The cumulative distribution function (<a
 * href="http://en.wikipedia.org/wiki/Cumulative_distribution_function"
 * >CDF</a>) of the <a
 * href="http://en.wikipedia.org/wiki/Poisson_distribution">Poisson
 * distribution</a>.
 */
public final class PoissonCDF extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final PoissonCDF INSTANCE = new PoissonCDF();

  /** instantiate */
  private PoissonCDF() {
    super();
  }

  /**
   * Returns the sum of the first k terms of the Poisson distribution.
   * 
   * @param k
   *          number of terms
   * @param x
   *          double value
   * @return The sum of the first k terms of the Poisson distribution.
   *         TODO: CHECK
   */

  public static final double poissonCDF(final int k, final double x) {
    if ((k < 0) || (x < 0.0d)) {
      return 0.0d;
    }

    return GammaRegularizedQ.INSTANCE.compute(k + 1, x);
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1, final double x2) {
    return PoissonCDF.poissonCDF(((int) (Math.round(x1))), x2);
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
