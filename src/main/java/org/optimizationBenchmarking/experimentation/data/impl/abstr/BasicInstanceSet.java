package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet}
 * interface.
 */
public class BasicInstanceSet extends AbstractInstanceSet {

  /** the internal list view with the instances */
  private final ArrayListView<IInstance> m_instances;

  /**
   * Create the abstract instance set. If {@code owner==null}, you must
   * later set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractInstanceSet)}
   * .
   *
   * @param owner
   *          the owner
   * @param instances
   *          the instances
   */
  public BasicInstanceSet(final IExperimentSet owner,
      final Collection<IInstance> instances) {
    super(owner);

    this.m_instances = ArrayListView.collectionToView(instances);
    for (final IInstance inst : this.m_instances) {
      if (inst instanceof AbstractInstance) {
        this.own((AbstractInstance) inst);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<? extends IInstance> getData() {
    return this.m_instances;
  }
}
