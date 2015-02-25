package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** Comparison operators as used by the compare operation */
public enum EMathComparison {

  /** very much less */
  VERY_MUCH_LESS,
  /** much less */
  MUCH_LESS,
  /** less */
  LESS,
  /** less or equal */
  LESS_OR_EQUAL,
  /** greater or equal */
  GREATER_OR_EQUAL,
  /** greater */
  GREATER,
  /** much greater */
  MUCH_GREATER,
  /** very much greater */
  VERY_MUCH_GREATER,
  /** equal */
  EQUAL,
  /** equivalent */
  EQUIVALENT,
  /** approximately */
  APPROXIMATELY,
  /** approximately equal */
  APPROXIMATELY_EQUAL,
  /** proportional to */
  PROPROTIONAL_TO,
  /** not equal */
  NOT_EQUAL,
  /** not equivalent */
  NOT_EQUIVALENT,
  /** not approximately */
  NOT_APPROXIMATELY,
  /** not approximately equal */
  NOT_APPROXIMATELY_EQUAL,
  /** not proportional to */
  NOT_PROPROTIONAL_TO,
  /** element of */
  ELEMENT_OF,
  /** not element of */
  NOT_ELEMENT_OF,
  /** subset of */
  SUBSET_OF,
  /** not subset of */
  NOT_SUBSET_OF,
  /** subset of or equal */
  SUBSET_OF_OR_EQUAL,
  /** not subset of or equal */
  NOT_SUBSET_OF_OR_EQUAL,
  /** defined as */
  DEFINED_AS,
  /** approximated as */
  APPROXIMATED_AS,
  /** precedes */
  PRECEDES,
  /** not precedes */
  NOT_PRECEDES,
  /** precedes or equal */
  PRECEDES_OR_EQUAL,
  /** not precedes or equal */
  NOT_PRECEDES_OR_EQUAL,
  /** succeeds */
  SUCCEEDS,
  /** not succeeds */
  NOT_SUCCEEDS,
  /** succeeds or equal */
  SUCCEEDS_OR_EQUAL,
  /** not succeeds */
  NOT_SUCCEEDS_OR_EQUAL,
  /** similar */
  SIMILAR,
  /** not similar */
  NOT_SIMILAR;

  /** all instances of the math comparison */
  public static final ArraySetView<EMathComparison> INSTANCES = new ArraySetView<>(
      EMathComparison.values());

}
