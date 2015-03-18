package test.junit.org.optimizationBenchmarking.utils.chart;

import org.optimizationBenchmarking.utils.chart.impl.jfree.JFreeChartDriver;

/** Test whether JFreeChart can be used */
public class JFreeChartDriverTest extends ChartDriverTest {

  /** create */
  public JFreeChartDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected JFreeChartDriver getInstance() {
    return JFreeChartDriver.getInstance();
  }

}
