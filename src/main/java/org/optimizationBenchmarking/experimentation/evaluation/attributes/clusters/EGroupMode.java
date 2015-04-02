package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters;

/** An enumeration defining a grouping mode */
public enum EGroupMode {

  /** Each distinct value is a separate group. */
  DISTINCT,

  /**
   * Group (numerical) values by scale of 2, e.g., in [0.25, 0.5), [0.5,
   * 1), [1, 2), [2, 4), ... Throws an
   * {@link java.lang.IllegalArgumentException} if the values are not
   * numerical.
   */
  SCALE_OF_2,

  /**
   * Group (numerical) values by scale of 10, e.g., in groups [0.1, 1), [1,
   * 10), [10, 100)... Throws an {@link java.lang.IllegalArgumentException}
   * if the values are not numerical.
   */
  SCALE_OF_10,

  /**
   * Group (numerical) values by any reasonable scale. This includes
   * {@link #SCALE_OF_2} and {@link #SCALE_OF_10}. Try to select a
   * reasonable scale leading to balanced groupings. Throws an
   * {@link java.lang.IllegalArgumentException} if the values are not
   * numerical.
   */
  ANY_SCALE,

  /**
   * Group (numerical) values by multiples of a power of 10, e.g., [0,10),
   * [10, 20), [20, 30)... or [0,10000), [10000,20000), [20000,30000), etc.
   * Try to select a reasonable multiple leading to balanced groupings.
   * Throws an {@link java.lang.IllegalArgumentException} if the values are
   * not numerical.
   */
  MULTIPLE_OF_POWER_OF_10,

  /**
   * Group (numerical) values by any reasonable multiple (try to choose
   * best). This includes {@link #MULTIPLE_OF_POWER_OF_10}. Try to select a
   * reasonable multiple leading to balanced groupings. Throws an
   * {@link java.lang.IllegalArgumentException} if the values are not
   * numerical.
   */
  ANY_MULTIPLE,

  /**
   * Try to automatically find a reasonable way to group property values
   * numerically. Throws an {@link java.lang.IllegalArgumentException} if
   * the values are not numerical.
   */
  ANY_NUMERICAL,

  /**
   * Group textual representations of values according to their starting
   * letter.
   */
  PREFIX_LETTER, //

  /**
   * Group textual representations of values according to some
   * reasonably-length starting letter group. This includes the possible
   * choice of {@link #PREFIX_LETTER}.
   */
  PREFIX_LETTER_GROUP, //

  /**
   * Group textual representations of values according to a reasonable
   * start word.
   */
  PREFIX_WORD, //

  /**
   * Test any textual prefix and check which leads to the most balanced
   * group. This includes {@link #PREFIX_LETTER},
   * {@link #PREFIX_LETTER_GROUP}, and {@link #PREFIX_WORD}.
   */
  ANY_PREFIX,

  /**
   * Try to automatically find a reasonable way to group values. That is,
   * try to find a grouping which is well-balanced and fits to the user
   * preferences. This includes testing all other allowed groupings, i.e.,
   * {@link #ANY_PREFIX} and {@link #ANY_NUMERICAL}.
   */
  ANY;
}
