package org.optimizationBenchmarking.utils.math.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.VarianceAggregate;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A statistic parameter computing the variance. */
public final class Variance extends StatisticalParameter {

  /** the short name */
  static final String SHORT = "var"; //$NON-NLS-1$
  /** the long name */
  static final String LONG = "variance"; //$NON-NLS-1$

  /**
   * the globally shares instance of the {@linkplain Variance variance}
   * parameter
   */
  public static final Variance INSTANCE = new Variance();

  /** create the variance parameter */
  private Variance() {
    super(Variance.SHORT, Variance.LONG);
  }

  /** {@inheritDoc} */
  @Override
  public final VarianceAggregate createSampleAggregate() {
    return new VarianceAggregate();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(//
        "the variance of a set of values.",// //$NON-NLS-1$
        textOut);
  }
}
