package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A shadow of a data element.
 *
 * @param <OT>
 *          the owner type
 * @param <ST>
 *          the shadow type
 */
class _ShadowDataElement<OT extends IDataElement, ST extends IDataElement>
    extends DataElement implements Comparable<_ShadowDataElement<OT, ST>> {

  /** the owner */
  OT m_owner;

  /** was the shadow delegate computed ? */
  private boolean m_shadowDelegateComputed;

  /** the shadowed object */
  ST m_shadowDelegate;

  /** the unpacked shadowed object */
  final ST m_shadowUnpacked;

  /**
   * create the shadow element
   *
   * @param owner
   *          the owner
   * @param shadow
   *          the object to shadow
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  _ShadowDataElement(final OT owner, final ST shadow) {
    super();

    this.m_owner = owner;

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
   * Set the owner of this element
   *
   * @param owner
   *          the owner
   */
  protected synchronized final void setOwner(final OT owner) {
    if (owner == null) {
      throw new IllegalArgumentException(
          "Cannot set null owner to an instance of " + //$NON-NLS-1$
              TextUtils.className(this.getClass()));
    }
    if (this.m_owner != null) {
      throw new IllegalStateException(//
          "Instance of "//$NON-NLS-1$
              + TextUtils.className(this.getClass()) + //
              " already has an owner.");//$NON-NLS-1$
    }
    this.m_owner = owner;
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
  synchronized final ST _getAttributeDelegate() {
    ST delegate, next, best;

    delegate = this.m_shadowDelegate;
    if (this.m_shadowDelegateComputed) {
      return delegate;
    }
    this.m_shadowDelegateComputed = true;

    findBest: {
      if (this._canDelegateAttributesTo(this.m_shadowUnpacked)) {
        best = this.m_shadowUnpacked;
        break findBest;
      }

      if (delegate instanceof _ShadowDataElement) {
        next = ((ST) (((_ShadowDataElement) delegate)
            ._getAttributeDelegate()));
        if ((next != null) && this._canDelegateAttributesTo(next)) {
          best = next;
          break findBest;
        }
      }

      if (this._canDelegateAttributesTo(delegate)) {
        best = delegate;
      } else {
        best = null;
      }
    }

    this.m_shadowDelegate = best;
    return best;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final <XDT extends IDataElement, RT> RT getAttribute(
      final Attribute<XDT, RT> attribute, final Logger logger) {
    final ST delegate;

    delegate = this._getAttributeDelegate();

    if (delegate != null) {
      return DataElement.delegateGetAttribute(((XDT) (delegate)),
          attribute, logger);
    }
    return super.getAttribute(attribute, logger);
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

    if ((this.m_shadowUnpacked == o.m_shadowUnpacked) || //
        (this == o.m_shadowUnpacked) || //
        (this.m_shadowUnpacked == o) || //
        (this == o.m_shadowDelegate) || //
        (this.m_shadowDelegate == o)) {
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
      a = this._getAttributeDelegate();
      if (a != null) {
        sde = ((_ShadowDataElement) o);
        b = sde._getAttributeDelegate();
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
