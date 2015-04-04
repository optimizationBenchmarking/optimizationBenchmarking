package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.DataSet;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A group of values. This class is most likely used to group property
 * values.
 * 
 * @param <VT>
 *          the value type
 * @param <DT>
 *          the data set type
 */
public class PropertyValueGroup<VT, DT extends DataSet<?>> extends
    HashObject implements Comparable<PropertyValueGroup<?, ?>> {

  /** the values */
  private final ArraySetView<VT> m_values;

  /** the data elements */
  private final ArraySetView<DT> m_data;

  /**
   * create the property value group
   * 
   * @param values
   *          the values
   * @param data
   *          the data
   */
  PropertyValueGroup(final VT[] values, final DT[] data) {
    super();

    if (values == null) {
      throw new IllegalArgumentException(
          "Values of proprety value group must not be null."); //$NON-NLS-1$
    }
    if (values.length < 1) {
      throw new IllegalArgumentException(//
          "There must be at least one value in a property value group."); //$NON-NLS-1$
    }

    if (data == null) {
      throw new IllegalArgumentException(//
          "Data elements belonging to property value group must not be null, but for value set" //$NON-NLS-1$
              + Arrays.toString(values) + " they are.");//$NON-NLS-1$
    }
    if (data.length < 1) {
      throw new IllegalArgumentException(//
          "There must be at least one data elements belonging to property value group, but for value set" //$NON-NLS-1$
              + Arrays.toString(values) + " there isn't.");//$NON-NLS-1$
    }

    this.m_values = new ArraySetView<>(values);
    this.m_data = new ArraySetView<>(data);
  }

  /**
   * Get the values
   * 
   * @return the values
   */
  public final ArraySetView<VT> getValues() {
    return this.m_values;
  }

  /**
   * Get the number of values in this group
   * 
   * @return the number of values in this group
   */
  public final int getValueCount() {
    return this.m_values.size();
  }

  /**
   * Obtain the &quot;smallest&quot; element in the group
   * 
   * @return the &quot;smallest&quot; element in the group
   */
  public final VT getSmallestValue() {
    return this.m_values.get(0);
  }

  /**
   * Obtain the &quot;largest&quot; element in the group
   * 
   * @return the &quot;largest&quot; element in the group
   */
  public final VT getLargestValue() {
    return this.m_values.get(this.m_values.size() - 1);
  }

  /**
   * Obtain the data belonging to this value group
   * 
   * @return the data belonging to this value group
   */
  public final ArraySetView<DT> getData() {
    return this.m_data;
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_data),//
        HashUtils.hashCode(this.m_values));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    final PropertyValueGroup pvg;

    if (o == this) {
      return true;
    }

    if (o instanceof PropertyValueGroup) {
      pvg = ((PropertyValueGroup) o);
      if (pvg.hashCode() == this.hashCode()) {
        return (this.m_values.equals(pvg.m_values)//
        && this.m_data.equals(pvg.m_data));
      }
    }

    return false;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final int compareTo(final PropertyValueGroup o) {
    int length, p, q, i, res;
    ArraySetView viewA, viewB;

    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }

    viewA = this.m_values;
    viewB = o.m_values;
    for (;;) {
      length = Math.min((p = viewA.size()), (q = viewB.size()));
      for (i = 0; i < length; i++) {
        res = EComparison.compareObjects(viewA.get(i), viewB.get(i));
        if (res != 0) {
          return res;
        }
      }
      if (p < q) {
        return (-1);
      }
      if (q < p) {
        return 1;
      }
      if (viewA == this.m_data) {
        break;
      }
      viewA = this.m_data;
      viewB = o.m_data;
    }

    return 0;
  }
}
