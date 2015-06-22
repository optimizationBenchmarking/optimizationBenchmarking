package org.optimizationBenchmarking.utils.math.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.ArithmeticMeanAggregate;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A statistic parameter computing the arithmetic mean. */
public final class ArithmeticMean extends StatisticalParameter {

  /** the short name */
  static final String SHORT = "mean"; //$NON-NLS-1$
  /** the long name */
  static final String LONG = "arithmetic mean"; //$NON-NLS-1$
  /** the other name */
  static final String OTHER = "average"; //$NON-NLS-1$

  /**
   * the globally shares instance of the {@linkplain ArithmeticMean
   * arithmetic mean} parameter
   */
  public static final ArithmeticMean INSTANCE = new ArithmeticMean();

  /** create the mean parameter */
  private ArithmeticMean() {
    super(ArithmeticMean.SHORT, ArithmeticMean.LONG);
  }

  /** {@inheritDoc} */
  @Override
  public final ArithmeticMeanAggregate createSampleAggregate() {
    return new ArithmeticMeanAggregate();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return ETextCase
        .ensure(textCase)
        .appendWords(//
            "the arithmetic mean (often refered to as average) of a set of values, i.e., the sum of the values divided by their number.",// //$NON-NLS-1$
            textOut);
  }
}
