package org.optimizationBenchmarking.utils.math.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.MaximumAggregate;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A statistic parameter computing the maximum. */
public final class Maximum extends StatisticalParameter {

  /** the short name */
  static final String SHORT = "max"; //$NON-NLS-1$
  /** the long name */
  static final String LONG = "maximum"; //$NON-NLS-1$

  /**
   * the globally shares instance of the {@linkplain Maximum maximum}
   * parameter
   */
  public static final Maximum INSTANCE = new Maximum();

  /** create the maximum parameter */
  private Maximum() {
    super(Maximum.SHORT, Maximum.LONG);
  }

  /** {@inheritDoc} */
  @Override
  public final MaximumAggregate createSampleAggregate() {
    return new MaximumAggregate();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return ETextCase.ensure(textCase).appendWords(//
        "the maximum, i.e., largest, of a set of values.",// //$NON-NLS-1$
        textOut);
  }
}
