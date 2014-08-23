package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.text.charset.Braces;

/** A text class for braces text */
public final class InBraces extends Text {

  /** the marks */
  private static final Braces[] BRACES = { Braces.PARENTHESES,
      Braces.BRACKETS, Braces.CURLY_BRACES, Braces.ANGLE, Braces.CHEVRON };

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
  protected InBraces(final Text owner, final int marks) {
    super(owner, null);
    this.m_braces = marks;
  }

  /** {@inheritDoc} */
  @Override
  protected Text getOwner() {
    return ((Text) (super.getOwner()));
  }

  /**
   * Get the brace marks
   * 
   * @return the brace marks
   */
  public final Braces getBraces() {
    return InBraces.BRACES[this.m_braces];
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
