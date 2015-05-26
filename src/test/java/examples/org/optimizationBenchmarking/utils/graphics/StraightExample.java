package examples.org.optimizationBenchmarking.utils.graphics;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.units.ELength;

import examples.org.optimizationBenchmarking.FinishedPrinter;

/** An example for using the graphic output subsystem. */
public final class StraightExample {

  /** the size */
  private static final PhysicalDimension SIZE = new PhysicalDimension(
      100d, (MathConstants.GOLDEN_RATIO * 100d), ELength.MM);

  /**
   * run the example: there are problems with the pdf output
   *
   * @param args
   *          the arguments
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {
    final Path dir;
    int i, j, z;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    z = 0;
    for (final GraphicConfiguration driver : ExampleGraphicConfigurations.CONFIGURATIONS) {
      try (final Graphic g = driver
          .createGraphic(
              dir,
              (((((StraightExample.class.getSimpleName() + '_') + (++z)) + '_') + driver
                  .toString())), StraightExample.SIZE,
              new FinishedPrinter(driver), Logger.getGlobal())) {

        g.setColor(Color.red);

        for (i = 1; i < 1000;) {
          j = (i + 1);
          g.drawLine(i, i, j, j);
          i = j;
        }

      }
    }
  }

  /** the forbidden constructor */
  private StraightExample() {
    ErrorUtils.doNotCall();
  }

}
