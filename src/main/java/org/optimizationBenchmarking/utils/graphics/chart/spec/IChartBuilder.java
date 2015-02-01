package org.optimizationBenchmarking.utils.graphics.chart.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/** Build a chart */
public interface IChartBuilder extends IToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IChartBuilder setLogger(final Logger logger);

  /**
   * Set the graphic to paint on
   * 
   * @param graphic
   *          the graphic to paint
   * @return this builder
   */
  public abstract IChartBuilder setGraphic(final Graphic graphic);

  /**
   * Set the style set to use for the diagram
   * 
   * @param styleSet
   *          the style set to use for the diagram
   * @return this builder
   */
  public abstract IChartBuilder setStyleSet(final StyleSet styleSet);

  /**
   * Create the chart selector.
   * 
   * @return the chart selector
   */
  @Override
  public abstract IChartSelector create();
}
