package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.IOException;
import java.io.Writer;

import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A basic version of the text output wrapped around any subclass of
 * {@link java.io.Writer}.
 *
 * @param <W>
 *          the writer type
 */
class _WriterWrappedTextOutputBase<W extends Writer> extends
    _AppendableWrappedTextOutputBase<W> {

  /**
   * Create a wrapped text output
   *
   * @param out
   *          the output
   */
  _WriterWrappedTextOutputBase(final W out) {
    super(out);
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final CharSequence csq) {
    try {
      this.m_out.append(csq);
    } catch (final IOException ioe) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while trying to append CharSequence to " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), true, ioe);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars) {
    try {
      this.m_out.write(chars);
    } catch (final IOException ioe) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while trying to append char[] to " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), true, ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s) {
    try {
      this.m_out.write(s);
    } catch (final IOException ioe) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while trying to append String to " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), true, ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    try {
      this.m_out.append(csq, start, end);
    } catch (final IOException ioe) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while trying to append part of CharSequence to " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), true, ioe);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final char c) {
    try {
      this.m_out.write(c);
    } catch (final IOException ioe) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while trying to append char to " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), true, ioe);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {
    try {
      this.m_out.flush();
    } catch (final IOException ioe) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while trying to flush " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), true, ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars, final int start,
      final int end) {
    try {
      this.m_out.write(chars, start, (end - start));
    } catch (final IOException ioe) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while trying to append part of char[] to " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), true, ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start, final int end) {
    try {
      this.m_out.write(s, start, (end - start));
    } catch (final IOException ioe) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while trying to append part of String to " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), true, ioe);
    }
  }
}
