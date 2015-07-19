package org.optimizationBenchmarking.utils.math.statistics.parameters;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A statistic parameter computing the quantile */
public final class Quantile extends StatisticalParameter {

  /** the short name */
  static final String SHORT = "q"; //$NON-NLS-1$
  /** the long name */
  static final String LONG = "quantil"; //$NON-NLS-1$

  /** the quantile value */
  private final double m_p;

  /**
   * create the quantile parameter
   *
   * @param p
   *          the quantile value
   * @param qString
   *          the quantile name
   */
  private Quantile(final double p, final String qString) {
    super((Quantile.SHORT + qString), (qString + Quantile.LONG));

    if ((p < 0d) || (p > 1d) || (p != p)) {
      throw new IllegalArgumentException(//
          "Quantile value must be in [0,1], but is " + p); //$NON-NLS-1$
    }
    this.m_p = p;
  }

  /**
   * create the quantile parameter
   *
   * @param p
   *          the quantile value
   */
  private Quantile(final double p) {
    this(p, SimpleNumberAppender.INSTANCE.toString(p,
        ETextCase.IN_SENTENCE));
  }

  /**
   * Get the instance of a quantile parameter for the given quantile
   * parameter value.
   *
   * @param p
   *          the quantile parameter value
   * @return the quantile parameter
   */
  public static final StatisticalParameter getInstance(final double p) {
    if (p <= 0d) {
      return Minimum.INSTANCE;
    }
    if (p >= 1d) {
      return Maximum.INSTANCE;
    }
    if (p == 0.5d) {
      return Median.INSTANCE;
    }
    return new Quantile(p);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    next = SimpleNumberAppender.INSTANCE.appendTo(//
        this.m_p, textCase, textOut);
    textOut.append(' ');
    return next.appendWord("quantile", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    next = textCase.appendWord("the", textOut);//$NON-NLS-1$
    textOut.append(' ');
    next = SimpleNumberAppender.INSTANCE.appendTo(//
        this.m_p, textCase, textOut);
    textOut.append('-');
    next = next.appendWords(
        "quantile, i.e., the value which would be at position",// //$NON-NLS-1$
        textOut);
    textOut.append(' ');
    next = SimpleNumberAppender.INSTANCE.appendTo(//
        this.m_p, textCase, textOut);
    textOut.append('*');
    next = next.appendWords("n if we sorted n values",// //$NON-NLS-1$
        textOut);

    return next;
  }

  /** {@inheritDoc} */
  @Override
  public final QuantileAggregate createSampleAggregate() {
    return new QuantileAggregate(this.m_p);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof Quantile) && (((Quantile) o).m_p == this.m_p)));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.getClass()),//
        HashUtils.hashCode(this.m_p));
  }
}
