package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters;

/** An enumeration defining a grouping mode */
public enum EGroupMode {

  /** each distinct value is a separate group */
  DISTINCT,

  /** group (numerical) values by scale of 2 */
  SCALE_OF_2,

  /** group (numerical) values by scale of 10 */
  SCALE_OF_10,

  /** group (numerical) values by any reasonable scale */
  ANY_SCALE,

  /** group (numerical) values by multiples of a power of 10 */
  MULTIPLE_OF_POWER_OF_10,

  /**
   * group (numerical) values by any reasonable multiple (try to choose
   * best)
   */
  ANY_MULTIPLE,

  /**
   * group (numerical) values by any reasonable grouping mode (try to
   * choose best)
   */
  ANY;
}
