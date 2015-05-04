package org.optimizationBenchmarking.experimentation.data.spec;

/**
 * The value of a feature of a benchmark instance.
 */
public interface IFeatureValue extends IPropertyValue {
  /**
   * get the owning feature
   *
   * @return the owning feature
   */
  @Override
  public abstract IFeature getOwner();

}
