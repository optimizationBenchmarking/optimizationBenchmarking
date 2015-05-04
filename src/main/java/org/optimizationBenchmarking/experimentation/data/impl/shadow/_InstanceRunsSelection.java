package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;

/** a run selection */
final class _InstanceRunsSelection extends
    _PlainSelection<IExperiment, IInstanceRuns> {

  /**
   * create the experiment selection
   *
   * @param experiment
   *          the original experiment
   */
  _InstanceRunsSelection(final IExperiment experiment) {
    super(experiment);
  }

  /** {@inheritDoc} */
  @Override
  final IExperiment _shadow(final IExperiment original,
      final Collection<IInstanceRuns> elements) {
    return new ShadowExperiment(null, original, elements);
  }
}
