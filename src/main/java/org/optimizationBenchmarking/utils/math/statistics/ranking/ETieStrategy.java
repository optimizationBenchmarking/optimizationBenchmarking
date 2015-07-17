package org.optimizationBenchmarking.utils.math.statistics.ranking;

/** The strategy for handling ties in ranking. */
public enum ETieStrategy {

  /**
   * All tied elements get the same rank, which is the minimum of the
   * applicable ranks.
   */
  MINIMUM,

  /**
   * All tied elements get the same rank, which is the maximum of the
   * applicable ranks.
   */
  MAXIMUM,

  /**
   * All tied elements get the same rank, which is the average of the
   * applicable rank.
   */
  AVERAGE;

  /** the default strategy for handling ties */
  public static final ETieStrategy DEFAULT = AVERAGE;
}
