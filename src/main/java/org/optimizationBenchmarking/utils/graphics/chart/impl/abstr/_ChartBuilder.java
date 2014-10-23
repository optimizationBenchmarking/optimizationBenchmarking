package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;

/** the chart builder */
class _ChartBuilder extends _TitledElementBuilder {

  /** the graphic */
  final Graphic m_graphic;

  /** the chart driver */
  final ChartDriver m_driver;

  /** the style set */
  final StyleSet m_styles;

  /** the line title font */
  private transient volatile Font m_lineTitleFont;
  /** the axis title font */
  private transient volatile Font m_axisTitleFont;
  /** the axis tick font */
  private transient volatile Font m_axisTickFont;
  /** the grid line color */
  private transient volatile Color m_gridLineColor;

  /**
   * create the line chart
   * 
   * @param graphic
   *          the graphic
   * @param styles
   *          the style set to use
   * @param driver
   *          the chart driver
   */
  _ChartBuilder(final Graphic graphic, final StyleSet styles,
      final ChartDriver driver) {
    super(null);

    if (graphic == null) {
      throw new IllegalArgumentException("Graphic must not be null."); //$NON-NLS-1$
    }
    if (styles == null) {
      throw new IllegalArgumentException("Style set must not be null.");//$NON-NLS-1$
    }
    if (driver == null) {
      throw new IllegalArgumentException("Driver must not be null."); //$NON-NLS-1$
    }
    this.m_styles = styles;
    this.m_graphic = graphic;
    this.m_driver = driver;
  }

  /**
   * Get a font to be used for line titles
   * 
   * @return the line title font
   */
  synchronized final Font _getLineTitleFont() {
    Font f, g;
    if (this.m_lineTitleFont == null) {
      f = this.m_styles.getDefaultFont().getFont();
      g = f.deriveFont(f.getSize2D() * 0.9f);
      if (g != null) {
        f = g;
      }
      this.m_lineTitleFont = f;
    }
    return this.m_lineTitleFont;
  }

  /**
   * Get a font to be used for axis titles
   * 
   * @return the axis title font
   */
  synchronized final Font _getAxisTitleFont() {
    Font f, g;
    if (this.m_axisTitleFont == null) {
      f = this.m_styles.getDefaultFont().getFont();
      g = f.deriveFont(Font.BOLD).deriveFont(f.getSize2D() * 0.95f);
      if (g != null) {
        f = g;
      }
      this.m_axisTitleFont = f;
    }
    return this.m_axisTitleFont;
  }

  /**
   * Get a font to be used for chart titles
   * 
   * @return the chart title font
   */
  final Font _getChartTitleFont() {
    Font f, g;

    f = this.m_styles.getEmphFont().getFont();
    g = f.deriveFont(Font.BOLD);
    if (g != null) {
      return g;
    }
    return f;
  }

  /**
   * Get a font to be used for axis ticks
   * 
   * @return the axis ticks font
   */
  synchronized final Font _getAxisTickFont() {
    Font f, g;
    if (this.m_axisTickFont == null) {
      f = this.m_styles.getDefaultFont().getFont();
      g = f.deriveFont(f.getSize2D() * 0.8f);
      if (g != null) {
        f = g;
      }
      this.m_axisTickFont = f;
    }
    return this.m_axisTickFont;
  }

  /**
   * Get the stroke to be used for lines
   * 
   * @return the line stroke
   */
  final Stroke _getLineStroke() {
    return this.m_styles.getDefaultStroke();
  }

  /**
   * Get the stroke to be used for axes
   * 
   * @return the axes stroke
   */
  final Stroke _getAxisStroke() {
    return this.m_styles.getThickStroke();
  }

  /**
   * Get the color to be used for axes
   * 
   * @return the axes stroke
   */
  final Color _getAxisColor() {
    return this.m_styles.getBlack();
  }

  /**
   * Get the stroke to be used for grid lines
   * 
   * @return the grid line stroke
   */
  final Stroke _getGridLineStroke() {
    return this.m_styles.getThinStroke();
  }

  /**
   * Get the color to be used for grid lines
   * 
   * @return the grid line color
   */
  synchronized final Color _getGridLineColor() {
    if (this.m_gridLineColor != null) {
      return this.m_gridLineColor;
    }
    return (this.m_gridLineColor = this.m_styles
        .getMostSimilarColor(Color.GRAY));
  }

}
