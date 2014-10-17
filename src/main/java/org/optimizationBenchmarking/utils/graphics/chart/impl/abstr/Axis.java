package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

/**
 * The class for all axes
 */
public final class Axis extends _TitledElement {

  /** the minimum */
  private final double m_min;
  /** the maximum */
  private final double m_max;

  /**
   * create the axis
   * 
   * @param title
   *          the title
   * @param min
   *          the minimum value
   * @param max
   *          the maximum value
   */
  Axis(final String title, final double min, final double max) {
    super(title);

    _AxisBuilder._assertMin(min);
    _AxisBuilder._assertMax(max);
    if (max <= min) {
      throw new IllegalArgumentException(//
          ((("Axis range [" + min) //$NON-NLS-1$
              + ',') + max)
              + "] is invalid."); //$NON-NLS-1$
    }
    this.m_min = min;
    this.m_max = max;
  }

  /**
   * Get the minimum value of this axis
   * 
   * @return the minimum value of this axis
   */
  public final double getMinimum() {
    return this.m_min;
  }

  /**
   * Get the maximum value of this axis
   * 
   * @return the maximum value of this axis
   */
  public final double getMaximum() {
    return this.m_max;
  }
}
