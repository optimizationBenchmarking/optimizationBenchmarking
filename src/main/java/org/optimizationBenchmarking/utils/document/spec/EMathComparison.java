package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** Comparison operators as used by the compare operation */
public enum EMathComparison {

  /** very much less */
  VERY_MUCH_LESS(0x22d8),
  /** much less */
  MUCH_LESS(0x226a),
  /** less */
  LESS(0x3c),
  /** less or equal */
  LESS_OR_EQUAL(0x2264),
  /** greater or equal */
  GREATER_OR_EQUAL(0x2265),
  /** greater */
  GREATER(0x3e),
  /** much greater */
  MUCH_GREATER(0x226b),
  /** very much greater */
  VERY_MUCH_GREATER(0x22d9),
  /** equal */
  EQUAL(0x3d),
  /** equivalent */
  EQUIVALENT(0x2261),
  /** approximately */
  APPROXIMATELY(0x2248),
  /** approximately equal */
  APPROXIMATELY_EQUAL(0x224a),
  /** proportional to */
  PROPROTIONAL_TO(0x221d),
  /** not equal */
  NOT_EQUAL(0x2260),
  /** not equivalent */
  NOT_EQUIVALENT(0x2262),
  /** not approximately */
  NOT_APPROXIMATELY(0x2249),
  /** not approximately equal */
  NOT_APPROXIMATELY_EQUAL(0x2247),
  /** element of */
  ELEMENT_OF(0x2208),
  /** not element of */
  NOT_ELEMENT_OF(0x2209),
  /** subset of */
  SUBSET_OF(0x2282),
  /** not subset of */
  NOT_SUBSET_OF(0x2284),
  /** subset of or equal */
  SUBSET_OF_OR_EQUAL(0x2286),
  /** not subset of or equal */
  NOT_SUBSET_OF_OR_EQUAL(0x2288),
  /** defined as */
  DEFINED_AS(0x225c),
  /** approximated as */
  APPROXIMATED_AS(0x2259),
  /** precedes */
  PRECEDES(0x227a),
  /** not precedes */
  NOT_PRECEDES(0x2280),
  /** precedes or equal */
  PRECEDES_OR_EQUAL(0x227c),
  /** not precedes or equal */
  NOT_PRECEDES_OR_EQUAL(0x22e0),
  /** succeeds */
  SUCCEEDS(0x227b),
  /** not succeeds */
  NOT_SUCCEEDS(0x2281),
  /** succeeds or equal */
  SUCCEEDS_OR_EQUAL(0x227d),
  /** not succeeds */
  NOT_SUCCEEDS_OR_EQUAL(0x22e1),
  /** similar */
  SIMILAR(0x223c),
  /** not similar */
  NOT_SIMILAR(0x2241);

  /** all instances of the math comparison */
  public static final ArraySetView<EMathComparison> INSTANCES = new ArraySetView<>(
      EMathComparison.values());

  /** the operator */
  private final char m_operatorChar;

  /** the string */
  private transient String m_string;

  /**
   * create the comparison
   *
   * @param operatorChar
   *          the operator char
   */
  EMathComparison(final int operatorChar) {
    this.m_operatorChar = ((char) operatorChar);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    if (this.m_string == null) {
      this.m_string = Character.toString(this.m_operatorChar);
    }
    return this.m_string;
  }

  /**
   * Get the unicode character representing the operator
   *
   * @return the unicode character representing the operator
   */
  public final char getOperatorChar() {
    return this.m_operatorChar;
  }

  /**
   * Translate an instance of
   * {@link org.optimizationBenchmarking.utils.comparison.EComparison} to
   * an instance of {@link EMathComparison}.
   *
   * @param compare
   *          the
   *          {@link org.optimizationBenchmarking.utils.comparison.EComparison
   *          comparison} instance
   * @return the {@link EMathComparison} instance
   */
  public static final EMathComparison fromEComparison(
      final EComparison compare) {
    switch (compare) {
      case LESS: {
        return LESS;
      }
      case LESS_OR_EQUAL: {
        return LESS_OR_EQUAL;
      }
      case EQUAL: {
        return EQUAL;
      }
      case GREATER_OR_EQUAL: {
        return GREATER_OR_EQUAL;
      }
      case GREATER: {
        return GREATER;
      }
      case NOT_EQUAL: {
        return NOT_EQUAL;
      }
      default: {
        throw new IllegalArgumentException(compare + //
            " does not match to any EMathComparison instance."); //$NON-NLS-1$
      }
    }
  }
}
