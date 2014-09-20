package org.optimizationBenchmarking.utils.hierarchy;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * <p>
 * A hierarchical way to structure
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * objects for parallel text output.
 * </p>
 * <p>
 * A hierarchical text is an instance of {@link java.lang.AutoCloseable}.
 * Thus, its scope can be managed with a {@code try...with...} statement.
 * However, it will not propagate any closing action to the underlying text
 * output objects. If it is connected to a stream, it will not close the
 * stream. You need to manage the stream separately.
 * </p>
 */
public abstract class HierarchicalText extends HierarchicalFSM {

  /**
   * the
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * to write to
   */
  ITextOutput m_out;

  /**
   * a cached
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * to be re-used for the next child
   */
  private volatile transient MemoryTextOutput m_cache;

  /** the output buffer has been taken */
  private volatile transient boolean m_outTaken;

  /**
   * Create the hierarchical {@link java.lang.Appendable}
   * 
   * @param owner
   *          the owning hierarchical fsm
   * @param out
   *          the {@link java.lang.Appendable}
   */
  protected HierarchicalText(final HierarchicalFSM owner,
      final Appendable out) {
    super(owner);

    final MemoryTextOutput sb;

    if (out == null) {
      if (owner == null) {
        sb = new MemoryTextOutput();
        sb.append("If the owner is null, this means this object is the root "); //$NON-NLS-1$
        FSM._name(this, sb);
        sb.append(", so it cannot have a null appendable."); //$NON-NLS-1$
        throw new IllegalArgumentException(sb.toString());
      }
    } else {
      if (owner instanceof HierarchicalText) {
        sb = new MemoryTextOutput();
        sb.append("If the "); //$NON-NLS-1$
        FSM._name(this, sb);
        sb.append(" receives a non-null appendable (in this case, an instance of "); //$NON-NLS-1$
        sb.append(TextUtils.className(out.getClass()));
        sb.append(") in the constructor, its owner cannot be an instance of "); //$NON-NLS-1$
        sb.append(TextUtils.className(HierarchicalText.class));
        sb.append(" but the provided owner "); //$NON-NLS-1$
        FSM._name(owner, sb);
        sb.append(" is."); //$NON-NLS-1$
        throw new IllegalArgumentException(sb.toString());
      }
      this.m_out = AbstractTextOutput.wrap(out);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    final HierarchicalText ct;
    final MemoryTextOutput m;

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof HierarchicalText) {
      ct = ((HierarchicalText) child);
      if (this.m_outTaken || this.mustChildBeBuffered(ct)) {
        m = this.m_cache;
        if (m != null) {
          this.m_cache = null;
          ct.m_out = m;
        } else {
          ct.m_out = new MemoryTextOutput();
        }
      } else {
        this.m_outTaken = true;
        ct.m_out = this.m_out;
      }
    }
  }

  /**
   * Check whether a given child text can write directly to the same output
   * destination as this object, {@code false} otherwise. If this method
   * returns {@code true}, {@code child} will definitely write its contents
   * to an instance of
   * {@link org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput}
   * which will then be copied to this text's output stream once the child
   * gets closed. If this method returns {@code false}, the child
   * <em>may</em> either be buffered as described above, or use this text's
   * output stream directly as well. There can only be at most one child
   * text sharing its text output stream with this text.
   * 
   * @param child
   *          the child element
   * @return {@code true} if the child must be buffered, {@code false}
   *         otherwise.
   * @see #processBufferedOutputFromChild(HierarchicalText,
   *      MemoryTextOutput)
   */
  protected boolean mustChildBeBuffered(final HierarchicalText child) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {
    final Appendable childAppendable, ownAppendable;
    final HierarchicalText ha;
    MemoryTextOutput sb;

    super.afterChildClosed(child);

    if (child instanceof HierarchicalText) {
      ha = ((HierarchicalText) (child));
      childAppendable = ha.m_out;
      ha.m_out = null;

      if (childAppendable == null) {
        sb = new MemoryTextOutput();
        sb.append("When closing an instance of "); //$NON-NLS-1$
        sb.append(TextUtils.className(HierarchicalText.class));
        sb.append(", its internal appendable must not be null, but "); //$NON-NLS-1$
        FSM._name(child, sb);
        sb.append(", the closed child of "); //$NON-NLS-1$
        FSM._name(this, sb);
        sb.append(" has a null appendable."); //$NON-NLS-1$
        throw new IllegalStateException(sb.toString());
      }

      ownAppendable = this.m_out;
      if (ownAppendable != childAppendable) {
        sb = ((MemoryTextOutput) childAppendable);
        this.processBufferedOutputFromChild(ha, sb);
        if (this.m_cache == null) {
          sb.clear();
          this.m_cache = sb;
        }
      } else {
        this.m_outTaken = false;
      }
    }
  }

  /**
   * <p>
   * Process the buffered output from a child text. The default
   * implementation of this method copies the output to the own text.
   * </p>
   * <p>
   * An overridden version of this method may cache the output. In any
   * case, it is not allowed to make any assumption about the content of
   * {@code out} after this method has returned, i.e., the contents of
   * {@code out} may change, may be overridden, or otherwise invalidated
   * directly after
   * {@link #processBufferedOutputFromChild(HierarchicalText, MemoryTextOutput)}
   * returns.
   * </p>
   * <p>
   * This method is designed to work together with
   * {@code #mustChildBeBuffered(HierarchicalText)} in order to provide a
   * way to always cache the output of certain objects for later use.
   * </p>
   * 
   * @param child
   *          the child text
   * @param out
   *          the output
   * @see #mustChildBeBuffered(HierarchicalText)
   */
  protected void processBufferedOutputFromChild(
      final HierarchicalText child, final MemoryTextOutput out) {
    out.toText(this.m_out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.m_cache = null;
    super.onClose();
  }

  /**
   * Obtain the encoding of the underlying {@link java.lang.Appendable}
   * 
   * @return the encoding
   */
  protected StreamEncoding<?, ?> getStreamEncoding() {
    if (this.m_owner instanceof HierarchicalText) {
      return ((HierarchicalText) (this.m_owner)).getStreamEncoding();
    }
    return StreamEncoding.getStreamEncoding(this.m_out);
  }

  /**
   * Obtain direct access to the underlying
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * . This method must be called in a synchronized block and the returned
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * must only be used inside that block.
   * 
   * @return the
   *         {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   */
  protected final ITextOutput getTextOutput() {
    this.assertNoChildren();
    return this.m_out;
  }
}