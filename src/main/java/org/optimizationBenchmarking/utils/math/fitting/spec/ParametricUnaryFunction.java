package org.optimizationBenchmarking.utils.math.fitting.spec;

import java.util.Random;

/**
 * A base class for representing a real function that depends on one
 * independent variable plus some extra parameters.
 */
public abstract class ParametricUnaryFunction {

  /** create */
  protected ParametricUnaryFunction() {
    super();
  }

  /**
   * Compute the value of the function.
   *
   * @param x
   *          Point for which the function value should be computed.
   * @param parameters
   *          function parameters.
   * @return the value.
   */
  public abstract double value(double x, double[] parameters);

  /**
   * Compute the gradient of the function with respect to its parameters.
   *
   * @param x
   *          Point for which the function value should be computed.
   * @param parameters
   *          Function parameters.
   * @param gradient
   *          the variable to receive the gradient
   */
  public abstract void gradient(double x, double[] parameters,
      final double[] gradient);

  /**
   * Get the number of parameters
   *
   * @return the number of parameters
   */
  public abstract int getParameterCount();

  /**
   * Make the parameters canonical, i.e., try to map redundant settings to
   * a canonical one
   *
   * @param parameters
   *          the parameters
   */
  public void canonicalizeParameters(final double[] parameters) {
    // do nothing
  }

  /**
   * Create a random guess of the parameters
   *
   * @param parameters
   *          the parameters
   * @param random
   *          the random guess
   */
  public void createRandomGuess(final double[] parameters,
      final Random random) {
    int index;

    for (index = this.getParameterCount(); (--index) >= 0;) {
      parameters[index] = (random.nextGaussian() * (random.nextInt(5) + 1));
    }
  }
}
