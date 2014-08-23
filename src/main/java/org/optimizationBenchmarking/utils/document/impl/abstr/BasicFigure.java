package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.Graphic;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IGraphicListener;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for figures or sub-figures
 */
public abstract class BasicFigure extends ComplexObject implements IFigure {

  /** the state when the caption has been created */
  private static final int STATE_CAPTION_CREATED = (DocumentElement.STATE_MAX_ELEMENT + 1);
  /** the state before the caption */
  private static final int STATE_CAPTION_BEFORE_OPEN = (BasicFigure.STATE_CAPTION_CREATED + 1);
  /** the state in the caption */
  private static final int STATE_CAPTION_OPENED = (BasicFigure.STATE_CAPTION_BEFORE_OPEN + 1);
  /** the state after the caption */
  private static final int STATE_CAPTION_CLOSED = (BasicFigure.STATE_CAPTION_OPENED + 1);

  /** the state when the graphic has been created */
  private static final int STATE_GRAPHIC_CREATED = (BasicFigure.STATE_CAPTION_CLOSED + 1);
  /** the state after the graphic */
  private static final int STATE_GRAPHIC_CLOSED = (BasicFigure.STATE_GRAPHIC_CREATED + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[BasicFigure.STATE_GRAPHIC_CLOSED + 1];

    BasicFigure.STATE_NAMES[BasicFigure.STATE_CAPTION_CREATED] = "captionCreated"; //$NON-NLS-1$
    BasicFigure.STATE_NAMES[BasicFigure.STATE_CAPTION_BEFORE_OPEN] = "captionBeforeOpen"; //$NON-NLS-1$
    BasicFigure.STATE_NAMES[BasicFigure.STATE_CAPTION_OPENED] = "captionOpened"; //$NON-NLS-1$
    BasicFigure.STATE_NAMES[BasicFigure.STATE_CAPTION_CLOSED] = "captionClosed"; //$NON-NLS-1$    
    BasicFigure.STATE_NAMES[BasicFigure.STATE_GRAPHIC_CREATED] = "graphicCreated"; //$NON-NLS-1$
    BasicFigure.STATE_NAMES[BasicFigure.STATE_GRAPHIC_CLOSED] = "graphicClosed"; //$NON-NLS-1$    
  }

  /** the default figure path offset for graphics */
  static final String GRAPHICS_OFFSET = "graphics/"; //$NON-NLS-1$

  /** the size template of this figure */
  private final EFigureSize m_size;

  /** a path */
  private final Path m_path;

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
  BasicFigure(final DocumentPart owner, final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    super(owner, useLabel, index);

    if (size == null) {
      throw new IllegalArgumentException(//
          "Figure size must not be null."); //$NON-NLS-1$
    }

    final Path ownerPath;
    String rpath;

    rpath = ((path == null) ? this.m_globalID : path);
    if (owner instanceof SectionBody) {
      ownerPath = this.m_doc.m_basePath;
      rpath = (BasicFigure.GRAPHICS_OFFSET + rpath);
    } else {
      if (owner instanceof FigureSeries) {
        ownerPath = ((FigureSeries) owner).m_path;
      } else {
        throw new IllegalArgumentException(//
            "The owner of a figure must be either a section body or a figure series, but you specified a "//$NON-NLS-1$ 
                + owner);
      }
    }

    this.m_size = size;
    this.m_path = DocumentElement._resolve(ownerPath, rpath);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= BasicFigure.STATE_CAPTION_CREATED)
        && (state < BasicFigure.STATE_NAMES.length)) {
      sb.append(BasicFigure.STATE_NAMES[state]);
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
   * Create the figure caption
   * 
   * @return the figure caption
   */
  protected BasicFigureCaption createCaption() {
    return new BasicFigureCaption(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized BasicFigureCaption caption() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        BasicFigure.STATE_CAPTION_CREATED);
    return this.createCaption();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof BasicFigureCaption) {
      this.fsmStateAssertAndSet(BasicFigure.STATE_CAPTION_CREATED,
          BasicFigure.STATE_CAPTION_BEFORE_OPEN);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof BasicFigureCaption) {
      this.fsmStateAssertAndSet(BasicFigure.STATE_CAPTION_BEFORE_OPEN,
          BasicFigure.STATE_CAPTION_OPENED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof BasicFigureCaption) {
      this.fsmStateAssertAndSet(BasicFigure.STATE_CAPTION_OPENED,
          BasicFigure.STATE_CAPTION_CLOSED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(BasicFigure.STATE_GRAPHIC_CLOSED,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }

  /**
   * the graphic of this figure has been closed
   * 
   * @param graphic
   *          the graphic
   * @param path
   *          the path to the graphic, or {@code null} if no path was
   *          specified
   */
  protected synchronized void onGraphicClosed(final Graphic graphic,
      final Path path) {
    this.fsmStateAssertAndSet(BasicFigure.STATE_GRAPHIC_CREATED,
        BasicFigure.STATE_GRAPHIC_CLOSED);
  }

  /**
   * Create the graphic object to paint on
   * 
   * @param listener
   *          the listener that needs to be passed to that object in order
   *          for the figure to be notified when it is closed
   * @param path
   *          the suggested path
   * @return the graphic
   */
  protected Graphic createGraphic(final IGraphicListener listener,
      final Path path) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Graphic body() {
    this.fsmStateAssertAndSet(BasicFigure.STATE_CAPTION_CLOSED,
        BasicFigure.STATE_GRAPHIC_CREATED);
    return this.createGraphic(new __GraphicListener(), this.m_path);
  }

  /**
   * The internal dispatcher for graphic listening events
   */
  private final class __GraphicListener implements IGraphicListener {
    /** create */
    __GraphicListener() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void onClosed(final Graphic graphic, final Path path) {
      BasicFigure.this.onGraphicClosed(graphic, path);
    }
  }

}
