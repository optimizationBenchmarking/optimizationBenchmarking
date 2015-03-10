package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockParams;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.general.Dataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.UnitType;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledChart;
import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/**
 * <p>
 * The pie chart as JFreeChar-facade.
 * </p>
 * 
 * @param <C>
 *          the chart type
 * @param <D>
 *          the data set type
 * @param <P>
 *          the plot type
 */
abstract class _JFreeChartRenderer<C extends CompiledChart, D extends Dataset, P extends Plot> {

  /** the default insets */
  static final RectangleInsets CHART_INSETS = RectangleInsets.ZERO_INSETS;

  /** the default insets */
  static final RectangleInsets LEGEND_MARGIN = new RectangleInsets(
      UnitType.ABSOLUTE, 0.5d, 0.5d, 0.5d, 0.5d);

  /** the empty shape */
  static final Shape EMPTY_SHAPE = new java.awt.geom.Line2D.Float(0f, 0f,
      0f, 0f);

  /** the line shape */
  static final Shape LINE_SHAPE = new java.awt.geom.Line2D.Float(0f, 0f,
      8f, 0f);

  /** the chart */
  final JFreeChart m_chart;

  /** the legend mode */
  final ELegendMode m_legendMode;

  /** the data set */
  final D m_dataset;
  /** the plot */
  final P m_plot;

  /**
   * Create a new line chart
   * 
   * @param chart
   *          the chart to paint
   */
  _JFreeChartRenderer(final C chart) {
    super();

    TextTitle chartTitle;
    String title;

    this.m_legendMode = chart.getLegendMode();

    this.m_dataset = this._createDataSet(chart);
    this.m_chart = this._createChart(chart, this.m_dataset);

    this.m_chart.setBackgroundPaint(Color.WHITE);
    this.m_chart.setPadding(_JFreeChartRenderer.CHART_INSETS);
    this.m_chart.setAntiAlias(true);
    this.m_chart.setTextAntiAlias(true);
    this.m_chart.setBorderVisible(false);
    this.m_chart.setRenderingHints(GraphicUtils
        .createDefaultRenderingHints());

    title = chart.getTitle();
    if (title != null) {
      this.m_chart.setTitle(title);
      chartTitle = this.m_chart.getTitle();
      chartTitle.setFont(chart.getTitleFont());
      chartTitle.setVisible(true);
    }

    this.m_plot = ((P) (this.m_chart.getPlot()));
    this.m_plot.setBackgroundPaint(Color.WHITE);
    this.m_plot.setBackgroundAlpha(0f);
    this.m_plot.setForegroundAlpha(1f);
    this.m_plot.setOutlineVisible(false);
  }

  /**
   * create the data set
   * 
   * @param chart
   *          the chart
   * @return the data set
   */
  abstract D _createDataSet(final C chart);

  /**
   * create the chart
   * 
   * @param dataset
   *          the data set
   * @param chart
   *          the chart
   * @return the JFreeChart instance
   */
  abstract JFreeChart _createChart(final C chart, final D dataset);

  /**
   * Render this chart
   * 
   * @param graphic
   *          the graphic to render on
   */
  final void _render(final Graphic graphic) {
    final Rectangle2D rect;

    rect = graphic.getBounds();
    if (this.m_legendMode == ELegendMode.CHART_IS_LEGEND) {
      this._paintAsLegend(graphic, rect);
    } else {
      this._paintNormal(graphic, rect);
    }
  }

  /**
   * Render this chart
   * 
   * @param graphic
   *          the graphic to render on
   * @param bounds
   *          the bounds
   */
  void _paintNormal(final Graphics2D graphic, final Rectangle2D bounds) {
    final LegendTitle t;
    final RectangleInsets ri;

    t = this.m_chart.getLegend();
    if ((t != null) && (t.isVisible())) {
      if (bounds.getHeight() < bounds.getWidth()) {
        t.setPosition(RectangleEdge.RIGHT);
      } else {
        t.setPosition(RectangleEdge.BOTTOM);
      }
    }
    ri = this.m_plot.getInsets();

    this.m_plot.setInsets(new RectangleInsets(ri.getTop(), 0d, 0d,//
        ri.getRight()));
    this.m_chart.draw(graphic, bounds);
  }

  /**
   * paint the plot as legend
   * 
   * @param graphics
   *          the graphics
   * @param bounds
   *          the bounds
   */
  void _paintAsLegend(final Graphics2D graphics, final Rectangle2D bounds) {
    final LegendTitle legend;

    legend = this.m_chart.getLegend();
    if ((legend == null) || (!(legend.isVisible()))) {
      this.m_chart.draw(graphics, bounds);
      return;
    }

    legend.setVisible(false);
    this._paintNormal(graphics, bounds);
    this._paintLegend(legend, graphics, bounds,
        this._getLegendVerticalAlignment());
  }

  /**
   * Get the vertical alignment for legends
   * 
   * @return the vertical alignment for legends
   */
  VerticalAlignment _getLegendVerticalAlignment() {
    return VerticalAlignment.TOP;
  }

  /**
   * Calculate bounds for a legend from a bounding rectangle
   * 
   * @param bounds
   *          the bounds
   * @return the legend bounds
   */
  static final Rectangle2D _createLegendBounds(final Rectangle2D bounds) {
    double minX, minY, width, height;

    minX = bounds.getMinX();
    minY = bounds.getMinY();
    width = (bounds.getMaxX() - minX);
    height = (bounds.getMaxY() - minY);

    return new Rectangle2D.Double(//
        minX + (0.05d * width), //
        minY + (0.05d * height),//
        (width * 0.9d), //
        (height * 0.9d));
  }

  /**
   * Creates a rectangle that is aligned to the frame.
   * 
   * @param dimensions
   *          the dimensions for the rectangle.
   * @param frame
   *          the frame to align to.
   * @param hAlign
   *          the horizontal alignment.
   * @param vAlign
   *          the vertical alignment.
   * @return A rectangle.
   */
  static final Rectangle2D _createAlignedRectangle2D(
      final Size2D dimensions, final Rectangle2D frame,
      final HorizontalAlignment hAlign, final VerticalAlignment vAlign) {
    double x, y;

    x = y = Double.NaN;

    if (hAlign == HorizontalAlignment.LEFT) {
      x = frame.getX();
    } else
      if (hAlign == HorizontalAlignment.CENTER) {
        x = frame.getCenterX() - (dimensions.width / 2.0);
      } else
        if (hAlign == HorizontalAlignment.RIGHT) {
          x = frame.getMaxX() - dimensions.width;
        }
    if (vAlign == VerticalAlignment.TOP) {
      y = frame.getY();
    } else
      if (vAlign == VerticalAlignment.CENTER) {
        y = frame.getCenterY() - (dimensions.height / 2.0);
      } else
        if (vAlign == VerticalAlignment.BOTTOM) {
          y = frame.getMaxY() - dimensions.height;
        }

    return new Rectangle2D.Double(x, y, dimensions.width,
        dimensions.height);
  }

  /**
   * paint a legend
   * 
   * @param legend
   *          the legend
   * @param graphics
   *          the graphics
   * @param bounds
   *          the bounds
   * @param verticalAlignment
   *          the vertical alignment
   */
  final void _paintLegend(final LegendTitle legend,
      final Graphics2D graphics, final Rectangle2D bounds,
      final VerticalAlignment verticalAlignment) {
    final Rectangle2D titleArea, inner;
    final BlockParams blockParams;
    final double width, height;
    final RectangleConstraint constraint;
    final Size2D size;

    legend.setVisible(true);
    legend.setBackgroundPaint(this.m_plot.getBackgroundPaint());

    inner = _JFreeChartRenderer._createLegendBounds(bounds);

    blockParams = new BlockParams();
    blockParams.setGenerateEntities(false);

    width = inner.getWidth();
    if (width <= 0.0d) {
      return;
    }
    height = inner.getHeight();
    if (height <= 0.0d) {
      return;
    }

    constraint = new RectangleConstraint(width, new Range(0.0, width),
        LengthConstraintType.RANGE, height, new Range(0.0, height),
        LengthConstraintType.RANGE);

    size = legend.arrange(graphics, constraint);
    titleArea = _JFreeChartRenderer._createAlignedRectangle2D(size, inner,
        legend.getHorizontalAlignment(), verticalAlignment);
    legend.setMargin(_JFreeChartRenderer.LEGEND_MARGIN);
    legend.setPadding(_JFreeChartRenderer.CHART_INSETS);
    legend.draw(graphics, titleArea, blockParams);
  }
}
