package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteXMLInput;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;

/** the default font palette for XHTML10 */
public final class XHTML10DefaultFontPalette extends FontPalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the palette
   * 
   * @param def
   *          the default font style
   * @param emph
   *          the emphasize font
   * @param code
   *          the code font style
   * @param data
   *          the palette data
   */
  XHTML10DefaultFontPalette(final FontStyle def, final FontStyle emph,
      final FontStyle code, final FontStyle[] data) {
    super(def, emph, code, data);
  }

  /**
   * read resolve
   * 
   * @return {@link #getInstance()}
   */
  private final Object readResolve() {
    return XHTML10DefaultFontPalette.getInstance();
  }

  /**
   * write replace
   * 
   * @return {@link #getInstance()}
   */
  private final Object writeReplace() {
    return XHTML10DefaultFontPalette.getInstance();
  }

  /**
   * Get an instance of the default XHTML 1.0 font palette
   * 
   * @return the default XHTML 1.0 font palette
   */
  public static final XHTML10DefaultFontPalette getInstance() {
    if (__XHTML10DefaultFontPaletteLoader.INSTANCE == null) {
      throw __XHTML10DefaultFontPaletteLoader.ERROR;
    }
    return __XHTML10DefaultFontPaletteLoader.INSTANCE;
  }

  /** the default XHTML 1.0 font palette builder */
  private static final class __XHTML10DefaultFontPaletteBuilder extends
      FontPaletteBuilder {
    /** the default XHTML 1.0 font palette builder */
    __XHTML10DefaultFontPaletteBuilder() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final XHTML10DefaultFontPalette createPalette(
        final FontStyle def, final FontStyle emph, final FontStyle code,
        final FontStyle[] data) {
      return new XHTML10DefaultFontPalette(def, emph, code, data);
    }
  }

  /** the default color palette loader */
  private static final class __XHTML10DefaultFontPaletteLoader {

    /** the globally shared instance of the default XHTML 1.0 font palette */
    static final XHTML10DefaultFontPalette INSTANCE;
    /** the error */
    static final UnsupportedOperationException ERROR;

    static {
      final Logger logger;
      Palette<FontStyle> pal;
      Throwable error;
      String msg;

      pal = null;
      error = null;
      logger = Configuration.getGlobalLogger();
      try (final __XHTML10DefaultFontPaletteBuilder cspb = new __XHTML10DefaultFontPaletteBuilder()) {
        FontPaletteXMLInput
            .getInstance()
            .use()
            .setLogger(Configuration.getGlobalLogger())
            .setDestination(cspb)
            .addResource(XHTML10DefaultFontPalette.class,
                "xhtml10.fontPalette").create().call(); //$NON-NLS-1$
        pal = cspb.getResult();
      } catch (final Throwable t) {
        error = t;
        pal = null;
        msg = "Error while loading the default font palette for the XHTML 1.0 Document Driver. This will creating XHTML 1.0 documents using this palette impossible."; //$NON-NLS-1$
        ErrorUtils.logError(logger, msg, error, true);
        ErrorUtils.throwRuntimeException(msg, t);
      }

      if (pal != null) {
        INSTANCE = ((XHTML10DefaultFontPalette) pal);
        ERROR = null;
      } else {
        INSTANCE = null;
        msg = "Could not load XHTML 1.0 default font palette."; //$NON-NLS-1$
        ERROR = ((error != null) ? new UnsupportedOperationException(msg,
            error) : new UnsupportedOperationException(msg));
      }
    }
  }
}
