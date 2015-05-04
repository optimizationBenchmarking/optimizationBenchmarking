package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.Flushable;

/**
 * A {@link ITextOutput} is a simple interface for, well, text output. It
 * has a set of advantages aimed at high-throughput text output:
 * <ol>
 * <li>It extends {@link java.lang.Appendable}, but is not throwing any
 * {@link java.io.IOException}s. Instead its implementations may generate
 * (unchecked) {@link java.lang.RuntimeException}s, if necessary.</li>
 * <li>It offers a set of functions extending beyond the capabilities of
 * {@link java.lang.Appendable}, which may be useful for convenient text
 * output.</li>
 * <li>We provide a set of wrapper classes that can turn any implementation
 * of {@link java.lang.Appendable} into a tailored and final
 * implementations of {@link ITextOutput}. These implementations make use
 * of the specific abilities of the underlying output objects for maximum
 * efficiency.</li>
 * </ol>
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
public interface ITextOutput extends Appendable, Flushable {

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutput append(final CharSequence csq);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutput append(final CharSequence csq,
      final int start, final int end);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutput append(final char c);

  /**
   * Append a string. Passing {@code null} to this method may potentially
   * throw an exception. In this aspect, this method behaves differently
   * from {@link #append(CharSequence)}.
   *
   * @param s
   *          the string
   */
  public abstract void append(final String s);

  /**
   * Append a sub-sequence of a string. Passing {@code null} to this method
   * may potentially throw an exception. In this aspect, this method
   * behaves differently from {@link #append(CharSequence,int,int)}.
   *
   * @param s
   *          the string
   * @param start
   *          the start index
   * @param end
   *          the end index
   */
  public abstract void append(final String s, final int start,
      final int end);

  /**
   * Write some characters.
   *
   * @param chars
   *          the characters
   */
  public abstract void append(final char[] chars);

  /**
   * Append a sub-sequence of a character array.
   *
   * @param chars
   *          the char array
   * @param start
   *          the start
   * @param end
   *          the end
   */
  public abstract void append(final char[] chars, final int start,
      final int end);

  /**
   * append a byte
   *
   * @param v
   *          the byte
   */
  public abstract void append(final byte v);

  /**
   * append a short
   *
   * @param v
   *          the short
   */
  public abstract void append(final short v);

  /**
   * append an integer
   *
   * @param v
   *          the int
   */
  public abstract void append(final int v);

  /**
   * append a long
   *
   * @param v
   *          the long
   */
  public abstract void append(final long v);

  /**
   * append a float
   *
   * @param v
   *          the float
   */
  public abstract void append(final float v);

  /**
   * append a double
   *
   * @param v
   *          the double
   */
  public abstract void append(final double v);

  /**
   * append a boolean
   *
   * @param v
   *          the boolean
   */
  public abstract void append(final boolean v);

  /**
   * Append an object
   *
   * @param o
   *          the object to append
   */
  public abstract void append(final Object o);

  /** Append a line break. */
  public abstract void appendLineBreak();

  /** Append a non-breaking space. */
  public abstract void appendNonBreakingSpace();

  /** {@inheritDoc} */
  @Override
  public abstract void flush();
}
