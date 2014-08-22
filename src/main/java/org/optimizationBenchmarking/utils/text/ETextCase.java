package org.optimizationBenchmarking.utils.text;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A set of text case modes.
 */
public enum ETextCase {

  /**
   * output should be written as normal part inside a normal text, as part
   * of a sentence
   */
  IN_SENTENCE(null),

  /** the output marks the beginning of a sentence */
  AT_SENTENCE_START(IN_SENTENCE) {

    /** {@inheritDoc} */
    @Override
    public final char adjustCaseOfFirstCharInWord(final char ch) {
      return ETextCase._upper(ch);
    }
  },

  /** the output is part of a title */
  IN_TITLE(null) {

    /** {@inheritDoc} */
    @Override
    public final char adjustCaseOfFirstCharInWord(final char ch) {
      return ETextCase._title(ch);
    }
  },

  /** the output is the start of a title */
  AT_TITLE_START(IN_TITLE) {

    /** {@inheritDoc} */
    @Override
    public final char adjustCaseOfFirstCharInWord(final char ch) {
      return ETextCase._title(ch);
    }
  };

  /** the next text case */
  private final ETextCase m_next;

  /**
   * create the text case
   * 
   * @param next
   *          the next case
   */
  ETextCase(final ETextCase next) {
    this.m_next = ((next != null) ? next : this);
  }

  /**
   * Obtain the text case for the next word
   * 
   * @return the text case for the next word
   */
  public final ETextCase nextCase() {
    return this.m_next;
  }

  /**
   * Adjust the case of the first character in a word
   * 
   * @param ch
   *          the first character of a word
   * @return the character as it can be printed
   */
  public char adjustCaseOfFirstCharInWord(final char ch) {
    return ch;
  }

  /**
   * get the title case of a character
   * 
   * @param ch
   *          the character
   * @return the title case of that character
   */
  static final char _title(final char ch) {
    int x;
    char y;

    x = Character.toTitleCase((int) ch);
    if ((x >= Character.MIN_VALUE) && (x <= Character.MAX_VALUE)) {
      y = ((char) x);
      if (Character.isDefined(y)) {
        return y;
      }
    }

    return ETextCase._upper(ch);
  }

  /**
   * get the upper case of a character
   * 
   * @param ch
   *          the character
   * @return the upper case of that character
   */
  static final char _upper(final char ch) {
    int x;
    char y;

    x = Character.toUpperCase((int) ch);
    if ((x >= Character.MIN_VALUE) && (x <= Character.MAX_VALUE)) {
      y = ((char) x);
      if (Character.isDefined(y)) {
        return y;
      }
    }

    return ch;
  }

  /**
   * Append a single word to the given text output
   * 
   * @param word
   *          the word to append
   * @param textOut
   *          the text output
   */
  public final void appendWord(final String word, final ITextOutput textOut) {
    final char c, u;
    final int l;

    l = word.length();
    if (l > 0) {
      c = word.charAt(0);
      u = this.adjustCaseOfFirstCharInWord(c);
      if (c != u) {
        textOut.append(u);
        textOut.append(word, 1, l);
      } else {
        textOut.append(word);
      }
    }
  }

  /**
   * Append a single word to the given text output
   * 
   * @param word
   *          the word to append
   * @param textOut
   *          the text output
   */
  public final void appendWord(final char[] word, final ITextOutput textOut) {
    final char c, u;
    final int l;

    l = word.length;
    if (l > 0) {
      c = word[0];
      u = this.adjustCaseOfFirstCharInWord(c);
      if (c != u) {
        textOut.append(u);
        textOut.append(word, 1, l);
      } else {
        textOut.append(word);
      }
    }
  }
}
