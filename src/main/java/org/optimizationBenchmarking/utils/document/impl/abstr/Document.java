package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.data.CitationsBuilder;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.document.IObjectListener;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.path.PathUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

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

  /** the label manager */
  final LabelManager m_manager;

  /** the base path, i.e., the folder containing the document */
  final Path m_basePath;

  /** the paths */
  private final LinkedHashSet<Path> m_paths;

  /** a citations builder */
  CitationsBuilder m_citations;

  /** the path to the document's main file */
  private final Path m_documentPath;

  /** the object listener */
  private final IObjectListener m_listener;

  /** the underlying writer */
  private final BufferedWriter m_writer;

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
   */
  private Document(final DocumentDriver driver,
      final BufferedWriter writer, final Path docPath,
      final IObjectListener listener) {
    super(driver, writer);

    this.m_styles = driver.createStyleSet();
    if (this.m_styles == null) {
      throw new IllegalArgumentException(//
          "Style set must not be null."); //$NON-NLS-1$
    }

    this.m_manager = driver.createLabelManager();
    if (this.m_manager == null) {
      throw new IllegalArgumentException(//
          "Label manager must not be null."); //$NON-NLS-1$
    }

    this.m_documentPath = PathUtils.normalize(docPath);
    this.m_basePath = PathUtils.normalize(docPath.getParent());
    this.m_paths = new LinkedHashSet<>();
    this.m_paths.add(this.m_documentPath);

    this.m_citations = new CitationsBuilder();
    this.m_listener = listener;
    this.m_writer = writer;
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
   */
  protected Document(final DocumentDriver driver, final Path docPath,
      final IObjectListener listener) {
    this(driver, new BufferedWriter(new OutputStreamWriter(
        PathUtils.openOutputStream(docPath))), docPath, listener);
  }

  /**
   * Add a path into the internal path list
   * 
   * @param p
   *          the path to add
   */
  protected synchronized final void addPath(final Path p) {
    this.m_paths.add(PathUtils.normalize(p));
  }

  /**
   * Add a set of paths into the internal path list
   * 
   * @param ps
   *          the paths to add
   */
  protected synchronized final void addPath(final Iterable<Path> ps) {
    for (final Path p : ps) {
      this.m_paths.add(PathUtils.normalize(p));
    }
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
   * Perform any post-processing, such as writing a footer to the main
   * {@link #getTextOutput() document stream}, or generating additional
   * files (such as style sheets or whatever).
   */
  protected void postProcess() {
    //
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected final synchronized void onClose() {
    final int s;
    Throwable error;

    this.fsmStateAssertAndSet(Document.STATE_FOOTER_CLOSED,
        DocumentElement.STATE_DEAD);

    error = null;

    try {
      this.postProcess();
    } catch (final Throwable t) {
      error = t;
    } finally {

      try {
        this.m_writer.close();
      } catch (final Throwable tt) {
        error = ErrorUtils.aggregateError(error, tt);
      } finally {
        try {
          super.onClose();
        } catch (final Throwable ttt) {
          error = ErrorUtils.aggregateError(error, ttt);
        } finally {
          try {

            if (this.m_listener != null) {
              s = this.m_paths.size();
              this.m_listener
                  .onObjectFinalized(((s > 0) ? (new ArrayListView(
                      this.m_paths.toArray(new Path[s])))
                      : ArraySetView.EMPTY_SET_VIEW));
            }
          } catch (final Throwable tttt) {
            error = ErrorUtils.aggregateError(error, tttt);
          }
        }
      }
    }

    if (error != null) {
      ErrorUtils.throwAsRuntimeException(error);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Label createLabel(final ELabelType type) {
    return this.m_manager.createLabel(type);
  }

  /** {@inheritDoc} */
  @Override
  public final StyleSet getStyles() {
    return this.m_styles;
  }
}
