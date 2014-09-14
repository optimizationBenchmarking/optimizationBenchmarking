package org.optimizationBenchmarking.utils.graphics.graphic;

import java.awt.geom.Dimension2D;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.DoubleDimension;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** the null driver */
final class _NullGraphicDriver extends AbstractGraphicDriver {

  /** the globally shared instance */
  static final _NullGraphicDriver INSTANCE = new _NullGraphicDriver();

  /** the null instance */
  _NullGraphicDriver() {
    super(".null"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphic doCreateGraphic(final GraphicID id,
      final Dimension2D size, final ELength sizeUnit,
      final IGraphicListener listener) {
    return new _NullGraphic(listener, id,
        ((sizeUnit == ELength.POINT) ? size : new DoubleDimension(//
            sizeUnit.convertTo(size.getWidth(), ELength.POINT),//
            sizeUnit.convertTo(size.getHeight(), ELength.POINT))));
  }

  /** {@inheritDoc} */
  @Override
  public final GraphicID createGraphicID(final Path suggestion) {
    return new GraphicID(this, suggestion);
  }

}
