package org.optimizationBenchmarking.experimentation.evaluation.spec;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;

/** Build the data sources. */
public interface IDataBuilder extends IEvaluationElement {

  /**
   * Add a given experiment set.
   * 
   * @param data
   *          the experiment set
   */
  public abstract void addExperimentSet(final ExperimentSet data);

  /**
   * Add a driver-based data source
   * 
   * @return the builder for the driver-based data source
   */
  public abstract IDriverBasedDataBuilder addDriverBasedDataSource();
}