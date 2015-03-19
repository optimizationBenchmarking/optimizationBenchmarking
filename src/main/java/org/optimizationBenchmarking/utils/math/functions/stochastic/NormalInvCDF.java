package org.optimizationBenchmarking.utils.math.functions.stochastic;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The inverted cumulative distribution function (<a
 * href="http://en.wikipedia.org/wiki/Cumulative_distribution_function"
 * >CDF</a>) of the <a href=
 * "http://en.wikipedia.org/wiki/Normal_distribution#Standard_normal_distribution"
 * >standard normal distribution</a>.
 */
public final class NormalInvCDF extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final NormalInvCDF INSTANCE = new NormalInvCDF();

  /** instantiate */
  private NormalInvCDF() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    return NormalPDF.DISTRIBUTION.inverseCumulativeProbability(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final NormalCDF invertFor(final int index) {
    return NormalCDF.INSTANCE;
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
    return NormalInvCDF.INSTANCE;
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
    return NormalInvCDF.INSTANCE;
  }
}
