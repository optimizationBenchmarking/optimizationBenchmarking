package org.optimizationBenchmarking.utils.math.units;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * The dimensions.
 */
public enum EDimensions implements IDimension {

  /** the length dimension */
  LENGTH("length") { //$NON-NLS-1$    /** {@inheritDoc} */
    @Override
    public final ArraySetView<ELength> getUnits() {
      return ELength.INSTANCES;
    }
  },

  /** the time dimension */
  TIME("time") { //$NON-NLS-1$    /** {@inheritDoc} */
    @Override
    public final ArraySetView<ETime> getUnits() {
      return ETime.INSTANCES;
    }
  };

  /** all dimensions */
  public static final ArraySetView<EDimensions> INSTANCES = //
      new ArraySetView<>(EDimensions.values());

  /** the name */
  private final String m_name;

  /**
   * create
   *
   * @param name
   *          the name
   */
  EDimensions(final String name) {
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_name;
  }
}
