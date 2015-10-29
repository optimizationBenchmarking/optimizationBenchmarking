package org.optimizationBenchmarking.utils.text.transformations;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An implementation of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * which transforms text according to a lookup table and writes it to its
 * output.
 */
class _LookupTransformedTextOutput extends _TransformedTextOutput {

  /** the lookup data */
  private final char[][] m_data;

  /** the characters that can be written directly to the output */
  private final byte[] m_state;

  /**
   * create the transformed text output
   *
   * @param out
   *          the wrapped output
   * @param data
   *          the lookup data
   * @param state
   *          the character state
   */
  _LookupTransformedTextOutput(final ITextOutput out, final char[][] data,
      final byte[] state) {
    super(out);
    this.m_data = data;
    this.m_state = state;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public AbstractTextOutput append(final CharSequence csq, final int start,
      final int end) {
    final byte[] state;
    char[] replace;
    char currentChar;
    int i, currentStart;

    // setup
    state = this.m_state;
    currentStart = start;
    replace = null;

    // iterate over all characters in the character array data
    outer: for (i = currentStart; i < end; i++) {
      currentChar = csq.charAt(i);

      // check if we need to do something with this character
      resolver: {

        // is there a state information for this character?
        if (currentChar < state.length) {

          // the state array tells us what to do
          switch (state[currentChar]) {

            case LookupCharTransformer.STATE_DIRECT: {
              // the character can be copied directly:
              // we continue the loop
              continue outer;
            }

            case LookupCharTransformer.STATE_OMIT: {
              // the character must be omitted:
              // set the replacement to null and write the previous data
              replace = null;
              break resolver;
            }

            case LookupCharTransformer.STATE_TO_SPACE: {
              // the character must be transformed to a space:
              replace = LookupCharTransformer.TO_SPACE;
              break resolver;
            }
          }
        }

        replace = this.__resolve(currentChar);
        if (replace == null) {
          this._onUnknown(currentChar, csq, start, end); // throw an error
        }
      }

      // write data before the current char
      if (i > currentStart) {
        this.m_out.append(csq, currentStart, i);
      }

      if (replace != null) {
        // if transformation found, write transformation
        this.m_out.append(replace, 1, replace.length);
      }

      // continue
      currentStart = (i + 1);
    }

    if (currentStart < end) {
      this.m_out.append(csq, currentStart, end);
    }

    return this;
  }

  /**
   * resolve a given character
   *
   * @param ch
   *          the character to resolve
   * @return the resolved sequence
   */
  private final char[] __resolve(final char ch) {
    final char[][] transform;
    char[] replace;
    char midChar;
    int low, high, mid;

    transform = this.m_data;
    low = 0;
    high = (transform.length - 1);
    if (ch > high) {
      mid = (high >>> 1);
    } else {
      // if we are lucky, we find the transformation in one step
      mid = ch;
    }

    while (low <= high) {
      replace = transform[mid];
      midChar = replace[0];

      if (midChar < ch) {
        low = (mid + 1);
      } else {
        if (midChar > ch) {
          high = (mid - 1);
        } else {
          return replace;
        }
      }

      mid = ((low + high) >>> 1);
    }

    // no transformation found: delegate
    return null;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public AbstractTextOutput append(final char c) {
    final char[] replace;
    switch (this.m_state[c]) {

      case LookupCharTransformer.STATE_DIRECT: {
        this.m_out.append(c);
        return this;
      }

      case LookupCharTransformer.STATE_OMIT: {
        return this;
      }

      case LookupCharTransformer.STATE_TO_SPACE: {
        this.m_out.append(' ');
        return this;
      }
    }

    replace = this.__resolve(c);
    if (replace == null) {
      this._onUnknown(c, null, (-1), (-1));// throw an error
    } else {
      this.m_out.append(replace, 1, replace.length);
    }
    return this;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public void append(final String s, final int start, final int end) {
    final byte[] state;
    char[] replace;
    char currentChar;
    int i, currentStart;

    // setup
    state = this.m_state;
    currentStart = start;
    replace = null;

    // iterate over all characters in the character array data
    outer: for (i = currentStart; i < end; i++) {
      currentChar = s.charAt(i);

      // check if we need to do something with this character
      resolver: {

        // is there a state information for this character?
        if (currentChar < state.length) {

          // the state array tells us what to do
          switch (state[currentChar]) {

            case LookupCharTransformer.STATE_DIRECT: {
              // the character can be copied directly:
              // we continue the loop
              continue outer;
            }

            case LookupCharTransformer.STATE_OMIT: {
              // the character must be omitted:
              // set the replacement to null and write the previous data
              replace = null;
              break resolver;
            }

            case LookupCharTransformer.STATE_TO_SPACE: {
              // the character must be transformed to a space:
              replace = LookupCharTransformer.TO_SPACE;
              break resolver;
            }
          }
        }
        replace = this.__resolve(currentChar);
        if (replace == null) {
          this._onUnknown(currentChar, s, start, end);// throw an error
        }
      }

      // write data before the current char
      if (i > currentStart) {
        this.m_out.append(s, currentStart, i);
      }

      if (replace != null) {
        // if transformation found, write transformation
        this.m_out.append(replace, 1, replace.length);
      }

      // continue
      currentStart = (i + 1);
    }

    if (currentStart < end) {
      this.m_out.append(s, currentStart, end);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public void append(final char[] chars, final int start, final int end) {
    final byte[] state;
    char[] replace;
    char currentChar;
    int i, currentStart;

    // setup
    state = this.m_state;
    currentStart = start;
    replace = null;

    // iterate over all characters in the character array data
    outer: for (i = currentStart; i < end; i++) {
      currentChar = chars[i];

      // check if we need to do something with this character
      resolver: {

        // is there a state information for this character?
        if (currentChar < state.length) {

          // the state array tells us what to do
          switch (state[currentChar]) {

            case LookupCharTransformer.STATE_DIRECT: {
              // the character can be copied directly:
              // we continue the loop
              continue outer;
            }

            case LookupCharTransformer.STATE_OMIT: {
              // the character must be omitted:
              // set the replacement to null and write the previous data
              replace = null;
              break resolver;
            }

            case LookupCharTransformer.STATE_TO_SPACE: {
              // the character must be transformed to a space:
              replace = LookupCharTransformer.TO_SPACE;
              break resolver;
            }
          }
        }
        replace = this.__resolve(currentChar);
        if (replace == null) {
          this._onUnknown(currentChar, chars, start, end); // throw error
        }
      }

      // write data before the current char
      if (i > currentStart) {
        this.m_out.append(chars, currentStart, i);
      }

      if (replace != null) {
        // if transformation found, write transformation
        this.m_out.append(replace, 1, replace.length);
      }

      // continue
      currentStart = (i + 1);
    }

    if (currentStart < end) {
      this.m_out.append(chars, currentStart, end);
    }
  }
}
