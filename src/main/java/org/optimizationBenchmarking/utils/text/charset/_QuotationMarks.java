package org.optimizationBenchmarking.utils.text.charset;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * the quotation marks set
 */
final class _QuotationMarks extends ArraySetView<QuotationMarks> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create
   *
   * @param data
   *          the data
   */
  _QuotationMarks(final QuotationMarks[] data) {
    super(data);
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  final Object writeReplace() {
    return _QuotationMarksSerial.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  final Object readResolve() {
    return QuotationMarks.QUOTATION_MARKS;
  }
}
