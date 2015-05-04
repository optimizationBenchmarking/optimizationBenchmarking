package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Label;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the label used in LaTeX documents */
final class _LaTeXLabel extends Label {
  /** the ref command */
  private static final char[] REF = { '{', '\\', 'r', 'e', 'f', '{' };

  /**
   * create an LaTeX label
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
  protected _LaTeXLabel(final LaTeXDocument owner, final ELabelType type,
      final String mark, final String refText) {
    super(owner, type, mark, refText);
  }

  /** {@inheritDoc} */
  @Override
  protected void doToSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput text, final ITextOutput raw) {
    String rt;

    if (isFirstInSequence) {
      rt = this.getType().getName();
      text.append(rt);
      if (!isLastInSequence) {
        raw.append('s');
      }
      raw.append('~');
    }

    raw.append(_LaTeXLabel.REF);
    raw.append(this.getLabelMark());
    raw.append('}');
    raw.append('}');
  }
}
