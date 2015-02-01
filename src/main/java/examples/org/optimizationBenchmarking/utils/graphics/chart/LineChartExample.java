package examples.org.optimizationBenchmarking.utils.graphics.chart;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.chart.impl.EChartFormat;
import org.optimizationBenchmarking.utils.graphics.chart.impl.jfree.JFreeChartDriver;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILineChart;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ITitledElement;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;
import org.optimizationBenchmarking.utils.math.random.LoremIpsum;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.MaximumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.MinimumAggregate;
import org.optimizationBenchmarking.utils.math.units.ELength;

import examples.org.optimizationBenchmarking.utils.document.FinishedPrinter;
import examples.org.optimizationBenchmarking.utils.graphics.ColorPaletteExample;
import examples.org.optimizationBenchmarking.utils.graphics.FontPaletteExample;
import examples.org.optimizationBenchmarking.utils.graphics.GraphicConfigExample;
import examples.org.optimizationBenchmarking.utils.graphics.StrokePaletteExample;

/**
 * An example for rendering line charts.
 */
public class LineChartExample {

  /** the chart drivers */
  public static final ArrayListView<IChartDriver> DRIVERS;

  static {
    final LinkedHashSet<IChartDriver> list;

    list = new LinkedHashSet<>();
    list.add(JFreeChartDriver.getInstance());

    for (final EChartFormat format : EChartFormat.values()) {
      list.add(format.getDefaultDriver());
    }

    DRIVERS = new ArrayListView<>(list.toArray(new IChartDriver[list
        .size()]));
  }

  /** get the legend modes */
  private static final ELegendMode[] LEGEND_MODES = ELegendMode.values();
  /** the line types */
  private static final ELineType[] LINE_TYPES = ELineType.values();

  /** the maximum value */
  private static final double MAX_VAL = (0.1d * Double.MAX_VALUE);
  /** the minimum value */
  private static final double MIN_VAL = (-LineChartExample.MAX_VAL);

  /**
   * run the example: there are problems with the different output
   * 
   * @param args
   *          the arguments
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {
    final Path dir;
    final Random rand;
    final PhysicalDimension size;
    long seed;
    Path sub;
    int z, example;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    rand = new Random();

    size = new PhysicalDimension((10 + rand.nextInt(12)),
        (6 + rand.nextInt(8)), ELength.CM);

    for (example = 1; example <= 9; example++) {
      seed = rand.nextLong();
      sub = dir.resolve("example_" + example); //$NON-NLS-1$
      z = 0;
      for (final GraphicConfiguration d : GraphicConfigExample.CONFIGURATIONS) {
        for (final IChartDriver c : LineChartExample.DRIVERS) {
          try (final Graphic g = d
              .createGraphic(
                  sub,//
                  ((((((((LineChartExample.class.getSimpleName() + '_') + example) + '_') + (++z)) + '_') + d
                      .toString()) + '_') + c.getClass().getSimpleName()),//
                  size, new FinishedPrinter(c, d), Logger.getGlobal())) {
            rand.setSeed(seed);
            LineChartExample.randomLineChart(rand, g,
                LineChartExample.randomStyleSet(rand), c);
          }
        }
      }
    }
  }

  /**
   * Create a random style set
   * 
   * @param rand
   *          the random number generator
   * @return the style set
   */
  public static final StyleSet randomStyleSet(final Random rand) {
    return new StyleSet(FontPaletteExample.PALETTES.get(rand
        .nextInt(FontPaletteExample.PALETTES.size())),//
        ColorPaletteExample.PALETTES.get(rand
            .nextInt(ColorPaletteExample.PALETTES.size())),//
        StrokePaletteExample.PALETTES.get(rand
            .nextInt(StrokePaletteExample.PALETTES.size())));//
  }

  /**
   * Render a random line chart to a given graphic
   * 
   * @param rand
   *          the random number generator
   * @param graphic
   *          the graphic
   * @param styles
   *          the styles
   * @param driver
   *          the chart driver to use
   */
  public static final void randomLineChart(final Random rand,
      final Graphic graphic, final StyleSet styles,
      final IChartDriver driver) {
    final double minX, maxX, minY, maxY;
    double a, b, c;

    try (final ILineChart chart = driver.use().setGraphic(graphic)
        .setStyleSet(styles).setLogger(Logger.getGlobal()).create()
        .lineChart()) {
      LineChartExample.__title(chart, rand, styles, null, 8);
      chart.setLegendMode(LineChartExample.LEGEND_MODES[rand
          .nextInt(LineChartExample.LEGEND_MODES.length)]);

      c = Math.exp(rand.nextGaussian() * 4);
      a = ((rand.nextDouble() - 0.5d) * c);
      b = ((rand.nextDouble() - 0.5d) * c);
      minX = Math.min(a, b);
      maxX = Math.max(a, b);

      c = Math.exp(rand.nextGaussian() * 4);
      a = ((rand.nextDouble() - 0.5d) * c);
      b = ((rand.nextDouble() - 0.5d) * c);
      minY = Math.min(a, b);
      maxY = Math.max(a, b);

      try (final IAxis axis = chart.xAxis()) {
        LineChartExample.__axis(axis, rand, styles, minX, maxX);
      }
      try (final IAxis axis = chart.yAxis()) {
        LineChartExample.__axis(axis, rand, styles, minY, maxY);
      }

      do {
        try (final ILine2D line = chart.line()) {
          LineChartExample.__line(line, rand, styles, minX, maxX, minY,
              maxY);
        }
      } while (rand.nextInt(4) > 0);
    }
  }

  /**
   * Set the title of a titled element
   * 
   * @param element
   *          the element
   * @param styles
   *          the style set
   * @param rand
   *          the random number generator
   * @param use
   *          a string to use
   * @param maxLength
   *          the maximum length of the title
   */
  private static final void __title(final ITitledElement element,
      final Random rand, final StyleSet styles, final String use,
      final int maxLength) {
    final String t;

    if (rand.nextBoolean()) {
      if (use != null) {
        t = use;
      } else {
        t = LoremIpsum.loremIpsum(rand, maxLength);
      }
      element.setTitle(t);
      switch (rand.nextInt(6)) {
        case 0: {
          element.setTitleFont(styles.getCodeFont().getFont());
          break;
        }
        case 1: {
          element.setTitleFont(styles.getEmphFont().getFont());
          break;
        }
        case 2: {
          element.setTitleFont(styles.getDefaultFont().getFont());
          break;
        }
        default: {
          /** */
        }
      }
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
      c = LineChartExample.__randomColor(styles, rand);
    }
    LineChartExample.__title(axis, rand, styles,
        ((c != null) ? c.toString() : null), 3);

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
      axis.setGridLineColor(LineChartExample.__randomColor(styles, rand));
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
   * create a random color
   * 
   * @param styles
   *          the styles
   * @param rand
   *          the random number generator
   * @return the color
   */
  private static final Color __randomColor(final StyleSet styles,
      final Random rand) {
    return styles.getMostSimilarColor(//
        (rand.nextInt() & 0x7f7f7f) | 0x070707);
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

    c = LineChartExample.__randomColor(styles, rand);
    LineChartExample.__title(line, rand, styles, c.toString(), 3);
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

    line.setType(LineChartExample.LINE_TYPES[rand
        .nextInt(LineChartExample.LINE_TYPES.length)]);

    x = new double[rand.nextInt(1000) + 2];
    y = new double[x.length];
    LineChartExample.__fill(x, minX, maxX, rand, false);
    LineChartExample.__fill(y, minY, maxY, rand, true);
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

        if ((realMin != realMin) || (realMin <= LineChartExample.MIN_VAL)) {
          realMin = LineChartExample.MIN_VAL;
        }
        if ((realMax != realMax) || (realMax >= LineChartExample.MAX_VAL)) {
          realMax = LineChartExample.MAX_VAL;
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
        LineChartExample.__revert(data, 0, (data.length - 1));
      }

      while (rand.nextInt(7) > 0) {
        LineChartExample.__revert(data, rand.nextInt(data.length),
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
