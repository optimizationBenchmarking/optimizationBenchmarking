package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A container for a sequence of reference items which all have the same
 * {@link #getType() type text}.
 */
public class ReferenceRun extends ArrayListView<Label> implements
    ISequenceable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the type name */
  private final String m_type;

  /** the sequence mode */
  private final ESequenceMode m_seq;

  /**
   * Create the reference item
   * 
   * @param type
   *          the type
   * @param seq
   *          the sequence mode
   * @param data
   *          the labels
   */
  public ReferenceRun(final String type, final ESequenceMode seq,
      final Label[] data) {
    super(data);
    this.m_type = type;
    this.m_seq = seq;
  }

  /**
   * Get the reference type
   * 
   * @return the reference type
   */
  public final String getType() {
    return this.m_type;
  }

  /**
   * Get the sequence mode
   * 
   * @return the sequence mode
   */
  public final ESequenceMode getSequenceMode() {
    return this.m_seq;
  }

  /** {@inheritDoc} */
  @Override
  public void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {
    this.m_seq.appendSequence(textCase, this, true, textOut);
  }
}
