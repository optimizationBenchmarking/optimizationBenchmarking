package org.optimizationBenchmarking.utils.math.statistics.ranking;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The strategy for handling ties in ranking. */
public enum ETieStrategy {

  /**
   * All tied elements get the same rank, which is the minimum of the
   * applicable ranks. The next rank is the number of ranked elements.
   * {@code (1, 2, 3, 4, 4, 5, 6, 6, 6, 7)} will be ranked as
   * {@code (1, 2, 3, 4, 4, 6, 7, 7, 7, 10)}.
   */
  MINIMUM {
    /** {@inheritDoc} */
    @Override
    final void _rank(final _RankedElement[] elements) {
      int startIndex, endIndex;
      _RankedElement current, next;

      for (startIndex = 0; startIndex < elements.length; startIndex = endIndex) {
        current = elements[startIndex];
        current.m_rank = (++startIndex);
        findEqual: for (endIndex = startIndex; endIndex < elements.length; endIndex++) {
          next = elements[endIndex];
          if (current.compareTo(next) != 0) {
            break findEqual;
          }
          next.m_rank = startIndex;
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      return textCase
          .appendWords(//
              "ties, i.e., equal elements, receive the same rank, which is the minimum of the applicable ranks.", //$NON-NLS-1$
              textOut);
    }
  },

  /**
   * All tied elements get the same rank, which is the minimum of the
   * applicable ranks. The next rank is the next higher number.
   * {@code (1, 2, 3, 4, 4, 5, 6, 6, 6, 7)} will be ranked as
   * {@code (1, 2, 3, 4, 4, 5, 6, 6, 6, 7)}.
   */
  MINIMUM_TIGHT {
    /** {@inheritDoc} */
    @Override
    final void _rank(final _RankedElement[] elements) {
      int nextRank, startIndex, endIndex;
      _RankedElement current, next;

      nextRank = 0;
      for (startIndex = 0; startIndex < elements.length; startIndex = endIndex) {
        current = elements[startIndex];
        current.m_rank = (++nextRank);
        findEqual: for (endIndex = startIndex; (++endIndex) < elements.length;) {
          next = elements[endIndex];
          if (current.compareTo(next) != 0) {
            break findEqual;
          }
          next.m_rank = nextRank;
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      textCase
          .appendWords(//
              "ties, i.e., equal elements, receive the same rank, which is the minimum of the applicable ranks.", //$NON-NLS-1$
              textOut);
      textOut.append(' ');
      return textCase
          .nextAfterSentenceEnd()
          .appendWords(
              "the following elements directly receive the next higher rank, regarless how many elements were tied.",//$NON-NLS-1$
              textOut);
    }
  },

  /**
   * All tied elements get the same rank, which is the maximum of the
   * applicable ranks. {@code (1, 2, 3, 4, 4, 5, 6, 6, 6, 7)} will be
   * ranked as {@code (1, 2, 3, 5, 5, 6, 9, 9, 9, 10)}.
   */
  MAXIMUM {
    /** {@inheritDoc} */
    @Override
    final void _rank(final _RankedElement[] elements) {
      int startIndex, endIndex;
      _RankedElement current;

      for (startIndex = 0; startIndex < elements.length;) {
        current = elements[startIndex];
        findEqual: for (endIndex = startIndex; (++endIndex) < elements.length;) {
          if (current.compareTo(elements[endIndex]) != 0) {
            break findEqual;
          }
        }
        for (; startIndex < endIndex; startIndex++) {
          elements[startIndex].m_rank = endIndex;
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      return textCase
          .appendWords(//
              "ties, i.e., equal elements, receive the same rank, which is the maximum of the applicable ranks.", //$NON-NLS-1$
              textOut);
    }
  },

  /**
   * All tied elements get the same rank, which is the average of the
   * applicable rank. {@code (1, 2, 3, 4, 4, 5, 6, 6, 6, 7)} will be ranked
   * as {@code (1, 2, 3, 4.5, 4.5, 6, 8, 8, 8, 10)}.
   */
  AVERAGE {
    /** {@inheritDoc} */
    @Override
    final void _rank(final _RankedElement[] elements) {
      int startIndex, endIndex;
      _RankedElement current;
      double rank;

      for (startIndex = 0; startIndex < elements.length;) {
        current = elements[startIndex];
        findEqual: for (endIndex = startIndex; (++endIndex) < elements.length;) {
          if (current.compareTo(elements[endIndex]) != 0) {
            break findEqual;
          }
        }

        rank = (0.5d * (startIndex + 1 + endIndex));

        for (; startIndex < endIndex; startIndex++) {
          elements[startIndex].m_rank = rank;
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      return textCase
          .appendWords(//
              "ties, i.e., equal elements, receive the same rank, which is the arithmetic mean of the applicable ranks.", //$NON-NLS-1$
              textOut);
    }
  };

  /** the default strategy for handling ties */
  public static final ETieStrategy DEFAULT = AVERAGE;

  /**
   * Rank the given sorted set of elements.
   *
   * @param elements
   *          the elements
   */
  abstract void _rank(final _RankedElement[] elements);

  /**
   * Describe this tie strategy.
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next case
   */
  abstract ETextCase _printDescription(final ITextOutput textOut,
      final ETextCase textCase);
}
