package org.optimizationBenchmarking.utils.collections.maps;

/**
 * the linked map entry
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class ChainedMapEntry<K, V> extends BasicMapEntry<K, V> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the next map entry */
  transient ChainedMapEntry<K, V> m_next;

  /** the hash code */
  transient int m_hash;

  /** instantiate */
  protected ChainedMapEntry() {
    super();
  }

}
