package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.PropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * The groups of a given property value type
 * 
 * @param <PVT>
 *          the property value type
 */
public class PropertyValueGroups<PVT extends PropertyValue<?>> extends
    ArraySetView<PropertyValueGroup<PVT>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the hash code */
  private final int m_hashCode;

  /**
   * create the property value group
   * 
   * @param data
   *          the data
   */
  PropertyValueGroups(final PropertyValueGroup<PVT>[] data) {
    super(data);
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
    final PropertyValueGroups pvg;
    if (o == this) {
      return true;
    }
    if (o instanceof PropertyValueGroups) {
      pvg = ((PropertyValueGroups) o);
      if (pvg.m_hashCode == this.m_hashCode) {
        return Arrays.equals(this.m_data, pvg.m_data);
      }
    }
    return false;
  }
}
