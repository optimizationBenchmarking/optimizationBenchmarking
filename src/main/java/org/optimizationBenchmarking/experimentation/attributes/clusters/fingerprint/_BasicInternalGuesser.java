package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.MinimumAggregate;

/**
 * This is a base class for the parameter guessers of our two model
 * functions. Both model functions have three parameters to fit. Hence, it
 * should be possible to provide a guess for these based on three different
 * points from the data set. We therefore are interested in picking three
 * points which are relatively far away from each other. The
 * {@link #guessBasedOn3Points(double, double, double, double, double, double, double[], Random)}
 * method then tries to fit the model through these points (in a fast but
 * extremely crude fashion).
 */
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
    final IMatrix data;
    int i, j, trials, curI, curJ, curK, sortDim;
    double t, curIX, curIY, curJX, curJY, curKX, curKY, //
        bestIX, bestIY, bestJX, bestJY, bestKX, bestKY, //
        curIZ, curJZ, curKZ, //
        curDist, bestDist, max, med, min, maxY;
    boolean logScale;

    data = this.m_data;
    m = data.m();

    maxY = 1d;
    
    switcher: switch (m) {
      case 1: {
        // If we only have one point, use that one for the guess.
        if (this.guessBasedOn1Point(//
            data.getDouble(0, 0), //
            maxY = data.getDouble(0, 1), //
            parameters)) {
          return;
        }
        break switcher;
      }

      case 2: {
        // If we only have two points, use these two for the guess.
        if (this.guessBasedOn2Points(//
            data.getDouble(0, 0), //
            curIY = data.getDouble(0, 1), //
            data.getDouble(1, 0), //
            curJY = data.getDouble(1, 1), //
            parameters)) {
          return;
        }
        maxY = Math.max(curIY, curJY);
        break switcher;
      }

      case 3: {
        // If we only have three points, use these three for the guess.
        if (this.guessBasedOn3Points(//
            data.getDouble(0, 0), //
            curIY = data.getDouble(0, 1), //
            data.getDouble(1, 0), //
            curJY = data.getDouble(1, 1), //
            data.getDouble(2, 0), //
            curKY = data.getDouble(2, 1), //
            parameters, random)) {
          return;
        }
        maxY = Math.max(curIY, Math.max(curJY, curKY));
        break switcher;
      }

      default: {
        if (m > 3) {
          // OK, we have more than three points. We now can try to guess
          // the parameters of our model in some intelligent way by
          // picking 3 points.

          // Actually, there are five ways in which we can pick the
          // points:
          switch (random.nextInt(5)) {
            case 0: {
              // Several times, randomly pick 3 points and take the
              // triple whose three points are most distant from each
              // other in terms of their x-coordinate.
              sortDim = 0;
              logScale = false;
              break;
            }
            case 1: {
              // Several times, randomly pick 3 points and take the
              // triple whose three points are most distant from each
              // other in terms of their log-scaled x-coordinate.
              sortDim = 0;
              logScale = true;
              break;
            }
            case 2: {
              // Several times, randomly pick 3 points and take the
              // triple whose three points are most distant from each
              // other in terms of their y-coordinate.
              sortDim = 1;
              logScale = false;
              break;
            }
            case 3: {
              // Several times, randomly pick 3 points and take the
              // triple whose three points are most distant from each
              // other in terms of their log-scaled y-coordinate.
              sortDim = 1;
              logScale = true;
              break;
            }
            default: {
              // Randomly pick 3 points.
              sortDim = -1;
              logScale = false;
            }
          }

          // OK, now that we have chosen the policy according to which we
          // will pick the points, we now actually need to pick them.
          // Since that may go wrong, i.e., we may pick points which
          // cannot be used to interpolate a starting fitting, we may
          // need to attempt this several times.
          for (trials = 200; (--trials) >= 0;) {

            curIX = curJX = curKX = curIY = curJY = curKY = //
            bestIX = bestJX = bestKX = bestIY = bestJY = bestKY = Double.NaN;
            maxY = bestDist = Double.NEGATIVE_INFINITY;

            // If we have a "most-distant" policy, we will try to pick 27
            // times 3 points. This should leave us with at least some
            // triplets that have one point in the first 33% quantil, one
            // point in the second 33% quantil, and one point in the
            // third 33% quantil of the data along whichever axis we
            // pick.
            inner: for (i = 27; (--i) >= 0;) {

              // Now interpolation of starting points may only work if
              // all three x-coordinates and all three y-coordinates is
              // different. Hence, for each point triplet, we may need to
              // try a few times to meet this condition. However, this
              // condition may be very unlikely or impossible to meet, in
              // which case a maximum step number will kick in to prevent
              // endless loops.
              innermost: for (j = 100; (--j) >= 0;) {
                curI = random.nextInt(m);

                do {
                  curJ = random.nextInt(m);
                } while (curI == curJ);

                do {
                  curK = random.nextInt(m);
                } while ((curK == curI) || (curK == curJ));

                curIX = data.getDouble(curI, 0);
                curIY = data.getDouble(curI, 1);
                if (curIY > maxY) {
                  maxY = curIY;
                }
                curJX = data.getDouble(curJ, 0);
                curJY = data.getDouble(curJ, 1);
                if (curJY > maxY) {
                  maxY = curJY;
                }
                curKX = data.getDouble(curK, 0);
                curKY = data.getDouble(curK, 1);
                if (curKY > maxY) {
                  maxY = curKY;
                }
                if ((curIX != curJX) && (curIX != curKX)
                    && (curJX != curKX) && //
                    (curIY != curJY) && (curIY != curKY)
                    && (curJY != curKY)) {
                  break innermost;
                }
              }

              // Now we need to check according to which dimension we
              // want to sort the points: either x, or y, or no sorting
              // (in which case we can just take the point triplet
              // currently at hand and run with it).
              switch (sortDim) {
                case 0: {
                  curIZ = curIX;
                  curJZ = curJX;
                  curKZ = curKX;
                  break;
                }
                case 1: {
                  curIZ = curIY;
                  curJZ = curJY;
                  curKZ = curKY;
                  break;
                }
                default: {
                  bestIX = curIX;
                  bestIY = curIY;
                  bestJX = curJX;
                  bestJY = curJY;
                  bestKX = curKX;
                  bestKY = curKY;
                  break inner;
                }
              }

              // Unwounded sorting:Find the maximum,minimum,and
              // medianvalue of the sorting dimension.
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

              // Log-scale data if necessary.
              if (logScale) {
                t = ((sortDim == 0) ? this.m_minX : this.m_minY);
                if (t <= 0d) {
                  max = (max - t) + 1d;
                  med = (med - t) + 1d;
                  min = (min - t) + 1d;
                }
                max = Math.log(max);
                med = Math.log(med);
                min = Math.log(min);
              }

              // Compute the current distance, the sum of the distance
              // from the first to the second and from the second to the
              // third point.
              max -= med;
              med -= min;
              curDist = ((max * max) + (med * med));

              // Remember the triplet where the three points are farthest
              // away along one axis.
              if ((curDist > bestDist) && MathUtils.isFinite(curDist)) {
                bestIX = curIX;
                bestJX = curJX;
                bestKX = curKX;
                bestIY = curIY;
                bestJY = curJY;
                bestKY = curKY;
                bestDist = curDist;
              }
            }

            // OK, now we got the three points, try to compute a guess.
            if (this.guessBasedOn3Points(//
                bestIX, bestIY, //
                bestJX, bestJY, //
                bestKX, bestKY, //
                parameters, random)) {
              return; // guess found? quit
            }

            // From the three points, we could not find a guess. So let's
            // try again.
          }
        }
      }
    }

    // no success?
    this._fallback(maxY, random, parameters);
  }

  /**
   * A fallback if all conventional guessing failed
   * 
   * @param maxY
   *          the maximum {@code y}-coordinate encountered
   * @param random
   *          the random number generator
   * @param parameters
   *          the parameters
   */
  void _fallback(final double maxY, final Random random,
      final double[] parameters) {
    parameters[0] = random.nextGaussian();
    parameters[1] = random.nextGaussian();
    parameters[2] = random.nextGaussian();
  }
}
