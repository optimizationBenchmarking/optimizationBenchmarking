package examples.org.optimizationBenchmarking.utils.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.StyleApplication;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.color.DefaultColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.DefaultGrayPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.HTML401Palette;
import org.optimizationBenchmarking.utils.graphics.style.color.JavaDefaultPalette;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * An example used to illustrate the available color palettes.
 */
public class ColorPalettes {

  /** the graphic drivers to use */
  private static final IGraphicDriver[] DRIVERS = GraphicsExample.DRIVERS;

  /** the palettes to print */
  @SuppressWarnings("unchecked")
  private static final Palette<ColorStyle>[] PALETTES = new Palette[] {//
  DefaultColorPalette.INSTANCE, JavaDefaultPalette.INSTANCE,
      DefaultGrayPalette.INSTANCE, HTML401Palette.INSTANCE };

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

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }
    for (final IGraphicDriver d : ColorPalettes.DRIVERS) {
      for (final Palette<ColorStyle> p : ColorPalettes.PALETTES) {//
        ColorPalettes.__paint(dir, (d.getClass().getSimpleName() + '_' + p
            .getClass().getSimpleName()), d, p);
      }
    }
  }

  /**
   * paint the palette
   * 
   * @param dir
   *          the directory
   * @param name
   *          the name
   * @param palette
   *          the palette
   * @param driver
   *          the graphic driver to use
   */
  private static final void __paint(final Path dir, final String name,
      final IGraphicDriver driver, final Palette<ColorStyle> palette) {
    final int s, w;
    final Rectangle2D b;
    int c, i, j, k;
    final AffineTransform t;
    ColorStyle st;
    Color use;

    try (final Graphic g = driver.createGraphic(dir, name,
        new PhysicalDimension(160, 160, ELength.MM),
        new FinishedPrinter())) {

      b = g.getBounds();
      g.setFont(new Font("Arial", Font.PLAIN, //$NON-NLS-1$
          ((int) (b.getHeight() * 0.25d))));

      s = palette.size();
      w = ((int) (0.5d + Math.ceil(Math.sqrt(s))));
      c = 0;
      t = g.getTransform();
      for (i = 0; i < w; i++) {
        for (j = 0; j < w; j++) {

          st = palette.get(c);
          try (StyleApplication x = st.applyTo(g)) {

            g.translate(((j * b.getWidth()) / w),//
                ((i * b.getHeight()) / w));
            g.scale((1d / w), (1d / w));

            g.fillRect(0, 0, b.getWidth(), b.getHeight());
            // ((j * b.getWidth()) / w),//
            // ((i * b.getHeight()) / w),//
            // (b.getWidth() / w),//
            // (b.getHeight() / w));

            if (Math.max(st.getRed(),
                Math.max(st.getBlue(), st.getGreen())) < 120) {
              use = Color.WHITE;
            } else {
              use = Color.BLACK;
            }

            g.setColor(use);
            k = 1;
            for (final String ss : st.toString().split(" ")) { //$NON-NLS-1$
              g.drawString(ss, 1d, b.getHeight() * 0.2d * (k++));
            }

            if ((++c) >= s) {
              return;
            }

            g.setTransform(t);
          }
        }
      }
    }
  }
}
