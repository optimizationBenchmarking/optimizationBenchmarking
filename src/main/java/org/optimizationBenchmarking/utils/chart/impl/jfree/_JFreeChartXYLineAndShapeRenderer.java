package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;

import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataElement;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * <p>
 * This internal class tries to avoid a problem with
 * {@link org.jfree.chart.renderer.xy.XYLineAndShapeRenderer}.
 * {@link org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
 * XYLineAndShapeRenderer} iterates over the dataElements in a data set. It
 * paints them only if they are within the clip bounds of the drawing
 * window. Only when it paints them, it will use methods such as
 * {@link org.jfree.chart.plot.DefaultDrawingSupplier#getNextPaint()
 * getNextPaint} of the {@link org.jfree.chart.plot.DefaultDrawingSupplier
 * DefaultDrawingSupplier}. This, in turn, may lead to dataElements being
 * painted with the wrong color if the dataElements before them are outside
 * of the limits of the figure&hellip;
 * </p>
 */
final class _JFreeChartXYLineAndShapeRenderer extends
    XYLineAndShapeRenderer implements DrawingSupplier {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the stroke index */
  private volatile int m_strokeIndex;
  /** the color index */
  private volatile int m_paintIndex;

  /** the dataElements */
  private final ArrayListView<CompiledDataElement> m_dataElements;

  /**
   * create
   *
   * @param dataElements
   *          the dataElements
   */
  _JFreeChartXYLineAndShapeRenderer(
      final ArrayListView<CompiledDataElement> dataElements) {
    super();
    this.m_dataElements = dataElements;
  }

  /**
   * Get the paint for the given item
   *
   * @param row
   *          the row (series)
   * @param column
   *          the column
   * @return the paint
   */
  @Override
  public final Paint getItemPaint(final int row, final int column) {
    return this.m_dataElements.get(row).getColor();
  }

  /**
   * Get the series paint for the given series
   *
   * @param series
   *          the series
   * @return the paint
   */
  @Override
  public final Paint getSeriesPaint(final int series) {
    return this.m_dataElements.get(series).getColor();
  }

  /**
   * Get the stroke for the given item
   *
   * @param row
   *          the row (series)
   * @param column
   *          the column
   * @return the stroke
   */
  @Override
  public final Stroke getItemStroke(final int row, final int column) {
    return this.m_dataElements.get(row).getStroke();
  }

  /**
   * Get the series stroke for the given series
   *
   * @param series
   *          the series
   * @return the stroke
   */
  @Override
  public final Stroke getSeriesStroke(final int series) {
    return this.m_dataElements.get(series).getStroke();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Stroke getNextStroke() {
    return this.lookupSeriesStroke(this.m_strokeIndex++);
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke getNextOutlineStroke() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Paint getNextPaint() {
    return this.lookupSeriesPaint(this.m_paintIndex++);
  }

  /** {@inheritDoc} */
  @Override
  public final Paint lookupSeriesPaint(final int series) {
    return this.m_dataElements.get(series).getColor();
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke lookupSeriesStroke(final int series) {
    return this.m_dataElements.get(series).getStroke();
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getNextOutlinePaint() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getNextFillPaint() {
    return null;
  }

  /**
   * Returns always {@code null}
   *
   * @return {@code null}
   */
  @Override
  public final Shape getNextShape() {
    return null;
  }

  /**
   * Returns always {@code this}
   *
   * @return {@code this}
   */
  @Override
  public final DrawingSupplier getDrawingSupplier() {
    return this;
  }

  /**
   * Returns always {@code false}
   *
   * @return {@code false}
   */
  @Override
  public final boolean getItemShapeVisible(final int series, final int item) {
    return false;
  }
}
