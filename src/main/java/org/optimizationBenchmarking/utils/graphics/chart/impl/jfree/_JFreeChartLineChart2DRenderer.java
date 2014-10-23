package org.optimizationBenchmarking.utils.graphics.chart.impl.jfree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.block.BlockParams;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.UnitType;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.chart.impl.abstr.Axis;
import org.optimizationBenchmarking.utils.graphics.chart.impl.abstr.Line2D;
import org.optimizationBenchmarking.utils.graphics.chart.impl.abstr.LineChart;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/**
 * <p>
 * The 2d line chart as JFreeChar-facade.
 * </p>
 */
final class _JFreeChartLineChart2DRenderer {

  /** the default insets */
  private static final RectangleInsets CHART_INSETS = RectangleInsets.ZERO_INSETS;
  /** the default insets */
  private static final RectangleInsets LEGEND_MARGIN = new RectangleInsets(
      UnitType.ABSOLUTE, 0.5d, 0.5d, 0.5d, 0.5d);

  /** the empty shape */
  private static final Shape EMPTY_SHAPE = new java.awt.geom.Line2D.Float(
      0f, 0f, 0f, 0f);

  /** the line shape */
  private static final Shape LINE_SHAPE = new java.awt.geom.Line2D.Float(
      0f, 0f, 8f, 0f);

  /** the tick unit source */
  private static final TickUnitSource UNITS = NumberAxis
      .createStandardTickUnits(Locale.US);

  /** the internal data set */
  private final _JFreeChartLines2D m_dataset;

  /** the chart */
  private final JFreeChart m_chart;

  /** the plot */
  private final XYPlot m_plot;

  /** the x-axis */
  private final NumberAxis m_x;

  /** the y-axis */
  private final NumberAxis m_y;

  /** the legend mode */
  private final ELegendMode m_legendMode;

  /**
   * Create a new line chart
   * 
   * @param chart
   *          the chart to paint
   */
  _JFreeChartLineChart2DRenderer(final LineChart chart) {
    super();

    final _JFreeChartXYLineAndShapeRenderer renderer;
    final LegendItemCollection legendCollection;
    final Axis x, y;
    final LegendTitle legend;
    TextTitle chartTitle;
    double min, max;
    LegendItem legendItem;
    String title;

    this.m_dataset = new _JFreeChartLines2D(chart.getLines());
    this.m_chart = ChartFactory.createXYLineChart(null, null, null,
        this.m_dataset, PlotOrientation.VERTICAL, false, false, false);

    this.m_chart.setBackgroundPaint(Color.WHITE);
    this.m_chart.setPadding(_JFreeChartLineChart2DRenderer.CHART_INSETS);
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

    this.m_plot = this.m_chart.getXYPlot();
    this.m_plot.setBackgroundPaint(Color.WHITE);
    this.m_plot.setBackgroundAlpha(0f);
    this.m_plot.setForegroundAlpha(1f);
    this.m_plot.setOutlineVisible(false);

    renderer = new _JFreeChartXYLineAndShapeRenderer(chart.getLines());
    this.m_plot.setRenderer(renderer);
    this.m_plot.setRenderer(0, renderer);

    x = chart.getXAxis();
    y = chart.getYAxis();
    this.m_plot.setDomainGridlinesVisible(true);
    this.m_plot.setDomainGridlinePaint(x.getGridLineColor());
    this.m_plot.setDomainGridlineStroke(x.getGridLineStroke());
    this.m_plot.setRangeGridlinesVisible(true);
    this.m_plot.setRangeGridlinePaint(y.getGridLineColor());
    this.m_plot.setRangeGridlineStroke(y.getGridLineStroke());

    this.m_plot.setAxisOffset(_JFreeChartLineChart2DRenderer.CHART_INSETS);
    this.m_plot.setDomainMinorGridlinesVisible(false);
    this.m_plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
    this.m_plot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);

    this.m_x = ((NumberAxis) (this.m_plot.getDomainAxis()));
    this.m_x.setAxisLineStroke(x.getAxisStroke());
    this.m_x.setAxisLinePaint(x.getAxisColor());
    title = x.getTitle();
    if (title != null) {
      this.m_x.setLabel(title);
      this.m_x.setLabelFont(x.getTitleFont());
      this.m_x.setLabelPaint(x.getAxisColor());
    }
    this.m_x.setTickMarksVisible(true);
    this.m_x.setTickLabelsVisible(true);
    this.m_x.setStandardTickUnits(_JFreeChartLineChart2DRenderer.UNITS);
    this.m_x.setTickLabelFont(x.getTickFont());
    this.m_x.setTickLabelPaint(x.getAxisColor());
    this.m_x.setTickMarkPaint(x.getAxisColor());
    this.m_x.setTickMarkStroke(x.getAxisStroke());
    this.m_x.setMinorTickCount(1);
    this.m_x.setMinorTickMarkInsideLength(0f);
    this.m_x.setMinorTickMarkOutsideLength(0f);
    this.m_x.setMinorTickMarksVisible(false);

    this.m_x.setAutoRange(false);
    min = x.getMinimum();
    max = x.getMaximum();
    this.m_x.setLowerBound(min);
    this.m_x.setUpperBound(max);
    this.m_x.setRange(min, max);

    this.m_y = ((NumberAxis) (this.m_plot.getRangeAxis()));
    this.m_y.setAxisLineStroke(y.getAxisStroke());
    this.m_y.setAxisLinePaint(y.getAxisColor());
    title = y.getTitle();
    if (title != null) {
      this.m_y.setLabel(title);
      this.m_y.setLabelFont(y.getTitleFont());
      this.m_y.setLabelPaint(y.getAxisColor());
    }
    this.m_y.setTickMarksVisible(true);
    this.m_y.setTickLabelsVisible(true);
    this.m_y.setStandardTickUnits(_JFreeChartLineChart2DRenderer.UNITS);
    this.m_y.setTickLabelFont(y.getTickFont());
    this.m_y.setTickLabelPaint(y.getAxisColor());
    this.m_y.setTickMarkPaint(y.getAxisColor());
    this.m_y.setTickMarkStroke(y.getAxisStroke());
    this.m_y.setMinorTickCount(1);
    this.m_y.setMinorTickMarkInsideLength(0f);
    this.m_y.setMinorTickMarkOutsideLength(0f);
    this.m_y.setMinorTickMarksVisible(false);
    min = y.getMinimum();
    max = y.getMaximum();
    this.m_y.setLowerBound(min);
    this.m_y.setUpperBound(max);
    this.m_y.setRange(min, max);

    this.m_legendMode = chart.getLegendMode();

    legendCollection = new LegendItemCollection();
    if (this.m_legendMode.isLegendShown()) {
      for (final Line2D line : chart.getLines()) {
        title = line.getTitle();
        if (title != null) {
          legendItem = new LegendItem(title, null, null, null, // text
              false, _JFreeChartLineChart2DRenderer.EMPTY_SHAPE, false,// shape
              line.getColor(),//
              false,//
              line.getColor(),//
              line.getStroke(),//
              false, _JFreeChartLineChart2DRenderer.EMPTY_SHAPE,// line
              line.getStroke(),//
              line.getColor()//
          );
          legendItem.setLabelFont(line.getTitleFont());
          legendItem.setLabelPaint(line.getColor());
          legendItem.setLineVisible(true);
          legendItem.setLine(_JFreeChartLineChart2DRenderer.LINE_SHAPE);
          legendCollection.add(legendItem);
        }
      }
    }
    this.m_plot.setFixedLegendItems(legendCollection);

    if (this.m_legendMode.isLegendShown()) {
      legend = new LegendTitle(this.m_plot);
      legend.setBackgroundPaint(Color.WHITE);
      legend.setVisible(true);
      this.m_chart.addLegend(legend);
    }
  }

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
      this.__paintAsLegend(graphic, rect);
    } else {
      this.__paintNormal(graphic, rect);
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
  private final void __paintNormal(final Graphics2D graphic,
      final Rectangle2D bounds) {
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
  private static final Rectangle2D __createAlignedRectangle2D(
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
   */
  private static final void __paintLegend(final LegendTitle legend,
      final Graphics2D graphics, final Rectangle2D bounds) {
    final Rectangle2D titleArea;
    final BlockParams p;
    final double ww, hh;
    final RectangleConstraint constraint;
    final Size2D size;

    p = new BlockParams();
    p.setGenerateEntities(false);

    ww = bounds.getWidth();
    if (ww <= 0.0d) {
      return;
    }
    hh = bounds.getHeight();
    if (hh <= 0.0d) {
      return;
    }

    constraint = new RectangleConstraint(ww, new Range(0.0, ww),
        LengthConstraintType.RANGE, hh, new Range(0.0, hh),
        LengthConstraintType.RANGE);

    size = legend.arrange(graphics, constraint);
    titleArea = _JFreeChartLineChart2DRenderer.__createAlignedRectangle2D(
        size, bounds, legend.getHorizontalAlignment(),
        VerticalAlignment.TOP);
    legend.setMargin(_JFreeChartLineChart2DRenderer.LEGEND_MARGIN);
    legend.setPadding(_JFreeChartLineChart2DRenderer.CHART_INSETS);
    legend.draw(graphics, titleArea, p);
  }

  /**
   * paint this chart as legend
   * 
   * @param graphics
   *          the graphics
   * @param bounds
   *          the bounds
   */
  private final void __paintAsLegend(final Graphics2D graphics,
      final Rectangle2D bounds) {
    final LegendTitle legend;
    final _JFreeChartXYItemRenderer render;
    final XYItemRenderer old;
    Rectangle2D r;
    double w, h;

    legend = this.m_chart.getLegend();
    if ((legend == null) || (!(legend.isVisible()))) {
      this.m_chart.draw(graphics, bounds);
      return;
    }

    legend.setVisible(false);
    old = this.m_plot.getRenderer();
    try {
      render = new _JFreeChartXYItemRenderer(old);
      this.m_plot.setRenderer(render);
      this.m_plot.setRenderer(0, render);
      this.__paintNormal(graphics, bounds);
      legend.setVisible(true);

      legend.setBackgroundPaint(this.m_plot.getBackgroundPaint());
      w = (render.m_maxX - render.m_minX);
      h = (render.m_maxY - render.m_minY);
      r = new Rectangle2D.Double(//
          render.m_minX + (0.05d * w), //
          render.m_minY + (0.05d * h),//
          w * 0.9d, //
          h * 0.9d);

      _JFreeChartLineChart2DRenderer.__paintLegend(legend, graphics, r);
    } finally {
      this.m_plot.setRenderer(old);
    }
  }
}
