package org.optimizationBenchmarking.utils.graphics.drivers.eps;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.freehep.graphicsio.ps.PSGraphics2D;
import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.GraphicProxy;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** the internal eps graphic */
final class _EPSGraphic extends GraphicProxy<PSGraphics2D> {

  /** the width */
  final int m_w;

  /** the height */
  final int m_h;

  /**
   * instantiate
   * 
   * @param graphic
   *          the graphic to use
   * @param id
   *          the graphic id identifying this graphic and the path under
   *          which the contents of the graphic are stored
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param w
   *          the width
   * @param h
   *          the height
   */
  _EPSGraphic(final PSGraphics2D graphic, final GraphicID id,
      final IGraphicListener listener, final int w, final int h) {
    super(graphic, id, listener);
    this.m_h = h;
    this.m_w = w;
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle2D getDeviceBounds() {
    return new Rectangle(0, 0, this.m_w, this.m_h);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction deviceToUnitWidth(final ELength unit) {
    return ELength.POINT_POSTSCRIPT.getConversion(unit);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction unitToDeviceWidth(final ELength unit) {
    return unit.getConversion(ELength.POINT_POSTSCRIPT);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction deviceToUnitHeight(final ELength unit) {
    return ELength.POINT_POSTSCRIPT.getConversion(unit);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction unitToDeviceHeight(final ELength unit) {
    return unit.getConversion(ELength.POINT_POSTSCRIPT);
  }

  /** {@inheritDoc} */
  @Override
  protected final void closeInner(final PSGraphics2D graphics)
      throws Throwable {
    Throwable error;

    error = null;
    try {
      graphics.endExport();
    } catch (final Throwable a) {
      error = a;
    }
    try {
      super.closeInner(graphics);
    } catch (final Throwable b) {
      error = ErrorUtils.aggregateError(error, b);
    }
    if (error != null) {
      ErrorUtils.throwAsRuntimeException(error);
    }
  }
}
