package org.optimizationBenchmarking.experimentation.evaluation.data;

import java.util.Iterator;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;

/**
 * The property set.
 * 
 * @param <PVT>
 *          the property value type
 * @param <PT>
 *          the property type
 * @param <PST>
 *          the property setting type
 */
abstract class _PropertySet<PVT extends _PropertyValue<?>, PT extends _Property<PVT>, PST extends _PropertySetting<?, ?>>
    extends _IDObjectSet<PT> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the properties */
  private final PT[] m_properties;

  /**
   * create
   * 
   * @param data
   *          the instances
   */
  @SuppressWarnings("unchecked")
  _PropertySet(final _Property<?>[] data) {
    super(((PT[]) data), false, true, true);
    this.m_properties = ((PT[]) data);
  }

  /**
   * Obtain a property fitting to a given name
   * 
   * @param name
   *          the property name
   * @return the property, or {@code null} if none could be found
   */
  @Override
  public final PT find(final String name) {
    final PT[] data;
    int low, high, mid, cmp;
    PT midVal;

    data = this.m_properties;
    low = 0;
    high = (data.length - 1);

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = data[mid];

      cmp = midVal.getName().compareTo(name);

      if (cmp < 0) {
        low = (mid + 1);
      } else {
        if (cmp > 0) {
          high = (mid - 1);
        } else {
          return midVal;
        }
      }
    }

    return null;
  }

  /**
   * Create a property setting based on the given collection of values. All
   * values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.Parameter#generalized()
   * generalized}
   * 
   * @param values
   *          the set of property values
   * @return the property setting
   */
  public final PST createSettingFromValues(
      final Iterable<? extends PVT> values) {
    return this.createSetting(values.iterator(), true, false);
  }

  /**
   * Create a property setting based on the given mapping of property names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.Parameter#generalized()
   * generalized}
   * 
   * @param values
   *          the mapping of names to values
   * @return the property setting
   */
  public final PST createSettingFromMapping(
      final Map<String, Object> values) {
    return this.createSettingFromMapping(values.entrySet());
  }

  /**
   * Create a property setting based on the given mapping of property names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.Parameter#generalized()
   * generalized}
   * 
   * @param values
   *          the mapping of names to values
   * @return the property setting
   */
  public final PST createSettingFromMapping(
      final Map.Entry<String, Object>[] values) {
    return this.createSetting(new _PropertyMappingIterator<>(this,
        new ArrayIterator<>(values), false), true, false);
  }

  /**
   * Create a property setting based on the given mapping of property names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.Parameter#generalized()
   * generalized}
   * 
   * @param values
   *          the mapping of names to values
   * @return the property setting
   */
  public final PST createSettingFromMapping(
      final Iterable<Map.Entry<String, Object>> values) {
    return this.createSetting(
        new _PropertyMappingIterator<>(this, values.iterator(), false),
        true, false);
  }

  /**
   * Create a property setting based on the given collection of values. All
   * values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.Parameter#generalized()
   * generalized}
   * 
   * @param values
   *          the set of property values
   * @return the property setting
   */
  @SuppressWarnings("unchecked")
  public final PST createSettingFromValues(final PVT... values) {
    return this.createSetting(new ArrayIterator<>(values), true, false);
  }

  /**
   * create the settings object
   * 
   * @param values
   *          the values
   * @param isGeneralized
   *          has there been a generalization
   * @return the setting
   */
  abstract PST _createSetting(_PropertyValue<?>[] values,
      final boolean isGeneralized);

  /**
   * Create a parameter setting based on the given collection of values.
   * All values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.Parameter#generalized()
   * generalized}
   * 
   * @param values
   *          the set of parameter values
   * @param fillGeneral
   *          fill generalized
   * @param allowNullValues
   *          are {@code null} values allowed and ignored?
   * @return the parameter setting
   */
  @SuppressWarnings("rawtypes")
  final PST createSetting(final Iterator<? extends PVT> values,
      final boolean fillGeneral, final boolean allowNullValues) {
    final _PropertyValue<?>[] ps;
    final PT[] data;
    boolean isGeneral;
    _Property<?> valueParameter;
    _PropertyValue<?> v;
    int valueId, valueParameterId, count;

    data = this.m_properties;
    ps = new _PropertyValue[data.length];
    count = 0;
    isGeneral = false;
    while (values.hasNext()) {
      v = values.next();
      if (v == null) {
        if (allowNullValues) {
          continue;
        }
        throw new IllegalArgumentException(//
            "Property value must not be null, but the " + //$NON-NLS-1$
                count + "th specified value is."); //$NON-NLS-1$
      }
      valueParameter = ((_Property) (v.m_owner));
      valueParameterId = valueParameter.m_id;

      if ((valueParameterId < 0) && (valueParameterId >= data.length)) {
        throw new IllegalArgumentException(//
            "Property value '" + v + //$NON-NLS-1$
                "' is not allowed since its owning property '"//$NON-NLS-1$
                + valueParameter + "' has an illegal id."); //$NON-NLS-1$
      }
      if (data[valueParameterId] != valueParameter) {
        throw new IllegalArgumentException(//
            "Property value '" + v + //$NON-NLS-1$
                "' is not allowed since its owning property '"//$NON-NLS-1$
                + valueParameter
                + "' has an id which belongs to the different property ('" + //$NON-NLS-1$
                data[valueParameterId] + "')."); //$NON-NLS-1$
      }

      if ((ps[valueParameterId] != null) && (ps[valueParameterId] != v)) {
        throw new IllegalArgumentException(
            (((((("Two values have been provided for property '" + //$NON-NLS-1$
            v.m_owner) + "': '") + ps[valueParameterId]) + //$NON-NLS-1$
            "' and '") + v) + '\'') + '.'); //$NON-NLS-1$
      }

      valueId = v.m_id;
      switch (valueId) {
        case (_PropertyValueUnspecified.ID): {
          if (v != valueParameter.unspecified()) {
            throw new IllegalArgumentException("Value '" + v + //$NON-NLS-1$
                "' must be unspecified, but is not."); //$NON-NLS-1$
          }
          break;
        }
        case (_PropertyValueGeneralized.ID): {
          if (v != valueParameter.m_general) {
            throw new IllegalArgumentException("Value '" + v + //$NON-NLS-1$
                "' must be a generalization value, but is not."); //$NON-NLS-1$
          }
          isGeneral = true;
          break;
        }
        default: {
          if ((valueId < 0)
              || (valueId >= (valueParameter.m_values.length))) {
            throw new IllegalArgumentException(
                ((((((("The id " + valueId) + //$NON-NLS-1$
                " of value '") + v) + //$NON-NLS-1$
                "' of property '") + v.m_owner) + //$NON-NLS-1$
                "' has is invalid, valid ids of that property are in 0..") //$NON-NLS-1$
                + (valueParameter.m_values.length - 1)) + '.');
          }
          if (valueParameter.m_values[valueId] != v) {
            throw new IllegalArgumentException(
                (("Value '" + v + "' of property '" + v.m_owner + //$NON-NLS-1$ //$NON-NLS-2$
                    "' has an invalid id which is already occupied by value '" + //$NON-NLS-1$
                valueParameter.m_values[valueId]) + '\'') + '.');
          }
        }
      }

      ps[valueParameterId] = v;
      count++;
    }

    if (count <= 0) {
      throw new IllegalArgumentException(//
          "Property setting cannot be empty."); //$NON-NLS-1$
    }
    if (count < data.length) {
      isGeneral |= (fillGeneral);
      count = 0;
      for (final _PropertyValue<?> p : ps) {
        if (p == null) {
          if ((ps[count] = (fillGeneral ? data[count].m_general : //
              data[count].unspecified())) == null) {
            throw new IllegalArgumentException(//
                "Error when filling undefined property values."); //$NON-NLS-1$
          }
        }
        count++;
      }
    }

    return this._createSetting(ps, isGeneral);
  }

  /** {@inheritDoc} */
  @Override
  public final ExperimentSet getOwner() {
    return ((ExperimentSet) (this.m_owner));
  }
}
