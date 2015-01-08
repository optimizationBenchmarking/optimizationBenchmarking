package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerBuilder;
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
public abstract class FileProducerBuilder<J extends IToolJob, R extends FileProducerBuilder<J, R>>
    extends ToolJobBuilder<J, R> implements IDocumentProducerBuilder {

  /** the listener */
  protected IFileProducerListener m_listener;

  /** the base path */
  protected Path m_basePath;

  /** the name suggestion for the main document */
  protected String m_mainDocumentNameSuggestion;

  /** create the tool job builder */
  protected FileProducerBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final R setFileProducerListener(
      final IFileProducerListener listener) {
    this.m_listener = listener;
    return ((R) this);
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
