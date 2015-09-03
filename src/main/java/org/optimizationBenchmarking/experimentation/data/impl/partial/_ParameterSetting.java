package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.ArrayList;
import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractParameterSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** the internal parameter setting container */
final class _ParameterSetting extends AbstractParameterSetting {

  /** the parameter list */
  private final ArrayList<_ParameterValue> m_parameters;

  /** create */
  _ParameterSetting() {
    super();
    this.m_parameters = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Iterator<IParameterValue> iterator() {
    return ((Iterator) (this.m_parameters.iterator()));
  }

  /**
   * Set the given parameter
   *
   * @param parameterValue
   *          the parameter value
   */
  final void _setParameterValue(final _ParameterValue parameterValue) {
    final IParameter parameter;

    parameter = parameterValue.getOwner();

    for (final _ParameterValue fvalue : this.m_parameters) {
      if (EComparison.equals(fvalue.getOwner(), parameter)) {
        if (fvalue == parameterValue) {
          return;
        }
        throw new IllegalStateException("Parameter '" + //$NON-NLS-1$
            parameter.getName() + //
            "' has already been set to '" + fvalue.getName() + //$NON-NLS-1$
            "' and cannot be set to '" + parameterValue.getName() + //$NON-NLS-1$
            '\'' + '.');
      }
    }

    this.m_parameters.add(parameterValue);
  }
}
