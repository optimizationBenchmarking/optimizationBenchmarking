package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigure;
import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigureCaption;
import org.optimizationBenchmarking.utils.document.object.PathEntry;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a sub-figure in a XHTML document */
final class _XHTML10SubFigure extends SubFigure {

  /** the caption */
  private char[] m_caption;

  /**
   * Create a new sub figure
   * 
   * @param owner
   *          the owning section body
   * @param useLabel
   *          the label to use
   * @param path
   *          the path suggestion
   */
  public _XHTML10SubFigure(final _XHTML10FigureSeries owner,
      final ILabel useLabel, final String path) {
    super(owner, useLabel, path);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean mustChildBeBuffered(final HierarchicalText child) {
    return (child instanceof SubFigureCaption);
  }

  /** {@inheritDoc} */
  @Override
  protected final void processBufferedOutputFromChild(
      final HierarchicalText child, final MemoryTextOutput out) {
    if (child instanceof SubFigureCaption) {
      this.m_caption = out.toChars();
    } else {
      super.processBufferedOutputFromChild(child, out);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void onFigureClose(final PhysicalDimension size,
      final ArrayListView<PathEntry> files) {

    super.onFigureClose(size, files);

    if (files.isEmpty()) {
      throw new IllegalStateException("No figure file generated?!"); //$NON-NLS-1$
    }

    ((_XHTML10FigureSeries) (this.getOwner()))
        ._subFigure(new _SubFigureDesc(this.m_caption, size, files.get(0)
            .getValue(), this.getLabel(), this.getGlobalID()));
    this.m_caption = null;
  }
}
