package org.optimizationBenchmarking.utils.ml.fitting.impl.guessers;

import java.util.Random;

import org.optimizationBenchmarking.utils.ml.fitting.spec.IParameterGuesser;

/**
 * An internal default implementation of the {@link IParameterGuesser}
 * interface.
 */
public class DefaultParameterGuesser implements IParameterGuesser {

  /** create */
  public DefaultParameterGuesser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public void createRandomGuess(final double[] parameters,
      final Random random) {
    for (int index = parameters.length; (--index) >= 0;) {
      parameters[index] = (random.nextGaussian()
          * (random.nextInt(5) + 1));
    }
  }
}
