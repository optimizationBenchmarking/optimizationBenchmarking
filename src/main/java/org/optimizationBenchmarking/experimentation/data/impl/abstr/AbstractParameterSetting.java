package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting}
 * interface.
 */
public class AbstractParameterSetting extends AbstractPropertySetting
implements IParameterSetting {

  /**
   * Create the abstract property setting
   */
  protected AbstractParameterSetting() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Iterator<IParameterValue> iterator() {
    return ((Iterator) (this.entrySet().iterator()));
  }

}