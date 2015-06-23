package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractParameterSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
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
    return (this.__getParameterValue(key) != null);
  }

  /**
   * Get the shadow parameter value for a given key
   *
   * @param key
   *          the key
   * @return the value
   */
  private final ShadowParameterValue __getParameterValue(final Object key) {
    final IParameter useKey;
    IParameter compare;

    if (key instanceof IParameter) {

      if (key instanceof ShadowParameter) {
        useKey = ((ShadowParameter) key).m_shadowUnpacked;
      } else {
        useKey = ((IParameter) key);
      }

      for (final ShadowParameterValue entry : this.m_data) {
        compare = entry.getOwner();
        if (compare instanceof ShadowParameter) {
          compare = ((ShadowParameter) compare).m_shadowUnpacked;
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
    final ShadowParameterValue value;
    value = this.__getParameterValue(key);
    if (value != null) {
      return value.getValue();
    }
    return null;
  }
}
