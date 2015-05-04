package org.optimizationBenchmarking.experimentation.data.spec;

/**
 * The basic interface of the experiment data API.
 */
public interface IDataElement {

  /**
   * Get the owning data element, or {@code null} if this element is not
   * owned by anything else.
   *
   * @return the owned data element
   */
  public abstract IDataElement getOwner();

}
