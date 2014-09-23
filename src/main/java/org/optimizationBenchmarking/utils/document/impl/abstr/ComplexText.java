package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.CitationsBuilder;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;

/**
 * A text class which allows creating text with more sophisticated styles
 * in it
 */
public class ComplexText extends Text implements IComplexText {

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
  public synchronized final InlineCode inlineCode() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createInlineCode(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Emphasize emphasize() {
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
   * Do the work of
   * {@link #cite(ECitationMode, ETextCase, ESequenceMode, BibRecord...)}
   * in a protected environment. You need to override this method to do the
   * stuff, maybe by delegating the citation rendering to a new sub-class
   * of
   * {@link org.optimizationBenchmarking.utils.document.impl.abstr.CitationItem}
   * for each item-
   * 
   * @param citationMode
   *          the citation mode
   * @param textCase
   *          the text case
   * @param sequenceMode
   *          the sequence mode
   * @param references
   *          the references
   */
  protected void doCite(final ECitationMode citationMode,
      final ETextCase textCase, final ESequenceMode sequenceMode,
      final CitationItem[] references) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void cite(final ECitationMode citationMode,
      final ETextCase textCase, final ESequenceMode sequenceMode,
      final BibRecord... references) {
    final CitationsBuilder cb;
    final int len;
    final CitationItem[] ci;
    int i, j;
    BibRecord a;

    this.assertNoChildren();

    if ((references == null) || ((len = references.length) <= 0)) {
      throw new IllegalArgumentException(//
          "References must not be null or empty."); //$NON-NLS-1$
    }

    for (i = len; (--i) >= 0;) {
      a = references[i];
      if (a == null) {
        throw new IllegalArgumentException(//
            "Reference must not be null."); //$NON-NLS-1$
      }
      for (j = len; (--j) >= i;) {
        if (a.equals(references[j])) {
          throw new IllegalArgumentException(//
              "The same reference must not appear twice in a citation, but '" //$NON-NLS-1$
                  + a + "' does."); //$NON-NLS-1$
        }
      }
    }

    cb = this.m_doc.m_citations;
    if (cb == null) {
      throw new IllegalStateException(//
          "Citations have already been flushed, cannot cite anything anymore."); //$NON-NLS-1$
    }

    ci = new CitationItem[len];
    synchronized (cb) {
      for (i = 0; i < len; i++) {
        a = references[i];
        ci[i] = this.m_driver.createCitationItem(a, cb.add(a),
            citationMode);

      }
    }

    this.doCite(citationMode, textCase, sequenceMode, ci);
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
    throw new UnsupportedOperationException();
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
        runs[count++] = this.m_driver.createReferenceRun(curType, cpy);
        start = i;
      }
      curType = newType;
    }

    cpy = new Label[i - start];
    System.arraycopy(labels, start, cpy, 0, cpy.length);
    runs[count++] = this.m_driver.createReferenceRun(curType, cpy);

    this.doReference(textCase, sequenceMode,//
        ((count < runs.length) ? (Arrays.copyOf(runs, count)) : runs));
  }

}
