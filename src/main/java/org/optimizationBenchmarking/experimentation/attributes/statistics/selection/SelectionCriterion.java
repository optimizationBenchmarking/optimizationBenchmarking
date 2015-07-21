package org.optimizationBenchmarking.experimentation.attributes.statistics.selection;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.hash.HashObject;

/** a selection criterion is used to find a specific data point */
public abstract class SelectionCriterion extends HashObject {

  /** create */
  public SelectionCriterion() {
    super();
  }

  /**
   * Get the earliest data point meeting the selection criterion from the
   * given run, or {@code null} if no such data point was found.
   *
   * @param run
   *          the run
   * @return the earliest data point meeting this selection criterion, or
   *         {@code null} if none was found
   */
  public abstract IDataPoint get(final IRun run);
}
