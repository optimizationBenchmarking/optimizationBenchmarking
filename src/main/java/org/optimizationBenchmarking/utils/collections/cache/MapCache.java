package org.optimizationBenchmarking.utils.collections.cache;

import java.lang.ref.SoftReference;
import java.util.WeakHashMap;

/**
 * A cache that can store key-value pairs.
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public final class MapCache<K, V> extends Cache {

  /** the cache */
  private SoftReference<WeakHashMap<K, SoftReference<V>>> m_cache;

  /** the cache */
  public MapCache() {
    super();
  }

  /**
   * Get the value belonging to a given key
   *
   * @param key
   *          the key
   * @return the value
   */
  public synchronized final V get(final K key) {
    final WeakHashMap<K, SoftReference<V>> cache;
    final SoftReference<V> ref;
    final V obj;

    if (key == null) {
      return null;
    }

    if (this.m_cache == null) {
      return null;
    }

    cache = this.m_cache.get();
    if (cache == null) {
      return null;
    }

    ref = cache.get(key);
    if (ref == null) {
      return null;
    }

    obj = ref.get();
    if (obj == null) {
      cache.remove(key);
      return null;
    }

    return obj;
  }

  /**
   * Put a key-value pair.
   *
   * @param key
   *          the key
   * @param value
   *          the value
   * @param ifNew
   *          {@code true} if the value is only stored if no value is
   *          already stored, {@code false} otherwise
   * @return the element stored under the key
   */
  public synchronized final V put(final K key, final V value,
      final boolean ifNew) {
    final SoftReference<V> ref;
    final V obj;
    WeakHashMap<K, SoftReference<V>> cache;

    if (key == null) {
      return null;
    }

    if ((this.m_cache == null) || ((cache = this.m_cache.get()) == null)) {
      cache = new WeakHashMap<>();
      this.m_cache = new SoftReference<>(cache);
      cache.put(key, new SoftReference<>(value));
      return value;
    }

    ref = cache.get(key);
    if ((ref == null) || ((obj = ref.get()) == null) || (!ifNew)) {
      cache.put(key, new SoftReference<>(value));
      return value;
    }

    return obj;
  }
}
