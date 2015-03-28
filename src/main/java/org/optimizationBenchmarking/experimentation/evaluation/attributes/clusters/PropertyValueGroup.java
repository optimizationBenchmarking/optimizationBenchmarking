package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.PropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A group of property values
 * 
 * @param <PVT>
 *          the property value type
 */
public class PropertyValueGroup<PVT extends PropertyValue<?>> extends
    ArraySetView<PVT> implements Comparable<PropertyValueGroup<PVT>> {

  /** the default serial version uid */
  private static final long serialVersionUID = 1L;

  /** the hash code */
  private final int m_hashCode;

  /**
   * create the property value group
   * 
   * @param data
   *          the data
   */
  PropertyValueGroup(final PVT[] data) {
    super(data);
    if (data.length < 1) {
      throw new IllegalArgumentException(//
          "There must be at least one value in a property value group."); //$NON-NLS-1$
    }
    this.m_hashCode = super.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_hashCode;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final boolean equals(final Object o) {
    final PropertyValueGroup pvg;
    if (o == this) {
      return true;
    }
    if (o instanceof PropertyValueGroup) {
      pvg = ((PropertyValueGroup) o);
      if (pvg.m_hashCode == this.m_hashCode) {
        return Arrays.equals(this.m_data, pvg.m_data);
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final PropertyValueGroup<PVT> o) {
    final PVT[] a, b;
    final int length;
    int i, res;

    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }

    a = this.m_data;
    b = o.m_data;
    length = Math.min(a.length, b.length);
    for (i = 0; i < a.length; i++) {
      res = a[i].compareTo(b[i]);
      if (res != 0) {
        return res;
      }
    }
    if (length < a.length) {
      return 1;
    }
    if (length < b.length) {
      return (-1);
    }
    return 0;
  }
}
