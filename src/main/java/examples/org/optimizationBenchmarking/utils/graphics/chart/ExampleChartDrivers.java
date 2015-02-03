package examples.org.optimizationBenchmarking.utils.graphics.chart;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.chart.impl.EChartFormat;
import org.optimizationBenchmarking.utils.graphics.chart.impl.jfree.JFreeChartDriver;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;

/** A set of chart drivers which can be used for examples */
public final class ExampleChartDrivers {
  /** the chart drivers */
  public static final ArrayListView<IChartDriver> DRIVERS = ExampleChartDrivers
      .__make();

  /**
   * make the list of example chart drivers
   * 
   * @return the list of example chart drivers
   */
  private static final ArrayListView<IChartDriver> __make() {
    final LinkedHashSet<IChartDriver> list;

    list = new LinkedHashSet<>();
    list.add(JFreeChartDriver.getInstance());

    for (final EChartFormat format : EChartFormat.values()) {
      list.add(format.getDefaultDriver());
    }

    return new ArrayListView<>(list.toArray(new IChartDriver[list.size()]));
  }

  /** the forbidden constructor */
  private ExampleChartDrivers() {
    ErrorUtils.doNotCall();
  }
}
