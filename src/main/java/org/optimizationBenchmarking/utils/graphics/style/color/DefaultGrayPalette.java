package org.optimizationBenchmarking.utils.graphics.style.color;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;

/** the default gray palette */
public final class DefaultGrayPalette extends ColorPalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Get an instance of the default gray palette
   * 
   * @return the default gray palette
   */
  public static final DefaultGrayPalette getInstance() {
    return __DefaultGrayPaletteLoader.INSTANCE;
  }

  /**
   * instantiate
   * 
   * @param data
   *          the data
   */
  DefaultGrayPalette(final ColorStyle[] data) {
    super(data);
  }

  /**
   * read resolve
   * 
   * @return {@link #getInstance()}
   */
  private final Object readResolve() {
    return DefaultGrayPalette.getInstance();
  }

  /**
   * write replace
   * 
   * @return {@link #getInstance()}
   */
  private final Object writeReplace() {
    return DefaultGrayPalette.getInstance();
  }

  /** the default palette builder */
  private static final class __DefaultGrayPaletteBuilder extends
      ColorPaletteBuilder {
    /** the default palette builder */
    __DefaultGrayPaletteBuilder() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final DefaultGrayPalette createPalette(
        final ArrayList<ColorStyle> data) {
      return new DefaultGrayPalette(data.toArray(new ColorStyle[data
          .size()]));
    }
  }

  /** the default gray palette loader */
  private static final class __DefaultGrayPaletteLoader {

    /** the globally shared instance of the default gray palette */
    static final DefaultGrayPalette INSTANCE;

    static {
      final Logger logger;
      final String msg;
      Palette<ColorStyle> pal;

      pal = null;
      logger = Configuration.getGlobalLogger();
      try (final __DefaultGrayPaletteBuilder cspb = new __DefaultGrayPaletteBuilder()) {
        ColorPaletteXMLInput
            .getInstance()
            .use()
            .setLogger(logger)
            .setDestination(cspb)
            .addResource(DefaultGrayPalette.class,
                "defaultGray.colorPalette").create().call(); //$NON-NLS-1$
        pal = cspb.getResult();
      } catch (final Throwable t) {
        msg = "Error while loading the default gray palette. This palette will not be available.";//$NON-NLS-1$
        ErrorUtils.logError(logger, msg, t, true);
        ErrorUtils.throwRuntimeException(msg, t);
      }

      INSTANCE = ((DefaultGrayPalette) pal);
    }
  }
}
