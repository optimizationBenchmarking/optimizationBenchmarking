package org.optimizationBenchmarking.experimentation.attributes.statistics.propertyExtremals;

import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * The minimal and maximal value of
 *
 * @param <PT>
 *          the property value type
 */
public final class ExtremalPropertyValues<PT extends IPropertyValue>
extends HashObject {

  /** the minimum value */
  private final PT m_min;

  /** the maximum value */
  private final PT m_max;

  /**
   * Create the property extremal value set
   *
   * @param min
   *          the minimum property value
   * @param max
   *          the maximal property value
   */
  ExtremalPropertyValues(final PT min, final PT max) {
    super();

    if (min == null) {
      throw new IllegalArgumentException(//
          "The minimum property value cannot be null."); //$NON-NLS-1$
    }
    if (max == null) {
      throw new IllegalArgumentException(//
          "The maximum property value cannot be null."); //$NON-NLS-1$
    }

    this.m_min = min;
    this.m_max = max;
  }

  /**
   * Get the minimum property value as a {@link java.lang.Number}
   *
   * @return the minimum property value as a {@link java.lang.Number}
   */
  public final Number getMinimumValue() {
    return ((Number) (this.m_min.getValue()));
  }

  /**
   * Get the maximum property value as a {@link java.lang.Number}
   *
   * @return the maximum property value as a {@link java.lang.Number}
   */
  public final Number getMaximumValue() {
    return ((Number) (this.m_max.getValue()));
  }

  /**
   * Get the minimum property value
   *
   * @return the minimum property value
   */
  public final PT getMinimum() {
    return this.m_max;
  }

  /**
   * Get the maximum property value
   *
   * @return the maximum property value
   */
  public final PT getMaximum() {
    return this.m_max;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_min.getValue()),//
        HashUtils.hashCode(this.m_max.getValue()));
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final boolean equals(final Object o) {
    final ExtremalPropertyValues ex;

    if (o == this) {
      return true;
    }

    if (o instanceof ExtremalPropertyValues) {
      ex = ((ExtremalPropertyValues) o);
      return (EComparison.equals(this.m_min.getValue(),
          ex.m_min.getValue()) && //
      EComparison.equals(this.m_max.getValue(), ex.m_max.getValue()));
    }

    return false;
  }
}
