package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for figure series
 */
public class FigureSeries extends ComplexObject implements IFigureSeries {

  /** the state when the caption has been created */
  private static final int STATE_CAPTION_CREATED = (DocumentElement.STATE_MAX_ELEMENT + 1);
  /** the state before the caption */
  private static final int STATE_CAPTION_BEFORE_OPEN = (FigureSeries.STATE_CAPTION_CREATED + 1);
  /** the state in the caption */
  private static final int STATE_CAPTION_OPENED = (FigureSeries.STATE_CAPTION_BEFORE_OPEN + 1);
  /** the state after the caption */
  private static final int STATE_CAPTION_CLOSED = (FigureSeries.STATE_CAPTION_OPENED + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[FigureSeries.STATE_CAPTION_CLOSED + 1];

    FigureSeries.STATE_NAMES[FigureSeries.STATE_CAPTION_CREATED] = "captionCreated"; //$NON-NLS-1$
    FigureSeries.STATE_NAMES[FigureSeries.STATE_CAPTION_BEFORE_OPEN] = "captionBeforeOpen"; //$NON-NLS-1$
    FigureSeries.STATE_NAMES[FigureSeries.STATE_CAPTION_OPENED] = "captionOpened"; //$NON-NLS-1$
    FigureSeries.STATE_NAMES[FigureSeries.STATE_CAPTION_CLOSED] = "captionClosed"; //$NON-NLS-1$    
  }

  /** the current sub-figure index */
  private int m_curFig;

  /** the size template of this figure */
  private final EFigureSize m_size;

  /** a path */
  final Path m_path;

  /**
   * Create a figure series
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
  public FigureSeries(final SectionBody owner, final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    super(owner, useLabel, index);

    if (size == null) {
      throw new IllegalArgumentException(//
          "Figure series size must not be null."); //$NON-NLS-1$
    }

    this.m_size = size;
    this.m_path = DocumentElement._resolve(this.m_doc.m_basePath,//
        (BasicFigure.GRAPHICS_OFFSET + ((path == null) ? this.m_globalID
            : path.toString())));
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= FigureSeries.STATE_CAPTION_CREATED)
        && (state < FigureSeries.STATE_NAMES.length)) {
      sb.append(FigureSeries.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /**
   * Obtain the size of this figure, relative to the parent document
   * 
   * @return the size of this figure
   */
  public final EFigureSize getSize() {
    return this.m_size;
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

  /**
   * Create the figure caption
   * 
   * @return the figure caption
   */
  protected FigureSeriesCaption createCaption() {
    return new FigureSeriesCaption(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FigureSeriesCaption caption() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        FigureSeries.STATE_CAPTION_CREATED);
    return this.createCaption();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof FigureSeriesCaption) {
      this.fsmStateAssertAndSet(FigureSeries.STATE_CAPTION_CREATED,
          FigureSeries.STATE_CAPTION_BEFORE_OPEN);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof FigureSeriesCaption) {
      this.fsmStateAssertAndSet(FigureSeries.STATE_CAPTION_BEFORE_OPEN,
          FigureSeries.STATE_CAPTION_OPENED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof FigureSeriesCaption) {
      this.fsmStateAssertAndSet(FigureSeries.STATE_CAPTION_OPENED,
          FigureSeries.STATE_CAPTION_CLOSED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(FigureSeries.STATE_CAPTION_CLOSED,
        DocumentElement.STATE_DEAD);
    if (this.m_curFig <= 0) {
      throw new IllegalStateException(//
          "A figure series must have at least one sub-figure."); //$NON-NLS-1$
    }
    super.onClose();
  }

  /**
   * Create a sub-figure
   * 
   * @param useLabel
   *          the label to use, or {@code null} if none is needed
   * @param path
   *          relative path
   * @param index
   *          the index of the sub-figure
   * @return the new sub-figure
   */
  protected SubFigure createFigure(final ILabel useLabel,
      final String path, final int index) {
    return new SubFigure(this, useLabel, this.m_size, path, index);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final SubFigure figure(final ILabel useLabel,
      final String path) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFigure(useLabel, path, (++this.m_curFig));
  }

  /** {@inheritDoc} */
  @Override
  final ELabelType _labelType() {
    return ELabelType.FIGURE;
  }

  /**
   * Get the (current) number of sub-figures
   * 
   * @return the (current) number of sub-figures
   */
  public final int getFigureCount() {
    return this.m_curFig;
  }

}
