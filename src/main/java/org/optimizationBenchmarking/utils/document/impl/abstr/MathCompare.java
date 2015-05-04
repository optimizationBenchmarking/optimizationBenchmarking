package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.EMathComparison;

/** a mathematics compare operator */
public class MathCompare extends MathFunction {

  /** the comparison */
  private final EMathComparison m_comp;

  /**
   * Create an compare function
   *
   * @param owner
   *          the owning FSM
   * @param comp
   *          the comparison to use
   */
  protected MathCompare(final BasicMath owner, final EMathComparison comp) {
    super(owner, 2, 2);
    if (comp == null) {
      throw new IllegalArgumentException(//
          "Comparator must not be null."); //$NON-NLS-1$
    }
    this.m_comp = comp;
  }

  /**
   * Get the comparison
   *
   * @return the comparison
   */
  public final EMathComparison getComparison() {
    return this.m_comp;
  }

}
