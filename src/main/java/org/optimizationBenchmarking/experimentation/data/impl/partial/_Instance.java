package org.optimizationBenchmarking.experimentation.data.impl.partial;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstance;

/**
 * An internal, modifiable implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance}
 * interface.
 */
final class _Instance extends AbstractInstance {

  /** the name */
  String m_name;
  /** the description */
  String m_description;

  /** the feature setting */
  private _FeatureSetting m_setting;

  /**
   * Create the abstract instance.
   *
   * @param owner
   *          the owner
   */
  _Instance(final _Instances owner) {
    super(owner);
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

  /** {@inheritDoc} */
  @Override
  public final _FeatureSetting getFeatureSetting() {
    if (this.m_setting == null) {
      this.m_setting = new _FeatureSetting(//
          ((_Experiments) (((_Instances) (this.getOwner())).//
              getOwner())).getFeatures());
    }
    return this.m_setting;
  }
}
