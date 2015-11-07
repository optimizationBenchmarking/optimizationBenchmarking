package org.optimizationBenchmarking.utils.collections.maps;

import java.util.AbstractSet;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;

/**
 * the entry set of the basic map.
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 * @param <NT>
 *          the entry type
 */
final class _BasicMapEntrySet<K, V, NT extends BasicMapEntry<K, V>>
    extends AbstractSet<NT> {

  /** the map */
  private final BasicMap<K, V, NT> m_map;

  /**
   * create
   *
   * @param map
   *          the map
   */
  _BasicMapEntrySet(final BasicMap<K, V, NT> map) {
    super();
    this.m_map = map;
  }

  /** {@inheritDoc} */
  @Override
  public final BasicIterator<NT> iterator() {
    return this.m_map.iterator();
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
  public final boolean contains(final Object o) {
    Map.Entry a, b;
    try {
      return this.m_map.contains(((NT) o));
    } catch (final ClassCastException cce) {
      try {
        a = ((Map.Entry) o);
        b = this.m_map.getEntry(((K) (a.getKey())), false);
        if (b != null) {
          return b.equals(o);
        }
        return false;
      } catch (final ClassCastException cce2) {
        return false;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "unused" })
  public final boolean remove(final Object o) {
    int s;
    try {
      s = this.m_map.size();
      this.m_map.removeEntry((NT) o);
      return (this.m_map.size() < s);
    } catch (final ClassCastException cce) {
      return false;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int size() {
    return this.m_map.size();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isEmpty() {
    return (this.m_map.isEmpty());
  }

  /** {@inheritDoc} */
  @Override
  public final void clear() {
    this.m_map.clear();
  }
}
