package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeatureSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
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
    return (this.__getFeatureValue(key) != null);
  }

  /**
   * Get the shadow feature value for a given key
   *
   * @param key
   *          the key
   * @return the value
   */
  private final ShadowFeatureValue __getFeatureValue(final Object key) {
    final IFeature useKey;
    IFeature compare;

    if (key instanceof IFeature) {

      if (key instanceof ShadowFeature) {
        useKey = ((ShadowFeature) key).m_shadowUnpacked;
      } else {
        useKey = ((IFeature) key);
      }

      for (final ShadowFeatureValue entry : this.m_data) {
        compare = entry.getOwner();
        if (compare instanceof ShadowFeature) {
          compare = ((ShadowFeature) compare).m_shadowUnpacked;
        }
        if (EComparison.equals(compare, useKey)) {
          return entry;
        }
      }
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final Object get(final Object key) {
    final ShadowFeatureValue value;
    value = this.__getFeatureValue(key);
    if (value != null) {
      return value.getValue();
    }
    return null;
  }
}
