package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.Iterator;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;

/**
 * the property mapping iterator
 */
@SuppressWarnings("rawtypes")
final class _PropertyMappingIterator extends BasicIterator<PropertyValue> {

  /** the owner */
  private final _PropertySet m_owner;

  /** the iterator */
  private final Iterator<Map.Entry<String, Object>> m_it;

  /** are {@code null} values allowed (and ignored)? */
  private final boolean m_allowNullValues;

  /**
   * create
   *
   * @param owner
   *          the owner
   * @param it
   *          the iterator
   * @param allowNullValues
   *          are {@code null} values allowed (and ignored)?
   */
  _PropertyMappingIterator(final _PropertySet owner,
      final Iterator<Map.Entry<String, Object>> it,
      final boolean allowNullValues) {
    super();
    this.m_owner = owner;
    this.m_it = it;
    this.m_allowNullValues = allowNullValues;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return this.m_it.hasNext();
  }

  /** {@inheritDoc} */
  @Override
  public final PropertyValue next() {
    final Map.Entry<String, Object> e;
    final Property prop;
    final String key;
    final Object val;
    final PropertyValue v;

    e = this.m_it.next();
    if (e == null) {
      if (this.m_allowNullValues) {
        return null;
      }
      throw new IllegalArgumentException(//
          "Mapping entry must not be null."); //$NON-NLS-1$
    }
    key = e.getKey();
    if (key == null) {
      if (this.m_allowNullValues) {
        return null;
      }
      throw new IllegalArgumentException(//
          "Mapping key must not be null."); //$NON-NLS-1$
    }
    val = e.getValue();
    if (val == null) {
      if (this.m_allowNullValues) {
        return null;
      }
      throw new IllegalArgumentException(//
          "Mapping value must not be null, but the value of property '" //$NON-NLS-1$
          + key + "' is."); //$NON-NLS-1$
    }

    prop = this.m_owner.find(key);
    if (prop == null) {
      if (this.m_allowNullValues) {
        return null;
      }
      throw new IllegalStateException(((//
          "Could not find a property belonging to the name '" + //$NON-NLS-1$
          key) + '\'') + '.');
    }

    v = prop.findValue(val);
    if (v == null) {
      if (this.m_allowNullValues) {
        return null;
      }
      throw new IllegalStateException(((((//
          "Could not find the value '"//$NON-NLS-1$
          + val) + "' of property '") + //$NON-NLS-1$
          key) + '\'') + '.');
    }

    return v;
  }
}
