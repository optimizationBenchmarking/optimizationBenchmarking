package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;

/**
 * A shadow run is basically a shadow of another run with a different owner
 * and potentially different attributes. If all associated data of this
 * element is the same, it will delegate attribute-based computations to
 * that run.
 */
public class ShadowRun extends _ShadowDataElement<IInstanceRuns, IRun>
implements IRun {

  /**
   * create a delegating run
   *
   * @param owner
   *          the owner
   * @param orig
   *          the original run
   */
  public ShadowRun(final IInstanceRuns owner, final IRun orig) {
    super(owner, orig);
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canDelegateAttributesTo(final IRun shadow) {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<? extends IDataPoint> getData() {
    return this.m_shadowUnpacked.getData();
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_shadowUnpacked.m();
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_shadowUnpacked.n();
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int row, final int column) {
    return this.m_shadowUnpacked.getDouble(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int row, final int column) {
    return this.m_shadowUnpacked.getLong(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isIntegerMatrix() {
    return this.m_shadowUnpacked.isIntegerMatrix();
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectColumns(final int... cols) {
    return this.m_shadowUnpacked.selectColumns(cols);
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectRows(final int... rows) {
    return this.m_shadowUnpacked.selectRows(rows);
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix transpose() {
    return this.m_shadowUnpacked.transpose();
  }

  /** {@inheritDoc} */
  @Override
  public final Iterator<IMatrix> iterateColumns() {
    return this.m_shadowUnpacked.iterateColumns();
  }

  /** {@inheritDoc} */
  @Override
  public final Iterator<IMatrix> iterateRows() {
    return this.m_shadowUnpacked.iterateRows();
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix copy() {
    return this.m_shadowUnpacked.copy();
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateColumn(final int column,
      final IAggregate aggregate) {
    this.m_shadowUnpacked.aggregateColumn(column, aggregate);
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateRow(final int row, final IAggregate aggregate) {
    this.m_shadowUnpacked.aggregateRow(row, aggregate);
  }

  /** {@inheritDoc} */
  @Override
  public final IDataPoint find(final int column, final double value) {
    return this.m_shadowUnpacked.find(column, value);
  }

  /** {@inheritDoc} */
  @Override
  public final IDataPoint find(final int column, final long value) {
    return this.m_shadowUnpacked.find(column, value);
  }

}
