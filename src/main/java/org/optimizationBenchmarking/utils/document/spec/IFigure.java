package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILineChart;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/**
 * The interface to create and draw on figures.
 */
public interface IFigure extends IDocumentElement, ILabeledObject,
    IChartSelector {

  /**
   * write the figure caption
   * 
   * @return the complex text to write the figure caption
   */
  public abstract IComplexText caption();

  /**
   * Use the figure to directly paint a raw graphic. If you choose to paint
   * a graphic directly, you cannot paint a
   * {@link org.optimizationBenchmarking.utils.graphics.chart.spec.IChartSelector
   * chart}.
   * 
   * @return the figure body to paint on
   */
  public abstract Graphic graphic();

  /**
   * Create a line chart. If you create a chart, you cannot draw a
   * {@link #graphic() raw graphic} anymore.
   * 
   * @return the line chart
   */
  @Override
  public abstract ILineChart lineChart();
}
