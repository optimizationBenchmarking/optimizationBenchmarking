package org.optimizationBenchmarking.utils.text.charset;

import java.io.Serializable;

/** the serial version of quotation marks */
final class _QuotationMarksSerial implements Serializable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the instance */
  static _QuotationMarksSerial INSTANCE = new _QuotationMarksSerial();

  /** create */
  _QuotationMarksSerial() {
    super();
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
