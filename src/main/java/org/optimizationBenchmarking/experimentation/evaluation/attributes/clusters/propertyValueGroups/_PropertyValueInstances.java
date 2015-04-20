package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * instances of a property value
 * 
 * @param <VT>
 *          the value type
 */
final class _PropertyValueInstances<VT> implements
    Comparable<_PropertyValueInstances<?>> {

  /** the value */
  final VT m_value;

  /** the elements */
  final IDataElement[] m_elements;

  /** the number of elements */
  int m_size;

  /**
   * create
   * 
   * @param value
   *          the values
   * @param estimatedSize
   *          the estimated size
   */
  _PropertyValueInstances(final VT value, final int estimatedSize) {
    super();
    this.m_elements = new IDataElement[estimatedSize];
    this.m_value = value;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final _PropertyValueInstances<?> o) {
    return EComparison.compareObjects(this.m_value, o.m_value);
  }
}
