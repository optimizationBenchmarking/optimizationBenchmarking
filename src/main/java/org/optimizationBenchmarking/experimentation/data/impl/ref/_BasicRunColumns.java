package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.utils.math.matrix.MatrixColumns;

/** A selection of columns from a basic run */
final class _BasicRunColumns extends MatrixColumns<_BasicRun> {
  /**
   * create
   *
   * @param owner
   *          the owning matrix
   * @param cols
   *          the matrix columns
   */
  _BasicRunColumns(final _BasicRun owner, final int[] cols) {
    super(owner, cols);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isIntegerMatrix() {
    for (final int i : this.m_cols) {
      if (this.m_owner.m_dims.get(i).m_primitiveType.isFloat()) {
        return false;
      }
    }
    return true;
  }
}
