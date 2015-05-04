package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A feature. */
public final class Feature extends Property<FeatureValue> implements
    IFeature {

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
      final EPrimitiveType primitiveType, final PropertyValue<?>[] values,
      final FeatureValue general) {
    super(name, desc, primitiveType, values, general);
  }

  /** {@inheritDoc} */
  @Override
  public final FeatureSet getOwner() {
    return ((FeatureSet) (this.m_owner));
  }

  /**
   * Get the value of this property for the given
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Instance}
   *
   * @param instance
   *          the instance to get the property value of
   * @return the property value
   */
  public final Object get(final Instance instance) {
    if (instance == null) {
      throw new IllegalArgumentException(//
          "Cannot get value of parameter '"//$NON-NLS-1$
              + this.getName() + //
              "' for null instance."); //$NON-NLS-1$
    }
    return instance.getFeatureSetting().get(this);
  }

  /**
   * Get the value of this parameter for the given element
   *
   * @param element
   *          the element to get the parameter value of, must be an
   *          instance, or else {@link IllegalArgumentException}
   * @return the property value
   * @throws IllegalArgumentException
   *           if {@code element} is not an instance of
   *           {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Instance}
   */
  @Override
  public final Object get(final DataElement element) {
    if (element instanceof Instance) {
      return this.get((Instance) element);
    }
    throw new IllegalArgumentException(//
        "Cannot get value of parameter '" //$NON-NLS-1$
            + this.getName() + //
            "' for data element '"//$NON-NLS-1$
            + element + //
            "' - only instances can have parameter values.");//$NON-NLS-1$
  }
}
