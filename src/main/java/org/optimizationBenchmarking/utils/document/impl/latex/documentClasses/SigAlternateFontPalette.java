package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteXMLInput;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;

/** the default font palette for sig-alternate */
public final class SigAlternateFontPalette extends FontPalette {

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
  SigAlternateFontPalette(final FontStyle def, final FontStyle emph,
      final FontStyle code, final FontStyle[] data) {
    super(def, emph, code, data);
  }

  /**
   * read resolve
   * 
   * @return {@link #getInstance()}
   */
  private final Object readResolve() {
    return SigAlternateFontPalette.getInstance();
  }

  /**
   * write replace
   * 
   * @return {@link #getInstance()}
   */
  private final Object writeReplace() {
    return SigAlternateFontPalette.getInstance();
  }

  /**
   * Get an instance of the default sig-alternate font palette
   * 
   * @return the default sig-alternate font palette
   */
  public static final SigAlternateFontPalette getInstance() {
    if (__SigAlternateDefaultFontPaletteLoader.INSTANCE == null) {
      throw __SigAlternateDefaultFontPaletteLoader.ERROR;
    }
    return __SigAlternateDefaultFontPaletteLoader.INSTANCE;
  }

  /** the sig-alternate font palette builder */
  private static final class __SigAlternateDefaultFontPaletteBuilder
      extends FontPaletteBuilder {
    /** the default sig-alternate font palette */
    __SigAlternateDefaultFontPaletteBuilder() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final SigAlternateFontPalette createPalette(
        final FontStyle def, final FontStyle emph, final FontStyle code,
        final FontStyle[] data) {
      return new SigAlternateFontPalette(def, emph, code, data);
    }
  }

  /** the sig-alternate font palette loader */
  private static final class __SigAlternateDefaultFontPaletteLoader {

    /**
     * the globally shared instance of the default sig-alternate font
     * palette
     */
    static final SigAlternateFontPalette INSTANCE;
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
      try (final __SigAlternateDefaultFontPaletteBuilder cspb = new __SigAlternateDefaultFontPaletteBuilder()) {
        FontPaletteXMLInput
            .getInstance()
            .use()
            .setLogger(Configuration.getGlobalLogger())
            .setDestination(cspb)
            .addResource(SigAlternateFontPalette.class,
                "sig-alternate.fontPalette").create().call(); //$NON-NLS-1$
        pal = cspb.getResult();
      } catch (final Throwable t) {
        error = t;
        pal = null;

        try {
          ErrorUtils
              .logError(
                  logger,
                  "Error while loading the sig-alternate font palette. This will make creating LaTeX documents depending on the sig-alternate document class impossible.",//$NON-NLS-1$
                  error, true, RethrowMode.THROW_AS_RUNTIME_EXCEPTION);
        } catch (final Throwable a) {
          error = a;
        }
      }

      if (pal != null) {
        INSTANCE = ((SigAlternateFontPalette) pal);
        ERROR = null;
      } else {
        INSTANCE = null;
        msg = "Could not load sig-alternate default font palette."; //$NON-NLS-1$
        ERROR = ((error != null) ? new UnsupportedOperationException(msg,
            error) : new UnsupportedOperationException(msg));
      }
    }
  }
}
