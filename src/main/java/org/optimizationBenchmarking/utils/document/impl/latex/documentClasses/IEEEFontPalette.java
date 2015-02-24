package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteXMLInput;

/** The font palette used by the {@code IEEEtran} document class */
public final class IEEEFontPalette {
  /** the default font palette */
  static final FontPalette INSTANCE;

  /** the error */
  static final UnsupportedOperationException ERROR;

  static {
    final Logger logger;
    FontPalette p;
    Throwable error;
    String s;

    p = null;
    error = null;
    logger = Configuration.getGlobalLogger();
    try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
      FontPaletteXMLInput
          .getInstance()
          .use()
          .setLogger(logger)
          .setDestination(tb)
          .addResource(IEEEFontPalette.class, "ieee.fontPalette").create().call(); //$NON-NLS-1$
      p = tb.getResult();
    } catch (final Throwable tt) {
      ErrorUtils
          .logError(
              logger,
              "Error while loading the IEEE font palette. This will make creating LaTeX documents depending on IEEE document classes impossible.",//$NON-NLS-1$ 
              tt, true);
      error = tt;
      p = null;
    }

    if (p != null) {
      INSTANCE = p;
      ERROR = null;
    } else {
      s = "Could not load font palette."; //$NON-NLS-1$
      ERROR = ((error != null) ? new UnsupportedOperationException(s,
          error) : new UnsupportedOperationException(s));
      INSTANCE = null;
    }
  }

  /**
   * Get the IEEE font palette
   * 
   * @return the IEEE font palette
   */
  public static final FontPalette getIEEEFontPalette() {
    return IEEEFontPalette.INSTANCE;
  }

  /** the forbidden constructor */
  private IEEEFontPalette() {
    ErrorUtils.doNotCall();
  }
}
