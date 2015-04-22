package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;

/**
 * A shadow parameter value is basically a shadow of another parameter
 * value with a different owner and potentially different attributes. If
 * all associated data of this element is the same, it will delegate
 * attribute-based computations to that parameter value.
 */
public class ShadowParameterValue extends
    _ShadowPropertyValue<IParameter, IParameterValue> implements
    IParameterValue {

  /**
   * create the shadow parameter value
   * 
   * @param owner
   *          the owning parameter
   * @param shadow
   *          the parameter value to shadow
   */
  public ShadowParameterValue(final IParameter owner,
      final IParameterValue shadow) {
    super(owner, shadow);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isUnspecified() {
    return this.m_shadowUnpacked.isUnspecified();
  }
}
