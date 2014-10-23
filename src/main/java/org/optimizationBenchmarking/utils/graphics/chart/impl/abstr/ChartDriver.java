package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILineChart;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** the chart driver base class */
public abstract class ChartDriver extends HashObject implements
    IChartDriver {

  /**
   * the chart driver
   */
  protected ChartDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ILineChart lineChart(final Graphic graphic,
      final StyleSet styles) {
    styles.initialize(graphic);
    return new _LineChartBuilder(graphic, styles, this);
  }

  /**
   * Draw the {@link #lineChart(Graphic, StyleSet) line chart} after the
   * chart has been closed and all data is set.
   * 
   * @param graphic
   *          the graphic
   * @param chart
   *          the chart to render
   */
  protected abstract void renderLineChart(final Graphic graphic,
      final LineChart chart);

  /** {@inheritDoc} */
  @Override
  public String toString() {
    final MemoryTextOutput mo;

    mo = new MemoryTextOutput(256);
    this.toText(mo);
    return mo.toString();
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.toString());
  }
}
