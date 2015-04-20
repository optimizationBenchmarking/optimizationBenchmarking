package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;

/**
 * A parameter value.
 */
public final class ParameterValue extends PropertyValue<Parameter>
    implements IParameterValue {
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

  /** {@inheritDoc} */
  @Override
  public final boolean isUnspecified() {
    return ParameterValue.isUnspecified(this.m_value);
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
