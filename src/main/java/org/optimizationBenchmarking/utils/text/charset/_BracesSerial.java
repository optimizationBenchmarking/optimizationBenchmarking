package org.optimizationBenchmarking.utils.text.charset;

import java.io.Serializable;

/** the serial version of braces */
final class _BracesSerial implements Serializable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the instance */
  static _BracesSerial INSTANCE = new _BracesSerial();

  /** create */
  _BracesSerial() {
    super();
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
