package org.optimizationBenchmarking.utils.graphics;

/** A common interface for output media */
public interface IMedium {

  /**
   * Get the physical dimension of the page size of the default
   * configuration of this medium
   *
   * @return the physical dimension of a page
   */
  public abstract PhysicalDimension getPageSize();

}
