package org.optimizationBenchmarking.utils.graphics.style.color;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;

/** the default color palette */
public final class DefaultColorPalette extends ColorPalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the default color palette */
  public static final DefaultColorPalette INSTANCE;

  static {
    Palette<ColorStyle> pal;

    pal = null;
    try (final __DefaultColorPaletteBuilder cspb = new __DefaultColorPaletteBuilder()) {
      PaletteInputDriver.INSTANCE.loadResource(cspb,
          DefaultColorPalette.class, "default.color.color.palette"); //$NON-NLS-1$
      pal = cspb.getResult();
    } catch (final Throwable t) {
      ErrorUtils.throwAsRuntimeException(t);
    }

    INSTANCE = ((DefaultColorPalette) pal);
  }

  /**
   * instantiate
   * 
   * @param data
   *          the data
   */
  DefaultColorPalette(final ColorStyle[] data) {
    super(data);
  }

  /**
   * read resolve
   * 
   * @return {@link #INSTANCE}
   */
  private final Object readResolve() {
    return DefaultColorPalette.INSTANCE;
  }

  /**
   * write replace
   * 
   * @return {@link #INSTANCE}
   */
  private final Object writeReplace() {
    return DefaultColorPalette.INSTANCE;
  }

  /** the default palette builder */
  static final class __DefaultColorPaletteBuilder extends
      ColorPaletteBuilder {
    /** the default palette builder */
    __DefaultColorPaletteBuilder() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final DefaultColorPalette createPalette(
        final ArrayList<ColorStyle> data) {
      return new DefaultColorPalette(data.toArray(new ColorStyle[data
          .size()]));
    }
  }
}
