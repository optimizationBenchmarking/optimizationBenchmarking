package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.iterators.InstanceIterator;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.MatrixColumnIterator;
import org.optimizationBenchmarking.utils.math.matrix.MatrixColumns;
import org.optimizationBenchmarking.utils.math.matrix.MatrixRows;

/** a run. */
public abstract class Run extends ElementSet<DataPoint> implements IRun {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * instantiate
   *
   * @param data
   *          the data points
   */
  protected Run(final DataPoint[] data) {
    super(data, false, false, false);
  }

  /** {@inheritDoc} */
  @Override
  public int m() {
    return this.m_data.size();
  }

  /** {@inheritDoc} */
  @Override
  public int n() {
    return this.m_data.get(0).size();
  }

  /** {@inheritDoc} */
  @Override
  public double getDouble(final int row, final int column) {
    return this.m_data.get(row).getDouble(column);
  }

  /** {@inheritDoc} */
  @Override
  public long getLong(final int row, final int column) {
    return this.m_data.get(row).getLong(column);
  }

  /** {@inheritDoc} */
  @Override
  public abstract DataPoint find(final int column, final double value);

  /** {@inheritDoc} */
  @Override
  public abstract DataPoint find(final int column, final long value);

  /** {@inheritDoc} */
  @Override
  public boolean isIntegerMatrix() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix selectColumns(final int... cols) {
    int i;

    checker: {
      i = 0;
      for (final int j : cols) {
        if (j != (i++)) {
          break checker;
        }
      }
      if (i == this.n()) {
        return this;
      }
    }

    return new MatrixColumns<>(this, cols);
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix selectRows(final int... rows) {
    int i;

    checker: {
      i = 0;
      for (final int j : rows) {
        if (j != (i++)) {
          break checker;
        }
      }
      if (i == this.m()) {
        return this;
      }
    }

    return new MatrixRows<>(this, rows);
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix transpose() {
    if ((this.m() > 1) || (this.n() > 1)) {
      return new _TransposedRun(this);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<IMatrix> iterateColumns() {
    if (this.n() <= 1) {
      return new InstanceIterator<IMatrix>(this);
    }

    return new MatrixColumnIterator<>(this);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final Iterator<IMatrix> iterateRows() {
    return ((Iterator) (this.m_data.iterator()));
  }

  /** {@inheritDoc} */
  @Override
  public final Run copy() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  final void _validateElementPair(final DataPoint before,
      final DataPoint after) {
    super._validateElementPair(before, after);
    after.validateAfter(before);
  }

  /** {@inheritDoc} */
  @Override
  public final InstanceRuns getOwner() {
    return ((InstanceRuns) (this.m_owner));
  }
}
