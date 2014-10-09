package org.optimizationBenchmarking.utils.text;

import java.util.List;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The set of sequence types. */
public enum ESequenceMode {

  /** A simple, comma-separated sequence {@code "a, b, c"}. */
  COMMA(null, null),

  /** A sequence of the type {@code "a, b, and c"}. */
  AND(null, new char[] { 'a', 'n', 'd' }),

  /** A sequence of the type {@code "a, b, or c"}. */
  OR(null, new char[] { 'o', 'r' }),

  /** A sequence of the type {@code "either a, b, or c"}. */
  EITHER_OR(new char[] { 'e', 'i', 't', 'h', 'e', 'r' }, OR.m_end),

  /** A sequence of the type {@code "neither a, b, nor c"}. */
  NEITHER_NOR(new char[] { 'n', 'e', 'i', 't', 'h', 'e', 'r' },
      new char[] { 'n', 'o', 'r' }),

  /** A sequence of the type {@code "from a to c"}. */
  FROM_TO(new char[] { 'f', 'r', 'o', 'm' }, new char[] { 't', 'o' }),

  /**
   * A sequence which consists of at most three elements concatenated in
   * {@link #AND} style. If there are more than three elements, then only
   * one element is printed, followed by {@code et al.}.
   */
  ET_AL(null, AND.m_end) {

    /** {@inheritDoc} */
    @Override
    public void appendSequence(final ETextCase textCase,
        final List<?> data,
        final boolean connectLastElementWithNonBreakableSpace,
        final ITextOutput dest) {
      final int s;

      s = data.size();
      if (s > 3) {
        ESequenceMode.__append(data.get(0), true, true, textCase, dest);
        dest.append(ESequenceMode.ET_AL_);
      } else {
        super.appendSequence(textCase, data,
            connectLastElementWithNonBreakableSpace, dest);
      }
    }
  };

  /** the et al */
  static final char[] ET_AL_ = { 'e', 't', ' ', 'a', 'l', '.' };

  /** the start string, or {@code null} if none is needed */
  private final char[] m_start;

  /** the end string, or {@code null} if none is needed */
  private final char[] m_end;

  /**
   * create the sequence type
   * 
   * @param start
   *          the start string, or {@code null} if none is needed
   * @param end
   *          the end string, or {@code null} if none is needed
   */
  ESequenceMode(final char[] start, final char[] end) {
    this.m_start = start;
    this.m_end = end;
  }

  /**
   * Append an object to a text output
   * 
   * @param textCase
   *          the text case
   * @param first
   *          is the object the first one in the sequence?
   * @param last
   *          is the object the last one in the sequence
   * @param o
   *          the object
   * @param dest
   *          the destination
   */
  static final void __append(final Object o, final boolean first,
      final boolean last, final ETextCase textCase, final ITextOutput dest) {
    final String s;
    final int l;
    int i, j;
    char lc, uc;

    if (o instanceof ISequenceable) {
      ((ISequenceable) o).toSequence(first, last, textCase, dest);
      return;
    }

    if ((textCase == null) || (textCase == ETextCase.IN_SENTENCE)) {
      dest.append(o);
      return;
    }

    s = String.valueOf(o);
    l = s.length();
    j = 0;
    do {
      i = j;
      if (textCase == ETextCase.AT_SENTENCE_START) {
        j = l;
      } else {
        j = s.indexOf(' ', i);
        if (j < 0) {
          j = l;
        } else {
          j++;
        }
      }

      lc = s.charAt(i);
      uc = textCase.adjustCaseOfFirstCharInWord(lc);
      if (lc != uc) {
        dest.append(uc);
        dest.append(s, (i + 1), j);
      } else {
        if ((i <= 0) && (j >= l)) {
          dest.append(s);
        } else {
          dest.append(s, i, j);
        }
      }

    } while (j < l);

  }

  /**
   * Append a sequence to this text output.
   * 
   * @param textCase
   *          the text case of the first sequence element
   * @param data
   *          the collection providing the sequence elements
   * @param connectLastElementWithNonBreakableSpace
   *          {@code true} if the last element should be connected with a
   *          non-breakable space, {@code false} if normal spaces can be
   *          used
   * @param dest
   *          the destination text output
   */
  public void appendSequence(final ETextCase textCase, final List<?> data,
      final boolean connectLastElementWithNonBreakableSpace,
      final ITextOutput dest) {
    final int s, s2;
    int i;
    char[] t;
    ETextCase useCase;
    char l, u;

    s = data.size();
    if (s <= 0) {
      return;
    }

    if (s <= 1) {
      ESequenceMode.__append(data.get(0), true, true, textCase, dest);
      return;
    }

    useCase = textCase;
    t = this.m_start;
    if (t != null) {
      app: {
        if ((useCase == ETextCase.AT_SENTENCE_START)
            || (useCase == ETextCase.AT_TITLE_START)) {
          l = t[0];
          u = useCase.adjustCaseOfFirstCharInWord(l);
          if (l != u) {
            dest.append(u);
            dest.append(t, 1, t.length);
            break app;
          }
        }

        dest.append(t);
      }
      dest.append(' ');
      useCase = useCase.nextCase();
    }
    ESequenceMode.__append(data.get(0), true, false, useCase, dest);

    s2 = (s - 1);
    for (i = 1; i < s2; i++) {
      dest.append(',');
      dest.append(' ');
      useCase = useCase.nextCase();
      ESequenceMode.__append(data.get(i), false, false, useCase, dest);
    }

    if (i > 1) {
      dest.append(',');
    }
    dest.append(' ');
    if (this.m_end != null) {
      useCase = useCase.nextCase();
      dest.append(this.m_end);
      if (connectLastElementWithNonBreakableSpace) {
        dest.appendNonBreakingSpace();
      } else {
        dest.append(' ');
      }
    }

    useCase = useCase.nextCase();
    ESequenceMode.__append(data.get(s2), false, true, useCase, dest);
  }
}
