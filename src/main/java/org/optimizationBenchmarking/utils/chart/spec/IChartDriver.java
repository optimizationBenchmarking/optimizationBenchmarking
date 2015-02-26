package org.optimizationBenchmarking.utils.chart.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/** The basic interface for chart drivers */
public interface IChartDriver extends ITool {

  /** {@inheritDoc} */
  @Override
  public abstract IChartBuilder use();

}
