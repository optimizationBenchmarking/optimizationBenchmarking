package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;
import java.awt.Stroke;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** the chart builder */
public class Chart extends TitledElement {

  /** the graphic */
  final Graphic m_graphic;

  /** the chart driver */
  final ChartDriver m_driver;

  /** the style set */
  final StyleSet m_styleSet;

  /** the logger */
  private final Logger m_logger;

  /** the title font */
  private transient volatile Font m_defaultChartTitleFont;
  /** the data title font */
  private transient volatile Font m_defaultDataTitleFont;
  /** the default data stroke */
  private transient volatile Stroke m_defaultDataStroke;
  /** the id */
  private transient volatile String m_id;

  /**
   * create the line chart
   * 
   * @param graphic
   *          the graphic
   * @param styleSet
   *          the style set to use
   * @param driver
   *          the chart driver
   * @param logger
   *          the logger
   */
  protected Chart(final Graphic graphic, final StyleSet styleSet,
      final Logger logger, final ChartDriver driver) {
    super(null);

    ChartBuilder._checkGraphic(graphic);
    ChartBuilder._checkStyleSet(styleSet);
    ChartBuilder._checkChartDriver(driver);
    this.m_styleSet = styleSet;
    this.m_graphic = graphic;
    this.m_driver = driver;
    this.m_logger = logger;

    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest("Beginning to builder " + this._id());//$NON-NLS-1$
    }
  }

  /**
   * Compute the id of the chart
   * 
   * @return the id
   */
  final String _id() {
    MemoryTextOutput mto;

    if (this.m_id == null) {
      mto = new MemoryTextOutput();
      mto.append(this.getClass().getSimpleName());
      mto.append('#');
      mto.append(System.identityHashCode(this));
      mto.append('@');
      mto.append(this.m_driver.getClass().getSimpleName());
      mto.append('>');
      mto.append(this.m_graphic.getClass().getSimpleName());
      mto.append('#');
      mto.append(System.identityHashCode(this.m_graphic));
      this.m_id = mto.toString();
    }

    return this.m_id;
  }

  /** {@inheritDoc} */
  @Override
  protected final Logger getLogger() {
    return this.m_logger;
  }

  /**
   * Get a font to be used for line titles
   * 
   * @return the line title font
   */
  synchronized final Font _getDefaultDataTitleFont() {
    if (this.m_defaultDataTitleFont == null) {
      this.m_defaultDataTitleFont = this.m_driver
          .createDefaultDataTitleFont(this.m_styleSet);
    }
    return this.m_defaultDataTitleFont;
  }

  /**
   * Get a font to be used for chart titles
   * 
   * @return the chart title font
   */
  synchronized final Font _getDefaultChartTitleFont() {

    if (this.m_defaultChartTitleFont == null) {
      this.m_defaultChartTitleFont = this.m_driver
          .createDefaultChartTitleFont(this.m_styleSet);
    }
    return this.m_defaultChartTitleFont;
  }

  /**
   * Get the stroke to be used for lines
   * 
   * @return the line stroke
   */
  synchronized final Stroke _getDefaultDataStroke() {
    if (this.m_defaultDataStroke == null) {
      this.m_defaultDataStroke = this.m_driver
          .createDefaultDataStroke(this.m_styleSet);
    }
    return this.m_defaultDataStroke;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINEST))) {
      this.m_logger.finest("Finished with " + this._id()); //$NON-NLS-1$
    }
    super.onClose();
  }
}
