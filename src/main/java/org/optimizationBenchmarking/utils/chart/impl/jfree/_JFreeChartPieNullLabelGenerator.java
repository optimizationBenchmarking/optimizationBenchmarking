package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.text.AttributedString;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

/** the pie null label generator */
final class _JFreeChartPieNullLabelGenerator implements
    PieSectionLabelGenerator {

  /** create */
  _JFreeChartPieNullLabelGenerator() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final String generateSectionLabel(final PieDataset dataset,
      final Comparable key) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final AttributedString generateAttributedSectionLabel(
      final PieDataset dataset, final Comparable key) {
    return null;
  }

}
