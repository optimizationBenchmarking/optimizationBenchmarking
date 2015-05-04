package org.optimizationBenchmarking.utils.text.transformations;

import java.text.Normalizer;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An implementation of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * which first normalizes a text and then transforms it to XMLFileType
 * entities and writes it to its output.
 *
 * @author Thomas Weise
 */
final class _NormalizingXMLTransformedTextOutput extends
    _XMLTransformedTextOutput {

  /** the normalizer form */
  private final Normalizer.Form m_form;

  /**
   * create the transformed text output
   *
   * @param out
   *          the wrapped output
   * @param form
   *          the normalizer form
   */
  _NormalizingXMLTransformedTextOutput(final ITextOutput out,
      final Normalizer.Form form) {
    super(out);
    this.m_form = form;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s) {
    final String t;
    t = Normalizer.normalize(s, this.m_form);
    super.append(t, 0, t.length());
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.append(csq.subSequence(start, end).toString());
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final char c) {
    this.append(String.valueOf(c));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start, final int end) {
    this.append(s.substring(start, end));
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars, final int start,
      final int end) {
    this.append(String.valueOf(chars, start, (end - start)));
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    final String s;
    s = Byte.toString(v);
    super.append(s, 0, s.length());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    final String s;
    s = Short.toString(v);
    super.append(s, 0, s.length());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    final String s;
    s = Integer.toString(v);
    super.append(s, 0, s.length());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    final String s;
    s = Long.toString(v);
    super.append(s, 0, s.length());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    final String s;
    s = Float.toString(v);
    super.append(s, 0, s.length());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    final String s;
    s = Double.toString(v);
    super.append(s, 0, s.length());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final boolean v) {
    final String s;
    s = Boolean.toString(v);
    super.append(s, 0, s.length());
  }
}