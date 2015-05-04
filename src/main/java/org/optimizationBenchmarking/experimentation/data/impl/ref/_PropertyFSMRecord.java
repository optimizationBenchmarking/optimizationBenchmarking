package org.optimizationBenchmarking.experimentation.data.impl.ref;

/**
 * A property value record which also allows us to keep track
 */
abstract class _PropertyFSMRecord {

  /** the name */
  volatile String m_propertyName;

  /** the parameter description */
  volatile String m_propertyDesc;

  /** the number of experiments specifying this value */
  volatile int m_refCount;

  /** the value is dead */
  volatile boolean m_dead;

  /**
   * create
   *
   * @param propertyName
   *          the property name
   * @param propertyDesc
   *          the property description
   */
  _PropertyFSMRecord(final String propertyName, final String propertyDesc) {
    super();

    this.m_propertyName = propertyName;
    this.m_propertyDesc = propertyDesc;
    this.m_refCount = 0;
  }

  /**
   * Merge a property definition
   *
   * @param propertyName
   *          the property name
   * @param propertyDesc
   *          the property description
   */
  synchronized final void _mergeProperty(final String propertyName,
      final String propertyDesc) {

    if (this.m_propertyName.equals(propertyName)) {
      this.m_propertyDesc = _NamedContext._mergeDescriptions(
          this.m_propertyDesc, propertyDesc);
    } else {
      this.m_dead = true;
      throw new IllegalStateException(
          "Properties '" + this.m_propertyName + //$NON-NLS-1$
              "' and '" + //$NON-NLS-1$
              propertyName + "' cannot be merged."); //$NON-NLS-1$
    }
  }

  /**
   * Merge a property value definition
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
  abstract void _mergePropertyValue(final String propertyName,
      final String propertyDesc, final Object propertyValue,
      final String propertyValueDesc);

  /**
   * assert that the reference count is equal to {@code target} and die
   * otherwise. Throw an exception if the reference count is too high.
   *
   * @param target
   *          the target count
   * @return {@code true} if the property value is erroneous, {@code false}
   *         if it is ok
   */
  synchronized final boolean _assert(final int target) {
    final int rc;
    rc = this.m_refCount;
    if (rc != target) {
      this.m_dead = true;
      if (rc > target) {
        throw new IllegalStateException(((((((//
            "The reference count ") + rc) + //$NON-NLS-1$
            " of property '") + this.m_propertyName) + //$NON-NLS-1$
            "' is higher than the maximum count ") + target) + //$NON-NLS-1$
            '.');
      }
      return false;
    }
    return this.m_dead;
  }

}
