package org.optimizationBenchmarking.utils.text.transformations;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * An implementation of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * that transforms the input text to Java and JavaScript entities where
 * necessary.
 * </p>
 */
class _JavaTransformedTextOutput extends _TransformedTextOutput {

  /** transform the character */
  private static final int MODE_PASS_THROUGH = 0;

  /** ignore the character */
  private static final int MODE_IGNORE = (_JavaTransformedTextOutput.MODE_PASS_THROUGH + 1);

  /** transform the character into a space */
  private static final int MODE_TO_SPACE = (_JavaTransformedTextOutput.MODE_IGNORE + 1);

  /** transform the character */
  private static final int MODE_TRANSFORM = (_JavaTransformedTextOutput.MODE_TO_SPACE + 1);

  /** transform the to an escape sequence */
  private static final int MODE_ESCAPE = (_JavaTransformedTextOutput.MODE_TRANSFORM + 1);

  /** the lookup */
  private static final char[] LOOKUP = { '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  /** the default replacement */
  private static final char[] REPLACE = { '\\', 'u', 0, 0, 0, 0, };

  /** the table of characters that can directly be passed on */
  private static final int[] MODES;

  static {
    final int[] list;

    list = new int[128];

    Arrays.fill(list, _JavaTransformedTextOutput.MODE_TRANSFORM);
    Arrays.fill(list, 0, 32, _JavaTransformedTextOutput.MODE_IGNORE);
    list[' '] = _JavaTransformedTextOutput.MODE_PASS_THROUGH;

    Arrays.fill(list, '(', ';',
        _JavaTransformedTextOutput.MODE_PASS_THROUGH);
    Arrays.fill(list, '?', ('_' + 1),
        _JavaTransformedTextOutput.MODE_PASS_THROUGH);
    Arrays.fill(list, 'a', 126,
        _JavaTransformedTextOutput.MODE_PASS_THROUGH);
    list['!'] = _JavaTransformedTextOutput.MODE_PASS_THROUGH;
    list['$'] = _JavaTransformedTextOutput.MODE_PASS_THROUGH;
    list['%'] = _JavaTransformedTextOutput.MODE_PASS_THROUGH;
    list['='] = _JavaTransformedTextOutput.MODE_PASS_THROUGH;

    list['\n'] = _JavaTransformedTextOutput.MODE_ESCAPE;
    list['\t'] = _JavaTransformedTextOutput.MODE_ESCAPE;
    list['\b'] = _JavaTransformedTextOutput.MODE_ESCAPE;
    list['\r'] = _JavaTransformedTextOutput.MODE_ESCAPE;
    list['\f'] = _JavaTransformedTextOutput.MODE_ESCAPE;
    list['\''] = _JavaTransformedTextOutput.MODE_ESCAPE;
    list['\"'] = _JavaTransformedTextOutput.MODE_ESCAPE;
    list['\\'] = _JavaTransformedTextOutput.MODE_ESCAPE;

    MODES = list;
  }

  /** the replacement buffer */
  private final char[] m_replace;

  /**
   * Create
   *
   * @param out
   *          the text output to wrap around
   */
  _JavaTransformedTextOutput(final ITextOutput out) {
    super(out);
    this.m_replace = _JavaTransformedTextOutput.REPLACE.clone();
  }

  /**
   * the character-to-entity conversion
   *
   * @param chr
   *          the char
   * @param dest
   *          the destination
   */
  private static final void __makeUnicodeRef(final int chr,
      final char[] dest) {
    dest[2] = _JavaTransformedTextOutput.LOOKUP[((chr & 0xf000) >>> 12)];
    dest[3] = _JavaTransformedTextOutput.LOOKUP[((chr & 0xf00) >>> 8)];
    dest[4] = _JavaTransformedTextOutput.LOOKUP[((chr & 0xf0) >>> 4)];
    dest[5] = _JavaTransformedTextOutput.LOOKUP[chr & 0xf];
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    final char[] replace;
    int i, currentStart, currentChar, mode;

    // setup
    currentStart = start;
    replace = this.m_replace;

    // iterate over all characters in the character array data
    outer: for (i = currentStart; i < end; i++) {
      currentChar = csq.charAt(i);

      // check character mode, if available
      if (currentChar < _JavaTransformedTextOutput.MODES.length) {
        mode = _JavaTransformedTextOutput.MODES[currentChar];
        if (mode == _JavaTransformedTextOutput.MODE_PASS_THROUGH) {
          continue outer;
        }
      } else {
        // omit iso control characters
        if (Character.isISOControl(currentChar)) {
          continue;
        }
        mode = _JavaTransformedTextOutput.MODE_TRANSFORM;
      }

      // ok, we need to do something special
      if (i > currentStart) {
        this.m_out.append(csq, currentStart, i);
      }
      currentStart = (i + 1);

      switch (mode) {
      // convert tabs to spaces
        case _JavaTransformedTextOutput.MODE_TO_SPACE: {
          this.m_out.append(' ');
          continue outer;
        }

        // omit ascii control characters
        case _JavaTransformedTextOutput.MODE_IGNORE: {
          continue outer;
        }

        // escape escape sequences
        case _JavaTransformedTextOutput.MODE_ESCAPE: {
          this.m_out.append('\\');
          switch (currentChar) {
            case '\n': {
              currentChar = 'n';
              break;
            }
            case '\r': {
              currentChar = 'r';
              break;
            }
            case '\f': {
              currentChar = 'f';
              break;
            }
            case '\b': {
              currentChar = 'b';
              break;
            }
            case '\t': {
              currentChar = 't';
              break;
            }
            case '\'': {
              currentChar = '\'';
              break;
            }
            case '"': {
              currentChar = '"';
              break;
            }
            case '\\': {
              currentChar = '\\';
              break;
            }
            default: {
              throw new IllegalStateException();
            }
          }
          this.m_out.append(currentChar);
          continue outer;
        }

        default: {
          // ok, the character is no control char -> transform it!
          _JavaTransformedTextOutput
          .__makeUnicodeRef(currentChar, replace);
          this.m_out.append(replace);
        }
      }
    }

    if (currentStart < end) {
      this.m_out.append(csq, currentStart, end);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void append(final String s, final int start, final int end) {
    final char[] replace;
    int i, currentStart, currentChar, mode;

    // setup
    currentStart = start;
    replace = this.m_replace;

    // iterate over all characters in the character array data
    outer: for (i = currentStart; i < end; i++) {
      currentChar = s.charAt(i);

      // check character mode, if available
      if (currentChar < _JavaTransformedTextOutput.MODES.length) {
        mode = _JavaTransformedTextOutput.MODES[currentChar];
        if (mode == _JavaTransformedTextOutput.MODE_PASS_THROUGH) {
          continue outer;
        }
      } else {
        // omit iso control characters
        if (Character.isISOControl(currentChar)) {
          continue;
        }

        mode = _JavaTransformedTextOutput.MODE_TRANSFORM;
      }

      // ok, we need to do something special
      if (i > currentStart) {
        this.m_out.append(s, currentStart, i);
      }
      currentStart = (i + 1);

      switch (mode) {
      // convert tabs to spaces
        case _JavaTransformedTextOutput.MODE_TO_SPACE: {
          this.m_out.append(' ');
          continue outer;
        }

        // omit ascii control characters
        case _JavaTransformedTextOutput.MODE_IGNORE: {
          continue outer;
        }

        // escape escape sequences
        case _JavaTransformedTextOutput.MODE_ESCAPE: {
          this.m_out.append('\\');
          switch (currentChar) {
            case '\n': {
              currentChar = 'n';
              break;
            }
            case '\r': {
              currentChar = 'r';
              break;
            }
            case '\f': {
              currentChar = 'f';
              break;
            }
            case '\b': {
              currentChar = 'b';
              break;
            }
            case '\t': {
              currentChar = 't';
              break;
            }
            case '\'': {
              currentChar = '\'';
              break;
            }
            case '"': {
              currentChar = '"';
              break;
            }
            case '\\': {
              currentChar = '\\';
              break;
            }
            default: {
              throw new IllegalStateException();
            }
          }
          this.m_out.append(currentChar);
          continue outer;
        }

        default: {
          // ok, the character is no control char -> transform it!
          _JavaTransformedTextOutput
          .__makeUnicodeRef(currentChar, replace);
          this.m_out.append(replace);
        }
      }
    }

    if (currentStart < end) {
      this.m_out.append(s, currentStart, end);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void append(final char[] ch, final int start, final int end) {
    final char[] replace;
    int i, currentStart, currentChar, mode;

    // setup
    currentStart = start;
    replace = this.m_replace;

    // iterate over all characters in the character array data
    outer: for (i = currentStart; i < end; i++) {
      currentChar = ch[i];

      // check character mode, if available
      if (currentChar < _JavaTransformedTextOutput.MODES.length) {
        mode = _JavaTransformedTextOutput.MODES[currentChar];
        if (mode == _JavaTransformedTextOutput.MODE_PASS_THROUGH) {
          continue outer;
        }
      } else {
        // omit iso control characters
        if (Character.isISOControl(currentChar)) {
          continue;
        }
        mode = _JavaTransformedTextOutput.MODE_TRANSFORM;
      }

      // ok, we need to do something special
      if (i > currentStart) {
        this.m_out.append(ch, currentStart, i);
      }
      currentStart = (i + 1);

      switch (mode) {
      // convert tabs to spaces
        case _JavaTransformedTextOutput.MODE_TO_SPACE: {
          this.m_out.append(' ');
          continue outer;
        }

        // omit ascii control characters
        case _JavaTransformedTextOutput.MODE_IGNORE: {
          continue outer;
        }

        // escape escape sequences
        case _JavaTransformedTextOutput.MODE_ESCAPE: {
          this.m_out.append('\\');
          switch (currentChar) {
            case '\n': {
              currentChar = 'n';
              break;
            }
            case '\r': {
              currentChar = 'r';
              break;
            }
            case '\f': {
              currentChar = 'f';
              break;
            }
            case '\b': {
              currentChar = 'b';
              break;
            }
            case '\t': {
              currentChar = 't';
              break;
            }
            case '\'': {
              currentChar = '\'';
              break;
            }
            case '"': {
              currentChar = '"';
              break;
            }
            case '\\': {
              currentChar = '\\';
              break;
            }
            default: {
              throw new IllegalStateException();
            }
          }
          this.m_out.append(currentChar);
          continue outer;
        }

        default: {
          // ok, the character is no control char -> transform it!
          _JavaTransformedTextOutput
          .__makeUnicodeRef(currentChar, replace);
          this.m_out.append(replace);
        }
      }
    }

    if (currentStart < end) {
      this.m_out.append(ch, currentStart, end);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public AbstractTextOutput append(final char ch) {
    final char currentChar;

    // check character mode, if available
    if (ch < _JavaTransformedTextOutput.MODES.length) {
      switch (_JavaTransformedTextOutput.MODES[ch]) {
        case _JavaTransformedTextOutput.MODE_PASS_THROUGH: {
          this.m_out.append(ch);
          return this;
        }
        case MODE_IGNORE: {
          return this;
        }
        case MODE_TO_SPACE: {
          this.m_out.append(' ');
          return this;
        }

        case _JavaTransformedTextOutput.MODE_ESCAPE: {
          this.m_out.append('\\');
          switch (ch) {
            case '\n': {
              currentChar = 'n';
              break;
            }
            case '\r': {
              currentChar = 'r';
              break;
            }
            case '\f': {
              currentChar = 'f';
              break;
            }
            case '\b': {
              currentChar = 'b';
              break;
            }
            case '\t': {
              currentChar = 't';
              break;
            }
            case '\'': {
              currentChar = '\'';
              break;
            }
            case '"': {
              currentChar = '"';
              break;
            }
            case '\\': {
              currentChar = '\\';
              break;
            }
            default: {
              throw new IllegalStateException();
            }
          }
          this.m_out.append(currentChar);
          return this;
        }
      }
    } else {
      if (Character.isISOControl(ch)) {
        return this;
      }
    }

    // ok, the character is no control char -> transform it!
    _JavaTransformedTextOutput.__makeUnicodeRef(ch, this.m_replace);
    this.m_out.append(this.m_replace);
    return this;
  }
}
