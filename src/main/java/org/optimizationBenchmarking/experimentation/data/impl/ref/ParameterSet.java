package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.concurrent.atomic.AtomicInteger;

import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;

/** The parameter set. */
public final class ParameterSet extends
    _PropertySet<ParameterValue, Parameter, ParameterSetting> implements
    IParameterSet {

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
  ParameterSet(final Property<?>[] data) {
    super(data);
    this.m_id = ParameterSet.ID_COUNTER.getAndIncrement();
  }

  /** {@inheritDoc} */
  @Override
  final ParameterSetting _createSetting(final PropertyValue<?>[] values,
      final boolean isGeneralized) {
    return new ParameterSetting(values, isGeneralized);
  }
}
