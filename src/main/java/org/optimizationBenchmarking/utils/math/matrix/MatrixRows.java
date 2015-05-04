package org.optimizationBenchmarking.utils.math.matrix;

/**
 * A class for matrix rows
 *
 * @param <OT>
 *          the owning matrix type
 */
public class MatrixRows<OT extends IMatrix> extends AbstractMatrix {

  /** the owner */
  protected final OT m_owner;
  /** the rows */
  protected final int[] m_rows;

  /**
   * create
   *
   * @param owner
   *          the owning matrix
   * @param rows
   *          the matrix rows
   */
  public MatrixRows(final OT owner, final int[] rows) {
    super();
    this.m_owner = owner;
    this.m_rows = rows;
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_rows.length;
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_owner.n();
  }

  /** {@inheritDoc} */
  @Override
  public double getDouble(final int row, final int col) {
    return this.m_owner.getDouble(this.m_rows[row], col);
  }

  /** {@inheritDoc} */
  @Override
  public long getLong(final int row, final int col) {
    return this.m_owner.getLong(this.m_rows[row], col);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isIntegerMatrix() {
    return this.m_owner.isIntegerMatrix();
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectRows(final int... rows) {
    final int[] cc, c;
    int i, j, k;
    boolean d, e;

    c = this.m_rows;
    cc = rows.clone();
    d = e = true;

    for (i = cc.length; (--i) >= 0;) {
      k = cc[i];
      cc[i] = j = c[k];
      d &= (j == k);
      e &= (i == k);
    }

    return ((e && (cc.length == c.length)) ? this : //
        this.m_owner.selectRows(d ? rows : cc));
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectColumns(final int... cols) {
    if (this.m_owner instanceof MatrixColumns) {
      return this.m_owner.selectColumns(cols).selectRows(this.m_rows);
    }

    return super.selectColumns(cols);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final IMatrix transpose() {
    final IMatrix grandpa;
    final MatrixColumns cols;

    if (this.m_owner instanceof MatrixColumns) {
      cols = ((MatrixColumns) this.m_owner);
      grandpa = cols.m_owner;
      if (grandpa instanceof TransposedMatrix) {
        return ((TransposedMatrix) grandpa).m_owner.selectColumns(
            this.m_rows).selectRows(cols.m_cols);
      }
    } else {
      if (this.m_owner instanceof TransposedMatrix) {
        return ((TransposedMatrix) (this.m_owner)).m_owner
            .selectColumns(this.m_rows);
      }
    }

    return super.transpose();
  }
}
