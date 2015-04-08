package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import java.util.ArrayList;
import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.Attribute;
import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.Feature;
import org.optimizationBenchmarking.experimentation.data.Instance;
import org.optimizationBenchmarking.experimentation.data.Parameter;
import org.optimizationBenchmarking.experimentation.data.Property;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.parsers.AnyNumberParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * An attribute which can group property values.
 * 
 * @param <PT>
 *          the property type
 * @param <DT>
 *          the data element type
 */
public final class PropertyValueGrouper<PT extends Property<?>, DT extends DataElement>
    extends Attribute<PT, PropertyValueGroups<DT>> {

  /** the default minimum number of anticipated groups */
  private static final int DEFAULT_MIN_GROUPS = 2;
  /** the default maximum number of anticipated groups */
  private static final int DEFAULT_MAX_GROUPS = 10;
  /** the default grouping mode */
  private static final EGroupingMode DEFAULT_GROUPING_MODE = EGroupingMode.ANY;

  /** The suffix of the grouping parameter: {@value} */
  public static final String PARAM_GROUPING_SUFFIX = "Grouping"; //$NON-NLS-1$
  /** The parameter suffix defining the minimum number of groups: {@value} */
  public static final String PARAM_GROUPING_MIN_GROUPS_SUFFIX = //
  (PropertyValueGrouper.PARAM_GROUPING_SUFFIX + "MinGroups");//$NON-NLS-1$
  /** The parameter suffix defining the maximum number of groups: {@value} */
  public static final String PARAM_GROUPING_MAX_GROUPS_SUFFIX = //
  (PropertyValueGrouper.PARAM_GROUPING_SUFFIX + "MaxGroups");//$NON-NLS-1$
  /** The parameter suffix defining the grouping mode: {@value} */
  public static final String PARAM_GROUPING_MODE_SUFFIX = //
  (PropertyValueGrouper.PARAM_GROUPING_SUFFIX + "Mode");//$NON-NLS-1$
  /** The parameter suffix defining the grouping parameter: {@value} */
  public static final String PARAM_GROUPING_PARAMETER_SUFFIX = //
  (PropertyValueGrouper.PARAM_GROUPING_SUFFIX + "Param");//$NON-NLS-1$

  /** The parameter for all grouping min group numbers */
  public static final String PARAM_ALL_GROUPING_MIN_GROUPS = //
  (Character
      .toLowerCase(PropertyValueGrouper.PARAM_GROUPING_MIN_GROUPS_SUFFIX
          .charAt(0))//
  + PropertyValueGrouper.PARAM_GROUPING_MIN_GROUPS_SUFFIX.substring(1));
  /** The parameter for all grouping max group numbers */
  public static final String PARAM_ALL_GROUPING_MAX_GROUPS = //
  (Character
      .toLowerCase(PropertyValueGrouper.PARAM_GROUPING_MAX_GROUPS_SUFFIX
          .charAt(0))//
  + PropertyValueGrouper.PARAM_GROUPING_MAX_GROUPS_SUFFIX.substring(1));
  /** The parameter for all grouping modes */
  public static final String PARAM_ALL_GROUPING_MODE = //
  (Character.toLowerCase(PropertyValueGrouper.PARAM_GROUPING_MODE_SUFFIX
      .charAt(0))//
  + PropertyValueGrouper.PARAM_GROUPING_MODE_SUFFIX.substring(1));
  /** The parameter for all grouping parameters */
  public static final String PARAM_ALL_GROUPING_PARAMETER = //
  (Character
      .toLowerCase(PropertyValueGrouper.PARAM_GROUPING_PARAMETER_SUFFIX
          .charAt(0))//
  + PropertyValueGrouper.PARAM_GROUPING_PARAMETER_SUFFIX.substring(1));

  /** The default value grouper for experiment parameters */
  @SuppressWarnings("rawtypes")
  private static final PropertyValueGrouper DEFAULT_GROUPER//
  = new PropertyValueGrouper(PropertyValueGrouper.DEFAULT_GROUPING_MODE,
      null, PropertyValueGrouper.DEFAULT_MIN_GROUPS,
      PropertyValueGrouper.DEFAULT_MAX_GROUPS);

  /** The default value grouper for experiment parameters */
  public static final PropertyValueGrouper<Parameter, Experiment>//
  DEFAULT_PARAMETER_GROUPER = PropertyValueGrouper.DEFAULT_GROUPER;

  /** The default value grouper for instance features */
  public static final PropertyValueGrouper<Feature, Instance>//
  DEFAULT_FEATURE_GROUPER = PropertyValueGrouper.DEFAULT_GROUPER;

  /** the grouping mode to use */
  private final EGroupingMode m_groupingMode;

  /** the parameter to be passed to the grouping */
  private final Number m_groupingParameter;

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
   * @param groupingParameter
   *          the parameter to be passed to the grouping
   * @param minGroups
   *          the goal minimum of the number of groups &ndash; any number
   *          of groups less than this would not be good
   * @param maxGroups
   *          the goal maximum of the number of groups &ndash; any number
   *          of groups higher than this would not be good
   */
  public PropertyValueGrouper(final EGroupingMode groupingMode,
      final Number groupingParameter, final int minGroups,
      final int maxGroups) {
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
    this.m_groupingParameter = groupingParameter;
    this.m_minGroups = minGroups;
    this.m_maxGroups = maxGroups;
  }

  /**
   * Load a property value grouper from a configuration
   * 
   * @param property
   *          the property
   * @param config
   *          the configuration
   * @return the grouper
   * @param <DX>
   *          the element type
   * @param <PX>
   *          the property type
   */
  public static final <DX extends DataElement, PX extends Property<?>> PropertyValueGrouper<PX, DX> configure(
      final PX property, final Configuration config) {
    return PropertyValueGrouper.configure(
        ((property != null) ? property.getName() : null), config);
  }

  /**
   * Load a property value grouper from a configuration
   * 
   * @param propertyName
   *          the property name
   * @param config
   *          the configuration
   * @return the grouper
   * @param <DX>
   *          the element type
   * @param <PX>
   *          the property type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final <DX extends DataElement, PX extends Property<?>> PropertyValueGrouper<PX, DX> configure(
      final String propertyName, final Configuration config) {
    final EGroupingMode mode, allMode;
    final int minGroups, maxGroups, allMinGroups, allMaxGroups;
    final Number param, allParam;
    final boolean isSpecial;
    String prefix;

    if (config == null) {
      return PropertyValueGrouper.DEFAULT_GROUPER;
    }

    prefix = TextUtils.prepare(propertyName);
    isSpecial = (prefix != null);

    allMode = config.getInstance(
        PropertyValueGrouper.PARAM_ALL_GROUPING_MODE,//
        EGroupingMode.class, PropertyValueGrouper.DEFAULT_GROUPING_MODE);
    if (isSpecial) {
      mode = config.getInstance((prefix + //
          PropertyValueGrouper.PARAM_GROUPING_MODE_SUFFIX),//
          EGroupingMode.class,//
          allMode);
    } else {
      mode = allMode;
    }

    allMinGroups = config.getInt(
        PropertyValueGrouper.PARAM_ALL_GROUPING_MIN_GROUPS, 0, 10000,//
        PropertyValueGrouper.DEFAULT_MIN_GROUPS);
    if (isSpecial) {
      minGroups = config.getInt(
          (prefix + //
          PropertyValueGrouper.PARAM_GROUPING_MIN_GROUPS_SUFFIX), 0,
          (Math.max(allMinGroups, 10000) + 1), allMinGroups);
    } else {
      minGroups = allMinGroups;
    }

    allMaxGroups = config.getInt(
        PropertyValueGrouper.PARAM_ALL_GROUPING_MAX_GROUPS, allMinGroups,
        (Math.max(allMinGroups, 10000) + 1),//
        PropertyValueGrouper.DEFAULT_MAX_GROUPS);
    if (isSpecial) {
      maxGroups = config.getInt(
          (prefix + //
          PropertyValueGrouper.PARAM_GROUPING_MAX_GROUPS_SUFFIX),
          Math.max(allMinGroups, minGroups),
          (Math.max(Math.max(minGroups, allMaxGroups), 10000) + 1),
          allMaxGroups);
    } else {
      maxGroups = allMaxGroups;
    }

    allParam = config.get(
        PropertyValueGrouper.PARAM_ALL_GROUPING_PARAMETER,
        AnyNumberParser.INSTANCE, null);
    if (isSpecial) {
      param = config.get(
          (prefix + PropertyValueGrouper.PARAM_GROUPING_PARAMETER_SUFFIX),
          AnyNumberParser.INSTANCE, allParam);
    } else {
      param = allParam;
    }

    if ((mode == PropertyValueGrouper.DEFAULT_GROUPER.m_groupingMode)
        && (minGroups == PropertyValueGrouper.DEFAULT_GROUPER.m_minGroups)
        && (maxGroups == PropertyValueGrouper.DEFAULT_GROUPER.m_maxGroups)
        && (param == PropertyValueGrouper.DEFAULT_GROUPER.m_groupingParameter)) {
      return PropertyValueGrouper.DEFAULT_GROUPER;
    }
    return new PropertyValueGrouper(mode, param, minGroups, maxGroups);
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_groupingMode),//
            HashUtils.hashCode(this.m_groupingParameter)),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_minGroups),//
            HashUtils.hashCode(this.m_maxGroups)));
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected final PropertyValueGroups<DT> compute(final PT data) {
    final _PropertyValueSet set;
    final _Groups groups;
    _Group[] buffer;
    int index;

    // obtain the values
    if (data instanceof Feature) {
      set = new _PropertyValueSet(data, ((Feature) data).getOwner()
          .getOwner().getInstances().getData());
    } else {
      if (data instanceof Parameter) {
        set = new _PropertyValueSet(data, ((Parameter) data).getOwner()
            .getOwner().getData());
      } else {
        throw new IllegalArgumentException(//
            "Property must either be a Feature or a Parameter, but is " //$NON-NLS-1$
                + data);
      }
    }

    // obtain the groups
    buffer = new _Group[set.m_values.length];
    for (index = buffer.length; (--index) >= 0;) {
      buffer[index] = new _Group();
    }

    if (set.m_areValuesIntegers) {
      groups = this.m_groupingMode._groupLongs(this.m_groupingParameter,
          set.m_values, this.m_minGroups, this.m_maxGroups, buffer);
    } else {
      if (set.m_areValuesDoubles) {
        groups = this.m_groupingMode._groupDoubles(
            this.m_groupingParameter, set.m_values, this.m_minGroups,
            this.m_maxGroups, buffer);
      } else {
        groups = this.m_groupingMode._groupObjects(
            this.m_groupingParameter, set.m_values, this.m_minGroups,
            this.m_maxGroups, buffer);
      }
    }

    buffer = null;

    switch (groups.m_groupingMode) {
      case DISTINCT: {
        return PropertyValueGrouper.__distinct(groups, set);
      }

      case POWERS:
      case MULTIPLES: {
        if (set.m_areValuesIntegers) {
          return PropertyValueGrouper.__longRange(groups, set);
        }
        if (set.m_areValuesDoubles) {
          return PropertyValueGrouper.__doubleRange(groups, set);
        }
        throw new IllegalStateException("Mode " //$NON-NLS-1$
            + groups.m_groups + //
            " must result in long or double range groups.");//$NON-NLS-1$
      }

      default: {
        throw new IllegalArgumentException(//
            "Unknown grouping mode: " + groups.m_groupingMode); //$NON-NLS-1$
      }
    }
  }

  /**
   * Create the distinct value groups
   * 
   * @param groups
   *          the groups
   * @param values
   *          the values
   * @return the groups
   * @param <DX>
   *          the element type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static final <DX extends DataElement> DistinctValueGroups<DX> __distinct(
      final _Groups groups, final _PropertyValueSet values) {
    final DistinctValueGroup<DX>[] list;
    final UnspecifiedValueGroup<DX> unspec;
    DataElement[] elements;
    int i;

    list = new DistinctValueGroup[groups.m_groups.length];
    i = 0;
    outer: for (final _Group group : groups.m_groups) {
      for (final _PropertyValueInstances inst : values.m_values) {
        if (EComparison.equals(inst.m_value, group.m_lower)) {
          elements = new DataElement[inst.m_size];
          System.arraycopy(inst.m_elements, 0, elements, 0, inst.m_size);
          list[i++] = new DistinctValueGroup(inst.m_value, elements);
          continue outer;
        }
      }

      throw new IllegalArgumentException(//
          "No corresponding elements for value " //$NON-NLS-1$
              + group.m_lower);
    }

    if (values.m_unspecified != null) {
      elements = new DataElement[values.m_unspecified.m_size];
      System.arraycopy(values.m_unspecified.m_elements, 0, elements, 0,
          values.m_unspecified.m_size);
      unspec = new UnspecifiedValueGroup(values.m_unspecified.m_value,
          elements);
    } else {
      unspec = null;
    }

    return new DistinctValueGroups(values.m_property, list, unspec);
  }

  /**
   * Create the long range groups
   * 
   * @param groups
   *          the groups
   * @param values
   *          the values
   * @return the groups
   * @param <DX>
   *          the element type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static final <DX extends DataElement> LongRangeGroups<DX> __longRange(
      final _Groups groups, final _PropertyValueSet values) {
    final LongRangeGroup<DX>[] list;
    final UnspecifiedValueGroup<DX> unspec;
    final ArrayList<DataElement> members;
    final ArrayList<Number> memberValues;
    DataElement[] elements;
    Number[] numbers;
    Number num;
    Long lower, upper, oldUpper;
    int i, size;

    list = new LongRangeGroup[groups.m_groups.length];
    members = new ArrayList<>();
    memberValues = new ArrayList<>();
    i = 0;
    oldUpper = null;
    for (final _Group group : groups.m_groups) {
      members.clear();
      memberValues.clear();

      lower = ((Long) (group.m_lower));
      if (lower.equals(oldUpper)) {
        lower = oldUpper;
      }
      oldUpper = upper = ((Long) (group.m_upper));

      inner: for (final _PropertyValueInstances inst : values.m_values) {
        num = ((Number) (inst.m_value));
        if (num.longValue() >= lower.longValue()) {

          if (group.m_isUpperExclusive) {
            if (num.longValue() >= upper.longValue()) {
              continue inner;
            }
          } else {
            if (num.longValue() > upper.longValue()) {
              continue inner;
            }
          }

          for (size = inst.m_size; (--size) >= 0;) {
            members.add(inst.m_elements[size]);
          }
          memberValues.add((Number) (inst.m_value));
        }
      }

      if ((size = members.size()) <= 0) {
        throw new IllegalStateException((((//
            "No corresponding elements for long range group [" //$NON-NLS-1$
            + group.m_lower) + ',') + group.m_upper)
            + (group.m_isUpperExclusive ? ')' : ']'));
      }
      elements = members.toArray(new DataElement[size]);
      Arrays.sort(elements);

      if ((size = memberValues.size()) <= 0) {
        throw new IllegalStateException((((//
            "No corresponding member values for long range group [" //$NON-NLS-1$
            + group.m_lower) + ',') + group.m_upper)
            + (group.m_isUpperExclusive ? ')' : ']'));
      }
      numbers = memberValues.toArray(new Number[size]);
      Arrays.sort(numbers);

      list[i++] = new LongRangeGroup(lower, upper,
          group.m_isUpperExclusive, numbers, elements);
    }

    if (values.m_unspecified != null) {
      elements = new DataElement[values.m_unspecified.m_size];
      System.arraycopy(values.m_unspecified.m_elements, 0, elements, 0,
          values.m_unspecified.m_size);
      unspec = new UnspecifiedValueGroup(values.m_unspecified.m_value,
          elements);
    } else {
      unspec = null;
    }

    return new LongRangeGroups(values.m_property, groups.m_groupingMode,
        groups.m_groupingParameter, list, unspec);
  }

  /**
   * Create the double range groups
   * 
   * @param groups
   *          the groups
   * @param values
   *          the values
   * @return the groups
   * @param <DX>
   *          the element type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static final <DX extends DataElement> DoubleRangeGroups<DX> __doubleRange(
      final _Groups groups, final _PropertyValueSet values) {
    final DoubleRangeGroup<DX>[] list;
    final UnspecifiedValueGroup<DX> unspec;
    final ArrayList<DataElement> members;
    final ArrayList<Number> memberValues;
    DataElement[] elements;
    Number[] numbers;
    Number num;
    Double lower, upper, oldUpper;
    int i, size;

    list = new DoubleRangeGroup[groups.m_groups.length];
    members = new ArrayList<>();
    memberValues = new ArrayList<>();
    i = 0;
    oldUpper = null;
    for (final _Group group : groups.m_groups) {
      members.clear();
      memberValues.clear();

      lower = ((Double) (group.m_lower));
      if (lower.equals(oldUpper)) {
        lower = oldUpper;
      }
      oldUpper = upper = ((Double) (group.m_upper));

      inner: for (final _PropertyValueInstances inst : values.m_values) {
        num = ((Number) (inst.m_value));
        if (num.doubleValue() >= lower.doubleValue()) {

          if (group.m_isUpperExclusive) {
            if (num.doubleValue() >= upper.doubleValue()) {
              continue inner;
            }
          } else {
            if (num.doubleValue() > upper.doubleValue()) {
              continue inner;
            }
          }

          for (size = inst.m_size; (--size) >= 0;) {
            members.add(inst.m_elements[size]);
          }
          memberValues.add((Number) (inst.m_value));
        }
      }

      if ((size = members.size()) <= 0) {
        throw new IllegalStateException((((//
            "No corresponding elements for double range group [" //$NON-NLS-1$
            + group.m_lower) + ',') + group.m_upper)
            + (group.m_isUpperExclusive ? ')' : ']'));
      }
      elements = members.toArray(new DataElement[size]);
      Arrays.sort(elements);

      if ((size = memberValues.size()) <= 0) {
        throw new IllegalStateException((((//
            "No corresponding member values for double range group [" //$NON-NLS-1$
            + group.m_lower) + ',') + group.m_upper)
            + (group.m_isUpperExclusive ? ')' : ']'));
      }
      numbers = memberValues.toArray(new Number[size]);
      Arrays.sort(numbers);

      list[i++] = new DoubleRangeGroup(lower, upper,
          group.m_isUpperExclusive, numbers, elements);
    }

    if (values.m_unspecified != null) {
      elements = new DataElement[values.m_unspecified.m_size];
      System.arraycopy(values.m_unspecified.m_elements, 0, elements, 0,
          values.m_unspecified.m_size);
      unspec = new UnspecifiedValueGroup(values.m_unspecified.m_value,
          elements);
    } else {
      unspec = null;
    }

    return new DoubleRangeGroups(values.m_property, groups.m_groupingMode,
        groups.m_groupingParameter, list, unspec);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    PropertyValueGrouper g;

    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o.getClass() == this.getClass()) {
      g = ((PropertyValueGrouper) o);
      return ((this.m_minGroups == g.m_minGroups) && //
          (this.m_maxGroups == g.m_maxGroups) && //
      EComparison.equals(this.m_groupingMode, g.m_groupingMode));
    }
    return false;
  }
}
