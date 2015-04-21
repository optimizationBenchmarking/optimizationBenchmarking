package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet}
 * interface.
 */
public class AbstractFeatureSet extends
    _AbstractPropertySet<IFeatureSetting> implements IFeatureSet {

  /**
   * Create the abstract feature set. If {@code owner==null}, you must
   * later set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractFeatureSet)}
   * .
   * 
   * @param owner
   *          the owner
   */
  protected AbstractFeatureSet(final IExperimentSet owner) {
    super(owner);
  }

  /**
   * Own an
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeature}
   * .
   * 
   * @param feature
   *          the feature set to own
   */
  protected final void own(final AbstractFeature feature) {
    if (feature == null) {
      throw new IllegalArgumentException(//
          "AbstractFeature to be owned by AbstractFeatureSet cannot be null."); //$NON-NLS-1$
    }
    synchronized (feature) {
      if (feature.m_owner != null) {
        throw new IllegalArgumentException(//
            "AbstractFeature to be owned by AbstractFeatureSet already owned.");//$NON-NLS-1$
      }
      feature.m_owner = this;
    }
  }

  /** {@inheritDoc} */
  @Override
  final IFeatureSetting _createEmpty() {
    return new AbstractFeatureSetting();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArrayListView<? extends IFeature> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IFeature find(final String name) {
    return ((IFeature) (super.find(name)));
  }
}
