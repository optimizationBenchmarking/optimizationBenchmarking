package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.Color;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataScalar;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledPieChart;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * <p>
 * The pie chart as JFreeChar-facade.
 * </p>
 */
final class _JFreeChartPieChartRenderer
    extends
    _JFreeChartRenderer<CompiledPieChart, _JFreeChartPieDataset, PiePlot3D> {

  /**
   * Create a new line chart
   * 
   * @param chart
   *          the chart to paint
   */
  _JFreeChartPieChartRenderer(final CompiledPieChart chart) {
    super(chart);

    final _JFreeChartPieDataset data;
    final ArrayListView<CompiledDataScalar> elements;
    final int size;
    Color color;
    int index;
    CompiledDataScalar element;
    Comparable<?> key;

    this.m_plot.setAutoPopulateSectionOutlineStroke(false);
    this.m_plot.setAutoPopulateSectionOutlinePaint(false);
    this.m_plot.setAutoPopulateSectionPaint(false);
    this.m_plot.setCircular(true);
    this.m_plot.setDarkerSides(true);
    this.m_plot.setDepthFactor(0.09d);
    this.m_plot.setShadowXOffset(1d);

    data = this.m_dataset;
    elements = chart.getSlices();
    size = elements.size();
    for (index = 0; index < size; index++) {
      element = elements.get(index);
      key = data.getKey(index);
      color = element.getColor();
      this.m_plot.setSectionPaint(key, color);
      this.m_plot.setSectionOutlinePaint(key, color.darker().darker());
      this.m_plot.setSectionOutlineStroke(key, element.getStroke());
      this.m_plot.setSectionOutlinesVisible(true);
    }
  }

  /** {@inheritDoc} */
  @Override
  final _JFreeChartPieDataset _createDataSet(final CompiledPieChart chart) {
    return new _JFreeChartPieDataset(chart.getSlices());
  }

  /** {@inheritDoc} */
  @Override
  final JFreeChart _createChart(final CompiledPieChart chart,
      final _JFreeChartPieDataset dataset) {
    return ChartFactory.createPieChart3D(null, this.m_dataset,
        this.m_legendMode.isLegendShown(), false, Locale.US);
  }
}
