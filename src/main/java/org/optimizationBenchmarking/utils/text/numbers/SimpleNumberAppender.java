package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A number appender which appends the numbers to the directly output, but
 * tries to simplify {@code double} numbers to {@code longs} if this is
 * possible without loss of information.
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
    boolean b;
    int len;
    char cl, clm1, clm2, clm3;

    if ((v >= Long.MIN_VALUE) && (v <= Long.MAX_VALUE)) {
      l = ((long) v);
      if (l == v) {
        s = Long.toString(l);
        if (Double.parseDouble(s) == v) {// this should always be true
          return s; // but let's better check it out
        }
      }
    }

    s = Double.toString(v);
    if ((v != v) || (v <= Double.NEGATIVE_INFINITY)
        || (v >= Double.POSITIVE_INFINITY)) {
      return s;
    }

    do {
      b = false;
      len = s.length();

      if (len > 2) {
        cl = s.charAt(--len);
        clm1 = s.charAt(--len);

        if ((cl == '0')
            && ((clm1 == '.') || (clm1 == 'E') || (clm1 == 'e'))) {
          b = true;
        } else {
          if (len > 0) {
            clm2 = s.charAt(--len);
            if ((cl == '0') && (clm1 == '0')
                && ((clm2 == '.') || (clm2 == 'E') || (clm2 == 'e'))) {
              b = true;
            } else {
              if (len > 0) {
                clm3 = s.charAt(--len);
                if ((cl == '0') && (clm1 == '0')
                    && ((clm2 == '+') || (clm2 == '-'))
                    && ((clm3 == 'E') || (clm3 == 'e'))) {
                  b = true;
                }
              }
            }
          }
        }
      }

      if (b) {
        t = s.substring(0, len);
        if (Double.parseDouble(t) == v) {
          s = t;
        } else {
          b = false;
        }
      }
    } while (b);

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
