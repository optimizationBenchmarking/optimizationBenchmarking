package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Map;

import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * A shadow property value is basically a shadow of another property value
 * with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that property value.
 * 
 * @param <OT>
 *          the owner type
 * @param <ST>
 *          the shadow type
 */
class _ShadowPropertyValue<OT extends IProperty, ST extends IPropertyValue>
    extends _ShadowNamedElement<OT, ST> implements IPropertyValue,
    Map.Entry<IProperty, Object> {

  /**
   * create the shadow property value
   * 
   * @param owner
   *          the owning property
   * @param shadow
   *          the property value to shadow
   */
  _ShadowPropertyValue(final OT owner, final ST shadow) {
    super(owner, shadow);
  }

  /** {@inheritDoc} */
  @Override
  public final Object getValue() {
    return this.m_shadowUnpacked.getValue();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isGeneralized() {
    return this.m_shadowUnpacked.isGeneralized();
  }

  /** {@inheritDoc} */
  @Override
  public final IProperty getKey() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @Override
  public final Object setValue(final Object value) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final _ShadowDataElement<OT, ST> o) {
    final IPropertyValue pv;
    int res;

    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }

    if ((this.m_shadowUnpacked == o.m_shadowUnpacked) || //
        (this == o.m_shadowUnpacked) || //
        (this.m_shadowUnpacked == o) || //
        (this == o.m_shadowDelegate) || //
        (this.m_shadowDelegate == o)) {
      return 0;
    }

    findWayToCompare: {
      if (o instanceof _ShadowPropertyValue) {
        res = EComparison.compareObjects(//
            this.m_shadowUnpacked.getOwner(),//
            o.m_shadowUnpacked.getOwner());
        if (res != 0) {
          return res;
        }

        break findWayToCompare;
      }

      if (o instanceof IPropertyValue) {
        pv = ((IPropertyValue) o);

        res = EComparison.compareObjects(//
            this.m_owner,//
            pv.getOwner());
        if (res != 0) {
          return res;
        }

        res = EComparison.compareObjects(this.m_shadowUnpacked.getValue(),
            pv.getValue());
        if (res != 0) {
          return res;
        }
      }
    }

    return EComparison.compareObjects(this.m_shadowUnpacked,
        o.m_shadowUnpacked);
  }
}
