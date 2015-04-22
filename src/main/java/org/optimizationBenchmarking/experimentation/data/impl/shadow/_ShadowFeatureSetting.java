package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeatureSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** A shadow feature setting */
final class _ShadowFeatureSetting extends AbstractFeatureSetting {

  /** the set */
  private final ArraySetView<ShadowFeatureValue> m_data;

  /**
   * create
   * 
   * @param data
   *          the data
   */
  _ShadowFeatureSetting(final ArraySetView<ShadowFeatureValue> data) {
    super();
    this.m_data = data;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArraySetView entrySet() {
    return this.m_data;
  }

  /** {@inheritDoc} */
  @Override
  public final int size() {
    return this.m_data.size();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean containsValue(final Object value) {
    for (final ShadowFeatureValue entry : this.m_data) {
      if (EComparison.equals(entry, value) || //
          EComparison.equals(entry.getValue(), value)) {
        return true;
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean containsKey(final Object key) {
    for (final ShadowFeatureValue entry : this.m_data) {
      if (EComparison.equals(entry.getOwner(), key)) {
        return true;
      }
    }
    return false;
  }
}
