package org.optimizationBenchmarking.utils.collections.maps;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;
import org.optimizationBenchmarking.utils.collections.visitors.IVisitor;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * The basic map class. The idea behind this class is that it should be
 * able to represent both, maps and sets. A map entry also may be an
 * arbitrary complex data structure and keys do not necessarily need to be
 * objects, they may as well be simple numbers (which then need to be
 * encapsulated into wrapper classes in the implementation).
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 * @param <NT>
 *          the entry type
 */
public class BasicMap<K, V, NT extends BasicMapEntry<K, V>> extends
    AbstractMap<K, V> implements Iterable<NT>, Serializable, Cloneable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the empty basic map */
  public static final BasicMap<Object, Object, BasicMapEntry<Object, Object>> EMPTY_MAP = new BasicMap<>();

  /** the entry set */
  private transient volatile _BasicMapEntrySet<K, V, NT> m_entries;

  /** instantiate */
  protected BasicMap() {
    super();
  }

  /**
   * get the map entry for a given key
   *
   * @param key
   *          the key
   * @param create
   *          {@code true} if the entry should be created if it does not
   *          exist yet, {@code false} if {@code null} should be returned
   *          in that case
   * @return the corresponding map entry, or {@code null} if both the key
   *         was not found and {@code create} is {@code false}, too
   */
  public NT getEntry(final K key, final boolean create) {
    if (create) {
      throw new UnsupportedOperationException();
    }
    return null;
  }

  /**
   * this method should be called whenever an entry has been created and
   * inserted into the map
   *
   * @param entry
   *          the new entry
   */
  protected void afterEntryCreation(final NT entry) {
    //
  }

  /**
   * this method should be called before an entry is removed. it may not be
   * called when the map is cleared
   *
   * @param entry
   *          the entry to be removed
   */
  protected void beforeEntryRemoval(final NT entry) {
    //
  }

  /**
   * create a map entry
   *
   * @return the new map entry
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected NT createMapEntry() {
    return ((NT) (new ObjectMapEntry()));
  }

  /**
   * remove the given entry from the map
   *
   * @param entry
   *          the entry to remove
   */
  public void removeEntry(final NT entry) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "unused" })
  public final boolean containsKey(final Object key) {
    try {
      return (this.getEntry(((K) key), false) != null);
    } catch (final ClassCastException cnse) {
      return false;
    }
  }

  /**
   * Check whether the map contains a given entry
   *
   * @param entry
   *          the entry to find
   * @return {@code true} if the entry was discovered, {@code false}
   *         otherwise
   */
  public final boolean contains(final NT entry) {
    return ((entry != null) && (this.getEntry(entry.getKey(), false) == entry));
  }

  /** {@inheritDoc} */
  @Override
  public boolean containsValue(final Object value) {
    Iterator<NT> it;

    it = this.iterator();
    while (it.hasNext()) {
      if (EComparison.equals(it.next().getValue(), value)) {
        return true;
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final V get(final Object key) {
    final NT e;

    e = this.getEntry((K) key, false);
    return ((e != null) ? e.getValue() : null);
  }

  /** {@inheritDoc} */
  @Override
  public V put(final K key, final V value) {
    NT e;
    V o;

    e = this.getEntry(key, true);
    o = e.getValue();
    e.setValue(value);
    return o;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final V remove(final Object key) {
    NT e;
    V re;

    e = this.getEntry((K) key, false);
    if (e == null) {
      return null;
    }
    re = e.getValue();
    this.removeEntry(e);

    return re;
  }

  /**
   * Access the internal entry set
   *
   * @return the entry set
   */
  public AbstractSet<NT> entries() {
    if (this.m_entries == null) {
      this.m_entries = new _BasicMapEntrySet<>(this);
    }
    return this.m_entries;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final AbstractSet<Map.Entry<K, V>> entrySet() {
    return ((AbstractSet) (this.entries()));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public BasicMap<K, V, NT> clone() {
    BasicMap<K, V, NT> m;
    try {
      m = ((BasicMap<K, V, NT>) (super.clone()));
      m.m_entries = null;
      return m;
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public BasicIterator<NT> iterator() {
    return ((BasicIterator) (BasicIterator.EMPTY_ITERATOR));
  }

  /**
   * Visit all the entries in this map
   *
   * @param visitor
   *          the visitor
   * @return {@code false} if the
   *         {@link org.optimizationBenchmarking.utils.collections.visitors.IVisitor#visit(Object)}
   *         method of {@code visitor} ever returned {@code false},
   *         {@code true} otherwise
   */
  public boolean visit(final IVisitor<NT> visitor) {
    final BasicIterator<NT> it;

    if ((visitor == null) || (this.isEmpty())) {
      return true;
    }

    it = this.iterator();
    while (it.hasNext()) {
      if (!(visitor.visit(it.next()))) {
        return false;
      }
    }
    return true;
  }
}
