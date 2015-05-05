package org.optimizationBenchmarking.experimentation.attributes.statistics.parameters;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.MinimumAggregate;

/** A statistic parameter computing the minimum. */
public final class Minimum extends StatisticalParameter {

  /** the short name */
  static final String SHORT = "min"; //$NON-NLS-1$
  /** the long name */
  static final String LONG = "minimum"; //$NON-NLS-1$

  /**
   * the globally shares instance of the {@linkplain Minimum maximum}
   * parameter
   */
  public static final Minimum INSTANCE = new Minimum();

  /** create the minimum parameter */
  private Minimum() {
    super(Minimum.SHORT, Minimum.LONG);
  }

  /** {@inheritDoc} */
  @Override
  public final MinimumAggregate createSampleAggregate() {
    return new MinimumAggregate();
  }
}
