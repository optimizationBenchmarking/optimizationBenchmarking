package org.optimizationBenchmarking.utils.ml.fitting.spec;

import java.util.Random;

/**
 * A way to guess parameters of a
 * {@link org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction}
 * .
 */
public interface IParameterGuesser {

  /**
   * Create a random guess of the parameters
   *
   * @param parameters
   *          the parameters
   * @param random
   *          the random guess
   */
  public abstract void createRandomGuess(final double[] parameters,
      final Random random);

}
