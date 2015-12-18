package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A property based selection.
 */
public final class PropertyValueSelector
    extends Attribute<IExperimentSet, PropertyValueGroups> {

  /** The the group-by parameter: {@value} */
  private static final String PARAM_GROUP_BY = "groupBy"; //$NON-NLS-1$
  /** The the group-by instances property parameter: {@value} */
  public static final String CHOICE_INSTANCES_BY_FEATURE_VALUE = "instances by feature values"; //$NON-NLS-1$
  /** The the group-by experiments property parameter: {@value} */
  public static final String CHOICE_EXPERIMENTS_BY_PARAMETER_VALUE = "experiments by parameter values"; //$NON-NLS-1$

  /** the property */
  private final String m_property;

  /** the grouper to apply */
  private final PropertyValueGrouper m_grouper;

  /**
   * Create the property-based selection
   *
   * @param property
   *          the property name
   * @param grouper
   *          the grouper
   */
  private PropertyValueSelector(final String property,
      final PropertyValueGrouper grouper) {
    super(EAttributeType.TEMPORARILY_STORED);

    this.m_property = TextUtils.prepare(property);
    if (this.m_property == null) {
      throw new IllegalArgumentException(//
          "Property name cannot be null, empty, or just consist of white space."); //$NON-NLS-1$
    }
    if (grouper == null) {
      throw new IllegalArgumentException(//
          "Property grouper cannot be null."); //$NON-NLS-1$
    }

    this.m_grouper = grouper;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_property), //
        HashUtils.hashCode(this.m_grouper));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final PropertyValueSelector selector;
    if (o == this) {
      return true;
    }

    if (o instanceof PropertyValueSelector) {
      selector = ((PropertyValueSelector) o);
      return (EComparison.equals(this.m_property, selector.m_property) && //
          EComparison.equals(this.m_grouper, selector.m_grouper));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final PropertyValueGroups compute(final IExperimentSet data,
      final Logger logger) {
    IProperty property;

    property = data.getFeatures().find(this.m_property);
    if (property == null) {
      property = data.getParameters().find(this.m_property);
      if (property == null) {
        throw new IllegalStateException(((//
        "Property '" + this.m_property) + //$NON-NLS-1$
            "' not known to experiment set ") //$NON-NLS-1$
            + data);
      }
    }

    return this.m_grouper.compute(property, logger);
  }

  /**
   * Configure a property value selector
   *
   * @param data
   *          the data
   * @param config
   *          the configuration
   * @return the selector, or {@code null} if none was specified
   */
  public static final PropertyValueSelector configure(
      final IExperimentSet data, final Configuration config) {
    final String groupBy;
    IProperty property;

    if (config == null) {
      throw new IllegalArgumentException(//
          "Configuration cannot be null."); //$NON-NLS-1$
    }
    if (data == null) {
      throw new IllegalArgumentException(//
          "IExperimentSet cannot be null."); //$NON-NLS-1$
    }

    groupBy = config.getString(PropertyValueSelector.PARAM_GROUP_BY, null);
    if (groupBy == null) {
      return null;
    }

    property = data.getFeatures().find(groupBy);
    if (property == null) {
      property = data.getParameters().find(groupBy);
      if (property == null) {
        throw new IllegalArgumentException(((//
        "Cannot find property '" + groupBy) //$NON-NLS-1$
            + '\'') + '.');
      }
    }

    return new PropertyValueSelector(property.getName(),
        PropertyValueGrouper.configure(property, config));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_property + " by " + this.m_grouper.toString();//$NON-NLS-1$
  }
}
