package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.text.charset.Braces;

/** A text class for braces text in codes */
public final class CodeInBraces extends Text {

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   */
  protected CodeInBraces(final CodeBody owner) {
    super(owner, null);
  }

  /** {@inheritDoc} */
  @Override
  protected CodeBody getOwner() {
    return ((CodeBody) (super.getOwner()));
  }

  /**
   * Get the brace marks
   * 
   * @return the brace marks
   */
  public final Braces getBraces() {
    return Braces.PARENTHESES;
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
