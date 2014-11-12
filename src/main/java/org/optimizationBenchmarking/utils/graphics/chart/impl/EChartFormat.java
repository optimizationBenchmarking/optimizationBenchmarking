package org.optimizationBenchmarking.utils.graphics.chart.impl;

import org.optimizationBenchmarking.utils.graphics.chart.impl.jfree.JFreeChartDriver;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;

/** An enumeration of available chart formats */
public enum EChartFormat {

  /** the JFreeChart chart format */
  JFreeChart() {
    /** {@inheritDoc} */
    @Override
    public final IChartDriver getDefaultDriver() {
      return JFreeChartDriver.getInstance();
    }
  };

  /** the default chart format */
  public static final EChartFormat DEFAULT = JFreeChart;

  /**
   * Get the chart driver corresponding to this format
   * 
   * @return the chart driver
   */
  public abstract IChartDriver getDefaultDriver();
}
