package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;

/** the chart builder */
public class AxisChart extends Chart {

  /** the axis title font */
  private transient volatile Font m_axisTitleFont;
  /** the axis tick font */
  private transient volatile Font m_axisTickFont;
  /** the axis stroke */
  private transient volatile Stroke m_axisStroke;
  /** the axis color */
  private transient volatile Color m_axisColor;
  /** the grid line stroke */
  private transient volatile Stroke m_gridLineStroke;
  /** the grid line color */
  private transient volatile Color m_gridLineColor;

  /**
   * create the line chart
   * 
   * @param graphic
   *          the graphic
   * @param styleSet
   *          the style set to use
   * @param driver
   *          the chart driver
   * @param logger
   *          the logger
   */
  protected AxisChart(final Graphic graphic, final StyleSet styleSet,
      final Logger logger, final ChartDriver driver) {
    super(graphic, styleSet, logger, driver);
  }

  /**
   * Get a font to be used for axis titles
   * 
   * @return the axis title font
   */
  synchronized final Font _getDefaultAxisTitleFont() {
    if (this.m_axisTitleFont == null) {
      this.m_axisTitleFont = this.m_driver
          .createDefaultAxisTitleFont(this.m_styleSet);
    }
    return this.m_axisTitleFont;
  }

  /**
   * Get a font to be used for axis ticks
   * 
   * @return the axis ticks font
   */
  synchronized final Font _getDefaultAxisTickFont() {
    if (this.m_axisTickFont == null) {
      this.m_axisTickFont = this.m_driver
          .createDefaultAxisTickFont(this.m_styleSet);
    }
    return this.m_axisTickFont;
  }

  /**
   * Get the stroke to be used for axes
   * 
   * @return the axes stroke
   */
  synchronized final Stroke _getDefaultAxisStroke() {
    if (this.m_axisStroke == null) {
      this.m_axisStroke = this.m_driver
          .createDefaultAxisStroke(this.m_styleSet);
    }
    return this.m_axisStroke;
  }

  /**
   * Get the color to be used for axes
   * 
   * @return the axes stroke
   */
  synchronized final Color _getDefaultAxisColor() {
    if (this.m_axisColor == null) {
      this.m_axisColor = this.m_driver
          .createDefaultAxisColor(this.m_styleSet);
    }
    return this.m_axisColor;
  }

  /**
   * Get the stroke to be used for grid lines
   * 
   * @return the grid line stroke
   */
  final Stroke _getDefaultGridLineStroke() {
    if (this.m_gridLineStroke == null) {
      this.m_gridLineStroke = this.m_driver
          .createDefaultGridLineStroke(this.m_styleSet);
    }
    return this.m_gridLineStroke;
  }

  /**
   * Get the color to be used for grid lines
   * 
   * @return the grid line color
   */
  synchronized final Color _getDefaultGridLineColor() {
    if (this.m_gridLineColor == null) {
      this.m_gridLineColor = this.m_driver
          .createDefaultGridLineColor(this.m_styleSet);
    }
    return this.m_gridLineColor;
  }
}
