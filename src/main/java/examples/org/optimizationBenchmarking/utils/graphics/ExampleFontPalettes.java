package examples.org.optimizationBenchmarking.utils.graphics;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDefaultFontPalette;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDriver;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.IEEEtranFontPalette;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.SigAlternateFontPalette;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10DefaultFontPalette;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteXMLInput;

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

      FontPaletteXMLInput
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
      FontPaletteXMLInput
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

    try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
      FontPaletteXMLInput
          .getInstance()
          .use()
          .setDestination(tb)
          .addResource(LaTeXDriver.class, "latex.fontPalette").create().call(); //$NON-NLS-1$
      palette = tb.getResult();
      if ((palette != null) && (!(palette.isEmpty()))) {
        all.add(palette);
      }
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }

    try {
      palette = XHTML10DefaultFontPalette.getInstance();
      if ((palette != null) && (!(palette.isEmpty()))) {
        all.add(palette);
      }
    } catch (final Throwable ignore) {
      /** ignore **/
    }

    try {
      palette = LaTeXDefaultFontPalette.getInstance();
      if ((palette != null) && (!(palette.isEmpty()))) {
        all.add(palette);
      }
    } catch (final Throwable ignore) {
      /** ignore **/
    }

    try {
      palette = IEEEtranFontPalette.getInstance();
      if ((palette != null) && (!(palette.isEmpty()))) {
        all.add(palette);
      }
    } catch (final Throwable ignore) {
      /** ignore **/
    }

    try {
      palette = SigAlternateFontPalette.getInstance();
      if ((palette != null) && (!(palette.isEmpty()))) {
        all.add(palette);
      }
    } catch (final Throwable ignore) {
      /** ignore **/
    }

    return ArrayListView.collectionToView(all, false);
  }

  /** the forbidden constructor */
  private ExampleFontPalettes() {
    ErrorUtils.doNotCall();
  }

}
