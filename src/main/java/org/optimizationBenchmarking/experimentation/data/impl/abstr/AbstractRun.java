package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.math.matrix.AbstractMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IRun}
 * interface.
 */
public abstract class AbstractRun extends AbstractElementSet implements
IRun {

  /** the owner */
  IInstanceRuns m_owner;

  /**
   * Create the abstract run. If {@code owner==null}, you must later set it
   * via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstanceRuns#own(AbstractRun)}
   * .
   *
   * @param owner
   *          the owner
   */
  protected AbstractRun(final IInstanceRuns owner) {
    super();
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  public final IInstanceRuns getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArrayListView<? extends IDataPoint> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public int m() {
    return this.getData().size();
  }

  /** {@inheritDoc} */
  @Override
  public int n() {
    final ArrayListView<? extends IDataPoint> data;
    data = this.getData();
    if (data.size() > 0) {
      return data.get(0).size();
    }
    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public double getDouble(final int row, final int column) {
    return this.getData().get(row).getDouble(column);
  }

  /** {@inheritDoc} */
  @Override
  public long getLong(final int row, final int column) {
    return this.getData().get(row).getLong(column);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isIntegerMatrix() {
    final ArrayListView<? extends IDataPoint> data;
    data = this.getData();
    if (data.size() > 0) {
      return data.get(0).isIntegerMatrix();
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix selectColumns(final int... cols) {
    return AbstractMatrix.copy(this).selectColumns(cols);
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix selectRows(final int... rows) {
    return AbstractMatrix.copy(this).selectRows(rows);
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix transpose() {
    return AbstractMatrix.copy(this).transpose();
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<IMatrix> iterateColumns() {
    return AbstractMatrix.copy(this).iterateColumns();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Iterator<IMatrix> iterateRows() {
    return ((Iterator) (this.getData().iterator()));
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix copy() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void aggregateColumn(final int column, final IAggregate aggregate) {
    AbstractMatrix.copy(this).aggregateColumn(column, aggregate);
  }

  /** {@inheritDoc} */
  @Override
  public void aggregateRow(final int row, final IAggregate aggregate) {
    AbstractMatrix.copy(this).aggregateRow(row, aggregate);
  }

  /** {@inheritDoc} */
  @Override
  public abstract IDataPoint find(int column, double value);

  /** {@inheritDoc} */
  @Override
  public abstract IDataPoint find(int column, long value);
}
