package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.Iterator;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;

/** an iterator of experiment set creators */
final class _ExperimentSetCreatorIterator extends
    BasicIterator<ExperimentSet> {

  /** the inner iterator */
  private final Iterator<Class<? extends ExperimentSetCreator>> m_it;

  /**
   * create
   * 
   * @param it
   *          the iterator
   */
  _ExperimentSetCreatorIterator(
      final Iterator<Class<? extends ExperimentSetCreator>> it) {
    super();
    this.m_it = it;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return this.m_it.hasNext();
  }

  /** {@inheritDoc} */
  @Override
  public final ExperimentSet next() {
    try {
      return this.m_it.next().newInstance().getInstance();
    } catch (final Throwable t) {
      throw new RuntimeException(
          "Creation of experiment set or experiment set creator failed. Odd, eh?"); //$NON-NLS-1$
    }
  }
}
