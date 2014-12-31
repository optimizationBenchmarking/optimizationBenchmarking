package org.optimizationBenchmarking.utils.graphics.style.color;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;

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
      Palette<ColorStyle> pal;

      pal = null;
      try (final __DefaultGrayPaletteBuilder cspb = new __DefaultGrayPaletteBuilder()) {
        PaletteInputDriver
            .getInstance()
            .use()
            .setDestination(cspb)
            .addResource(DefaultGrayPalette.class,
                "defaultGray.colorPalette").create().call(); //$NON-NLS-1$
        pal = cspb.getResult();
      } catch (final Throwable t) {
        ErrorUtils.throwAsRuntimeException(t);
      }

      INSTANCE = ((DefaultGrayPalette) pal);
    }
  }
}
