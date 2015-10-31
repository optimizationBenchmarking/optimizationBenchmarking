package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A polynomial to be fitted in order to model the relationship of
 * similarly-typed dimensions (time-time, objective-objective):
 * {@code a+b*x+c*x*x}
 */
public final class PolynomialModel extends ParametricUnaryFunction {

  /** create */
  public PolynomialModel() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    return Add3.INSTANCE.computeAsDouble(parameters[0],
        (parameters[1] * x), //
        (parameters[2] * x * x));
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
  public final IParameterGuesser createParameterGuesser(
      final IMatrix data) {
    return new _PolynomialGuesser(data);
  }
}
