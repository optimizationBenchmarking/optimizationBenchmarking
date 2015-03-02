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

/** the default font palette for IEEETran */
public final class IEEEtranFontPalette extends FontPalette {

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
  IEEEtranFontPalette(final FontStyle def, final FontStyle emph,
      final FontStyle code, final FontStyle[] data) {
    super(def, emph, code, data);
  }

  /**
   * read resolve
   * 
   * @return {@link #getInstance()}
   */
  private final Object readResolve() {
    return IEEEtranFontPalette.getInstance();
  }

  /**
   * write replace
   * 
   * @return {@link #getInstance()}
   */
  private final Object writeReplace() {
    return IEEEtranFontPalette.getInstance();
  }

  /**
   * Get an instance of the default IEEEtran font palette
   * 
   * @return the default IEEEtran font palette
   */
  public static final IEEEtranFontPalette getInstance() {
    if (__IEEETranDefaultFontPaletteLoader.INSTANCE == null) {
      throw __IEEETranDefaultFontPaletteLoader.ERROR;
    }
    return __IEEETranDefaultFontPaletteLoader.INSTANCE;
  }

  /** theIEEEtran font palette builder */
  private static final class __IEEETranDefaultFontPaletteBuilder extends
      FontPaletteBuilder {
    /** the IEEEtran font palette builder */
    __IEEETranDefaultFontPaletteBuilder() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final IEEEtranFontPalette createPalette(final FontStyle def,
        final FontStyle emph, final FontStyle code, final FontStyle[] data) {
      return new IEEEtranFontPalette(def, emph, code, data);
    }
  }

  /** the IEEEtran font palette loader */
  private static final class __IEEETranDefaultFontPaletteLoader {

    /** the globally shared instance of the IEEEtran font palette */
    static final IEEEtranFontPalette INSTANCE;
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
      try (final __IEEETranDefaultFontPaletteBuilder cspb = new __IEEETranDefaultFontPaletteBuilder()) {
        FontPaletteXMLInput
            .getInstance()
            .use()
            .setLogger(Configuration.getGlobalLogger())
            .setDestination(cspb)
            .addResource(IEEEtranFontPalette.class, "IEEEtran.fontPalette").create().call(); //$NON-NLS-1$
        pal = cspb.getResult();
      } catch (final Throwable t) {
        error = t;
        pal = null;
        try {
          ErrorUtils
              .logError(
                  logger,
                  "Error while loading the IEEEtran font palette. This will make creating LaTeX documents depending on the IEEEtran document class impossible.",//$NON-NLS-1$
                  error, true, RethrowMode.THROW_AS_RUNTIME_EXCEPTION);
        } catch (final Throwable a) {
          error = a;
        }
      }

      if (pal != null) {
        INSTANCE = ((IEEEtranFontPalette) pal);
        ERROR = null;
      } else {
        INSTANCE = null;
        msg = "Could not load IEEETran default font palette."; //$NON-NLS-1$
        ERROR = ((error != null) ? new UnsupportedOperationException(msg,
            error) : new UnsupportedOperationException(msg));
      }
    }
  }
}
