package org.optimizationBenchmarking.utils.collections.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * A cache can normalize object representations: You pass an object into
 * {@link #normalize(Object)}. If an
 * {@linkplain java.lang.Object#equals(Object) equal} object has been
 * passed into this method before, this original instance is returned.
 * Otherwise, the new object instance is remembered and returned. However,
 * the cache is memory-dependent: If we run low on memory, the cache can be
 * flushed automatically.
 */
public final class NormalizingCache extends Cache {

  /** the cache */
  private SoftReference<HashMap<Object, Object>> m_cache;

  /** the cache */
  public NormalizingCache() {
    super();
  }

  /**
   * Obtain a normalized representation of the given object. If an
   * {@linkplain java.lang.Object#equals(Object) equal} object has been
   * passed into this method before and the cache does not have been
   * flushed due to memory consumption, the original object is returned
   * again.
   *
   * @param <T>
   *          the type of the object
   * @param object
   *          the object
   * @return the normalized representation of the object, i.e., an
   *         {@linkplain java.lang.Object#equals(Object) equal} object
   */
  @SuppressWarnings("unchecked")
  public synchronized final <T> T normalize(final T object) {
    HashMap<Object, Object> map;
    Object got;

    if (object == null) {
      return null;
    }

    if ((this.m_cache == null) || ((map = this.m_cache.get()) == null)) {
      map = new HashMap<>();
      this.m_cache = new SoftReference<>(map);
    } else {
      got = map.get(object);
      if (got != null) {
        return ((T) got);
      }
    }

    map.put(object, object);
    return object;
  }
}
