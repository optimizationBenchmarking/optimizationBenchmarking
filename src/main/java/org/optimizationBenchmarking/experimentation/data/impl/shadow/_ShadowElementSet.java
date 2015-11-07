package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Arrays;
import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.text.TextUtils;

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
  ArrayListView<? extends PT> m_data;

  /** the original property to copy from */
  ST m_orig;

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

    this.m_orig = shadow;
    if (selection != null) {
      this.__shadow(selection, true);
    }
  }

  /**
   * Discard the original object after the goal has been achieved.
   */
  void _checkDiscardOrig() {
    this.m_orig = null;
  }

  /**
   * shadow the given collection of elements
   *
   * @param selection
   *          the selection
   * @param canOwn
   *          can we own the elements?
   */
  @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
  private final void __shadow(final Collection<? extends PT> selection,
      final boolean canOwn) {
    final IDataElement[] array;
    final int size;
    PT set;
    _ShadowDataElement spv;
    int i;

    if ((selection == null) || ((size = selection.size()) <= 0)) {
      this.m_data = ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
    } else {
      array = new IDataElement[size];
      i = 0;
      for (final PT value : selection) {
        if (value == null) {
          throw new IllegalArgumentException(//
              "Null value in selection collection."); //$NON-NLS-1$
        }

        set = value;
        shadow: {
          if (set instanceof _ShadowDataElement) {
            spv = ((_ShadowDataElement) set);
            synchronized (spv) {
              if (spv.m_owner == null) {
                if (canOwn) {
                  spv.m_owner = this;
                  break shadow;
                }
                throw new IllegalArgumentException(//
                    "Instance of " //$NON-NLS-1$
                        + TextUtils.className(spv.getClass()) + //
                        "without owner encountered."); //$NON-NLS-1$
              }
            }
          }
          set = this._shadow(value);
          if (set == null) {
            throw new IllegalStateException(//
                "Element shadow cannot be null.");//$NON-NLS-1$
          }
        }

        if (set.getOwner() != this) {
          throw new IllegalStateException(//
              "Can only have elements in the shadow collection which belong to the owning object.");//$NON-NLS-1$
        }

        array[i++] = set;
      }

      if (i != size) {
        throw new IllegalStateException(//
            "Immutable collection has gotten shorter?");//$NON-NLS-1$
      }

      try {
        Arrays.sort(array);
      } catch (final Throwable tt) {
        // ignore
      }

      this.m_data = new ArrayListView(array);
    }
    this._checkDiscardOrig();
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
    }
    return this.m_data;
  }

}
