package org.optimizationBenchmarking.utils.ml.fitting.impl.lssimplex;

import org.optimizationBenchmarking.utils.math.MathUtils;

/** the class managing the solutions we have */
final class _CandidateManager {

  /** the candidates */
  private final _Candidate[] m_candidates;

  /** the number of parameters */
  private final int m_numParameters;

  /** the number of managed candidates */
  private int m_count;

  /**
   * create the candidate manager
   *
   * @param numParameters
   *          the number of parameters
   * @param maxCandidates
   *          the maximum number of candidates
   */
  _CandidateManager(final int maxCandidates, final int numParameters) {
    super();

    this.m_candidates = new _Candidate[maxCandidates];
    this.m_numParameters = numParameters;
  }

  /**
   * Obtain the next candidate record
   *
   * @return the next candidate record
   */
  final _Candidate _create() {
    final int count;
    final _Candidate res;

    count = this.m_count;
    this.m_count = (count + 1);
    res = this.m_candidates[count];
    if (res != null) {
      res.m_processedBy = 0;
      return res;
    }

    return (this.m_candidates[count] = new _Candidate(
        this.m_numParameters));
  }

  /** dispose the last solution */
  final void _dispose() {
    --this.m_count;
  }

  /**
   * Check if a given candidate solution has already been found.
   *
   * @param candidate
   *          the solution to check
   * @return the candidate to be used from now on
   */
  final _Candidate _tryCoalesce(final _Candidate candidate) {
    final int count;
    int solutionIndex, valueIndex;

    count = (this.m_count - 1);

    if (candidate != this.m_candidates[count]) {
      // otherwise, the solution has already been coalesced
      return candidate;
    }

    solutionIndex = (-1);
    outer: for (final _Candidate other : this.m_candidates) {
      if ((++solutionIndex) >= count) {
        return candidate;
      }

      valueIndex = (-1);
      for (final double d : other.solution) {
        if (MathUtils.difference(d,
            candidate.solution[++valueIndex]) > 2) {
          continue outer;
        }
      }

      other.m_processedBy |= candidate.m_processedBy;
      this.m_count = count;// discard last element
      return other;
    }
    return candidate; // no same element found
  }

  /**
   * check if a vector is sufficiently unique
   *
   * @param element
   *          the vector
   * @param limit
   *          the limit acceptable distance
   * @return {@code true} if the vector is sufficiently unique,
   *         {@code false} otherwise
   */
  final boolean _isUnique(final double[] element, final double limit) {
    final int count;
    double dist, bi;
    int solutionIndex, valueIndex;

    solutionIndex = (-1);
    count = this.m_count;
    for (final _Candidate vector : this.m_candidates) {
      if ((++solutionIndex) >= count) {
        return true;
      }

      dist = 0d;
      valueIndex = (-1);
      for (final double ai : vector.solution) {
        bi = element[++valueIndex];
        if (ai != bi) {
          bi = ((ai - bi) / Math.max(Double.MIN_NORMAL, //
              Math.max(Math.abs(ai), Math.abs(bi))));
          dist += (bi * bi);
        }
      }

      if (dist <= (valueIndex * limit)) {
        return false;
      }
    }

    return true;
  }
}
