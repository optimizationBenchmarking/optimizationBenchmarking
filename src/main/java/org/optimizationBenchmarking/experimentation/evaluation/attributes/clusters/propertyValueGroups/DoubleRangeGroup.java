package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;

/**
 * A group of elements belonging to a floating-point based range range. The
 * limits of this range
 * {@link org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups.ValueRangeGroup#getLowerBound()}
 * and
 * {@link org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups.ValueRangeGroup#getUpperBound()}
 * can be interpreted via {@link java.lang.Number#doubleValue()} without
 * loss of accuracy or precision, but are not necessarily instances of
 * {@link java.lang.Double}. They may even be instances of
 * {@link java.lang.Byte}, if they are actually {@code byte} values.
 * 
 * @param <DT>
 *          the data set type
 */
public final class DoubleRangeGroup<DT extends DataElement> extends
    ValueRangeGroup<Number, DT> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

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
  DoubleRangeGroup(final Number lowerBound, final Number upperBound,
      final boolean isUpperExclusive, final Number[] values,
      final DT[] data) {
    super(lowerBound, upperBound, isUpperExclusive, values, data);

    if (lowerBound.doubleValue() >= upperBound.doubleValue()) {
      throw new IllegalArgumentException(
          "Interval cannot be empty or composed of a single value, but lower bound " //$NON-NLS-1$
              + lowerBound + //
              " is specified and upper bound is "//$NON-NLS-1$ 
              + upperBound);
    }
    if (Double.isNaN(lowerBound.doubleValue())) {
      throw new IllegalArgumentException(//
          "Lower bound of interval cannot be NaN.");//$NON-NLS-1$
    }
    if (Double.isNaN(upperBound.doubleValue())) {
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
