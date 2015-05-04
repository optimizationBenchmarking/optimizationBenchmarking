package org.optimizationBenchmarking.utils.collections.maps;

import java.util.NoSuchElementException;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;

/**
 * the hash map iterator
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 * @param <NT>
 *          the entry type
 */
final class _ObjectMapIterator<K, V, NT extends ChainedMapEntry<K, V>>
    extends BasicIterator<NT> {

  /** the map */
  private final ObjectMap<K, V, NT> m_map;

  /** @serial the hash map iterator */
  private ChainedMapEntry<K, V> m_next;

  /** @serial the current index */
  private int m_index;

  /** @serial the current entry */
  private ChainedMapEntry<K, V> m_current;

  /**
   * the iterator
   *
   * @param map
   *          the map
   */
  _ObjectMapIterator(final ObjectMap<K, V, NT> map) {
    super();

    final ChainedMapEntry<K, V>[] t;

    this.m_map = map;

    if (map.m_size > 0) { // advance to first entry
      t = map.m_table;
      while ((this.m_index < t.length) && //
          ((this.m_next = (t[this.m_index++])) == null)) {
        //
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_next != null);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final NT next() {
    ChainedMapEntry<K, V> e;
    final ChainedMapEntry<K, V>[] t;

    e = this.m_next;
    if (e == null) {
      throw new NoSuchElementException();
    }

    if ((this.m_next = e.m_next) == null) {
      t = this.m_map.m_table;
      while (((this.m_index < t.length) && //
      (((this.m_next = (t[this.m_index++]))) == null))) {//
      }
    }
    this.m_current = e;
    return ((NT) e);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final void remove() {
    if (this.m_current == null) {
      throw new IllegalStateException();
    }
    this.m_map.removeEntry(((NT) (this.m_current)));
    this.m_current = null;
  }
}
