package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.awt.geom.Rectangle2D;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.DoubleDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

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
  static final String GRAPHICS_OFFSET = "graphics"; //$NON-NLS-1$

  /** the size template of this figure */
  private final EFigureSize m_size;

  /** a path */
  private final Path m_folder;

  /** the name suggestion */
  private final String m_suggestion;

  /** the figure size in points */
  private PhysicalDimension m_figureSize;

  /** the figure files */
  private ArrayListView<Map.Entry<Path, IFileType>> m_figureFiles;

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

    String rpath;

    rpath = ((path == null) ? this.m_globalID : path);
    if (owner instanceof SectionBody) {
      this.m_folder = PathUtils.normalize(this.m_doc.m_basePath
          .resolve(BasicFigure.GRAPHICS_OFFSET));
      this.m_suggestion = rpath;
    } else {
      if (owner instanceof FigureSeries) {
        this.m_folder = ((FigureSeries) owner).m_folder;
        this.m_suggestion = rpath;
      } else {
        throw new IllegalArgumentException(//
            "The owner of a figure must be either a section body or a figure series, but you specified a "//$NON-NLS-1$ 
                + owner);
      }
    }

    this.m_size = size;
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
  abstract BasicFigureCaption createCaption();

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

  /**
   * This method is called when the figure is closed, one single time
   * 
   * @param size
   *          the figure size
   * @param files
   *          a list of the generated files
   */
  protected void onFigureClose(final PhysicalDimension size,
      final ArrayListView<Map.Entry<Path, EGraphicFormat>> files) {
    //
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected synchronized final void onClose() {
    this.fsmStateAssertAndSet(BasicFigure.STATE_GRAPHIC_CLOSED,
        DocumentElement.STATE_DEAD);
    try {
      this.onFigureClose(((this.m_figureSize != null) ? this.m_figureSize
          : DoubleDimension.EMPTY),
          ((this.m_figureFiles != null) ? this.m_figureFiles
              : ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW))));
    } finally {
      this.m_figureFiles = null;
      this.m_figureSize = null;
      super.onClose();
    }
  }

  /**
   * the graphic of this figure has been closed
   * 
   * @param result
   *          the result files
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  synchronized final void _onGraphicClosed(
      final Collection<Map.Entry<Path, IFileType>> result) {
    final Logger log;
    final int size;
    final MemoryTextOutput mto;
    final Map.Entry<Path, EGraphicFormat>[] list, list2;
    int i;
    boolean empty;

    this.fsmStateAssertAndSet(BasicFigure.STATE_GRAPHIC_CREATED,
        BasicFigure.STATE_GRAPHIC_CLOSED);

    log = this.m_doc.m_logger;
    if ((result == null) || ((size = result.size()) <= 0)) {
      empty = true;
      this.m_figureFiles = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    } else {
      this.m_doc.addFiles(result);

      list = new Map.Entry[size];
      i = 0;
      for (final Map.Entry<Path, IFileType> entry : result) {
        if (entry.getValue() instanceof EGraphicFormat) {
          list[i++] = ((Map.Entry) entry);
        }
      }
      if (i >= size) {
        empty = false;
        if (result instanceof ArrayListView) {
          this.m_figureFiles = ((ArrayListView) result);
        } else {
          this.m_figureFiles = ArrayListView.collectionToView(result,
              false);
        }
      } else {
        empty = (i <= 0);
        if (empty) {
          this.m_figureFiles = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
        } else {
          list2 = new Map.Entry[i];
          System.arraycopy(list, 0, list2, 0, i);
          this.m_figureFiles = new ArrayListView(list2);
        }
      }
    }

    if ((log != null) && (log.isLoggable(Level.FINER))) {
      mto = new MemoryTextOutput();
      mto.append("Finished creating graphic ");//$NON-NLS-1$
      if (!empty) {
        this.m_figureFiles.toText(mto);
      }
      mto.append(" of ");//$NON-NLS-1$
      mto.append(this._getType());
      if (empty) {
        mto.append(", but no file was produced. (Was this intentionally? If not, something may be wrong.)");//$NON-NLS-1$
      } else {
        mto.append('.');
      }

      log.finer(mto.toString());
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Graphic body() {
    final Graphic g;
    final Rectangle2D r;

    this.fsmStateAssertAndSet(BasicFigure.STATE_CAPTION_CLOSED,
        BasicFigure.STATE_GRAPHIC_CREATED);
    g = this.m_driver.createGraphic(this.m_size)
        .setBasePath(this.m_folder).setLogger(this.m_doc.m_logger)
        .setMainDocumentNameSuggestion(this.m_suggestion)
        .setFileProducerListener(new __GraphicListener()).create();
    this.m_doc.m_styles.initialize(g);

    r = g.getBounds();
    this.m_figureSize = new PhysicalDimension(r.getWidth(), r.getHeight(),
        ELength.POINT);
    return g;
  }

  /**
   * The internal dispatcher for graphic listening events. It is basically
   * a wrapper for the
   * {@link org.optimizationBenchmarking.utils.document.impl.abstr.BasicFigure#_onGraphicClosed(Collection)}
   * method, allowing it to remain protected and allowing that
   * {@link org.optimizationBenchmarking.utils.document.impl.abstr.BasicFigure}
   * does not need to implement
   * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener}
   * .
   */
  private final class __GraphicListener implements IFileProducerListener {
    /** create */
    __GraphicListener() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void onFilesFinalized(
        final Collection<Map.Entry<Path, IFileType>> result) {
      BasicFigure.this._onGraphicClosed(result);
    }
  }

}
