package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.collections.maps.ObjectMapEntry;
import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;

/**
 * A configuration map is a case-insensitive string map which allows
 * setting a type for an entry one time.
 */
final class _ConfigMap extends StringMapCI<Object> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the configuration map */
  _ConfigMap() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final ObjectMapEntry<String, Object> createMapEntry() {
    return new _ConfigMapEntry();
  }
}
