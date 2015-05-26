package examples.org.optimizationBenchmarking.utils.chart;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.chart.spec.IDataScalar;
import org.optimizationBenchmarking.utils.chart.spec.IPieChart;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;

/**
 * A simple, almost deterministic example for rendering pie charts.
 */
public class SimplePieChartExample extends ChartExample {

  /** the globally shared instance */
  public static final SimplePieChartExample INSTANCE = new SimplePieChartExample();

  /** create */
  private SimplePieChartExample() {
    super();
  }

  /**
   * run the example: there are problems with the different output
   *
   * @param args
   *          the arguments
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {
    SimplePieChartExample.INSTANCE.run(args);
  }

  /** {@inheritDoc} */
  @Override
  public void perform(final IChartSelector selector,
      final StyleSet styles, final Random random) {
    final ELegendMode mode;
    final boolean dataMode;
    final int count;

    try (final IPieChart chart = selector.pieChart()) {
      mode = ChartExample.randomLegendMode(random);
      ChartExample.setTitle(chart, ("Simple Pie Chart " + mode), //$NON-NLS-1$
          random, styles);
      chart.setLegendMode(mode);

      dataMode = random.nextBoolean();
      count = random.nextInt(6);

      SimplePieChartExample.__slice(chart,
          styles.getMostSimilarColor(Color.red),//
          "red", dataMode, random, styles);//$NON-NLS-1$

      SimplePieChartExample.__slice(chart,
          styles.getMostSimilarColor(Color.blue),//
          "blue", dataMode, random, styles);//$NON-NLS-1$

      if (count > 0) {
        SimplePieChartExample.__slice(chart,
            styles.getMostSimilarColor(Color.green),//
            "green", dataMode, random, styles);//$NON-NLS-1$

        if (count > 1) {
          SimplePieChartExample.__slice(chart,
              styles.getMostSimilarColor(Color.orange),//
              "orange", dataMode, random, styles);//$NON-NLS-1$

          if (count > 2) {
            SimplePieChartExample.__slice(chart,
                styles.getMostSimilarColor(Color.CYAN),//
                "cyan", dataMode, random, styles);//$NON-NLS-1$

            if (count > 3) {
              SimplePieChartExample.__slice(chart,
                  styles.getMostSimilarColor(Color.MAGENTA),//
                  "magenta", dataMode, random, styles);//$NON-NLS-1$
              if (count > 3) {
                SimplePieChartExample.__slice(chart,
                    styles.getMostSimilarColor(Color.PINK),//
                    "pink", dataMode, random, styles);//$NON-NLS-1$
              }

            }
          }
        }
      }
    }
  }

  /**
   * make a pie slice
   *
   * @param chart
   *          the chart
   * @param color
   *          the color
   * @param name
   *          the name
   * @param rand
   *          the random number generator
   * @param dataMode
   *          the data mode
   * @param styles
   *          the style set
   */
  private static final void __slice(final IPieChart chart,
      final Color color, final String name, final boolean dataMode,
      final Random rand, final StyleSet styles) {
    String name2;

    try (final IDataScalar dest = chart.slice()) {
      dest.setColor(color);

      name2 = color.toString();
      if (name2.equalsIgnoreCase(name)) {
        name2 = name;
      } else {
        name2 = ((((name + ' ') + '(') + name2) + ')');
      }
      ChartExample.setTitle(dest, name2, rand, styles);

      if (dataMode) {
        dest.setData(rand.nextInt(100));
      } else {
        dest.setData(rand.nextDouble());
      }
    }
  }
}
