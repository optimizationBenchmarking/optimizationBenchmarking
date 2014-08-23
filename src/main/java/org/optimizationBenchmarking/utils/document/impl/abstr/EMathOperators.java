package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.comparison.EComparison;

/** An enumeration with the supported mathematics operators */
public enum EMathOperators {

  /** {@code <} */
  LESS(2, 2),
  /** {@code <=} */
  LESS_OR_EQUAL(2, 2),
  /** {@code ==} */
  EQUAL(2, 2),
  /** {@code >=} */
  GREATER_OR_EQUAL(2, 2),
  /** {@code >} */
  GREATER(2, 2),
  /** {@code !=} */
  NOT_EQUAL(2, 2),

  /** {@code +} */
  ADD(2, 100),

  /** {@code -} */
  SUB(2, 100),

  /** for {@code *} */
  MUL(2, 100),

  /** for {@code /} */
  DIV(2, 2),

  /** {@code mod} */
  MOD(2, 2),

  /** {@code log} */
  LOG(2, 2),

  /** {@code ln} */
  LN(1, 1),

  /** {@code ld} */
  LD(1, 1),

  /** {@code lg} */
  LG(1, 1),

  /** {@code a^b}) */
  POW(2, 2),

  /** {@code a^(1/b)} */
  ROOT(2, 2),

  /** {@code sqrt(a)} */
  SQRT(1, 1);

  /** the minimum number of parameters */
  final int m_minParamCount;
  /** the maximum number of parameters */
  final int m_maxParamCount;

  /**
   * create the math operator
   * 
   * @param minParamCount
   *          the minimum number of parameters
   * @param maxParamCount
   *          the maximum number of parameters
   */
  EMathOperators(final int minParamCount, final int maxParamCount) {
    if (minParamCount <= 0) {
      throw new IllegalArgumentException(//
          "There must be at least one parameter, but the minimum number is set to " //$NON-NLS-1$
              + minParamCount);
    }
    if (maxParamCount < minParamCount) {
      throw new IllegalArgumentException(//
          "The maximum parameter count must be greater or equal to the minimum parameter counter, but is " //$NON-NLS-1$
              + maxParamCount + " vs. " + minParamCount);//$NON-NLS-1$
    }
    this.m_minParamCount = minParamCount;
    this.m_maxParamCount = maxParamCount;
  }

  /**
   * Get the minimum parameter count
   * 
   * @return the minimum parameter count
   */
  public final int getMinimumParamCount() {
    return this.m_minParamCount;
  }

  /**
   * Get the minimum parameter count
   * 
   * @return the minimum parameter count
   */
  public final int getMaximumParameterCount() {
    return this.m_maxParamCount;
  }

  /**
   * Translate a comparison operator to a math operation
   * 
   * @param comp
   *          the comparison operator
   * @return the corresponding math operation
   */
  static final EMathOperators _comp2MathOp(final EComparison comp) {
    switch (comp) {

      case LESS: {
        return LESS;
      }

      case LESS_OR_EQUAL:
      case LESS_OR_SAME: {
        return LESS_OR_EQUAL;
      }

      case SAME:
      case EQUAL: {
        return EQUAL;
      }

      case GREATER_OR_EQUAL:
      case GREATER_OR_SAME: {
        return GREATER_OR_EQUAL;
      }

      case GREATER: {
        return GREATER;
      }

      case NOT_EQUAL:
      case NOT_SAME: {
        return NOT_EQUAL;
      }

      default: {
        throw new IllegalArgumentException(//
            "Comparison operator '" + comp //$NON-NLS-1$
                + "' is invalid."); //$NON-NLS-1$
      }
    }
  }
}
