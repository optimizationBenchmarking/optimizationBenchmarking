package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Arrays;
import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySet;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * A shadow property is basically a shadow of another property with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that property.
 * 
 * @param <OT>
 *          the owner type
 * @param <ST>
 *          the shadow type
 * @param <PVT>
 *          the property value type
 */
abstract class _ShadowProperty<OT extends IPropertySet, ST extends IProperty, PVT extends IPropertyValue>
    extends _ShadowNamedElement<OT, ST> implements IProperty {

  /** the generalized value has not yet been set */
  private static final int NEEDS_GENERALIZED = 1;
  /** the data has not yet been set */
  static final int NEEDS_DATA = (_ShadowProperty.NEEDS_GENERALIZED << 1);

  /** the data */
  private ArrayListView<? extends PVT> m_data;

  /** the general property value type */
  PVT m_general;

  /** the original property to copy from */
  ST m_orig;

  /** the state of shadowing elements to the original property */
  int m_origState;

  /**
   * create the shadow property
   * 
   * @param owner
   *          the owning property set
   * @param shadow
   *          the property to shadow
   * @param selection
   *          the selection of property value
   */
  _ShadowProperty(final OT owner, final ST shadow,
      final Collection<? extends PVT> selection) {
    super(owner, shadow);

    if (selection != null) {
      this.__shadow(selection, true);
      this.m_origState = _ShadowProperty.NEEDS_GENERALIZED;
    } else {
      this.m_origState = (_ShadowProperty.NEEDS_GENERALIZED | _ShadowProperty.NEEDS_DATA);
    }
    this.m_orig = shadow;
  }

  /**
   * shadow the given collection of property values
   * 
   * @param selection
   *          the selection
   * @param canOwn
   *          can we own elements?
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final void __shadow(final Collection<? extends PVT> selection,
      final boolean canOwn) {
    final IPropertyValue[] array;
    PVT set;
    _ShadowPropertyValue spv;
    int i;

    if ((selection == null) || ((i = selection.size()) <= 0)) {
      this.m_data = ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
      return;
    }
    array = new IPropertyValue[i];
    i = 0;
    for (final PVT value : selection) {
      set = value;
      shadow: {
        if (set instanceof _ShadowPropertyValue) {
          spv = ((_ShadowPropertyValue) set);
          if (spv.m_owner == null) {
            if (canOwn) {
              spv.m_owner = this;
              break shadow;
            }
            throw new IllegalArgumentException(//
                "Property value without owner encountered."); //$NON-NLS-1$
          }
        }
        set = this._shadow(value);
      }

      array[i++] = set;
    }

    try {
      Arrays.sort(array);
    } catch (final Throwable tt) {
      // ignore
    }

    this.m_data = new ArrayListView(array);
  }

  /**
   * Shadow a given property value type
   * 
   * @param value
   *          the value
   * @return the shadow copy
   */
  abstract PVT _shadow(final PVT value);

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getPrimitiveType() {
    return this.m_shadowUnpacked.getPrimitiveType();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public synchronized final ArrayListView<? extends PVT> getData() {
    if (this.m_data == null) {
      this.__shadow((ArrayListView) (this.m_orig.getData()), false);
      if ((this.m_origState &= (~_ShadowProperty.NEEDS_DATA)) == 0) {
        this.m_orig = null;
      }
    }
    return this.m_data;
  }

  /** {@inheritDoc} */
  @Override
  public PVT findValue(final Object value) {
    PVT general;

    for (final PVT known : this.getData()) {
      if (known == value) {
        return known;
      }
      if (EComparison.equals(known.getValue(), value)) {
        return known;
      }
    }

    general = this.getGeneralized();
    if ((value == general)
        || (EComparison.equals(general.getValue(), value))) {
      return general;
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final PVT getGeneralized() {
    if (this.m_general == null) {
      this.m_general = this._shadow((PVT) (this.m_orig.getGeneralized()));
      if ((this.m_origState &= (~_ShadowProperty.NEEDS_GENERALIZED)) == 0) {
        this.m_orig = null;
      }
    }
    return this.m_general;
  }
}
