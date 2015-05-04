package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * The base class for figures
 */
public class Figure extends BasicFigure {

  /**
   * Create a figure
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
  protected Figure(final SectionBody owner, final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    super(owner, useLabel, size, path, index);
  }

  /** {@inheritDoc} */
  @Override
  final ELabelType _labelType() {
    return ELabelType.FIGURE;
  }

  /**
   * Get the owning section body
   *
   * @return the owning section body
   */
  @Override
  protected SectionBody getOwner() {
    return ((SectionBody) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  final FigureCaption createCaption() {
    return this.m_driver.createFigureCaption(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FigureCaption caption() {
    return ((FigureCaption) (super.caption()));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof FigureCaption)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof FigureCaption)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof FigureCaption)) {
      this.throwChildNotAllowed(child);
    }
  }
}
