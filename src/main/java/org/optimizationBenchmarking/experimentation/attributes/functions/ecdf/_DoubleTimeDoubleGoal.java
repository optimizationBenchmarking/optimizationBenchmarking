package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;

/** both time and goal are doubles */
final class _DoubleTimeDoubleGoal extends _Doubles {

  /** the goal */
  private final double m_goal;

  /**
   * create the {@code double} list
   *
   * @param timeDim
   *          the time dimension
   * @param goalIndex
   *          the goal index
   * @param goal
   *          the goal value
   */
  _DoubleTimeDoubleGoal(final IDimension timeDim, final int goalIndex,
      final double goal) {
    super(timeDim, goalIndex);
    this.m_goal = goal;
  }

  /** {@inheritDoc} */
  @Override
  final void _addRun(final IRun run) {
    final IDataPoint dp;
    final double last;

    if (run != null) {
      this.m_total++;
      dp = run.find(this.m_goalIndex, this.m_goal);
      if (dp != null) {
        this._add(dp.getDouble(this.m_timeIndex));
      }

      last = run.getLong((run.m() - 1), this.m_timeIndex);
      if (this.m_isTimeIncreasing) {
        this.m_last = Math.max(this.m_last, last);
      } else {
        this.m_last = Math.min(this.m_last, last);
      }
    }
  }
}
