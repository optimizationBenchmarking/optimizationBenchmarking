package org.optimizationBenchmarking.experimentation.data;

import java.lang.ref.SoftReference;

/**
 * The storage type of
 * {@link org.optimizationBenchmarking.experimentation.data.Attribute
 * attribute}. Attributes can be {@link #PERMANENTLY_STORED permanently}
 * stored, {@link #TEMPORARILY_STORED temporarily} stored as long as there
 * is enough memory, or {@link #NEVER_STORED not stored} at all in internal
 * caches. Whenever a attribute which may be stored in a cache is accessed,
 * first it is checked whether the attribute resides in the cache. If so,
 * the cached value is returned. Otherwise, it is computed.
 */
public enum EAttributeType {

  /**
   * This type is for attributes which must be permanently stored in the
   * data set.
   */
  PERMANENTLY_STORED(true),

  /**
   * Attributes of this type my be stored in the data sets but may also be
   * purged in low-memory situations. Once purged, they will simply be
   * re-computed. This is realized by internally referencing them with
   * {@link java.lang.ref.SoftReference soft references} which will be
   * garbage collected when the memory situation warrants it.
   */
  TEMPORARILY_STORED(true) {

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    final <T> T unpack(final Object o) {
      return ((o != null) ? (((SoftReference<T>) (o)).get()) : null);
    }

    /** {@inheritDoc} */
    @Override
    final Object pack(final Object o) {
      return new SoftReference<>(o);
    }

  },

  /**
   * Attributes of this type will never be stored. We would use this
   * attribute type for attributes that either consume a lot of memory or
   * are known to be rarely accessed twice. Storing them is either not
   * feasible or makes no sense. Attributes of this type will always be
   * re-computed when accessed.
   */
  NEVER_STORED(false);

  /** store the data */
  final boolean m_store;

  /**
   * Create the attribute type
   * 
   * @param store
   *          should the attribute data
   */
  private EAttributeType(final boolean store) {
    this.m_store = store;
  }

  /**
   * Unpack an object: the method is internally used to unwrap objects by
   * the cache in a
   * {@link org.optimizationBenchmarking.experimentation.data._IDSet data
   * object}.
   * 
   * @param o
   *          the packed object
   * @return the unpacked object
   * @param <T>
   *          the goal type
   */
  @SuppressWarnings("unchecked")
  <T> T unpack(final Object o) {
    return ((T) o);
  }

  /**
   * pack an object: the method is internally used to wrap objects by the
   * cache in a
   * {@link org.optimizationBenchmarking.experimentation.data._IDSet data
   * object}.
   * 
   * @param o
   *          the unpacked object
   * @return the packed object
   */
  Object pack(final Object o) {
    return o;
  }
}
