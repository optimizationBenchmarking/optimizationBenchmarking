package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Map;

import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySetting;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * a concrete property setting
 *
 * @param <PT>
 *          the property type
 * @param <PVT>
 *          the property value type
 */
class _PropertySetting<PVT extends PropertyValue<?>, PT extends Property<PVT>>
    extends AbstractMap<IProperty, Object> implements Serializable,
    IPropertySetting {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the property values */
  final PropertyValue<?>[] m_values;

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
  _PropertySetting(final PropertyValue<?>[] values, final boolean isGeneral) {
    super();
    this.m_values = values;
    this.m_isGeneral = isGeneral;
  }

  /** {@inheritDoc} */
  @Override
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
    final PropertyValue[] pv;
    final _IDObject p;
    final int id;

    return ((value != null)
        && ((p = ((_IDObject) (value.m_owner))) != null)
        && ((id = p.m_id) >= 0) && (id < (pv = this.m_values).length)//
    && (pv[id] == value));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final boolean contains(final IPropertyValue property) {
    if (property instanceof PropertyValue) {
      return this.contains((PVT) property);
    }
    return false;
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
    final PropertyValue[] pv;
    final _IDObject p, paramValue;
    final int id;

    return ((object instanceof PropertyValue)//
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
    final PropertyValue v;
    final int id;
    final PropertyValue[] pv;
    final Object o;

    return ((property != null)//
        && ((id = (property.m_id)) >= 0)//
        && (id < (pv = this.m_values).length)
        && ((v = pv[id]).m_owner == property)//
        && ((o = v.m_value) != _PropertyValueGeneralized.INSTANCE) && //
    (o != _PropertyValueUnspecified.INSTANCE));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final boolean specifies(final IProperty property) {
    if (property instanceof Property) {
      return this.specifies((PT) property);
    }
    return false;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArrayIterator<? extends IPropertyValue> iterator() {
    return new ArrayIterator(this.m_values);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final AbstractSet<Map.Entry<IProperty, Object>> entrySet() {
    if (this.m_propertySet == null) {
      this.m_propertySet = new _PropertyValueSet(this);
    }
    return ((AbstractSet) (this.m_propertySet));
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final boolean containsKey(final Object o) {
    final PropertyValue[] pv;
    final int id;

    return ((o instanceof Property) && //
        ((id = (((Property) o).m_id)) >= 0) && //
        (id < (pv = this.m_values).length) && //
    (pv[id].m_owner == o));
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final Object get(final Object key) {
    final int id;
    final PropertyValue[] pv;
    final PropertyValue v;

    return ((((key instanceof Property) && //
        ((id = ((Property) key).m_id) >= 0) && //
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

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean subsumes(final IPropertySetting setting) {
    final PropertyValue[] vs1, vs2;
    PropertyValue v2;
    int i;

    if (setting instanceof _PropertySetting) {

      if (setting == this) {
        return true;
      }

      vs1 = this.m_values;
      vs2 = ((_PropertySetting) setting).m_values;
      if (vs1 == vs2) {
        return true;
      }
      i = 0;
      for (final PropertyValue v1 : vs1) {
        v2 = vs2[i++];
        if (v1 == v2) {
          continue;
        }
        if ((v1.m_owner == v2.m_owner) && //
            (_PropertyValueGeneralized.INSTANCE.equals(v1.m_value))) {
          continue;
        }
        return false;
      }
      return true;
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final int compareTo(final IPropertySetting o) {
    final PropertyValue[] vs1, vs2;
    final _PropertySetting<?, ?> x;
    PropertyValue v2;
    int i;

    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }

    x = ((_PropertySetting<?, ?>) o);

    vs1 = this.m_values;
    vs2 = x.m_values;
    if (vs1 == vs2) {
      return 0;
    }
    i = 0;
    for (final PropertyValue v1 : vs1) {
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
    for (final PropertyValue<?> p : this.m_values) {
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
