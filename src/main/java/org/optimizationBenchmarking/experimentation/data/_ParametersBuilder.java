package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * a manager for properties
 */
final class _ParametersBuilder extends
    _PropertyFSMPropertiesBuilder<ParameterValue, Parameter, ParameterSet> {

  /**
   * create
   * 
   * @param ctx
   *          the context
   */
  _ParametersBuilder(final _FSM ctx) {
    super(ctx);
  }

  /** {@inheritDoc} */
  @Override
  final ParameterValue _createPropertyValue(final String name,
      final String desc, final Object value) {
    return new ParameterValue(name, desc, value);
  }

  /** {@inheritDoc} */
  @Override
  final Parameter _createProperty(final String name, final String desc,
      final EPrimitiveType primitiveType,
      final _PropertyValue<?>[] values, final boolean hasUnspecified) {
    return new Parameter(
        name,
        desc,
        primitiveType,
        values,//
        (hasUnspecified ? //
        new ParameterValue(
            _PropertyValueUnspecified.NAME,//
            "The unspecified parameter value, used when settings do not specify the value.", //$NON-NLS-1$
            _PropertyValueUnspecified.INSTANCE)//
            : null),//
        new ParameterValue(
            _PropertyValueGeneralized.NAME,//
            "The generalized parameter value which can stand for any other parameter value.", //$NON-NLS-1$
            _PropertyValueGeneralized.INSTANCE)//
    );
  }

  /** {@inheritDoc} */
  @Override
  final ParameterSet _createPropertySet(final _Property<?>[] data) {
    return new ParameterSet(data);
  }

}
