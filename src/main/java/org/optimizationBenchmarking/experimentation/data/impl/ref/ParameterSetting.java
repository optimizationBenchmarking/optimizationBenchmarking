package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;

/** a concrete parameter setting */
public final class ParameterSetting extends
    _PropertySetting<ParameterValue, Parameter> implements
    IParameterSetting {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create
   * 
   * @param params
   *          the parameter values
   * @param isGeneral
   *          is this setting generalized?
   */
  ParameterSetting(final PropertyValue<?>[] params, final boolean isGeneral) {
    super(params, isGeneral);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArrayIterator<IParameterValue> iterator() {
    return new ArrayIterator(this.m_values);
  }

}
