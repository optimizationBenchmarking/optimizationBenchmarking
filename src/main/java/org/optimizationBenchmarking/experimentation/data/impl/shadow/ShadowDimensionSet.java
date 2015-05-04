package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;

/**
 * A shadow dimension set is basically a shadow of another dimension set
 * with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that dimension set.
 */
public class ShadowDimensionSet extends //
    _ShadowNamedElementSet<IExperimentSet, IDimensionSet, IDimension>
    implements IDimensionSet {

  /**
   * create the shadow dimension
   *
   * @param owner
   *          the owning dimension set
   * @param shadow
   *          the dimension to shadow
   * @param selection
   *          the selection of dimensions
   */
  public ShadowDimensionSet(final IExperimentSet owner,
      final IDimensionSet shadow,
      final Collection<? extends IDimension> selection) {
    super(owner, shadow, selection);
  }

  /** {@inheritDoc} */
  @Override
  final IDimension _shadow(final IDimension element) {
    return new ShadowDimension(this, element);
  }

}
