package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** The interface describing instance sets */
public interface IInstanceSet extends INamedElementSet {

  /**
   * Get the instances of this instance set
   *
   * @return instances of this instance set
   */
  @Override
  public abstract ArrayListView<? extends IInstance> getData();

  /**
   * Get the owning experiment set
   *
   * @return the owning experiment set
   */
  @Override
  public abstract IExperimentSet getOwner();

  /**
   * Find the instance of the given name
   *
   * @param name
   *          the name of the instance to find
   * @return the instance, or {@code null} if none could be found
   */
  @Override
  public abstract IInstance find(final String name);
}
