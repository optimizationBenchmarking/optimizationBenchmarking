package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.MinimumAggregate;

/** A base class for the internal parameter guessers */
abstract class _BasicInternalGuesser implements IParameterGuesser {

  /** the data matrix */
  private final IMatrix m_data;

  /** the minimum x value */
  private final double m_minX;
  /** the minimum y value */
  private final double m_minY;

  /**
   * create the guesser
   *
   * @param data
   *          the data
   */
  _BasicInternalGuesser(final IMatrix data) {
    super();

    final MinimumAggregate x, y;
    int i;

    this.m_data = data;

    x = new MinimumAggregate();
    y = new MinimumAggregate();
    for (i = data.m(); (--i) >= 0;) {
      x.append(data.getDouble(i, 0));
      y.append(data.getDouble(i, 1));
    }

    this.m_minX = x.doubleValue();
    this.m_minY = y.doubleValue();
  }

  /**
   * guess if three points are available
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
   * @param dest
   *          the destination
   * @param random
   *          the random number generator
   * @return {@code true} if fitting was successful
   */
  boolean guessBasedOn3Points(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double[] dest, final Random random) {
    return false;
  }

  /**
   * guess if two points are available
   *
   * @param x0
   *          the first x-coordinate
   * @param y0
   *          the first y-coordinate
   * @param x1
   *          the second x-coordinate
   * @param y1
   *          the second y-coordinate
   * @param dest
   *          the destination
   * @return {@code true} if fitting was successful
   */
  boolean guessBasedOn2Points(final double x0, final double y0,
      final double x1, final double y1, final double[] dest) {
    return false;
  }

  /**
   * guess if one point is available
   *
   * @param x0
   *          the first x-coordinate
   * @param y0
   *          the first y-coordinate
   * @param dest
   *          the destination
   * @return {@code true} if fitting was successful
   */
  boolean guessBasedOn1Point(final double x0, final double y0,
      final double[] dest) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void createRandomGuess(final double[] parameters,
      final Random random) {
    final int m;
    int i, trials, curI, curJ, curK, bestI, bestJ, bestK, useDistDim;
    double t, curIZ, curJZ, curKZ, bestIZ, bestJZ, bestKZ, curDist,
        bestDist, max, med, min;
    boolean logScale;

    m = this.m_data.m();

    find: {
      switcher: switch (m) {
        case 1: {
          if (this.guessBasedOn1Point(this.m_data.getDouble(0, 0), //
              this.m_data.getDouble(0, 1), //
              parameters)) {
            break find;
          }
          break switcher;
        }

        case 2: {
          if (this.guessBasedOn2Points(this.m_data.getDouble(0, 0), //
              this.m_data.getDouble(0, 1), //
              this.m_data.getDouble(1, 0), //
              this.m_data.getDouble(1, 1), //
              parameters)) {
            break find;
          }
          break switcher;
        }

        case 3: {
          if (this.guessBasedOn3Points(this.m_data.getDouble(0, 0), //
              this.m_data.getDouble(0, 1), //
              this.m_data.getDouble(1, 0), //
              this.m_data.getDouble(1, 1), //
              this.m_data.getDouble(2, 0), //
              this.m_data.getDouble(2, 1), //
              parameters, random)) {
            break find;
          }
          break switcher;
        }

        default: {
          if (m > 3) {

            switch (random.nextInt(5)) {
              case 0: {
                useDistDim = 0;
                logScale = false;
                break;
              }
              case 1: {
                useDistDim = 0;
                logScale = true;
                break;
              }
              case 2: {
                useDistDim = 1;
                logScale = false;
                break;
              }
              case 3: {
                useDistDim = 1;
                logScale = true;
                break;
              }
              default: {
                useDistDim = -1;
                logScale = false;
              }
            }

            for (trials = 9; (--trials) >= 0;) {

              bestI = bestJ = bestK = -1;
              bestIZ = bestJZ = bestKZ = Double.NaN;
              bestDist = Double.NEGATIVE_INFINITY;

              inner: for (i = 20; (--i) >= 0;) {

                curI = random.nextInt(m);
                do {
                  curJ = random.nextInt(m);
                } while (curI == curJ);
                do {
                  curK = random.nextInt(m);
                } while ((curK == curI) || (curK == curJ));

                if (useDistDim < 0) {
                  bestI = curI;
                  bestJ = curJ;
                  bestK = curK;
                  break inner;
                }

                curIZ = this.m_data.getDouble(curI, useDistDim);
                curJZ = this.m_data.getDouble(curJ, useDistDim);
                curKZ = this.m_data.getDouble(curK, useDistDim);

                if (curIZ > curJZ) {
                  if (curIZ > curKZ) {
                    max = curIZ;
                    if (curJZ > curKZ) {
                      med = curJZ;
                      min = curKZ;
                    } else {
                      med = curKZ;
                      min = curJZ;
                    }
                  } else {
                    med = curIZ;
                    if (curJZ > curKZ) {
                      max = curJZ;
                      min = curKZ;
                    } else {
                      max = curKZ;
                      min = curJZ;
                    }
                  }
                } else {
                  if (curJZ > curKZ) {
                    max = curJZ;
                    if (curIZ > curKZ) {
                      med = curIZ;
                      min = curKZ;
                    } else {
                      med = curKZ;
                      min = curIZ;
                    }
                  } else {
                    med = curJZ;
                    max = curKZ;
                    min = curIZ;
                  }
                }

                if (logScale) {
                  t = ((useDistDim == 0) ? this.m_minX : this.m_minY);
                  if (t <= 0d) {
                    max = (max - t) + 1d;
                    med = (med - t) + 1d;
                    min = (min - t) + 1d;
                  }
                  max = Math.log(max);
                  med = Math.log(med);
                  min = Math.log(min);
                }

                max -= med;
                med -= min;
                curDist = ((max * max) + (med * med));

                if ((curDist > bestDist) && MathUtils.isFinite(curDist)) {
                  bestI = curI;
                  bestJ = curJ;
                  bestK = curK;
                  bestIZ = curIZ;
                  bestJZ = curJZ;
                  bestKZ = curKZ;
                  bestDist = curDist;
                }
              }

              if (this.guessBasedOn3Points(//
                  (useDistDim == 0) ? bestIZ
                      : this.m_data.getDouble(bestI, 0), //
                  (useDistDim == 1) ? bestIZ
                      : this.m_data.getDouble(bestI, 1), //
                  (useDistDim == 0) ? bestJZ
                      : this.m_data.getDouble(bestJ, 0), //
                  (useDistDim == 1) ? bestJZ
                      : this.m_data.getDouble(bestJ, 1), //
                  (useDistDim == 0) ? bestKZ
                      : this.m_data.getDouble(bestK, 0), //
                  (useDistDim == 1) ? bestKZ
                      : this.m_data.getDouble(bestK, 1), //
                  parameters, random)) {
                break find;
              }
            }
          }
        }
      }

      parameters[0] = random.nextGaussian();
      parameters[1] = random.nextGaussian();
      parameters[2] = random.nextGaussian();
      return;
    }

    if (random.nextBoolean()) {
      for (i = parameters.length; (--i) >= 0;) {
        t = Math.abs(parameters[i]);
        if (t <= 0d) {
          parameters[i] = (0.1d * random.nextGaussian());
        } else {
          parameters[i] += (0.05d * t * random.nextGaussian());
        }
      }
    }
  }
}
