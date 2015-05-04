package org.optimizationBenchmarking.experimentation.data.spec;

/**
 * A benchmark instance.
 */
public interface IInstance extends INamedElement {

  /**
   * Get the owning instance set
   *
   * @return the owning instance set
   */
  @Override
  public abstract IInstanceSet getOwner();

  /**
   * Get the features of this instance
   *
   * @return the features of this instance
   */
  public abstract IFeatureSetting getFeatureSetting();

  /**
   * Get the upper boundary of a given dimension for this benchmark
   * instance
   *
   * @param dim
   *          the dimension
   * @return the upper boundary for dimension {@code dim} for this
   *         benchmark instance
   */
  public abstract Number getUpperBound(final IDimension dim);

  /**
   * Get the lower boundary of a given dimension for this benchmark
   * instance
   *
   * @param dim
   *          the dimension
   * @return the lower boundary for dimension {@code dim} for this
   *         benchmark instance
   */
  public abstract Number getLowerBound(final IDimension dim);
}
