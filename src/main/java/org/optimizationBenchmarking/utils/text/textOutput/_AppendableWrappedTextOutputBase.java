package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.Flushable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A version of the text output wrapped around an
 * {@link java.lang.Appendable}.
 * 
 * @param <A>
 *          the appendable type
 */
class _AppendableWrappedTextOutputBase<A extends Appendable> extends
    AbstractTextOutput {

  /** the appendable */
  final A m_out;

  /**
   * Create a wrapped text output
   * 
   * @param out
   *          the output
   */
  _AppendableWrappedTextOutputBase(final A out) {
    super();
    if (out == null) {
      throw new IllegalArgumentException(//
          "The Appendable instance to be wrapped into a " + //$NON-NLS-1$
              TextUtils.className(this.getClass()) + " cannot be null.");//$NON-NLS-1$
    }
    this.m_out = out;
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final CharSequence csq) {
    try {
      this.m_out.append(csq);
    } catch (final IOException ioe) {
      ErrorUtils.throwAsRuntimeException(ioe);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    try {
      this.m_out.append(csq, start, end);
    } catch (final IOException ioe) {
      ErrorUtils.throwAsRuntimeException(ioe);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final char c) {
    try {
      this.m_out.append(c);
    } catch (final IOException ioe) {
      ErrorUtils.throwAsRuntimeException(ioe);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void flush() {
    if (this.m_out instanceof Flushable) {
      try {
        ((Flushable) (this.m_out)).flush();
      } catch (final IOException ioe) {
        ErrorUtils.throwAsRuntimeException(ioe);
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final StreamEncoding<? extends Reader, ? extends Writer> getStreamEncoding() {
    StreamEncoding enc;

    enc = StreamEncoding.getStreamEncoding(this.m_out);
    if (enc != StreamEncoding.TEXT) {
      if (Reader.class.isAssignableFrom(enc.getInputClass())
          && Writer.class.isAssignableFrom(enc.getOutputClass())) {
        return enc;
      }
    }
    return StreamEncoding.TEXT;
  }
}
