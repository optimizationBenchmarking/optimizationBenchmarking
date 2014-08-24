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
  private transient MemoryTextOutput m_cache;

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
      if (hasOtherChildren) {
        m = this.m_cache;
        if (m != null) {
          this.m_cache = null;
          ct.m_out = m;
        } else {
          ct.m_out = new MemoryTextOutput();
        }
      } else {
        ct.m_out = this.m_out;
      }
    }
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
        sb.toText(this.m_out);
        if (this.m_cache == null) {
          sb.clear();
          this.m_cache = sb;
        }
      }
    }
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