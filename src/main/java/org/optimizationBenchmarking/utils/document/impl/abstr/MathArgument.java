package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A mathematics function argument */
public class MathArgument extends BasicMath {

  /** the index */
  private final int m_index;

  /**
   * Create a mathematics output.
   * 
   * @param owner
   *          the owning FSM
   */
  public MathArgument(final MathFunction owner) {
    super(owner);

    this.m_index = owner.m_argIndex;
    if (this.m_index <= 0) {
      throw new IllegalArgumentException(//
          "A argument index must be greater than 0, but is " //$NON-NLS-1$
              + this.m_index);
    }
  }

  /**
   * Get the ({@code 1}-based) index of this math argument
   * 
   * @return the ({@code 1}-based) index of this math argument
   */
  public final int getIndex() {
    return this.m_index;
  }

}
