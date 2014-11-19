package examples.org.optimizationBenchmarking.utils.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleApplication;
import org.optimizationBenchmarking.utils.graphics.style.stroke.DefaultStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokeStyle;
import org.optimizationBenchmarking.utils.math.units.ELength;

import examples.org.optimizationBenchmarking.utils.document.FinishedPrinter;

/**
 * An example used to illustrate the available color palettes.
 */
public class StrokePaletteExample {

  /** the graphic driver to use */
  private static final ArrayListView<IGraphicDriver> DRIVERS = GraphicsExample.DRIVERS;

  /** the palettes to print */
  public static final ArrayListView<StrokePalette> PALETTES = new ArrayListView<>(
      new StrokePalette[] {//
      DefaultStrokePalette.getInstance() });

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
    int z;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    z = 0;
    for (final IGraphicDriver d : StrokePaletteExample.DRIVERS) {
      for (final StrokePalette p : StrokePaletteExample.PALETTES) {//
        StrokePaletteExample
            .__paint(
                dir,
                ((((StrokePaletteExample.class.getSimpleName() + '_') + (++z)) + '_')
                    + d.getClass().getSimpleName() + '_' + p.getClass()
                    .getSimpleName()), d, p);
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
   *          the driver to use
   */
  private static final void __paint(final Path dir, final String name,
      final IGraphicDriver driver, final StrokePalette palette) {
    final Rectangle2D b;
    final int s;
    final double h, w;
    final ArrayList<StrokeStyle> styles;
    int i;
    StrokeStyle st;

    styles = new ArrayList<>(palette.size() + 3);
    styles.add(palette.getDefaultStroke());
    styles.add(palette.getThinStroke());
    styles.add(palette.getThickStroke());
    styles.addAll(palette);

    s = styles.size();

    try (final Graphic g = driver.use().setBasePath(dir)
        .setMainDocumentNameSuggestion(name)
        .setSize(new PhysicalDimension(160, 160, ELength.MM))
        .setFileProducerListener(new FinishedPrinter(driver)).create()) {

      b = g.getBounds();
      g.setColor(Color.white);
      g.setBackground(Color.white);
      g.fill(b);
      g.setColor(Color.black);

      h = (b.getHeight() / s);
      w = b.getWidth();
      g.setFont(new Font("Arial", Font.PLAIN, //$NON-NLS-1$
          ((int) (0.5d * h))));

      for (i = 0; i < s; i++) {
        st = styles.get(i);
        try (final StyleApplication x = st.applyTo(g)) {
          g.drawLine(0.05 * w, (i + 0.5) * h, 0.45 * w, (i + 0.5) * h);
          g.drawString(st.toString(), 0.55 * w, (i + 0.7) * h);
        }
      }

    }
  }

}
