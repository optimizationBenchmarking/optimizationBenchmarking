package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;

/** a list of {@code long}s */
abstract class _Longs extends _List {

  /** the data */
  long[] m_data;

  /**
   * create the {@code long} list
   *
   * @param timeDim
   *          the time dimension
   * @param goalIndex
   *          the goal index
   */
  _Longs(final IDimension timeDim, final int goalIndex) {
    super(timeDim, goalIndex);
    this.m_data = new long[1024];
  }

  /**
   * add an element
   *
   * @param data
   *          the element
   */
  final void _add(final long data) {
    final int size;
    long[] ldata;

    size = this.m_size;
    ldata = this.m_data;
    if (size >= ldata.length) {
      this.m_data = new long[size << 1];
      System.arraycopy(ldata, 0, this.m_data, 0, size);
      ldata = this.m_data;
    }

    this.m_data[size] = data;
    this.m_size = (size + 1);
  }

  /** {@inheritDoc} */
  @Override
  final DoubleMatrix1D _toMatrix() {
    final int size;
    final double total;
    final double[] ret;
    int offset;
    double[] res;
    long[] data;
    int i, idx;
    double current;
    long earliest, temp;

    // We may need to add an "earliest" data point at which no run has
    // succeeded yet.
    if (this.m_isTimeIncreasing) {
      earliest = this.m_timeDim.getParser().getLowerBoundLong();
    } else {
      earliest = this.m_timeDim.getParser().getUpperBoundLong();
    }

    // We will not add this point if at the earliest possible time, one run
    // has already succeeded.
    idx = this.m_size;
    offset = 0;
    add: {
      loop: for (final long lng : this.m_data) {
        if ((idx--) <= 0) {
          break loop;
        }
        if (lng == earliest) {
          break add;
        }
      }
    this._add(earliest);
    offset = (-1);
    }

    // Sort the data points in ascending order.
    data = this.m_data;
    this.m_data = null;
    size = this.m_size;
    Arrays.sort(data, 0, size);

    if (!(this.m_isTimeIncreasing)) {
      // Time is decreasing? Whatever, let's rotate array.
      for (idx = size, i = 0; (--idx) > i; i++) {
        temp = data[idx];
        data[idx] = data[i];
        data[i] = temp;
      }
    }

    // The output matrix will points of the form (time, fraction of
    // success)
    res = new double[size << 1];

    idx = 0;
    total = this.m_total;
    for (i = 0; i < size;) {
      current = data[i];
      inner: for (; (++i) < size;) {
        if (current != data[i]) {
          break inner;
        }
      }

      res[idx++] = current;
      res[idx++] = ((i + offset) / total);
    }
    data = null;

    // Resize data if necessary
    if (idx >= res.length) {
      ret = res;
    } else {
      ret = new double[idx];
      System.arraycopy(res, 0, ret, 0, idx);
      res = null;
    }

    return new DoubleMatrix1D(ret, (idx >>> 1), 2);
  }
}
