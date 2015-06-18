package org.optimizationBenchmarking.experimentation.attributes.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.StandardDeviationAggregate;

/** A statistic parameter computing the standard deviation. */
public final class StandardDeviation extends StatisticalParameter {

  /** the short name */
  static final String SHORT = "stddev"; //$NON-NLS-1$
  /** the long name */
  static final String LONG = "standard deviation"; //$NON-NLS-1$

  /**
   * the globally shares instance of the {@linkplain StandardDeviation
   * standard deviation} parameter
   */
  public static final StandardDeviation INSTANCE = new StandardDeviation();

  /** create the standard deviation parameter */
  private StandardDeviation() {
    super(StandardDeviation.SHORT, StandardDeviation.LONG);
  }

  /** {@inheritDoc} */
  @Override
  public final StandardDeviationAggregate createSampleAggregate() {
    return new StandardDeviationAggregate();
  }
}