package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.VerticalAlignment;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledAxis;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledLine2D;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledLineChart2D;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * <p>
 * The 2d line chart as JFreeChar-facade.
 * </p>
 */
final class _JFreeChartLineChart2DRenderer extends
    _JFreeChartRenderer<CompiledLineChart2D, _JFreeChartXYDataset, XYPlot> {

  /** the tick unit source */
  private static final TickUnitSource UNITS = NumberAxis
      .createStandardTickUnits(Locale.US);

  /** the x-axis */
  private final NumberAxis m_x;

  /** the y-axis */
  private final NumberAxis m_y;

  /**
   * Create a new line chart
   *
   * @param chart
   *          the chart to paint
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  _JFreeChartLineChart2DRenderer(final CompiledLineChart2D chart) {
    super(chart);

    final _JFreeChartXYLineAndShapeRenderer renderer;
    final LegendItemCollection legendCollection;
    final CompiledAxis x, y;
    final LegendTitle legend;
    double min, max;
    LegendItem legendItem;
    String title;

    renderer = new _JFreeChartXYLineAndShapeRenderer(
        (ArrayListView) (chart.getLines()));
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

    this.m_plot.setAxisOffset(_JFreeChartRenderer.CHART_INSETS);
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

    legendCollection = new LegendItemCollection();
    if (this.m_legendMode.isLegendShown()) {
      for (final CompiledLine2D line : chart.getLines()) {
        title = line.getTitle();
        if (title != null) {
          legendItem = new LegendItem(title, null, null, null, // text
              false, _JFreeChartRenderer.EMPTY_SHAPE, false,// shape
              line.getColor(),//
              false,//
              line.getColor(),//
              line.getStroke(),//
              false, _JFreeChartRenderer.EMPTY_SHAPE,// line
              line.getStroke(),//
              line.getColor()//
          );
          legendItem.setLabelFont(line.getTitleFont());
          legendItem.setLabelPaint(line.getColor());
          legendItem.setLineVisible(true);
          legendItem.setLine(_JFreeChartRenderer.LINE_SHAPE);
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

  /** {@inheritDoc} */
  @Override
  final _JFreeChartXYDataset _createDataSet(final CompiledLineChart2D chart) {
    return new _JFreeChartXYDataset(chart.getLines());
  }

  /** {@inheritDoc} */
  @Override
  final JFreeChart _createChart(final CompiledLineChart2D chart,
      final _JFreeChartXYDataset dataset) {
    return ChartFactory.createXYLineChart(null, null, null,
        this.m_dataset, PlotOrientation.VERTICAL, false, false, false);
  }

  /** {@inheritDoc} */
  @Override
  final void _paintAsLegend(final Graphics2D graphics,
      final Rectangle2D bounds) {
    final LegendTitle legend;
    final _JFreeChartXYItemRenderer render;
    final XYItemRenderer old;

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
      this._paintNormal(graphics, bounds);

      this._paintLegend(legend, graphics, //
          new Rectangle2D.Double(render.m_minX, render.m_minY,//
              (render.m_maxX - render.m_minX),//
              (render.m_maxY - render.m_minY)),//
          VerticalAlignment.TOP);
    } finally {
      this.m_plot.setRenderer(old);
    }
  }
}
