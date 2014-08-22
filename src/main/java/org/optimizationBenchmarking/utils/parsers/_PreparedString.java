package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.charset.Char;
import org.optimizationBenchmarking.utils.text.charset.Characters;
import org.optimizationBenchmarking.utils.text.charset.EnclosureEnd;

/** an internal class to hold a prepared string */
final class _PreparedString {

  /** the string */
  private String m_string;

  /** a divisor: -1 for negative/false */
  private int m_div;

  /** the state */
  private int m_state;

  /**
   * create the prepared string
   * 
   * @param s
   *          the string
   */
  _PreparedString(final String s) {
    super();
    this.m_string = s;
    this.m_div = 1;
  }

  /**
   * get a boolean return value
   * 
   * @param b
   *          the boolean to be formatted
   * @return the return value
   */
  final boolean getReturn(final boolean b) {
    return ((this.m_div < 0) ? (!b) : b);
  }

  /**
   * get an int return value
   * 
   * @param b
   *          the int to be formatted
   * @return the return value
   */
  final int getReturn(final int b) {
    switch (this.m_div) {
      case (-1): {
        return (-b);
      }
      case 0:
      case 1: {
        return b;
      }
      default: {
        return (b / this.m_div);
      }
    }
  }

  /**
   * get an char return value
   * 
   * @param b
   *          the char to be formatted
   * @return the return value
   */
  final char getReturn(final char b) {
    if ((this.m_div < 0) || (this.m_div > 1)) {
      throw new IllegalArgumentException();
    }
    return b;
  }

  /**
   * get a long return value
   * 
   * @param b
   *          the long to be formatted
   * @return the return value
   */
  final long getReturn(final long b) {
    switch (this.m_div) {
      case (-1): {
        return (-b);
      }
      case 0:
      case 1: {
        return b;
      }
      default: {
        return (b / this.m_div);
      }
    }
  }

  /**
   * get a double return value
   * 
   * @param b
   *          the double to be formatted
   * @return the return value
   */
  final double getReturn(final double b) {
    switch (this.m_div) {
      case (-1): {
        return (-b);
      }
      case 0:
      case 1: {
        return b;
      }
      default: {
        return (b / this.m_div);
      }
    }
  }

  /**
   * Get the next string
   * 
   * @return the next string
   */
  @SuppressWarnings("fallthrough")
  final String next() {
    final String oldS;
    int div;
    int newState, i, len, charsLen, first;
    String newS;
    char ch;
    char[] chars;
    boolean breakOuter;
    Char chr;
    EnclosureEnd ee;

    newS = this.m_string;
    if (newS == null) {
      return null;
    }

    oldS = newS;
    newState = this.m_state;

    switch (newState) {
    // we just return the original string
      case 0: {
        newState++;
        break;
      }

      // the trimmed version of the original string
      case 1: {
        newS = TextUtils.prepare(newS);
        if (newS == null) {
          return null;
        }

        newState++;
        if (newS != oldS) {
          break;
        }
      }

      // a version of the original string with all special characters
      // expanded
      case 2: {
        newS = TextUtils.prepare(StringParser.INSTANCE.parseString(newS));
        if (newS == null) {
          return null;
        }

        newState++;
        if (newS != oldS) {
          break;
        }
      }

      // now strip all div information off
      case 3: {
        // so now we are going to remove signs - from now on, this.m_signed
        // becomes relevant

        div = 1;
        len = newS.length();
        i = 0;
        ee = null;

        outerLooper: for (; i < len;) {
          looperA: for (; i < len; i++) {
            ch = newS.charAt(i);
            switch (ch) {

              case 0x21:// !
              case 0x2d:// -
              case 0x5e:// ^
              case 0x7e:// ~
              case 0xac:// logical not
              case 0x2010:// -
              case 0x2011:// -
              case 0x2012:// -
              case 0x2013:// -
              case 0x2014:// -
              case 0x2015:// -
              case 0x223c:// ~
              case 0x223d:// ~
              { // flip the value's div
                div = (-div);
                continue looperA; // do next iteration
              }

              case 0x2b:// +
              {
                // a '+' has no impact
                continue looperA; // do next iteration
              }

              default: {
                if (ch > 32) {
                  // check if the character is a quotation or brace
                  // character
                  chr = Characters.CHARACTERS.getChar(ch);
                  if (chr instanceof EnclosureEnd) {
                    ee = ((EnclosureEnd) chr);
                    if (ee.isOpening()) {
                      break;
                    }
                    ee = null;
                  }

                  // so we have a non-whitespace character: leave the loop,
                  // do
                  // not touch character
                  break looperA;
                }
                continue looperA;
                // so this is whitespace - do nothing
              }
            }

            // so we found a potentially removable enclosing sequence,
            // otherwise we would have continued the loop
            ch = newS.charAt(len - 1);
            if (/* (ee != null) && *///
            (ee.canEndWith(Characters.CHARACTERS.getChar(ch)))) {
              ee = null;
              // yes, the other hand has the proper ending of the enclosing
              // sequence
              len--; // remove last character (end of sequence)

              while ((len > i) && (newS.charAt(len) <= 32)) {
                len--;// strip trailing white spaces
              }// we are done here
            } else {
              // ok, we cannot strip the leading character as there is no
              // trailing match
              break looperA;
            }
          }

          breakOuter = true; // quit outer loop by default

          // now check for percent symbols etc. at the end...
          looperB: for (; i < len; len--) {
            ch = newS.charAt(len - 1);
            switch (ch) {
              case 0x25: { // percent == 1e-2
                div *= 100;
                breakOuter = false;
                continue looperB;
              }

              case 0xb5: {// mico == 1e-6
                div *= 1000000;
                breakOuter = false;
                continue looperB;
              }
              case 0x3bc: {// mico == 1e-6
                div *= 1000000;
                breakOuter = false;
                continue looperB;
              }
              case 0x2030: {// per mille = 1e-3
                div *= 1000;
                breakOuter = false;
                continue looperB;
              }
              case 0x2031: { // per 10 mille = 1e-4
                div *= 10000;
                breakOuter = false;
                continue looperB;
              }

              default: {
                if (ch > 32) {
                  break looperB;
                }
                continue looperB;
              }
            }
          }

          if (breakOuter) {
            break outerLooper;
          }
        }

        if (i >= len) {
          return null;
        }
        newS = newS.substring(i, len);
        this.m_div = div;

        // check the state
        newState++;
        if (newS != oldS) {
          break;
        }
      }

      // finally, it is time to remove underscores and inner spaces
      case 4: {
        chars = null;
        charsLen = 0;
        first = (0);
        len = newS.length();

        for (i = 0; i < len; i++) {
          ch = newS.charAt(i);
          if ((ch <= 32) || (ch == '_')) {
            if (chars == null) {
              chars = new char[len];
            }

            if (first < i) {
              newS.getChars(first, i, chars, charsLen);
              charsLen += (i - first);
            }
            first = (i + 1);
          }
        }

        if (chars == null) {
          return null;
        }

        if (first < i) {
          newS.getChars(first, i, chars, charsLen);
          charsLen += (i - first);
        }

        if (charsLen <= 0) {
          return null;
        }
        newS = String.valueOf(chars, 0, charsLen);

        // check the state
        newState++;
        break;
      }

      default: {
        newS = null;
      }
    }

    // store
    this.m_string = newS;
    this.m_state = newState;

    return newS;
  }

  /**
   * get the base identified by the character
   * 
   * @param chr
   *          the identifier
   * @return the base, or {@code 0} if none
   */
  static final int _getBase(final int chr) {
    switch (chr) {
      case 'B':
      case 'b': {// binary
        return 2;
      }
      case 'D':
      case 'd': {// decimal
        return 10;
      }
      case 'O':
      case 'o': {// octal
        return 8;
      }
      case 'H':
      case 'h':
      case 'X':
      case 'x': {// hexadecimal
        return 16;
      }
      default: {
        return 0;
      }
    }
  }
}
