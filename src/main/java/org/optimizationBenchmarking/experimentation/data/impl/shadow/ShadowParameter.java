package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * A shadow parameter is basically a shadow of another parameter with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that parameter.
 */
public class ShadowParameter extends
    _ShadowProperty<IParameterSet, IParameter, IParameterValue> implements
    IParameter {

  /** the unspecified parameter value */
  private IParameterValue m_unspecified;

  /** do we have the unspecified parameter value? */
  private boolean m_hasUnspecified;

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
  }

  /** {@inheritDoc} */
  @Override
  void _checkDiscardOrig() {
    if ((this.m_data != null) && (this.m_general != null)
        && (this.m_hasUnspecified)) {
      this.m_orig = null;
    }
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

    value = this.m_unspecified;
    if (value == null) {
      if (!(this.m_hasUnspecified)) {
        this.m_hasUnspecified = true;
        origVal = this.m_orig.getUnspecified();
        if (origVal != null) {
          this.m_unspecified = value = this._shadow(origVal);
        }
        this._checkDiscardOrig();
      }
    }

    return value;
  }

  /** {@inheritDoc} */
  @Override
  public final IParameterValue findValue(final Object value) {
    IParameterValue unspec, result;

    result = super.findValue(value);
    if (result != null) {
      return result;
    }

    unspec = this.getUnspecified();
    if ((unspec != null)
        && ((value == unspec) || (EComparison.equals(unspec.getValue(),
            value)))) {
      return unspec;
    }
    return null;
  }

}
