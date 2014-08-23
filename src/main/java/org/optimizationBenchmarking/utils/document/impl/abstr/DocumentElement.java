package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

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

  /**
   * Create a document element.
   * 
   * @param owner
   *          the owning FSM
   * @param out
   *          the output destination
   */
  protected DocumentElement(final HierarchicalFSM owner,
      final Appendable out) {
    super(owner, out);
  }

  /**
   * get the owner
   * 
   * @return the owner
   */
  final HierarchicalFSM _owner() {
    return this.getOwner();
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
   * get an absolute path
   * 
   * @param p
   *          the path
   * @return the path
   */
  static final Path _path(final Path p) {
    return p.normalize().toAbsolutePath();
  }

  /**
   * resolve a path
   * 
   * @param p
   *          the path
   * @param s
   *          a relative string
   * @return a resolved path
   */
  static final Path _resolve(final Path p, final String s) {
    return DocumentElement._path(p.resolve(s));
  }
}
