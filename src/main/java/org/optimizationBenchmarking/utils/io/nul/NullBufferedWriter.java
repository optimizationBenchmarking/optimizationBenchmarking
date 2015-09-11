package org.optimizationBenchmarking.utils.io.nul;

import java.io.BufferedWriter;

/** A buffered writer that throws away all its input. */
public final class NullBufferedWriter extends BufferedWriter {

  /** the globally shared instance */
  public static final NullBufferedWriter INSTANCE = new NullBufferedWriter();

  /** the null appendable */
  private NullBufferedWriter() {
    super(NullWriter.INSTANCE, 1);
  }

  /** {@inheritDoc} */
  @Override
  public final NullBufferedWriter append(final CharSequence csq) {
    return NullBufferedWriter.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final NullBufferedWriter append(final CharSequence csq,
      final int start, final int end) {
    return NullBufferedWriter.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final NullBufferedWriter append(final char c) {
    return NullBufferedWriter.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final int c) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char cbuf[]) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char cbuf[], final int off, final int len) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String str) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String str, final int off, final int len) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {//
  }

  /** {@inheritDoc} */
  @Override
  public final void close() { //
  }

  /** {@inheritDoc} */
  @Override
  public final void newLine() {//
  }
}
