package org.optimizationBenchmarking.utils.math.matrix;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator iterating over matrix columns.
 * 
 * @param <OT>
 *          the matrix type
 */
public class MatrixColumnIterator<OT extends IMatrix> extends
    MatrixColumns<OT> implements Iterator<IMatrix> {

  /**
   * create
   * 
   * @param owner
   *          the owning matrix
   */
  public MatrixColumnIterator(final OT owner) {
    super(owner, new int[] { (-1) });
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_cols[0] < (this.m_owner.n() - 1));
  }

  /** {@inheritDoc} */
  @Override
  public final MatrixColumnIterator<OT> next() {
    if ((++this.m_cols[0]) >= this.m_owner.n()) {
      throw new NoSuchElementException(//
          "End of iteration reached: The matrix has only " + //$NON-NLS-1$
              this.m_owner.n() + " columns."); //$NON-NLS-1$
    }

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void remove() {
    throw new UnsupportedOperationException(//
        "Removing of columns is not allowed."); //$NON-NLS-1$
  }
}
