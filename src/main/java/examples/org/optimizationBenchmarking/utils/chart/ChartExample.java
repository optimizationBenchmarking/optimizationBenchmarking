package examples.org.optimizationBenchmarking.utils.chart;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.chart.spec.ITitledElement;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.graphics.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.math.random.LoremIpsum;
import org.optimizationBenchmarking.utils.math.units.ELength;

import examples.org.optimizationBenchmarking.FinishedPrinter;
import examples.org.optimizationBenchmarking.utils.graphics.ColorPaletteExample;
import examples.org.optimizationBenchmarking.utils.graphics.ExampleFontPalettes;
import examples.org.optimizationBenchmarking.utils.graphics.ExampleGraphicConfigurations;
import examples.org.optimizationBenchmarking.utils.graphics.StrokePaletteExample;

/** The abstract base class for chart examples */
public abstract class ChartExample {

  /** the legend modes */
  private static final ELegendMode[] LEGEND_MODES = ELegendMode.values();
  /** the line types */
  private static final ELineType[] LINE_TYPES = ELineType.values();

  /** the random number generator */
  private Random m_rand;

  /** create */
  protected ChartExample() {
    super();
  }

  /**
   * Perform the chart example
   * 
   * @param selector
   *          the chart selector
   * @param styles
   *          the style set
   * @param random
   *          a random number generator
   */
  public abstract void perform(final IChartSelector selector,
      final StyleSet styles, final Random random);

  /**
   * Perform the chart example
   * 
   * @param selector
   *          the chart selector
   * @param styles
   *          the style set
   */
  public void perform(final IChartSelector selector, final StyleSet styles) {
    if (this.m_rand == null) {
      this.m_rand = new Random();
    }
    this.perform(selector, styles, this.m_rand);
  }

  /**
   * Obtain a random legend mode
   * 
   * @param rand
   *          the random number generator
   * @return the legend mode
   */
  protected static final ELegendMode randomLegendMode(final Random rand) {
    return ChartExample.LEGEND_MODES[rand
        .nextInt(ChartExample.LEGEND_MODES.length)];
  }

  /**
   * Obtain a random line type
   * 
   * @param rand
   *          the random number generator
   * @return the line type
   */
  protected static final ELineType randomLineType(final Random rand) {
    return ChartExample.LINE_TYPES[rand
        .nextInt(ChartExample.LINE_TYPES.length)];
  }

  /**
   * Create a random style set
   * 
   * @param rand
   *          the random number generator
   * @return the style set
   */
  protected static final StyleSet randomStyleSet(final Random rand) {
    return new StyleSet(//
        ExampleFontPalettes.PALETTES.get(rand
            .nextInt(ExampleFontPalettes.PALETTES.size())),//
        ColorPaletteExample.PALETTES.get(rand
            .nextInt(ColorPaletteExample.PALETTES.size())),//
        StrokePaletteExample.PALETTES.get(rand
            .nextInt(StrokePaletteExample.PALETTES.size())));//
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
   * @param title
   *          a string to use
   */
  protected static final void setTitle(final ITitledElement element,
      final String title, final Random rand, final StyleSet styles) {
    final String useTitle;
    final EFontFamily family;

    if (rand.nextBoolean()) {
      if (title != null) {
        useTitle = title;
      } else {
        useTitle = LoremIpsum.loremIpsum(rand, 4);
      }

      element.setTitle(useTitle);
      if (rand.nextInt(6) <= 0) {
        switch (rand.nextInt(3)) {
          case 0: {
            family = EFontFamily.SERIF;
            break;
          }
          case 1: {
            family = EFontFamily.SANS_SERIF;
            break;
          }
          default: {
            family = EFontFamily.MONOSPACED;
            break;
          }
        }
        element.setTitleFont(styles.getMostSimilarFont(family,//
            (rand.nextInt(3) <= 0),//
            (rand.nextInt(3) <= 0),//
            (rand.nextInt(3) <= 0),//
            styles.getDefaultFont().getSize()).getFont());
      }
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
  protected static final Color randomColor(final StyleSet styles,
      final Random rand) {
    int r, g, b, color;

    r = rand.nextInt(256);
    g = rand.nextInt(256);
    b = rand.nextInt(256);

    color = ((r << 16) | (g << 8) | b);
    if (((r < g) ? ((g < b) ? b : g) : ((r < b) ? b : r)) <= 60) {
      color |= 0x070707;
    }
    if ((r + g + b) >= 600) {
      color &= 0x7f7f7f;
    }
    return styles.getMostSimilarColor(color);
  }

  /**
   * run the example: there are problems with the different output
   * 
   * @param args
   *          the arguments
   * @throws IOException
   *           if i/o fails
   */
  protected void run(final String[] args) throws IOException {
    final Path dir;
    final Random rand;
    final PhysicalDimension size;
    final Logger logger;
    StyleSet styles;
    long seed;
    Path sub;
    int z, example;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    rand = new Random();
    logger = Configuration.getGlobalLogger();
    size = new PhysicalDimension((10 + rand.nextInt(12)),
        (6 + rand.nextInt(8)), ELength.CM);

    seed = rand.nextLong();

    for (example = 1; example <= 9; example++) {
      rand.setSeed(seed);
      styles = ChartExample.randomStyleSet(rand);
      seed = rand.nextLong();
      sub = dir.resolve("example_" + example); //$NON-NLS-1$
      z = 0;
      for (final GraphicConfiguration d : ExampleGraphicConfigurations.CONFIGURATIONS) {
        for (final IChartDriver c : ExampleChartDrivers.DRIVERS) {
          try (final Graphic g = d
              .createGraphic(
                  sub,//
                  ((((((((this.getClass().getSimpleName() + '_') + example) + '_') + (++z)) + '_') + d
                      .toString()) + '_') + c.getClass().getSimpleName()),//
                  size, new FinishedPrinter(c, d), logger)) {
            rand.setSeed(seed);
            this.perform(c.use().setGraphic(g).setLogger(logger)
                .setStyleSet(styles).create(), styles, rand);
          }
        }
      }
    }
  }
}
