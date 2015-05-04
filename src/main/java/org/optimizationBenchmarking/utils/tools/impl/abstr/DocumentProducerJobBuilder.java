package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * The base class for file producer job builders
 *
 * @param <J>
 *          the job type
 * @param <R>
 *          the return type of the setter methods
 */
public abstract class DocumentProducerJobBuilder<J extends IToolJob, R extends DocumentProducerJobBuilder<J, R>>
    extends ToolJobBuilder<J, R> implements IDocumentProducerJobBuilder {

  /** the output folder */
  public static final String PARAM_OUTPUT_FOLDER = "output"; //$NON-NLS-1$
  /** the document name */
  public static final String PARAM_DOCUMENT_NAME = "docName"; //$NON-NLS-1$
  /** the logger */
  public static final String PARAM_LOGGER = Configuration.PARAM_LOGGER;

  /** the listener */
  private IFileProducerListener m_listener;

  /** the base path */
  private Path m_basePath;

  /** the name suggestion for the main document */
  private String m_mainDocumentNameSuggestion;

  /** create the tool job builder */
  protected DocumentProducerJobBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public R configure(final Configuration config) {
    final Path oldBase, newBase;
    final String oldName, newName;
    final Logger oldLogger, newLogger;

    oldBase = this.m_basePath;
    newBase = config.getPath(
        DocumentProducerJobBuilder.PARAM_OUTPUT_FOLDER, this.m_basePath);
    if ((newBase != null) || (oldBase != null)) {
      this.setBasePath(newBase);
    }

    oldName = this.m_mainDocumentNameSuggestion;
    newName = config.getString(
        DocumentProducerJobBuilder.PARAM_DOCUMENT_NAME, oldName);
    if ((oldName != null) || (newName != null)) {
      this.setMainDocumentNameSuggestion(newName);
    }

    oldLogger = this.m_logger;
    newLogger = config.getLogger(DocumentProducerJobBuilder.PARAM_LOGGER,
        oldLogger);
    if ((oldLogger != null) || (newLogger != null)) {
      this.setLogger(newLogger);
    }

    return ((R) this);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final R setFileProducerListener(
      final IFileProducerListener listener) {
    this.m_listener = listener;
    return ((R) this);
  }

  /**
   * Get the file producer listener
   *
   * @return the file producer listener
   */
  public final IFileProducerListener getFileProducerListener() {
    return this.m_listener;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final R setBasePath(final Path basePath) {
    final Path p;

    p = PathUtils.normalize(basePath);
    if (p == null) {
      throw new IllegalArgumentException(//
          "The base path for a file producer cannot be set to something equivalent to null, but '" //$NON-NLS-1$
              + basePath + "' is.");//$NON-NLS-1$
    }
    this.m_basePath = p;
    return ((R) this);
  }

  /**
   * Get the base path
   *
   * @return the base path
   */
  public final Path getBasePath() {
    return this.m_basePath;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final R setMainDocumentNameSuggestion(final String name) {
    final String s;

    s = TextUtils.normalize(name);
    if (s == null) {
      throw new IllegalArgumentException(//
          "The main document name suggestion cannot be empty, but '" + //$NON-NLS-1$
              name + "' is equivalent to an empty name.");//$NON-NLS-1$
    }
    this.m_mainDocumentNameSuggestion = s;
    return ((R) this);
  }

  /**
   * Get the main document name suggestion
   *
   * @return the main document name suggestion
   */
  public final String getMainDocumentNameSuggestion() {
    return this.m_mainDocumentNameSuggestion;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    if (this.m_basePath == null) {
      throw new IllegalArgumentException("The base path must be set.");//$NON-NLS-1$
    }
    if (this.m_mainDocumentNameSuggestion == null) {
      throw new IllegalArgumentException(
          "The main document name suggestion must be set.");//$NON-NLS-1$
    }
  }
}
