package org.optimizationBenchmarking.utils.math.statistics.ranking;

/**
 * How should {@link java.lang.Double#NaN} values be handled during
 * ranking?
 */
public enum ENaNStrategy {

  /**
   * {@link java.lang.Double#NaN} values are equal to each other but
   * smaller than anything else, including
   * {@link java.lang.Double#NEGATIVE_INFINITY}.
   */
  MINIMAL,

  /**
   * {@link java.lang.Double#NaN} values are equal to
   * {@link java.lang.Double#NEGATIVE_INFINITY}.
   */
  NEGATIVE_INFINITY,

  /**
   * {@link java.lang.Double#NaN} values are equal
   * {@link java.lang.Double#POSITIVE_INFINITY}.
   */
  POSITIVE_INFINITY,

  /**
   * {@link java.lang.Double#NaN} values are equal to each other but larger
   * than anything else, including
   * {@link java.lang.Double#POSITIVE_INFINITY}.
   */
  MAXIMAL,

  /**
   * {@link java.lang.Double#NaN} values will lead to {@lang
   * java.lang.IllegalArgumentException}s.
   */
  ERROR,

  /**
   * {@link java.lang.Double#NaN} values will get
   * {@link java.lang.Double#NaN} as rank.
   */
  NAN;
  
  /** the default strategy for handling NaN */
  public static final ENaNStrategy DEFAULT = ERROR;

}
