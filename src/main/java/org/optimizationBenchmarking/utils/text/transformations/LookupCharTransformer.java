package org.optimizationBenchmarking.utils.text.transformations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A lookup-table based character transformer: Instances of this class
 * translate unicode characters ({@code char}s) to a (canonical) textual
 * representations. Single chacters can either be omitted (see
 * {@link #STATE_MARK_OMIT}), copied directly (see
 * {@link #STATE_MARK_DIRECT}), transformed to a normal space character
 * (see {@link #STATE_MARK_TO_SPACE}), or replaced by a fixed, pre-defined
 * string (see {@link #STATE_LOOKUP}). The character transformer loads the
 * information what to do from a resource text file.
 */
public class LookupCharTransformer extends CharTransformer {

  /** the default data */
  private static final char[][] DEF_DATA = new char[0][];

  /** the default hyphen */
  private static final char[] DEF_HY = new char[0];

  /** the default character state */
  private static final byte[] DEF_STATE = new byte[0];

  /** a replacement that transforms a character to a space */
  static final char[] TO_SPACE = new char[] { ' ', ' ' };

  /** the character must be looked up */
  static final byte STATE_LOOKUP = 0;

  /** the character can be written directly */
  static final byte STATE_DIRECT = (LookupCharTransformer.STATE_LOOKUP + 1);

  /** the character is omitted, nothing is written to the output */
  static final byte STATE_OMIT = (LookupCharTransformer.STATE_DIRECT + 1);

  /** the character is omitted, nothing is written to the output */
  static final byte STATE_TO_SPACE = (LookupCharTransformer.STATE_OMIT + 1);

  /** the state mark for direct output */
  private static final char STATE_MARK_DIRECT = '+';

  /** the state mark for omitting */
  private static final char STATE_MARK_OMIT = '-';

  /** the state mark for omitting */
  private static final char STATE_MARK_TO_SPACE = '_';
  /** the hyphen */
  private static final String HYPHEN_MARK = "hyphen"; //$NON-NLS-1$

  /** the data */
  final char[][] m_data;

  /** the characters that can be written directly to the output */
  final byte[] m_state;

  /**
   * instantiate
   *
   * @param resource
   *          the name of the resource file
   */
  public LookupCharTransformer(final String resource) {
    super();

    final ArrayList<char[]> al;
    byte[] state;
    int lastDir;
    String s, a, b;
    int i, chrA, chrB, cur, last;
    byte nextState;
    char[] chrs, hyphen;

    al = new ArrayList<>();
    state = new byte[256];
    lastDir = -1;
    hyphen = LookupCharTransformer.DEF_HY;

    // load the transformation description from a file
    try (final InputStream is = this.getClass().getResourceAsStream(
        resource)) {
      try (final InputStreamReader isr = new InputStreamReader(is)) {
        try (final BufferedReader br = new BufferedReader(isr)) {

          //
          outer: while ((s = br.readLine()) != null) {
            s = TextUtils.prepare(s);
            if (s == null) {
              continue;
            }
            i = s.indexOf(' ');
            if (i <= 0) {
              continue;
            }
            a = TextUtils.prepare(s.substring(0, i));
            if (a == null) {
              continue;
            }

            // did we find a hyphen mark?
            if (a.equals(LookupCharTransformer.HYPHEN_MARK)) {
              if (hyphen != LookupCharTransformer.DEF_HY) {
                throw new IllegalStateException(//
                    "You can only define one hyphen mark"); //$NON-NLS-1$
              }
              b = TextUtils.prepare(s.substring(i + 1));
              if (b != null) {
                hyphen = b.toCharArray();
              }
              continue;
            }

            a = TextUtils.toLowerCase(a);
            try {
              chrA = Integer.parseInt(a, 16);
            } catch (final Throwable t) {
              throw new IOException(t);
            }

            if (chrA < 0) {
              continue;
            }

            b = TextUtils.prepare(s.substring(i + 1));
            if (b == null) {
              b = EmptyUtils.EMPTY_STRING;
              chrB = LookupCharTransformer.STATE_MARK_OMIT;
            } else {
              chrB = b.charAt(0);
            }

            nextState = (-1);

            switch (chrB) {
              case LookupCharTransformer.STATE_MARK_DIRECT: {
                nextState = LookupCharTransformer.STATE_DIRECT;
                break;
              }
              case LookupCharTransformer.STATE_MARK_OMIT: {
                nextState = LookupCharTransformer.STATE_OMIT;
                break;
              }
              case LookupCharTransformer.STATE_MARK_TO_SPACE: {
                nextState = LookupCharTransformer.STATE_TO_SPACE;
                break;
              }
              default: {
                nextState = (((chrB == chrA) && (b.length() <= 1)) ? //
                LookupCharTransformer.STATE_DIRECT
                    : ((byte) (-1)));
              }
            }

            if (nextState >= 0) {
              if (chrA >= state.length) {
                state = Arrays.copyOf(state, ((chrA + 1) << 1));
              }
              state[chrA] = nextState;
              lastDir = Math.max(chrA, lastDir);

              if (lastDir >= 0x17e) { // keep special chars outside of
                // table
                continue outer; // for high char indices
              }

              // for low char indices, try to build a complete table to
              // speed
              // up
              // lookup operations
              chrs = new char[] { ((char) chrA) };
            } else {
              i = b.length();
              chrs = new char[i + 1];
              chrs[0] = ((char) (chrA));
              b.getChars(0, i, chrs, 1);
            }
            al.add(chrs);
          }
        }
      }
    } catch (final Throwable t) {
      throw new ExceptionInInitializerError(t);
    }

    // ok, we finished loading - now let us prepare the data

    // hyphenation is deprecated... we can read that data, but let's ignore
    // it
    // this.m_hyphen = (((hyphen != null) && (hyphen.length > 0))//
    // ? new CharArrayCharSequence(hyphen)
    // : null);

    // for lower-index characters, we have a direct lookup
    if (lastDir >= 0) {
      lastDir++;
      if (lastDir == state.length) {
        this.m_state = state;
      } else {
        this.m_state = new byte[lastDir];
        System.arraycopy(state, 0, this.m_state, 0, lastDir);
      }
    } else {
      this.m_state = LookupCharTransformer.DEF_STATE;
    }

    // the others go into a sorted table
    i = al.size();
    if (i > 0) {

      this.m_data = al.toArray(new char[i][]);
      Arrays.sort(this.m_data, _CharTransformerSorter.SORTER);

      last = (-1);
      for (final char ch[] : this.m_data) {
        cur = ch[0];
        if (cur == last) {
          throw new IllegalArgumentException(//
              "Double mapping for char " + cur + //$NON-NLS-1$
                  "(0x" + Integer.toHexString(cur) + //$NON-NLS-1$
                  "): " + Arrays.toString(ch)); //$NON-NLS-1$
        }
        last = cur;
      }

    } else {
      this.m_data = LookupCharTransformer.DEF_DATA;
    }
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput transform(final ITextOutput out,
      final Normalizer.Form form) {

    if (form == null) {
      return new _LookupTransformedTextOutput(out, this.m_data,
          this.m_state);
    }
    return new _NormalizingLookupTransformedTextOutput(out, this.m_data,
        this.m_state, form);
  }

}
