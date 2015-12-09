package org.optimizationBenchmarking.utils.ml.clustering.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/** The clustering tool */
public interface IClusterer extends ITool {

  /** {@inheritDoc} */
  @Override
  public abstract IClusteringJobBuilder use();
}
