package org.optimizationBenchmarking.utils.collections.maps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;
import org.optimizationBenchmarking.utils.collections.visitors.IVisitor;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * The default map. This code basically corresponds to
 * {@link java.util.HashMap} , but it allows us to define the internal
 * entry data structures freely.
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 * @param <NT>
 *          the entry type
 */
public class ObjectMap<K, V, NT extends ChainedMapEntry<K, V>> extends
    BasicMap<K, V, NT> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * The maximum capacity, used if a higher value is implicitly specified
   * by either of the constructors with arguments. MUST be a power of two
   * &le; <code>1&lt;&lt;30</code>.
   */
  private static final int MAXIMUM_CAPACITY = (1 << 30);

  /** the empty table */
  @SuppressWarnings("rawtypes")
  private static final ChainedMapEntry[] EMPTY_TABLE = new ChainedMapEntry[0];

  /**
   * The table, resized as necessary. Length MUST Always be a power of two.
   */
  transient ChainedMapEntry<K, V>[] m_table;

  /** The number of key-value mappings contained in this map. */
  transient int m_size;

  /**
   * @serial The next size value at which to resize (capacity * load
   *         factor).
   */
  transient int m_threshold;

  /**
   * Constructs an empty {@code ObjectMap} with the default initial
   * capacity (16).
   */
  public ObjectMap() {
    this(16);
  }

  /**
   * Constructs an empty {@code ObjectMap} with enough capacity to hold the
   * specified initial size.
   *
   * @param allocateForSize
   *          the number of elements we expect to be added soon
   */
  @SuppressWarnings("unchecked")
  public ObjectMap(final int allocateForSize) {
    super();

    int capacity;

    if (allocateForSize <= 0) {
      this.m_table = ObjectMap.EMPTY_TABLE;
      this.m_threshold = 0;
    } else {
      capacity = ObjectMap._getCapacityForSize(allocateForSize);
      this.m_threshold = ObjectMap._getThresholdFromCapacity(capacity);
      this.m_table = new ChainedMapEntry[capacity];
    }
  }

  /**
   * Compute the hash code
   *
   * @param b
   *          the data
   * @return the hash
   */
  private static final int __hashCode(final Object b) {
    int h;

    if (b == null) {
      return 0;
    }
    h = b.hashCode();
    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
  }

  /**
   * Returns index for hash code h.
   *
   * @param h
   *          the hash code
   * @param length
   *          the table length
   * @return the index
   */
  static int _indexFor(final int h, final int length) {
    return (h & (length - 1));
  }

  /**
   * Compute the resize threshold belonging to a given table capacity.
   *
   * @param capacity
   *          the table capacity
   * @return the threshold
   */
  static final int _getThresholdFromCapacity(final int capacity) {
    return ((capacity >= ObjectMap.MAXIMUM_CAPACITY) ? Integer.MAX_VALUE
        : ((capacity / 3) << 1));
  }

  /**
   * Calculate the capacity needed to facilitate {@code size} elements in
   * the table without violating the
   * {@link #_getThresholdFromCapacity(int) threshold}
   *
   * @param size
   *          the number of elements
   * @return the capacity
   */
  static final int _getCapacityForSize(final int size) {
    int capa, th;

    if (size >= ObjectMap.MAXIMUM_CAPACITY) {
      return ObjectMap.MAXIMUM_CAPACITY;
    }

    capa = (1 << (32 - Integer.numberOfLeadingZeros(size - 1)));
    for (;;) {
      th = ObjectMap._getThresholdFromCapacity(capa);
      if (th > size) {
        return capa;
      }
      if (capa >= ObjectMap.MAXIMUM_CAPACITY) {
        return ObjectMap.MAXIMUM_CAPACITY;
      }
      capa <<= 1;
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected NT createMapEntry() {
    return ((NT) (new ObjectMapEntry()));
  }

  /** {@inheritDoc} */
  @Override
  public final int size() {
    return this.m_size;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isEmpty() {
    return (this.m_size <= 0);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public NT getEntry(final K key, final boolean create) {
    final int hash, index;
    final ChainedMapEntry<K, V> s;
    ChainedMapEntry<K, V>[] table;
    ChainedMapEntry<K, V> e;
    final NT z;

    hash = ObjectMap.__hashCode(key);
    table = this.m_table;

    if (this.m_size <= 0) {
      if (create) {
        if (table == ObjectMap.EMPTY_TABLE) {
          this.m_table = table = new ChainedMapEntry[16];
        }
      } else {
        return null;
      }
    }

    index = ObjectMap._indexFor(hash, table.length);
    for (e = s = table[index]; e != null; e = e.m_next) {
      if ((e.m_hash == hash) && EComparison.equals(e.getKey(), key)) {
        return ((NT) e);
      }
    }

    if (create) {
      z = this.createMapEntry();
      z.m_hash = hash;
      z.m_next = s;
      table[index] = z;

      if ((this.m_size++) >= this.m_threshold) {
        this._resize(table.length << 1);
      }

      z.setKey(key);
      this.afterEntryCreation(z);

      return z;
    }

    return null;
  }

  /**
   * Rehashes the contents of this map into a new array with a larger
   * capacity. This method is called automatically when the number of keys
   * in this map reaches its threshold. If current capacity is
   * MAXIMUM_CAPACITY, this method does not resize the map, but sets
   * threshold to Integer.MAX_VALUE. This has the effect of preventing
   * future calls.
   *
   * @param newCapacity
   *          the new capacity, MUST be a power of two; must be greater
   *          than current capacity unless current capacity is
   *          MAXIMUM_CAPACITY (in which case value is irrelevant).
   */
  @SuppressWarnings("unchecked")
  final void _resize(final int newCapacity) {
    final ChainedMapEntry<K, V>[] oldTable, newTable;
    int i;
    ChainedMapEntry<K, V> next;

    oldTable = this.m_table;
    newTable = new ChainedMapEntry[newCapacity];

    for (ChainedMapEntry<K, V> e : oldTable) {
      while (e != null) {
        next = e.m_next;
        i = ObjectMap._indexFor(e.m_hash, newCapacity);
        e.m_next = newTable[i];
        newTable[i] = e;
        e = next;
      }
    }

    this.m_table = newTable;
    this.m_threshold = ObjectMap._getThresholdFromCapacity(newCapacity);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final boolean visit(final IVisitor<NT> visitor) {
    if (this.m_size <= 0) {
      return true;
    }
    for (ChainedMapEntry<K, V> e : this.m_table) {
      while (e != null) {
        if (!(visitor.visit((NT) e))) {
          return false;
        }
        e = e.m_next;
      }
    }

    return true;
  }

  /** {@inheritDoc} */
  @Override
  public void putAll(final Map<? extends K, ? extends V> m) {
    final int numKeysToBeAdded, targetSize, newCapacity;

    numKeysToBeAdded = m.size();
    if (numKeysToBeAdded <= 0) {
      return;
    }

    targetSize = (numKeysToBeAdded + this.m_size);
    if (targetSize > this.m_threshold) {
      newCapacity = ObjectMap._getCapacityForSize(targetSize);
      if (newCapacity > this.m_table.length) {
        this._resize(newCapacity);
      }
    }

    for (final Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
      this.put(e.getKey(), e.getValue());
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public void removeEntry(final NT entry) {
    final int hash, index;
    final ChainedMapEntry<K, V>[] table;
    ChainedMapEntry<K, V> prev, e, next;

    if (this.m_size > 0) {
      table = this.m_table;
      hash = entry.m_hash;
      index = ObjectMap._indexFor(hash, table.length);

      prev = e = table[index];

      while (e != null) {
        next = e.m_next;
        if (e == entry) {
          this.beforeEntryRemoval(entry);
          if ((--this.m_size) <= 0) {
            this.m_table = ObjectMap.EMPTY_TABLE;
          }
          if (prev == e) {
            table[index] = next;
          } else {
            prev.m_next = next;
          }
          return;
        }
        prev = e;
        e = next;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    if (this.m_size > 0) {
      this.m_table = ObjectMap.EMPTY_TABLE;
      this.m_size = 0;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean containsValue(final Object value) {
    final ChainedMapEntry<K, V>[] table;

    if (this.m_size > 0) {
      table = this.m_table;
      for (ChainedMapEntry<K, V> e : table) {
        while (e != null) {
          if (EComparison.equals(value, e.getValue())) {
            return true;
          }
          e = e.m_next;
        }
      }
    }

    return false;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public ObjectMap<K, V, NT> clone() {
    final ObjectMap<K, V, NT> result;
    ChainedMapEntry<K, V>[] table;
    int i;

    result = ((ObjectMap<K, V, NT>) (super.clone()));

    if (result.m_size <= 0) {
      result.m_table = ObjectMap.EMPTY_TABLE;
      return result;
    }
    result.m_table = table = result.m_table.clone();

    i = 0;
    for (ChainedMapEntry<K, V> e : table) {
      if (e != null) {
        table[i] = ((ChainedMapEntry<K, V>) (e.clone()));
        while (e.m_next != null) {
          e = e.m_next = ((ChainedMapEntry<K, V>) (e.m_next.clone()));
        }
      }
      i++;
    }

    return result;
  }

  /** {@inheritDoc} */
  @Override
  public final BasicIterator<NT> iterator() {
    return new _ObjectMapIterator<>(this);
  }

  /**
   * write this object
   *
   * @param s
   *          the stream
   * @throws IOException
   *           if i/o fails
   */
  private void writeObject(final ObjectOutputStream s) throws IOException {
    s.defaultWriteObject();

    s.writeInt(this.m_size);
    if (this.m_size <= 0) {
      return;
    }

    for (ChainedMapEntry<K, V> e : this.m_table) {
      while (e != null) {
        s.writeObject(e);
        e = e.m_next;
      }
    }
  }

  /**
   * read this object
   *
   * @param stream
   *          the stream
   * @throws IOException
   *           if i/o fails
   * @throws ClassNotFoundException
   *           if a class is missing
   */
  @SuppressWarnings("unchecked")
  private void readObject(final ObjectInputStream stream)
      throws IOException, ClassNotFoundException {
    final ChainedMapEntry<K, V>[] table;
    final int size;
    int i, idx, capa;
    NT entry2;

    stream.defaultReadObject();

    this.m_size = size = stream.readInt();
    if (size <= 0) {
      this.m_table = ObjectMap.EMPTY_TABLE;
      this.m_threshold = 0;
      return;
    }

    capa = ObjectMap._getCapacityForSize(size);
    this.m_threshold = ObjectMap._getThresholdFromCapacity(capa);
    this.m_table = table = new ChainedMapEntry[capa];

    for (i = size; (--i) >= 0;) {
      entry2 = ((NT) (stream.readObject()));
      entry2.m_hash = ObjectMap.__hashCode(entry2.getKey());
      idx = ObjectMap._indexFor(entry2.m_hash, capa);
      entry2.m_next = table[idx];
      table[idx] = entry2;
    }
  }
}
