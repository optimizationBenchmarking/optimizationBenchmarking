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
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleApplication;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import examples.org.optimizationBenchmarking.utils.document.FinishedPrinter;

/**
 * An example used to illustrate the available color palettes.
 */
public class FontPaletteExample {

  /** the graphic driver to use */
  private static final ArrayListView<IGraphicDriver> DRIVERS = GraphicsExample.DRIVERS;

  /** the palettes to print */
  public static final ArrayListView<FontPalette> PALETTES;

  static {
    final FontPalette[] list = new FontPalette[4];

    try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
      PaletteInputDriver
          .getInstance()
          .use()
          .setDestination(tb)
          .addResource(FontPaletteExample.class, "examples.fontPalette").create().call(); //$NON-NLS-1$
      list[0] = tb.getResult();
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }
    try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
      PaletteInputDriver
          .getInstance()
          .use()
          .setDestination(tb)
          .addResource(XHTML10Driver.class, "xhtml10.fontPalette").create().call(); //$NON-NLS-1$
      list[1] = tb.getResult();
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }

    list[2] = XHTML10Driver.getDefaultFontPalette();
    list[3] = LaTeXDocumentClass.getDefaultFontPalette();

    PALETTES = new ArrayListView<>(list);
  }

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
    int i;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    i = 0;
    for (final FontPalette p : FontPaletteExample.PALETTES) {//
      for (final IGraphicDriver d : FontPaletteExample.DRIVERS) {
        FontPaletteExample
            .__paint(
                dir,
                (((FontPaletteExample.class.getSimpleName() + '_') + (++i)) + '_')
                    + (d.getClass().getSimpleName()), d, p);
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
      final IGraphicDriver driver, final FontPalette palette) {
    final Rectangle2D b;
    final int s;
    final double w;
    final ArrayList<FontStyle> styles;
    final MemoryTextOutput mo;
    double h, hh;
    int i;
    Font f;
    String x;
    FontStyle st;
    boolean q;

    styles = new ArrayList<>(palette.size() + 1);
    styles.add(palette.getDefaultFont());
    styles.add(palette.getCodeFont());
    styles.add(palette.getEmphFont());
    styles.addAll(palette);

    mo = new MemoryTextOutput();

    s = styles.size();

    try (final Graphic g = driver.use().setBasePath(dir)
        .setMainDocumentNameSuggestion(name)
        .setSize(new PhysicalDimension(320, 160, ELength.MM))
        .setFileProducerListener(new FinishedPrinter(driver)).create()) {

      b = g.getBounds();
      g.setColor(Color.white);
      g.setBackground(Color.white);
      g.fill(b);
      g.setColor(Color.black);

      w = b.getWidth();
      h = 0;
      for (i = 0; i < s; i++) {
        st = styles.get(i);
        f = st.getFont();
        q = st.appendDescription(ETextCase.IN_SENTENCE, mo, true);
        if (q) {
          mo.append(' ');
          mo.append('[');
        }
        st.appendDescription(ETextCase.IN_SENTENCE, mo, false);
        if (q) {
          mo.append(']');
        }

        mo.append(' ');
        mo.append('-');
        mo.append(' ');
        st.getFaceChoices().toText(mo);

        x = mo.toString();
        mo.clear();
        hh = f.getLineMetrics(x, g.getFontRenderContext()).getHeight();
        h += hh * 1.5d;

        try (final StyleApplication fa = st.applyTo(g)) {
          g.drawString(x, 0.05 * w, h);
          h += (0.4d * hh);
        }
      }

    }
  }

}
