package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.comparison.EComparison;

/** a mathematics compare operator */
public class MathCompare extends MathFunction {

  /** the comparison */
  private final EComparison m_comp;

  /**
   * Create an compare function
   * 
   * @param owner
   *          the owning FSM
   * @param comp
   *          the comparison to use
   */
  protected MathCompare(final BasicMath owner, final EComparison comp) {
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
  public final EComparison getComparison() {
    return this.m_comp;
  }

}
