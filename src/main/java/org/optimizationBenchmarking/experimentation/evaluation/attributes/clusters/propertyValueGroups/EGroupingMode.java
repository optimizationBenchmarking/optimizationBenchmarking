package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

/**
 * A set of different modi for grouping elements.
 */
public enum EGroupingMode {

  /** Group distinct values, i.e., each value becomes an own group */
  DISTINCT {
    /** {@inheritDoc} */
    @Override
    _Groups _groupObjects(final Object[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      int count;

      count = 0;
      for (final _Group group : buffer) {
        group.m_size = 1;
        group.m_isUpperExclusive = false;
        group.m_lower = group.m_upper = data[count++];
      }

      return new _Groups(buffer, minGroups, maxGroups, this, null);
    }

    /** {@inheritDoc} */
    @Override
    _Groups _groupLongs(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return this._groupObjects(data, minGroups, maxGroups, buffer);
    }

    /** {@inheritDoc} */
    @Override
    _Groups _groupDoubles(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return this._groupObjects(data, minGroups, maxGroups, buffer);
    }
  },

  /** Group elements in terms of reasonable powers of {@code 2} */
  POWERS_OF_2 {

    /** {@inheritDoc} */
    @Override
    final _Groups _groupLongs(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return EGroupingMode._groupLongsByPower(2L, data, minGroups,
          maxGroups, buffer);
    }

    /** {@inheritDoc} */
    @Override
    final _Groups _groupDoubles(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return EGroupingMode._groupDoublesByPower(2L, data, minGroups,
          maxGroups, buffer);
    }
  },

  /** Group elements in terms of reasonable powers of {@code 10} */
  POWERS_OF_10 {

    /** {@inheritDoc} */
    @Override
    final _Groups _groupLongs(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return EGroupingMode._groupLongsByPower(10L, data, minGroups,
          maxGroups, buffer);
    }

    /** {@inheritDoc} */
    @Override
    final _Groups _groupDoubles(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return EGroupingMode._groupDoublesByPower(10L, data, minGroups,
          maxGroups, buffer);
    }
  },

  /**
   * Group elements in terms of reasonable powers, including powers of
   * {@code 2}, {@code 10}, etc.
   */
  ANY_POWERS {

    /** {@inheritDoc} */
    @Override
    final _Groups _groupLongs(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      _Groups best, current;

      best = null;
      for (final long power : EGroupingMode.POWER_CHOICES) {
        current = EGroupingMode._groupLongsByPower(power, data, minGroups,
            maxGroups, buffer);
        if ((best == null) || (best.m_score > current.m_score)) {
          best = current;
        }
      }
      return best;
    }

    /** {@inheritDoc} */
    @Override
    final _Groups _groupDoubles(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      _Groups best, current;

      best = null;
      for (final long power : EGroupingMode.POWER_CHOICES) {
        current = EGroupingMode._groupDoublesByPower(power, data,
            minGroups, maxGroups, buffer);
        if ((best == null) || (best.m_score > current.m_score)) {
          best = current;
        }
      }
      return best;
    }
  },

  /** Group according to any possible choice */
  ANY {

    /** {@inheritDoc} */
    @Override
    final _Groups _groupObjects(final Object[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return DISTINCT._groupObjects(data, minGroups, maxGroups, buffer);
    }

    /** {@inheritDoc} */
    @Override
    _Groups _groupLongs(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      _Groups best, current;

      best = DISTINCT._groupLongs(data, minGroups, maxGroups, buffer);
      current = ANY_POWERS._groupLongs(data, minGroups, maxGroups, buffer);
      if (current.m_score < best.m_score) {
        best = current;
      }
      return best;
    }

    /** {@inheritDoc} */
    @Override
    _Groups _groupDoubles(final Number[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      _Groups best, current;

      best = DISTINCT._groupDoubles(data, minGroups, maxGroups, buffer);
      current = ANY_POWERS._groupDoubles(data, minGroups, maxGroups,
          buffer);
      if (current.m_score < best.m_score) {
        best = current;
      }
      return best;
    }

  }

  ;

  /** the power choices */
  static final long[] POWER_CHOICES = { 10L, 2L, 100L, 1000L, 10000L };

  /**
   * @param power
   *          the power to group by
   * @param data
   *          the data
   * @param minGroups
   *          the anticipated minimum number of groups
   * @param maxGroups
   *          the anticipated maximum number of groups
   * @param buffer
   *          a multi-purpose buffer
   * @return the objects to group
   */
  static _Groups _groupLongsByPower(final long power, final Number[] data,
      final int minGroups, final int maxGroups, final _Group[] buffer) {

    long prev, next, cur;
    int minIndex, exclusiveMaxIndex, groupIndex;
    _Group group;
    boolean exclusive;

    prev = power;
    do {
      next = prev;
      next *= power;
    } while (next > prev);

    next = (-prev);
    prev = Long.MIN_VALUE;

    exclusiveMaxIndex = 0;
    groupIndex = 0;
    exclusive = true;

    // First, we want to find all the negative powers, then the positive
    // ones.
    outer: for (;;) {
      if (exclusiveMaxIndex >= data.length) {
        break outer;
      }
      minIndex = exclusiveMaxIndex;

      inner: for (;;) {
        cur = data[exclusiveMaxIndex].longValue();
        if (cur < prev) {
          break inner;
        }
        if (exclusive) {
          if (cur >= next) {
            break inner;
          }
        } else {
          if (cur > next) {
            break inner;
          }
        }
        exclusiveMaxIndex++;
        if (exclusiveMaxIndex >= data.length) {
          break inner;
        }
      }

      if (exclusiveMaxIndex > minIndex) {
        group = buffer[groupIndex++];
        group.m_lower = Long.valueOf(prev);
        group.m_upper = Long.valueOf(next);
        group.m_isUpperExclusive = true;
        group.m_size = (exclusiveMaxIndex - minIndex);
        if ((exclusiveMaxIndex >= data.length) || //
            (groupIndex >= buffer.length)) {
          break outer;
        }
      }

      prev = next;
      if (prev > 0L) {
        if (next >= Long.MAX_VALUE) {
          throw new IllegalStateException(//
              "There are long values bigger than MAX_LONG??"); //$NON-NLS-1$
        }
        next *= power;
        if (next <= prev) {
          next = Long.MAX_VALUE;
          exclusive = false;
        }
      } else {
        if (prev == 0L) {
          next = power;
        } else {
          next /= power;
        }
      }
    }

    if (groupIndex < buffer.length) {
      buffer[groupIndex].m_size = (-1);
    }

    return new _Groups(buffer, minGroups, maxGroups,
        ((power == 2L) ? POWERS_OF_2 : //
            ((power == 10L) ? POWERS_OF_10 : ANY_POWERS)),//
        Long.valueOf(power));
  }

  /**
   * @param power
   *          the power to group by
   * @param data
   *          the data
   * @param minGroups
   *          the anticipated minimum number of groups
   * @param maxGroups
   *          the anticipated maximum number of groups
   * @param buffer
   *          a multi-purpose buffer
   * @return the objects to group
   */
  static _Groups _groupDoublesByPower(final long power,
      final Number[] data, final int minGroups, final int maxGroups,
      final _Group[] buffer) {

    double prev, next, cur;
    long pwr;
    int minIndex, exclusiveMaxIndex, groupIndex;
    _Group group;
    boolean exclusive;

    pwr = ((long) (Math.log(Double.MAX_VALUE) / Math.log(power)));

    next = (-Math.pow(power, pwr));
    prev = Double.NEGATIVE_INFINITY;

    exclusiveMaxIndex = 0;
    groupIndex = 0;
    exclusive = true;

    // First, we want to find all the negative powers, then the positive
    // ones.
    outer: for (;;) {
      if (exclusiveMaxIndex >= data.length) {
        break outer;
      }
      minIndex = exclusiveMaxIndex;

      inner: for (;;) {
        cur = data[exclusiveMaxIndex].doubleValue();
        if (cur < prev) {
          break inner;
        }
        if (exclusive) {
          if (cur >= next) {
            break inner;
          }
        } else {
          if (cur > next) {
            break inner;
          }
        }
        exclusiveMaxIndex++;
        if (exclusiveMaxIndex >= data.length) {
          break inner;
        }
      }

      if (exclusiveMaxIndex > minIndex) {
        group = buffer[groupIndex++];
        group.m_lower = Double.valueOf(prev);
        group.m_upper = Double.valueOf(next);
        group.m_isUpperExclusive = true;
        group.m_size = (exclusiveMaxIndex - minIndex);
        if ((exclusiveMaxIndex >= data.length) || //
            (groupIndex >= buffer.length)) {
          break outer;
        }
      }

      prev = next;
      if (prev > 0d) {
        if (next >= Double.POSITIVE_INFINITY) {
          throw new IllegalStateException(//
              "There are double values bigger than POSITIVE_INFINITY??"); //$NON-NLS-1$
        }
        next = Math.pow(power, (--pwr));
        if (next >= Double.POSITIVE_INFINITY) {
          exclusive = false;
        }
      } else {
        if (prev == 0L) {
          pwr = ((long) (Math.log(Double.MIN_VALUE) / Math.log(power)));
          if (Math.pow(power, pwr) <= 0d) {
            pwr++;
          }
        }
        next = Math.pow(power, pwr++);
      }
    }
    if (groupIndex < buffer.length) {
      buffer[groupIndex].m_size = (-1);
    }

    return new _Groups(buffer, minGroups, maxGroups,
        ((power == 2L) ? POWERS_OF_2 : //
            ((power == 10L) ? POWERS_OF_10 : ANY_POWERS)),//
        Long.valueOf(power));
  }

  /**
   * Create a grouping
   * 
   * @param data
   *          the data
   * @param minGroups
   *          the anticipated minimum number of groups
   * @param maxGroups
   *          the anticipated maximum number of groups
   * @param buffer
   *          a multi-purpose buffer
   * @return the objects to group
   */
  _Groups _groupObjects(final Object[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer) {
    throw new UnsupportedOperationException("Mode " + this + //$NON-NLS-1$
        " can only apply to numbers."); //$NON-NLS-1$
  }

  /**
   * Create a grouping
   * 
   * @param data
   *          the data
   * @param minGroups
   *          the anticipated minimum number of groups
   * @param maxGroups
   *          the anticipated maximum number of groups
   * @param buffer
   *          a multi-purpose buffer
   * @return the objects to group
   */
  abstract _Groups _groupLongs(final Number[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer);

  /**
   * Create a grouping
   * 
   * @param data
   *          the data
   * @param minGroups
   *          the anticipated minimum number of groups
   * @param maxGroups
   *          the anticipated maximum number of groups
   * @param buffer
   *          a multi-purpose buffer
   * @return the objects to group
   */
  abstract _Groups _groupDoubles(final Number[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer);

}
