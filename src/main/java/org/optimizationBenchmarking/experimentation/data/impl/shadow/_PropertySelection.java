package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySet;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;

/**
 * a property selection
 *
 * @param <PST>
 *          the property set type
 * @param <PT>
 *          the property type
 * @param <PVT>
 *          the property value type
 */
abstract class _PropertySelection<PST extends IPropertySet, PT extends IProperty, PVT extends IPropertyValue>
extends _MappedSelection<PST, PT, PVT> {

  /**
   * create the property selection
   *
   * @param set
   *          the property set to select from
   */
  _PropertySelection(final PST set) {
    super(set);
  }

  /** {@inheritDoc} */
  @Override
  final boolean _checkCanAddSub(final PVT value) {
    return (super._checkCanAddSub(value) && _PropertyValueSelection
        ._canAdd(value));
  }
}
