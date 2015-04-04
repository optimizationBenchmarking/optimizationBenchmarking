package org.optimizationBenchmarking.experimentation.data;

/**
 * A parameter value.
 */
public final class ParameterValue extends PropertyValue<Parameter> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create the parameter value
   * 
   * @param name
   *          the string representation of this value
   * @param desc
   *          the description
   * @param value
   *          the value
   */
  ParameterValue(final String name, final String desc, final Object value) {
    super(name, desc, value);
  }

  /**
   * Is this parameter value unspecified?
   * 
   * @return {@code true} if and only if
   *         <code>this.{@link #getValue() getValue()}=={@link org.optimizationBenchmarking.experimentation.data._PropertyValueUnspecified#INSTANCE}</code>
   *         , {@code false} otherwise
   * @see #isUnspecified(Object)
   */
  public final boolean isUnspecified() {
    return (this.m_value == _PropertyValueUnspecified.INSTANCE);
  }

  /**
   * Check whether a property value is unspecified
   * 
   * @param value
   *          the value
   * @return {@code true} if the value is unspecified, {@code false} if it
   *         is not
   * @see #isGeneralized(Object)
   * @see #isUnspecified()
   */
  public static final boolean isUnspecified(final Object value) {
    return _PropertyValueUnspecified.INSTANCE.equals(value);
  }
}
