package org.optimizationBenchmarking.utils.text.transformations;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * An implementation of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * that transforms the input text to XML entities where necessary.
 * </p>
 */
class _XMLTransformedTextOutput extends _TransformedTextOutput {

  /** transform the character */
  private static final int MODE_PASS_THROUGH = 0;

  /** ignore the character */
  private static final int MODE_IGNORE = (_XMLTransformedTextOutput.MODE_PASS_THROUGH + 1);

  /** transform the character into a space */
  private static final int MODE_TO_SPACE = (_XMLTransformedTextOutput.MODE_IGNORE + 1);

  /** transform the character */
  private static final int MODE_TRANSFORM = (_XMLTransformedTextOutput.MODE_TO_SPACE + 1);

  /** the non-breaking space */
  private static final char[] NBSP = { '&', 'n', 'b', 's', 'p', ';' };

  /** the lookup */
  private static final char[] LOOKUP = { '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  /** the default replacement */
  private static final char[] REPLACE = { '&', '#', 'x', 0, 0, 0, 0, ';' };

  /** the table of characters that can directly be passed on */
  private static final int[] MODES;

  static {
    final int[] list;

    list = new int[128];

    Arrays.fill(list, _XMLTransformedTextOutput.MODE_TRANSFORM);
    Arrays.fill(list, 0, 32, _XMLTransformedTextOutput.MODE_IGNORE);
    list[' '] = _XMLTransformedTextOutput.MODE_PASS_THROUGH;
    list['\n'] = _XMLTransformedTextOutput.MODE_PASS_THROUGH;
    list['\t'] = _XMLTransformedTextOutput.MODE_TO_SPACE;

    Arrays.fill(list, '(', ';',
        _XMLTransformedTextOutput.MODE_PASS_THROUGH);
    Arrays.fill(list, '?', ('_' + 1),
        _XMLTransformedTextOutput.MODE_PASS_THROUGH);
    Arrays.fill(list, 'a', 126,
        _XMLTransformedTextOutput.MODE_PASS_THROUGH);
    list['!'] = _XMLTransformedTextOutput.MODE_PASS_THROUGH;
    list['$'] = _XMLTransformedTextOutput.MODE_PASS_THROUGH;
    list['%'] = _XMLTransformedTextOutput.MODE_PASS_THROUGH;
    list['='] = _XMLTransformedTextOutput.MODE_PASS_THROUGH;

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
  _XMLTransformedTextOutput(final ITextOutput out) {
    super(out);
    this.m_replace = _XMLTransformedTextOutput.REPLACE.clone();
  }

  /**
   * the character-to-entity conversion
   *
   * @param chr
   *          the char
   * @param dest
   *          the destination
   * @return len the length
   */
  private static final int __makeEntity(final int chr, final char[] dest) {
    boolean go;
    int idx, val;

    idx = 3;
    go = false;

    val = ((chr & 0xf000) >>> 12);
    if (val != 0) {
      dest[idx++] = _XMLTransformedTextOutput.LOOKUP[val];
      go = true;
    }

    val = ((chr & 0xf00) >>> 8);
    if ((val != 0) || go) {
      go = true;
      dest[idx++] = _XMLTransformedTextOutput.LOOKUP[val];
    }

    val = ((chr & 0xf0) >>> 4);
    if ((val != 0) || go) {
      go = true;
      dest[idx++] = _XMLTransformedTextOutput.LOOKUP[val];
    }

    dest[idx++] = _XMLTransformedTextOutput.LOOKUP[chr & 0xf];
    dest[idx++] = ';';
    return idx;
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
      if (currentChar < _XMLTransformedTextOutput.MODES.length) {
        mode = _XMLTransformedTextOutput.MODES[currentChar];
        if (mode == _XMLTransformedTextOutput.MODE_PASS_THROUGH) {
          continue outer;
        }
      } else {
        // omit iso control characters
        if (Character.isISOControl(currentChar)) {
          continue;
        }
        mode = _XMLTransformedTextOutput.MODE_TRANSFORM;
      }

      // ok, we need to do something special
      if (i > currentStart) {
        this.m_out.append(csq, currentStart, i);
      }
      currentStart = (i + 1);

      // convert tabs to spaces
      if (mode == _XMLTransformedTextOutput.MODE_TO_SPACE) {
        this.m_out.append(' ');
        continue;
      }

      // omit ascii control characters
      if (mode == _XMLTransformedTextOutput.MODE_IGNORE) {
        continue;
      }

      // ok, the character is no control char -> transform it!
      this.m_out.append(replace, 0,
          _XMLTransformedTextOutput.__makeEntity(currentChar, replace));
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
      if (currentChar < _XMLTransformedTextOutput.MODES.length) {
        mode = _XMLTransformedTextOutput.MODES[currentChar];
        if (mode == _XMLTransformedTextOutput.MODE_PASS_THROUGH) {
          continue outer;
        }
      } else {
        // omit iso control characters
        if (Character.isISOControl(currentChar)) {
          continue;
        }

        mode = _XMLTransformedTextOutput.MODE_TRANSFORM;
      }

      // ok, we need to do something special
      if (i > currentStart) {
        this.m_out.append(s, currentStart, i);
      }
      currentStart = (i + 1);

      // convert tabs to spaces
      if (mode == _XMLTransformedTextOutput.MODE_TO_SPACE) {
        this.m_out.append(' ');
        continue;
      }

      // omit ascii control characters
      if (mode == _XMLTransformedTextOutput.MODE_IGNORE) {
        continue;
      }

      // ok, the character is no control char -> transform it!
      this.m_out.append(replace, 0,
          _XMLTransformedTextOutput.__makeEntity(currentChar, replace));
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
      if (currentChar < _XMLTransformedTextOutput.MODES.length) {
        mode = _XMLTransformedTextOutput.MODES[currentChar];
        if (mode == _XMLTransformedTextOutput.MODE_PASS_THROUGH) {
          continue outer;
        }
      } else {
        // omit iso control characters
        if (Character.isISOControl(currentChar)) {
          continue;
        }
        mode = _XMLTransformedTextOutput.MODE_TRANSFORM;
      }

      // ok, we need to do something special
      if (i > currentStart) {
        this.m_out.append(ch, currentStart, i);
      }
      currentStart = (i + 1);

      // convert tabs to spaces
      if (mode == _XMLTransformedTextOutput.MODE_TO_SPACE) {
        this.m_out.append(' ');
        continue;
      }

      // omit ascii control characters
      if (mode == _XMLTransformedTextOutput.MODE_IGNORE) {
        continue;
      }

      // ok, the character is no control char -> transform it!
      this.m_out.append(replace, 0,
          _XMLTransformedTextOutput.__makeEntity(currentChar, replace));
    }

    if (currentStart < end) {
      this.m_out.append(ch, currentStart, end);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public AbstractTextOutput append(final char ch) {

    // check character mode, if available
    if (ch < _XMLTransformedTextOutput.MODES.length) {
      switch (_XMLTransformedTextOutput.MODES[ch]) {
        case _XMLTransformedTextOutput.MODE_PASS_THROUGH: {
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
      }
    } else {
      if (Character.isISOControl(ch)) {
        return this;
      }
    }

    // ok, the character is no control char -> transform it!
    this.m_out.append(this.m_replace, 0,
        _XMLTransformedTextOutput.__makeEntity(ch, this.m_replace));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void appendNonBreakingSpace() {
    this.m_out.append(_XMLTransformedTextOutput.NBSP);
  }
}
