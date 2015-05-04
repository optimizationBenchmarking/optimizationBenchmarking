package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;

/** a selection of instances */
final class _InstanceSelection extends
    _PlainSelection<IInstanceSet, IInstance> {

  /**
   * Create the instance selection
   *
   * @param set
   *          the instance set
   */
  _InstanceSelection(final IInstanceSet set) {
    super(set);
  }

  /** {@inheritDoc} */
  @Override
  final IInstanceSet _shadow(final IInstanceSet original,
      final Collection<IInstance> elements) {
    return new ShadowInstanceSet(null, original, elements);
  }
}
