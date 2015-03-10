package examples.org.optimizationBenchmarking.utils.chart;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** All the chart examples */
public class AllChartExamples {

  /** all the chart examples */
  public static final ArrayListView<ChartExample> ALL_CHART_EXAMPLES//
  = new ArrayListView<>(new ChartExample[] {//
      RandomLineChart2DExample.INSTANCE,//
          SimpleLineChart2DExample.INSTANCE,//
          SimplePieChartExample.INSTANCE,//
      });

}
