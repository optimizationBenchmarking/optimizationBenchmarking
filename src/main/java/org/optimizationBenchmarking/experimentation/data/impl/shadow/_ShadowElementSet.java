package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Arrays;
import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A shadow element set is basically a shadow of another element set with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that element set.
 * 
 * @param <OT>
 *          the owner type
 * @param <ST>
 *          the shadow type
 * @param <PT>
 *          the element type
 */
abstract class _ShadowElementSet<OT extends IDataElement, ST extends IElementSet, //
PT extends IDataElement> extends //
    _ShadowDataElement<OT, ST> implements IElementSet {

  /** the data */
  private ArrayListView<? extends PT> m_data;

  /** the original property to copy from */
  private ST m_orig;

  /**
   * create the shadow element set
   * 
   * @param owner
   *          the owning element
   * @param shadow
   *          the element set to shadow
   * @param selection
   *          the selection of elements
   */
  _ShadowElementSet(final OT owner, final ST shadow,
      final Collection<? extends PT> selection) {
    super(owner, shadow);

    if (selection != null) {
      this.__shadow(selection, true);
    } else {
      this.m_orig = shadow;
    }
  }

  /**
   * shadow the given collection of properties
   * 
   * @param selection
   *          the selection
   * @param canOwn
   *          can we own the elements?
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final void __shadow(final Collection<? extends PT> selection,
      final boolean canOwn) {
    final IDataElement[] array;
    PT set;
    _ShadowProperty spv;
    int i;

    if ((selection == null) || ((i = selection.size()) <= 0)) {
      this.m_data = ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
      return;
    }
    array = new IProperty[i];
    i = 0;
    for (final PT value : selection) {
      set = value;
      shadow: {
        if (set instanceof _ShadowProperty) {
          spv = ((_ShadowProperty) set);
          if (spv.m_owner == null) {
            if (canOwn) {
              spv.m_owner = this;
              break shadow;
            }
            throw new IllegalArgumentException(//
                "Element without owner encountered."); //$NON-NLS-1$
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
   * Shadow a given element
   * 
   * @param element
   *          the element
   * @return the shadow copy
   */
  abstract PT _shadow(final PT element);

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public synchronized final ArrayListView<? extends PT> getData() {
    if (this.m_data == null) {
      this.__shadow((ArrayListView) (this.m_orig.getData()), false);
      this.m_orig = null;
    }
    return this.m_data;
  }

}
