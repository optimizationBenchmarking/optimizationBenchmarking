package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.CharBuffer;

import org.optimizationBenchmarking.utils.io.NullWriter;
import org.optimizationBenchmarking.utils.io.encoding.IStreamEncoded;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.CharArrayCharSequence;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * A {@link AbstractTextOutput} is a base class for implementing
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}.
 * </p>
 * <p>
 * A plain implementation of {@link ITextOutput} is not necessarily an
 * instance of {@link java.lang.AutoCloseable}. Thus, even if it is
 * {@link org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput#wrap(Appendable)
 * wrapped} around an instance of that interface, it will not necessarily
 * propagate any closing when being closed.
 * </p>
 * 
 * @author Thomas Weise
 */
public class AbstractTextOutput implements ITextOutput, IStreamEncoded {

  /** the null character array */
  static final char[] NULL = { 'n', 'u', 'l', 'l' };

  /** create the text output object */
  protected AbstractTextOutput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final CharSequence csq) {
    if (csq != null) {
      this.append(csq, 0, csq.length());
    } else {
      this.append(AbstractTextOutput.NULL, 0, 4);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final char c) {
    this.append(new CharArrayCharSequence(new char[] { c }));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void append(final String s) {
    this.append(s, 0, s.length());
  }

  /** {@inheritDoc} */
  @Override
  public void append(final String s, final int start, final int end) {
    this.append(((CharSequence) s), start, end);
  }

  /** {@inheritDoc} */
  @Override
  public void append(final char[] chars) {
    this.append(chars, 0, chars.length);
  }

  /** {@inheritDoc} */
  @Override
  public void append(final char[] chars, final int start, final int end) {
    this.append(new CharArrayCharSequence(chars), start, end);
  }

  /** {@inheritDoc} */
  @Override
  public void append(final byte v) {
    this.append(Byte.toString(v));
  }

  /** {@inheritDoc} */
  @Override
  public void append(final short v) {
    this.append(Short.toString(v));
  }

  /** {@inheritDoc} */
  @Override
  public void append(final int v) {
    this.append(Integer.toString(v));
  }

  /** {@inheritDoc} */
  @Override
  public void append(final long v) {
    this.append(Long.toString(v));
  }

  /** {@inheritDoc} */
  @Override
  public void append(final float v) {
    this.append(Float.toString(v));
  }

  /** {@inheritDoc} */
  @Override
  public void append(final double v) {
    this.append(Double.toString(v));
  }

  /** {@inheritDoc} */
  @Override
  public void append(final boolean v) {
    this.append(Boolean.toString(v));
  }

  /** {@inheritDoc} */
  @Override
  public void append(final Object o) {
    if (o instanceof ITextable) {
      ((ITextable) o).toText(this);
    } else {
      this.append(String.valueOf(o));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void appendLineBreak() {
    this.append(TextUtils.LINE_SEPARATOR);
  }

  /**
   * Append a non-breaking space. This default implementation appends the
   * Unicode character with the hexadecimal code point 2007, which is a
   * non-breakable white space.
   */
  @Override
  public void appendNonBreakingSpace() {
    this.append('\u2007');
  }

  /** {@inheritDoc} */
  @Override
  public void flush() {//
  }

  /**
   * Wrap a text output object around a given {@link java.lang.Appendable}
   * 
   * @param in
   *          the {@link java.lang.Appendable}
   * @return the text output
   */
  public static final AbstractTextOutput wrap(final Appendable in) {
    if (in == null) {
      throw new IllegalArgumentException(//
          "Cannot wrap a null Appendable into an AbstractTextOutput."); //$NON-NLS-1$
    }

    if (in instanceof AbstractTextOutput) {
      return ((AbstractTextOutput) (in));
    }
    if (in instanceof Writer) {
      if (in instanceof BufferedWriter) {
        return new _BufferedWriterWrappedTextOutput((BufferedWriter) in);
      }
      if (in instanceof PrintWriter) {
        return new _PrintWriterWrappedTextOutput((PrintWriter) in);
      }
      if (in instanceof StringWriter) {
        return new _StringBufferWrappedTextOutput(
            ((StringWriter) in).getBuffer());
      }
      if (in instanceof CharArrayWriter) {
        return new _CharArrayWriterWrappedTextOutput((CharArrayWriter) in);
      }
      if (in instanceof NullWriter) {
        return NullTextOutput.INSTANCE;
      }
      return new _WriterWrappedTextOutput((Writer) in);
    }
    if (in instanceof PrintStream) {
      return new _PrintStreamWrappedTextOutput((PrintStream) in);
    }
    if (in instanceof StringBuilder) {
      return new _StringBuilderWrappedTextOutput((StringBuilder) in);
    }
    if (in instanceof StringBuffer) {
      return new _StringBufferWrappedTextOutput((StringBuffer) in);
    }
    if (in instanceof CharBuffer) {
      return new _CharBufferWrappedTextOutput((CharBuffer) in);
    }
    return new _AppendableWrappedTextOutput(in);
  }

  /** {@inheritDoc} */
  @Override
  public StreamEncoding<? extends Reader, ? extends Writer> getStreamEncoding() {
    return StreamEncoding.TEXT;
  }
}
