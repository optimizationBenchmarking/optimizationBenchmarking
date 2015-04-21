package org.optimizationBenchmarking.experimentation.data.impl.delegates;

import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractRun;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;

/**
 * A delegating run is basically a shadow of another run with a different
 * owner, but delegates attribute-based computations to that run.
 */
public class DelegatingRun extends AbstractRun implements
    Comparable<DelegatingRun> {

  /** the original run */
  private final IRun m_orig;

  /**
   * create a delegating run
   * 
   * @param orig
   *          the original run
   * @param owner
   *          the owner
   */
  public DelegatingRun(final IRun orig, final IInstanceRuns owner) {
    super(owner);
    if (orig == null) {
      throw new IllegalArgumentException(//
          "IRun to delegate to must not be null."); //$NON-NLS-1$
    }
    this.m_orig = orig;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<? extends IDataPoint> getData() {
    return this.m_orig.getData();
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_orig.m();
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_orig.n();
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int row, final int column) {
    return this.m_orig.getDouble(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int row, final int column) {
    return this.m_orig.getLong(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isIntegerMatrix() {
    return this.m_orig.isIntegerMatrix();
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectColumns(final int... cols) {
    return this.m_orig.selectColumns(cols);
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectRows(final int... rows) {
    return this.m_orig.selectRows(rows);
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix transpose() {
    return this.m_orig.transpose();
  }

  /** {@inheritDoc} */
  @Override
  public final Iterator<IMatrix> iterateColumns() {
    return this.m_orig.iterateColumns();
  }

  /** {@inheritDoc} */
  @Override
  public final Iterator<IMatrix> iterateRows() {
    return this.m_orig.iterateRows();
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix copy() {
    return this.m_orig.copy();
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateColumn(final int column,
      final IAggregate aggregate) {
    this.m_orig.aggregateColumn(column, aggregate);
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateRow(final int row, final IAggregate aggregate) {
    this.m_orig.aggregateRow(row, aggregate);
  }

  /** {@inheritDoc} */
  @Override
  public final IDataPoint find(final int column, final double value) {
    return this.m_orig.find(column, value);
  }

  /** {@inheritDoc} */
  @Override
  public final IDataPoint find(final int column, final long value) {
    return this.m_orig.find(column, value);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final <XDT extends IDataElement, RT> RT getAttribute(
      final Attribute<XDT, RT> attribute) {
    return DataElement.delegateGetAttribute(((XDT) (this.m_orig)),
        attribute);
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(final DelegatingRun o) {
    if (o != null) {
      return EComparison.compareObjects(this.m_orig, o.m_orig);
    }
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || //
    ((o instanceof DelegatingRun) && //
    (EComparison.equals(this.m_orig, ((DelegatingRun) o).m_orig))));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return this.m_orig.hashCode();
  }
}
