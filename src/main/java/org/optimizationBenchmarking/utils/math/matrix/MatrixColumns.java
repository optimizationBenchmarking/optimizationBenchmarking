package org.optimizationBenchmarking.utils.math.matrix;

/**
 * A class for matrix columns
 *
 * @param <OT>
 *          the owning matrix type
 */
public class MatrixColumns<OT extends IMatrix> extends AbstractMatrix {

  /** the owner */
  protected final OT m_owner;
  /** the columns */
  protected final int[] m_cols;

  /**
   * create
   *
   * @param owner
   *          the owning matrix
   * @param cols
   *          the matrix columns
   */
  public MatrixColumns(final OT owner, final int[] cols) {
    super();
    this.m_owner = owner;
    this.m_cols = cols;
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_owner.m();
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_cols.length;
  }

  /** {@inheritDoc} */
  @Override
  public double getDouble(final int row, final int column) {
    return this.m_owner.getDouble(row, this.m_cols[column]);
  }

  /** {@inheritDoc} */
  @Override
  public long getLong(final int row, final int column) {
    return this.m_owner.getLong(row, this.m_cols[column]);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isIntegerMatrix() {
    return this.m_owner.isIntegerMatrix();
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectColumns(final int... cols) {

    final int[] cc, c;
    int i, j, k;
    boolean d, e;

    c = this.m_cols;
    cc = cols.clone();
    d = e = true;

    for (i = cc.length; (--i) >= 0;) {
      k = cc[i];
      cc[i] = j = c[k];
      d &= (j == k);
      e &= (i == k);
    }

    return ((e && (cc.length == c.length)) ? this : //
        this.m_owner.selectColumns(d ? cols : cc));
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectRows(final int... rows) {
    if (this.m_owner instanceof MatrixRows) {
      return this.m_owner.selectRows(rows).selectColumns(this.m_cols);
    }

    return super.selectRows(rows);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final IMatrix transpose() {
    final IMatrix grandpa;
    final MatrixRows rows;

    if (this.m_owner instanceof MatrixRows) {
      rows = ((MatrixRows) this.m_owner);
      grandpa = rows.m_owner;
      if (grandpa instanceof TransposedMatrix) {
        return ((TransposedMatrix) grandpa).m_owner.selectColumns(
            rows.m_rows).selectRows(this.m_cols);
      }
    } else {
      if (this.m_owner instanceof TransposedMatrix) {
        return ((TransposedMatrix) (this.m_owner)).m_owner
            .selectRows(this.m_cols);
      }
    }

    return super.transpose();
  }
}
