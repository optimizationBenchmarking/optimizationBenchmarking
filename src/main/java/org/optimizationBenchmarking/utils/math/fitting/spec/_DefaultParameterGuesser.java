package org.optimizationBenchmarking.utils.math.fitting.spec;

import java.util.Random;

/**
 * An internal default implementation of the {@link IParameterGuesser}
 * interface.
 */
final class _DefaultParameterGuesser implements IParameterGuesser {

  /** create */
  _DefaultParameterGuesser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void createRandomGuess(final double[] parameters,
      final Random random) {
    for (int index = parameters.length; (--index) >= 0;) {
      parameters[index] = (random.nextGaussian()
          * (random.nextInt(5) + 1));
    }
  }
}
