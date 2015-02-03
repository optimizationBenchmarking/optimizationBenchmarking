package org.optimizationBenchmarking.experimentation.data;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * a concrete parameter setting
 * 
 * @param <PT>
 *          the property type
 * @param <PVT>
 *          the property value type
 */
class _PropertySetting<PVT extends _PropertyValue<?>, PT extends _Property<PVT>>
    extends AbstractMap<PT, Object> implements
    Comparable<_PropertySetting<?, ?>>, Serializable, Iterable<PVT> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the property values */
  final _PropertyValue<?>[] m_values;

  /** is this setting general? */
  private final boolean m_isGeneral;

  /** the property value set */
  private transient _PropertyValueSet m_propertySet;

  /**
   * create
   * 
   * @param values
   *          the property values
   * @param isGeneral
   *          is this setting generalized?
   */
  _PropertySetting(final _PropertyValue<?>[] values,
      final boolean isGeneral) {
    super();
    this.m_values = values;
    this.m_isGeneral = isGeneral;
  }

  /**
   * Does this setting contain at least one generalized value?
   * 
   * @return {@code true} if and only if this setting contains at least one
   *         generalized value, {@code false} otherwise
   */
  public final boolean isGeneralized() {
    return this.m_isGeneral;
  }

  /** {@inheritDoc} */
  @Override
  public final int size() {
    return this.m_values.length;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isEmpty() {
    return false;
  }

  /**
   * Get the value of a given property, or {@code null} if this property is
   * not specified.
   * 
   * @param property
   *          the property
   * @return the property value
   */
  @SuppressWarnings("rawtypes")
  public final Object valueOf(final PT property) {
    final int id;
    final _PropertyValue[] pv;
    final _PropertyValue v;

    return ((((property != null) && //
        ((id = property.m_id) >= 0) && //
        (id < (pv = this.m_values).length) && //
    ((v = pv[id]).m_owner == property))) ? v.m_value : null);
  }

  /**
   * Check whether a given property value is contained in the property
   * value set.
   * 
   * @param value
   *          the property value
   * @return {@code true} if the property value is contained, {@code false}
   *         otherwise
   */
  @SuppressWarnings("rawtypes")
  public final boolean contains(final PVT value) {
    final _PropertyValue[] pv;
    final _IDObject p;
    final int id;

    return ((value != null)
        && ((p = ((_IDObject) (value.m_owner))) != null)
        && ((id = p.m_id) >= 0) && (id < (pv = this.m_values).length)//
    && (pv[id] == value));
  }

  /**
   * Check whether a given property value is contained in this property
   * setting
   * 
   * @param object
   *          the object
   * @return {@code true} if the property value is contained, {@code false}
   *         otherwise
   */
  @SuppressWarnings("rawtypes")
  public final boolean contains(final Object object) {
    final _PropertyValue[] pv;
    final _IDObject p, paramValue;
    final int id;

    return ((object instanceof _PropertyValue)//
        && ((p = ((_IDObject) ((paramValue = ((_IDObject) object)).m_owner))) != null)//
        && ((id = p.m_id) >= 0) && (id < (pv = this.m_values).length) && //
    (pv[id] == paramValue));
  }

  /**
   * Check whether a given property has a value in the configuration of
   * this experiment
   * 
   * @param property
   *          the property
   * @return {@code true} if the property has a value, {@code false}
   *         otherwise
   */
  @SuppressWarnings("rawtypes")
  public final boolean specifies(final PT property) {
    final _PropertyValue v;
    final int id;
    final _PropertyValue[] pv;
    final Object o;

    return ((property != null)//
        && ((id = (property.m_id)) >= 0)//
        && (id < (pv = this.m_values).length)
        && ((v = pv[id]).m_owner == property)//
        && ((o = v.m_value) != _PropertyValueGeneralized.INSTANCE) && //
    (o != _PropertyValueUnspecified.INSTANCE));
  }

  /**
   * Get an iterator iterating over all property values
   * 
   * @return the iterator iterating over the property values
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArrayIterator<PVT> iterator() {
    return new ArrayIterator(this.m_values);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final AbstractSet<Map.Entry<PT, Object>> entrySet() {
    if (this.m_propertySet == null) {
      this.m_propertySet = new _PropertyValueSet(this);
    }
    return ((AbstractSet) (this.m_propertySet));
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final boolean containsKey(final Object o) {
    final _PropertyValue[] pv;
    final int id;

    return ((o instanceof _Property) && //
        ((id = (((_Property) o).m_id)) >= 0) && //
        (id < (pv = this.m_values).length) && //
    (pv[id].m_owner == o));
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final Object get(final Object key) {
    final int id;
    final _PropertyValue[] pv;
    final _PropertyValue v;

    return ((((key instanceof _Property) && //
        ((id = ((Parameter) key).m_id) >= 0) && //
        (id < (pv = this.m_values).length) && //
    ((v = pv[id]).m_owner == key))) ? v.m_value : null);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof _PropertySetting) {
      return Arrays.equals(this.m_values, ((_PropertySetting) o).m_values);
    }
    return super.equals(o);
  }

  /**
   * Check if this parameter set is the same or a super-set of another
   * setting.
   * 
   * @param setting
   *          the other setting
   * @return {@code true} if this set here either specifies the same
   *         parameter values as {@code setting} or is more general (does
   *         not specify some settings)
   */
  @SuppressWarnings("rawtypes")
  public final boolean contains(final _PropertySetting<?, ?> setting) {
    final _PropertyValue[] vs1, vs2;
    _PropertyValue v2;
    int i;

    if (setting == this) {
      return true;
    }
    if (setting == null) {
      return false;
    }

    vs1 = this.m_values;
    vs2 = setting.m_values;
    if (vs1 == vs2) {
      return true;
    }
    i = 0;
    for (final _PropertyValue v1 : vs1) {
      v2 = vs2[i++];
      if (v1 == v2) {
        continue;
      }
      if ((v1.m_owner == v2.m_owner) && //
          (v1.m_value == _PropertyValueGeneralized.INSTANCE)) {
        continue;
      }
      return false;
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final int compareTo(final _PropertySetting<?, ?> o) {
    final _PropertyValue[] vs1, vs2;
    _PropertyValue v2;
    int i;

    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }

    vs1 = this.m_values;
    vs2 = o.m_values;
    if (vs1 == vs2) {
      return 0;
    }
    i = 0;
    for (final _PropertyValue v1 : vs1) {
      v2 = vs2[i++];
      if (v1 == v2) {
        continue;
      }
      if (v1.m_id < v2.m_id) {
        return (-1);
      }
      if (v1.m_id > v2.m_id) {
        return 1;
      }
    }

    if (vs1.length < vs2.length) {
      return (-1);
    }
    if (vs1.length > vs2.length) {
      return 1;
    }
    return 0;
  }

  /**
   * convert this property setting to a name
   * 
   * @return the name
   */
  public final String toName() {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    for (final _PropertyValue<?> p : this.m_values) {
      if ((!(p.m_value instanceof _PropertyValueGeneralized)) && //
          (!(p.m_value instanceof _PropertyValueUnspecified))) {
        if (sb.length() > 0) {
          sb.append(',');
          sb.append(' ');
        }
        sb.append(p);
      }
    }
    return sb.toString();
  }
}
