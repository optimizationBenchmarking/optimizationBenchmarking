package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;

/** the time are longs and goals are doubles */
final class _LongTimeDoubleGoal extends _Longs {

  /** the goal */
  private final double m_goal;

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
  _LongTimeDoubleGoal(final IDimension timeDim, final int goalIndex,
      final double goal) {
    super(timeDim, goalIndex);
    this.m_goal = goal;
  }

  /** {@inheritDoc} */
  @Override
  final void _addRun(final IRun run) {
    final IDataPoint dp;

    if (run != null) {
      this.m_total++;
      dp = run.find(this.m_goalIndex, this.m_goal);
      if (dp != null) {
        this._add(dp.getLong(this.m_timeIndex));
      }
    }
  }
}
