package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.PolynomialFitter;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;

/** A parameter guesser for polynomials */
final class _PolynomialGuesser implements IParameterGuesser {

  /** the data matrix */
  private final IMatrix m_data;

  /** the quantile for a0 */
  private final QuantileAggregate m_a0;
  /** the quantile for a1 */
  private final QuantileAggregate m_a1;
  /** the quantile for a2 */
  private final QuantileAggregate m_a2;

  /**
   * create the guesser
   *
   * @param data
   *          the data
   */
  _PolynomialGuesser(final IMatrix data) {
    super();
    this.m_data = data;
    this.m_a0 = new QuantileAggregate(0.5d);
    this.m_a1 = new QuantileAggregate(0.5d);
    this.m_a2 = new QuantileAggregate(0.5d);
  }

  /** {@inheritDoc} */
  @Override
  public void createRandomGuess(final double[] parameters,
      final Random random) {
    final int m;
    int trials, p;
    boolean notFound;
    double x0, x1, x2, y0, y1, y2;

    m = this.m_data.m();

    this.m_a0.reset();
    this.m_a1.reset();
    this.m_a2.reset();

    notFound = true;
    trials = 10000;

    do {

      p = random.nextInt(m);
      x0 = this.m_data.getDouble(p, 0);
      y0 = this.m_data.getDouble(p, 1);
      p = random.nextInt(m);
      x1 = this.m_data.getDouble(p, 0);
      y1 = this.m_data.getDouble(p, 1);
      p = random.nextInt(m);
      x2 = this.m_data.getDouble(p, 0);
      y2 = this.m_data.getDouble(p, 1);

      PolynomialFitter.findCoefficientsDegree2(x0, y0, x1, y1, x2, y2,
          parameters);
      // TODO

    } while ((((--trials) >= 0) && notFound) || (random.nextInt(3) > 0));

    parameters[0] = random.nextGaussian();
    parameters[1] = random.nextGaussian();
    parameters[2] = random.nextGaussian();
  }

}
