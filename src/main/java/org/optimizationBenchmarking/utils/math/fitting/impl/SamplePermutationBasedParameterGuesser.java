package org.optimizationBenchmarking.utils.math.fitting.impl;

import java.util.Arrays;
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
  private final double[] m_bestGuess2;

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
    this.m_bestGuess2 = new double[parameterCount];

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

    temp = this.m_errorTemp;
    for (i = temp.length, j = (i << 1); (--i) >= 0;) {
      temp[i] = Math.abs(points[--j] - //
          this.value(points[--j], parameters));
    }
    return AddN.destructiveSum(temp);
  }

  /**
   * compute the median of some numbers
   *
   * @param a
   *          the numbers
   * @return the median
   */
  private static final double __finiteMed(final double[] a) {
    int right, i, j, k, mid;
    double ak, ai, median, realMedian;

    right = a.length - 1;
    Arrays.sort(a);

    // move NaNs to the right
    while (0 <= right && Double.isNaN(a[right])) {
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

    // compute median
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

  /** {@inheritDoc} */
  @Override
  protected final boolean guess(final double[] points, final double[] dest,
      final Random random) {
    final double[] currentGuess, tempPoints, best2;
    final double[][] medians;
    boolean improved;
    double oldError, newError, best2Error;
    int steps, i, index;

    steps = 100;

    currentGuess = this.m_currentGuess;
    tempPoints = this.m_tempPoints;
    medians = this.m_medianGuess;
    newError = Double.POSITIVE_INFINITY;
    best2Error = Double.POSITIVE_INFINITY;
    best2 = this.m_bestGuess2;

    while ((--steps) > 0) {
      this.fallback(points, dest, random);

      do {
        oldError = newError;
        improved = false;

        // try all permutations of the points
        this.m_pointPermutations.reset();
        index = 0;
        for (int[] permutation : this.m_pointPermutations) {
          i = 0;
          for (int p : permutation) {
            p <<= 1;
            tempPoints[i++] = points[p];
            tempPoints[i++] = points[p + 1];
          }

          // make a guess based on the current permutation of points
          this.guessBasedOnPermutation(tempPoints, dest, currentGuess);

          // compute the quality of the guess, keep it if it is better
          newError = this.error(tempPoints, currentGuess);
          if (newError >= 0d) {// is the guess feasible? (not NaN)
            if (newError < oldError) {// is the guess better than the best?
              System.arraycopy(currentGuess, 0, dest, 0, dest.length);
              if (newError <= 0d) {
                return true;
              }
              improved = true;
              oldError = newError;
            } else {// or is it at least the new second best?
              if (newError < best2Error) {
                System.arraycopy(currentGuess, 0, best2, 0, best2.length);
                best2Error = newError;
              }
            }
          }

          // collect all guesses to compute median parameter values
          for (i = medians.length; (--i) >= 0;) {
            medians[i][index] = currentGuess[i];
          }
          index++;
        }

        // compute median parameter values
        for (i = medians.length; (--i) >= 0;) {
          currentGuess[i] = __finiteMed(medians[i]);
        }

        // compute the quality of the guess, keep it if it is better
        newError = this.error(tempPoints, currentGuess);
        if ((newError >= 0d) && (newError < oldError)) {
          System.arraycopy(currentGuess, 0, dest, 0, dest.length);
          if (newError <= 0d) {
            return true;
          }
          improved = true;
          oldError = newError;
        }

        // compute mean of best and second-best guess
        for (i = currentGuess.length; (--i) >= 0;) {
          currentGuess[i] = ((0.5d * dest[i]) + (0.5d * best2[i]));
        }
        newError = this.error(tempPoints, currentGuess);
        if ((newError >= 0d) && (newError < oldError)) {
          System.arraycopy(currentGuess, 0, dest, 0, dest.length);
          if (newError <= 0d) {
            return true;
          }
          improved = true;
          oldError = newError;
        }

        if ((--steps) <= 0) {
          // runtime is up, check if valid guess found
          return MathUtils.isFinite(oldError);
        }
      } while (improved);

      // is guess feasible?
      if (MathUtils.isFinite(oldError)) {
        return true; // ok, no improvement, return
      }
    }

    return false;
  }
}