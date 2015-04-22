package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractParameterSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** A shadow parameter setting */
final class _ShadowParameterSetting extends AbstractParameterSetting {

  /** the set */
  private final ArraySetView<ShadowParameterValue> m_data;

  /**
   * create
   * 
   * @param data
   *          the data
   */
  _ShadowParameterSetting(final ArraySetView<ShadowParameterValue> data) {
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
    for (final ShadowParameterValue entry : this.m_data) {
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
    for (final ShadowParameterValue entry : this.m_data) {
      if (EComparison.equals(entry.getOwner(), key)) {
        return true;
      }
    }
    return false;
  }
}
