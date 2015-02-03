package org.optimizationBenchmarking.experimentation.data;

import java.util.HashMap;

/**
 * The base class for everything that creates a property.
 * 
 * @param <PST>
 *          the property settings type
 */
class _PropertyFSMSettingBuilder<PST extends _PropertySetting<?, ?>>
    extends
    _PropertyFSM<_PropertyFSMSettingRecord, HashMap<String, Object>, PST, _PropertyFSM<?, ?, ?, ?>> {

  /**
   * create
   * 
   * @param owner
   *          the owner
   */
  _PropertyFSMSettingBuilder(final _PropertyFSM<?, ?, ?, ?> owner) {
    super(owner);
    this._open();
  }

  /** {@inheritDoc} */
  @Override
  final _PropertyFSMSettingRecord _createFSMRecord(
      final String propertyName, final String propertyDesc,
      final Object propertyValue, final String propertyValueDesc) {
    return new _PropertyFSMSettingRecord(propertyName, propertyDesc,
        propertyValue, propertyValueDesc);
  }

  /**
   * process the default parameters
   * 
   * @param map
   *          the map to process
   */
  private static final void __processDefaults(
      final HashMap<String, _PropertyFSMSettingRecord> map) {
    _PropertyFSMSettingRecord classProperty, nameProperty, fullProperty;
    String classString, nameString, fullString, v;
    Object t;

    classProperty = map.get(Parameter.PARAMETER_ALGORITHM_CLASS);
    classString = null;
    if (classProperty != null) {
      if ((t = classProperty.m_propertyValue) instanceof String) {
        classString = ((String) t);
      }
      classProperty.m_propertyName = Parameter.PARAMETER_ALGORITHM_CLASS;
    }

    nameProperty = map.get(Parameter.PARAMETER_ALGORITHM_NAME);
    nameString = null;
    if (nameProperty != null) {
      if ((t = nameProperty.m_propertyValue) instanceof String) {
        nameString = ((String) t);
      }
      nameProperty.m_propertyName = Parameter.PARAMETER_ALGORITHM_NAME;
    }

    fullProperty = map.get(Parameter.PARAMETER_ALGORITHM);
    fullString = null;
    if (fullProperty != null) {
      if ((t = fullProperty.m_propertyValue) instanceof String) {
        fullString = ((String) t);
      }
      fullProperty.m_propertyName = Parameter.PARAMETER_ALGORITHM;
    }

    if (fullProperty == null) {
      if ((classString != null) && (nameString != null)) {
        map.remove(Parameter.PARAMETER_ALGORITHM_CLASS);
        map.remove(Parameter.PARAMETER_ALGORITHM_NAME);
        v = ((((nameString + ' ') + '(') + classString) + ')');
        fullProperty = new _PropertyFSMSettingRecord(//
            Parameter.PARAMETER_ALGORITHM,//
            null, v, null);
        map.put(fullProperty.m_propertyName, fullProperty);
      } else {
        if (classString != null) {
          map.remove(Parameter.PARAMETER_ALGORITHM_CLASS);
          classProperty.m_propertyName = Parameter.PARAMETER_ALGORITHM;
          map.put(classProperty.m_propertyName, classProperty);
        } else {
          if (nameString != null) {
            map.remove(Parameter.PARAMETER_ALGORITHM_NAME);
            nameProperty.m_propertyName = Parameter.PARAMETER_ALGORITHM;
            map.put(nameProperty.m_propertyName, nameProperty);
          }
        }
      }
    } else {
      if (fullString != null) {
        if ((classString != null) && (nameString == null)) {
          map.remove(Parameter.PARAMETER_ALGORITHM_CLASS);
          fullProperty.m_propertyValue = ((((fullString + ' ') + '(') + classString) + ')');
        }
      }
    }

    classProperty = map.get(Parameter.PARAMETER_INITIALIZER_CLASS);
    classString = null;
    if (classProperty != null) {
      if ((t = classProperty.m_propertyValue) instanceof String) {
        classString = ((String) t);
      }
      classProperty.m_propertyName = Parameter.PARAMETER_INITIALIZER_CLASS;
    }

    nameProperty = map.get(Parameter.PARAMETER_INITIALIZER_NAME);
    nameString = null;
    if (nameProperty != null) {
      if ((t = nameProperty.m_propertyValue) instanceof String) {
        nameString = ((String) t);
      }
      nameProperty.m_propertyName = Parameter.PARAMETER_INITIALIZER_NAME;
    }

    fullProperty = map.get(Parameter.PARAMETER_INITIALIZER);
    fullString = null;
    if (fullProperty != null) {
      if ((t = fullProperty.m_propertyValue) instanceof String) {
        fullString = ((String) t);
      }
      fullProperty.m_propertyName = Parameter.PARAMETER_INITIALIZER;
    }

    if (fullProperty == null) {
      if ((classString != null) && (nameString != null)) {
        map.remove(Parameter.PARAMETER_INITIALIZER_CLASS);
        map.remove(Parameter.PARAMETER_INITIALIZER_NAME);
        v = ((((nameString + ' ') + '(') + classString) + ')');
        fullProperty = new _PropertyFSMSettingRecord(//
            Parameter.PARAMETER_INITIALIZER,//
            null, v, null);
        map.put(fullProperty.m_propertyName, fullProperty);
      } else {
        if (classString != null) {
          map.remove(Parameter.PARAMETER_INITIALIZER_CLASS);
          classProperty.m_propertyName = Parameter.PARAMETER_INITIALIZER;
          map.put(classProperty.m_propertyName, classProperty);
        } else {
          if (nameString != null) {
            map.remove(Parameter.PARAMETER_INITIALIZER_NAME);
            nameProperty.m_propertyName = Parameter.PARAMETER_INITIALIZER;
            map.put(nameProperty.m_propertyName, nameProperty);
          }
        }
      }
    } else {
      if (fullString != null) {
        if ((classString != null) && (nameString == null)) {
          map.remove(Parameter.PARAMETER_INITIALIZER_CLASS);
          fullProperty.m_propertyValue = ((((fullString + ' ') + '(') + classString) + ')');
        }
      }
    }

  }

  /** {@inheritDoc} */
  @Override
  final HashMap<String, Object> _doCompile(
      final HashMap<String, _PropertyFSMSettingRecord> map, final int count) {

    final HashMap<String, Object> nm;

    _PropertyFSMSettingBuilder.__processDefaults(map);

    this.m_owner._begin();

    if (this.m_owner instanceof _PropertyFSMSettingBuilder) {
      nm = null;
    } else {
      nm = new HashMap<>(map.size());
    }

    for (final _PropertyFSMSettingRecord rec : map.values()) {
      if (rec._assert(count)) {
        continue;
      }
      this.m_owner._setPropertyValue(rec);
      if (nm != null) {
        nm.put(rec.m_propertyName, rec.m_propertyValue);
      }
    }

    this.m_owner._end();

    return nm;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  final PST _doFinalize(final HashMap<String, Object> compiled,
      final int count) {
    final _PropertySet ps;

    ps = this._getPropertySet();
    return ((PST) (ps.createSetting(new _PropertyMappingIterator(ps,
        compiled.entrySet().iterator(), true), false, true)));
  }
}
