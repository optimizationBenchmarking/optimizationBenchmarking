package org.optimizationBenchmarking.utils.math.fitting.spec;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

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
   * Create a parameter guesser
   *
   * @param data
   *          the data matrix
   * @return the parameter guesser
   */
  public IParameterGuesser createParameterGuesser(final IMatrix data) {
    return new _DefaultParameterGuesser();
  }
}
