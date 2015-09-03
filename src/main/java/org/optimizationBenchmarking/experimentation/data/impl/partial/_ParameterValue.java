package org.optimizationBenchmarking.experimentation.data.impl.partial;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractParameterValue;

/** the internal parameter value class */
final class _ParameterValue extends AbstractParameterValue {

  /** the value */
  final Object m_value;

  /** the description */
  String m_description;

  /**
   * Create the parameter value
   *
   * @param parameter
   *          the parameter
   * @param value
   *          the parameter value
   */
  _ParameterValue(final _Parameter parameter, final Object value) {
    super(parameter);
    this.m_value = value;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_description;
  }

  /** {@inheritDoc} */
  @Override
  public final Object getValue() {
    return this.m_value;
  }
}
