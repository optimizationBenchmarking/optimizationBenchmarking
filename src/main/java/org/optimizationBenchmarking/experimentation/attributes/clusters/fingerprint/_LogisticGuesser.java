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

  /**
   * compute the error of a given fitting
   *
   * @param x0
   *          the first x-coordinate
   * @param y0
   *          the first y-coordinate
   * @param x1
   *          the second x-coordinate
   * @param y1
   *          the second y-coordinate
   * @param x2
   *          the third x-coordinate
   * @param y2
   *          the third y-coordinate
   * @param a
   *          the first fitting parameter
   * @param b
   *          the second fitting parameter
   * @param c
   *          the first fitting parameter
   * @return the fitting error
   */
  private static final double __error(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double a, final double b, final double c) {
    return Add3.INSTANCE.computeAsDouble(//
        Math.abs(
            y0 - (a / (1d + (b * Pow.INSTANCE.computeAsDouble(x0, c))))), //
        Math.abs(
            y1 - (a / (1d + (b * Pow.INSTANCE.computeAsDouble(x1, c))))), //
        Math.abs(
            y2 - (a / (1d + (b * Pow.INSTANCE.computeAsDouble(x1, c))))));
  }

  /**
   * perform one update step of the fitting
   *
   * @param x0
   *          the first x-coordinate
   * @param y0
   *          the first y-coordinate
   * @param x1
   *          the second x-coordinate
   * @param y1
   *          the second y-coordinate
   * @param x2
   *          the third x-coordinate
   * @param y2
   *          the third y-coordinate
   * @param best
   *          the best fitting so far
   * @param bestError
   *          the error of the best fitting so far
   * @return the fitting error
   */
  private static final double __update(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double[] best, final double bestError) {
    final double newA, newB, newC, oldC, newError, x1c, x0c, x2c;

    oldC = best[2];
    x0c = Pow.INSTANCE.computeAsDouble(x0, oldC);
    x1c = Pow.INSTANCE.computeAsDouble(x1, oldC);
    x2c = Pow.INSTANCE.computeAsDouble(x2, oldC);

    // iteration 1
    newB = (-((y1 - y0) / (((x1c * y1) - (x0c * y0)))));
    if (MathUtils.isFinite(newB)) {

      newA = (((x2c - x1c) * y1 * y2) / ((x2c * y2) - (x1c * y1)));
      if (MathUtils.isFinite(newA)) {

        newC = Math.abs(Math.log(((newA / y0) - 1) / newB) / Math.log(x0));
        if (MathUtils.isFinite(newC)) {

          newError = _LogisticGuesser.__error(x0, y0, x1, y1, x2, y2, newA,
              newB, newC);
          if (MathUtils.isFinite(newError) && (newError < bestError)) {
            best[0] = newA;
            best[1] = newB;
            best[2] = newC;
            return newError;
          }
        }
      }
    }

    return bestError;
  }

  /** {@inheritDoc} */
  @Override
  final boolean guessBasedOn3Points(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double[] dest, final Random random) {
    int steps;
    double oldError, bestError, v;

    steps = 100;
    bestError = Double.POSITIVE_INFINITY;

    for (;;) {

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

      if (v == 0d) {
        v = 1e-10d;
      }
      dest[0] = Math.abs(v * (1d + (0.1d * random.nextGaussian())));
      dest[1] = (-Math.abs(
          v * (1d / (15 + random.nextInt(1))) * random.nextGaussian()));
      do {
        dest[2] = (1d + random.nextGaussian());
      } while (dest[2] <= 1e-12d);

      inner: for (;;) {
        --steps;
        oldError = bestError;

        bestError = _LogisticGuesser.__update(x0, y0, x1, y1, x2, y2, dest,
            bestError);
        bestError = _LogisticGuesser.__update(x0, y0, x2, y2, x1, y1, dest,
            bestError);
        bestError = _LogisticGuesser.__update(x1, y1, x0, y0, x2, y2, dest,
            bestError);
        bestError = _LogisticGuesser.__update(x1, y1, x2, y2, x0, y0, dest,
            bestError);
        bestError = _LogisticGuesser.__update(x2, y2, x0, y0, x1, y1, dest,
            bestError);
        bestError = _LogisticGuesser.__update(x2, y2, x1, y1, x0, y0, dest,
            bestError);

        if (MathUtils.isFinite(bestError)) {
          if ((steps <= 0) || (bestError >= oldError)) {
            return true;
          }
          continue inner;
        }
        break inner;
      }

      if (MathUtils.isFinite(bestError)) {
        return true;
      }

      if (steps <= 0) {
        return false;
      }
    }
  }
}
