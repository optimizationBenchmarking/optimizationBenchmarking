package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The selector the chart to create */
public class ChartSelector extends ToolJob implements IChartSelector {

  /** the graphic to use */
  private final Graphic m_graphic;

  /** the style set to use */
  private final StyleSet m_styleSet;

  /** the chart driver */
  private final ChartDriver m_driver;

  /**
   * create the chart selector
   * 
   * @param builder
   *          the chart builder
   */
  public ChartSelector(final ChartBuilder builder) {
    this(builder.getGraphic(), builder.getStyleSet(), builder
        .getChartDriver(), builder.getLogger());
  }

  /**
   * create the chart selector
   * 
   * @param graphic
   *          the graphic
   * @param styleSet
   *          the style set
   * @param driver
   *          the chart driver
   * @param logger
   *          the logger
   */
  protected ChartSelector(final Graphic graphic, final StyleSet styleSet,
      final ChartDriver driver, final Logger logger) {
    super(logger);
    ChartBuilder._checkGraphic(graphic);
    ChartBuilder._checkStyleSet(styleSet);
    ChartBuilder._checkChartDriver(driver);
    this.m_graphic = graphic;
    this.m_styleSet = styleSet;
    this.m_driver = driver;
  }

  /**
   * get the logger
   * 
   * @return the logger
   */
  final Logger _getLogger() {
    return this.getLogger();
  }

  /**
   * Get the graphic to draw the chart on
   * 
   * @return the graphic to draw the chart on
   */
  protected final Graphic getGraphic() {
    return this.m_graphic;
  }

  /**
   * Get the style set to be used for the chart
   * 
   * @return the style set to be used for the chart
   */
  protected final StyleSet getStyleSet() {
    return this.m_styleSet;
  }

  /**
   * Get the chart driver
   * 
   * @return the chart driver
   */
  public final ChartDriver getChartDriver() {
    return this.m_driver;
  }

  /** {@inheritDoc} */
  @Override
  public final LineChart2D lineChart2D() {
    return new LineChart2D(this);
  }
}
