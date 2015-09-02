package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeatureSet;
import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractNamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** the feature set */
final class _Features extends AbstractFeatureSet {

  /** the internal list view with the features */
  private ArrayListView<IFeature> m_features;
  /** the list of features */
  private final ArrayList<_Feature> m_featureList;
  /** do we need a new feature? */
  boolean m_needsNew;

  /**
   * create
   *
   * @param owner
   *          the owner
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  _Features(final _Experiments owner) {
    super(owner);
    this.m_features = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    this.m_featureList = new ArrayList<>();
    this.m_needsNew = true;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final ArrayListView<IFeature> getData() {
    if (this.m_features == null) {
      this.m_features = ((ArrayListView) (ArrayListView
          .collectionToView(this.m_featureList)));
    }
    return this.m_features;
  }

  /**
   * get the feature
   *
   * @param forceNew
   *          do we need a new one
   * @return the feature
   */
  final _Feature _getFeature(final boolean forceNew) {
    final _Feature feature;
    final int size;

    size = this.m_featureList.size();
    if (forceNew || this.m_needsNew || (size <= 0)) {
      feature = new _Feature(this);
      this.m_featureList.add(feature);
      this.m_needsNew = false;
      this.m_features = null;
      return feature;
    }
    return this.m_featureList.get(size - 1);
  }

  /**
   * Get a feature of the given name
   *
   * @param name
   *          the name
   * @return the feature
   */
  final _Feature _getFeatureForName(final String name) {
    final String useName;
    _Feature nfeature;

    useName = AbstractNamedElement.formatName(name);
    for (final _Feature feature : this.m_featureList) {
      if (EComparison.equals(feature.m_name, useName)) {
        return feature;
      }
    }

    nfeature = this._getFeature(false);
    if (nfeature.m_name != null) {
      nfeature = this._getFeature(true);
    }
    nfeature.m_name = useName;
    return nfeature;
  }
}
