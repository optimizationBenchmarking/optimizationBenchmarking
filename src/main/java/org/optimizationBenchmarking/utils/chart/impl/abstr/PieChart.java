package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IDataScalar;
import org.optimizationBenchmarking.utils.chart.spec.IPieChart;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** The builder for pie charts. */
public class PieChart extends Chart implements IPieChart {

  /** at least one slice has been added */
  private static final int FLAG_HAS_SLICE = (Chart.FLAG_CHART_BUILDER_MAX << 1);

  /** the id counter */
  private volatile int m_idCounter;

  /** the slices */
  private ArrayList<CompiledDataScalar> m_slices;

  /**
   * create the line chart
   *
   * @param selector
   *          the selector
   */
  protected PieChart(final ChartSelector selector) {
    this(selector.getGraphic(), selector.getStyleSet(),//
        selector._getLogger(), selector.getChartDriver());
  }

  /**
   * create the line chart
   *
   * @param graphic
   *          the graphic
   * @param styles
   *          the style set to use
   * @param logger
   *          the logger to use
   * @param driver
   *          the chart driver
   */
  protected PieChart(final Graphic graphic, final StyleSet styles,
      final Logger logger, final ChartDriver driver) {
    super(graphic, styles, logger, driver);

    this.m_slices = new ArrayList<>();

    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_SLICE: {
        append.append("hasSlice");break;} //$NON-NLS-1$
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Add a new slice
   *
   * @param slice
   *          the slice
   */
  synchronized final void _addSlice(final CompiledDataScalar slice) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, FSM.FLAG_NOTHING,
        PieChart.FLAG_HAS_SLICE, FSM.FLAG_NOTHING);
    this.m_slices.add(slice);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {
    super.afterChildClosed(child);
    if (child instanceof DataScalar) {
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    Logger logger;
    Graphic graphic;
    CompiledPieChart chart;
    Font titleFont;

    this.fsmStateAssertAndSet(ChartElement.STATE_ALIVE,
        ChartElement.STATE_DEAD);
    this.fsmFlagsAssertTrue(PieChart.FLAG_HAS_SLICE);

    graphic = this.m_graphic;
    logger = this.getLogger();
    try {
      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Now compiling " + this._id()); //$NON-NLS-1$
      }

      if (this.m_title != null) {
        titleFont = this.m_titleFont;
        if (titleFont == null) {
          titleFont = this.m_driver
              .getDefaultChartTitleFont(this.m_styleSet);
        } else {
          titleFont = this.m_driver.scaleTitleFont(this.m_titleFont);
        }
      } else {
        titleFont = null;
      }

      chart = new CompiledPieChart(this.m_title, titleFont,
          this.m_legendMode, new ArrayListView<>(
              this.m_slices.toArray(new CompiledDataScalar[this.m_slices
                                                           .size()])));

      this.m_slices = null;

      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Now rendering the compiled " + this._id()); //$NON-NLS-1$
      }

      this.m_driver.renderPieChart(chart, graphic, logger);
    } catch (final Throwable error) {
      ErrorUtils.logError(logger,
          ("Unrecoverable error during rendering of compiled pie chart #" //$NON-NLS-1$
              + this._id()), error, true, RethrowMode.AS_RUNTIME_EXCEPTION);
    } finally {
      this.m_slices = null;
      chart = null;
      graphic = null;
    }

    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IDataScalar slice() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    return new DataScalar(this, (++this.m_idCounter));
  }
}
