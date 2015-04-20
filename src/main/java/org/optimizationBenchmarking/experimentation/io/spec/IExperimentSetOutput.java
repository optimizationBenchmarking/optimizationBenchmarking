package org.optimizationBenchmarking.experimentation.io.spec;

import org.optimizationBenchmarking.utils.io.structured.spec.IOutputTool;

/**
 * An interface able to store experiment data. Basically, an experiment
 * output tool <em>may</em> be able to store any kind of object from the
 * experiment data API (hence the open generic parameter {@code T}).
 * However, it <em>must</em> support storing instances of
 * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet}
 * .
 * 
 * @param <T>
 *          the experiment data item type which can be stored
 */
public interface IExperimentSetOutput<T> extends IOutputTool<T> {
  //
}
