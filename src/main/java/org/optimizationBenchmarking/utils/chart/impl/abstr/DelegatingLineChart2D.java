package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;
import java.util.concurrent.atomic.AtomicBoolean;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.chart.spec.ILineChart2D;

/** A delegating wrapper for 2D-line charts */
public class DelegatingLineChart2D implements ILineChart2D {

  /** the chart to delegate to */
  private final ILineChart2D m_chart;

  /** are we alife */
  private final AtomicBoolean m_alive;

  /**
   * create the delegating 2D line chart
   *
   * @param chart
   *          the chart
   */
  public DelegatingLineChart2D(final ILineChart2D chart) {
    super();
    if (chart == null) {
      throw new IllegalArgumentException(//
          "LineChart2D to delegate to cannot be null."); //$NON-NLS-1$
    }
    this.m_alive = new AtomicBoolean(true);
    this.m_chart = chart;
  }

  /** {@inheritDoc} */
  @Override
  public final void setTitleFont(final Font titleFont) {
    this.m_chart.setTitleFont(titleFont);
  }

  /** perform the closing */
  protected void doClose() {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    if (this.m_alive.getAndSet(false)) {
      try {
        this.m_chart.close();
      } finally {
        this.doClose();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void setTitle(final String title) {
    this.m_chart.setTitle(title);
  }

  /** {@inheritDoc} */
  @Override
  public final IAxis xAxis() {
    return this.m_chart.xAxis();
  }

  /** {@inheritDoc} */
  @Override
  public final IAxis yAxis() {
    return this.m_chart.yAxis();
  }

  /** {@inheritDoc} */
  @Override
  public final ILine2D line() {
    return this.m_chart.line();
  }

  /** {@inheritDoc} */
  @Override
  public final void setLegendMode(final ELegendMode legendMode) {
    this.m_chart.setLegendMode(legendMode);
  }
}
