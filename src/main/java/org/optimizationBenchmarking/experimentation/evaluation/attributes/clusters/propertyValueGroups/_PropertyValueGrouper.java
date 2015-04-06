package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.optimizationBenchmarking.experimentation.data.Attribute;
import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.ParameterValue;
import org.optimizationBenchmarking.experimentation.data.Property;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * An attribute which can group property values.
 * 
 * @param <PT>
 *          the property type
 * @param <DT>
 *          the data element type
 */
abstract class _PropertyValueGrouper<PT extends Property<?>, DT extends DataElement>
    extends Attribute<PT, PropertyValueGroups<DT>> {

  /** the grouping mode to use */
  private final EGroupingMode m_groupingMode;

  /**
   * the goal minimum of the number of groups &ndash; any number of groups
   * less than this would not be good
   */
  private final int m_minGroups;
  /**
   * the goal maximum of the number of groups &ndash; any number of groups
   * higher than this would not be good
   */
  private final int m_maxGroups;

  /**
   * create the property value grouper
   * 
   * @param groupingMode
   *          the grouping mode
   * @param minGroups
   *          the goal minimum of the number of groups &ndash; any number
   *          of groups less than this would not be good *
   * @param maxGroups
   *          the goal maximum of the number of groups &ndash; any number
   *          of groups higher than this would not be good
   */
  _PropertyValueGrouper(final EGroupingMode groupingMode,
      final int minGroups, final int maxGroups) {
    super(EAttributeType.TEMPORARILY_STORED);

    if (groupingMode == null) {
      throw new IllegalArgumentException(//
          "Grouping mode must not be null."); //$NON-NLS-1$
    }
    if (minGroups < 0) {
      throw new IllegalArgumentException(//
          "The minimum number of groups must be greater or equal to 0, but is " //$NON-NLS-1$
              + minGroups);
    }
    if (maxGroups < minGroups) {//
      throw new IllegalArgumentException(//
          "The maximum number of groups must be greater or equal to the minimum number, but is " //$NON-NLS-1$
              + maxGroups + " while the minimum is " + minGroups);//$NON-NLS-1$
    }
    this.m_groupingMode = groupingMode;
    this.m_minGroups = minGroups;
    this.m_maxGroups = maxGroups;
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.hashCode(this.m_groupingMode);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof _PropertyValueGrouper) {
      return EComparison.equals(//
          this.m_groupingMode,//
          ((_PropertyValueGrouper) o).m_groupingMode);
    }
    return false;
  }

  /**
   * Group the property values
   * 
   * @param property
   *          the property to group by
   * @param values
   *          the values to group
   * @param data
   *          the data
   * @return the groups
   */
  final PropertyValueGroups<DT> _group(final PT property,
      final Iterable<?> values, final Iterable<DT> data) {
    final _Groups groups;
    Object[] raw;
    _Group[] buffer;
    boolean canLong, canDouble;
    int size;
    Object unspecified;

    // first, we need to determine the data types for the grouping
    canLong = canDouble = true;
    unspecified = null;
    size = 0;
    for (final Object o : values) {
      if (ParameterValue.isUnspecified(o)) {
        if (unspecified != null) {
          throw new IllegalArgumentException(//
              "There cannot be two unspecified values."); //$NON-NLS-1$
        }
        unspecified = o;
      } else {
        size++;
        if ((o instanceof Byte) || (o instanceof Short)
            || (o instanceof Integer) || (o instanceof Long)) {
          continue;
        }

        if ((o instanceof Float) || (o instanceof Double)) {
          canLong = false;
          continue;
        }

        canDouble = canLong = false;
        continue;
      }
    }

    // We have counted the data items and know their types

    if (canDouble || canLong) {
      raw = new Number[size];
    } else {
      raw = new Object[size];
    }
    buffer = new _Group[size];
    size = 0;
    for (final Object o : values) {
      if (o != unspecified) {
        buffer[size] = new _Group();
        raw[size++] = o;
      }
    }
    try {
      Arrays.sort(raw);
    } catch (final Throwable t) {
      // can be ignored
    }

    if (canLong) {
      groups = this.m_groupingMode._groupLongs(((Number[]) raw),
          this.m_minGroups, this.m_maxGroups, buffer);
    } else {
      if (canDouble) {
        groups = this.m_groupingMode._groupDoubles(((Number[]) raw),
            this.m_minGroups, this.m_maxGroups, buffer);
      } else {
        groups = this.m_groupingMode._groupObjects(raw, this.m_minGroups,
            this.m_maxGroups, buffer);
      }
    }
    buffer = null;
    raw = null;

    // We can now group stuff
    switch (groups.m_mode) {
      case DISTINCT: {
        return this.__makeDistinctValueGroups(property, groups,
            unspecified, data);
      }
      case ANY_POWERS:
      case POWERS_OF_10:
      case POWERS_OF_2: {
        if (canLong) {
          return this.__makeLongRangeGroups(property, groups,
              groups.m_mode, ((Long) (groups.m_parameter)), unspecified,
              data);
        }
        return this.__makeDoubleRangeGroups(property, groups,
            groups.m_mode, ((Long) (groups.m_parameter)), unspecified,
            data);
      }

      default: {
        throw new IllegalArgumentException("Unknown grouping mode " + //$NON-NLS-1$
            groups.m_mode);
      }
    }
  }

  /**
   * Create the distinct value groups
   * 
   * @param property
   *          the property to group by
   * @param groups
   *          the grouping
   * @param unspecified
   *          the unspecified value
   * @param data
   *          the data
   * @return the groups
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private final DistinctValueGroups<DT> __makeDistinctValueGroups(
      final Property<?> property, final _Groups groups,
      final Object unspecified, final Iterable<DT> data) {
    final ArrayList<DistinctValueGroup<DT>> list;
    final ArrayList<DT> elements;
    final UnspecifiedValueGroup uvg;
    Object obj;

    elements = new ArrayList<>();
    list = new ArrayList<>();
    for (final _Group group : groups.m_groups) {
      elements.clear();
      for (final DT element : data) {
        if (EComparison.equals(group.m_lower, property.get(element))) {
          elements.add(element);
        }
      }

      list.add(new DistinctValueGroup(group.m_lower,//
          elements.toArray(new DataElement[elements.size()])));
    }

    // make the unspecified group
    if (unspecified != null) {
      elements.clear();
      for (final DT element : data) {
        obj = property.get(element);
        if (ParameterValue.isUnspecified(obj) || //
            EComparison.equals(obj, unspecified)) {
          elements.add(element);
        }
      }
      if (elements.size() > 0) {
        uvg = new UnspecifiedValueGroup(unspecified,
            elements.toArray(new DataElement[elements.size()]));
      } else {
        uvg = null;
      }
    } else {
      uvg = null;
    }

    return new DistinctValueGroups(property,
        list.toArray(new DistinctValueGroup[list.size()]), uvg);
  }

  /**
   * Create the long range groups
   * 
   * @param property
   *          the property to group by
   * @param groups
   *          the grouping
   * @param mode
   *          the grouping mode
   * @param parameter
   *          the grouping parameter
   * @param unspecified
   *          the unspecified value
   * @param data
   *          the data
   * @return the groups
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private final LongRangeGroups<DT> __makeLongRangeGroups(
      final Property<?> property, final _Groups groups,
      final EGroupingMode mode, final Long parameter,
      final Object unspecified, final Iterable<DT> data) {
    final ArrayList<LongRangeGroup<DT>> list;
    final ArrayList<DT> elements;
    final HashSet<Number> values;
    final UnspecifiedValueGroup uvg;
    Long upper, lower, old;
    Number[] nums;
    Object obj;
    Number num;
    long value;

    elements = new ArrayList<>();
    values = new HashSet<>();
    list = new ArrayList<>();
    old = null;
    for (final _Group group : groups.m_groups) {
      elements.clear();

      upper = ((Long) (group.m_upper));
      lower = ((Long) (group.m_lower));
      if (lower.equals(old)) {
        lower = old;
      }
      old = upper;

      checkElements: for (final DT element : data) {
        obj = property.get(element);
        if (obj instanceof Number) {
          num = ((Number) obj);
          value = num.longValue();
          if (((Number) (group.m_lower)).longValue() <= value) {
            if (group.m_isUpperExclusive) {
              if (((Number) (group.m_upper)).longValue() <= value) {
                continue checkElements;
              }
            } else {
              if (((Number) (group.m_upper)).longValue() < value) {
                continue checkElements;
              }
            }

            elements.add(element);
            values.add(num);
          }
        }
      }

      nums = values.toArray(new Number[values.size()]);
      Arrays.sort(nums);

      list.add(new LongRangeGroup(lower, upper, group.m_isUpperExclusive,
          nums, elements.toArray(new DataElement[elements.size()])));
    }

    // make the unspecified group
    if (unspecified != null) {
      elements.clear();
      for (final DT element : data) {
        obj = property.get(element);
        if (ParameterValue.isUnspecified(obj) || //
            EComparison.equals(obj, unspecified)) {
          elements.add(element);
        }
      }
      if (elements.size() > 0) {
        uvg = new UnspecifiedValueGroup(unspecified,
            elements.toArray(new DataElement[elements.size()]));
      } else {
        uvg = null;
      }
    } else {
      uvg = null;
    }

    return new LongRangeGroups(property, groups.m_mode,
        groups.m_parameter, list.toArray(new LongRangeGroup[list.size()]),
        uvg);
  }

  /**
   * Create the long range groups
   * 
   * @param property
   *          the property to group by
   * @param groups
   *          the grouping
   * @param mode
   *          the grouping mode
   * @param parameter
   *          the grouping parameter
   * @param unspecified
   *          the unspecified value
   * @param data
   *          the data
   * @return the groups
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final DoubleRangeGroups<DT> __makeDoubleRangeGroups(
      final Property<?> property, final _Groups groups,
      final EGroupingMode mode, final Long parameter,
      final Object unspecified, final Iterable<DT> data) {
    final ArrayList<DoubleRangeGroup<DT>> list;
    final ArrayList<DT> elements;
    final HashSet<Number> values;
    final UnspecifiedValueGroup uvg;
    Double upper, lower, old;
    Number[] nums;
    Object obj;
    Number num;
    double value;

    elements = new ArrayList<>();
    values = new HashSet<>();
    list = new ArrayList<>();
    old = null;
    for (final _Group group : groups.m_groups) {
      elements.clear();

      upper = ((Double) (group.m_upper));
      lower = ((Double) (group.m_lower));
      if (lower.equals(old)) {
        lower = old;
      }
      old = upper;

      checkElements: for (final DT element : data) {
        obj = property.get(element);
        if (obj instanceof Number) {
          num = ((Number) obj);
          value = num.doubleValue();
          if (((Number) (group.m_lower)).doubleValue() <= value) {
            if (group.m_isUpperExclusive) {
              if (((Number) (group.m_upper)).doubleValue() <= value) {
                continue checkElements;
              }
            } else {
              if (((Number) (group.m_upper)).doubleValue() < value) {
                continue checkElements;
              }
            }

            elements.add(element);
            values.add(num);
          }
        }
      }

      nums = values.toArray(new Number[values.size()]);
      Arrays.sort(nums);

      list.add(new DoubleRangeGroup(lower, upper,
          group.m_isUpperExclusive, nums, elements
              .toArray(new DataElement[elements.size()])));
    }

    // make the unspecified group
    if (unspecified != null) {
      elements.clear();
      for (final DT element : data) {
        obj = property.get(element);
        if (ParameterValue.isUnspecified(obj) || //
            EComparison.equals(obj, unspecified)) {
          elements.add(element);
        }
      }
      if (elements.size() > 0) {
        uvg = new UnspecifiedValueGroup(unspecified,
            elements.toArray(new DataElement[elements.size()]));
      } else {
        uvg = null;
      }
    } else {
      uvg = null;
    }

    return new DoubleRangeGroups(property, groups.m_mode,
        groups.m_parameter,
        list.toArray(new DoubleRangeGroup[list.size()]), uvg);
  }
}
