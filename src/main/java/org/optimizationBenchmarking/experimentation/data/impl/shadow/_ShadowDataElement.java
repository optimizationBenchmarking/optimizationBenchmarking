package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * A shadow of a data element.
 * 
 * @param <OT>
 *          the owner type
 * @param <ST>
 *          the shadow type
 */
class _ShadowDataElement<OT extends IDataElement, ST> extends DataElement
    implements Comparable<_ShadowDataElement<OT, ST>> {

  /** the owner */
  OT m_owner;

  /** was the shadow delegate computed ? */
  private boolean m_shadowDelegateComputed;

  /** the shadowed object */
  private ST m_shadowDelegate;

  /** the unpacked shadowed object */
  final ST m_shadowUnpacked;

  /**
   * create the shadow element
   * 
   * @param shadow
   *          the object to shadow
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  _ShadowDataElement(final ST shadow) {
    super();

    if (shadow == null) {
      throw new IllegalArgumentException(//
          "Object to shadow cannot be null."); //$NON-NLS-1$
    }

    this.m_shadowDelegate = shadow;
    if (shadow instanceof _ShadowDataElement) {
      this.m_shadowUnpacked = ((ST) (((_ShadowDataElement) shadow).m_shadowUnpacked));
    } else {
      this.m_shadowUnpacked = shadow;
    }
  }

  /**
   * Get the delegate to which all attribute computations can be forwarded
   * to, or {@code null} if no forwarding should take place
   * 
   * @param shadow
   *          the shadow
   * @return the delegate
   */
  boolean _canDelegateAttributesTo(final ST shadow) {
    return false;
  }

  /**
   * Get the shadow delegate
   * 
   * @return the shadow delegate
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private synchronized final ST __getAttributeDelegate() {
    ST delegate, best;

    delegate = this.m_shadowDelegate;
    if (this.m_shadowDelegateComputed) {
      return delegate;
    }
    this.m_shadowDelegateComputed = true;

    best = null;
    if (this._canDelegateAttributesTo(delegate)) {
      best = delegate;
      if (delegate instanceof _ShadowDataElement) {
        delegate = ((ST) (((_ShadowDataElement) delegate)
            .__getAttributeDelegate()));
        if (delegate != null) {
          best = delegate;
        }
      }
    }

    this.m_shadowDelegate = best;
    return best;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final <XDT extends IDataElement, RT> RT getAttribute(
      final Attribute<XDT, RT> attribute) {
    final ST delegate;

    delegate = this.__getAttributeDelegate();

    if (delegate != null) {
      return DataElement.delegateGetAttribute(((XDT) (delegate)),
          attribute);
    }
    return super.getAttribute(attribute);
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(final _ShadowDataElement<OT, ST> o) {
    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }
    return EComparison.compareObjects(this.m_shadowUnpacked,
        o.m_shadowUnpacked);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public boolean equals(final Object o) {
    final _ShadowDataElement sde;
    final Object a, b;

    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof _ShadowDataElement) {
      a = this.__getAttributeDelegate();
      if (a != null) {
        sde = ((_ShadowDataElement) o);
        b = sde.__getAttributeDelegate();
        if (a == b) {
          return EComparison.equals(this.m_shadowUnpacked,
              ((_ShadowDataElement) o).m_shadowUnpacked);
        }
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return this.m_shadowUnpacked.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final OT getOwner() {
    return this.m_owner;
  }
}
