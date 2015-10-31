package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A model function which I think may be suitable to model how
 * time-objective value relationships optimization processes usually work.
 * The derivatives have been obtained with
 * http://www.numberempire.com/derivativecalculator.php.
 * <ol>
 * <li>Original function: {@code a/(1+exp(b+c*x)}</li>
 * <li>{@code d/da}: {@code 1/(1+exp(c*x+b)))}</li>
 * <li>{@code d/db}:
 * {@code -(a*  exp(c*x+b))/(1+2*exp(c*x+b)+exp(2*c*x+2*b))}</li>
 * <li>{@code d/dc}:
 * {@code -(a*x*exp(c*x+b))/(1+2*exp(c*x+b)+exp(2*c*x+2*b))}</li>
 * </ol>
 */
public final class LogisticModel extends ParametricUnaryFunction {

  /** create */
  public LogisticModel() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    return parameters[0]
        / (1d + Math.exp(parameters[1] + (parameters[2] * x)));
  }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    final double cxpb, ecxpb;

    cxpb = (parameters[1] + (parameters[2] * x));
    ecxpb = Math.exp(cxpb);

    gradient[0] = (1d / (1d + ecxpb));
    gradient[1] = ((-parameters[0] * ecxpb) / //
        Add3.INSTANCE.computeAsDouble(1d, 2 * ecxpb, Math.exp(2 * cxpb)));
    gradient[2] = (x * gradient[1]);
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
    return new _LogisticGuesser(data);
  }
}
