package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;

/**
 * an internal class managing property values
 * 
 * @param <PT>
 *          the property type
 * @param <PVT>
 *          the property value type
 */
abstract class _PropertyValueSelection<PT extends IProperty, PVT extends IPropertyValue>
    extends _PlainSelection<PT, PVT> {

  /**
   * create the property value selection
   * 
   * @param property
   *          the original property
   */
  _PropertyValueSelection(final PT property) {
    super(property);
  }

  /**
   * Check whether a property value should be ignored
   * 
   * @param value
   *          the value
   * @return {@code true} if it should be ignored
   */
  static final boolean _canAdd(final IPropertyValue value) {
    if (value == null) {
      throw new IllegalArgumentException(//
          "Property value cannot be null."); //$NON-NLS-1$
    }
    if (value.isGeneralized() || //
        ((value instanceof IParameterValue) && //
        (((IParameterValue) value).isUnspecified()))) {
      return false;
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _checkCanAdd(final PVT value) {
    if (super._checkCanAdd(value)) {
      return _PropertyValueSelection._canAdd(value);
    }
    return false;
  }
}
