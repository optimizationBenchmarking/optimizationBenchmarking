package org.optimizationBenchmarking.utils.graphics.style.stroke;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.style.Palette;

/** the default stroke palette */
public final class DefaultStrokePalette extends StrokePalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Get the default stroke palette
   *
   * @return the default stroke palette
   */
  public static final DefaultStrokePalette getInstance() {
    return __DefaultStrokePaletteLoader.INSTANCE;
  }

  /**
   * instantiate
   *
   * @param def
   *          the default style
   * @param thin
   *          the thin style
   * @param fat
   *          the fat style
   * @param data
   *          the data
   */
  DefaultStrokePalette(final StrokeStyle def, final StrokeStyle thin,
      final StrokeStyle fat, final StrokeStyle[] data) {
    super(def, thin, fat, data);
  }

  /**
   * read resolve
   *
   * @return {@link #getInstance()}
   */
  private final Object readResolve() {
    return DefaultStrokePalette.getInstance();
  }

  /**
   * write replace
   *
   * @return {@link #getInstance()}
   */
  private final Object writeReplace() {
    return DefaultStrokePalette.getInstance();
  }

  /** the default palette builder */
  private static final class __DefaultStrokePaletteBuilder extends
      StrokePaletteBuilder {
    /** the default palette builder */
    __DefaultStrokePaletteBuilder() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final StrokePalette createPalette(final StrokeStyle def,
        final StrokeStyle thin, final StrokeStyle fat,
        final StrokeStyle[] data) {
      return new DefaultStrokePalette(def, thin, fat, data);
    }
  }

  /** the internal loader class */
  private static final class __DefaultStrokePaletteLoader {

    /** the globally shared instance of the default stroke palette */
    static final DefaultStrokePalette INSTANCE;

    static {
      final Logger logger;
      Palette<StrokeStyle> pal;
      pal = null;

      logger = Configuration.getGlobalLogger();
      try (final __DefaultStrokePaletteBuilder cspb = new __DefaultStrokePaletteBuilder()) {
        StrokePaletteXMLInput
            .getInstance()
            .use()
            .setLogger(logger)
            .setDestination(cspb)
            .addResource(DefaultStrokePalette.class,
                "default.strokePalette").create().call(); //$NON-NLS-1$
        pal = cspb.getResult();
      } catch (final Throwable t) {
        ErrorUtils
            .logError(
                logger,
                "Error while loading the default stroke palette. This palette will not be available.",//$NON-NLS-1$
                t, true, RethrowMode.AS_RUNTIME_EXCEPTION);
      }

      INSTANCE = ((DefaultStrokePalette) pal);
    }
  }

}
