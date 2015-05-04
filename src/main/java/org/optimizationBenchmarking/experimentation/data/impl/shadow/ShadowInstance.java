package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;

/**
 * A shadow instance is basically a shadow of another instance with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that instance.
 */
public class ShadowInstance extends
_ShadowNamedElement<IInstanceSet, IInstance> implements IInstance {

  /** the feature setting */
  private IFeatureSetting m_setting;

  /**
   * create the shadow instance
   *
   * @param owner
   *          the owning instance set
   * @param shadow
   *          the instance to shadow
   */
  ShadowInstance(final IInstanceSet owner, final IInstance shadow) {
    super(owner, shadow);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IFeatureSetting getFeatureSetting() {
    if (this.m_setting == null) {
      this.m_setting = this
          .getOwner()
          .getOwner()
          .getFeatures()
          .createSettingFromValues(
              this.m_shadowUnpacked.getFeatureSetting());
    }
    return this.m_setting;
  }

  /** {@inheritDoc} */
  @Override
  public final Number getUpperBound(final IDimension dim) {
    if (dim instanceof ShadowDimension) {
      return this.m_shadowUnpacked.getUpperBound(//
          ((ShadowDimension) dim).m_shadowUnpacked);
    }
    return this.m_shadowUnpacked.getUpperBound(dim);// let's hope...
  }

  /** {@inheritDoc} */
  @Override
  public final Number getLowerBound(final IDimension dim) {
    if (dim instanceof ShadowDimension) {
      return this.m_shadowUnpacked.getLowerBound(//
          ((ShadowDimension) dim).m_shadowUnpacked);
    }
    return this.m_shadowUnpacked.getLowerBound(dim);// TODO: unpack
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canDelegateAttributesTo(final IInstance shadow) {
    final IInstanceSet insts, others;

    if (this == shadow) {
      return true;
    }

    insts = this.getOwner();
    others = shadow.getOwner();
    if (insts == others) {
      return true;
    }

    if (insts instanceof ShadowInstanceSet) {
      if (((ShadowInstanceSet) insts)._canDelegateAttributesTo(others)) {
        return true;
      }
    }

    return false;
  }
}
