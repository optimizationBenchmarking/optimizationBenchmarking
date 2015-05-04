package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IFeatureSetting}
 * interface.
 */
public class AbstractFeatureSetting extends AbstractPropertySetting
    implements IFeatureSetting {

  /**
   * Create the abstract property setting
   */
  protected AbstractFeatureSetting() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Iterator<IFeatureValue> iterator() {
    return ((Iterator) (this.entrySet().iterator()));
  }

}