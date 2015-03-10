package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.font.TextAttribute;
import java.text.AttributedString;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataElement;

/** the pie title label generator */
final class _JFreeChartPieTitleLabelGenerator implements
    PieSectionLabelGenerator {

  /** create */
  _JFreeChartPieTitleLabelGenerator() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final String generateSectionLabel(final PieDataset dataset,
      final Comparable key) {
    _JFreeChartDataset<CompiledDataElement> data;
    CompiledDataElement element;
    int idx;

    if (dataset instanceof _JFreeChartDataset) {
      data = ((_JFreeChartDataset) dataset);
      idx = data.indexOf(key);
      if (idx >= 0) {
        element = data.m_data.get(idx);
        if (element != null) {
          return element.getTitle();
        }
      }
    }

    return null;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final AttributedString generateAttributedSectionLabel(
      final PieDataset dataset, final Comparable key) {
    _JFreeChartDataset<CompiledDataElement> data;
    CompiledDataElement element;
    AttributedString string;
    String title;
    int idx;

    if (dataset instanceof _JFreeChartDataset) {
      data = ((_JFreeChartDataset) dataset);
      idx = data.indexOf(key);
      if (idx >= 0) {
        element = data.m_data.get(idx);
        if (element != null) {
          title = element.getTitle();
          if (title != null) {
            string = new AttributedString(title);
            string
                .addAttribute(TextAttribute.FONT, element.getTitleFont());
            return string;
          }
        }
      }
    }

    return null;
  }

}
