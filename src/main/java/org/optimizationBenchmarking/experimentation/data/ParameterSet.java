package org.optimizationBenchmarking.experimentation.data;

import java.util.concurrent.atomic.AtomicInteger;

/** The parameter set. */
public final class ParameterSet extends
    _PropertySet<ParameterValue, Parameter, ParameterSetting> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** an atomic id counter */
  private static final AtomicInteger ID_COUNTER = new AtomicInteger();

  /**
   * create
   * 
   * @param data
   *          the instances
   */
  ParameterSet(final _Property<?>[] data) {
    super(data);
    this.m_id = ParameterSet.ID_COUNTER.getAndIncrement();
  }

  /** {@inheritDoc} */
  @Override
  final ParameterSetting _createSetting(final _PropertyValue<?>[] values,
      final boolean isGeneralized) {
    return new ParameterSetting(values, isGeneralized);
  }
}
