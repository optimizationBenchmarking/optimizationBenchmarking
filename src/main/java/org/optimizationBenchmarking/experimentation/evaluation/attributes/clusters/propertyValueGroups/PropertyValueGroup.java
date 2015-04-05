package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A group of values. This class is most likely used to group property
 * values.
 * 
 * @param <DT>
 *          the data set type
 */
public abstract class PropertyValueGroup<DT extends DataElement> extends
    HashObject {

  /** the data elements */
  private final ArraySetView<DT> m_data;
  /** the owning value group set */
  PropertyValueGroups<?> m_owner;

  /**
   * create the property value group
   * 
   * @param data
   *          the data
   */
  PropertyValueGroup(final DT[] data) {
    super();

    if (data == null) {
      throw new IllegalArgumentException(//
          "Data elements belonging to property value group must not be null."); //$NON-NLS-1$
    }
    if (data.length < 1) {
      throw new IllegalArgumentException(//
          "There must be at least one data elements belonging to property value group."); //$NON-NLS-1$
    }

    this.m_data = new ArraySetView<>(data);
  }

  /**
   * Get the owning group set
   * 
   * @return the owning group set
   */
  public PropertyValueGroups<?> getOwner() {
    return this.m_owner;
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
    return HashUtils.hashCode(this.m_data);
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
      return this.m_data.equals(pvg.m_data);
    }

    return false;
  }
}
