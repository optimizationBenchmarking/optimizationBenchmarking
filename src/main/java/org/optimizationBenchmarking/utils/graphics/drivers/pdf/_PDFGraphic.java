package org.optimizationBenchmarking.utils.graphics.drivers.pdf;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.freehep.graphicsio.PageConstants;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.GraphicProxy;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** the internal pdf graphic */
final class _PDFGraphic extends GraphicProxy<PDFGraphics2D> {

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
  _PDFGraphic(final PDFGraphics2D graphic, final GraphicID id,
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
  protected final void closeInner(final PDFGraphics2D graphics)
      throws Throwable {
    final Dimension mess;
    Throwable error;

    synchronized (PageConstants.class) {
      error = null;
      try {
        mess = PDFGraphicDriver.INSTANCE.m_messWith;
        mess.setSize(this.m_w, this.m_h);
        try {
          graphics.endExport();
        } finally {
          mess.setSize(PDFGraphicDriver.INSTANCE.m_correctDim);
        }
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
}
