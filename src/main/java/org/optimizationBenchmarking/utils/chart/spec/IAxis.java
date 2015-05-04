package org.optimizationBenchmarking.utils.chart.spec;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;

/** The interface for defining an axis */
public interface IAxis extends ITitledElement {

  /**
   * Set the font to be used for the ticks of the axis. Depending on the
   * chart implementation, this font may be scaled to a smaller (or maybe
   * even larger) size to create a more overall pleasing visual expression.
   * Thus, you can directly use fonts from a
   * {@link org.optimizationBenchmarking.utils.graphics.style.font.FontPalette}
   * which would potentially be too large or too small to look good and
   * expect the underlying
   * {@link org.optimizationBenchmarking.utils.chart.spec.IChartDriver
   * chart driver} implementation to resize them appropriately.
   *
   * @param tickFont
   *          the font to be used for the ticks of the axis
   */
  public abstract void setTickFont(final Font tickFont);

  /**
   * Set the stroke to be used for the axis. If you do not set a stroke, a
   * default stroke will be used.
   *
   * @param axisStroke
   *          the stroke to be used for the axis
   */
  public abstract void setAxisStroke(final Stroke axisStroke);

  /**
   * Set the color to be used for the axis. If you do not set a color, a
   * default color will be used.
   *
   * @param axisColor
   *          the color to be used for the axis
   */
  public abstract void setAxisColor(final Color axisColor);

  /**
   * Set the stroke to be used for the grid lines. If you do not set a
   * stroke, a default stroke will be used.
   *
   * @param gridLineStroke
   *          the stroke to be used for the grid lines
   */
  public abstract void setGridLineStroke(final Stroke gridLineStroke);

  /**
   * Set the color to be used for the grid lines. If you do not set a
   * color, a default color will be used.
   *
   * @param gridLineColor
   *          the color to be used for the grid lines
   */
  public abstract void setGridLineColor(final Color gridLineColor);

  /**
   * Set the aggregate used to compute the minimum axis value
   *
   * @param min
   *          the aggregate used to compute the minimum axis value
   */
  public abstract void setMinimumAggregate(final ScalarAggregate min);

  /**
   * Set the minimum value for the axis
   *
   * @param min
   *          the minimum value for the axis
   */
  public abstract void setMinimum(final double min);

  /**
   * Set the aggregate used to compute the maximum axis value
   *
   * @param max
   *          the aggregate used to compute the maximum axis value
   */
  public abstract void setMaximumAggregate(final ScalarAggregate max);

  /**
   * Set the maximum value for the axis
   *
   * @param max
   *          the maximum value for the axis
   */
  public abstract void setMaximum(final double max);

}
