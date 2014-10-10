package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.data.CitationsBuilder;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.document.object.PathEntry;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.path.PathUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
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

  /** the base path, i.e., the folder containing the document */
  final Path m_basePath;

  /** the paths */
  private LinkedHashSet<PathEntry> m_paths;

  /** a citations builder */
  CitationsBuilder m_citations;

  /** the path to the document's main file */
  private final Path m_documentPath;

  /** the object listener */
  private final IObjectListener m_listener;

  /** the underlying writer */
  private final BufferedWriter m_writer;

  /** the used styles */
  private HashSet<IStyle> m_usedStyles;
  /** the label counters */
  private final int[] m_counters;
  /** the internal memory text output */
  private final MemoryTextOutput m_mto;

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

    this.m_documentPath = PathUtils.normalize(docPath);
    this.m_basePath = PathUtils.normalize(docPath.getParent());
    this.m_paths = new LinkedHashSet<>();

    this.m_citations = new CitationsBuilder();
    this.m_listener = listener;
    this.m_writer = writer;

    this.m_usedStyles = new HashSet<>();
    this.m_usedStyles.add(this.m_styles.getDefaultFont());

    this.m_counters = new int[ELabelType.INSTANCES.size()];
    this.m_mto = new MemoryTextOutput(16);
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
  protected synchronized final void addPath(final PathEntry p) {
    if (p == null) {
      throw new IllegalArgumentException(//
          "Cannot add null path entry."); //$NON-NLS-1$
    }
    this.m_paths.add(p);
  }

  /**
   * Add a set of paths into the internal path list
   * 
   * @param ps
   *          the paths to add
   */
  protected synchronized final void addPaths(final Iterable<PathEntry> ps) {
    for (final PathEntry p : ps) {
      this.addPath(p);
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
   * {@link #addPaths(Iterable)} or {@link #addPath(PathEntry)} (this will
   * not change the value of {@code paths} passed into this method).
   * 
   * @param usedStyles
   *          the set of used styles
   * @param paths
   *          the path entries
   */
  protected void postProcess(final Set<IStyle> usedStyles,
      final ArrayListView<PathEntry> paths) {
    //
  }

  /**
   * {@inheritDoc}
   * 
   * @see #doOnClose()
   * @see #postProcess(Set, ArrayListView)
   */
  @Override
  protected final synchronized void onClose() {
    Throwable error;
    ArrayListView<PathEntry> paths;
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
        paths = ArrayListView.collectionToView(this.m_paths, false);
        styles = this.m_usedStyles;
        this.m_usedStyles = null;
        try {
          this.postProcess(Collections.unmodifiableSet(styles), paths);
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
              if (this.m_listener != null) {
                if (this.m_paths.size() > paths.size()) {
                  paths = ArrayListView.collectionToView(this.m_paths,
                      true);
                }
                this.m_paths = null;
                this.m_listener.onObjectFinalized(paths);
              }
            } catch (final Throwable ttttt) {
              error = ErrorUtils.aggregateError(error, ttttt);
            }
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
    return this.createLabel(type, null);
  }

  /** {@inheritDoc} */
  @Override
  public final StyleSet getStyles() {
    return this.m_styles;
  }
}
