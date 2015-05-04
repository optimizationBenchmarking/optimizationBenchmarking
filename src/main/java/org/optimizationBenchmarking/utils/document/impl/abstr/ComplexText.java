package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.IBibliographyConsumer;
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
  public synchronized final Text inlineCode() {
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

  /** {@inheritDoc} */
  @Override
  public synchronized final BibliographyBuilder cite(
      final ECitationMode citationMode, final ETextCase textCase,
      final ESequenceMode sequenceMode) {
    final BibliographyBuilder bb;

    this.assertNoChildren();

    DocumentDriver._checkCitationSetup(citationMode, textCase,
        sequenceMode);

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
   * has been closed. The standard implementation defers control to the
   * cite method of the driver.
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
   */
  final synchronized void _doCite(final Bibliography bib,
      final ECitationMode citationMode, final ETextCase textCase,
      final ESequenceMode sequenceMode) {
    final ITextOutput out;

    this.assertNoChildren();
    DocumentDriver._checkCitationSetup(citationMode, textCase,
        sequenceMode);
    DocumentDriver._checkCitations(bib);

    out = this.getTextOutput();
    this.m_driver.prependSpaceToCitation(//
        citationMode, textCase, this, out);
    this.m_driver.cite(bib, citationMode, textCase, sequenceMode, this,
        out);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void reference(final ETextCase textCase,
      final ESequenceMode sequenceMode, final ILabel... labels) {
    final ITextOutput out;

    this.assertNoChildren();
    DocumentDriver._checkReferenceSetup(textCase, sequenceMode, labels);

    out = this.getTextOutput();
    this.m_driver.prependSpaceToReference(textCase, this, out);
    this.m_driver.reference(textCase, sequenceMode, labels, this, out);
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
      ComplexText.this._doCite(bib, this.m_citationMode, this.m_textCase,
          this.m_sequenceMode);
    }

  }

}
