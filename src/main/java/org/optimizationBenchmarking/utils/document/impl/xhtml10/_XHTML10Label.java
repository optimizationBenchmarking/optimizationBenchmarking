package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Label;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the label used in XHTML 1.0 documents */
final class _XHTML10Label extends Label {

  /** the star entity */
  private static final char[] STAR = { '&', '#', '9', '7', '3', '3', ';' };

  /**
   * create an xhtml 1.0 label
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
  protected _XHTML10Label(final _XHTML10Document owner,
      final ELabelType type, final String mark, final String refText) {
    super(owner, type, mark, refText);
  }

  /** {@inheritDoc} */
  @Override
  protected void doToSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput text, final ITextOutput raw) {
    final int l;
    String rt;

    if (isFirstInSequence) {
      rt = this.getType().getName();
      text.append(rt);
      if (!isLastInSequence) {
        raw.append('s');
      }
      raw.append(XHTML10Driver.NBSP);
    }

    raw.append(XHTML10Driver.A_REF);
    raw.append(this.getLabelMark());
    raw.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);

    rt = this.getReferenceText();
    if (rt != null) {
      l = (rt.length() - 1);
      if ((l >= 0) && (rt.charAt(l) == '.')) {
        raw.append(rt, 0, l);
      } else {
        raw.append(rt);
      }
    } else {
      raw.append(_XHTML10Label.STAR);
    }

    raw.append(XHTML10Driver.A_REF_END);
  }
}
