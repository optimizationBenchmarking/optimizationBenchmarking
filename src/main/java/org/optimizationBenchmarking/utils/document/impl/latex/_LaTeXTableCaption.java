package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableCaption;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the caption of a table in a LaTeX document */
final class _LaTeXTableCaption extends TableCaption {
  /** begin small */
  private static final char[] SMALL_BEGIN = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 's', 'm', 'a', 'l', 'l', '}' };

  /**
   * Create the caption of a table
   *
   * @param owner
   *          the owning table
   */
  _LaTeXTableCaption(final _LaTeXTable owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();

    out.append(LaTeXDriver.CAPTION_BEGIN);
    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    LaTeXDriver._endLine(out);
    LaTeXDriver._label(this.getOwner().getLabel(), out);
    LaTeXDriver._endCommandLine(out);
    out.append(LaTeXDriver.CENTERING);
    LaTeXDriver._endLine(out);
    out.append(_LaTeXTableCaption.SMALL_BEGIN);
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
