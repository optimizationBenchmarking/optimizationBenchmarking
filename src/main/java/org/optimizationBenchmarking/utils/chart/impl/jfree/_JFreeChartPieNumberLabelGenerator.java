package org.optimizationBenchmarking.utils.chart.impl.jfree;

import org.jfree.chart.plot.PiePlot;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataElement;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataScalar;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;

/** the pie label generator */
final class _JFreeChartPieNumberLabelGenerator extends
    _JFreeChartPieLabelGenerator {

  /**
   * create*
   *
   * @param plot
   *          the plot
   */
  _JFreeChartPieNumberLabelGenerator(final PiePlot plot) {
    super(plot);
  }

  /** {@inheritDoc} */
  @Override
  final String _getTitle(final CompiledDataElement element) {
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

}
