package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A feature. */
public final class Feature extends _Property<FeatureValue> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create
   * 
   * @param name
   *          the feature name
   * @param desc
   *          the description
   * @param primitiveType
   *          the primitive type of the feature, or {@code null} if the
   *          feature is a string feature
   * @param values
   *          the values
   * @param general
   *          the generalized value
   */
  Feature(final String name, final String desc,
      final EPrimitiveType primitiveType,
      final _PropertyValue<?>[] values, final FeatureValue general) {
    super(name, desc, primitiveType, values, general);
  }

  /** {@inheritDoc} */
  @Override
  public final FeatureSet getOwner() {
    return ((FeatureSet) (this.m_owner));
  }
}
