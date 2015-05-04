package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A number appender which appends the numbers directly to the output, but
 * tries to simplify {@code double} numbers via some heuristic:
 * </p>
 * <ol>
 * <li>First it checks whether a {@code double} can be converted to a
 * {@code long} without loss of precision. If so, it will append the
 * {@code long} instead.</li>
 * <li>Some {@code double}s have an overly long string representation in
 * Java. &quot;{@code -7.66eE22}&quot;, for instance, will be represented
 * as &quot;{@code -7.664000000000001E22}&quot; by
 * {@link java.lang.Double#toString(double)}. This appender tries to remedy
 * this problem.</li>
 * </ol>
 */
public final class SimpleNumberAppender extends _PlainIntNumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the simple number appender */
  public static final SimpleNumberAppender INSTANCE = new SimpleNumberAppender();

  /** create */
  private SimpleNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final double v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(this.toString(v, textCase));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double v, final ETextCase textCase) {
    final long l;
    String s, t;
    _CharFloat inc, dec;

    if ((v >= Long.MIN_VALUE) && (v <= Long.MAX_VALUE)) {
      l = ((long) v);
      if (l == v) {
        s = Long.toString(l);
        if (Double.parseDouble(s) == v) {// this should always be true
          return s; // but let's better check it out
        }
      }
    }

    s = t = Double.toString(v);
    if ((v != v) || (v <= Double.NEGATIVE_INFINITY)
        || (v >= Double.POSITIVE_INFINITY)) {
      return s;
    }

    // Now we have the string representation of the double number.
    // Let us see how and whether we can make it shorter.

    inc = new _CharFloat(s);
    t = inc.toString();
    if (Double.parseDouble(t) == v) {
      if (t.length() < s.length()) {
        s = t;
      }
    }

    dec = inc.clone();

    increment: {
      while (inc._incOrDec(1)) {
        t = inc.toString();
        if (Double.parseDouble(t) == v) {
          if (t.length() < s.length()) {
            s = t;
          }
        } else {
          break increment;
        }
      }
    }

    decrement: {
      while (dec._incOrDec(-1)) {
        t = dec.toString();
        if (Double.parseDouble(t) == v) {
          if (t.length() < s.length()) {
            s = t;
          }
        } else {
          break decrement;
        }
      }
    }

    return s;
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return SimpleNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return SimpleNumberAppender.INSTANCE;
  }
}
