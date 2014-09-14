package org.optimizationBenchmarking.utils.graphics.style.color;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.PaletteIODriver;

/** the default gray palette */
public final class DefaultGrayPalette extends ColorPalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the default gray palette */
  public static final DefaultGrayPalette INSTANCE;

  static {
    Palette<ColorStyle> pal;

    pal = null;
    try (final __DefaultGrayPaletteBuilder cspb = new __DefaultGrayPaletteBuilder()) {
      PaletteIODriver.INSTANCE.loadResource(cspb,
          DefaultGrayPalette.class, "default.gray.color.palette"); //$NON-NLS-1$
      pal = cspb.getResult();
    } catch (final Throwable t) {
      ErrorUtils.throwAsRuntimeException(t);
    }

    INSTANCE = ((DefaultGrayPalette) pal);
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
   * @return {@link #INSTANCE}
   */
  private final Object readResolve() {
    return DefaultGrayPalette.INSTANCE;
  }

  /**
   * write replace
   * 
   * @return {@link #INSTANCE}
   */
  private final Object writeReplace() {
    return DefaultGrayPalette.INSTANCE;
  }

  /** the default palette builder */
  static final class __DefaultGrayPaletteBuilder extends
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
}
