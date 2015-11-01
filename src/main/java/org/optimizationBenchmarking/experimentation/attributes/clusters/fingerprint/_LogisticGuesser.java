package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A parameter guesser for the logistic models */
final class _LogisticGuesser extends _BasicInternalGuesser {

  /**
   * create the guesser
   *
   * @param data
   *          the data
   */
  _LogisticGuesser(final IMatrix data) {
    super(data);
  }

  /** {@inheritDoc} */
  @Override
  final boolean guessBasedOn3Points(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double[] dest, final Random random) {
    int steps;
    double newA, newB, newC, oldA, oldB, oldC, newError, oldError, x1c,
        x0c, x2c, v;
    boolean found;

    newError = Double.POSITIVE_INFINITY;
    steps = 1000;
    found = false;

    main: for (;;) {

      switch (random.nextInt(5)) {
        case 0: {
          v = y0;
          break;
        }
        case 1: {
          v = y1;
          break;
        }
        case 2: {
          v = y2;
          break;
        }
        default: {
          v = Math.max(y0, Math.max(y1, y2));
          break;
        }
      }

      newA = Math.abs(v * (1d + (0.1d * random.nextGaussian())));
      newB = (-Math.abs(
          v * (1d / (15 + random.nextInt(1))) * random.nextGaussian()));
      do {
        newC = (1d + random.nextGaussian());
      } while (newC <= 1e-12d);

      inner: for (;;) {
        oldA = newA;
        oldB = newB;
        oldC = newC;
        oldError = newError;

        x0c = Pow.INSTANCE.computeAsDouble(x0, newC);
        x1c = Pow.INSTANCE.computeAsDouble(x1, newC);
        x2c = Pow.INSTANCE.computeAsDouble(x2, newC);

        // iteration 1
        newB = (-((y1 - y0) / (((x1c * y1) - (x0c * y0)))));
        if (!(MathUtils.isFinite(newB))) {
          break inner;
        }

        newA = (((x2c - x1c) * y1 * y2) / ((x2c * y2) - (x1c * y1)));
        if (!(MathUtils.isFinite(newA))) {
          break inner;
        }

        newC = Math.abs(Math.log(((newA / y0) - 1) / newB) / Math.log(x0));
        if (!(MathUtils.isFinite(newC))) {
          break inner;
        }

        // iteration 2
        newB = (-((y2 - y0) / (((x2c * y2) - (x0c * y0)))));
        if (!(MathUtils.isFinite(newB))) {
          break inner;
        }

        newA = (((x0c - x1c) * y1 * y0) / ((x0c * y0) - (x1c * y1)));
        if (!(MathUtils.isFinite(newA))) {
          break inner;
        }

        newC = Math.abs(Math.log(((newA / y2) - 1) / newB) / Math.log(x2));
        if (!(MathUtils.isFinite(newC))) {
          break inner;
        }

        // iteration 3
        newB = (-((y2 - y1) / (((x2c * y2) - (x1c * y1)))));
        if (!(MathUtils.isFinite(newB))) {
          break inner;
        }

        newA = (((x0c - x2c) * y2 * y0) / ((x0c * y0) - (x2c * y2)));
        if (!(MathUtils.isFinite(newA))) {
          break inner;
        }

        newC = Math.abs(Math.log(((newA / y1) - 1) / newB) / Math.log(x1));
        if (!(MathUtils.isFinite(newC))) {
          break inner;
        }

        newError = Add3.INSTANCE.computeAsDouble(//
            Math.abs(y0 - (newA / (1d + (newB * x0c)))), //
            Math.abs(y1 - (newA / (1d + (newB * x1c)))), //
            Math.abs(y2 - (newA / (1d + (newB * x2c)))));
        if (!(MathUtils.isFinite(newError))) {
          break inner;
        }

        found = true;
        if ((--steps) <= 0) {
          break main;
        }

        if (newError >= oldError) {
          break main;
        }
      }

      if (found || ((--steps) <= 0)) {
        break main;
      }
    }

    if (found) {
      dest[0] = oldA;
      dest[1] = oldB;
      dest[2] = oldC;
      return true;
    }

    return false;
  }
}
