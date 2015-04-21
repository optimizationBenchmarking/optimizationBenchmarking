package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * The basic interface for properties.
 */
public interface IProperty extends IElementSet, INamedElement {

  /**
   * Get the owning property set
   * 
   * @return the owning property set
   */
  @Override
  public abstract IPropertySet getOwner();

  /**
   * Get the primitive type of this property, or {@code null} if the
   * property values are strings.
   * 
   * @return the property type
   */
  public abstract EPrimitiveType getPrimitiveType();

  /**
   * Get the set of property values
   * 
   * @return the set of property values
   */
  @Override
  public abstract ArraySetView<? extends IPropertyValue> getData();

  /**
   * Obtain a property value fitting to a given value object
   * 
   * @param value
   *          the property value object
   * @return the property value, or {@code null} if none could be found
   */
  public abstract IPropertyValue findValue(final Object value);

  /**
   * The property value record indicating generalization
   * 
   * @return the property value record indicating generalization
   */
  public abstract IPropertyValue getGeneralized();
}
