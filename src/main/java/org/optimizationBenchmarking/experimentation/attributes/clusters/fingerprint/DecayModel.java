package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;

/**
 * A model function which I think may be suitable to model how
 * time-objective value relationships optimization processes usually work.
 * The derivatives have been obtained with
 * http://www.numberempire.com/derivativecalculator.php.
 * <ol>
 * <li>Original function: {@code -(exp(a*(x^b))-1)}</li>
 * <li>{@code d/da}: {@code -x^b*exp(a*x^b)}</li>
 * <li>{@code d/dd}: {@code -a*x^b*exp(a*x^b)*log(x)}</li>
 * </ol>
 */
public final class DecayModel extends ParametricUnaryFunction {

  /** create */
  public DecayModel() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    double temp;

    if ((x <= 0d) || // x cannot be negative anyway
        ((temp = Math.pow(x, parameters[1])) <= 0d) || // maybe imprecision
        (Math.abs(temp *= parameters[0]) <= 0d)) {// dito
      return 1;
    }
    return (-Math.expm1(temp));
  }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    final double xb, axb, expaxb;

    if ((x <= 0d) || //
        (Math.abs(xb = Math.pow(x, parameters[1])) <= 0d) || //
        (Math.abs(axb = (parameters[0] * xb)) <= 0d) || //
        (Math.abs(expaxb = Math.exp(axb)) <= 0d) || //
        (Math.abs(gradient[0] = (-xb * expaxb)) <= 0d)) {
      gradient[0] = gradient[1] = 0d;
    } else {
      if (Math.abs(gradient[1] = (-axb * expaxb * Math.log(x))) <= 0d) {
        gradient[1] = 0d;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getParameterCount() {
    return 2;
  }

  /** {@inheritDoc} */
  @Override
  public void canonicalizeParameters(final double[] parameters) {
    double x;

    x = parameters[0];
    if (x > 0d) {
      parameters[0] = (-x);
    }
    x = parameters[1];
    if (x > 0d) {
      parameters[1] = (-x);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void createRandomGuess(final double[] parameters,
      final Random random) {
    parameters[0] = (-1e-2d * Math.abs(random.nextGaussian()));
    parameters[1] = (-random.nextDouble());
  }
}
