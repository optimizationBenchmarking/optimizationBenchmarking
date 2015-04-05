package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;

/**
 * A group of elements belonging to an integer range.
 * 
 * @param <DT>
 *          the data set type
 */
public final class LongRangeGroup<DT extends DataElement> extends
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
  LongRangeGroup(final long lowerBound, final long upperBound,
      final boolean isUpperExclusive, final Number[] values,
      final DT[] data) {
    super(Long.valueOf(lowerBound), Long.valueOf(upperBound),
        isUpperExclusive, values, data);

    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException(
          "Interval cannot be empty or composed of a single value, but lower bound " //$NON-NLS-1$
              + lowerBound + //
              " is specified and upper bound is "//$NON-NLS-1$ 
              + upperBound);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public LongRangeGroups<?> getOwner() {
    return ((LongRangeGroups) (this.m_owner));
  }
}
