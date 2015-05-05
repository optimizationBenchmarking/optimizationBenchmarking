package org.optimizationBenchmarking.experimentation.attributes.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.MaximumAggregate;

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
}
