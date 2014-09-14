package org.optimizationBenchmarking.utils.graphics.style.stroke;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.graphics.style.PaletteIODriver;

/** the default stroke palette */
public final class DefaultStrokePalette extends StrokePalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the default stroke palette */
  public static final DefaultStrokePalette INSTANCE;

  static {
    Palette<StrokeStyle> pal;

    pal = null;
    try (final __DefaultStrokePaletteBuilder cspb = new __DefaultStrokePaletteBuilder()) {
      PaletteIODriver.INSTANCE.loadResource(cspb,
          DefaultStrokePalette.class, "default.stroke.palette"); //$NON-NLS-1$
      pal = cspb.getResult();
    } catch (final Throwable t) {
      ErrorUtils.throwAsRuntimeException(t);
    }

    INSTANCE = ((DefaultStrokePalette) pal);
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
   * @return {@link #INSTANCE}
   */
  private final Object readResolve() {
    return DefaultStrokePalette.INSTANCE;
  }

  /**
   * write replace
   * 
   * @return {@link #INSTANCE}
   */
  private final Object writeReplace() {
    return DefaultStrokePalette.INSTANCE;
  }

  /** the default palette builder */
  static final class __DefaultStrokePaletteBuilder extends
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
}
