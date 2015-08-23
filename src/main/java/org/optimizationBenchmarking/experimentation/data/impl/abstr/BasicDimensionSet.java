package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet}
 * interface.
 */
public class BasicDimensionSet extends AbstractDimensionSet {

  /** the internal list view with the dimensions */
  private final ArrayListView<IDimension> m_dimensions;

  /**
   * Create the abstract dimension set. If {@code owner==null}, you must
   * later set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractDimensionSet)}
   * .
   *
   * @param owner
   *          the owner
   * @param dimensions
   *          the dimensions
   */
  public BasicDimensionSet(final IExperimentSet owner,
      final Collection<IDimension> dimensions) {
    super(owner);

    IDimension d1;
    int i1;

    this.m_dimensions = ArrayListView.collectionToView(dimensions);
    for (i1 = this.m_dimensions.size(); (--i1) >= 0;) {
      d1 = this.m_dimensions.get(i1);
      if (d1 instanceof AbstractDimension) {
        this.own((AbstractDimension) d1);
        if (d1.getIndex() != i1) {
          throw new IllegalStateException(//
              "Dimension index is wrong. It should be " + i1 + //$NON-NLS-1$
                  ", but is " + d1.getIndex()); //$NON-NLS-1$
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<? extends IDimension> getData() {
    return this.m_dimensions;
  }
}
