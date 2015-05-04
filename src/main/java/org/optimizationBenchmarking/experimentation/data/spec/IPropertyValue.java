package org.optimizationBenchmarking.experimentation.data.spec;

/**
 * The interface for property values.
 */
public interface IPropertyValue extends INamedElement {

  /**
   * Get the owning property
   *
   * @return the owning property
   */
  @Override
  public abstract IProperty getOwner();

  /**
   * Get the property value
   *
   * @return the property value
   */
  public abstract Object getValue();

  /**
   * Is the value generalized?
   *
   * @return {@code true} if this is the generalized value of the property
   *         returned by {@link #getOwner()}, {@code false} otherwise
   */
  public abstract boolean isGeneralized();
}
