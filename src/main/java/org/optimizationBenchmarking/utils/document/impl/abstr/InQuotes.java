package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.charset.QuotationMarks;

/** A text class for quoted text */
public final class InQuotes extends Text {

  /** the marks */
  private static final QuotationMarks[] MARKS = {
      QuotationMarks.DEFAULT_DOUBLE, QuotationMarks.DEFAULT_SINGLE,
      QuotationMarks.ANGLE_DOUBLE, QuotationMarks.ANGLE_SINGLE, };

  /** the marks */
  final int m_marks;

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   * @param marks
   *          the marks
   */
  protected InQuotes(final HierarchicalFSM owner, final int marks) {
    super(owner, null);
    this.m_marks = marks;
  }

  /**
   * Get the quotation marks
   * 
   * @return the quotation marks
   */
  public final QuotationMarks getMarks() {
    return InQuotes.MARKS[this.m_marks];
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onOpen() {
    super.onOpen();
    this.m_encoded.append(this.getMarks().getBeginChar());
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    this.m_encoded.append(this.getMarks().getEndChar());
    super.onClose();
  }
}
