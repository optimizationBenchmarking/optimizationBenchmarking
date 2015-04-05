package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import java.util.Arrays;

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
 * @param <DT>
 *          the data element type
 */
public abstract class PropertyValueGrouper<DT extends DataElement> extends
    Attribute<DT, PropertyValueGroups<?>> {

  /** the grouping mode to use */
  private final EGroupingMode m_groupingMode;

  /**
   * create the property value grouper
   * 
   * @param groupingMode
   *          the grouping mode
   */
  PropertyValueGrouper(final EGroupingMode groupingMode) {
    super(EAttributeType.TEMPORARILY_STORED);

    if (groupingMode == null) {
      throw new IllegalArgumentException(//
          "Grouping mode must not be null."); //$NON-NLS-1$
    }
    this.m_groupingMode = groupingMode;
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
    if (o instanceof PropertyValueGrouper) {
      return EComparison.equals(//
          this.m_groupingMode,//
          ((PropertyValueGrouper) o).m_groupingMode);
    }
    return false;
  }

  /**
   * Group the property values
   * 
   * @param property
   *          the properties
   * @param values
   *          the values to group
   * @param data
   *          the data
   * @return the groups
   */
  final PropertyValueGroups<?> _group(final Property<?> property,
      final Iterable<?> values, final Iterable<? extends DataElement> data) {
    boolean canLong, canDouble;
    int size;
    Object unspecified;
    Object[] raw;

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
    size = 0;
    for (final Object o : values) {
      if (o != unspecified) {
        raw[size++] = o;
      }
    }
    try {
      Arrays.sort(raw);
    } catch (final Throwable t) {
      // can be ignored
    }

    // We can now group stuff
    return null;
  }
}
