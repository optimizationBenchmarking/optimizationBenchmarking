package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * a manager for properties
 */
final class _FeaturesBuilder extends
_PropertyFSMPropertiesBuilder<FeatureValue, Feature, FeatureSet> {

  /**
   * create
   *
   * @param ctx
   *          the context
   */
  _FeaturesBuilder(final _FSM ctx) {
    super(ctx);
  }

  /** {@inheritDoc} */
  @Override
  final FeatureValue _createPropertyValue(final String name,
      final String desc, final Object value) {
    return new FeatureValue(name, desc, value);
  }

  /** {@inheritDoc} */
  @Override
  final Feature _createProperty(final String name, final String desc,
      final EPrimitiveType primitiveType, final PropertyValue<?>[] values,
      final boolean hasUnspecified) {
    if (hasUnspecified) {
      throw new IllegalArgumentException(//
          "Instance features cannot be unspecified."); //$NON-NLS-1$
    }
    return new Feature(
        name,
        desc,
        primitiveType,
        values,
        new FeatureValue(
            _PropertyValueGeneralized.NAME,
            "The generalized feature value which can stand for any other feature value.", //$NON-NLS-1$
            _PropertyValueGeneralized.INSTANCE));
  }

  /** {@inheritDoc} */
  @Override
  final FeatureSet _createPropertySet(final Property<?>[] data) {
    return new FeatureSet(data);
  }

}
