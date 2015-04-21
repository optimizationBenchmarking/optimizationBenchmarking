package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A set of data elements.
 */
public interface IElementSet extends IDataElement {

  /**
   * Get the data elements of this data set
   * 
   * @return the data elements of this data set
   */
  public abstract ArraySetView<?> getData();

}
