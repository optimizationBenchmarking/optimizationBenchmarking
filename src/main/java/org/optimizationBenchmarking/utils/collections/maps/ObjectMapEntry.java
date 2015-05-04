package org.optimizationBenchmarking.utils.collections.maps;

/**
 * A map entry for {@link java.lang.Object} keys and values
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class ObjectMapEntry<K, V> extends ObjectSetEntry<K, V> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** @serial the value */
  V m_value;

  /** instantiate */
  protected ObjectMapEntry() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final V getValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final V setValue(final V value) {
    final V old;
    old = this.m_value;
    this.m_value = value;
    return old;
  }
}
