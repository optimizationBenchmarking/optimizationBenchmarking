package org.optimizationBenchmarking.utils.math.statistics.ranking;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** This class lets us rank elements */
public final class RankingStrategy implements ISemanticComponent {

  /** the NaN strategy for the ranking */
  public static final String NAN_STRATEGY_PARAMETER = "rankingNaNStrategy"; //$NON-NLS-1$

  /** the tie strategy for the ranking */
  public static final String TIE_STRATEGY_PARAMETER = "rankingTieStrategy"; //$NON-NLS-1$

  /** the short name for the ranking */
  private static final char[] SHORT_NAME = { 'r', 'a', 'n', 'k' };

  /** the nan strategy */
  private final ENaNStrategy m_nan;

  /** the tie strategy */
  private final ETieStrategy m_ties;

  /**
   * Create the ranking
   *
   * @param nan
   *          the {@linkplain ENaNStrategy NaN strategy}, or {@code null}
   *          for {@linkplain ENaNStrategy#DEFAULT default}
   * @param ties
   *          the {@linkplain ETieStrategy tie strategy}, or {@code null}
   *          for {@linkplain ETieStrategy#DEFAULT default}
   */
  public RankingStrategy(final ENaNStrategy nan, final ETieStrategy ties) {
    super();

    this.m_nan = ((nan != null) ? nan : ENaNStrategy.DEFAULT);
    this.m_ties = ((ties != null) ? ties : ETieStrategy.DEFAULT);
  }

  /**
   * Obtain the {@linkplain ENaNStrategy NaN strategy}, i.e., the way in
   * which {@value java.lang.Double#NaN}s are handled
   *
   * @return the {@linkplain ENaNStrategy NaN strategy}, i.e., the way in
   *         which {@value java.lang.Double#NaN}s are handled
   */
  public final ENaNStrategy getNaNStrategy() {
    return this.m_nan;
  }

  /**
   * Obtain the {@linkplain ETieStrategy tie strategy}, i.e., the way in
   * which ties handled, meaning how equal values are ranked
   *
   * @return the {@linkplain ETieStrategy tie strategy}, i.e., the way in
   *         which ties handled, meaning how equal values are ranked
   */
  public final ETieStrategy getTieStrategy() {
    return this.m_ties;
  }

  /**
   * Rank all elements in the {@code data} array and store the resulting
   * ranks in the destination array.
   *
   * @param data
   *          the data array, containing the elements to be ranked
   * @param dest
   *          the destination array
   */
  public final void rank(final double[] data, final double[] dest) {
    final _RankedElement[] elements;
    int i;

    elements = new _RankedElement[data.length];
    i = 0;
    for (final double d : data) {
      elements[i] = this.m_nan._element(i, 0, d);
      ++i;
    }
    Arrays.sort(elements);
    this.m_ties._rank(elements);
    this.m_nan._refine(elements);
    for (final _RankedElement e : elements) {
      dest[e.m_index1] = e.m_rank;
    }
  }

  /**
   * Rank all elements in the {@code data} array and store the resulting
   * ranks in the destination array.
   *
   * @param data
   *          the data array, containing the elements to be ranked
   * @param dest
   *          the destination array
   */
  public final void rank(final long[] data, final double[] dest) {
    final _RankedElement[] elements;
    int i;

    elements = new _RankedElement[data.length];
    i = 0;
    for (final double d : data) {
      elements[i] = this.m_nan._element(i, 0, d);
      ++i;
    }
    Arrays.sort(elements);
    this.m_ties._rank(elements);
    this.m_nan._refine(elements);
    for (final _RankedElement e : elements) {
      dest[e.m_index1] = e.m_rank;
    }
  }

  /**
   * Load a ranking strategy from a configuration
   *
   * @param config
   *          the configuration
   * @return the ranking strategy
   */
  public static final RankingStrategy create(final Configuration config) {
    final ENaNStrategy nan;
    final ETieStrategy tie;

    nan = config.get(RankingStrategy.NAN_STRATEGY_PARAMETER,
        NaNStrategyParser.INSTANCE, ENaNStrategy.DEFAULT);
    tie = config.get(RankingStrategy.TIE_STRATEGY_PARAMETER,
        TieStrategyParser.INSTANCE, ETieStrategy.DEFAULT);

    return new RankingStrategy(nan, tie);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWord(RankingStrategy.SHORT_NAME, textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWord("ranking", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    next = textCase.appendWords("during the ranking", textOut);//$NON-NLS-1$
    textOut.append(',');
    textOut.append(' ');

    if (this.m_nan != ENaNStrategy.ERROR) {
      next = this.m_nan._printDescription(textOut, next);
      textOut.append(',');
      textOut.append(' ');
      next = next.appendWord("while", textOut);//$NON-NLS-1$
      textOut.append(' ');
    }

    return this.m_ties._printDescription(textOut, next);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    mto.append(RankingStrategy.SHORT_NAME);
    if (this.m_nan != ENaNStrategy.DEFAULT) {
      mto.append('_');
      mto.append(this.m_nan.name().toLowerCase());
    }
    if (this.m_ties != ETieStrategy.DEFAULT) {
      mto.append('_');
      mto.append(this.m_ties.name().toLowerCase());
    }

    return mto.toString();
  }
}
