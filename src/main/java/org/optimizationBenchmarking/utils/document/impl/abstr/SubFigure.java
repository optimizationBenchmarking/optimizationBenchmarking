package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
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
   * @param index
   *          the figure index in the owning section
   * @param useLabel
   *          the label to use
   * @param size
   *          the figure size
   * @param path
   *          the path suggestion
   */
  public SubFigure(final FigureSeries owner, final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    super(owner, useLabel, size, path, index);
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
  protected SubFigureCaption createCaption() {
    return new SubFigureCaption(this);
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
