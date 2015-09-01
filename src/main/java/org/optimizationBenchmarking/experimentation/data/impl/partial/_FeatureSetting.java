package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.ArrayList;
import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeatureSetting;
import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractNamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** the internal feature setting container */
final class _FeatureSetting extends AbstractFeatureSetting {

  /** the feature list */
  private final ArrayList<_FeatureValue> m_features;

  /** the owning feature set */
  private final _Features m_owner;

  /**
   * create
   *
   * @param owner
   *          the owning features
   */
  _FeatureSetting(final _Features owner) {
    super();
    this.m_owner = owner;
    this.m_features = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Iterator<IFeatureValue> iterator() {
    return ((Iterator) (this.m_features.iterator()));
  }

  /**
   * Set the given feature
   *
   * @param feature
   *          the feature name
   * @param value
   *          the value to set
   * @return the feature value object
   */
  final _FeatureValue _setFeature(final String feature, final Object value) {
    final String useName;
    final _FeatureValue nv;

    useName = AbstractNamedElement.formatName(feature);

    for (final _FeatureValue fvalue : this.m_features) {
      if (EComparison.equals(((_Feature) (fvalue.getOwner())).m_name,
          useName)) {
        return fvalue;
      }
    }

    nv = this.m_owner._getFeatureForName(feature)._getValue(value);
    this.m_features.add(nv);
    return nv;
  }
}
