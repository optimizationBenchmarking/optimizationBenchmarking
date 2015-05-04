package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.utils.collections.iterators.InstanceIterator;
import org.optimizationBenchmarking.utils.collections.lists.NumberList;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.MatrixColumnIterator;
import org.optimizationBenchmarking.utils.math.matrix.MatrixColumns;
import org.optimizationBenchmarking.utils.math.matrix.MatrixRows;
import org.optimizationBenchmarking.utils.math.matrix.TransposedMatrix;

/**
 * <p>
 * A data point is an object holding the log data collected at a specific
 * point in time. Data points are {@link #m() m}-dimensional vectors. Each
 * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Dimension
 * dimension} may represent a different
 * {@link org.optimizationBenchmarking.experimentation.data.spec.EDimensionType
 * type} of measurement.
 * </p>
 * <p>
 * Data points implement Java's {@link java.util.List list interface} with
 * {@link java.lang.Number number object} elements. This interface is
 * implemented in an immutable way, i.e., all modifying operations will
 * throw a {@link java.lang.UnsupportedOperationException}. Data points are
 * furthermore {@link java.lang.Comparable comparable} objects, as they
 * usually represents elements of a (temporal) sequence of measurements.
 * </p>
 * <p>
 * The class {@link DataPoint} itself is an abstract base class. Usually,
 * compact application-specific implementations of this class will
 * automatically be generated on the fly based on
 * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.DimensionSet
 * dimension set specifications}.
 * </p>
 */
public abstract class DataPoint extends NumberList implements IDataPoint {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  protected DataPoint() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean addAll(final Collection<? extends Number> c) {
    if (c.isEmpty()) {
      return false;
    }
    throw new UnsupportedOperationException(//
        "Cannot add elements " + c + //$NON-NLS-1$
        " to this list."//$NON-NLS-1$
        );
  }

  /** {@inheritDoc} */
  @Override
  public final boolean addAll(final int index,
      final Collection<? extends Number> c) {
    if (c.isEmpty()) {
      return false;
    }
    throw new UnsupportedOperationException(//
        "Cannot add elements " + c + //$NON-NLS-1$
        "at index " + index//$NON-NLS-1$
        );
  }

  /** {@inheritDoc} */
  @Override
  public final boolean removeAll(final Collection<?> c) {
    for (final Object o : c) {
      if (this.contains(o)) {
        throw new UnsupportedOperationException(//
            "Cannot remove elements " + c //$NON-NLS-1$
            );
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean retainAll(final Collection<?> c) {
    for (final Object o : this) {
      if (!(c.contains(o))) {
        throw new UnsupportedOperationException(//
            "Cannot retain the elements " + c //$NON-NLS-1$
            );
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void clear() {
    if (this.size() > 0) {
      throw new UnsupportedOperationException(//
          "Cannot clear this list."//$NON-NLS-1$
          );
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Number set(final int index, final Number element) {
    throw new UnsupportedOperationException(//
        "Cannot set element " + element + //$NON-NLS-1$
        " at index " + index//$NON-NLS-1$
        );
  }

  /** {@inheritDoc} */
  @Override
  public final void add(final int index, final Number element) {
    throw new UnsupportedOperationException(//
        "Cannot add element " + element + //$NON-NLS-1$
        " at index " + index//$NON-NLS-1$
        );
  }

  /** {@inheritDoc} */
  @Override
  public final boolean add(final Number element) {
    throw new UnsupportedOperationException(//
        "Cannot add element " + element//$NON-NLS-1$
        );
  }

  /** {@inheritDoc} */
  @Override
  public boolean remove(final Object o) {
    final int i;

    i = this.indexOf(o);
    if (i >= 0) {
      throw new UnsupportedOperationException(//
          "Cannot remove element " + o + //$NON-NLS-1$
          ", which is at index " + i//$NON-NLS-1$
          );
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final Number remove(final int index) {
    throw new UnsupportedOperationException(//
        "Cannot remove the element at index " + index//$NON-NLS-1$
        );
  }

  /** {@inheritDoc} */
  @Override
  public final void removeRange(final int fromIndex, final int toIndex) {
    if (toIndex > fromIndex) {
      throw new UnsupportedOperationException(
          "Cannot remove range [" + fromIndex + //$NON-NLS-1$
          ", " + toIndex + ")." //$NON-NLS-1$ //$NON-NLS-2$
          );
    }
  }

  /**
   * Check whether this point can follow the point {@code before} and throw
   * an {@link java.lang.IllegalStateException} otherwise.
   *
   * @param before
   *          the point supposed to be before this one
   */
  public abstract void validateAfter(final DataPoint before);

  /**
   * A data point is a row vector (or row matrix) with {@link #m() m}=1
   * rows.
   *
   * @return 1
   */
  @Override
  public final int m() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  public int n() {
    return this.size();
  }

  /** {@inheritDoc} */
  @Override
  public double getDouble(final int row, final int column) {
    return this.getDouble(column);
  }

  /** {@inheritDoc} */
  @Override
  public long getLong(final int row, final int column) {
    return this.getLong(column);
  }

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
      if (i == 1) {
        return this;
      }
    }

    return new MatrixRows<>(this, rows);
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix transpose() {
    if ((this.m() > 1) || (this.n() > 1)) {
      return new TransposedMatrix<>(this);
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
  public final Iterator<IMatrix> iterateRows() {
    return new InstanceIterator<IMatrix>(this);
  }

  /** {@inheritDoc} */
  @Override
  public final DataPoint copy() {
    return this;
  }
}
