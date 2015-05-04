package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.Iterator;
import java.util.Map;

/**
 * A property setting defines a specific setting of property values.
 */
public interface IPropertySetting extends Map<IProperty, Object>,
    Comparable<IPropertySetting> {

  /**
   * Iterate over the property values in this setting.
   *
   * @return an {@link java.util.Iterator} over the property values in this
   *         setting.
   */
  public abstract Iterator<? extends IPropertyValue> iterator();

  /**
   * Check if this property set is the same or a super-set of another
   * setting.
   *
   * @param setting
   *          the other setting
   * @return {@code true} if this set here either specifies the same
   *         property values as {@code setting} or is more general (does
   *         not specify some settings)
   */
  public abstract boolean subsumes(final IPropertySetting setting);

  /**
   * Does this setting contain at least one generalized value?
   *
   * @return {@code true} if and only if this setting contains at least one
   *         generalized value, {@code false} otherwise
   */
  public abstract boolean isGeneralized();

  /**
   * Check whether a given property has a value in the configuration of
   * this experiment
   *
   * @param property
   *          the property
   * @return {@code true} if the property has a value, {@code false}
   *         otherwise
   */
  public abstract boolean specifies(final IProperty property);

  /**
   * Check whether a given property value is contained in the property
   * value set.
   *
   * @param value
   *          the property value
   * @return {@code true} if the property value is contained, {@code false}
   *         otherwise
   */
  public abstract boolean contains(final IPropertyValue value);

}
