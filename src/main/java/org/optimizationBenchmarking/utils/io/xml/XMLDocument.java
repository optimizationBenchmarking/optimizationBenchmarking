package org.optimizationBenchmarking.utils.io.xml;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** The */
public final class XMLDocument extends XMLBase {

  /** a flag indicating that there is an element */
  private static final int FLAG_HAS_ELEMENT = 1;

  /** the xml declaration */
  private static final char[] XML = { '<', '?', 'x', 'm', 'l', ' ', 'v',
      'e', 'r', 's', 'i', 'o', 'n', '=', '"', '1', '.', '0', '"', };

  /** the encoding */
  private static final char[] ENCODING = { 'e', 'n', 'c', 'o', 'd', 'i',
      'n', 'g', '=', '"' };

  /** the closing sequence */
  private static final char[] CLOSE = { '"', '?', '>' };

  /**
   * Create the hierarchical XML writer
   *
   * @param owner
   *          the owning hierarchical fsm
   * @param out
   *          the {@link java.lang.Appendable}
   */
  public XMLDocument(final HierarchicalFSM owner, final Appendable out) {
    super(owner, out);
    this.open();
  }

  /**
   * Create the hierarchical XML writer
   *
   * @param out
   *          the {@link java.lang.Appendable}
   */
  public XMLDocument(final Appendable out) {
    this(null, out);
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    if (flagValue == XMLDocument.FLAG_HAS_ELEMENT) {
      append.append("hasElement"); //$NON-NLS-1$
    } else {
      super.fsmFlagsAppendName(flagValue, flagIndex, append);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onOpen() {
    final StreamEncoding<?, ?> se;
    final ITextOutput ap;

    super.onOpen();
    this.fsmFlagsAssertFalse(XMLDocument.FLAG_HAS_ELEMENT);

    ap = this.getTextOutput();
    ap.append(XMLDocument.XML);
    se = this.getStreamEncoding();
    if ((se != null) && (se != StreamEncoding.UNKNOWN)
        && (se != StreamEncoding.TEXT)
        && (Appendable.class.isAssignableFrom(se.getOutputClass()))) {
      ap.append(XMLDocument.ENCODING);
      ap.append(se.name());
      ap.append(XMLDocument.CLOSE);
    } else {
      ap.append(XMLDocument.CLOSE, 1, 3);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);
    if (child instanceof XMLElement) {
      this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
          XMLDocument.FLAG_HAS_ELEMENT, XMLDocument.FLAG_HAS_ELEMENT,
          FSM.FLAG_NOTHING);
    } else {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    super.onClose();
    this.fsmFlagsAssertTrue(XMLDocument.FLAG_HAS_ELEMENT);
  }

}
