package org.optimizationBenchmarking.utils.text;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.tokenizers.WordBasedStringIterator;

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

  /** the instances */
  public static final ArraySetView<ETextCase> INSTANCES = new ArraySetView<>(
      ETextCase.values());

  /**
   * Some words which should be ignored in title case, such as articles,
   * conjunctions, and prepositions. See
   * http://www.grammar-monster.com/lessons/capital_letters_title_case.htm
   */
  private static final char[][] IGNORE = {//
  { 'a', },//
      { 'a', 'b', 'o', 'u', 't', },//
      { 'a', 'b', 'o', 'v', 'e', },//
      { 'a', 'c', 'r', 'o', 's', 's', },//
      { 'a', 'f', 't', 'e', 'r', },//
      { 'a', 'g', 'a', 'i', 'n', 's', 't', },//
      { 'a', 'l', 'o', 'n', 'g', },//
      { 'a', 'l', 't', 'h', 'o', 'u', 'g', 'h', },//
      { 'a', 'm', 'o', 'n', 'g', },//
      { 'a', 'n', },//
      { 'a', 'n', 'd', },//
      { 'a', 'r', 'o', 'u', 'n', 'd', },//
      { 'a', 's', },//
      { 'a', 't', },//
      { 'b', 'e', 'c', 'a', 'u', 's', 'e', },//
      { 'b', 'e', 'f', 'o', 'r', 'e', },//
      { 'b', 'e', 'h', 'i', 'n', 'd', },//
      { 'b', 'e', 'l', 'o', 'w', },//
      { 'b', 'e', 'n', 'e', 'a', 't', 'h', },//
      { 'b', 'e', 's', 'i', 'd', 'e', },//
      { 'b', 'e', 't', 'w', 'e', 'e', 'n', },//
      { 'b', 'e', 'y', 'o', 'n', 'd', },//
      { 'b', 'u', 't', },//
      { 'b', 'y', },//
      { 'd', 'o', 'w', 'n', },//
      { 'd', 'u', 'r', 'i', 'n', 'g', },//
      { 'e', 'i', 't', 'h', 'e', 'r', },//
      { 'e', 'x', 'c', 'e', 'p', 't', },//
      { 'f', 'o', 'r', },//
      { 'f', 'r', 'o', 'm', },//
      { 'i', 'f', },//
      { 'i', 'n', },//
      { 'i', 'n', 's', 'i', 'd', 'e', },//
      { 'i', 'n', 't', 'o', },//
      { 'l', 'i', 'k', 'e', },//
      { 'n', 'e', 'a', 'r', },//
      { 'n', 'e', 'i', 't', 'h', 'e', 'r', },//
      { 'n', 'o', 'r', },//
      { 'o', 'f', },//
      { 'o', 'f', 'f', },//
      { 'o', 'n', },//
      { 'o', 'r', },//
      { 'o', 'v', 'e', 'r' },//
      { 's', 'i', 'n', 'c', 'e', },//
      { 's', 'o', },//
      { 's', 'o', 'o', 'n', },//
      { 't', 'h', 'a', 'n', },//
      { 't', 'h', 'a', 't', },//
      { 't', 'h', 'e', },//
      { 't', 'h', 'o', 'u', 'g', 'h', },//
      { 't', 'h', 'r', 'o', 'u', 'g', 'h', },//
      { 't', 'o', },//
      { 't', 'o', 'w', 'a', 'r', 'd', },//
      { 'u', 'n', 'd', 'e', 'r', },//
      { 'u', 'n', 't', 'i', 'l', },//
      { 'u', 'p', },//
      { 'u', 'p', 'o', 'n', },//
      { 'w', 'h', 'e', 'n', },//
      { 'w', 'h', 'e', 'n', 'e', 'v', 'e', 'r', },//
      { 'w', 'h', 'e', 'r', 'e', },//
      { 'w', 'h', 'e', 'r', 'e', 'a', 's', },//
      { 'w', 'h', 'e', 'r', 'e', 'v', 'e', 'r', },//
      { 'w', 'h', 'e', 't', 'h', 'e', 'r', },//
      { 'w', 'h', 'i', 'l', 'e', },//
      { 'w', 'h', 'y', },//
      { 'w', 'i', 't', 'h', },//
      { 'y', 'e', 't', } };

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
   * Append a single word to the given text output. Use this method only if
   * it is clear that there is at most one word in the string {@code word},
   * use {@link #appendWords(String, ITextOutput)} otherwise. The word must
   * be in lower case.
   *
   * @param word
   *          the word to append
   * @param textOut
   *          the text output
   * @return the next text case to use
   */
  public final ETextCase appendWord(final String word,
      final ITextOutput textOut) {
    final char c, u;
    final int l;

    l = word.length();
    if (l <= 0) {
      return this;
    }
    c = word.charAt(0);

    useOrig: {
      if (this != IN_SENTENCE) {
        if ((this != IN_TITLE) || ETextCase.__cantIgnore(word)) {
          u = this.adjustCaseOfFirstCharInWord(c);
          if (c != u) {
            textOut.append(u);
            textOut.append(word, 1, l);
            break useOrig;
          }
        }
      }
      textOut.append(word);
    }

    return this.nextCase();
  }

  /**
   * Compare a word to a character array
   *
   * @param word
   *          the word
   * @param data
   *          the array
   * @return the typical comparison result
   */
  private static final int __compare(final String word, final char[] data) {
    final int len, max;
    char ch1, ch2;
    int i;

    len = word.length();
    max = Math.min(len, data.length);
    for (i = 0; i < max; i++) {
      ch1 = word.charAt(i);
      ch2 = data[i];
      if (ch1 < ch2) {
        return -1;
      }
      if (ch1 > ch2) {
        return 1;
      }
    }
    return ((len < data.length) ? -1 : ((len > data.length) ? 1 : 0));
  }

  /**
   * Compare a word to a character array
   *
   * @param word
   *          the word
   * @param data
   *          the array
   * @return the typical comparison result
   */
  private static final int __compare(final char[] word, final char[] data) {
    final int len, max;
    char ch1, ch2;
    int i;

    len = word.length;
    max = Math.min(len, data.length);
    for (i = 0; i < max; i++) {
      ch1 = word[i];
      ch2 = data[i];
      if (ch1 < ch2) {
        return -1;
      }
      if (ch1 > ch2) {
        return 1;
      }
    }
    return ((len < data.length) ? -1 : ((len > data.length) ? 1 : 0));
  }

  /**
   * check whether a word can be ignored.
   *
   * @param word
   *          the word
   * @return {@code true} if it cannot be ignored inside a title,
   *         {@code false} otherwise
   */
  private static final boolean __cantIgnore(final String word) {
    int low, high, mid, cmp;

    low = 0;
    high = (ETextCase.IGNORE.length - 1);

    while (low <= high) {
      mid = ((low + high) >>> 1);
      cmp = ETextCase.__compare(word, ETextCase.IGNORE[mid]);

      if (cmp > 0) {
        low = (mid + 1);
      } else {
        if (cmp < 0) {
          high = (mid - 1);
        } else {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * check whether a word can be ignored.
   *
   * @param word
   *          the word
   * @return {@code true} if it cannot be ignored inside a title,
   *         {@code false} otherwise
   */
  private static final boolean __cantIgnore(final char[] word) {
    int low, high, mid, cmp;

    low = 0;
    high = (ETextCase.IGNORE.length - 1);

    while (low <= high) {
      mid = ((low + high) >>> 1);
      cmp = ETextCase.__compare(word, ETextCase.IGNORE[mid]);

      if (cmp > 0) {
        low = (mid + 1);
      } else {
        if (cmp < 0) {
          high = (mid - 1);
        } else {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Append a string potentially containing more than one word to the given
   * text output. The words must be in lower case.
   *
   * @param words
   *          the words to append
   * @param textOut
   *          the text output
   * @return the next text case to use
   */
  public final ETextCase appendWords(final String words,
      final ITextOutput textOut) {
    final WordBasedStringIterator it;
    ETextCase t;
    boolean first;

    it = new WordBasedStringIterator(words);
    t = this;
    first = true;

    while (it.hasNext()) {
      if (first) {
        first = false;
      } else {
        textOut.append(' ');
      }
      t = t.appendWord(it.next(), textOut);
    }

    return t;
  }

  /**
   * Append a single word to the given text output. The word must be in
   * lower case.
   *
   * @param word
   *          the word to append
   * @param textOut
   *          the text output
   * @return the next text case to use
   */
  public final ETextCase appendWord(final char[] word,
      final ITextOutput textOut) {
    final char c, u;
    final int l;

    l = word.length;
    if (l <= 0) {
      return this;
    }
    c = word[0];

    useOrig: {
      if (this != IN_SENTENCE) {
        if ((this != IN_TITLE) || ETextCase.__cantIgnore(word)) {
          u = this.adjustCaseOfFirstCharInWord(c);
          if (c != u) {
            textOut.append(u);
            textOut.append(word, 1, l);
            break useOrig;
          }
        }
      }
      textOut.append(word);
    }

    return this.nextCase();
  }

  /**
   * Ensure that the a text case is not {@code null}, return the default
   * text case if it is.
   *
   * @param textCase
   *          the text case
   * @return {@code textCase} if {@code textCase != null},
   *         {@link #IN_SENTENCE} otherwise
   */
  public static final ETextCase ensure(final ETextCase textCase) {
    return ((textCase != null) ? textCase : ETextCase.IN_SENTENCE);
  }
}
