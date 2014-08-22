package org.optimizationBenchmarking.experimentation.data;

/**
 * A property value record which also allows us to keep track
 */
final class _PropertyFSMSettingRecord extends _PropertyFSMRecord {

  /** the value */
  volatile Object m_propertyValue;

  /** the parameter value description */
  volatile String m_propertyValueDesc;

  /**
   * create
   * 
   * @param propertyName
   *          the property name
   * @param propertyDesc
   *          the property description
   * @param propertyValue
   *          the property value
   * @param propertyValueDesc
   *          the property value description
   */
  _PropertyFSMSettingRecord(final String propertyName,
      final String propertyDesc, final Object propertyValue,
      final String propertyValueDesc) {
    super(propertyName, propertyDesc);

    if (propertyValue != null) {
      this.m_refCount++;
    }
    this.m_propertyValue = propertyValue;
    this.m_propertyValueDesc = propertyValueDesc;
  }

  /** {@inheritDoc} */
  @Override
  synchronized final void _mergePropertyValue(final String propertyName,
      final String propertyDesc, final Object propertyValue,
      final String propertyValueDesc) {

    this._mergeProperty(propertyName, propertyDesc);

    if (this.m_refCount != 0) {

      if (this.m_propertyValue == null) {
        this.m_dead = true;
        throw new IllegalStateException(//
            "Value of property '" + this.m_propertyName + //$NON-NLS-1$
                "' cannot be null as it has a non-zero refCount."); //$NON-NLS-1$
      }

      if (!(this.m_propertyValue.equals(propertyValue))) {
        this.m_dead = true;
        //        throw new IllegalArgumentException((((((("Value update of property '" //$NON-NLS-1$
        //            + this.m_propertyName) + "' cannot change property value, but new value is '")//$NON-NLS-1$
        //            + propertyValue) + "' while old value is '") + //$NON-NLS-1$
        // this.m_propertyValue) + '\'') + '.');
      }

    } else {
      if (propertyValue == null) {
        this.m_dead = true;
        throw new IllegalStateException(//
            "Value of property '" + this.m_propertyName + //$NON-NLS-1$
                "' cannot be set to null."); //$NON-NLS-1$
      }
      this.m_propertyValue = propertyValue;
    }

    this.m_propertyValueDesc = _NamedContext._mergeDescriptions(
        this.m_propertyValueDesc, propertyValueDesc);
  }
}
