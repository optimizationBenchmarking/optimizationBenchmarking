package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;

/** a list */
abstract class _List {

  /** the size */
  int m_size;

  /** the total number of potential elements */
  int m_total;

  /** the time index */
  final int m_timeIndex;

  /** the goal index */
  final int m_goalIndex;

  /** the time dimension */
  final IDimension m_timeDim;

  /** is the time an increasing dimension ? */
  final boolean m_isTimeIncreasing;

  /**
   * create the {@code long} list
   *
   * @param timeDim
   *          the time dimension
   * @param goalIndex
   *          the goal index
   */
  _List(final IDimension timeDim, final int goalIndex) {
    super();
    this.m_timeIndex = timeDim.getIndex();
    this.m_goalIndex = goalIndex;
    this.m_timeDim = timeDim;
    this.m_isTimeIncreasing = timeDim.getDirection().isIncreasing();
  }

  /**
   * add a run
   *
   * @param run
   *          the run to add
   */
  abstract void _addRun(final IRun run);

  /**
   * Convert the data to a matrix
   *
   * @return the matrix
   */
  abstract DoubleMatrix1D _toMatrix();
}
