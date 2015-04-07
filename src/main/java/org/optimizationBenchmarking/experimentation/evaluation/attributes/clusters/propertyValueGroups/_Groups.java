package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

/** a class representing a grouping */
final class _Groups {
  /** the groups */
  final _Group[] m_groups;

  /** the score */
  final double m_score;

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
    double number, balance;
    int count, sum, sumSqr;

    count = sum = sumSqr = 0;
    for (final _Group group : groups) {
      if ((group == null) || (group.m_size <= 0)) {
        break;
      }
      count++;
      sum += group.m_size;
      sumSqr += (group.m_size * group.m_size);
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
      number = (minGroups - count);
      if (maxGroups > minGroups) {
        number /= (maxGroups - minGroups);
      } else {
        number /= minGroups;
      }
    } else {
      if (count > maxGroups) {
        number = (count - maxGroups);
        if (maxGroups > minGroups) {
          number /= (maxGroups - minGroups);
        } else {
          number /= maxGroups;
        }
      } else {
        number = 0d;
      }
    }

    balance = (((double) (sum)) / count);
    balance = (Math.sqrt((((double) sumSqr) / count) - balance) / sum);

    this.m_score = (number + balance);

    this.m_groupingMode = mode;
    this.m_groupingParameter = parameter;
  }
}