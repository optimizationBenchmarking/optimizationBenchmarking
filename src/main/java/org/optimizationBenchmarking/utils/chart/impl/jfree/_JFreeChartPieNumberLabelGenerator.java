package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.font.TextAttribute;
import java.text.AttributedString;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataElement;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataScalar;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;

/** the pie label generator */
final class _JFreeChartPieNumberLabelGenerator implements
    PieSectionLabelGenerator {

  /** create */
  _JFreeChartPieNumberLabelGenerator() {
    super();
  }

  /**
   * Get the string representation of the element
   * 
   * @param element
   *          the compiled element
   * @return the string
   */
  private static final String __string(final CompiledDataElement element) {
    final Number number;

    if (element != null) {
      if (element instanceof CompiledDataScalar) {
        number = ((CompiledDataScalar) element).getData();
        if (number != null) {
          if (number instanceof Long) {
            return Long.toString(number.longValue());
          }
          if (number instanceof Integer) {
            return Integer.toString(number.intValue());
          }
          if (number instanceof Short) {
            return Short.toString(number.shortValue());
          }
          if (number instanceof Byte) {
            return Byte.toString(number.byteValue());
          }
          return SimpleNumberAppender.INSTANCE.toString(
              number.doubleValue(), ETextCase.IN_SENTENCE);
        }
      }
      return element.getTitle();
    }
    return null;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final String generateSectionLabel(final PieDataset dataset,
      final Comparable key) {
    _JFreeChartDataset<CompiledDataElement> data;
    int idx;

    if (dataset instanceof _JFreeChartDataset) {
      data = ((_JFreeChartDataset) dataset);
      idx = data.indexOf(key);
      if (idx >= 0) {
        return _JFreeChartPieNumberLabelGenerator.__string(data.m_data
            .get(idx));
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
        title = _JFreeChartPieNumberLabelGenerator.__string(element);
        if (title != null) {
          string = new AttributedString(title);
          string.addAttribute(TextAttribute.FONT, element.getTitleFont());
          return string;
        }
      }
    }

    return null;
  }

}
