package test.junit.org.optimizationBenchmarking.experimentation.evaluation.data;

import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;

/** an iterable for experiment sets */
final class _ExperimentSetCreatorIterable implements
    Iterable<ExperimentSet> {

  /** create */
  _ExperimentSetCreatorIterable() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Iterator<ExperimentSet> iterator() {
    return new _ExperimentSetCreatorIterator(
        ExperimentSetCreator.AVAILABLE_CREATORS.iterator());
  }
}
