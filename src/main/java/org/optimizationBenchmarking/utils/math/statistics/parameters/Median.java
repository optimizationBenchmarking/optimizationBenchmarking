package org.optimizationBenchmarking.utils.math.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A statistic parameter computing the median . */
public final class Median extends StatisticalParameter {

  /** the short name */
  static final String SHORT = "med"; //$NON-NLS-1$
  /** the long name */
  static final String LONG = "median"; //$NON-NLS-1$

  /**
   * the globally shares instance of the {@linkplain Median median}
   * parameter
   */
  public static final Median INSTANCE = new Median();

  /** create the median aggregate */
  private Median() {
    super(Median.SHORT, Median.LONG);
  }

  /** {@inheritDoc} */
  @Override
  public final ScalarAggregate createSampleAggregate() {
    return new QuantileAggregate(0.5d);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return ETextCase
        .ensure(textCase)
        .appendWords(//
            "the median of a set of values, i.e., the value which would be in the middle if we sorted the set.",// //$NON-NLS-1$
            textOut);
  }
}
