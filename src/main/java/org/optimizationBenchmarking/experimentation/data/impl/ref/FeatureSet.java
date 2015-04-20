package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.concurrent.atomic.AtomicInteger;

import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;

/** The feature set. */
public final class FeatureSet extends
    _PropertySet<FeatureValue, Feature, FeatureSetting> implements
    IFeatureSet {

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
  FeatureSet(final Property<?>[] data) {
    super(data);
    this.m_id = FeatureSet.ID_COUNTER.getAndIncrement();
  }

  /** {@inheritDoc} */
  @Override
  final FeatureSetting _createSetting(final PropertyValue<?>[] values,
      final boolean isGeneralized) {
    return new FeatureSetting(values, isGeneralized);
  }
}
