package org.optimizationBenchmarking.utils.math.functions.stochastic;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The probability density function (<a
 * href="http://en.wikipedia.org/wiki/Probability_density_function"
 * >PDF</a>) of the <a href=
 * "http://en.wikipedia.org/wiki/Normal_distribution#Standard_normal_distribution"
 * >standard normal distribution</a>.
 */
public final class NormalPDF extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final NormalPDF INSTANCE = new NormalPDF();

  /** the internal normal distribution */
  static final NormalDistribution DISTRIBUTION = new NormalDistribution(
      null, 0d, 1d);

  /** instantiate */
  private NormalPDF() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    return NormalPDF.DISTRIBUTION.density(x1);
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
    return NormalPDF.INSTANCE;
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
    return NormalPDF.INSTANCE;
  }
}
