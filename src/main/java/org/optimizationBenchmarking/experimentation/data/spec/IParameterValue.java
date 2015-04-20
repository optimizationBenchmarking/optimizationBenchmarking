package org.optimizationBenchmarking.experimentation.data.spec;

/**
 * The value of a parameter of an experiment.
 */
public interface IParameterValue extends IPropertyValue {
  /**
   * get the owning parameter
   * 
   * @return the owning parameter
   */
  @Override
  public abstract IParameter getOwner();

  /**
   * Is this parameter value unspecified?
   * 
   * @return {@code true} if and only if the parameter value is
   *         unspecified, {@code false} otherwise
   */
  public abstract boolean isUnspecified();
}
