package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.HashMap;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractNamedElement;

/**
 * A property value record which also allows us to keep track
 */
final class _PropertyFSMPropertyRecord extends _PropertyFSMRecord {

  /** the map with the descriptions */
  volatile HashMap<Object, String> m_descriptions;

  /**
   * create
   *
   * @param propertyName
   *          the property name
   * @param propertyDesc
   *          the property description
   * @param propertyValue
   *          the property value
   * @param propertyValueDesc
   *          the property value description
   */
  _PropertyFSMPropertyRecord(final String propertyName,
      final String propertyDesc, final Object propertyValue,
      final String propertyValueDesc) {
    super(propertyName, propertyDesc);

    final HashMap<Object, String> hm;

    this.m_descriptions = hm = new HashMap<>();

    if (propertyValue != null) {
      this.m_refCount++;
      hm.put(propertyValue, propertyValueDesc);
    }
  }

  /** {@inheritDoc} */
  @Override
  final synchronized void _mergePropertyValue(final String propertyName,
      final String propertyDesc, final Object propertyValue,
      final String propertyValueDesc) {
    final HashMap<Object, String> hm;
    final String x;

    this._mergeProperty(propertyName, propertyDesc);
    hm = this.m_descriptions;

    x = hm.get(propertyValue);
    if (x == null) {
      hm.put(propertyValue, propertyValueDesc);
    } else {
      hm.put(propertyValue,
          AbstractNamedElement.mergeDescriptions(x, propertyValueDesc));
    }

  }
}
