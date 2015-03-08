package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.impl.abstr.ChartDriver;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledLineChart2D;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledPieChart;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/**
 * The driver for JFreeChart-based charts.
 */
public final class JFreeChartDriver extends ChartDriver {

  /** the error */
  private final Throwable m_error;

  /** create */
  JFreeChartDriver() {
    super();

    Throwable cannot;

    cannot = null;
    try {

      ReflectionUtils.ensureClassesAreLoaded(//
          "org.jfree.chart.ChartFactory", //$NON-NLS-1$
          "org.jfree.chart.JFreeChart", //$NON-NLS-1$
          "org.jfree.chart.LegendItem", //$NON-NLS-1$
          "org.jfree.chart.LegendItemCollection", //$NON-NLS-1$
          "org.jfree.chart.annotations.XYAnnotation", //$NON-NLS-1$
          "org.jfree.chart.axis.NumberAxis", //$NON-NLS-1$
          "org.jfree.chart.axis.TickUnitSource", //$NON-NLS-1$
          "org.jfree.chart.axis.ValueAxis", //$NON-NLS-1$
          "org.jfree.chart.block.BlockParams", //$NON-NLS-1$
          "org.jfree.chart.block.LengthConstraintType", //$NON-NLS-1$
          "org.jfree.chart.block.RectangleConstraint", //$NON-NLS-1$
          "org.jfree.chart.event.RendererChangeListener", //$NON-NLS-1$
          "org.jfree.chart.labels.ItemLabelPosition", //$NON-NLS-1$
          "org.jfree.chart.labels.XYItemLabelGenerator", //$NON-NLS-1$
          "org.jfree.chart.labels.XYSeriesLabelGenerator", //$NON-NLS-1$
          "org.jfree.chart.labels.XYToolTipGenerator", //$NON-NLS-1$
          "org.jfree.chart.plot.CrosshairState", //$NON-NLS-1$
          "org.jfree.chart.plot.DatasetRenderingOrder", //$NON-NLS-1$
          "org.jfree.chart.plot.DrawingSupplier", //$NON-NLS-1$
          "org.jfree.chart.plot.Marker", //$NON-NLS-1$
          "org.jfree.chart.plot.PlotOrientation", //$NON-NLS-1$
          "org.jfree.chart.plot.PlotRenderingInfo", //$NON-NLS-1$
          "org.jfree.chart.plot.SeriesRenderingOrder", //$NON-NLS-1$
          "org.jfree.chart.plot.XYPlot", //$NON-NLS-1$
          "org.jfree.chart.renderer.xy.XYItemRenderer", //$NON-NLS-1$
          "org.jfree.chart.renderer.xy.XYItemRendererState", //$NON-NLS-1$
          "org.jfree.chart.renderer.xy.XYLineAndShapeRenderer", //$NON-NLS-1$
          "org.jfree.chart.title.LegendTitle", //$NON-NLS-1$
          "org.jfree.chart.title.TextTitle", //$NON-NLS-1$
          "org.jfree.chart.urls.XYURLGenerator", //$NON-NLS-1$
          "org.jfree.data.DomainOrder", //$NON-NLS-1$
          "org.jfree.data.Range", //$NON-NLS-1$
          "org.jfree.data.Range", //$NON-NLS-1$
          "org.jfree.data.general.DatasetChangeListener", //$NON-NLS-1$
          "org.jfree.data.general.DatasetGroup", //$NON-NLS-1$
          "org.jfree.data.xy.XYDataset", //$NON-NLS-1$
          "org.jfree.ui.HorizontalAlignment", //$NON-NLS-1$
          "org.jfree.ui.Layer", //$NON-NLS-1$
          "org.jfree.ui.RectangleEdge", //$NON-NLS-1$
          "org.jfree.ui.RectangleInsets", //$NON-NLS-1$
          "org.jfree.ui.Size2D", //$NON-NLS-1$
          "org.jfree.ui.VerticalAlignment", //$NON-NLS-1$
          "org.jfree.util.UnitType" //$NON-NLS-1$
      );

    } catch (final Throwable error) {
      cannot = error;
    }

    this.m_error = cannot;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (this.m_error == null);
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    if (this.m_error != null) {
      throw new UnsupportedOperationException(//
          "JFreeChart driver cannot be used.", //$NON-NLS-1$
          this.m_error);
    }
    super.checkCanUse();
  }

  /**
   * Get the instance of the {@link JFreeChartDriver}.
   * 
   * @return the instance of the {@link JFreeChartDriver}
   */
  public static final JFreeChartDriver getInstance() {
    return __JFreeChartDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderLineChart2D(final CompiledLineChart2D chart,
      final Graphic graphic, final Logger logger) {
    new _JFreeChartLineChart2DRenderer(chart)._render(graphic);
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderPieChart(final CompiledPieChart chart,
      final Graphic graphic, final Logger logger) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "JFreeChart-based Chart Driver"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __JFreeChartDriverLoader {

    /** the JFreeChart-based chart driver */
    static final JFreeChartDriver INSTANCE = new JFreeChartDriver();

  }
}