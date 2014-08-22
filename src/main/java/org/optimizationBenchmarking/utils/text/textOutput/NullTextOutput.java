package org.optimizationBenchmarking.utils.text.textOutput;

/**
 * An implementation of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * which does nothing.
 * 
 * @author Thomas Weise
 */
public final class NullTextOutput extends AbstractTextOutput {

  /** the globally shared instance of the null text output */
  public static final NullTextOutput INSTANCE = new NullTextOutput();

  /** create the null text output object */
  private NullTextOutput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final NullTextOutput append(final CharSequence csq) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final NullTextOutput append(final CharSequence csq,
      final int start, final int end) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final NullTextOutput append(final char c) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start, final int end) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars, final int start,
      final int end) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final boolean v) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final Object o) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void appendLineBreak() {//
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {//
  }
}
