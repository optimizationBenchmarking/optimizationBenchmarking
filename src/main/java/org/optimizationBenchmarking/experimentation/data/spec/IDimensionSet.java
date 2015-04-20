package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * The set of measurement dimensions.
 */
public interface IDimensionSet extends INamedElementSet {
  /**
   * Get the dimensions of this dimension set
   * 
   * @return the dimensions of this dimension set
   */
  @Override
  public abstract ArraySetView<? extends IDimension> getData();

  /**
   * Find the dimension with the given name
   * 
   * @param name
   *          the name
   * @return the dimension, or {@code null} if it could not be found
   */
  @Override
  public abstract IDimension find(final String name);

}
