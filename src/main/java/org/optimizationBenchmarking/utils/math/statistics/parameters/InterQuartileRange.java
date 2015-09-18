package org.optimizationBenchmarking.utils.math.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileRangeAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A statistic parameter computing the interquartile range median . */
public final class InterQuartileRange extends StatisticalParameter {

  /** the short name */
  private static final String SHORT = "IQR"; //$NON-NLS-1$
  /** the short cmp */
  static final String SHORT_CMP = "iqr"; //$NON-NLS-1$  
  /** the long name */
  static final String LONG = "interquartile range"; //$NON-NLS-1$

  /**
   * the globally shares instance of the {@linkplain InterQuartileRange
   * IQR} parameter
   */
  public static final InterQuartileRange INSTANCE = new InterQuartileRange();

  /** create the median aggregate */
  private InterQuartileRange() {
    super(InterQuartileRange.SHORT, InterQuartileRange.LONG);
  }

  /** {@inheritDoc} */
  @Override
  public final ScalarAggregate createSampleAggregate() {
    return new QuantileRangeAggregate(0.25d, 0.75d);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase
        .appendWords(//
            "the interquartile range is the difference between the upper and lower quartiles, i.e., the difference between the values separating the top and bottom 25% of the data from the rest.",// //$NON-NLS-1$
            textOut);
  }
}
