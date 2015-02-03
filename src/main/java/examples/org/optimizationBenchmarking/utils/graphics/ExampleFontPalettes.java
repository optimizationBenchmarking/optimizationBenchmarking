package examples.org.optimizationBenchmarking.utils.graphics;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDriver;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;

/** a set of example font palettes */
public final class ExampleFontPalettes {

  /** the fonts */
  public static final ArrayListView<FontPalette> PALETTES = ExampleFontPalettes
      .__make();

  /**
   * create the example font palettes
   * 
   * @return the font palettes
   */
  private static final ArrayListView<FontPalette> __make() {
    LinkedHashSet<FontPalette> all;
    FontPalette palette;

    all = new LinkedHashSet<>();
    try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {

      PaletteInputDriver
          .getInstance()
          .use()
          .setDestination(tb)
          .addResource(FontPaletteExample.class, "examples.fontPalette").create().call(); //$NON-NLS-1$
      palette = tb.getResult();
      if ((palette != null) && (!(palette.isEmpty()))) {
        all.add(palette);
      }
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }

    try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
      PaletteInputDriver
          .getInstance()
          .use()
          .setDestination(tb)
          .addResource(XHTML10Driver.class, "xhtml10.fontPalette").create().call(); //$NON-NLS-1$
      palette = tb.getResult();
      if ((palette != null) && (!(palette.isEmpty()))) {
        all.add(palette);
      }
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }

    palette = XHTML10Driver.defaultFontPalette();
    if ((palette != null) && (!(palette.isEmpty()))) {
      all.add(palette);
    }
    palette = LaTeXDriver.defaultFontPalette();
    if ((palette != null) && (!(palette.isEmpty()))) {
      all.add(palette);
    }

    return ArrayListView.collectionToView(all, false);
  }

  /** the forbidden constructor */
  private ExampleFontPalettes() {
    ErrorUtils.doNotCall();
  }

}
