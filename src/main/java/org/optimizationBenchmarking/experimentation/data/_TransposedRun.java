package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.TransposedMatrix;

/** an internal transposed matrix for runs */
final class _TransposedRun extends TransposedMatrix<Run> {

  /**
   * create
   * 
   * @param run
   *          the run
   */
  _TransposedRun(final Run run) {
    super(run);
  }

  // /** {@inheritDoc} */
  // @Override
  // public final IMatrix selectColumns(final int... cols) {
  // return this.m_owner.selectRows(cols).transpose();
  // }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectRows(final int... rows) {
    return this.m_owner.selectColumns(rows).transpose();
  }

  /** {@inheritDoc} */
  @Override
  public final _TransposedRun copy() {
    return this;
  }
}
