package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingAdd;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingSub;
import org.optimizationBenchmarking.utils.math.functions.power.Log;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;

/**
 * A set of different modi for grouping elements.
 */
public enum EGroupingMode {

  /** Group distinct values, i.e., each value becomes an own group */
  DISTINCT {
    /** {@inheritDoc} */
    @Override
    _Groups _groupObjects(final Number param,
        final _PropertyValueInstances<Object>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      int count;

      count = 0;
      for (final _Group group : buffer) {
        group.m_size = 1;
        group.m_isUpperExclusive = false;
        group.m_lower = group.m_upper = data[count++].m_value;
      }

      return new _Groups(buffer, minGroups, maxGroups, this, null);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    _Groups _groupLongs(final Number param,
        final _PropertyValueInstances<Number>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return this._groupObjects(param, ((_PropertyValueInstances[]) data),
          minGroups, maxGroups, buffer);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    _Groups _groupDoubles(final Number param,
        final _PropertyValueInstances<Number>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return this._groupObjects(param, ((_PropertyValueInstances[]) data),
          minGroups, maxGroups, buffer);
    }
  },

  /**
   * Group elements in terms of reasonable powers, including powers of
   * {@code 2}, {@code 10}, etc.
   */
  POWERS {

    /** {@inheritDoc} */
    @Override
    final _Groups _groupLongs(final Number param,
        final _PropertyValueInstances<Number>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      final long pl;
      _Groups best, current;

      if (param != null) {
        pl = param.longValue();
        best = EGroupingMode._groupLongsByPowerRange(pl, data, minGroups,
            maxGroups, buffer);
        if ((param instanceof Float) || (param instanceof Double)) {
          if ((pl < Long.MAX_VALUE) && (pl != param.doubleValue())) {
            current = EGroupingMode._groupLongsByPowerRange((pl + 1L),
                data, minGroups, maxGroups, buffer);
            if ((best == null) || (current.compareTo(best) < 0)) {
              best = current;
            }
          }
        }
        if (best != null) {
          return best;
        }
      } else {
        best = null;
      }

      for (final long power : EGroupingMode.POWER_CHOICES) {
        current = EGroupingMode._groupLongsByPowerRange(power, data,
            minGroups, maxGroups, buffer);
        if (current != null) {
          if ((best == null) || (current.compareTo(best) < 0)) {
            best = current;
          }
        }
      }

      if (best == null) {
        return DISTINCT._groupLongs(param, data, minGroups, maxGroups,
            buffer);
      }
      return best;
    }

    /** {@inheritDoc} */
    @Override
    final _Groups _groupDoubles(final Number param,
        final _PropertyValueInstances<Number>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      final double dl;
      _Groups best, current;

      if (param != null) {
        dl = param.doubleValue();
        best = EGroupingMode._groupDoublesByPowerRange(dl, data,
            minGroups, maxGroups, buffer);
        if (best != null) {
          return best;
        }
      } else {
        best = null;
      }

      for (final long power : EGroupingMode.POWER_CHOICES) {
        current = EGroupingMode._groupDoublesByPowerRange(power, data,
            minGroups, maxGroups, buffer);
        if (current != null) {
          if ((best == null) || (current.compareTo(best) < 0)) {
            best = current;
          }
        }
      }

      if (best == null) {
        return DISTINCT._groupDoubles(param, data, minGroups, maxGroups,
            buffer);
      }
      return best;
    }
  },

  /**
   * Group elements in terms of reasonable multiples, including multipes of
   * {@code 1}, {@code 2}, {@code 10}, etc.
   */
  MULTIPLES {

    /** {@inheritDoc} */
    @Override
    final _Groups _groupLongs(final Number param,
        final _PropertyValueInstances<Number>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      final long pl;
      _Groups best, current;

      if (param != null) {
        pl = param.longValue();
        best = EGroupingMode._groupLongsByMultipleRange(pl, data,
            minGroups, maxGroups, buffer);
        if ((param instanceof Float) || (param instanceof Double)) {
          if ((pl < Long.MAX_VALUE) && (pl != param.doubleValue())) {
            current = EGroupingMode._groupLongsByMultipleRange((pl + 1L),
                data, minGroups, maxGroups, buffer);
            if ((best == null) || (current.compareTo(best) < 0)) {
              best = current;
            }
          }
        }
        if (best != null) {
          return best;
        }
      } else {
        best = null;
      }

      for (final long range : EGroupingMode.MULTIPLE_CHOICES_L) {
        current = EGroupingMode._groupLongsByMultipleRange(range, data,
            minGroups, maxGroups, buffer);
        if (current != null) {
          if ((best == null) || (current.compareTo(best) < 0)) {
            best = current;
          }
        }
      }

      if (best == null) {
        return DISTINCT._groupLongs(param, data, minGroups, maxGroups,
            buffer);
      }
      return best;
    }

    /** {@inheritDoc} */
    @Override
    final _Groups _groupDoubles(final Number param,
        final _PropertyValueInstances<Number>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      final double dl;
      _Groups best, current;

      if (param != null) {
        dl = param.doubleValue();
        best = EGroupingMode._groupDoublesByMultipleRange(dl, data,
            minGroups, maxGroups, buffer);
        if (best != null) {
          return best;
        }
      } else {
        best = null;
      }

      for (final double range : EGroupingMode.MULTIPLE_CHOICES_D) {
        current = EGroupingMode._groupDoublesByMultipleRange(range, data,
            minGroups, maxGroups, buffer);
        if (current != null) {
          if ((best == null) || (current.compareTo(best) < 0)) {
            best = current;
          }
        }
      }

      if (best == null) {
        return DISTINCT._groupDoubles(param, data, minGroups, maxGroups,
            buffer);
      }
      return best;
    }
  },

  /** Group according to any possible choice */
  ANY {

    /** {@inheritDoc} */
    @Override
    final _Groups _groupObjects(final Number param,
        final _PropertyValueInstances<Object>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      return DISTINCT._groupObjects(param, data, minGroups, maxGroups,
          buffer);
    }

    /** {@inheritDoc} */
    @Override
    _Groups _groupLongs(final Number param,
        final _PropertyValueInstances<Number>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      _Groups best, current;

      best = DISTINCT._groupLongs(param, data, minGroups, maxGroups,
          buffer);
      current = POWERS._groupLongs(param, data, minGroups, maxGroups,
          buffer);
      if (current.compareTo(best) < 0) {
        best = current;
      }
      current = MULTIPLES._groupLongs(param, data, minGroups, maxGroups,
          buffer);
      if ((current != null) && (current.compareTo(best) < 0)) {
        best = current;
      }
      return best;
    }

    /** {@inheritDoc} */
    @Override
    _Groups _groupDoubles(final Number param,
        final _PropertyValueInstances<Number>[] data, final int minGroups,
        final int maxGroups, final _Group[] buffer) {
      _Groups best, current;

      best = DISTINCT._groupDoubles(param, data, minGroups, maxGroups,
          buffer);
      current = POWERS._groupDoubles(param, data, minGroups, maxGroups,
          buffer);
      if (current.compareTo(best) < 0) {
        best = current;
      }
      current = MULTIPLES._groupDoubles(param, data, minGroups, maxGroups,
          buffer);
      if ((current != null) && (current.compareTo(best) < 0)) {
        best = current;
      }
      return best;
    }

  }

  ;

  /** the power choices */
  static final long[] POWER_CHOICES = { 10L, 2L, 100L, 1000L, 10000L };

  /** the choices for the {@code long} multiples */
  static final long[] MULTIPLE_CHOICES_L = { 2L, 3L, 4L, 5L, 10L, 15L,
      20L, 25L, 30L, 40L, 50L, 75L, 100L, 200L, 250L, 500L, 750L, 1_000L,
      1_500L, 2_000L, 2_500L, 5_000L, 10_000L, 100_000L, 1_000_000L,
      10_000_000L, 100_000_000L, 1_000_000_000L, 10_000_000_000L,
      100_000_000_000L, 1_000_000_000_000L };

  /** the choices for the {@code double} multiples */
  static final double[] MULTIPLE_CHOICES_D = { 1e-30d, 1e-24d, 1e-21d,
      1e-18d, 1e-15d, 1e-12d, 1e-9d, 1e-6, 1e-5, 1e-4d, 1E-3d, 1E-2d,
      1E-1d, 0.125d, 0.2d, 0.25d, 0.3d, (1d / 3d), 0.4d, 0.5d, 0.75d, 1d,
      1.5d, 2d, 2.5d, 3d, 4d, 5d, 7.5d, 10d, 15d, 20d, 25d, 30d, 40d, 50d,
      75d, 100d, 200d, 250d, 500d, 750d, 1_000d, 1_500d, 2_000d, 2_500d,
      5_000d, 1e4d, 1e5d, 1e6d, 1e7d, 1e8d, 1e9d, 1e10d, 1e12d, 1e15d,
      1e18d, 1e20d, 1e21d, 1e24d, 1e27d, 1e30d, 1e35d, 1e40d, 1e50d,
      1e60d, 1e70d, 1e100d, 1e200d, 1e300d };

  /**
   * Create a grouping
   * 
   * @param param
   *          the parameter
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
  _Groups _groupObjects(final Number param,
      final _PropertyValueInstances<Object>[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer) {
    throw new UnsupportedOperationException("Mode " + this + //$NON-NLS-1$
        " can only apply to numbers."); //$NON-NLS-1$
  }

  /**
   * Create a grouping
   * 
   * @param param
   *          the parameter
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
  abstract _Groups _groupLongs(final Number param,
      final _PropertyValueInstances<Number>[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer);

  /**
   * Create a grouping
   * 
   * @param param
   *          the parameter
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
  abstract _Groups _groupDoubles(final Number param,
      final _PropertyValueInstances<Number>[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer);

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
  static _Groups _groupLongsByPowerRange(final long power,
      final _PropertyValueInstances<Number>[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer) {

    long prev, next, cur;
    int minIndex, exclusiveMaxIndex, groupIndex;
    _Group group;
    boolean exclusive;

    if (power <= 1L) {
      return null;
    }

    next = power;
    do {
      prev = next;
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
        cur = data[exclusiveMaxIndex].m_value.longValue();
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
        group.m_lower = NumericalTypes.valueOf(prev);
        group.m_upper = NumericalTypes.valueOf(next);
        group.m_isUpperExclusive = exclusive;
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

    return new _Groups(buffer, minGroups, maxGroups, POWERS,//
        NumericalTypes.valueOf(power));
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
  static _Groups _groupDoublesByPowerRange(final double power,
      final _PropertyValueInstances<Number>[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer) {
    double prev, next, cur;
    long pwr;
    int minIndex, exclusiveMaxIndex, groupIndex;
    _Group group;
    boolean exclusive;

    if ((power <= 1d) || (power != power)
        || (power >= Double.POSITIVE_INFINITY)) {
      return null;
    }

    pwr = (((long) (Log.INSTANCE.computeAsDouble(power, Double.MAX_VALUE))) + 1L);
    prev = Double.NEGATIVE_INFINITY;
    while ((next = (-Pow.INSTANCE.computeAsDouble(power, pwr))) <= prev) {
      pwr--;
    }

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
        cur = data[exclusiveMaxIndex].m_value.doubleValue();
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
        group.m_lower = NumericalTypes.valueOf(prev);
        group.m_upper = NumericalTypes.valueOf(next);
        group.m_isUpperExclusive = exclusive;
        group.m_size = (exclusiveMaxIndex - minIndex);
        if ((exclusiveMaxIndex >= data.length) || //
            (groupIndex >= buffer.length)) {
          break outer;
        }
      }

      prev = next;
      if (next > 0d) {
        if (next >= Double.POSITIVE_INFINITY) {
          throw new IllegalStateException(//
              "There are double values bigger than POSITIVE_INFINITY??"); //$NON-NLS-1$
        }
        next = Pow.INSTANCE.computeAsDouble(power, (++pwr));
        if (next >= Double.POSITIVE_INFINITY) {
          exclusive = false;
        }
      } else {
        if (prev == 0d) {
          pwr = (((long) (Log.INSTANCE.computeAsDouble(power,
              Double.MIN_VALUE))) - 1L);

          while ((next = Pow.INSTANCE.computeAsDouble(power, pwr)) <= 0d) {
            pwr++;
          }
        } else {
          next = (-(Pow.INSTANCE.computeAsDouble(power, (--pwr))));
          if (next == 0d) {
            next = 0d; // deal with -0d -> 0d
          }
        }
      }
    }
    if (groupIndex < buffer.length) {
      buffer[groupIndex].m_size = (-1);
    }

    return new _Groups(buffer, minGroups, maxGroups, POWERS,//
        NumericalTypes.valueOf(pwr));
  }

  /**
   * Group longs by range
   * 
   * @param range
   *          the range to group by
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
  static _Groups _groupLongsByMultipleRange(final long range,
      final _PropertyValueInstances<Number>[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer) {

    long prev, next, cur;
    int minIndex, exclusiveMaxIndex, groupIndex;
    _Group group;
    boolean exclusive;

    if (range <= 0L) {
      return null;
    }

    cur = data[0].m_value.longValue();
    next = (cur / range);
    prev = (data[data.length - 1].m_value.longValue() / range);

    if (SaturatingAdd.INSTANCE.computeAsLong(//
        ((next > Long.MIN_VALUE) ? Math.abs(next) : Long.MAX_VALUE),//
        ((prev > Long.MIN_VALUE) ? Math.abs(prev) : Long.MAX_VALUE)) >= 1000L) {
      return null; // too many steps!
    }

    if (next >= prev) {
      return null;// range to big
    }

    next *= range;
    prev = next;
    if (prev <= 0L) {
      while (prev > cur) {
        prev = SaturatingSub.INSTANCE.computeAsLong(prev, range);
      }
    }
    next = SaturatingAdd.INSTANCE.computeAsLong(prev, range);

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
        cur = data[exclusiveMaxIndex].m_value.longValue();
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
        group.m_isUpperExclusive = exclusive;
        group.m_size = (exclusiveMaxIndex - minIndex);
        if ((exclusiveMaxIndex >= data.length) || //
            (groupIndex >= buffer.length)) {
          break outer;
        }
      }

      prev = next;
      if (next >= Long.MAX_VALUE) {
        throw new IllegalStateException(//
            "There are long values bigger than MAX_LONG??"); //$NON-NLS-1$
      }
      next = SaturatingAdd.INSTANCE.computeAsLong(next, range);
      if (next >= Long.MAX_VALUE) {
        exclusive = false;
      }
    }

    if (groupIndex < buffer.length) {
      buffer[groupIndex].m_size = (-1);
    }

    return new _Groups(buffer, minGroups, maxGroups, MULTIPLES,
        NumericalTypes.valueOf(range));
  }

  /**
   * Group longs by range
   * 
   * @param range
   *          the range to group by
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
  static _Groups _groupDoublesByMultipleRange(final double range,
      final _PropertyValueInstances<Number>[] data, final int minGroups,
      final int maxGroups, final _Group[] buffer) {
    double prev, next, cur;
    long prevMul;
    int minIndex, exclusiveMaxIndex, groupIndex;
    _Group group;

    if ((range <= 0d) || (range != range)
        || (range >= Double.POSITIVE_INFINITY)) {
      return null;
    }

    cur = data[0].m_value.doubleValue();
    prev = Div.INSTANCE.computeAsDouble(cur, range);
    next = Div.INSTANCE.computeAsDouble(
        data[data.length - 1].m_value.doubleValue(), range);

    if ((Math.abs(next) + Math.abs(prev)) >= 1000d) {
      return null; // too many steps!
    }

    if (Math.ceil(prev) >= Math.floor(next)) {
      return null;// range too big
    }

    prevMul = ((long) (prev));
    if (prevMul != prev) {
      return null; // cannot use the range due to imprecision
    }

    next = prev = Mul.INSTANCE.computeAsDouble(prevMul, range);
    if ((prev + range) <= prev) {
      return null;
    }

    if (prev <= 0d) {
      while (prev > cur) {
        if (prevMul <= Long.MIN_VALUE) {
          return null;
        }
        prevMul--;
        prev = Mul.INSTANCE.computeAsDouble(prevMul, range);
        if ((prev + range) <= prev) {
          return null;
        }
      }
    }

    if (prevMul >= Long.MAX_VALUE) {
      return null;
    }
    next = Mul.INSTANCE.computeAsDouble((++prevMul), range);
    if (next <= prev) {
      return null;
    }

    exclusiveMaxIndex = 0;
    groupIndex = 0;

    // First, we want to find all the negative powers, then the positive
    // ones.
    outer: for (;;) {
      if (exclusiveMaxIndex >= data.length) {
        break outer;
      }
      minIndex = exclusiveMaxIndex;

      inner: for (;;) {
        cur = data[exclusiveMaxIndex].m_value.longValue();
        if ((cur < prev) || (cur >= next)) {
          break inner;
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
      if (prevMul >= Long.MAX_VALUE) {
        return null;
      }
      prevMul++;
      next = Mul.INSTANCE.computeAsDouble(prevMul, range);
      if (next <= prev) {
        return null;
      }
    }

    if (groupIndex < buffer.length) {
      buffer[groupIndex].m_size = (-1);
    }

    return new _Groups(buffer, minGroups, maxGroups, MULTIPLES,
        NumericalTypes.valueOf(range));
  }
}