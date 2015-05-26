package examples.org.optimizationBenchmarking.utils.chart;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/** All the chart examples */
public class AllChartExamples {

  /** all the chart examples */
  public static final ArrayListView<ChartExample> ALL_CHART_EXAMPLES//
  = new ArrayListView<>(new ChartExample[] {//
      RandomLineChart2DExample.INSTANCE,//
          SimpleLineChart2DExample.INSTANCE,//
          SimplePieChartExample.INSTANCE,//
      });

  /**
   * The main method
   *
   * @param args
   *          the command line arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static void main(final String[] args) throws Throwable {
    final Path dir;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    for (final ChartExample example : AllChartExamples.ALL_CHART_EXAMPLES) {
      example.run(new String[] { PathUtils.getPhysicalPath(//
          dir.resolve(example.getClass().getSimpleName()), false) });
    }
  }
}
