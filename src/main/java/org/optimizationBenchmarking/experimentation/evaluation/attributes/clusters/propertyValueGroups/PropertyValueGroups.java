package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.Property;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A set of property value groups.
 * 
 * @param <DT>
 *          the data element type
 */
public class PropertyValueGroups<DT extends DataElement> extends
    _GroupBase implements Comparable<PropertyValueGroups<?>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the property */
  final Property<?> m_property;

  /** the data */
  final ArraySetView<PropertyValueGroup<DT>> m_data;

  /** the grouping mode */
  private final EGroupingMode m_groupingMode;

  /** the grouping info */
  private final Number m_groupingInfo;

  /**
   * a group holding all elements for which the property value was
   * unspecified, or {@code null} if no such elements exist
   */
  private final UnspecifiedValueGroup<DT> m_unspecified;

  /**
   * create the property value groups
   * 
   * @param groups
   *          the groups
   * @param mode
   *          the grouping mode
   * @param info
   *          the grouping info
   * @param property
   *          the property
   * @param unspecified
   *          a group holding all elements for which the property value was
   *          unspecified, or {@code null} if no such elements exist
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  PropertyValueGroups(final Property<?> property,
      final EGroupingMode mode, final Number info,
      final PropertyValueGroup<DT>[] groups,
      final UnspecifiedValueGroup<DT> unspecified) {
    super();

    if (property == null) {
      throw new IllegalArgumentException(//
          "Property must not be null."); //$NON-NLS-1$
    }
    if (groups == null) {
      throw new IllegalArgumentException(((//
          "Property groups must not be null, but are for property '" //$NON-NLS-1$
          + property.getName()) + '\'') + '.');
    }
    if (groups.length < 1) {
      throw new IllegalArgumentException(((//
          "Property groups must contain at least one group, but do not for property '" //$NON-NLS-1$
          + property.getName()) + '\'') + '.');
    }
    if (mode == null) {
      throw new IllegalArgumentException(((//
          "Grouping mode must not be null, but is for property '" //$NON-NLS-1$
          + property.getName()) + '\'') + '.');
    }

    for (final PropertyValueGroup p : groups) {
      p.m_owner = this;
    }

    this.m_data = new ArraySetView<>(groups);
    this.m_property = property;
    this.m_groupingMode = mode;
    this.m_groupingInfo = info;
    this.m_unspecified = unspecified;

    if (unspecified != null) {
      unspecified.m_owner = this;
    }

    this.m_hashCode = HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(mode),//
            HashUtils.hashCode(info)),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(property),//
            HashUtils.hashCode(unspecified)));
  }

  /** {@inheritDoc} */
  @Override
  public final Property<?> getOwner() {
    return this.m_property;
  }

  /**
   * Get the group for which the property value is unspecified. For
   * {@link org.optimizationBenchmarking.experimentation.data.Feature
   * features}, this method will always return {@code null}. For
   * {@link org.optimizationBenchmarking.experimentation.data.Parameter
   * parameters}, this method might either return {@code null} (in case
   * that all experiments have this parameter value specified) or a group
   * with the distinct, unspecified value.
   * 
   * @return the distinct value group holding all elements for which the
   *         property is not specified, or {@code null} if all elements
   *         have the property value specified
   */
  public final UnspecifiedValueGroup<DT> getUnspecifiedGroup() {
    return this.m_unspecified;
  }

  /**
   * Get the grouping mode
   * 
   * @return the grouping mode
   */
  public final EGroupingMode getGroupingMode() {
    return this.m_groupingMode;
  }

  /**
   * Get the property groups
   * 
   * @return the property groups
   */
  public ArraySetView<? extends PropertyValueGroup<DT>> getGroups() {
    return this.m_data;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public boolean equals(final Object o) {
    final PropertyValueGroups x;
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof PropertyValueGroups) {
      x = ((PropertyValueGroups) o);
      return (EComparison.equals(this.m_property, x.m_property) //
          && EComparison.equals(this.m_groupingMode, x.m_groupingMode)//
          && EComparison.equals(this.m_groupingInfo, x.m_groupingInfo)//
          && EComparison.equals(this.m_unspecified, x.m_unspecified)//
      && EComparison.equals(this.m_data, x.m_data));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final PropertyValueGroups<?> o) {
    final int lenA, lenB, minLen;
    int res, index;

    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }

    res = EComparison.compareObjects(this.m_property, o.m_property);
    if (res != 0) {
      return res;
    }

    res = EComparison
        .compareObjects(this.m_groupingMode, o.m_groupingMode);
    if (res != 0) {
      return res;
    }

    res = EComparison
        .compareObjects(this.m_groupingInfo, o.m_groupingInfo);
    if (res != 0) {
      return res;
    }

    lenA = this.m_data.size();
    lenB = o.m_data.size();
    minLen = Math.min(lenA, lenB);
    for (index = 0; index < minLen; index++) {
      res = EComparison.compareObjects(this.m_data.get(index),//
          o.m_data.get(index));
      if (res != 0) {
        return res;
      }
    }

    return 0;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public void toText(final ITextOutput textOut) {
    boolean first;

    first = true;
    for (final PropertyValueGroup group : this.m_data) {
      if (first) {
        first = false;
      } else {
        textOut.append(',');
        textOut.append(' ');
      }
      group.toText(textOut);
    }

    if (this.m_unspecified != null) {
      if (!first) {
        textOut.append(',');
        textOut.append(' ');
      }
      this.m_unspecified.toText(textOut);
    }
  }
}
