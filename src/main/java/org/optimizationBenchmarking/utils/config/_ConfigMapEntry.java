package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.collections.maps.ObjectMapEntry;

/**
 * A map entry for {@link java.lang.Object} keys and values
 */
final class _ConfigMapEntry extends ObjectMapEntry<String, Object> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * @serial is the entry locked, i.e., is further parsing and type
   *         conversion forbidden?
   */
  volatile boolean m_isLocked;

  /** instantiate */
  _ConfigMapEntry() {
    super();
    this.m_isLocked = false;
  }
}