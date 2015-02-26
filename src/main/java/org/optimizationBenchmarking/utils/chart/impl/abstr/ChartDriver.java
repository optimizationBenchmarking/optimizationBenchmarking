package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** the chart driver base class */
public abstract class ChartDriver extends Tool implements IChartDriver {

  /** the font scale for the chart title: {@value} */
  static final float FONT_SCALE_CHART_TITLE = 1f;
  /** the font scale for the axis title: {@value} */
  static final float FONT_SCALE_AXIS_TITLE = 0.9f;
  /** the font scale for the data title: {@value} */
  static final float FONT_SCALE_DATA_TITLE = ChartDriver.FONT_SCALE_AXIS_TITLE;
  /** the font scale for the axis ticks: {@value} */
  static final float FONT_SCALE_AXIS_TICKS = (ChartDriver.FONT_SCALE_AXIS_TITLE * ChartDriver.FONT_SCALE_AXIS_TITLE);

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

  /**
   * Scale a font with the base font scale
   * 
   * @param font
   *          the font
   * @param scale
   *          the scale
   * @return the scaled font
   */
  static final Font _scale(final Font font, final float scale) {
    final float oldSize;
    final Font derived;
    int goalSize;

    if (scale == 1f) {
      return font;
    }

    oldSize = font.getSize2D();
    if (scale < 1f) {
      goalSize = (int) (Math.floor(oldSize * scale));
    } else {
      goalSize = (int) (Math.ceil(oldSize * scale));
    }
    goalSize = Math.max(5, Math.min(100, goalSize));
    if (goalSize == oldSize) {
      return font;
    }

    derived = font.deriveFont((float) goalSize);
    if (derived == null) {
      return font;
    }
    if (Math.abs(derived.getSize2D() - goalSize) < //
    Math.abs(oldSize - goalSize)) {
      return derived;
    }

    return font;
  }

  /**
   * Create the default font to be used for chart element titles
   * 
   * @param styles
   *          the available style set
   * @return the default font to be used for chart element titles
   */
  protected Font createDefaultDataTitleFont(final StyleSet styles) {
    return ChartDriver._scale(styles.getDefaultFont().getFont(),
        ChartDriver.FONT_SCALE_DATA_TITLE);
  }

  /**
   * Create the default font to be used for the chart title
   * 
   * @param styles
   *          the available style set
   * @return the default font to be used for the chart title
   */
  protected Font createDefaultChartTitleFont(final StyleSet styles) {
    return ChartDriver._scale(styles.getDefaultFont().getFont(),
        ChartDriver.FONT_SCALE_CHART_TITLE);
  }

  /**
   * Create the default data stroke
   * 
   * @param styles
   *          the styles
   * @return the default data stroke
   */
  protected Stroke createDefaultDataStroke(final StyleSet styles) {
    return styles.getDefaultStroke();
  }

  /**
   * Create the default font to be used for axis titles
   * 
   * @param styles
   *          the available style set
   * @return the default font to be used for axis titles
   */
  protected Font createDefaultAxisTitleFont(final StyleSet styles) {
    return ChartDriver._scale(styles.getDefaultFont().getFont(),
        ChartDriver.FONT_SCALE_AXIS_TITLE);
  }

  /**
   * Create the default font to be used for axis ticks
   * 
   * @param styles
   *          the available style set
   * @return the default font to be used for axis ticks
   */
  protected Font createDefaultAxisTickFont(final StyleSet styles) {
    return ChartDriver._scale(styles.getDefaultFont().getFont(),
        ChartDriver.FONT_SCALE_AXIS_TICKS);
  }

  /**
   * Get the stroke to be used for axes
   * 
   * @param styles
   *          the styles
   * @return the axes stroke
   */
  protected Stroke createDefaultAxisStroke(final StyleSet styles) {
    BasicStroke a;

    a = styles.getDefaultStroke();
    return new BasicStroke(//
        ((styles.getThickStroke().getLineWidth() + //
        a.getLineWidth()) * 0.5f),//
        a.getEndCap(), a.getLineJoin(), a.getMiterLimit());
  }

  /**
   * Create the color to be used for axes
   * 
   * @param styles
   *          the styles
   * @return the axis color
   */
  protected Color createDefaultAxisColor(final StyleSet styles) {
    return styles.getBlack();
  }

  /**
   * Get the stroke to be used for grid lines
   * 
   * @param styles
   *          the styles
   * @return the grid line stroke
   */
  protected Stroke createDefaultGridLineStroke(final StyleSet styles) {
    return styles.getThinStroke();
  }

  /**
   * Get the color to be used for grid lines
   * 
   * @param styles
   *          the styles
   * @return the grid line color
   */
  protected Color createDefaultGridLineColor(final StyleSet styles) {
    return styles.getMostSimilarColor(Color.GRAY);
  }
}
