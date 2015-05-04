package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.Layer;

/**
 * An internal wrapper class for the XY-Renderer
 */
public class _JFreeChartXYItemRenderer implements XYItemRenderer {

  /** the renderer to forward to */
  private final XYItemRenderer m_out;

  /** the maximum x */
  double m_maxX;

  /** the maximum y */
  double m_maxY;

  /** the minimum x */
  double m_minX;

  /** the minimum y */
  double m_minY;

  /**
   * create a wrapped renderer
   *
   * @param out
   *          the output renderer
   */
  _JFreeChartXYItemRenderer(final XYItemRenderer out) {
    this.m_out = out;

    this.m_maxX = Double.NEGATIVE_INFINITY;
    this.m_maxY = Double.NEGATIVE_INFINITY;
    this.m_minX = Double.POSITIVE_INFINITY;
    this.m_minY = Double.POSITIVE_INFINITY;
  }

  /** {@inheritDoc} */
  @Override
  public final LegendItemCollection getLegendItems() {
    return this.m_out.getLegendItems();
  }

  /** {@inheritDoc} */
  @Override
  public final void addAnnotation(final XYAnnotation arg0) {
    this.m_out.addAnnotation(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void addAnnotation(final XYAnnotation arg0, final Layer arg1) {
    this.m_out.addAnnotation(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void addChangeListener(final RendererChangeListener arg0) {
    this.m_out.addChangeListener(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawAnnotations(final Graphics2D arg0,
      final Rectangle2D arg1, final ValueAxis arg2, final ValueAxis arg3,
      final Layer arg4, final PlotRenderingInfo arg5) {
    this.m_out.drawAnnotations(arg0, arg1, arg2, arg3, arg4, arg5);

  }

  /** {@inheritDoc} */
  @Override
  public final void drawDomainGridLine(final Graphics2D arg0,
      final XYPlot arg1, final ValueAxis arg2, final Rectangle2D arg3,
      final double arg4) {
    this.m_out.drawDomainGridLine(arg0, arg1, arg2, arg3, arg4);

  }

  /** {@inheritDoc} */
  @Override
  public final void drawDomainMarker(final Graphics2D arg0,
      final XYPlot arg1, final ValueAxis arg2, final Marker arg3,
      final Rectangle2D arg4) {
    this.m_out.drawDomainMarker(arg0, arg1, arg2, arg3, arg4);

  }

  /** {@inheritDoc} */
  @Override
  public final void drawItem(final Graphics2D arg0,
      final XYItemRendererState arg1, final Rectangle2D arg2,
      final PlotRenderingInfo arg3, final XYPlot arg4,
      final ValueAxis arg5, final ValueAxis arg6, final XYDataset arg7,
      final int arg8, final int arg9, final CrosshairState arg10,
      final int arg11) {
    this.m_out.drawItem(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7,
        arg8, arg9, arg10, arg11);

  }

  /** {@inheritDoc} */
  @Override
  public final void drawRangeLine(final Graphics2D arg0,
      final XYPlot arg1, final ValueAxis arg2, final Rectangle2D arg3,
      final double arg4, final Paint arg5, final Stroke arg6) {
    this.m_out.drawRangeLine(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRangeMarker(final Graphics2D arg0,
      final XYPlot arg1, final ValueAxis arg2, final Marker arg3,
      final Rectangle2D arg4) {
    this.drawRangeMarker(arg0, arg1, arg2, arg3, arg4);

  }

  /** {@inheritDoc} */
  @Override
  public final void fillDomainGridBand(final Graphics2D arg0,
      final XYPlot arg1, final ValueAxis arg2, final Rectangle2D arg3,
      final double arg4, final double arg5) {
    this.m_out.fillDomainGridBand(arg0, arg1, arg2, arg3, arg4, arg5);

  }

  /** {@inheritDoc} */
  @Override
  public final void fillRangeGridBand(final Graphics2D arg0,
      final XYPlot arg1, final ValueAxis arg2, final Rectangle2D arg3,
      final double arg4, final double arg5) {
    this.m_out.fillRangeGridBand(arg0, arg1, arg2, arg3, arg4, arg5);
  }

  /** {@inheritDoc} */
  @Override
  public final Range findDomainBounds(final XYDataset arg0) {
    return this.m_out.findDomainBounds(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final Range findRangeBounds(final XYDataset arg0) {
    return this.m_out.findRangeBounds(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final Font getBaseItemLabelFont() {
    return this.m_out.getBaseItemLabelFont();
  }

  /** {@inheritDoc} */
  @Override
  public final XYItemLabelGenerator getBaseItemLabelGenerator() {
    return this.m_out.getBaseItemLabelGenerator();
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getBaseItemLabelPaint() {
    return this.m_out.getBaseItemLabelPaint();
  }

  /** {@inheritDoc} */
  @Override
  public final Boolean getBaseItemLabelsVisible() {
    return this.m_out.getBaseItemLabelsVisible();
  }

  /** {@inheritDoc} */
  @Override
  public final ItemLabelPosition getBaseNegativeItemLabelPosition() {
    return this.m_out.getBaseNegativeItemLabelPosition();
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getBaseOutlinePaint() {
    return this.m_out.getBaseOutlinePaint();
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke getBaseOutlineStroke() {
    return this.m_out.getBaseOutlineStroke();
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getBasePaint() {
    return this.m_out.getBasePaint();
  }

  /** {@inheritDoc} */
  @Override
  public final ItemLabelPosition getBasePositiveItemLabelPosition() {
    return this.m_out.getBasePositiveItemLabelPosition();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean getBaseSeriesVisible() {
    return this.m_out.getBaseSeriesVisible();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean getBaseSeriesVisibleInLegend() {
    return this.m_out.getBaseSeriesVisibleInLegend();
  }

  /** {@inheritDoc} */
  @Override
  public final Shape getBaseShape() {
    return this.m_out.getBaseShape();
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke getBaseStroke() {
    return this.m_out.getBaseStroke();
  }

  /** {@inheritDoc} */
  @Override
  public final XYToolTipGenerator getBaseToolTipGenerator() {
    return this.m_out.getBaseToolTipGenerator();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated deprecated
   */
  @SuppressWarnings("deprecation")
  @Deprecated
  @Override
  public final Font getItemLabelFont() {
    return this.m_out.getItemLabelFont();
  }

  /** {@inheritDoc} */
  @Override
  public final Font getItemLabelFont(final int arg0, final int arg1) {
    return this.m_out.getItemLabelFont(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final XYItemLabelGenerator getItemLabelGenerator(final int arg0,
      final int arg1) {
    return this.m_out.getItemLabelGenerator(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated deprecated
   */
  @SuppressWarnings("deprecation")
  @Deprecated
  @Override
  public final Paint getItemLabelPaint() {
    return this.m_out.getItemLabelPaint();
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getItemLabelPaint(final int arg0, final int arg1) {
    return this.m_out.getItemLabelPaint(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getItemOutlinePaint(final int arg0, final int arg1) {
    return this.m_out.getItemOutlinePaint(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke getItemOutlineStroke(final int arg0, final int arg1) {
    return this.m_out.getItemOutlineStroke(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getItemPaint(final int arg0, final int arg1) {
    return this.m_out.getItemPaint(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final Shape getItemShape(final int arg0, final int arg1) {
    return this.m_out.getItemShape(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke getItemStroke(final int arg0, final int arg1) {
    return this.m_out.getItemStroke(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean getItemVisible(final int arg0, final int arg1) {
    return this.m_out.getItemVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final LegendItem getLegendItem(final int arg0, final int arg1) {
    return this.m_out.getLegendItem(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final XYSeriesLabelGenerator getLegendItemLabelGenerator() {
    return this.m_out.getLegendItemLabelGenerator();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated deprecated
   */
  @SuppressWarnings("deprecation")
  @Deprecated
  @Override
  public final ItemLabelPosition getNegativeItemLabelPosition() {
    return this.m_out.getNegativeItemLabelPosition();
  }

  /** {@inheritDoc} */
  @Override
  public final ItemLabelPosition getNegativeItemLabelPosition(
      final int arg0, final int arg1) {
    return this.m_out.getNegativeItemLabelPosition(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final int getPassCount() {
    return this.m_out.getPassCount();
  }

  /** {@inheritDoc} */
  @Override
  public final XYPlot getPlot() {
    return this.m_out.getPlot();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated deprecated
   */
  @SuppressWarnings("deprecation")
  @Deprecated
  @Override
  public final ItemLabelPosition getPositiveItemLabelPosition() {
    return this.getPositiveItemLabelPosition();
  }

  /** {@inheritDoc} */
  @Override
  public final ItemLabelPosition getPositiveItemLabelPosition(
      final int arg0, final int arg1) {
    return this.m_out.getPositiveItemLabelPosition(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final Font getSeriesItemLabelFont(final int arg0) {
    return this.m_out.getSeriesItemLabelFont(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final XYItemLabelGenerator getSeriesItemLabelGenerator(
      final int arg0) {
    return this.m_out.getSeriesItemLabelGenerator(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getSeriesItemLabelPaint(final int arg0) {
    return this.m_out.getSeriesItemLabelPaint(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final ItemLabelPosition getSeriesNegativeItemLabelPosition(
      final int arg0) {
    return this.m_out.getSeriesNegativeItemLabelPosition(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getSeriesOutlinePaint(final int arg0) {
    return this.m_out.getSeriesOutlinePaint(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke getSeriesOutlineStroke(final int arg0) {
    return this.m_out.getSeriesOutlineStroke(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getSeriesPaint(final int arg0) {
    return this.m_out.getSeriesPaint(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final ItemLabelPosition getSeriesPositiveItemLabelPosition(
      final int arg0) {
    return this.m_out.getSeriesPositiveItemLabelPosition(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final Shape getSeriesShape(final int arg0) {
    return this.m_out.getSeriesShape(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke getSeriesStroke(final int arg0) {
    return this.m_out.getSeriesStroke(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final XYToolTipGenerator getSeriesToolTipGenerator(final int arg0) {
    return this.m_out.getSeriesToolTipGenerator(arg0);
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated deprecated
   */
  @SuppressWarnings("deprecation")
  @Deprecated
  @Override
  public final Boolean getSeriesVisible() {
    return this.m_out.getSeriesVisible();
  }

  /** {@inheritDoc} */
  @Override
  public final Boolean getSeriesVisible(final int arg0) {
    return this.getSeriesVisible(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final Boolean getSeriesVisibleInLegend() {
    return this.getSeriesVisibleInLegend();
  }

  /** {@inheritDoc} */
  @Override
  public final Boolean getSeriesVisibleInLegend(final int arg0) {
    return this.getSeriesVisible(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final XYToolTipGenerator getToolTipGenerator(final int arg0,
      final int arg1) {
    return this.getToolTipGenerator(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final XYURLGenerator getURLGenerator() {
    return this.getURLGenerator();
  }

  /** {@inheritDoc} */
  @Override
  public final XYItemRendererState initialise(final Graphics2D arg0,
      final Rectangle2D arg1, final XYPlot arg2, final XYDataset arg3,
      final PlotRenderingInfo arg4) {
    final double x1, y1, x2, y2;
    // AffineTransform t;

    if (arg1 != null) {

      x1 = arg1.getMinX();
      x2 = arg1.getMaxX();
      y1 = arg1.getMinY();
      y2 = arg1.getMaxY();

      this.m_minX = Math.min(this.m_minX, Math.min(x1, x2));
      this.m_maxX = Math.max(this.m_maxX, Math.max(x1, x2));

      this.m_minY = Math.min(this.m_minY, Math.min(y1, y2));
      this.m_maxY = Math.max(this.m_maxY, Math.max(y1, y2));
    }

    return this.m_out.initialise(arg0, arg1, arg2, arg3, arg4);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isItemLabelVisible(final int arg0, final int arg1) {
    return this.m_out.isItemLabelVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isSeriesItemLabelsVisible(final int arg0) {
    return this.m_out.isSeriesItemLabelsVisible(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isSeriesVisible(final int arg0) {
    return this.m_out.isSeriesVisible(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isSeriesVisibleInLegend(final int arg0) {
    return this.m_out.isSeriesVisibleInLegend(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean removeAnnotation(final XYAnnotation arg0) {
    return this.m_out.removeAnnotation(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void removeAnnotations() {
    this.m_out.removeAnnotations();
  }

  /** {@inheritDoc} */
  @Override
  public final void removeChangeListener(final RendererChangeListener arg0) {
    this.m_out.removeChangeListener(arg0);

  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseItemLabelFont(final Font arg0) {
    this.m_out.setBaseItemLabelFont(arg0);

  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseItemLabelGenerator(
      final XYItemLabelGenerator arg0) {
    this.m_out.setBaseItemLabelGenerator(arg0);

  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseItemLabelPaint(final Paint arg0) {
    this.m_out.setBaseItemLabelPaint(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseItemLabelsVisible(final boolean arg0) {
    this.m_out.setBaseItemLabelsVisible(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseItemLabelsVisible(final Boolean arg0) {
    this.m_out.setBaseItemLabelsVisible(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseItemLabelsVisible(final Boolean arg0,
      final boolean arg1) {
    this.m_out.setBaseItemLabelsVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseNegativeItemLabelPosition(
      final ItemLabelPosition arg0) {
    this.m_out.setBaseNegativeItemLabelPosition(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseNegativeItemLabelPosition(
      final ItemLabelPosition arg0, final boolean arg1) {
    this.m_out.setBaseNegativeItemLabelPosition(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseOutlinePaint(final Paint arg0) {
    this.m_out.setBaseOutlinePaint(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseOutlineStroke(final Stroke arg0) {
    this.m_out.setBaseOutlineStroke(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBasePaint(final Paint arg0) {
    this.m_out.setBasePaint(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBasePositiveItemLabelPosition(
      final ItemLabelPosition arg0) {
    this.m_out.setBasePositiveItemLabelPosition(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBasePositiveItemLabelPosition(
      final ItemLabelPosition arg0, final boolean arg1) {
    this.m_out.setBasePositiveItemLabelPosition(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseSeriesVisible(final boolean arg0) {
    this.m_out.setBaseSeriesVisible(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseSeriesVisible(final boolean arg0,
      final boolean arg1) {
    this.m_out.setBaseSeriesVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseSeriesVisibleInLegend(final boolean arg0) {
    this.m_out.setBaseSeriesVisibleInLegend(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseSeriesVisibleInLegend(final boolean arg0,
      final boolean arg1) {
    this.setBaseSeriesVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseShape(final Shape arg0) {
    this.m_out.setBaseShape(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseStroke(final Stroke arg0) {
    this.m_out.setBaseStroke(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseToolTipGenerator(final XYToolTipGenerator arg0) {
    this.m_out.setBaseToolTipGenerator(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setItemLabelFont(final Font arg0) {
    this.m_out.setItemLabelFont(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setItemLabelGenerator(final XYItemLabelGenerator arg0) {
    this.m_out.setItemLabelGenerator(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setItemLabelPaint(final Paint arg0) {
    this.m_out.setItemLabelPaint(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setItemLabelsVisible(final boolean arg0) {
    this.m_out.setItemLabelsVisible(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setItemLabelsVisible(final Boolean arg0) {
    this.m_out.setItemLabelsVisible(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setItemLabelsVisible(final Boolean arg0,
      final boolean arg1) {
    this.m_out.setItemLabelsVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setLegendItemLabelGenerator(
      final XYSeriesLabelGenerator arg0) {
    this.m_out.setLegendItemLabelGenerator(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setNegativeItemLabelPosition(
      final ItemLabelPosition arg0) {
    this.m_out.setNegativeItemLabelPosition(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setNegativeItemLabelPosition(
      final ItemLabelPosition arg0, final boolean arg1) {
    this.m_out.setNegativeItemLabelPosition(arg0, arg1);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setOutlinePaint(final Paint arg0) {
    this.m_out.setOutlinePaint(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setOutlineStroke(final Stroke arg0) {
    this.m_out.setOutlineStroke(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setPaint(final Paint arg0) {
    this.m_out.setPaint(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setPlot(final XYPlot arg0) {
    this.m_out.setPlot(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setPositiveItemLabelPosition(
      final ItemLabelPosition arg0) {
    this.m_out.setPositiveItemLabelPosition(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setPositiveItemLabelPosition(
      final ItemLabelPosition arg0, final boolean arg1) {
    this.m_out.setPositiveItemLabelPosition(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesItemLabelFont(final int arg0, final Font arg1) {
    this.m_out.setSeriesItemLabelFont(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesItemLabelGenerator(final int arg0,
      final XYItemLabelGenerator arg1) {
    this.m_out.setSeriesItemLabelGenerator(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesItemLabelPaint(final int arg0,
      final Paint arg1) {
    this.m_out.setSeriesItemLabelPaint(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesItemLabelsVisible(final int arg0,
      final boolean arg1) {
    this.m_out.setSeriesItemLabelsVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesItemLabelsVisible(final int arg0,
      final Boolean arg1) {
    this.m_out.setSeriesItemLabelsVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesItemLabelsVisible(final int arg0,
      final Boolean arg1, final boolean arg2) {
    this.m_out.setSeriesItemLabelsVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesNegativeItemLabelPosition(final int arg0,
      final ItemLabelPosition arg1) {
    this.m_out.setSeriesNegativeItemLabelPosition(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesNegativeItemLabelPosition(final int arg0,
      final ItemLabelPosition arg1, final boolean arg2) {
    this.m_out.setSeriesNegativeItemLabelPosition(arg0, arg1, arg2);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesOutlinePaint(final int arg0, final Paint arg1) {
    this.m_out.setSeriesOutlinePaint(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesOutlineStroke(final int arg0,
      final Stroke arg1) {
    this.m_out.setSeriesOutlineStroke(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesPaint(final int arg0, final Paint arg1) {
    this.m_out.setSeriesPaint(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesPositiveItemLabelPosition(final int arg0,
      final ItemLabelPosition arg1) {
    this.m_out.setSeriesPositiveItemLabelPosition(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesPositiveItemLabelPosition(final int arg0,
      final ItemLabelPosition arg1, final boolean arg2) {
    this.m_out.setSeriesPositiveItemLabelPosition(arg0, arg1, arg2);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesShape(final int arg0, final Shape arg1) {
    this.m_out.setSeriesShape(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesStroke(final int arg0, final Stroke arg1) {
    this.m_out.setSeriesStroke(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesToolTipGenerator(final int arg0,
      final XYToolTipGenerator arg1) {
    this.m_out.setSeriesToolTipGenerator(arg0, arg1);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setSeriesVisible(final Boolean arg0) {
    this.m_out.setSeriesVisible(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesVisible(final int arg0, final Boolean arg1) {
    this.m_out.setSeriesVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setSeriesVisible(final Boolean arg0, final boolean arg1) {
    this.m_out.setSeriesVisible(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesVisible(final int arg0, final Boolean arg1,
      final boolean arg2) {
    this.m_out.setSeriesVisible(arg0, arg1, arg2);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setSeriesVisibleInLegend(final Boolean arg0) {
    this.m_out.setSeriesVisibleInLegend(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesVisibleInLegend(final int arg0,
      final Boolean arg1) {
    this.m_out.setSeriesVisibleInLegend(arg0, arg1);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setSeriesVisibleInLegend(final Boolean arg0,
      final boolean arg1) {
    this.m_out.setSeriesVisibleInLegend(arg0, arg1);
  }

  /** {@inheritDoc} */
  @Override
  public final void setSeriesVisibleInLegend(final int arg0,
      final Boolean arg1, final boolean arg2) {
    this.m_out.setSeriesVisibleInLegend(arg0, arg1, arg2);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setShape(final Shape arg0) {
    this.m_out.setShape(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setStroke(final Stroke arg0) {
    this.m_out.setStroke(arg0);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final void setToolTipGenerator(final XYToolTipGenerator arg0) {
    this.m_out.setToolTipGenerator(arg0);
  }

  /** {@inheritDoc} */
  @Override
  public final void setURLGenerator(final XYURLGenerator arg0) {
    this.m_out.setURLGenerator(arg0);
  }

}
