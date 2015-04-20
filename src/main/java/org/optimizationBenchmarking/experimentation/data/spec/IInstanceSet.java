package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** The interface describing instance sets */
public interface IInstanceSet extends IDataSet {

  /**
   * Get the instances of this instance set
   * 
   * @return instances of this instance set
   */
  @Override
  public abstract ArraySetView<? extends IInstance> getData();
}
