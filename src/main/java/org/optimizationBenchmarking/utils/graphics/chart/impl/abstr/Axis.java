package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;

/**
 * The base class for all axes
 */
public class Axis extends TitleElement implements IAxis {

  /** the id */
  final int m_id;

  /** the minimum aggregate */
  ScalarAggregate m_min;
  /** the maximum aggregate */
  ScalarAggregate m_max;

  /**
   * create the chart item
   * 
   * @param id
   *          the id
   * @param owner
   *          the owner
   */
  protected Axis(final ChartElement owner, final int id) {
    super(owner);
    this.m_id = id;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setMinimumAggregate(final ScalarAggregate min) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_min = min;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setMaximumAggregate(final ScalarAggregate max) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_max = max;
  }

}
