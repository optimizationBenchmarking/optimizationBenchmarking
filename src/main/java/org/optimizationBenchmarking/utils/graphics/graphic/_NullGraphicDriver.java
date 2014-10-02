package org.optimizationBenchmarking.utils.graphics.graphic;

import java.io.OutputStream;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.DoubleDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.JavaDefaultPalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.DefaultStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** the null driver */
final class _NullGraphicDriver implements IGraphicDriver {

  /** the globally shared instance */
  static final _NullGraphicDriver INSTANCE = new _NullGraphicDriver();

  /** the null instance */
  _NullGraphicDriver() {
    super();
  }

  /**
   * Create the {@code NullGraphic}
   * 
   * @param size
   *          the size
   * @param listener
   *          the listener
   * @return the graphic
   */
  private static final Graphic __create(final PhysicalDimension size,
      final IObjectListener listener) {
    final ELength sizeUnit;
    sizeUnit = size.getUnit();
    return new _NullGraphic(listener, ((sizeUnit == ELength.POINT) ? size
        : new DoubleDimension(//
            sizeUnit.convertTo(size.getWidth(), ELength.POINT),//
            sizeUnit.convertTo(size.getHeight(), ELength.POINT))));
  }

  /** {@inheritDoc} */
  @Override
  public final Graphic createGraphic(final Path folder,
      final String nameSuggestion, final PhysicalDimension size,
      final IObjectListener listener) {
    return _NullGraphicDriver.__create(size, listener);
  }

  /** {@inheritDoc} */
  @Override
  public final Graphic createGraphic(final OutputStream os,
      final PhysicalDimension size, final IObjectListener listener) {
    return _NullGraphicDriver.__create(size, listener);
  }

  /** {@inheritDoc} */
  @Override
  public final ColorPalette getColorPalette() {
    return JavaDefaultPalette.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final StrokePalette getStrokePalette() {
    return DefaultStrokePalette.INSTANCE;
  }

}
