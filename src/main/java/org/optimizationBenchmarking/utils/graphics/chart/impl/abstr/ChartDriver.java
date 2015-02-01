package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** the chart driver base class */
public abstract class ChartDriver extends Tool implements IChartDriver {

  /**
   * the chart driver
   */
  protected ChartDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public ChartBuilder use() {
    this.checkCanUse();
    return new ChartBuilder(this);
  }

  /**
   * render a compiled line chart
   * 
   * @param chart
   *          the chart to be rendered.
   * @param graphic
   *          the graphic output interface
   * @param logger
   *          a logger for logging info, or {@code null} if none is needed
   */
  protected abstract void renderLineChart(final CompiledLineChart chart,
      final Graphic graphic, final Logger logger);

}
