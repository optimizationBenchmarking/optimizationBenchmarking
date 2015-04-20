package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** A set of runs, grouped for whatever reason. */
public interface IRuns extends IDataSet {

  /**
   * Get the runs of this instance runs set
   * 
   * @return the runs of this instance runs set
   */
  @Override
  public abstract ArraySetView<? extends IRun> getData();
}
