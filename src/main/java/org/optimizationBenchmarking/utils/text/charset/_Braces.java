package org.optimizationBenchmarking.utils.text.charset;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * the quotation marks set
 */
final class _Braces extends ArraySetView<Braces> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create
   *
   * @param data
   *          the data
   */
  _Braces(final Braces[] data) {
    super(data);
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  final Object writeReplace() {
    return _BracesSerial.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  final Object readResolve() {
    return Braces.BRACES;
  }
}
