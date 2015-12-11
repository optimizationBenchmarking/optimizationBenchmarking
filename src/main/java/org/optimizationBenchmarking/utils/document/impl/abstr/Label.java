package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a label implementation */
public class Label extends Textable implements ILabel, ISequenceable {

  /** the label type */
  final ELabelType m_type;

  /** the label mark */
  private final String m_mark;

  /** the document */
  final Document m_owner;

  /** the reference text */
  String m_refText;

  /**
   * create a label
   *
   * @param owner
   *          the owner
   * @param type
   *          the label type
   * @param mark
   *          the label mark
   * @param refText
   *          the reference text
   */
  protected Label(final Document owner, final ELabelType type,
      final String mark, final String refText) {
    super();

    if (owner == null) {
      throw new IllegalArgumentException(
          "Label owning document must not be null."); //$NON-NLS-1$
    }
    if (type == null) {
      throw new IllegalArgumentException("Label type must not be null."); //$NON-NLS-1$
    }
    if (mark == null) {
      throw new IllegalArgumentException("Label mark must not be null."); //$NON-NLS-1$
    }

    this.m_type = type;
    this.m_mark = mark;
    this.m_refText = refText;
    this.m_owner = owner;
  }

  /**
   * Get the label type
   *
   * @return the label type
   */
  public final ELabelType getType() {
    return this.m_type;
  }

  /**
   * Get the label mark
   *
   * @return the label mark
   */
  public final String getLabelMark() {
    return this.m_mark;
  }

  /**
   * Get the reference text
   *
   * @return the reference text
   */
  public final String getReferenceText() {
    return this.m_refText;
  }

  /**
   * write this label to a sequence
   *
   * @param isFirstInSequence
   *          is this the first sequence item?
   * @param isLastInSequence
   *          is this the last sequence item?
   * @param textCase
   *          the text case
   * @param text
   *          the text output
   * @param raw
   *          the underlying raw text destination
   */
  protected void doToSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput text, final ITextOutput raw) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {
    if (textOut instanceof ComplexText) {
      this.doToSequence(isFirstInSequence, isLastInSequence, textCase,
          textOut, ((ComplexText) textOut)._raw());
    } else {
      this.doToSequence(isFirstInSequence, isLastInSequence, textCase,
          textOut, textOut);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    if (this.m_refText != null) {
      textOut.append(this.m_type.getName());
      textOut.appendNonBreakingSpace();
      textOut.append(this.m_refText);
    } else {
      textOut.append("[forward"); //$NON-NLS-1$
      textOut.appendNonBreakingSpace();
      textOut.append(this.m_type.getName());
      if (this.m_mark != null) {
        textOut.append('#');
        textOut.append(this.m_mark);
      }
      textOut.append(']');
    }
  }
}
