package org.optimizationBenchmarking.utils.math.matrix;

/**
 * A class for matrix columns
 * 
 * @param <OT>
 *          the owning matrix type
 */
public class TransposedMatrix<OT extends IMatrix> extends AbstractMatrix {

  /** the owner */
  protected final OT m_owner;

  /**
   * create
   * 
   * @param owner
   *          the owning matrix
   */
  public TransposedMatrix(final OT owner) {
    super();
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_owner.n();
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_owner.m();
  }

  /** {@inheritDoc} */
  @Override
  public double getDouble(final int row, final int column) {
    return this.m_owner.getDouble(column, row);
  }

  /** {@inheritDoc} */
  @Override
  public long getLong(final int row, final int column) {
    return this.m_owner.getLong(column, row);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isIntegerMatrix() {
    return this.m_owner.isIntegerMatrix();
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix transpose() {
    return this.m_owner;
  }
}
