package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;
import java.util.concurrent.atomic.AtomicBoolean;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.IDataScalar;
import org.optimizationBenchmarking.utils.chart.spec.IPieChart;

/** A delegating wrapper for pie charts */
public class DelegatingPieChart implements IPieChart {

  /** the chart to delegate to */
  private final IPieChart m_chart;

  /** are we alife */
  private final AtomicBoolean m_alive;

  /**
   * create the delegating pie chart
   *
   * @param chart
   *          the chart
   */
  public DelegatingPieChart(final IPieChart chart) {
    super();
    if (chart == null) {
      throw new IllegalArgumentException(//
          "PieChart to delegate to cannot be null."); //$NON-NLS-1$
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
  public final void setLegendMode(final ELegendMode legendMode) {
    this.m_chart.setLegendMode(legendMode);
  }

  /** {@inheritDoc} */
  @Override
  public final IDataScalar slice() {
    return this.m_chart.slice();
  }
}
