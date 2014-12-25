package org.optimizationBenchmarking.experimentation.evaluation.data;

/**
 * A feature value.
 */
public final class FeatureValue extends _PropertyValue<Feature> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create the feature value
   * 
   * @param name
   *          the string representation of this value
   * @param desc
   *          the description
   * @param value
   *          the value
   */
  FeatureValue(final String name, final String desc, final Object value) {
    super(name, desc, value);
  }

  /**
   * Is this feature value unspecified?
   * 
   * @return {@code true} if and only if
   *         <code>this.{@link #getValue() getValue()}=={@link org.optimizationBenchmarking.experimentation.evaluation.data._PropertyValueUnspecified#INSTANCE}</code>
   *         , {@code false} otherwise
   */
  public final boolean isUnspecified() {
    return (this.m_value == _PropertyValueUnspecified.INSTANCE);
  }
}
