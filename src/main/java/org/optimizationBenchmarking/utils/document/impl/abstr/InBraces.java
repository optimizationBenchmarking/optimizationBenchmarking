package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.text.charset.Braces;

/** A text class for braces text */
public class InBraces extends PlainText {

  /** the marks */
  private static final Braces[] BRACES = { Braces.PARENTHESES,
      Braces.BRACKETS, Braces.CURLY_BRACES, Braces.ANGLE, Braces.CHEVRON };

  /** the marks */
  private final int m_braces;

  /**
   * Create a text.
   *
   * @param owner
   *          the owning FSM
   */
  @SuppressWarnings("resource")
  protected InBraces(final PlainText owner) {
    super(owner);

    DocumentElement d;

    for (d = owner; d != null; d = d._owner()) {
      if (d instanceof InBraces) {
        this.m_braces = (((InBraces) d).m_braces + 1);
        return;
      }
    }

    this.m_braces = 0;
  }

  /** {@inheritDoc} */
  @Override
  protected PlainText getOwner() {
    return ((PlainText) (super.getOwner()));
  }

  /**
   * Get the brace index
   *
   * @return the brace index
   */
  protected final int getBraceIndex() {
    return this.m_braces;
  }

  /**
   * Get the brace marks
   *
   * @return the brace marks
   */
  public Braces getBraces() {
    return InBraces.BRACES[this.m_braces % InBraces.BRACES.length];
  }

  /**
   * Write the starting brace
   */
  protected void writeBegin() {
    this.m_encoded.append(this.getBraces().getBeginChar());
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onOpen() {
    super.onOpen();
    this.writeBegin();
  }

  /**
   * Write the ending brace
   */
  protected void writeEnd() {
    this.m_encoded.append(this.getBraces().getEndChar());
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    this.writeEnd();
    super.onClose();
  }
}
