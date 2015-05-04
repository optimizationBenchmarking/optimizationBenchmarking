package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IFeature}
 * interface.
 */
public class AbstractFeature extends AbstractProperty implements IFeature {

  /** the owner */
  IFeatureSet m_owner;

  /**
   * Create the abstract feature. If {@code owner==null}, you must later
   * set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeatureSet#own(AbstractFeature)}
   * .
   *
   * @param owner
   *          the owner
   */
  protected AbstractFeature(final IFeatureSet owner) {
    super();
    this.m_owner = owner;
  }

  /**
   * Own an
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeatureValue}
   * .
   *
   * @param featureValue
   *          the feature set to own
   */
  protected final void own(final AbstractFeatureValue featureValue) {
    if (featureValue == null) {
      throw new IllegalArgumentException(//
          "AbstractFeatureValue to be owned by AbstractFeature cannot be null."); //$NON-NLS-1$
    }
    synchronized (featureValue) {
      if (featureValue.m_owner != null) {
        throw new IllegalArgumentException(//
            "AbstractFeatureValue to be owned by AbstractFeature already owned.");//$NON-NLS-1$
      }
      featureValue.m_owner = this;
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ArrayListView<? extends IFeatureValue> getData() {
    return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IFeatureValue findValue(final Object value) {
    return ((IFeatureValue) (super.findValue(value)));
  }

  /** {@inheritDoc} */
  @Override
  public IFeatureValue getGeneralized() {
    return ((IFeatureValue) (super.getGeneralized()));
  }

  /** {@inheritDoc} */
  @Override
  public final IFeatureSet getOwner() {
    return this.m_owner;
  }

}
