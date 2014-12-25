package org.optimizationBenchmarking.experimentation.evaluation.data;

import java.util.Iterator;

import org.optimizationBenchmarking.utils.collections.iterators.InstanceIterator;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.MatrixColumnIterator;
import org.optimizationBenchmarking.utils.math.matrix.MatrixColumns;
import org.optimizationBenchmarking.utils.math.matrix.MatrixRows;

/**  */
public abstract class Run extends DataSet<DataPoint> implements IMatrix {
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

  /**
   * Find the the data point in this run whose column/dimension
   * {@code column} fulfills a given condition:
   * <ul>
   * <li>If dimension {@code column} represents a
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.EDimensionType#isSolutionQualityMeasure()
   * solution quality} measure, find the earliest data point in this run
   * whose value in dimension {@code column} is at least as good (i.e., the
   * same or better) than {@code value}. If the run does not contain a
   * point which has a quality (in dimension {@code column}) which is as
   * same as good or better than {@code value}, return {@code null}.</li>
   * <li>If dimension {@code column} represents a
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.EDimensionType#isTimeMeasure()
   * time measure}, then find either the (earliest) data point in this run
   * exactly at time {@code value} or the last data point in this run which
   * is earlier than {@code value} (all according to time dimension
   * {@code column} ). If no such point exists, i.e., if all points in this
   * run happen after time {@code value}, return {@code null}.</li>
   * </ul>
   * If either case, if there are any data points with value {@code value}
   * in dimension/column {@code column} in this run, then the
   * first/earliest one of them will be returned.
   * 
   * @param column
   *          the column
   * @param value
   *          the value
   * @return the point
   */
  public abstract DataPoint find(final int column, final double value);

  /**
   * Find the the data point in this run whose column/dimension
   * {@code column} fulfills a given condition:
   * <ul>
   * <li>If dimension {@code column} represents a
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.EDimensionType#isSolutionQualityMeasure()
   * solution quality} measure, find the earliest data point in this run
   * whose value in dimension {@code column} is at least as good (i.e., the
   * same or better) than {@code value}. If the run does not contain a
   * point which has a quality (in dimension {@code column}) which is as
   * same as good or better than {@code value}, return {@code null}.</li>
   * <li>If dimension {@code column} represents a
   * {@link org.optimizationBenchmarking.experimentation.evaluation.data.EDimensionType#isTimeMeasure()
   * time measure}, then find either the (earliest) data point in this run
   * exactly at time {@code value} or the last data point in this run which
   * is earlier than {@code value} (all according to time dimension
   * {@code column} ). If no such point exists, i.e., if all points in this
   * run happen after time {@code value}, return {@code null}.</li>
   * </ul>
   * 
   * @param column
   *          the column
   * @param value
   *          the value
   * @return the point
   */
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
