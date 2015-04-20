package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A set of named elements.
 */
public interface INamedElementSet extends IDataSet {
  /**
   * Get the data elements of this data set
   * 
   * @return the data elements of this data set
   */
  @Override
  public abstract ArraySetView<? extends INamedElement> getData();

  /**
   * Find the element with the given name
   * 
   * @param name
   *          the name
   * @return the element, or {@code null} if it could not be found
   */
  public abstract INamedElement find(final String name);
}
