package org.optimizationBenchmarking.utils.chart.spec;

import java.io.Closeable;

/** The basic interface for chart components */
public interface IChartElement extends Closeable {

  /** Close the chart item: all of its data has been set. */
  @Override
  public abstract void close();

}
