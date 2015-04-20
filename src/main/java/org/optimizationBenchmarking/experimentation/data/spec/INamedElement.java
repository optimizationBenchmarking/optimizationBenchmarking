package org.optimizationBenchmarking.experimentation.data.spec;

/**
 * This interface is common to all elements which have a name.
 */
public interface INamedElement extends IDataElement {
  /**
   * Obtain the name of this object.
   * 
   * @return the name of this object.
   */
  public abstract String getName();

  /**
   * Obtain the description of this object.
   * 
   * @return the description of this object, or {@code null} if none is
   *         specified
   */
  public abstract String getDescription();
}
