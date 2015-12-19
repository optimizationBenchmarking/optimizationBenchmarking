package org.optimizationBenchmarking.utils.ml.fitting.models;

/** the internal value checker */
abstract class _Checker {

  /** create the value checker */
  _Checker() {
    super();
  }

  /**
   * check a given value
   *
   * @param d
   *          the value to check
   * @return {@code true} if it is OK, {@code false} otherwise
   */
  abstract boolean _check(final double d);
}
