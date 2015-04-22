package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;

/**
 * A shadow parameter is basically a shadow of another parameter with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that parameter.
 */
public class ShadowParameter extends
    _ShadowProperty<IParameterSet, IParameter, IParameterValue> implements
    IParameter {

  /** the unspecified value has not yet been set */
  static final int NEEDS_UNSPECIFIED = (_ShadowProperty.NEEDS_DATA << 1);

  /** the unspecified parameter value */
  private IParameterValue m_unspecified;

  /**
   * create the shadow parameter value
   * 
   * @param owner
   *          the owning parameter
   * @param shadow
   *          the parameter value to shadow
   * @param selection
   *          the selected parameter values
   */
  public ShadowParameter(final IParameterSet owner,
      final IParameter shadow,
      final Collection<? extends IParameterValue> selection) {
    super(owner, shadow, selection);
    this.m_origState |= 4;
  }

  /** {@inheritDoc} */
  @Override
  final IParameterValue _shadow(final IParameterValue value) {
    return new ShadowParameterValue(this, value);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IParameterValue getUnspecified() {
    final IParameterValue origVal;
    IParameterValue value;
    int state;

    value = this.m_unspecified;
    if (value == null) {
      if (this.m_orig != null) {
        state = this.m_origState;
        if ((state & ShadowParameter.NEEDS_UNSPECIFIED) != 0) {
          origVal = this.m_orig.getUnspecified();
          if (origVal != null) {
            this.m_unspecified = value = this._shadow(origVal);
          }
          if ((this.m_origState = (state & (~ShadowParameter.NEEDS_UNSPECIFIED))) == 0) {
            this.m_orig = null;
          }
        }
      }
    }

    return value;
  }

}
