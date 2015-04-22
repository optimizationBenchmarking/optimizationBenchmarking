package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A shadow instance run set is basically a shadow of another instance run
 * set with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that instance run set.
 */
public class ShadowInstanceRuns extends //
    _ShadowElementSet<IExperiment, IInstanceRuns, IRun> implements
    IInstanceRuns {

  /** the instance */
  private IInstance m_instance;

  /**
   * create the shadow instance runs
   * 
   * @param owner
   *          the owning element
   * @param shadow
   *          the instance runs to shadow
   * @param selection
   *          the selection of runs
   */
  public ShadowInstanceRuns(final IExperiment owner,
      final IInstanceRuns shadow,
      final Collection<? extends IRun> selection) {
    super(owner, shadow, selection);
  }

  /** {@inheritDoc} */
  @Override
  final IRun _shadow(final IRun element) {
    return new ShadowRun(this, element);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IInstance getInstance() {
    if (this.m_instance == null) {
      this.m_instance = this.getOwner().getOwner().getInstances().find(//
          this.m_shadowUnpacked.getInstance().getName());
    }
    return this.m_instance;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canDelegateAttributesTo(final IInstanceRuns shadow) {
    final ArrayListView<? extends IRun> mine, yours;
    int size;

    mine = this.getData();
    yours = shadow.getData();

    size = mine.size();
    if (size != yours.size()) {
      return false;
    }

    for (final IRun run : mine) {
      if (!(yours.contains(((ShadowRun) run).m_shadowUnpacked))) {
        return false;
      }
    }

    return true;
  }
}
