package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
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
  private FileProducerSupport m_paths;

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

  /**
   * Create a document.
   * 
   * @param driver
   *          the document driver
   * @param writer
   *          the writer
   * @param docPath
   *          the path to the document
   * @param listener
   *          the object listener the object listener
   * @param logger
   *          the logger
   */
  private Document(final DocumentDriver driver,
      final BufferedWriter writer, final Path docPath,
      final Logger logger, final IFileProducerListener listener) {
    super(driver, writer);

    this.m_styles = driver.createStyleSet();
    if (this.m_styles == null) {
      throw new IllegalArgumentException(//
          "Style set must not be null."); //$NON-NLS-1$
    }

    this.m_logger = logger;
    this.m_documentPath = PathUtils.normalize(docPath);
    this.m_basePath = PathUtils.normalize(docPath.getParent());
    this.m_paths = new FileProducerSupport(listener);

    this.m_citations = new BibliographyBuilder();
    this.m_writer = writer;

    this.m_usedStyles = new HashSet<>();
    this.m_usedStyles.add(this.m_styles.getDefaultFont());

    this.m_counters = new int[ELabelType.INSTANCES.size()];
    this.m_mto = new MemoryTextOutput(16);
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
   * Create a document.
   * 
   * @param driver
   *          the document driver
   * @param docPath
   *          the path to the document
   * @param listener
   *          the object listener the object listener
   * @param logger
   *          the logger
   */
  protected Document(final DocumentDriver driver, final Path docPath,
      final Logger logger, final IFileProducerListener listener) {
    this(driver, Document.__getOutput(docPath), docPath, logger, listener);
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
      ErrorUtils.throwAsRuntimeException(thro);
      return null; // we'll never get here
    }
    return new BufferedWriter(new OutputStreamWriter(stream));
  }

  /**
   * Add a file to this document
   * 
   * @param path
   *          the path to the file
   * @param type
   *          the file type
   */
  public final void addFile(final Path path, final IFileType type) {
    this.m_paths.addFile(path, type);
  }

  /**
   * Add a path into the internal path list
   * 
   * @param p
   *          the path to add
   */
  protected final void addFile(final Map.Entry<Path, IFileType> p) {
    this.m_paths.addFile(p);
  }

  /**
   * Add a set of paths into the internal path list
   * 
   * @param ps
   *          the paths to add
   */
  protected final void addFiles(
      final Iterable<Map.Entry<Path, IFileType>> ps) {
    this.m_paths.addFiles(ps);
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
   * be generated and added to the overall outcome via
   * {@link #addFiles(Iterable)} or {@link #addFile(java.util.Map.Entry)}
   * (this will not change the value of {@code paths} passed into this
   * method).
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
  @Override
  protected final synchronized void onClose() {
    Logger log;
    Throwable error;
    Set<IStyle> styles;

    this.fsmStateAssertAndSet(Document.STATE_FOOTER_CLOSED,
        DocumentElement.STATE_DEAD);

    error = null;

    try {
      this.doOnClose();
    } catch (final Throwable t) {
      error = t;
    } finally {
      try {
        this.m_writer.close();
      } catch (final Throwable tt) {
        error = ErrorUtils.aggregateError(error, tt);
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
          error = ErrorUtils.aggregateError(error, ttt);
        } finally {
          styles.clear();
          styles = null;
          try {
            super.onClose();
          } catch (final Throwable tttt) {
            error = ErrorUtils.aggregateError(error, tttt);
          } finally {
            try {
              this.m_paths.close();
            } catch (final Throwable ttttt) {
              error = ErrorUtils.aggregateError(error, ttttt);
            } finally {
              this.m_paths = null;
            }
          }
        }
      }
    }
    if (error != null) {
      log = this.m_logger;
      if ((log != null) && (log.isLoggable(Level.SEVERE))) {
        log.log(Level.SEVERE, ("Error during finalization of" //$NON-NLS-1$
            + this.__name()), error);
      }
      ErrorUtils.throwAsRuntimeException(error);
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
}
