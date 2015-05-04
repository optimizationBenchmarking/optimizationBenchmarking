package org.optimizationBenchmarking.utils.text.charset;

import java.io.Serializable;

/** the serial version of characters */
final class _CharactersSerial implements Serializable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the instance */
  static _CharactersSerial INSTANCE = new _CharactersSerial();

  /** create */
  _CharactersSerial() {
    super();
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  final Object readResolve() {
    return Characters.CHARACTERS;
  }
}
