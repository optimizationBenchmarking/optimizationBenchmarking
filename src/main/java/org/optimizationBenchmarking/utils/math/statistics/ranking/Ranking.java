package org.optimizationBenchmarking.utils.math.statistics.ranking;

/** This class lets us rank elements */
public final class Ranking {

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
  public Ranking(final ENaNStrategy nan, final ETieStrategy ties) {
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
}
