package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue}
 * interface.
 */
public class AbstractFeatureValue extends AbstractPropertyValue implements
IFeatureValue {

  /** the owning feature */
  IFeature m_owner;

  /**
   * Create the abstract feature value. If {@code owner==null}, you must
   * later set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractFeature#own(AbstractFeatureValue)}
   * .
   *
   * @param owner
   *          the owner
   */
  protected AbstractFeatureValue(final IFeature owner) {
    super();
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  public final IFeature getOwner() {
    return this.m_owner;
  }
}
