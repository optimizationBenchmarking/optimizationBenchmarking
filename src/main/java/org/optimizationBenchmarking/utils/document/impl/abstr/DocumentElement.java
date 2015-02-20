package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.impl.abstr.FileCollector;

/**
 * The base object for the document API.
 */
public abstract class DocumentElement extends HierarchicalText implements
    IDocumentElement {

  /** the state after opening */
  static final int STATE_ALIFE = (FSM.STATE_NOTHING + 1);
  /** the state after the closing */
  static final int STATE_DEAD = (DocumentElement.STATE_ALIFE + 1);
  /** the highest state occupied by the element */
  static final int STATE_MAX_ELEMENT = DocumentElement.STATE_DEAD;

  /** the document driver */
  final DocumentDriver m_driver;

  /**
   * Create a document element.
   * 
   * @param driver
   *          the driver
   * @param out
   *          the output destination
   */
  DocumentElement(final DocumentDriver driver, final Appendable out) {
    super(null, out);

    if (driver == null) {
      throw new IllegalArgumentException(//
          "Document driver must not be null."); //$NON-NLS-1$
    }
    this.m_driver = driver;
  }

  /**
   * Create a document element.
   * 
   * @param owner
   *          the owning element
   */
  DocumentElement(final DocumentElement owner) {
    super(owner, null);
    this.m_driver = owner.m_driver;
  }

  /**
   * Obtain the logger to which logging information can be written, or
   * {@code null} if no logging information should be produced
   * 
   * @return the logger to receive progress information, or {@code null} if
   *         no progress information should be logged
   */
  protected abstract Logger getLogger();

  /**
   * get the owner
   * 
   * @return the owner
   */
  final DocumentElement _owner() {
    return ((DocumentElement) (this.getOwner()));
  }

  /**
   * get the raw, unencoded text output
   * 
   * @return the text output
   */
  final ITextOutput _raw() {
    return this.getTextOutput();
  }

  /**
   * Obtain the document driver
   * 
   * @return the document driver
   */
  protected final DocumentDriver getDriver() {
    return this.m_driver;
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    switch (state) {
      case STATE_ALIFE: {
        sb.append("alife"); //$NON-NLS-1$
        return;
      }
      case STATE_DEAD: {
        sb.append("dead"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmStateAppendName(state, sb);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    super.onOpen();
    this.fsmStateAssertAndSet(FSM.STATE_NOTHING,
        DocumentElement.STATE_ALIFE);
  }

  /**
   * Obtain an instance of
   * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener}
   * which forwards all produced files to the main file collection of the
   * document-
   * 
   * @return a file producer listener which appends all received
   *         notifications to the document
   */
  protected abstract FileCollector getFileCollector();

  /**
   * Notify the document that a given style has been used.
   * 
   * @param style
   *          the style which has been used
   */
  protected void styleUsed(final IStyle style) {
    //
  }

}
