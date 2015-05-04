package org.optimizationBenchmarking.utils.collections.maps;

/**
 * An entry for an object set:
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class ObjectSetEntry<K, V> extends ChainedMapEntry<K, V> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** @serial the key */
  K m_key;

  /** instantiate */
  protected ObjectSetEntry() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final K getKey() {
    return this.m_key;
  }

  /** {@inheritDoc} */
  @Override
  protected final void setKey(final K key) {//
    this.m_key = key;
  }
}
