package org.optimizationBenchmarking.experimentation.data.impl.partial;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeatureValue;

/** the internal feature value class */
final class _FeatureValue extends AbstractFeatureValue {

  /** the value */
  final Object m_value;

  /** the description */
  String m_description;

  /**
   * Create the feature value
   *
   * @param feature
   *          the feature
   * @param value
   *          the feature value
   */
  _FeatureValue(final _Feature feature, final Object value) {
    super(feature);
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
