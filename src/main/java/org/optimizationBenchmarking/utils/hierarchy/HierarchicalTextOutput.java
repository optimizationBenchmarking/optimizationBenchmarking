package org.optimizationBenchmarking.utils.hierarchy;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A subclass of
 * {@link org.optimizationBenchmarking.utils.hierarchy.HierarchicalText}
 * which publishes its API in the dedicated interfaces.
 */
public class HierarchicalTextOutput extends HierarchicalText implements
ITextOutput {

  /**
   * Create the hierarchical {@link java.lang.Appendable} with published
   * API
   *
   * @param out
   *          the {@link java.lang.Appendable}
   */
  public HierarchicalTextOutput(final Appendable out) {
    this(null, out);
  }

  /**
   * Create the hierarchical {@link java.lang.Appendable} with published
   * API
   *
   * @param owner
   *          the owning hierarchical fsm
   */
  public HierarchicalTextOutput(final HierarchicalText owner) {
    this(owner, null);
  }

  /**
   * Create the hierarchical {@link java.lang.Appendable} with published
   * API
   *
   * @param owner
   *          the owning hierarchical fsm
   */
  public HierarchicalTextOutput(final HierarchicalTextOutput owner) {
    this(owner, null);
  }

  /**
   * Obtain a new hierarchical {@link java.lang.Appendable} attached to
   * this one.
   *
   * @return a new hierarchical {@link java.lang.Appendable} attached to
   *         this one
   */
  public HierarchicalTextOutput newText() {
    return new HierarchicalTextOutput(this);
  }

  /**
   * Create the hierarchical {@link java.lang.Appendable} with published
   * API
   *
   * @param owner
   *          the owning hierarchical fsm
   * @param out
   *          the {@link java.lang.Appendable}
   */
  private HierarchicalTextOutput(final HierarchicalFSM owner,
      final Appendable out) {
    super(owner, out);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final HierarchicalTextOutput append(
      final CharSequence csq) {
    this.assertNoChildren();
    this.m_out.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final HierarchicalTextOutput append(
      final CharSequence csq, final int start, final int end) {
    this.assertNoChildren();
    this.m_out.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final HierarchicalTextOutput append(final char c) {
    this.assertNoChildren();
    this.m_out.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final String s) {
    this.assertNoChildren();
    this.m_out.append(s);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final String s, final int start,
      final int end) {
    this.assertNoChildren();
    this.m_out.append(s, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final char[] chars) {
    this.assertNoChildren();
    this.m_out.append(chars);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final char[] chars,
      final int start, final int end) {

    this.assertNoChildren();
    this.m_out.append(chars, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final byte v) {
    this.assertNoChildren();
    this.m_out.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final short v) {
    this.assertNoChildren();
    this.m_out.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final int v) {
    this.assertNoChildren();
    this.m_out.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final long v) {
    this.assertNoChildren();
    this.m_out.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final float v) {
    this.assertNoChildren();
    this.m_out.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final double v) {
    this.assertNoChildren();
    this.m_out.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final boolean v) {
    this.assertNoChildren();
    this.m_out.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final Object o) {
    this.assertNoChildren();
    this.m_out.append(o);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.m_out.appendLineBreak();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendNonBreakingSpace() {
    this.assertNoChildren();
    this.m_out.appendNonBreakingSpace();
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {
    this.m_out.flush();
  }

}
