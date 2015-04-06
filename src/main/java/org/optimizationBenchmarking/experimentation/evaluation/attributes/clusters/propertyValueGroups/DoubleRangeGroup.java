package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;

/**
 * A group of elements belonging to an double range.
 * 
 * @param <DT>
 *          the data set type
 */
public final class DoubleRangeGroup<DT extends DataElement> extends
    ValueRangeGroup<Number, DT> {

  /**
   * create the property value group
   * 
   * @param lowerBound
   *          the inclusive lower bound
   * @param upperBound
   *          the exclusive or inclusive upper bound
   * @param isUpperExclusive
   *          is the upper bound exclusive?
   * @param values
   *          the values
   * @param data
   *          the data
   */
  DoubleRangeGroup(final double lowerBound, final double upperBound,
      final boolean isUpperExclusive, final Number[] values,
      final DT[] data) {
    super(Double.valueOf(lowerBound), Double.valueOf(upperBound),
        isUpperExclusive, values, data);

    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException(
          "Interval cannot be empty or composed of a single value, but lower bound " //$NON-NLS-1$
              + lowerBound + //
              " is specified and upper bound is "//$NON-NLS-1$ 
              + upperBound);
    }
    if (lowerBound != lowerBound) {
      throw new IllegalArgumentException(//
          "Lower bound of interval cannot be NaN.");//$NON-NLS-1$
    }
    if (upperBound != upperBound) {
      throw new IllegalArgumentException(//
          "Upper bound of interval cannot be NaN.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public DoubleRangeGroups<DT> getOwner() {
    return ((DoubleRangeGroups) (this.m_owner));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean contains(final Object o) {
    final double num;
    if (o instanceof Number) {
      num = ((Number) o).doubleValue();
      if (num >= this.getLowerBound().doubleValue()) {
        if (this.isUpperBoundExclusive()) {
          return (num < this.getUpperBound().doubleValue());
        }
        return (num <= this.getUpperBound().doubleValue());
      }
    }
    return false;
  }
}
