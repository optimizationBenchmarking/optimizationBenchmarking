package org.optimizationBenchmarking.experimentation.evaluation.data;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * The direction into which the values of this dimension change over time.
 */
public enum EDimensionDirection {

  /**
   * the values of this dimension increase over time, but also may stay
   * constant
   */
  INCREASING(true, false, EComparison.GREATER_OR_EQUAL),

  /**
   * the values of this dimension will always increase over time and never
   * stay constant
   */
  INCREASING_STRICTLY(true, true, EComparison.GREATER),

  /**
   * the values of this dimension decrease over time, but also may stay
   * constant
   */
  DECREASING(false, false, EComparison.LESS_OR_EQUAL),

  /**
   * the values of this dimension decrease over time and never stay
   * constant
   */
  DECREASING_STRICTLY(false, true, EComparison.LESS);

  /** the dimension direction instances */
  public static final ArraySetView<EDimensionDirection> INSTANCES = new ArraySetView<>(
      EDimensionDirection.values());

  /** is the direction increasing? */
  private final transient boolean m_isIncreasing;

  /** is the direction strict? */
  private final transient boolean m_isStrict;

  /** the comparison */
  private final transient EComparison m_comp;

  /**
   * Create
   * 
   * @param isIncreasing
   *          is the direction increasing?
   * @param isStrict
   *          is the direction strict?
   * @param comp
   *          the comparison
   */
  EDimensionDirection(final boolean isIncreasing, final boolean isStrict,
      final EComparison comp) {
    this.m_isIncreasing = isIncreasing;
    this.m_isStrict = isStrict;
    this.m_comp = comp;
  }

  /**
   * Is this dimension increasing or decreasing?
   * 
   * @return {@code true} if the values of this dimension are increasing,
   *         {@code false} if they are decreasing
   */
  public final boolean isIncreasing() {
    return this.m_isIncreasing;
  }

  /**
   * Is the increasing/decreasing of this dimension strict?
   * 
   * @return {@code true} if the dimension must always change over time,
   *         {@code false} if it may remain constant
   */
  public final boolean isStrict() {
    return this.m_isStrict;
  }

  /**
   * Get the comparison representing this direction that will be
   * {@code true} when comparing the next step in this dimension with the
   * previous one, i.e.,
   * <code>{@link org.optimizationBenchmarking.utils.comparison.EComparison}.{@link org.optimizationBenchmarking.utils.comparison.EComparison#compare(boolean, boolean) compare(next, previous)}=true</code>
   * 
   * @return the comparison representing this direction
   */
  public final EComparison getComparison() {
    return this.m_comp;
  }
}
