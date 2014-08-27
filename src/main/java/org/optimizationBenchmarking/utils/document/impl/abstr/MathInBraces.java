package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.text.charset.Braces;

/**
 * A math class for braces text. Different from the
 * {@link org.optimizationBenchmarking.utils.document.impl.abstr.InBraces
 * text braces}, this class does not print the braces &nbsp; you need to
 * implement that by yourself.
 */
public final class MathInBraces extends BasicMath {

  /** the marks */
  private static final Braces[] BRACES = { Braces.PARENTHESES,
      Braces.BRACKETS };

  /** the marks */
  final int m_braces;

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   * @param marks
   *          the marks
   */
  protected MathInBraces(final BasicMath owner, final int marks) {
    super(owner, null);
    this.m_braces = marks;
  }

  /** {@inheritDoc} */
  @Override
  protected BasicMath getOwner() {
    return ((BasicMath) (super.getOwner()));
  }

  /**
   * Get the brace marks
   * 
   * @return the brace marks
   */
  public final Braces getBraces() {
    return MathInBraces.BRACES[this.m_braces];
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onOpen() {
    super.onOpen();
    this.m_encoded.append(this.getBraces().getBeginChar());
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    this.m_encoded.append(this.getBraces().getEndChar());
    super.onClose();
  }
}
