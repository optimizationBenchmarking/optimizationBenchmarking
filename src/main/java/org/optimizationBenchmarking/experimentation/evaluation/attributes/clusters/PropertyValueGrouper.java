package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters;

import org.optimizationBenchmarking.experimentation.data.Attribute;
import org.optimizationBenchmarking.experimentation.data.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.Property;
import org.optimizationBenchmarking.experimentation.data.PropertyValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * An attribute computing groups of property values
 * 
 * @param <PVT>
 *          the property value type
 * @param <GT>
 *          the group type
 */
public abstract class PropertyValueGrouper<PVT extends PropertyValue<?>, GT extends PropertyValueGroups<PVT>>
    extends Attribute<Property<PVT>, GT> {

  /** the group mode */
  private final EGroupMode m_mode;

  /**
   * the goal fraction for the minimum number of elements per group,
   * relative to the total number of elements of the property
   */
  private final double m_minElementsPerGroupFraction;

  /**
   * the goal fraction for the maximum number of elements per group,
   * relative to the total number of elements of the property
   */
  private final double m_maxElementsPerGroupFraction;

  /** the goal fraction for the minimum number of groups */
  private final int m_minGroups;

  /** the goal fraction for the maximum number of groups */
  private final int m_maxGroups;

  /**
   * Create the property value grouper
   * 
   * @param mode
   *          the group mode
   * @param minElementsPerGroupFraction
   *          the goal fraction for the minimum number of elements per
   *          group, relative to the total number of elements of the
   *          property; {@code 0} for don't care
   * @param maxElementsPerGroupFraction
   *          the goal fraction for the maximum number of elements per
   *          group, relative to the total number of elements of the
   *          property; {@code 1} for don't care
   * @param minGroups
   *          the goal fraction for the minimum number of groups; {@code 0}
   *          for don't care
   * @param maxGroups
   *          the goal fraction for the maximum number of groups;
   *          {@link java.lang.Integer#MAX_VALUE} for don't care
   */
  PropertyValueGrouper(final EGroupMode mode,
      final double minElementsPerGroupFraction,
      final double maxElementsPerGroupFraction, final int minGroups,
      final int maxGroups) {
    super(EAttributeType.TEMPORARILY_STORED);

    if (mode == null) {
      throw new IllegalArgumentException(//
          "Grouping mode must not be null."); //$NON-NLS-1$
    }
    this.m_mode = mode;

    if ((minElementsPerGroupFraction < 0d)
        || (minElementsPerGroupFraction >= 1d)) {
      throw new IllegalArgumentException(//
          "The goal fraction for the minimum elements per group must be from the real interval [0,1), but is " //$NON-NLS-1$
              + minElementsPerGroupFraction);
    }
    this.m_minElementsPerGroupFraction = minElementsPerGroupFraction;

    if ((maxElementsPerGroupFraction <= minElementsPerGroupFraction)
        || (maxElementsPerGroupFraction > 1d)) {
      throw new IllegalArgumentException(//
          "The goal fraction for the maximum elements per group must be larger than the goal fraction for the minimum elements per group (" + //$NON-NLS-1$
              minElementsPerGroupFraction + //
              ") and <= 1, but is " //$NON-NLS-1$
              + maxElementsPerGroupFraction);
    }
    this.m_maxElementsPerGroupFraction = maxElementsPerGroupFraction;

    if ((minGroups < 0) || (minGroups >= Integer.MAX_VALUE)) {
      throw new IllegalArgumentException(//
          "The goal for the minimum number of groups must be from the interval 0..(Integer.MAX_VALUE-1), but is " //$NON-NLS-1$
              + minGroups);
    }
    this.m_minGroups = minGroups;

    if (maxGroups <= minGroups) {
      throw new IllegalArgumentException(//
          "The goal for the maximum number of groups must be larger than the goal for the minimum number of groups (" + //$NON-NLS-1$
              minGroups + //
              "), but is " //$NON-NLS-1$
              + maxGroups);
    }
    this.m_maxGroups = maxGroups;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_mode),//
        HashUtils.combineHashes(HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_minElementsPerGroupFraction),//
            HashUtils.hashCode(this.m_maxElementsPerGroupFraction)),//
            HashUtils.combineHashes(HashUtils.hashCode(this.m_minGroups),
                HashUtils.hashCode(this.m_maxGroups))));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    final PropertyValueGrouper grouper;

    if (o == this) {
      return true;
    }

    if (o instanceof PropertyValueGrouper) {
      grouper = ((PropertyValueGrouper) o);
      return (EComparison.equals(this.m_mode, grouper.m_mode) && //
          (EComparison.EQUAL.compare(this.m_minElementsPerGroupFraction,//
              grouper.m_minElementsPerGroupFraction)) && //
          (EComparison.EQUAL.compare(this.m_maxElementsPerGroupFraction,//
              grouper.m_maxElementsPerGroupFraction)) && // /
          (this.m_minGroups == grouper.m_minGroups) && //
      (this.m_maxGroups == grouper.m_maxGroups));
    }

    return false;
  }

  /**
   * create an array of the given size
   * 
   * @param size
   *          the size
   * @return the array
   */
  abstract PVT[] _createArray(final int size);

  /**
   * create a property value group from a list of elements
   * 
   * @param array
   *          the group's elements
   * @return the property value group
   */
  abstract PropertyValueGroup<PVT> _createGroup(final PVT[] array);

  /**
   * create the result type from a list of groups
   * 
   * @param groups
   *          the groups
   * @return the result
   */
  abstract GT _createResult(final PropertyValueGroup<PVT>[] groups);

  /** {@inheritDoc} */
  @Override
  protected GT compute(final Property<PVT> data) {
    // TODO Auto-generated method stub
    return null;
  }
}
