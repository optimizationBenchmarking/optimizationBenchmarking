package org.optimizationBenchmarking.utils.math.matrix;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator iterating over matrix rows.
 * 
 * @param <OT>
 *          the matrix type
 */
public class MatrixRowIterator<OT extends IMatrix> extends MatrixRows<OT>
    implements Iterator<IMatrix> {

  /**
   * create
   * 
   * @param owner
   *          the owning matrix
   */
  public MatrixRowIterator(final OT owner) {
    super(owner, new int[] { (-1) });
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_rows[0] < (this.m_owner.m() - 1));
  }

  /** {@inheritDoc} */
  @Override
  public final MatrixRowIterator<OT> next() {
    if ((++this.m_rows[0]) >= this.m_owner.m()) {
      throw new NoSuchElementException(//
          "End of iteration reached: The matrix has only " + //$NON-NLS-1$
              this.m_owner.m() + " rows."); //$NON-NLS-1$
    }

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void remove() {
    throw new UnsupportedOperationException(//
        "Removing of rows is not allowed."); //$NON-NLS-1$
  }
}
