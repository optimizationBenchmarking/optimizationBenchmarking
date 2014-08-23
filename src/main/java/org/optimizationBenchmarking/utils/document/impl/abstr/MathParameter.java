package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A mathematics function parameter */
public class MathParameter extends BasicMath {

  /** the index */
  private final int m_index;

  /**
   * Create a mathematics output.
   * 
   * @param owner
   *          the owning FSM
   * @param index
   *          the index of the parameter
   */
  public MathParameter(final MathFunction owner, final int index) {
    super(owner, null);

    if (index <= 0) {
      throw new IllegalArgumentException(//
          "A parameter index must be greater than 0, but is " //$NON-NLS-1$
              + index);
    }
    this.m_index = index;
  }

  /**
   * Get the ({@code 1}-based) index of this math parameter
   * 
   * @return the ({@code 1}-based) index of this math parameter
   */
  public final int getIndex() {
    return this.m_index;
  }

}
