package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;

/** a parameter selection */
final class _ParameterSelection extends
    _PropertySelection<IParameterSet, IParameter, IParameterValue> {

  /**
   * create the parameter selection
   *
   * @param set
   *          the original set
   */
  _ParameterSelection(final IParameterSet set) {
    super(set);
  }

  /** {@inheritDoc} */
  @Override
  final _Selection<IParameter, IParameterValue> _createSelection(
      final IParameter element) {
    return new _ParameterValueSelection(element);
  }

  /** {@inheritDoc} */
  @Override
  final IParameterSet _shadow(final IParameterSet original,
      final Collection<IParameter> elements) {
    return new ShadowParameterSet(null, original, elements);
  }
}
