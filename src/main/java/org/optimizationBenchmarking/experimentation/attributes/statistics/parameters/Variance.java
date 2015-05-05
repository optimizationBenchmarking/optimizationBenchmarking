package org.optimizationBenchmarking.experimentation.attributes.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.VarianceAggregate;

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
}
