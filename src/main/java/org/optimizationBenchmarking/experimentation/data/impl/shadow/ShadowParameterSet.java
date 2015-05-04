package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A shadow parameter set is basically a shadow of another parameter set
 * with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that parameter set.
 */
public class ShadowParameterSet extends
_ShadowPropertySet<IParameterSet, IParameter, IParameterSetting>
implements IParameterSet {
  /**
   * create the shadow parameter set
   *
   * @param owner
   *          the owning parameter
   * @param shadow
   *          the parameter value to shadow
   * @param selection
   *          the selected parameters
   */
  public ShadowParameterSet(final IExperimentSet owner,
      final IParameterSet shadow,
      final Collection<? extends IParameter> selection) {
    super(owner, shadow, selection);
  }

  /** {@inheritDoc} */
  @Override
  final IParameter _shadow(final IParameter element) {
    return new ShadowParameter(this, element, null);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  final IParameterSetting _createSettingFromArray(
      final IPropertyValue[] values) {
    return new _ShadowParameterSetting(new ArraySetView(values));
  }

}
