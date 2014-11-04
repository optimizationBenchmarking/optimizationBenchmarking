package org.optimizationBenchmarking.utils.graphics.style.color;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;

/**
 * a palette based on the <a
 * href="http://www.w3.org/TR/REC-html40/types.html#h-6.5">HTML 4.01</a>
 * color names
 */
public final class HTML401Palette extends ColorPalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the globally shared instance of ta palette based on the <a
   * href="http://www.w3.org/TR/REC-html40/types.html#h-6.5">HTML 4.01</a>
   * color names
   */
  public static final HTML401Palette INSTANCE;

  static {
    Palette<ColorStyle> pal;

    pal = null;
    try (final __DefaultHTML401PaletteBuilder cspb = new __DefaultHTML401PaletteBuilder()) {
      PaletteInputDriver.INSTANCE.loadResource(cspb, HTML401Palette.class,
          "html401.color.palette"); //$NON-NLS-1$
      pal = cspb.getResult();
    } catch (final Throwable t) {
      ErrorUtils.throwAsRuntimeException(t);
    }

    INSTANCE = ((HTML401Palette) pal);
  }

  /**
   * instantiate
   * 
   * @param data
   *          the data
   */
  HTML401Palette(final ColorStyle[] data) {
    super(data);
  }

  /**
   * read resolve
   * 
   * @return {@link #INSTANCE}
   */
  private final Object readResolve() {
    return HTML401Palette.INSTANCE;
  }

  /**
   * write replace
   * 
   * @return {@link #INSTANCE}
   */
  private final Object writeReplace() {
    return HTML401Palette.INSTANCE;
  }

  /** the default palette builder */
  static final class __DefaultHTML401PaletteBuilder extends
      ColorPaletteBuilder {
    /** the default palette builder */
    __DefaultHTML401PaletteBuilder() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final HTML401Palette createPalette(
        final ArrayList<ColorStyle> data) {
      return new HTML401Palette(data.toArray(new ColorStyle[data.size()]));
    }
  }
}
