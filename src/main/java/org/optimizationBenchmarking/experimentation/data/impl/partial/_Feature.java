package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.LinkedHashMap;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** the internal feature class */
final class _Feature extends AbstractFeature {

  /** the feature name */
  String m_name;

  /** the description */
  String m_description;

  /** the feature values */
  private final LinkedHashMap<Object, _FeatureValue> m_values;

  /** the feature values */
  private ArrayListView<_FeatureValue> m_featureValues;

  /**
   * create the feature
   *
   * @param owner
   *          the owner
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  _Feature(final _Features owner) {
    super(owner);
    this.m_featureValues = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    this.m_values = new LinkedHashMap<>();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_description;
  }

  /**
   * create a value based on the provided object
   *
   * @param value
   *          the value object
   * @return the the created value
   */
  final _FeatureValue _getValue(final Object value) {
    _FeatureValue f;

    f = this.m_values.get(value);
    if (f != null) {
      return f;
    }

    f = new _FeatureValue(this, value);
    this.m_values.put(value, f);
    return f;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<? extends IFeatureValue> getData() {
    if (this.m_featureValues == null) {
      this.m_featureValues = ArrayListView.collectionToView(//
          this.m_values.values());
    }
    return this.m_featureValues;
  }
}
