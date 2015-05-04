package org.optimizationBenchmarking.utils.chart.spec;

/** The legend mode of the diagram */
public enum ELegendMode {

  /** hide the whole legend as well as all title information */
  HIDE_COMPLETE_LEGEND(false),

  /** show the complete legend as well as all title information */
  SHOW_COMPLETE_LEGEND(true),

  /**
   * the whole diagram is designed as legend, i.e., the legend can fill all
   * of the space, the data is unimportant. This makes sense when we plot
   * several small diagrams of the same structure. We can plot one single
   * "legend chart" and omit the legends on the other, "real" charts,
   * saving space.
   */
  CHART_IS_LEGEND(true);

  /** should we show the legend? */
  private final boolean m_showLegend;

  /**
   * Create the legend mode
   *
   * @param showLegend
   *          should we show the legend?
   */
  private ELegendMode(final boolean showLegend) {
    this.m_showLegend = showLegend;
  }

  /**
   * Should we show the legend?
   *
   * @return {@code true} if the legend should be shown, {@code false}
   *         otherwise
   */
  public final boolean isLegendShown() {
    return this.m_showLegend;
  }
}
