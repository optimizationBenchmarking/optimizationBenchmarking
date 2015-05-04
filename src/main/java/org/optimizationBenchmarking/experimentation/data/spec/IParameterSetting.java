package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.Iterator;

/** A concrete setting of experiment parameters. */
public interface IParameterSetting extends IPropertySetting,
Iterable<IParameterValue> {

  /**
   * Iterate over the parameter values in this setting.
   *
   * @return an {@link java.util.Iterator} over the parameter values in
   *         this setting.
   */
  @Override
  public abstract Iterator<IParameterValue> iterator();
}
