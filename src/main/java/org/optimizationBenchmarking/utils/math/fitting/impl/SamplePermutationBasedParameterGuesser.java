package org.optimizationBenchmarking.utils.math.fitting.impl;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.combinatorics.PermutationIterator;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.AddN;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A parameter guesser which tries to compute the parameter values for each
 * permutation of the points from a sample.
 */
public abstract class SamplePermutationBasedParameterGuesser
    extends SampleBasedParameterGuesser {

  /** the current guess in progress */
  private final double[] m_currentGuess;

  /** the temporary point array */
  private final double[] m_tempPoints;

  /** the point permutations */
  private final PermutationIterator m_pointPermutations;

  /** the temporary array for errors */
  private final double[] m_errorTemp;

  /** the holder for the median of all guesses */
  private final double[][] m_medianGuess;

  /** the second best guess */
  private final double[] m_best2Guess;

  /** the old error */
  private double m_bestError;
  /** the best-2 error */
  private double m_best2Error;
  /** did we improve? */
  private boolean m_improved;

  /**
   * Create the parameter guesser
   *
   * @param data
   *          the data
   * @param parameterCount
   *          the number of parameters to guess
   * @param pointCount
   *          the number of required points
   */
  protected SamplePermutationBasedParameterGuesser(final IMatrix data,
      final int parameterCount, final int pointCount) {
    super(data, pointCount);

    this.m_currentGuess = new double[parameterCount];
    this.m_best2Guess = new double[parameterCount];

    this.m_errorTemp = new double[pointCount];
    this.m_tempPoints = new double[pointCount << 1];
    this.m_pointPermutations = new PermutationIterator(pointCount, true);

    this.m_medianGuess = new double[parameterCount][//
    (int) (this.m_pointPermutations.size())];
  }

  /**
   * Create the parameter guesser
   *
   * @param data
   *          the data
   * @param parameterCount
   *          the number of parameters to guess
   */
  protected SamplePermutationBasedParameterGuesser(final IMatrix data,
      final int parameterCount) {
    this(data, parameterCount, parameterCount);
  }

  /**
   * Compute the value of the function to guess
   *
   * @param x
   *          the {@code x}-coordinate
   * @param parameters
   *          the parameters
   * @return the {@code y} value
   */
  protected abstract double value(final double x,
      final double[] parameters);

  /**
   * compute the error
   *
   * @param points
   *          the points
   * @param parameters
   *          the parameters
   * @return the error
   */
  protected final double error(final double[] points,
      final double[] parameters) {
    final double[] temp;
    int i, j;

    for (final double value : parameters) {
      if (!(MathUtils.isFinite(value))) {
        return Double.POSITIVE_INFINITY;
      }
    }

    temp = this.m_errorTemp;
    for (i = temp.length, j = (i << 1); (--i) >= 0;) {
      temp[i] = Math.abs(points[--j] - //
          this.value(points[--j], parameters));
    }

    return AddN.destructiveSum(temp);
  }

  /**
   * Compute the median of some numbers. The sorting code has been stolen
   * from {@link java.util.Arrays#sort(double[])}'s internal insertion
   * sort.
   *
   * @param a
   *          the numbers
   * @param count
   *          the number of points in the list
   * @return the median
   */
  private static final double __finiteMed(final double[] a,
      final int count) {
    int right, i, j, k, mid;
    double ak, ai, median, realMedian;

    right = (count - 1);

    // move NaNs to the right: there should not be any
    while ((0 <= right) && Double.isNaN(a[right])) {
      --right;
    }
    for (k = right; --k >= 0;) {
      ak = a[k];
      if (ak != ak) { // a[k] is NaN
        a[k] = a[right];
        a[right] = ak;
        --right;
      }
    }

    // perform insertion sort
    for (i = 0, j = i; i < right; j = ++i) {
      ai = a[i + 1];
      while (ai < a[j]) {
        a[j + 1] = a[j];
        if (j-- == 0) {
          break;
        }
      }
      a[j + 1] = ai;
    }

    // compute the finite median
    mid = (a.length >>> 1);
    realMedian = median = a[mid];
    if ((a.length & 1) == 0) {
      ai = a[mid - 1];

      realMedian = (0.5d * (ai + median));
      if (MathUtils.isFinite(realMedian)) {
        return realMedian;
      }

      realMedian = ((05d * ai) + (0.5d * median));
      if (MathUtils.isFinite(realMedian)) {
        return realMedian;
      }
    }
    if (MathUtils.isFinite(median)) {
      return median;
    }

    // ok, no finite median, try to find the closest finite number
    for (i = 1; i < mid; i++) {
      median = a[mid - i];
      if (MathUtils.isFinite(median)) {
        return median;
      }
      median = a[mid + i];
      if (MathUtils.isFinite(median)) {
        return median;
      }
    }

    median = a[0];
    if (MathUtils.isFinite(median)) {
      return median;
    }
    return realMedian;
  }

  /**
   * guess the parameter values based on a point permutation
   *
   * @param points
   *          the {@code (x,y)} value pairs
   * @param bestGuess
   *          the best current known guess
   * @param destGuess
   *          the destination array for the current guess
   */
  protected abstract void guessBasedOnPermutation(final double[] points,
      final double[] bestGuess, final double[] destGuess);

  /**
   * process the current guess
   *
   * @param dest
   *          the destination array to receive the guess
   * @param points
   *          the points
   * @return {@code 0} if we can stop now because we found the perfect
   *         guess, {@code 1} if there was an improvement, {@code 2} if
   *         not, {@code 3} if the result is not finite
   */
  private final int __processCurrentGuess(final double[] points,
      final double[] dest) {
    final double newError;

    // compute the quality of the guess, keep it if it is better
    newError = this.error(this.m_tempPoints, this.m_currentGuess);
    if (newError >= 0d) {// is the guess feasible? (not NaN)
      // is the guess better than the best?
      if (newError < this.m_bestError) { // yes it is!
        System.arraycopy(dest, 0, this.m_best2Guess, 0, dest.length);
        this.m_best2Error = this.m_bestError;
        System.arraycopy(this.m_currentGuess, 0, dest, 0, dest.length);
        if (newError <= 0d) {
          return 0;
        }
        this.m_improved = true;
        this.m_bestError = newError;
        return 1;
      }
      // or is it at least the new second best?
      if ((newError < this.m_best2Error)
          && (newError > this.m_bestError)) {
        System.arraycopy(this.m_currentGuess, 0, this.m_best2Guess, 0,
            this.m_best2Guess.length);
        this.m_best2Error = newError;
      }
    }
    return (MathUtils.isFinite(newError) ? 2 : 3);
  }

  /**
   * apply a permutation
   *
   * @param points
   *          the points
   * @param permutation
   *          the permutation
   * @param dest
   *          the destination
   */
  private static final void __applyPermutation(final double[] points,
      final int[] permutation, final double[] dest) {
    int index;

    index = (-1);
    for (int p : permutation) {
      p <<= 1;
      dest[++index] = points[p];
      dest[++index] = points[p + 1];
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean guess(final double[] points, final double[] dest,
      final Random random) {
    final double[] currentGuess, tempPoints, best2;
    final double[][] medians;
    int steps, steps2, i, medianCount;
    double factor;

    currentGuess = this.m_currentGuess; // the current guess
    tempPoints = this.m_tempPoints; // current permutation of the sample
    medians = this.m_medianGuess; // medians of all guesses
    best2 = this.m_best2Guess;
    steps = 20;

    for (;;) {
      this.fallback(points, dest, random);

      do {
        this.m_bestError = Double.POSITIVE_INFINITY;
        this.m_best2Error = Double.POSITIVE_INFINITY;
        this.m_improved = false;

        // try all permutations of the points
        this.m_pointPermutations.reset();
        medianCount = 0;
        for (final int[] permutation : this.m_pointPermutations) {
          SamplePermutationBasedParameterGuesser.__applyPermutation(points,
              permutation, tempPoints); // apply the
          // permutation

          // make a guess based on the current permutation of points
          this.guessBasedOnPermutation(tempPoints, dest, currentGuess);

          // compute the quality of the guess, keep it if it is better
          switcher: switch (this.__processCurrentGuess(points, dest)) {
            case 0: {
              return true;
            }
            case 3: {
              break switcher; // non-finite result!
            }
            default: {

              // collect all guesses to compute median parameter values
              for (i = medians.length; (--i) >= 0;) {
                medians[i][medianCount] = currentGuess[i];
              }
              ++medianCount;
            }
          }
        }

        // did we have any solution with finite result?
        if (medianCount > 0) {// we did!
          // compute median parameter values
          for (i = medians.length; (--i) >= 0;) {
            currentGuess[i] = SamplePermutationBasedParameterGuesser
                .__finiteMed(medians[i], medianCount);
          }

          // compute the quality of the guess, keep it if it is better
          if (this.__processCurrentGuess(points, dest) <= 0) {
            return true;
          }
        }

        // compute mean of best and second-best guess
        bestAndSecondBest: for (steps2 = 5; (--steps2) >= 0;) {
          for (i = currentGuess.length; (--i) >= 0;) {
            currentGuess[i] = ((0.5d * dest[i]) + (0.5d * best2[i]));
          }
          switch (this.__processCurrentGuess(points, dest)) {
            case 0: {
              return true;
            }
            case 1: {
              continue bestAndSecondBest;
            }
            default: {
              break bestAndSecondBest;
            }
          }
        }

        // compute a guess being moved from the best guess into the
        // opposite direction of the second best guess
        factor = 0.5d;
        bestAwaySecondBest: for (steps2 = 5; (--steps2) >= 0;) {
          for (i = currentGuess.length; (--i) >= 0;) {
            currentGuess[i] = (dest[i] + (factor * (dest[i] - best2[i])));
          }
          switch (this.__processCurrentGuess(points, dest)) {
            case 0: {
              return true;
            }
            case 1: {
              factor *= 2.2d;
              continue bestAwaySecondBest;
            }
            default: {
              break bestAwaySecondBest;
            }
          }
        }

        if ((--steps) <= 0) {
          // runtime is up, check if valid guess found
          return MathUtils.isFinite(this.m_bestError);
        }
      } while (this.m_improved);

      // is guess feasible?
      if (MathUtils.isFinite(this.m_bestError)) {
        return true; // ok, no improvement, return
      }
    }
  }
}