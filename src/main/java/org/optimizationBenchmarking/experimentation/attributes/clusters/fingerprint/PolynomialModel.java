package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;

/**
 * A polynomial to be fitted in order to model the relationship of
 * similarly-typed dimensions (time-time, objective-objective):
 * {@code a+b*x+c*x*x}
 */
public final class PolynomialModel extends ParametricUnaryFunction {

  /** the sum */
  private final StableSum m_sum;

  /** create */
  public PolynomialModel() {
    super();
    this.m_sum = new StableSum();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    final StableSum sum;

    sum = this.m_sum;
    sum.reset();
    sum.append(parameters[0]);
    sum.append(parameters[1] * x);
    sum.append(parameters[2] * x * x);
    return sum.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    gradient[0] = 1d;// a
    gradient[1] = x;// b
    gradient[2] = x * x;// c
  }

  /** {@inheritDoc} */
  @Override
  public final int getParameterCount() {
    return 3;
  }

  /** {@inheritDoc} */
  @Override
  public IParameterGuesser createParameterGuesser(final IMatrix data) {
    return new _PolynomialGuesser(data);
  }
}
