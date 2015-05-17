package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;

/** both time and goal are longs */
final class _LongTimeLongGoal extends _Longs {

  /** the goal */
  private final long m_goal;

  /**
   * create the {@code long} list
   *
   * @param timeDim
   *          the time dimension
   * @param goalIndex
   *          the goal index
   * @param goal
   *          the goal value
   */
  _LongTimeLongGoal(final IDimension timeDim, final int goalIndex,
      final long goal) {
    super(timeDim, goalIndex);
    this.m_goal = goal;
  }

  /** {@inheritDoc} */
  @Override
  final void _addRun(final IRun run) {
    final IDataPoint dp;
    final long last;

    if (run != null) {
      this.m_total++;
      dp = run.find(this.m_goalIndex, this.m_goal);
      if (dp != null) {
        this._add(dp.getLong(this.m_timeIndex));
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
