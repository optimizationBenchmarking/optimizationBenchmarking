package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.IBibliographyConsumer;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A text class which allows creating text with more sophisticated styles
 * in it
 */
public class ComplexText extends PlainText implements IComplexText {

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   */
  protected ComplexText(final DocumentElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final StyledText style(final IStyle style) {
    this.m_driver.checkStyleForText(style);
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createStyledText(this, style);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final PlainText inlineCode() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createInlineCode(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final PlainText emphasize() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createEmphasize(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Subscript subscript() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createSubscript(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Superscript superscript() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createSuperscript(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final InlineMath inlineMath() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createInlineMath(this);
  }

  /**
   * Check a bibliography
   * 
   * @param citationMode
   *          the citation mode
   * @param textCase
   *          the text case
   * @param sequenceMode
   *          the sequence mode
   */
  private static void __checkBib(final ECitationMode citationMode,
      final ETextCase textCase, final ESequenceMode sequenceMode) {
    if (citationMode == null) {
      throw new IllegalArgumentException("Citation mode must not be null."); //$NON-NLS-1$
    }
    if (textCase == null) {
      throw new IllegalArgumentException("Text case must not be null."); //$NON-NLS-1$
    }
    if (sequenceMode == null) {
      throw new IllegalArgumentException("Sequence mode must not be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final BibliographyBuilder cite(
      final ECitationMode citationMode, final ETextCase textCase,
      final ESequenceMode sequenceMode) {
    final BibliographyBuilder bb;

    this.assertNoChildren();

    ComplexText.__checkBib(citationMode, textCase, sequenceMode);

    bb = this.m_doc.m_citations;
    if (bb == null) {
      throw new IllegalStateException(//
          "Citations have already been flushed, cannot cite anything anymore."); //$NON-NLS-1$
    }

    return bb.subBibliography(new _BibliographyConsumer(citationMode,
        textCase, sequenceMode));
  }

  /**
   * This method is called whenever a sub-bibliography has been created via
   * {@link #cite(ECitationMode, ETextCase, ESequenceMode)}. It is called
   * immediately after corresponding the
   * {@link org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder}
   * has been closed. The standard implementation defers control to
   * {@link #doCite(CitationItem[], ECitationMode, ETextCase, ESequenceMode, ITextOutput, ITextOutput)}
   * .
   * 
   * @param bib
   *          the bibliography
   * @param citationMode
   *          the citation mode
   * @param textCase
   *          the text case
   * @param sequenceMode
   *          the sequence mode
   * @see #cite(ECitationMode, ETextCase, ESequenceMode)
   * @see #doCite(CitationItem[], ECitationMode, ETextCase, ESequenceMode,
   *      ITextOutput, ITextOutput)
   */
  protected synchronized void doCite(final Bibliography bib,
      final ECitationMode citationMode, final ETextCase textCase,
      final ESequenceMode sequenceMode) {
    final int len;
    final CitationItem[] ci;
    int i;

    this.assertNoChildren();

    ComplexText.__checkBib(citationMode, textCase, sequenceMode);

    if ((bib == null) || ((len = bib.size()) <= 0)) {
      throw new IllegalArgumentException(//
          "References must not be null or empty."); //$NON-NLS-1$
    }

    ci = new CitationItem[len];
    for (i = 0; i < len; i++) {
      ci[i] = this.m_driver.createCitationItem(bib.get(i), citationMode);

    }

    this.doCite(ci, citationMode, textCase, sequenceMode,
        this.getTextOutput(), this.m_encoded);
  }

  /**
   * Do the work of {@link #cite(ECitationMode, ETextCase, ESequenceMode)}
   * in a protected environment. You need to override either this method or
   * {@link #doCite(Bibliography, ECitationMode, ETextCase, ESequenceMode)}
   * to do the stuff, maybe by delegating the citation rendering to a new
   * sub-class of
   * {@link org.optimizationBenchmarking.utils.document.impl.abstr.CitationItem}
   * for each item.
   * 
   * @param citationMode
   *          the citation mode
   * @param textCase
   *          the text case
   * @param sequenceMode
   *          the sequence mode
   * @param references
   *          the references
   * @param raw
   *          the raw text output
   * @param encoded
   *          the encoded text output
   * @see #doCite(Bibliography, ECitationMode, ETextCase, ESequenceMode)
   * @see #cite(ECitationMode, ETextCase, ESequenceMode)
   */
  protected void doCite(final CitationItem[] references,
      final ECitationMode citationMode, final ETextCase textCase,
      final ESequenceMode sequenceMode, final ITextOutput raw,
      final ITextOutput encoded) {

    ComplexText.__checkBib(citationMode, textCase, sequenceMode);
    if ((references == null) || (references.length <= 0)) {
      throw new IllegalArgumentException(
          "References must not be null or empty."); //$NON-NLS-1$
    }

    sequenceMode.appendSequence(textCase, new ArrayListView<>(references),
        true, this);
  }

  /**
   * Do the actual referencing work
   * 
   * @param textCase
   *          the text case
   * @param sequenceMode
   *          the sequence mode
   * @param runs
   *          the runs
   */
  protected void doReference(final ETextCase textCase,
      final ESequenceMode sequenceMode, final ReferenceRun[] runs) {
    if (runs.length > 0) {
      ESequenceMode.AND.appendNestedSequence(textCase,
          new ArrayListView<>(runs), true, 1, this);
    } else {
      runs[0].toSequence(true, true, textCase, this);
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void reference(final ETextCase textCase,
      final ESequenceMode sequenceMode, final ILabel... labels) {
    final int len;
    int i, j, start, count;
    Label a;
    String curType, newType;
    ReferenceRun[] runs;
    Label[] cpy;

    this.assertNoChildren();

    if ((labels == null) || ((len = labels.length) <= 0)) {
      throw new IllegalArgumentException(//
          "Labels must not be null or empty."); //$NON-NLS-1$
    }

    curType = null;
    start = 0;
    count = 0;
    runs = new ReferenceRun[len];

    // check the labels and identify runs of the same types (or type names)
    for (i = 0; i < len; i++) {
      a = ((Label) (labels[i]));
      if (a == null) {
        throw new IllegalArgumentException(//
            "Label must not be null."); //$NON-NLS-1$
      }
      for (j = i; (--j) >= 0;) {
        if (a.equals(labels[j])) {
          throw new IllegalArgumentException(//
              "The same label must not appear twice in a reference, but '" //$NON-NLS-1$
                  + a + "' does."); //$NON-NLS-1$
        }
      }

      newType = a.getType().getName();
      if (newType.equals(curType)) {
        continue;
      }
      if (i > 0) {
        cpy = new Label[i - start];
        System.arraycopy(labels, start, cpy, 0, cpy.length);
        runs[count++] = this.m_driver.createReferenceRun(curType,
            sequenceMode, cpy);
        start = i;
      }
      curType = newType;
    }

    cpy = new Label[i - start];
    System.arraycopy(labels, start, cpy, 0, cpy.length);
    runs[count++] = this.m_driver.createReferenceRun(curType,
        sequenceMode, cpy);

    this.doReference(textCase, sequenceMode,//
        ((count < runs.length) ? (Arrays.copyOf(runs, count)) : runs));
  }

  /** the bibliography consumer */
  private final class _BibliographyConsumer implements
      IBibliographyConsumer {

    /** the citation mode */
    private final ECitationMode m_citationMode;

    /** the text case */
    private final ETextCase m_textCase;

    /** the sequence mode */
    private final ESequenceMode m_sequenceMode;

    /**
     * create
     * 
     * @param citationMode
     *          the citation mode
     * @param textCase
     *          the text case
     * @param sequenceMode
     *          the sequence mode
     */
    _BibliographyConsumer(final ECitationMode citationMode,
        final ETextCase textCase, final ESequenceMode sequenceMode) {
      super();
      this.m_citationMode = citationMode;
      this.m_textCase = textCase;
      this.m_sequenceMode = sequenceMode;
    }

    /** {@inheritDoc} */
    @Override
    public final void consumeBibliography(final Bibliography bib) {
      ComplexText.this.doCite(bib, this.m_citationMode, this.m_textCase,
          this.m_sequenceMode);
    }

  }

}
