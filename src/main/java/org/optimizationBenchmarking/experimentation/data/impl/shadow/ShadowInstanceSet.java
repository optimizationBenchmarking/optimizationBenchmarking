package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;

/**
 * A shadow instance set is basically a shadow of another instance set with
 * a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that instance set.
 */
public class ShadowInstanceSet extends //
    _ShadowNamedElementSet<IExperimentSet, IInstanceSet, IInstance>
    implements IInstanceSet {

  /**
   * create the shadow instance
   * 
   * @param owner
   *          the owning instance set
   * @param shadow
   *          the instance to shadow
   * @param selection
   *          the selection of instances
   */
  public ShadowInstanceSet(final IExperimentSet owner,
      final IInstanceSet shadow,
      final Collection<? extends IInstance> selection) {
    super(owner, shadow, selection);
  }

  /** {@inheritDoc} */
  @Override
  final IInstance _shadow(final IInstance element) {
    return new ShadowInstance(this, element);
  }

}
