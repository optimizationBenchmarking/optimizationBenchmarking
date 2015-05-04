package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** A compiled pie chart, ready for painting. */
public class CompiledPieChart extends CompiledChart {

  /** the slices */
  private final ArrayListView<CompiledDataScalar> m_slices;

  /**
   * Create a titled element
   *
   * @param title
   *          the title, or {@code null} if no title is specified
   * @param titleFont
   *          the title font, or {@code null} if no specific font is set
   * @param legendMode
   *          the legend mode
   * @param slices
   *          the slices
   */
  protected CompiledPieChart(final String title, final Font titleFont,
      final ELegendMode legendMode,
      final ArrayListView<CompiledDataScalar> slices) {
    super(title, titleFont, legendMode);

    if ((slices == null) || (slices.isEmpty())) {
      throw new IllegalArgumentException(
          "Pie chart must have at least one slice."); //$NON-NLS-1$
    }

    this.m_slices = slices;
  }

  /**
   * Get the slices
   *
   * @return the slices
   */
  public final ArrayListView<CompiledDataScalar> getSlices() {
    return this.m_slices;
  }
}
