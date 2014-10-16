package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILine2D;

/**
 * The base class for all 2-dimensional lines
 */
public class Line2D extends DataSeries2D implements ILine2D {

  /** the line type */
  ELineType m_type;

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   */
  protected Line2D(final ChartElement owner) {
    super(owner);

    this.m_type = ELineType.DIRECT_CONNECT;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setType(final ELineType type) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_type = ((type == null) ? ELineType.DIRECT_CONNECT : type);
  }
}
