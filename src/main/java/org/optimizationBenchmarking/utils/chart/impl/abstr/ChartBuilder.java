package org.optimizationBenchmarking.utils.chart.impl.abstr;

import org.optimizationBenchmarking.utils.chart.spec.IChartBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * A builder for creating the chart selector.
 */
public class ChartBuilder extends
ToolJobBuilder<ChartSelector, ChartBuilder> implements IChartBuilder {

  /** the chart driver */
  private final ChartDriver m_driver;

  /** the graphic to use */
  private Graphic m_graphic;

  /** the style set to use */
  private StyleSet m_styleSet;

  /**
   * create the chart builder
   *
   * @param driver
   *          the chart driver
   */
  protected ChartBuilder(final ChartDriver driver) {
    super();
    ChartBuilder._checkChartDriver(driver);
    this.m_driver = driver;
  }

  /**
   * check whether a graphic parameter is valid
   *
   * @param graphic
   *          the graphic parameter
   */
  static final void _checkGraphic(final Graphic graphic) {
    if (graphic == null) {
      throw new IllegalArgumentException(//
          "The graphic for a chart cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * check whether a chart driver is valid
   *
   * @param driver
   *          the chart driver to check
   */
  static final void _checkChartDriver(final ChartDriver driver) {
    if (driver == null) {
      throw new IllegalArgumentException("Chart driver cannot be null.");//$NON-NLS-1$
    }
  }

  /**
   * check whether a style set for a chart is valid
   *
   * @param styleSet
   *          the style set
   */
  static final void _checkStyleSet(final StyleSet styleSet) {
    if (styleSet == null) {
      throw new IllegalArgumentException(//
          "Style set for a chart cannot be null.");//$NON-NLS-1$
    }
    if (styleSet.getDefaultFont() == null) {
      throw new IllegalArgumentException(//
          "Style set for a chart cannot have null default font.");//$NON-NLS-1$
    }
    if (styleSet.getDefaultStroke() == null) {
      throw new IllegalArgumentException(//
          "Style set for a chart cannot have null default stroke.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ChartBuilder setGraphic(final Graphic graphic) {
    ChartBuilder._checkGraphic(graphic);
    this.m_graphic = graphic;
    return this;
  }

  /**
   * Get the graphic to draw the chart on
   *
   * @return the graphic to draw the chart on
   * @see #setGraphic(Graphic)
   */
  public final Graphic getGraphic() {
    return this.m_graphic;
  }

  /** {@inheritDoc} */
  @Override
  public final ChartBuilder setStyleSet(final StyleSet styleSet) {
    ChartBuilder._checkStyleSet(styleSet);
    this.m_styleSet = styleSet;
    return this;
  }

  /**
   * Get the style set to be used for the chart
   *
   * @return the style set to be used for the chart
   */
  public final StyleSet getStyleSet() {
    return this.m_styleSet;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    ChartBuilder._checkGraphic(this.m_graphic);
    ChartBuilder._checkStyleSet(this.m_styleSet);
    ChartBuilder._checkChartDriver(this.m_driver);
  }

  /**
   * Get the chart driver
   *
   * @return the chart driver
   */
  public final ChartDriver getChartDriver() {
    return this.m_driver;
  }

  /** {@inheritDoc} */
  @Override
  public ChartSelector create() {
    this.validate();
    return new ChartSelector(this);
  }

}
