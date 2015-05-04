package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.text.charset.QuotationMarks;

/** A text class for quoted text */
public class InQuotes extends PlainText {

  /** the marks */
  private static final QuotationMarks[] MARKS = {
      QuotationMarks.DEFAULT_DOUBLE, QuotationMarks.DEFAULT_SINGLE,
      QuotationMarks.ANGLE_DOUBLE, QuotationMarks.ANGLE_SINGLE, };

  /** the marks */
  private final int m_marks;

  /**
   * Create a text.
   *
   * @param owner
   *          the owning FSM
   */
  @SuppressWarnings("resource")
  protected InQuotes(final PlainText owner) {
    super(owner);

    DocumentElement d;

    for (d = owner; d != null; d = d._owner()) {
      if (d instanceof InQuotes) {
        this.m_marks = (((InQuotes) d).m_marks + 1);
        return;
      }
    }

    this.m_marks = 0;
  }

  /** {@inheritDoc} */
  @Override
  protected PlainText getOwner() {
    return ((PlainText) (super.getOwner()));
  }

  /**
   * Get the marks index
   *
   * @return the marks index
   */
  protected int getMarksIndex() {
    return this.m_marks;
  }

  /**
   * Get the quotation marks
   *
   * @return the quotation marks
   */
  public QuotationMarks getMarks() {
    return InQuotes.MARKS[this.m_marks % InQuotes.MARKS.length];
  }

  /**
   * Write the starting mark
   */
  protected void writeBegin() {
    this.m_encoded.append(this.getMarks().getBeginChar());
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onOpen() {
    super.onOpen();
    this.writeBegin();
  }

  /**
   * Write the ending mark
   */
  protected void writeEnd() {
    this.m_encoded.append(this.getMarks().getEndChar());
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    this.writeEnd();
    super.onClose();
  }
}
