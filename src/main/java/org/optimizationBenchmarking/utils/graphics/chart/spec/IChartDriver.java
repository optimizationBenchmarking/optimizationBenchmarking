package org.optimizationBenchmarking.utils.graphics.chart.spec;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The basic interface for chart drivers */
public interface IChartDriver extends ITextable {

  /**
   * Create a line chart to be painted on the given graphic object. The
   * chart will <em>not</em> close the graphic object once its paining has
   * completed. The chart will fill the complete graphic, i.e., set its
   * extent to
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#getBounds()}
   * .
   * 
   * @param graphic
   *          the graphic to paint on
   * @param styles
   *          the style set to use
   * @return the line chart
   */
  public abstract ILineChart lineChart(final Graphic graphic,
      final StyleSet styles);

  /**
   * Append a descriptive, unique name of the driver
   * 
   * @param textOut
   *          the destination to append to
   */
  @Override
  public abstract void toText(final ITextOutput textOut);
}
