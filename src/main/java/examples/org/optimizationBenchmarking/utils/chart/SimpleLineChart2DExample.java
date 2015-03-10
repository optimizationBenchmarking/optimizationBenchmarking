package examples.org.optimizationBenchmarking.utils.chart;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.chart.spec.ILineChart2D;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.math.matrix.impl.LongMatrix1D;
import org.optimizationBenchmarking.utils.math.statistics.Maximum;
import org.optimizationBenchmarking.utils.math.statistics.Minimum;

/**
 * A simple, almost deterministic example for rendering 2D line charts.
 */
public class SimpleLineChart2DExample extends ChartExample {

  /** the globally shared instance */
  public static final SimpleLineChart2DExample INSTANCE = new SimpleLineChart2DExample();

  /** create */
  private SimpleLineChart2DExample() {
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
    SimpleLineChart2DExample.INSTANCE.run(args);
  }

  /** {@inheritDoc} */
  @Override
  public void perform(final IChartSelector selector,
      final StyleSet styles, final Random random) {
    final ELegendMode mode;

    try (final ILineChart2D chart = selector.lineChart2D()) {
      mode = ChartExample.randomLegendMode(random);
      ChartExample.setTitle(chart, ("Simple Line Chart " + mode), //$NON-NLS-1$
          random, styles);
      chart.setLegendMode(mode);

      try (final IAxis axis = chart.xAxis()) {
        axis.setTitle("x-axis");//$NON-NLS-1$
        axis.setMaximumAggregate(Maximum.INSTANCE.createAggregate());
        axis.setMinimumAggregate(Minimum.INSTANCE.createAggregate());
      }
      try (final IAxis axis = chart.yAxis()) {
        axis.setTitle("y-axis");//$NON-NLS-1$
        axis.setMaximumAggregate(Maximum.INSTANCE.createAggregate());
        axis.setMinimumAggregate(Minimum.INSTANCE.createAggregate());
      }

      SimpleLineChart2DExample.__line(chart,
          styles.getMostSimilarColor(Color.red),//
          "red", 0, random);//$NON-NLS-1$

      SimpleLineChart2DExample.__line(chart,
          styles.getMostSimilarColor(Color.blue),//
          "blue", 1, random);//$NON-NLS-1$

      if (random.nextBoolean()) {
        SimpleLineChart2DExample.__line(chart,
            styles.getMostSimilarColor(Color.green),//
            "green", 2, random);//$NON-NLS-1$

        if (random.nextBoolean()) {
          SimpleLineChart2DExample.__line(chart,
              styles.getMostSimilarColor(Color.orange),//
              "orange", 3, random);//$NON-NLS-1$

          if (random.nextBoolean()) {
            SimpleLineChart2DExample.__line(chart,
                styles.getMostSimilarColor(Color.CYAN),//
                "cyan", 4, random);//$NON-NLS-1$

            if (random.nextBoolean()) {
              SimpleLineChart2DExample.__line(chart,
                  styles.getMostSimilarColor(Color.MAGENTA),//
                  "magenta", 5, random);//$NON-NLS-1$
            }
          }
        }
      }
    }
  }

  /**
   * make a line
   * 
   * @param chart
   *          the chart
   * @param color
   *          the color
   * @param name
   *          the name
   * @param rand
   *          the random number generator
   * @param index
   *          the index
   */
  private static final void __line(final ILineChart2D chart,
      final Color color, final String name, final int index,
      final Random rand) {
    final ELineType type;
    final int m, start, end;
    final long[] data;
    String name2;
    int i, j, last;

    try (final ILine2D dest = chart.line()) {

      dest.setColor(color);
      type = ChartExample.randomLineType(rand);

      name2 = color.toString();
      if (name2.equalsIgnoreCase(name)) {
        name2 = name;
      } else {
        name2 = ((((name + ' ') + '(') + name2) + ')');
      }
      dest.setTitle((name2 + ' ') + type);
      dest.setType(type);

      start = 1;
      end = 64;

      last = (end - start);
      m = (2 + rand.nextInt(last));
      last = ((index + 1) * (last + 3));

      data = new long[m << 1];
      j = 0;
      for (i = 1; i <= m; i++) {
        data[j++] = ((i >= m) ? end : ((i <= 1) ? start
            : ((((i - 1) * (end - start)) / m))));
        last += (rand.nextBoolean() ? 1 : (-1));
        data[j++] = last;
      }

      dest.setData(new LongMatrix1D(data, m, 2));
    }
  }

}
