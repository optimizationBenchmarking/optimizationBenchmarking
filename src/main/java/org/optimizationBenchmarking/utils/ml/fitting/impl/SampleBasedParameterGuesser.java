package org.optimizationBenchmarking.utils.ml.fitting.impl;

import java.util.Random;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Hypot;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.fitting.spec.DefaultParameterGuesser;

/**
 * <p>
 * This is a base class for parameter guessers working on a subset of the
 * data. They should be used multiple times, say a few hundred times, to
 * obtain good-enough guesses. Guesses are computed based on a very small
 * number of points, say 2, 3, or 4, usually as same as many points as the
 * corresponding model has variables. These points are randomly chosen, but
 * with a bias of being distant from each other according to a randomly
 * chosen distance measure.
 * </p>
 * <p>
 * The general idea is as follows: Imagine you want to fit the
 * {@linkplain org.optimizationBenchmarking.utils.ml.fitting.models.QuadraticModel
 * quadratic curve}, {@code a+b*x+c*x^2} through many points. One way to
 * compute starting values for the parameters {@code a}, {@code b}, and
 * {@code c} would be to take three points from the data set, maybe one on
 * the left side (small {@code x} value), one in the middle (medium
 * {@code x} value), and one on the right side (comparatively large
 * {@code x} value). We can
 * {@linkplain org.optimizationBenchmarking.utils.math.Polynomials#degree2FindCoefficients(double, double, double, double, double, double, double[])
 * compute} the exact values of {@code a}, {@code b}, and {@code c} of a
 * quadratic function going through the three points. Of course, since we
 * only took three points from the data set, our guess may be bad and
 * strongly depends on the points chosen. However, if we use this guesser a
 * couple of times and keep the one guess with the smallest error over the
 * whole data set, we may get a reasonable starting point for fitting. For
 * fitting polynomials, such a complex procedure may not be necessary. For
 * more complicated functions, it may be a good idea.
 * </p>
 */
public abstract class SampleBasedParameterGuesser
    extends DefaultParameterGuesser {

  /** the data matrix */
  private final IMatrix m_data;

  /** the minimum x value */
  protected final double m_minX;
  /** the minimum y value */
  protected final double m_minY;
  /** the maximum x value */
  protected final double m_maxX;
  /** the maximum y value */
  protected final double m_maxY;
  /**
   * the {@code x} range, i.e.,
   * <code>{@link #m_maxX}-{@link #m_minX}</code>
   */
  protected final double m_rangeX;
  /**
   * the {@code y} range, i.e.,
   * <code>{@link #m_maxY}-{@link #m_minY}</code>
   */
  protected final double m_rangeY;

  /** the maximum log-scale {@code x} */
  private final double m_logScaleX;
  /** the maximum log-scale {@code Y} */
  private final double m_logScaleY;

  /** the the logarithm of the minimum {@code x}-coordinate */
  private final double m_logMinX;
  /** the the logarithm of the minimum {@code Y}-coordinate */
  private final double m_logMinY;

  /** the temporary variable holding the point selection */
  private final double[] m_selection;
  /** the temporary variable holding candidate selection */
  private final double[] m_candidate;
  /** the chosen indexes */
  private final int[] m_indexes;

  /**
   * Create the sample-based guesser. Normally, the number of rows in the
   * {@code data} matrix should be much higher than the number of
   * {@code required} points.
   *
   * @param data
   *          the data
   * @param required
   *          the number of points needed for an educated guess
   */
  protected SampleBasedParameterGuesser(final IMatrix data,
      final int required) {
    super();

    final int available;
    double t, minX, minY, maxX, maxY;
    int i, j;

    available = data.m();

    if (required < available) {
      this.m_data = data;
      i = (available - 1);
      minX = maxX = data.getDouble(i, 0);
      minY = maxY = data.getDouble(i, 1);

      for (i = available; (--i) >= 0;) {
        t = data.getDouble(i, 0);
        if (t < minX) {
          minX = t;
        } else {
          if (t > maxX) {
            maxX = t;
          }
        }
        t = data.getDouble(i, 1);
        if (t < minY) {
          minY = t;
        } else {
          if (t > maxY) {
            maxY = t;
          }
        }
      }

      i = (required << 1);
      this.m_selection = new double[i];
      this.m_candidate = new double[i];
      this.m_indexes = new int[required];
    } else {
      if (available > 0) {
        j = (available << 1);
        minX = minY = Double.POSITIVE_INFINITY;
        maxX = maxY = Double.NEGATIVE_INFINITY;
        this.m_selection = new double[j];
        for (i = available; (--i) >= 0;) {
          this.m_selection[--j] = t = data.getDouble(i, 1);
          if (t < minY) {
            minY = t;
          }
          if (t > maxY) {
            maxY = t;
          }
          this.m_selection[--j] = t = data.getDouble(i, 0);
          if (t < minX) {
            minX = t;
          }
          if (t > maxX) {
            maxX = t;
          }
        }
      } else {
        this.m_selection = null;
        minX = maxX = minY = maxY = Double.NaN;
      }
      this.m_data = null;
      this.m_candidate = null;
      this.m_indexes = null;
    }

    this.m_minX = minX;
    this.m_maxX = maxX;
    this.m_rangeX = (maxX - minX);
    this.m_minY = minY;
    this.m_maxY = maxY;
    this.m_rangeY = (maxY - minY);

    if (minX <= 0d) {
      this.m_logScaleX = Math.log((maxX - minX) + 1d);
      this.m_logMinX = Double.NaN;
    } else {
      this.m_logMinX = Math.log(minX);
      this.m_logScaleX = Math.log(maxX) - this.m_logMinX;
    }

    if (minY <= 0d) {
      this.m_logScaleY = Math.log((maxY - minY) + 1d);
      this.m_logMinY = Double.NaN;
    } else {
      this.m_logMinY = Math.log(minY);
      this.m_logScaleY = Math.log(maxY) - this.m_logMinY;
    }
  }

  /**
   * guess if all required points are available
   *
   * @param points
   *          an array with {@code x, y} coordinate pairs
   * @param dest
   *          the destination
   * @param random
   *          the random number generator
   * @return {@code true} if guessing was successful
   */
  protected boolean guess(final double[] points, final double[] dest,
      final Random random) {
    return this.fallback(points, dest, random);
  }

  /**
   * guess not all required points are available
   *
   * @param points
   *          an array with {@code x, y} coordinate pairs
   * @param dest
   *          the destination
   * @param random
   *          the random number generator
   * @return {@code true} if guessing was successful
   */
  protected boolean fallback(final double[] points, final double[] dest,
      final Random random) {
    this.fallback(dest, random);
    return true;
  }

  /**
   * Guess if {@link #guess(double[], double[], Random)} or
   * {@link #fallback(double[], double[], Random)} have failed, i.e.,
   * returned {@code false}.
   *
   * @param dest
   *          the destination
   * @param random
   *          the random number generator
   */
  protected void fallback(final double[] dest, final Random random) {
    super.createRandomGuess(dest, random);
  }

  /**
   * Draw a random set of points from the data matrix. All points will stem
   * from different rows of the matrix and it is attempted to ensure that
   * they differ in all of their {@code x} and {@code y} coordinates. The
   * points in the array are sorted in lexicographically.
   *
   * @param currentChoice
   *          the destination array to receive the candidate points
   * @param indexes
   *          the temporary array to use for ensuring index uniqueness
   * @param data
   *          the matrix to draw from
   * @param random
   *          the random number generator
   * @param useX
   *          should we use the {@code x}-coordinate for sorting?
   * @param useY
   *          should we use the {@code y}-coordinate for sorting?
   */
  private static final void __drawCandidate(final double[] currentChoice,
      final int[] indexes, final IMatrix data, final Random random,
      final boolean useX, final boolean useY) {
    final int m, n;
    double x, y;
    int i, j, index, uniqueXYAttempt;

    n = (currentChoice.length >>> 1);
    m = data.m();
    uniqueXYAttempt = (3 + Math.min(30, n));

    // Draw n different points. The goal is to find points with different
    // coordinates.
    for (i = 0; i < n; i++) {

      // We ideally want to find points which are different in all of their
      // x and y coordinates.
      findUniqueXY: for (;;) {

        // Find a unique index, i.e., a point not yet used. This will
        // always be possible, since m>n.
        findUniqueIndex: for (;;) {
          index = random.nextInt(m);
          for (j = i; (--j) >= 0;) {
            if (indexes[j] == index) {
              continue;
            }
          }
          break findUniqueIndex;
        }

        // Check whether the x and y values are unique
        x = data.getDouble(index, 0);
        y = data.getDouble(index, 1);
        isOK: {
          for (j = (i << 1); j > 0;) {
            if (EComparison.compareDoubles(y, currentChoice[--j]) == 0) {
              break isOK;
            }
            if (EComparison.compareDoubles(x, currentChoice[--j]) == 0) {
              break isOK;
            }
          }
          break findUniqueXY;
        }

        if ((--uniqueXYAttempt) < 0) {
          break findUniqueXY;
        }
      }

      indexes[i] = index;
      j = (i << 1);
      currentChoice[j] = x;
      currentChoice[j + 1] = y;
    }

    if (useX || useY) {
      // Now sort the points.
      for (i = n; (--i) > 0;) {
        for (j = i; (--j) >= 0;) {
          SampleBasedParameterGuesser.__compareAndSwap(currentChoice, j, i,
              useX, useY);
        }
      }
    }
  }

  /**
   * Compare the points at indexes {@code i} and {@code j} and swap them if
   * necessary. {@code i} must be less than {@code j}. This method is used
   * in the lexicographic sorting.
   *
   * @param data
   *          the data array
   * @param i
   *          the first,smaller index
   * @param j
   *          the second,larger index
   * @param useX
   *          should we use the {@code x}-coordinate for sorting?
   * @param useY
   *          should we use the {@code y}-coordinate for sorting?
   */
  private static final void __compareAndSwap(final double[] data,
      final int i, final int j, final boolean useX, final boolean useY) {
    double x1, x2, y1, y2;
    final int ii, jj, resX;

    ii = (i << 1);
    jj = (j << 1);

    x1 = data[ii];
    x2 = data[jj];

    if (useX) {
      resX = EComparison.compareDoubles(x1, x2);
      if (resX < 0) {
        return;
      }
    } else {
      resX = 0;
    }

    y1 = data[ii + 1];
    y2 = data[jj + 1];

    if (useY) {
      if ((resX == 0) && (EComparison.compareDoubles(y1, y2) < 0)) {
        return;
      }
    }

    data[ii] = x2;
    data[ii + 1] = y2;
    data[jj] = x1;
    data[jj + 1] = y1;
  }

  /**
   * Format an {@code x}-coordinate for the distance computation
   *
   * @param curX
   *          the coordinate
   * @param logScaleX
   *          the log scale indicator
   * @return the formatted result
   */
  private final double __formatX(final double curX,
      final boolean logScaleX) {
    final double d;
    if (logScaleX) {
      if (this.m_minX <= 0d) {
        d = Math.log((curX - this.m_minX) + 1d);
      } else {
        d = (Math.log(curX) - this.m_logMinX);
      }
      return (d / this.m_logScaleX);
    }
    return ((curX - this.m_minX) / this.m_rangeX);
  }

  /**
   * Format an {@code y}-coordinate for the distance computation
   *
   * @param curY
   *          the coordinate
   * @param logScaleY
   *          the log scale indicator
   * @return the formatted result
   */
  private final double __formatY(final double curY,
      final boolean logScaleY) {
    final double d;
    if (logScaleY) {
      if (this.m_minY <= 0d) {
        d = Math.log((curY - this.m_minY) + 1d);
      } else {
        d = (Math.log(curY) - this.m_logMinY);
      }
      return (d / this.m_logScaleY);
    }
    return ((curY - this.m_minY) / this.m_rangeY);
  }

  /**
   * Compute the quality of a point sample. The sample is assumed to be
   * lexicographically sorted. The quality is the minimal distance of two
   * successive points in the point set and larger quality values are
   * better. In other words, we want to find points which are widely spread
   * over the available data range.
   *
   * @param candidate
   *          the candidate sample
   * @param useX
   *          should we use the {@code x}-coordinates in the distance
   *          computation?
   * @param useY
   *          should we use the {@code y}-coordinates in the distance
   *          computation?
   * @param logScaleX
   *          should we log-scale the {@code x}-coordinates in the distance
   *          computation?
   * @param logScaleY
   *          should we log-scale the {@code y}-coordinates in the distance
   *          computation?
   * @return the quality value
   */
  private final double __quality(final double[] candidate,
      final boolean useX, final boolean useY, final boolean logScaleX,
      final boolean logScaleY) {
    double quality, curX, curY, prevX, prevY;
    int i;

    curX = (useX ? curX = this.__formatX(candidate[0], logScaleX) : 0d);
    curY = (useY ? curY = this.__formatY(candidate[1], logScaleY) : 0d);

    i = 2;
    quality = Double.POSITIVE_INFINITY;
    while (i < candidate.length) {
      prevX = curX;
      prevY = curY;

      curX = (useX ? curX = this.__formatX(candidate[i], logScaleX) : 0d);
      ++i;
      curY = (useY ? curY = this.__formatY(candidate[i], logScaleY) : 0d);
      ++i;

      quality = Math.min(quality, //
          Hypot.INSTANCE.computeAsDouble((curX - prevX), (curY - prevY)));
    }

    return quality;
  }

  /**
   * Create a random guess based on a sample of the data matrix of this
   * guesser. Attempts will be made to draw points which are as different
   * from each other as possible according to a randomly chosen metric.
   * Based on these points, the parameters will be guessed.
   *
   * @param parameters
   *          the destination array to receive the guess
   * @param random
   *          the random numbers generator
   */
  @Override
  public final void createRandomGuess(final double[] parameters,
      final Random random) {
    final IMatrix data;
    final double[] bestChoice, currentChoice;
    final int[] indexes;
    boolean useX, useY, logScaleX, logScaleY;
    double bestQuality, currentQuality;
    int distanceMeasureChoice, pointSetChoice, guessAttempts, pointChoice;

    if ((data = this.m_data) != null) {

      bestChoice = this.m_selection;
      currentChoice = this.m_candidate;
      indexes = this.m_indexes;

      for (distanceMeasureChoice = 3; (--distanceMeasureChoice) >= 0;) {
        // In the main loop, we first choose a distance measure, then
        // attempt to find points far away from each other under this
        // measure.
        switch (random.nextInt(9)) {
          case 0: {
            useX = useY = logScaleX = logScaleY = false;
            break;
          }
          case 1: {
            useX = true;
            useY = logScaleX = logScaleY = false;
            break;
          }
          case 2: {
            useY = true;
            useX = logScaleX = logScaleY = false;
            break;
          }
          case 3: {
            useX = useY = true;
            logScaleX = logScaleY = false;
            break;
          }
          case 4: {
            useX = logScaleX = true;
            useY = logScaleY = false;
            break;
          }
          case 5: {
            useX = useY = logScaleX = true;
            logScaleY = false;
            break;
          }
          case 6: {
            useY = logScaleY = true;
            useX = logScaleX = false;
            break;
          }
          case 7: {
            useX = useY = logScaleY = true;
            logScaleX = false;
            break;
          }
          default: {
            useX = useY = logScaleX = logScaleY = true;
            break;
          }
        }

        for (pointSetChoice = 20; (--pointSetChoice) >= 0;) {
          // Draw a set of points.
          SampleBasedParameterGuesser.__drawCandidate(bestChoice, indexes,
              data, random, useX, useY);
          if (useX || useY) {// we do care about the distance
            bestQuality = this.__quality(bestChoice, useX, useY, logScaleX,
                logScaleY);
            for (pointChoice = 30; (--pointChoice) >= 0;) {
              SampleBasedParameterGuesser.__drawCandidate(currentChoice,
                  indexes, data, random, useX, useY);
              currentQuality = this.__quality(currentChoice, useX, useY,
                  logScaleX, logScaleY);
              if (currentQuality > bestQuality) {
                System.arraycopy(currentChoice, 0, bestChoice, 0,
                    bestChoice.length);
                bestQuality = currentQuality;
              }
            }
          }

          for (guessAttempts = 3; (--guessAttempts) >= 0;) {
            if (this.guess(bestChoice, parameters, random)) {
              return;
            }
          }
        }
      }
    } else {
      if (this.m_selection != null) {
        if (this.fallback(this.m_selection, parameters, random)) {
          return;
        }
      }
    }

    // no success?
    this.fallback(parameters, random);
  }
}
