package examples.org.optimizationBenchmarking.utils.graphics.chart;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;

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
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.MaximumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.MinimumAggregate;
import org.optimizationBenchmarking.utils.math.units.ELength;

import examples.org.optimizationBenchmarking.LoremIpsum;
import examples.org.optimizationBenchmarking.utils.document.FinishedPrinter;
import examples.org.optimizationBenchmarking.utils.graphics.ColorPaletteExample;
import examples.org.optimizationBenchmarking.utils.graphics.FontPaletteExample;
import examples.org.optimizationBenchmarking.utils.graphics.GraphicsExample;
import examples.org.optimizationBenchmarking.utils.graphics.StrokePaletteExample;

/**
 * An example for rendering line charts.
 */
public class LineChartExample {

  /** the graphic driver to use */
  private static final ArrayListView<IGraphicDriver> GRAPHIC_DRIVERS = GraphicsExample.DRIVERS;

  /** the chart drivers */
  public static final ArrayListView<IChartDriver> DRIVERS;

  static {
    final LinkedHashSet<IChartDriver> list;

    list = new LinkedHashSet<>();
    list.add(JFreeChartDriver.INSTANCE);

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
    final long seed;
    final PhysicalDimension size;
    int z;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    rand = new Random();

    size = new PhysicalDimension((10 + rand.nextInt(12)),
        (6 + rand.nextInt(8)), ELength.CM);

    seed = rand.nextLong();
    z = 0;
    for (final IGraphicDriver d : LineChartExample.GRAPHIC_DRIVERS) {
      for (final IChartDriver c : LineChartExample.DRIVERS) {
        try (final Graphic g = d.createGraphic(dir,
            ((((((LineChartExample.class.getSimpleName() + '_') + d
                .getClass().getSimpleName()) + '_') + c.getClass()
                .getSimpleName()) + '_') + (++z)), size,
            new FinishedPrinter(c, d))) {
          rand.setSeed(seed);
          LineChartExample.randomLineChart(rand, g,
              LineChartExample.randomStyleSet(rand), c);
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

    try (final ILineChart chart = driver.lineChart(graphic, styles)) {
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
    int i;
    double realMax;

    data[0] = (min + (0.05 * (rand.nextDouble() - 0.5d) * (max - min)));
    realMax = (max - (0.05 * (rand.nextDouble() - 0.5d) * (max - min)));
    for (i = 1; i < data.length; i++) {
      data[i] = (data[i - 1] + ((((1.3d * rand.nextDouble()) - 0.1d) / (data.length - i)) * (realMax - data[i - 1])));
    }
    Arrays.sort(data);

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
