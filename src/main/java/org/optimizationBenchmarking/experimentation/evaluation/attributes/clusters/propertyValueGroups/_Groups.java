package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;

/** a class representing a grouping */
final class _Groups implements Comparable<_Groups> {
  /** the groups */
  final _Group[] m_groups;

  /** the score */
  final double m_countScore;
  /** the balance score */
  final double m_balanceScore;
  /** the score for discontinuous grouping */
  final double m_discontinuousScore;

  /** the mode */
  final EGroupingMode m_groupingMode;

  /** the grouping-mode based parameter */
  final Object m_groupingParameter;

  /**
   * Create a grouping
   * 
   * @param groups
   *          the groups
   * @param minGroups
   *          the anticipated minimum number of groups
   * @param maxGroups
   *          the anticipated maximum number of groups
   * @param mode
   *          the grouping mode
   * @param parameter
   *          the parameter of the grouping mode
   */
  _Groups(final _Group[] groups, final int minGroups, final int maxGroups,
      final EGroupingMode mode, final Object parameter) {
    double balance;
    Object prev, cur;
    int count, discontinuous;
    long sum, sumSqr;

    count = 0;
    discontinuous = (-1);
    sum = sumSqr = 0L;
    prev = null;
    for (final _Group group : groups) {
      if ((group == null) || (group.m_size <= 0)) {
        break;
      }
      count++;
      sum += group.m_size;
      sumSqr += (group.m_size * group.m_size);
      cur = group.m_lower;
      if (!(EComparison.equals(cur, prev))) {
        discontinuous++;
      }
      prev = group.m_upper;
    }

    this.m_groups = new _Group[count];
    count = 0;
    for (final _Group group : groups) {
      if ((group == null) || (group.m_size <= 0)) {
        break;
      }
      this.m_groups[count++] = group.clone();
    }

    if (count < minGroups) {
      this.m_countScore = Div.INSTANCE.computeAsDouble(count, minGroups);
    } else {
      if (count > maxGroups) {
        this.m_countScore = Div.INSTANCE.computeAsDouble(count, maxGroups);
      } else {
        this.m_countScore = 0d;
      }
    }

    balance = Div.INSTANCE.computeAsDouble(sum, count);
    this.m_balanceScore = (Math.sqrt(Div.INSTANCE.computeAsDouble(sumSqr,
        count) - (balance * balance)) / balance);

    if ((mode != EGroupingMode.DISTINCT) && (discontinuous > 0)) {
      this.m_discontinuousScore = Div.INSTANCE.computeAsDouble(
          discontinuous, count);
    } else {
      this.m_discontinuousScore = 0d;
    }

    this.m_groupingMode = mode;
    this.m_groupingParameter = parameter;
  }

  /**
   * Check whether the values {@code a} and {@code b} differ relatively
   * less than {@code fraction}.
   * 
   * @param a
   *          the first value
   * @param b
   *          the second value
   * @param fraction
   *          the fraction
   * @return {@code true} if {@code a} and {@code b} differ relatively less
   *         than {@code fraction}
   */
  private static final boolean __inRange(final double a, final double b,
      final double fraction) {
    if (a == b) {
      return true;
    }

    return ((Math.abs(a - b) / Math.max(a, b)) <= fraction);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public final int compareTo(final _Groups o) {
    final int resCount, resBalance, resDisc, sum;

    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }

    resCount = ((this.m_countScore < o.m_countScore) ? (-1)
        : (this.m_countScore > o.m_countScore) ? (1) : 0);
    resDisc = ((this.m_discontinuousScore < o.m_discontinuousScore) ? (-1)
        : (this.m_discontinuousScore > o.m_discontinuousScore) ? (1) : 0);
    resBalance = ((this.m_balanceScore < o.m_balanceScore) ? (-1)
        : (this.m_balanceScore > o.m_balanceScore) ? (1) : 0);

    sum = (resCount + resDisc + resBalance);
    if (sum <= (-3)) {
      return (-1);
    }
    if (sum >= 3) {
      return 1;
    }

    // Some rugged heuristics to weight different measures against each
    // other:
    // Normally, we would take the grouping which fits the pre-defined
    // min/maxGroups the best.
    // However, if the groupings do not differ much in their count scores,
    // we may check whether they differ much in their discontinuous or
    // balance scores.
    if (((resCount == 0) || //
        (Math.max(this.m_countScore, o.m_countScore) < 0.1d) || //
        _Groups.__inRange(this.m_countScore, o.m_countScore, 0.05d) || //
        (Math.abs(this.m_groups.length - o.m_groups.length) <= 1))//
        && ((this.m_groups.length > 1) && (o.m_groups.length > 1))) {

      // If at least one of the groupings is discontinuous to some
      // noticeable degree and much more discontinuous than the other one,
      // we will take the less discontinuous one.
      if ((resDisc != 0) && //
          ((Math.max(this.m_discontinuousScore, o.m_discontinuousScore) > 0.1d) && //
          (!(_Groups.__inRange(this.m_discontinuousScore,
              o.m_discontinuousScore, 0.5d))))) {
        return resDisc;
      }

      // If the groups are not too different in terms of their
      // discontinuity, but differ quite a bit in terms of balance, we take
      // the better balanced one.
      if ((resDisc == 0) || //
          _Groups.__inRange(this.m_discontinuousScore,
              o.m_discontinuousScore, 0.1d)) {
        if ((resBalance != 0) && //
            (!(_Groups.__inRange(this.m_balanceScore, o.m_balanceScore,
                0.3d)))) {
          return resBalance;
        }
      }
    }

    // Otherwise, we value adherence to the expected group count over
    // discontinuity over balance.
    if (resCount != 0) {
      return resCount;
    }
    if (resDisc != 0) {
      return resDisc;
    }
    if (resBalance != 0) {
      return resBalance;
    }

    // OK, all scores are the same. Then let's see which grouping mode is
    // easier to understand.
    if (this.m_groupingMode != o.m_groupingMode) {
      switch (this.m_groupingMode) {
        case DISTINCT: {
          return (-1);
        }
        case POWERS_OF_10: {
          switch (o.m_groupingMode) {
            case DISTINCT: {
              return 1;
            }
            default: {
              return (-1);
            }
          }
        }
        case POWERS_OF_2: {
          switch (o.m_groupingMode) {
            case DISTINCT:
            case POWERS_OF_10: {
              return 1;
            }
            default: {
              return (-1);
            }
          }
        }
        default: {
          switch (o.m_groupingMode) {
            case DISTINCT: {
              return 1;
            }
          }
        }
      }
    }

    return 0;
  }
}