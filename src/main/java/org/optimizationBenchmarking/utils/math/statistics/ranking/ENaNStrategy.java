package org.optimizationBenchmarking.utils.math.statistics.ranking;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * How should {@link java.lang.Double#NaN} values be handled during
 * ranking?
 */
public enum ENaNStrategy {

  /**
   * {@link java.lang.Double#NaN} values are equal to each other but
   * smaller than anything else, including
   * {@link java.lang.Double#NEGATIVE_INFINITY}.
   */
  MINIMAL {
    /** {@inheritDoc} */
    @Override
    final int _compareLong() {
      return (-1);
    }

    /** {@inheritDoc} */
    @Override
    final int _compareDouble(final double value) {
      return (-1);
    }

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      final ETextCase next;

      textOut.append(Double.NaN);
      textOut.append('-');
      next = textCase
          .nextCase()
          .appendWords(//
              "are treated as equal to each other and smaller than anything else, including", //$NON-NLS-1$
              textOut);
      textOut.append(' ');
      textOut.append(Double.NEGATIVE_INFINITY);
      return next.nextCase();
    }
  },

  /**
   * {@link java.lang.Double#NaN} values are equal to
   * {@link java.lang.Double#NEGATIVE_INFINITY}.
   */
  NEGATIVE_INFINITY {
    /** {@inheritDoc} */
    @Override
    final int _compareLong() {
      return (-1);
    }

    /** {@inheritDoc} */
    @Override
    final int _compareDouble(final double value) {
      return ((value <= Double.NEGATIVE_INFINITY) ? 0 : (-1));
    }

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      final ETextCase next;

      textOut.append(Double.NaN);
      textOut.append('-');
      next = textCase.nextCase().appendWords(//
          "are treated as equal to each other and ", //$NON-NLS-1$
          textOut);
      textOut.append(' ');
      textOut.append(Double.NEGATIVE_INFINITY);
      return next.nextCase();
    }
  },

  /**
   * {@link java.lang.Double#NaN} values are equal
   * {@link java.lang.Double#POSITIVE_INFINITY}.
   */
  POSITIVE_INFINITY {
    /** {@inheritDoc} */
    @Override
    final int _compareDouble(final double value) {
      return ((value >= Double.POSITIVE_INFINITY) ? 0 : (1));
    }

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      final ETextCase next;

      textOut.append(Double.NaN);
      textOut.append('-');
      next = textCase.nextCase().appendWords(//
          "are treated as equal to each other and", //$NON-NLS-1$
          textOut);
      textOut.append(' ');
      textOut.append(Double.NEGATIVE_INFINITY);
      return next.nextCase();
    }
  },

  /**
   * {@link java.lang.Double#NaN} values are equal to each other but larger
   * than anything else, including
   * {@link java.lang.Double#POSITIVE_INFINITY}.
   */
  MAXIMAL {

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      final ETextCase next;

      textOut.append(Double.NaN);
      textOut.append('-');
      next = textCase
          .nextCase()
          .appendWords(//
              "are treated as equal to each other and larger than anything else, including", //$NON-NLS-1$
              textOut);
      textOut.append(' ');
      textOut.append(Double.POSITIVE_INFINITY);
      return next.nextCase();
    }
  },

  /**
   * {@link java.lang.Double#NaN} values will lead to
   * {@link java.lang.IllegalArgumentException}s.
   */
  ERROR {
    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      textOut.append(Double.NaN);
      textOut.append('-');
      return textCase
          .nextCase()
          .appendWords(//
              "are not permitted (i.e., would lead to an exception during the report generation", //$NON-NLS-1$
              textOut);
    }
  },

  /**
   * {@link java.lang.Double#NaN} values will get
   * {@link java.lang.Double#NaN} as rank.
   */
  NAN {
    /** {@inheritDoc} */
    @Override
    final void _refine(final _RankedElement[] ranks) {
      _RankedElement e;
      int i;
      for (i = ranks.length; (--i) >= 0;) {
        e = ranks[i];
        if (e instanceof _RankedNaN) {
          e.m_rank = Double.NaN;
        } else {
          return;
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    final ETextCase _printDescription(final ITextOutput textOut,
        final ETextCase textCase) {
      final ETextCase next;
      textOut.append(Double.NaN);
      textOut.append('-');
      next = textCase.nextCase().appendWords(//
          "will also receive", //$NON-NLS-1$
          textOut);
      textOut.append(' ');
      textOut.append(Double.NaN);
      textOut.append(' ');
      return next.appendWords(//
          "as rank", //$NON-NLS-1$
          textOut);
    }
  };

  /** the default strategy for handling NaN */
  public static final ENaNStrategy DEFAULT = ERROR;

  /**
   * compare to a {@code long} value, whatever it is
   *
   * @return the comparison result
   */
  int _compareLong() {
    return 1;
  }

  /**
   * compare to a {@code double} value
   *
   * @param value
   *          the value
   * @return the comparison result
   */
  int _compareDouble(final double value) {
    return 1;
  }

  /**
   * Get the ranked element for a {@code long} value
   *
   * @param index1
   *          the first index of the original value
   * @param index2
   *          the second index of the original value
   * @param value
   *          the value
   * @return the ranked element
   */
  final _RankedElement _element(final int index1, final int index2,
      final long value) {
    return new _RankedLong(index1, index2, value);
  }

  /**
   * Get the ranked element for a {@code double} value
   *
   * @param index1
   *          the first index of the original value
   * @param index2
   *          the second index of the original value
   * @param value
   *          the value
   * @return the ranked element
   */
  final _RankedElement _element(final int index1, final int index2,
      final double value) {
    if (NumericalTypes.isLong(value)) {
      return this._element(index1, index2, ((long) value));
    }
    if (value != value) {
      if (this == ENaNStrategy.ERROR) {
        throw new IllegalArgumentException(//
            "NaN not permitted with NaN Strategy " //$NON-NLS-1$
                + this);
      }
      return new _RankedNaN(index1, index2, this);
    }
    return new _RankedDouble(index1, index2, value);
  }

  /**
   * Refine the ranks of the ranked elements
   *
   * @param ranks
   *          the ranks
   */
  void _refine(final _RankedElement[] ranks) {
    //
  }

  /**
   * Describe this NaN strategy.
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
