package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.IChart;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** the chart builder */
public class Chart extends TitledElement implements IChart {

  /** the show legend has been set */
  private static final int FLAG_HAS_LEGEND_MODE = (TitledElement.FLAG_TITLED_ELEMENT_BUILDER_MAX << 1);

  /** the show legend has been set */
  static final int FLAG_CHART_BUILDER_MAX = Chart.FLAG_HAS_LEGEND_MODE;

  /** the graphic */
  final Graphic m_graphic;

  /** the chart driver */
  final ChartDriver m_driver;

  /** the style set */
  final StyleSet m_styleSet;

  /** the logger */
  private final Logger m_logger;

  /** the id */
  private transient volatile String m_id;

  /** the legend mode */
  ELegendMode m_legendMode;

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
    this.m_legendMode = ELegendMode.SHOW_COMPLETE_LEGEND;

    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest("Beginning to builder " + this._id());//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_LEGEND_MODE: {
        append.append("legendModeSet");break;} //$NON-NLS-1$
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
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
  public final void setLegendMode(final ELegendMode legendMode) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        Chart.FLAG_HAS_LEGEND_MODE, Chart.FLAG_HAS_LEGEND_MODE,
        FSM.FLAG_NOTHING);
    if (legendMode == null) {
      throw new IllegalArgumentException(//
          "Cannot set legend mode to null, if you don't want to specify it, don't set it in the first place."); //$NON-NLS-1$
    }
    this.m_legendMode = legendMode;
  }

  /** {@inheritDoc} */
  @Override
  protected final Logger getLogger() {
    return this.m_logger;
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
