package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.chart.spec.IChartBuilder;
import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.impl.abstr.FileCollector;
import org.optimizationBenchmarking.utils.tools.impl.abstr.FileProducerSupport;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * The root object for the document API.
 */
public class Document extends DocumentElement implements IDocument {

  /** the state when the header has been created */
  private static final int STATE_HEADER_CREATED = (DocumentElement.STATE_MAX_ELEMENT + 1);
  /** the state before the header */
  private static final int STATE_HEADER_BEFORE_OPEN = (Document.STATE_HEADER_CREATED + 1);
  /** the state in the header */
  private static final int STATE_HEADER_OPENED = (Document.STATE_HEADER_BEFORE_OPEN + 1);
  /** the state after the header */
  private static final int STATE_HEADER_CLOSED = (Document.STATE_HEADER_OPENED + 1);
  /** the state when the body has been created */
  private static final int STATE_BODY_CREATED = (Document.STATE_HEADER_CLOSED + 1);
  /** the state before the body */
  private static final int STATE_BODY_BEFORE_OPEN = (Document.STATE_BODY_CREATED + 1);
  /** the state in the body */
  private static final int STATE_BODY_OPENED = (Document.STATE_BODY_BEFORE_OPEN + 1);
  /** the state after the body */
  private static final int STATE_BODY_CLOSED = (Document.STATE_BODY_OPENED + 1);
  /** the state when the footer has been created */
  private static final int STATE_FOOTER_CREATED = (Document.STATE_BODY_CLOSED + 1);
  /** the state before the footer */
  private static final int STATE_FOOTER_BEFORE_OPEN = (Document.STATE_FOOTER_CREATED + 1);
  /** the state in the footer */
  private static final int STATE_FOOTER_OPENED = (Document.STATE_FOOTER_BEFORE_OPEN + 1);
  /** the state after the footer */
  private static final int STATE_FOOTER_CLOSED = (Document.STATE_FOOTER_OPENED + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[Document.STATE_FOOTER_CLOSED + 1];
    Document.STATE_NAMES[Document.STATE_HEADER_CREATED] = "headerCreated"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_HEADER_BEFORE_OPEN] = "headerBeforeOpen"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_HEADER_OPENED] = "headerOpened"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_HEADER_CLOSED] = "headerClosed"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_BODY_CREATED] = "bodyCreated"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_BODY_BEFORE_OPEN] = "bodyBeforeOpen"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_BODY_OPENED] = "bodyOpened"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_BODY_CLOSED] = "bodyClosed"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_FOOTER_CREATED] = "footerCreated"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_FOOTER_BEFORE_OPEN] = "footerBeforeOpen"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_FOOTER_OPENED] = "footerOpened"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_FOOTER_CLOSED] = "footerClosed"; //$NON-NLS-1$
  }

  /** the style set */
  final StyleSet m_styles;

  /** the base path, i.e., the folder containing the document */
  final Path m_basePath;

  /** the path to the document's main file */
  private final Path m_documentPath;

  /** the logger */
  final Logger m_logger;

  /** the paths */
  FileProducerSupport m_paths;

  /** a citations builder */
  BibliographyBuilder m_citations;

  /** the underlying writer */
  private final BufferedWriter m_writer;

  /** the used styles */
  private HashSet<IStyle> m_usedStyles;
  /** the label counters */
  private final int[] m_counters;
  /** the internal memory text output */
  private final MemoryTextOutput m_mto;

  /** the document name */
  private transient volatile String m_docName;

  /** the graphic configuration */
  private final GraphicConfiguration m_graphicConfig;

  /** the chart driver */
  private final IChartDriver m_chartDriver;

  /**
   * Create a document.
   *
   * @param driver
   *          the document driver
   * @param writer
   *          the writer
   * @param docPath
   *          the path to the document
   * @param builder
   *          the document builder
   */
  private Document(final DocumentDriver driver,
      final BufferedWriter writer, final Path docPath,
      final DocumentBuilder builder) {
    super(driver, writer);

    this.m_graphicConfig = builder._graphicConfig();
    if (this.m_graphicConfig == null) {
      throw new IllegalArgumentException(//
          "Graphic configuration must not be null."); //$NON-NLS-1$
    }

    DocumentConfiguration._checkChartDriver(//
        this.m_chartDriver = builder.getChartDriver());

    this.m_styles = builder.createStyleSet();
    if (this.m_styles == null) {
      throw new IllegalArgumentException(//
          "Style set must not be null."); //$NON-NLS-1$
    }

    this.m_logger = builder.getLogger();
    this.m_documentPath = PathUtils.normalize(docPath);
    this.m_basePath = PathUtils.normalize(docPath.getParent());
    this.m_paths = new FileProducerSupport(
        builder.getFileProducerListener());

    this.m_citations = new BibliographyBuilder();
    this.m_writer = writer;

    this.m_usedStyles = new HashSet<>();
    this.m_usedStyles.add(this.m_styles.getDefaultFont());

    this.m_counters = new int[ELabelType.INSTANCES.size()];
    this.m_mto = new MemoryTextOutput(16);
  }

  /** {@inheritDoc} */
  @Override
  protected final FileCollector getFileCollector() {
    return this.m_paths;
  }

  /**
   * Create a document.
   *
   * @param driver
   *          the document driver
   * @param builder
   *          the document builder
   */
  protected Document(final DocumentDriver driver,
      final DocumentBuilder builder) {
    this(driver, PathUtils.createPathInside(builder.getBasePath(),
        PathUtils.makeFileName(builder.getMainDocumentNameSuggestion(),
            driver.getFileType().getDefaultSuffix())), builder);
  }

  /**
   * Create a document.
   *
   * @param driver
   *          the document driver
   * @param docPath
   *          the path to the document
   * @param builder
   *          the document builder
   */
  private Document(final DocumentDriver driver, final Path docPath,
      final DocumentBuilder builder) {
    this(driver, Document.__getOutput(docPath), docPath, builder);
  }

  /** {@inheritDoc} */
  @Override
  protected final Logger getLogger() {
    return this.m_logger;
  }

  /**
   * Get the label for a given object.
   *
   * @param type
   *          the label type
   * @param label
   *          the label placeholder, must not be {@code null}
   * @param text
   *          the label text
   * @return the label to use
   */
  final Label _getLabel(final ELabelType type, final ILabel label,
      final String text) {
    final Label r;

    if (text == null) {
      throw new IllegalArgumentException(//
          "The reference text must not be null."); //$NON-NLS-1$
    }

    if (label == ELabelType.AUTO) {
      return this.createLabel(type, text);
    }
    if (label instanceof Label) {
      r = ((Label) (label));
      if (r.m_type != type) {
        throw new IllegalArgumentException(//
            "A label of type '" + r.m_type + //$NON-NLS-1$
                "' cannot be used to label an '" + //$NON-NLS-1$
                type + "'."); //$NON-NLS-1$
      }
      this.__setReferenceText(r, text);
      return r;
    }

    throw new IllegalArgumentException(//
        "Invalid label: '" + label + //$NON-NLS-1$
            "', only " + TextUtils.className(ELabelType.class) + //$NON-NLS-1$
            ".AUTO and instances of " + TextUtils.className(Label.class) + //$NON-NLS-1$
            " permitted."); //$NON-NLS-1$
  }

  /**
   * Create a new label to mark a table or figure or section with that is
   * going to be written in the future.
   *
   * @param type
   *          the label type
   * @param refText
   *          the reference text
   * @return the label to be used in forward references
   */
  public final Label createLabel(final ELabelType type,
      final String refText) {
    final MemoryTextOutput mto;
    final String s;

    mto = this.m_mto;
    synchronized (mto) {
      mto.append(type.getLabelPrefixChar());
      mto.append(ELabelType.LABEL_PREFIX_SEPARATOR);
      AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(
          (this.m_counters[type.ordinal()]++), ETextCase.IN_SENTENCE, mto);
      s = mto.toString();
      mto.clear();
    }

    return this.m_driver.createLabel(this, type, s, refText);
  }

  /**
   * set the reference text of a label
   *
   * @param label
   *          the label
   * @param refText
   *          the reference text
   */
  private final void __setReferenceText(final Label label,
      final String refText) {
    if (refText == null) {
      throw new IllegalArgumentException(//
          "Reference text must not be null."); //$NON-NLS-1$
    }
    if (label.m_owner != this) {
      throw new IllegalArgumentException(//
          "Reference text can only be set to owned labels."); //$NON-NLS-1$
    }
    label.m_refText = refText;
  }

  /**
   * Remember that a given style has been used
   *
   * @param style
   *          the style
   */
  @Override
  protected void styleUsed(final IStyle style) {
    synchronized (this.m_usedStyles) {
      this.m_usedStyles.add(style);
    }
  }

  /**
   * Get the output writer
   *
   * @param docPath
   *          the document path
   * @return the writer
   */
  private static final BufferedWriter __getOutput(final Path docPath) {
    final OutputStream stream;
    try {
      stream = PathUtils.openOutputStream(docPath);
    } catch (final Throwable thro) {
      throw new RuntimeException(((//
          "Error while trying to open the Document OutputStream to path '" //$NON-NLS-1$
          + docPath) + '\''), thro);
    }
    return new BufferedWriter(new OutputStreamWriter(stream));
  }

  /** {@inheritDoc} */
  @Override
  public final Path getDocumentPath() {
    return this.m_documentPath;
  }

  /**
   * Get the path to the folder containing the document
   *
   * @return the path to the folder containing the document
   */
  public final Path getDocumentFolder() {
    return this.m_basePath;
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= Document.STATE_HEADER_CREATED)
        && (state < Document.STATE_NAMES.length)) {
      sb.append(Document.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final DocumentHeader header() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        Document.STATE_HEADER_CREATED);
    return this.m_driver.createDocumentHeader(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final DocumentBody body() {
    this.fsmStateAssertAndSet(Document.STATE_HEADER_CLOSED,
        Document.STATE_BODY_CREATED);
    return this.m_driver.createDocumentBody(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final DocumentFooter footer() {
    this.fsmStateAssertAndSet(Document.STATE_BODY_CLOSED,
        Document.STATE_FOOTER_CREATED);
    return this.m_driver.createDocumentFooter(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof DocumentHeader) {
      this.fsmStateAssertAndSet(Document.STATE_HEADER_CREATED,
          Document.STATE_HEADER_BEFORE_OPEN);
      return;
    }
    if (child instanceof DocumentBody) {
      this.fsmStateAssertAndSet(Document.STATE_BODY_CREATED,
          Document.STATE_BODY_BEFORE_OPEN);
      return;
    }
    if (child instanceof DocumentFooter) {
      this.fsmStateAssertAndSet(Document.STATE_FOOTER_CREATED,
          Document.STATE_FOOTER_BEFORE_OPEN);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof DocumentHeader) {
      this.fsmStateAssertAndSet(Document.STATE_HEADER_BEFORE_OPEN,
          Document.STATE_HEADER_OPENED);
      return;
    }
    if (child instanceof DocumentBody) {
      this.fsmStateAssertAndSet(Document.STATE_BODY_BEFORE_OPEN,
          Document.STATE_BODY_OPENED);
      return;
    }
    if (child instanceof DocumentFooter) {
      this.fsmStateAssertAndSet(Document.STATE_FOOTER_BEFORE_OPEN,
          Document.STATE_FOOTER_OPENED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof DocumentHeader) {
      this.fsmStateAssertAndSet(Document.STATE_HEADER_OPENED,
          Document.STATE_HEADER_CLOSED);
      return;
    }
    if (child instanceof DocumentBody) {
      this.fsmStateAssertAndSet(Document.STATE_BODY_OPENED,
          Document.STATE_BODY_CLOSED);
      return;
    }
    if (child instanceof DocumentFooter) {
      this.fsmStateAssertAndSet(Document.STATE_FOOTER_OPENED,
          Document.STATE_FOOTER_CLOSED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /**
   * Perform any post-processing to the output stream, such as writing a
   * termination sequence to the main {@link #getTextOutput() document
   * stream}. The stream is still open, but will be closed directly after
   * this method returns.
   *
   * @see #onClose()
   */
  protected void doOnClose() {
    //
  }

  /**
   * Post-process the generated files. Here, e.g., a LaTeX document could
   * be compiled to postscript or pdf. During this process, new files may
   * be generated and added to the overall outcome via the methods provided
   * by {@link #getFileCollector()} (this will not change the value of
   * {@code paths} passed into this method).
   *
   * @param usedStyles
   *          the set of used styles
   * @param paths
   *          the path entries
   */
  protected void postProcess(final Set<IStyle> usedStyles,
      final ArrayListView<ImmutableAssociation<Path, IFileType>> paths) {
    //
  }

  /**
   * Get the document id
   *
   * @return the document id
   */
  final String __name() {
    String s;

    s = this.m_docName;
    if (s == null) {
      this.m_docName = s = (" document '" + //$NON-NLS-1$
          this.m_documentPath + "' of type " + //$NON-NLS-1$
      TextUtils.className(this.m_driver.getClass()));
    }
    return s;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    final Logger log;

    super.onOpen();

    log = this.m_logger;
    if ((log != null) && (log.isLoggable(Level.FINE))) {
      log.fine("Begin creating" + this.__name());//$NON-NLS-1$
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see #doOnClose()
   * @see #postProcess(Set, ArrayListView)
   */
  @SuppressWarnings("null")
  @Override
  protected final synchronized void onClose() {
    Logger log;
    Object error;
    Set<IStyle> styles;

    this.fsmStateAssertAndSet(Document.STATE_FOOTER_CLOSED,
        DocumentElement.STATE_DEAD);

    error = null;

    try {
      this.doOnClose();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(t, error);
    } finally {
      try {
        this.m_writer.close();
      } catch (final Throwable tt) {
        error = ErrorUtils.aggregateError(tt, error);
      } finally {

        log = this.m_logger;
        if ((log != null) && (log.isLoggable(Level.FINE))) {
          log.fine("Finished creating base files of" + //$NON-NLS-1$
              this.__name() + ", now beginning to post-process."); //$NON-NLS-1$
        }

        styles = this.m_usedStyles;
        this.m_usedStyles = null;
        try {
          this.postProcess(Collections.unmodifiableSet(styles),
              this.m_paths.getProducedFiles());

          log = this.m_logger;
          if ((log != null) && (log.isLoggable(Level.FINE))) {
            log.fine("Finished post-processing" + //$NON-NLS-1$
                this.__name());
          }

        } catch (final Throwable ttt) {
          error = ErrorUtils.aggregateError(ttt, error);
        } finally {
          styles.clear();
          styles = null;
          try {
            super.onClose();
          } catch (final Throwable tttt) {
            error = ErrorUtils.aggregateError(tttt, error);
          } finally {
            try {
              this.m_paths.close();
            } catch (final Throwable ttttt) {
              error = ErrorUtils.aggregateError(ttttt, error);
            } finally {
              this.m_paths = null;
            }
          }
        }
      }
    }
    if (error != null) {
      ErrorUtils.logError(log,//
          ("Error during finalization of" + this.__name()), //$NON-NLS-1$
          error, true, RethrowMode.AS_RUNTIME_EXCEPTION);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Label createLabel(final ELabelType type) {
    return this.createLabel(type, null);
  }

  /** {@inheritDoc} */
  @Override
  public final StyleSet getStyles() {
    return this.m_styles;
  }

  /**
   * Translate a figure size to a physical dimension
   *
   * @param size
   *          the size
   * @return the translated size
   */
  protected PhysicalDimension getFigureSize(final EFigureSize size) {
    return size.approximateSize(new PageDimension(EScreenSize.DEFAULT
        .getPageSize(EScreenSize.DEFAULT_SCREEN_DPI)));
  }

  /**
   * Get the figures per row
   *
   * @param size
   *          the figure size
   * @return the number of figures of this size that fit into one row
   */
  protected int getFiguresPerRow(final EFigureSize size) {
    return ((size != null) ? (size.getNX()) : 1);
  }

  /**
   * Create a graphics object with a {@code size} relative to the document
   * base size. If the resulting object is an object which writes contents
   * to a file, then it will write its contents to a file in the specified
   * by {@code basePath}. The file name will be generated based on a
   * {@code name}. It may be slightly different, though, maybe with a
   * different suffix. Once the graphic is
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#close()
   * closed}, it will notify the provided {@code listener} interface
   * (unless {@code listener==null}).
   *
   * @param basePath
   *          the base path, i.e., the folder in which the graphic should
   *          be created
   * @param name
   *          the name of the graphics file (without extension)
   * @param size
   *          the size of the graphic, relative to the document-defined
   *          base size
   * @param listener
   *          the listener to be notified when painting the graphic has
   *          been completed
   * @param logger
   *          the logger to use
   * @return the graphic object
   */
  protected Graphic createGraphic(final Path basePath, final String name,
      final EFigureSize size, final IFileProducerListener listener,
      final Logger logger) {
    return this.m_graphicConfig.createGraphic(basePath, name,
        this.getFigureSize(size), listener, logger);
  }

  /**
   * Get the graphic format used by this document
   *
   * @return the graphic format used by this document
   */
  protected EGraphicFormat getGraphicFormat() {
    final IGraphicDriver driver;
    final EGraphicFormat format;
    driver = this.m_graphicConfig.getGraphicDriver();
    if (driver != null) {
      format = driver.getFileType();
      if (format != null) {
        return format;
      }
    }
    return EGraphicFormat.NULL;
  }

  /**
   * Create a chart selector
   *
   * @param graphic
   *          the graphic
   * @return the chart selector
   */
  protected IChartSelector createChart(final Graphic graphic) {
    final IChartBuilder builder;

    builder = this.m_chartDriver.use();
    builder.setStyleSet(this.m_styles);
    builder.setLogger(this.m_logger);
    builder.setGraphic(graphic);
    return builder.create();
  }
}
