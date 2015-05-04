package org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/** A modifiable number class. */
abstract class _Number extends BasicNumber {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _Number() {
    super();
  }

  /**
   * Set the {@code long} value
   *
   * @param value
   *          the {@code long} value
   */
  void _setLongValue(final long value) {
    throw new IllegalStateException();
  }

  /**
   * Set the {@code double} value
   *
   * @param value
   *          the {@code double} value
   */
  void _setDoubleValue(final double value) {
    throw new IllegalStateException();
  }
}
