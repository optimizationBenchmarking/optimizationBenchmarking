package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * The base class for sub-figures
 */
public class SubFigure extends BasicFigure {

  /**
   * Create a sub-figure
   *
   * @param owner
   *          the owning section body
   * @param useLabel
   *          the label to use
   * @param path
   *          the path suggestion
   */
  protected SubFigure(final FigureSeries owner, final ILabel useLabel,
      final String path) {
    super(owner, useLabel, owner.m_size, path, owner.m_curFig);
  }

  /** {@inheritDoc} */
  @Override
  final ELabelType _labelType() {
    return ELabelType.SUBFIGURE;
  }

  /**
   * Get the owning section body
   *
   * @return the owning section body
   */
  @Override
  protected FigureSeries getOwner() {
    return ((FigureSeries) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  final SubFigureCaption createCaption() {
    return this.m_driver.createSubFigureCaption(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final SubFigureCaption caption() {
    return ((SubFigureCaption) (super.caption()));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof SubFigureCaption)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof SubFigureCaption)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof SubFigureCaption)) {
      this.throwChildNotAllowed(child);
    }
  }
}
