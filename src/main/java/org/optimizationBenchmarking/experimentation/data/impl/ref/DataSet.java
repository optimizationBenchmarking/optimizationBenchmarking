package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * A set of data elements with a unique ID.
 * </p>
 * 
 * @param <DT>
 *          the type
 */
public class DataSet<DT extends Comparable<?>> extends _IDObject {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the data elements */
  final ArraySetView<DT> m_data;

  /**
   * Create a data set with the given data
   * 
   * @param owner
   *          the owning element, or {@code null} if none needs to be set
   * @param data
   *          the data
   */
  @SuppressWarnings("unchecked")
  DataSet(final DataElement owner, final DT... data) {
    this(data, true, false, false);
  }

  /**
   * instantiate
   * 
   * @param data
   *          the data of the set
   * @param clone
   *          should we clone the data?
   * @param sort
   *          should we sort the data?
   * @param own
   *          should we own the elements?
   */
  DataSet(final DT[] data, final boolean clone, final boolean sort,
      final boolean own) {
    super();

    final DT[] mdata;
    int index1, index2;

    if (data == null) {
      throw new IllegalArgumentException("Data must not be null."); //$NON-NLS-1$
    }

    if (data.length <= 0) {
      throw new IllegalArgumentException(//
          TextUtils.className(this.getClass())//
              + " must contain at least one element."); //$NON-NLS-1$
    }

    mdata = (clone ? data.clone() : data);

    if (sort) {
      Arrays.sort(mdata);
    }

    index1 = 0;
    for (final DT element1 : mdata) {
      if (own) {
        this._setOwnerOfElement(element1, index1);
      }
      this._validateSingleElement(element1, index1, own);
      for (index2 = index1; (--index2) >= 0;) {
        this._validateElementPair(mdata[index2], element1);
      }
      index1++;
    }

    this.m_data = new ArraySetView<>(mdata);
  }

  /**
   * Obtain the data
   * 
   * @return the data array
   */
  public final ArraySetView<DT> getData() {
    return this.m_data;
  }

  /**
   * Mark {@code element} as owned by this element and located at
   * {@code index}
   * 
   * @param element
   *          the owned element
   * @param index
   *          the element index
   */
  void _setOwnerOfElement(final DT element, final int index) {
    throw new UnsupportedOperationException(//
        TextUtils.className(this.getClass())
            + (((" does not know how to become an owner of '" + //$NON-NLS-1$
            element) + "' at index ") + index) + '.'); //$NON-NLS-1$
  }

  /**
   * Validate a single element to be included in this set. This method is
   * really thorough in investigating any potential error. We are as strict
   * as possible when it comes to creating our experiment evaluation data
   * structures, since any mistake may have severe impact on the evaluation
   * results we can get...
   * 
   * @param element
   *          the single element to validate
   * @param index
   *          the index at which the element is located
   * @param shouldWeBeOwner
   *          should we be the owner of the element?
   */
  void _validateSingleElement(final DT element, final int index,
      final boolean shouldWeBeOwner) {
    if (element == null) {
      throw new IllegalArgumentException(//
          "No element in an instance of " + //$NON-NLS-1$
              TextUtils.className(this.getClass()) + " can be null."); //$NON-NLS-1$
    }
    if (element == this) {
      throw new IllegalArgumentException("An instance of " + //$NON-NLS-1$
          TextUtils.className(this.getClass()) + //
          " cannot contain itself!"); //$NON-NLS-1$
    }
    if (element.getClass() == this.getClass()) {
      throw new IllegalArgumentException(((("An instance of " + //$NON-NLS-1$
          TextUtils.className(this.getClass())) + //
          " cannot contain another instance of ") + //$NON-NLS-1$
          TextUtils.className(this.getClass())) + '.');
    }
  }

  /**
   * Validate two supposedly different elements to be included in this set.
   * Element {@code before} is located before element {@code after} in this
   * set.
   * 
   * @param before
   *          the first element to validate
   * @param after
   *          the second element to validate
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  void _validateElementPair(final DT before, final DT after) {
    int cra, crb;

    if (before == after) {
      throw new IllegalArgumentException("No two elements in a " + //$NON-NLS-1$
          TextUtils.className(this.getClass())
          + " can be the same (referentially speaking), but '" + before + //$NON-NLS-1$
          "' is included twice!"); //$NON-NLS-1$
    }

    if (before.equals(after) || after.equals(before)) {
      throw new IllegalArgumentException("No two elements in a " + //$NON-NLS-1$
          TextUtils.className(this.getClass())
          + " can be equal, but '" + before + //$NON-NLS-1$
          "' and '" + after + //$NON-NLS-1$
          "' are."); //$NON-NLS-1$
    }

    if (before.getClass() != after.getClass()) {
      throw new IllegalArgumentException((((((((((//
          "The elements of an " + //$NON-NLS-1$
          TextUtils.className(this.getClass())) + " must belong to the same class, but element '") + before) + //$NON-NLS-1$
          "' belongs to class ") + //$NON-NLS-1$
          TextUtils.className(before.getClass())) + " whereas element '") + //$NON-NLS-1$
          after) + "' belongs to class ") + //$NON-NLS-1$
          TextUtils.className(after.getClass())) + '.');
    }

    cra = ((Comparable) before).compareTo(after);
    crb = ((Comparable) after).compareTo(before);
    if ((cra > 0) || (crb < 0)) {
      throw new IllegalStateException(
          "Element '" + before + //$NON-NLS-1$
              "' should come after element '" //$NON-NLS-1$
              + after
              + "' according to the " + //$NON-NLS-1$
              TextUtils.className(Comparable.class)
              + //
              "-implementations of these elements, but it comes before in an instance of" + //$NON-NLS-1$
              TextUtils.className(this.getClass()) + '.');
    }

    if ((cra == 0) || (crb == 0)) {
      throw new IllegalStateException("One of the " + //$NON-NLS-1$
          TextUtils.className(Comparable.class) + //
          "-implementations of '" + before + //$NON-NLS-1$
          "' or '" + after + //$NON-NLS-1$
          "' returns 0, which is not permitted in an instance of " + //$NON-NLS-1$
          TextUtils.className(this.getClass()) + '.');
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  int _compareTo(final _IDObject o) {
    final ArraySetView a, b;
    final int s1, s2, min;
    Object x, y;
    int i, r;

    if (o instanceof DataSet) {
      a = this.m_data;
      b = ((DataSet) o).m_data;

      if (a == b) {
        return 0;
      }

      if (a == null) {
        return 1;
      }
      if (b == null) {
        return (-1);
      }

      s1 = a.size();
      s2 = b.size();
      min = Math.min(s1, s2);

      for (i = 0; i < min; i++) {
        x = a.get(i);
        y = b.get(i);

        if (x == y) {
          continue;
        }
        if (x == null) {
          return 1;
        }
        if (y == null) {
          return (-1);
        }

        if (x instanceof Comparable) {
          try {
            r = ((Comparable) x).compareTo(y);
            if (r != 0) {
              return r;
            }
          } catch (final Throwable t) {//
          }
        }

        if (y instanceof Comparable) {
          try {
            r = ((Comparable) y).compareTo(x);
            if (r != 0) {
              return (-r);
            }
          } catch (final Throwable t) {//
          }
        }
      }

      if (s1 < s2) {
        return (-1);
      }
      if (s1 > s2) {
        return 1;
      }
    }

    return super._compareTo(o);
  }

  /** Get the owning element */
  @Override
  public DataElement getOwner() {
    return this.m_owner;
  }
}
