package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.error.RethrowMode;

/** a floating point number encoded as a character string */
final class _CharFloat implements Cloneable {

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
  _CharFloat(final String string) {
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
          res = '0';
        } else {
          if (res < '0') {
            res = '9';
          } else {
            end = (i + 1);
            break add;
          }
        }
      }

      if (carry < 0) {
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

    return String.valueOf(data, start, mantissaEnd - start);
  }

  /** {@inheritDoc} */
  @Override
  protected final _CharFloat clone() {
    _CharFloat r;
    try {
      r = ((_CharFloat) (super.clone()));
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
