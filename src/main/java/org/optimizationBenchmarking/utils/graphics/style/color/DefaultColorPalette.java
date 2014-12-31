package org.optimizationBenchmarking.utils.graphics.style.color;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;

/** the default color palette */
public final class DefaultColorPalette extends ColorPalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Get an instance of the default color palette
   * 
   * @return the default color palette
   */
  public static final DefaultColorPalette getInstance() {
    return __DefaultColorPaletteLoader.INSTANCE;
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
   * @return {@link #getInstance()}
   */
  private final Object readResolve() {
    return DefaultColorPalette.getInstance();
  }

  /**
   * write replace
   * 
   * @return {@link #getInstance()}
   */
  private final Object writeReplace() {
    return DefaultColorPalette.getInstance();
  }

  /** the default palette builder */
  private static final class __DefaultColorPaletteBuilder extends
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

  /** the default color palette loader */
  private static final class __DefaultColorPaletteLoader {

    /** the globally shared instance of the default color palette */
    static final DefaultColorPalette INSTANCE;

    static {
      Palette<ColorStyle> pal;

      pal = null;
      try (final __DefaultColorPaletteBuilder cspb = new __DefaultColorPaletteBuilder()) {
        PaletteInputDriver
            .getInstance()
            .use()
            .setDestination(cspb)
            .addResource(DefaultColorPalette.class,
                "defaultColor.colorPalette").create().call(); //$NON-NLS-1$
        pal = cspb.getResult();
      } catch (final Throwable t) {
        ErrorUtils.throwAsRuntimeException(t);
      }

      INSTANCE = ((DefaultColorPalette) pal);
    }
  }
}
