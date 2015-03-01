package org.optimizationBenchmarking.utils.document.impl.latex;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteXMLInput;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;

/** the default font palette for LaTeX */
public final class LaTeXDefaultFontPalette extends FontPalette {

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
  LaTeXDefaultFontPalette(final FontStyle def, final FontStyle emph,
      final FontStyle code, final FontStyle[] data) {
    super(def, emph, code, data);
  }

  /**
   * read resolve
   * 
   * @return {@link #getInstance()}
   */
  private final Object readResolve() {
    return LaTeXDefaultFontPalette.getInstance();
  }

  /**
   * write replace
   * 
   * @return {@link #getInstance()}
   */
  private final Object writeReplace() {
    return LaTeXDefaultFontPalette.getInstance();
  }

  /**
   * Get an instance of the default LaTeX font palette
   * 
   * @return the default LaTeX font palette
   */
  public static final LaTeXDefaultFontPalette getInstance() {
    if (__LaTeXDefaultFontPaletteLoader.INSTANCE == null) {
      throw __LaTeXDefaultFontPaletteLoader.ERROR;
    }
    return __LaTeXDefaultFontPaletteLoader.INSTANCE;
  }

  /** the default LaTeX font palette builder */
  private static final class __LaTeXDefaultFontPaletteBuilder extends
      FontPaletteBuilder {
    /** the efault LaTeX font palette builder */
    __LaTeXDefaultFontPaletteBuilder() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final LaTeXDefaultFontPalette createPalette(
        final FontStyle def, final FontStyle emph, final FontStyle code,
        final FontStyle[] data) {
      return new LaTeXDefaultFontPalette(def, emph, code, data);
    }
  }

  /** the efault LaTeX font palette loader */
  private static final class __LaTeXDefaultFontPaletteLoader {

    /** the globally shared instance of the default LaTeX font palette */
    static final LaTeXDefaultFontPalette INSTANCE;
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
      try (final __LaTeXDefaultFontPaletteBuilder cspb = new __LaTeXDefaultFontPaletteBuilder()) {
        FontPaletteXMLInput
            .getInstance()
            .use()
            .setLogger(Configuration.getGlobalLogger())
            .setDestination(cspb)
            .addResource(LaTeXDefaultFontPalette.class,
                "latex.fontPalette").create().call(); //$NON-NLS-1$
        pal = cspb.getResult();
      } catch (final Throwable t) {
        error = t;
        pal = null;
        msg = "Error while loading the default font palette for the LaTeX Document Driver. This will creating LaTeX documents using this palette impossible.";//$NON-NLS-1$
        ErrorUtils.logError(logger, msg, error, true);
        ErrorUtils.throwRuntimeException(msg, t);
      }

      if (pal != null) {
        INSTANCE = ((LaTeXDefaultFontPalette) pal);
        ERROR = null;
      } else {
        INSTANCE = null;
        msg = "Could not load LaTeX default font palette."; //$NON-NLS-1$
        ERROR = ((error != null) ? new UnsupportedOperationException(msg,
            error) : new UnsupportedOperationException(msg));
      }
    }
  }
}
