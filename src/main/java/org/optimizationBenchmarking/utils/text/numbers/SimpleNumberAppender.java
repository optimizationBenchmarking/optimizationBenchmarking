package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.error.RethrowMode;
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
  public final ETextCase appendTo(final double v,
      final ETextCase textCase, final ITextOutput textOut) {
    textOut.append(this.toString(v, textCase));
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double v, final ETextCase textCase) {
    final long l;
    String s;

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

    return SimpleNumberAppender._simplify(v, s).m_string;
  }

  /**
   * Simplify a given string
   *
   * @param value
   *          the double value
   * @param orig
   *          the original string
   * @return the simplified version
   */
  static final _NumberString _simplify(final double value,
      final String orig) {
    __CharFloat inc, dec;
    String temp;
    _NumberString current, best;
    // Now we have the string representation of the double number.
    // Let us see how and whether we can make it shorter.
    best = new _NumberString(orig);

    inc = new __CharFloat(orig);
    temp = inc.toString();
    if (Double.parseDouble(temp) == value) {
      current = new _NumberString(temp);
      if (current.compareTo(best) < 0) {
        best = current;
      }
    }

    dec = inc.clone();

    increment: {
      while (inc._incOrDec(1)) {
        temp = inc.toString();
        if (Double.parseDouble(temp) == value) {
          current = new _NumberString(temp);
          if (current.compareTo(best) < 0) {
            best = current;
          }
        } else {
          break increment;
        }
      }
    }

    decrement: {
      while (dec._incOrDec(-1)) {
        temp = dec.toString();
        if (Double.parseDouble(temp) == value) {
          current = new _NumberString(temp);
          if (current.compareTo(best) < 0) {
            best = current;
          }
        } else {
          break decrement;
        }
      }
    }

    return best;
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

  /** a floating point number encoded as a character string */
  private static final class __CharFloat implements Cloneable {

    /** the data */
    private char[] m_data;

    /** the mantissa start */
    private int m_mantissaStart;

    /** the mantissa dot position */
    int m_mantissaDot;

    /** the exclusive mantissa end */
    private int m_mantissaEnd;

    /** is the mantissa negative? */
    private final boolean m_mantissaIsNegative;

    /** the exponent */
    private int m_exponent;

    /**
     * create the character representation from a string
     *
     * @param string
     *          the string
     */
    __CharFloat(final String string) {
      super();

      final char[] data;
      char ch;
      boolean exponentSign;
      int length, dot, mantissaStart, mantissaEnd, exponentStart, exponent;

      length = string.length();
      neverLeave: {
        if (length <= 0) {
          break neverLeave;
        }

        this.m_data = data = new char[length + 2];
        string.getChars(0, length, data, 1);
        length++;

        mantissaStart = 1;
        switch (ch = data[mantissaStart]) {
          case '-': {
            mantissaStart++;
            this.m_mantissaIsNegative = true;
            break;
          }
          case '+': {
            mantissaStart++;
            this.m_mantissaIsNegative = false;
            break;
          }
          default: {
            if ((ch < '0') || (ch > '9')) {
              break neverLeave;
            }
            this.m_mantissaIsNegative = false;
          }
        }

        if (mantissaStart >= length) {
          break neverLeave;
        }

        while (data[mantissaStart] == '0') {
          mantissaStart++;
          if (mantissaStart >= length) {
            break neverLeave;
          }
        }

        exponentStart = dot = (-1);
        findMantissaEnd: for (mantissaEnd = mantissaStart; mantissaEnd < length; mantissaEnd++) {
          switch (ch = data[mantissaEnd]) {
            case '.': {
              if (dot >= 0) {
                break neverLeave;
              }
              dot = mantissaEnd;
              if (dot == mantissaStart) {
                if (mantissaStart <= 1) {
                  break neverLeave;
                }
                if (data[--mantissaStart] != '0') {
                  break neverLeave;
                }
              }
              break;
            }
            case 'E':
            case 'e': {
              exponentStart = (mantissaEnd + 1);
              break findMantissaEnd;
            }
            default: {
              if ((ch < '0') || (ch > '9')) {
                break neverLeave;
              }
            }
          }
        }

        if ((exponentStart < 0) && (mantissaEnd < length)) {
          break neverLeave;
        }

        if (dot >= 0) {
          while ((mantissaEnd > dot) && (data[mantissaEnd - 1] == '0')) {
            mantissaEnd--;
          }
        }

        this.m_mantissaDot = dot;
        if ((mantissaEnd <= mantissaStart)
            || ((dot >= 0) && (mantissaEnd <= dot))) {
          break neverLeave;
        }
        this.m_mantissaStart = mantissaStart;
        this.m_mantissaEnd = mantissaEnd;

        if ((exponentStart > 0) && (exponentStart < length)) {
          switch (data[exponentStart]) {
            case '-': {
              exponentSign = true;
              exponentStart++;
              break;
            }
            case '+': {
              exponentSign = false;
              exponentStart++;
              break;
            }
            default: {
              exponentSign = false;
            }
          }

          if (exponentStart >= length) {
            break neverLeave;
          }
          exponent = 0;
          while (exponentStart < length) {
            ch = data[exponentStart++];
            if ((ch < '0') || (ch > '9')) {
              break neverLeave;
            }
            exponent = ((exponent * 10) + (ch - '0'));
          }
          this.m_exponent = (exponentSign ? (-exponent) : exponent);
        }

        return;
      }

      throw new IllegalArgumentException(string);
    }

    /**
     * increase or decrease by 1
     *
     * @param carry
     *          either 1 or -1
     * @return {@code true} if the float is still valid
     */
    boolean _incOrDec(final int carry) {
      char[] data;
      int start, i, end;
      char res;

      start = this.m_mantissaStart;
      data = this.m_data;

      end = this.m_mantissaEnd;

      add: {
        for (i = end; (--i) >= start;) {
          res = data[i];
          if (res == '.') {
            continue;
          }
          res += carry;
          data[i] = res;
          if (res > '9') {
            data[i] = res = '0';
          } else {
            if (res < '0') {
              data[i] = res = '9';
            } else {
              end = (i + 1);
              break add;
            }
          }
        }

        if (i < 0) {
          return false;
        }

        if (start <= 0) {
          return false;
        }
        this.m_mantissaStart = (--start);
        data[start] = '1';
        i = this.m_mantissaDot;
        if (i >= 0) {
          data[i] = data[--i];
          data[i] = '.';
          this.m_mantissaDot = i;
          if (this.m_exponent == 0) {
            return false;
          }
          this.m_exponent++;
        }
      }

      while (end > start) {
        res = data[end - 1];
        if ((res != '0') && (res != '.')) {
          break;
        }
        end--;
      }

      this.m_mantissaEnd = end;
      if (end <= this.m_mantissaDot) {
        this.m_mantissaDot = (-1);
      }
      if (end <= start) {
        return false;
      }
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {

      final char[] data;
      int start, mantissaEnd, e, g;
      boolean doIt;

      start = this.m_mantissaStart;
      mantissaEnd = this.m_mantissaEnd;
      data = this.m_data;

      if (this.m_mantissaIsNegative) {
        data[--start] = '-';
      }

      e = this.m_exponent;
      if (e != 0) {
        data[mantissaEnd++] = 'E';
        if (e < 0) {
          data[mantissaEnd++] = '-';
          e = (-e);
        }

        doIt = false;
        g = (e / 100);
        if (g != 0) {
          data[mantissaEnd++] = (char) ('0' + g);
          doIt = true;
          e %= 100;
        }
        g = (e / 10);
        if (doIt || (g != 0)) {
          data[mantissaEnd++] = (char) ('0' + g);
          e %= 10;
        }
        data[mantissaEnd++] = (char) ('0' + e);
      }

      return String.valueOf(data, start, (mantissaEnd - start));
    }

    /** {@inheritDoc} */
    @Override
    protected final __CharFloat clone() {
      __CharFloat r;
      try {
        r = ((__CharFloat) (super.clone()));
        r.m_data = this.m_data.clone();
        return r;
      } catch (final CloneNotSupportedException cnse) {
        RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
            "Error while cloning _CharFloat. This should never happen.", //$NON-NLS-1$
            true, cnse);
        return null;
      }
    }
  }
}
