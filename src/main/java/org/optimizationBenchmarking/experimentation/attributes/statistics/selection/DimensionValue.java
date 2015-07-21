package org.optimizationBenchmarking.experimentation.attributes.statistics.selection;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/** A data point which meets a specific dimension value. */
public final class DimensionValue extends SelectionCriterion {

  /** the dimension */
  private final int m_dimension;

  /** are the values of the dimension integers? */
  private final boolean m_isInteger;

  /** the goal value as double */
  private final double m_valDouble;

  /** the goal value as long */
  private final long m_valLong;

  /**
   * create
   *
   * @param dimension
   *          the dimension
   * @param value
   *          the value it should reach
   */
  private DimensionValue(final IDimension dimension, final Number value) {
    super();

    if (dimension == null) {
      throw new IllegalArgumentException(//
          "Dimension cannot be null for DimensionValue selection criterion.");//$NON-NLS-1$
    }
    if (value == null) {
      throw new IllegalArgumentException(//
          "Value cannot be null for DimensionValue selection criterion.");//$NON-NLS-1$
    }

    this.m_dimension = dimension.getIndex();
    this.m_isInteger = dimension.getDataType().isInteger();
    this.m_valLong = value.longValue();
    this.m_valDouble = value.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public final IDataPoint get(final IRun run) {
    if (this.m_isInteger) {
      return run.find(this.m_dimension, this.m_valLong);
    }
    return run.find(this.m_dimension, this.m_valDouble);
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_dimension),//
        HashUtils.hashCode(this.m_isInteger)),//
        HashUtils.combineHashes(//
            (this.m_isInteger ? HashUtils.hashCode(this.m_valLong)
                : HashUtils.hashCode(this.m_valDouble)), 235235449));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final DimensionValue val;
    if (o == this) {
      return true;
    }
    if (o instanceof DimensionValue) {
      val = ((DimensionValue) o);
      return ((this.m_dimension == val.m_dimension) && //
          (this.m_isInteger == val.m_isInteger) && //
      (this.m_isInteger//
      ? (this.m_valLong == val.m_valLong)//
            : EComparison.EQUAL.compare(this.m_valDouble, val.m_valDouble)));
    }
    return false;
  }
}
