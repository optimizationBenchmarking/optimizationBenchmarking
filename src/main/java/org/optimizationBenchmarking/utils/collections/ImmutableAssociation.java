package org.optimizationBenchmarking.utils.collections;

import org.optimizationBenchmarking.utils.collections.maps.BasicMapEntry;

/**
 * A basic implementation of the {@link java.util.Map.Entry} interface
 * which leaves all value-related information abstract but provides methods
 * for hash codes and equality checking.
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class ImmutableAssociation<K, V> extends BasicMapEntry<K, V> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the key */
  private final K m_key;

  /** the value */
  private final V m_value;

  /**
   * create
   *
   * @param key
   *          the key
   * @param value
   *          the value
   */
  public ImmutableAssociation(final K key, final V value) {
    super();
    this.m_key = key;
    this.m_value = value;
  }

  /** {@inheritDoc} */
  @Override
  public final K getKey() {
    return this.m_key;
  }

  /** {@inheritDoc} */
  @Override
  public final V getValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  protected final Object clone() {
    return this;
  }
}
