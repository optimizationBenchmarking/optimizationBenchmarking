package examples.org.optimizationBenchmarking.utils.chart;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import org.optimizationBenchmarking.utils.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.chart.spec.ILineChart2D;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.MaximumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.MinimumAggregate;

/**
 * A random example for rendering 2D line charts.
 */
public class RandomLineChart2DExample extends ChartExample {

  /** the globally shared instance */
  public static final RandomLineChart2DExample INSTANCE = new RandomLineChart2DExample();

  /** the maximum value */
  private static final double MAX_VAL = (0.1d * Double.MAX_VALUE);
  /** the minimum value */
  private static final double MIN_VAL = (-RandomLineChart2DExample.MAX_VAL);

  /** create */
  private RandomLineChart2DExample() {
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
    RandomLineChart2DExample.INSTANCE.run(args);
  }

  /** {@inheritDoc} */
  @Override
  public void perform(final IChartSelector selector,
      final StyleSet styles, final Random random) {
    final int minX, maxX, minY, maxY;

    try (final ILineChart2D chart = selector.lineChart2D()) {

      ChartExample.setTitle(chart, null, random, styles);
      chart.setLegendMode(ChartExample.randomLegendMode(random));

      minX = (random.nextInt(50) - 25);
      maxX = (minX + 1 + random.nextInt(100));

      minY = (random.nextInt(50) - 25);
      maxY = (minY + 1 + random.nextInt(100));

      try (final IAxis axis = chart.xAxis()) {
        RandomLineChart2DExample.__axis(axis, random, styles, minX, maxX);
      }
      try (final IAxis axis = chart.yAxis()) {
        RandomLineChart2DExample.__axis(axis, random, styles, minY, maxY);
      }

      do {
        try (final ILine2D line = chart.line()) {
          RandomLineChart2DExample.__line(line, random, styles, minX,
              maxX, minY, maxY);
        }
      } while (random.nextInt(4) > 0);
    }
  }

  /**
   * Setup the axis
   * 
   * @param axis
   *          the axis
   * @param styles
   *          the style set
   * @param rand
   *          the random number generator
   * @param min
   *          the minimum
   * @param max
   *          the maximum
   */
  private static final void __axis(final IAxis axis, final Random rand,
      final StyleSet styles, final double min, final double max) {
    Color c;

    c = null;
    if (rand.nextInt(20) <= 0) {
      c = ChartExample.randomColor(styles, rand);
    }
    ChartExample.setTitle(axis, ((c != null) ? c.toString() : null), rand,
        styles);

    switch (rand.nextInt(30)) {
      case 0: {
        axis.setAxisStroke(styles.getThinStroke());
        break;
      }
      case 1: {
        axis.setAxisStroke(styles.getThickStroke());
        break;
      }
      case 2: {
        axis.setAxisStroke(styles.getDefaultStroke());
        break;
      }
      default: {
        //
      }
    }

    if (rand.nextInt(20) <= 0) {
      axis.setGridLineColor(ChartExample.randomColor(styles, rand));
    }

    switch (rand.nextInt(30)) {
      case 0: {
        axis.setGridLineStroke(styles.getThinStroke());
        break;
      }
      case 1: {
        axis.setGridLineStroke(styles.getThickStroke());
        break;
      }
      case 2: {
        axis.setGridLineStroke(styles.getDefaultStroke());
        break;
      }
      default: {
        //
      }
    }

    switch (rand.nextInt(9)) {
      case 0: {
        axis.setTickFont(styles.getCodeFont().getFont());
        break;
      }
      case 1: {
        axis.setTickFont(styles.getEmphFont().getFont());
        break;
      }
      case 2: {
        axis.setTickFont(styles.getDefaultFont().getFont());
        break;
      }
      default: {
        /** */
      }
    }

    if (rand.nextBoolean()) {
      axis.setMinimum(min);
    } else {
      axis.setMinimumAggregate(new MinimumAggregate());
    }

    if (rand.nextBoolean()) {
      axis.setMaximum(max);
    } else {
      axis.setMaximumAggregate(new MaximumAggregate());
    }

  }

  /**
   * Setup the line
   * 
   * @param line
   *          the line
   * @param styles
   *          the style set
   * @param rand
   *          the random number generator
   * @param minX
   *          the minimum x
   * @param maxX
   *          the maximum x
   * @param minY
   *          the minimum y
   * @param maxY
   *          the maximum y
   */
  private static final void __line(final ILine2D line, final Random rand,
      final StyleSet styles, final double minX, final double maxX,
      final double minY, final double maxY) {
    final Color c;
    final double[] x, y;
    final double[][] data;
    int i;

    c = ChartExample.randomColor(styles, rand);
    ChartExample.setTitle(line, c.toString(), rand, styles);
    line.setColor(c);

    switch (rand.nextInt(10)) {
      case 0: {
        line.setStroke(styles.getThinStroke());
        break;
      }
      case 1: {
        line.setStroke(styles.getThickStroke());
        break;
      }
      case 2: {
        line.setStroke(styles.getDefaultStroke());
        break;
      }
      default: {
        //
      }
    }

    if (rand.nextBoolean()) {
      line.setStart(Double.NEGATIVE_INFINITY,
          (rand.nextBoolean() ? Double.NEGATIVE_INFINITY
              : Double.POSITIVE_INFINITY));
    }
    if (rand.nextBoolean()) {
      line.setEnd(Double.POSITIVE_INFINITY,
          (rand.nextBoolean() ? Double.NEGATIVE_INFINITY
              : Double.POSITIVE_INFINITY));
    }

    line.setType(ChartExample.randomLineType(rand));

    x = new double[rand.nextInt(1000) + 2];
    y = new double[x.length];
    RandomLineChart2DExample.__fill(x, minX, maxX, rand, false);
    RandomLineChart2DExample.__fill(y, minY, maxY, rand, true);
    data = new double[x.length][2];
    for (i = x.length; (--i) >= 0;) {
      data[i][0] = x[i];
      data[i][1] = y[i];
    }
    line.setData(new DoubleMatrix2D(data));
  }

  /**
   * fill a data array
   * 
   * @param data
   *          the array
   * @param min
   *          the minimum
   * @param max
   *          the maximum
   * @param rand
   *          the randomizer
   * @param canReverse
   *          can sub-sections be reversed?
   */
  private static final void __fill(final double[] data, final double min,
      final double max, final Random rand, final boolean canReverse) {
    int i, j, k, div;
    double realMin, realMax, temp, range;

    realMax = max;
    realMin = min;

    findBounds: {
      for (i = 1000; (--i) >= 0;) {

        if (realMin > realMax) {
          temp = realMin;
          realMin = realMax;
          realMax = realMin;
        }

        if (realMax <= realMin) {
          if (realMin < 0d) {
            realMin = (1.1d * realMin);
          } else {
            realMin = (0.9d * realMin);
          }
          realMin -= 1d;
          realMin = Math.nextAfter(realMin, Double.NEGATIVE_INFINITY);

          if (realMax < 0d) {
            realMax = (0.9d * realMax);
          } else {
            realMax = (1.1d * realMax);
          }
          realMax += 1d;
          realMax = Math.nextUp(realMax);
        }

        if ((realMin != realMin)
            || (realMin <= RandomLineChart2DExample.MIN_VAL)) {
          realMin = RandomLineChart2DExample.MIN_VAL;
        }
        if ((realMax != realMax)
            || (realMax >= RandomLineChart2DExample.MAX_VAL)) {
          realMax = RandomLineChart2DExample.MAX_VAL;
        }
        if (realMin > realMax) {
          temp = realMin;
          realMin = realMax;
          realMax = realMin;
        }
        range = (realMax - realMin);
        if ((range < Double.MAX_VALUE) && (range == range)) {
          break findBounds;
        }
      }

      // we could not sanitize the bounds, so let's use random data from
      // [0,1)
      for (i = data.length; (--i) >= 0;) {
        data[i] = rand.nextDouble();
      }
      return;
    }

    data[0] = realMin;
    div = (data.length - 1);
    data[div] = realMax;
    if (div > 1) {
      for (i = div; (--i) > 0;) {
        data[i] = ((realMin + ((i * range) / div)));
      }
    }

    for (;;) {

      if ((data.length > 2) && (rand.nextBoolean())) {
        for (i = data.length; (--i) >= 0;) {
          j = (1 + rand.nextInt(data.length - 2));
          temp = (0.2d + (0.6d * rand.nextDouble()));
          data[j] = ((temp * data[j - 1]) + ((1d - temp) * data[j + 1]));
        }

        if (rand.nextBoolean()) {
          break;
        }
      }

      if (rand.nextBoolean()) {
        j = rand.nextInt(data.length);
        for (k = 2; (--k) >= 0;) {
          for (i = data.length; (--i) >= 0;) {

            if (rand.nextInt(5) <= 0) {
              j = rand.nextInt(data.length);
            }

            temp = ((rand.nextGaussian() * (0.01d * range)) + data[j]);
            if ((temp >= realMin) && (temp <= realMax)) {
              data[i] = temp;
            }
          }
        }

        if (rand.nextBoolean()) {
          break;
        }
      }
    }

    Arrays.sort(data); // just in case

    if (canReverse) {
      if (rand.nextBoolean()) {
        RandomLineChart2DExample.__revert(data, 0, (data.length - 1));
      }

      while (rand.nextInt(7) > 0) {
        RandomLineChart2DExample.__revert(data, rand.nextInt(data.length),
            rand.nextInt(data.length));
      }
    }
  }

  /**
   * revert a part of a data array
   * 
   * @param data
   *          the data
   * @param i
   *          the start index
   * @param j
   *          the end index
   */
  private static final void __revert(final double[] data, final int i,
      final int j) {
    int ii, jj;
    double d;

    if (i > j) {
      ii = j;
      jj = i;
    } else {
      ii = i;
      jj = j;
    }
    while (ii < jj) {
      d = data[ii];
      data[ii] = data[jj];
      data[jj] = d;
      ii++;
      jj--;
    }
  }

}
