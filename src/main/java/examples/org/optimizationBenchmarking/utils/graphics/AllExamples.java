package examples.org.optimizationBenchmarking.utils.graphics;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;

import examples.org.optimizationBenchmarking.utils.graphics.chart.LineChartExample;

/**
 * A quick wrapper to invoke all graphics examples.
 */
public class AllExamples {

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

    GraphicsExample.main(//
        new String[] { PathUtils.getPhysicalPath(//
            dir.resolve("graphicsExample"), false) }); //$NON-NLS-1$

    ColorPaletteExample.main(//
        new String[] { PathUtils.getPhysicalPath(//
            dir.resolve("colorPalette"), false) }); //$NON-NLS-1$

    FontPaletteExample.main(//
        new String[] { PathUtils.getPhysicalPath(//
            dir.resolve("fontPalette"), false) }); //$NON-NLS-1$

    StrokePaletteExample.main(//
        new String[] { PathUtils.getPhysicalPath(//
            dir.resolve("strokePalette"), false) }); //$NON-NLS-1$

    LineChartExample.main(//
        new String[] { PathUtils.getPhysicalPath(//
            dir.resolve("lineChart"), false) }); //$NON-NLS-1$
  }

}
