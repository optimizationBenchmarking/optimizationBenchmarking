package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ConfigurableToolJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A builder for LaTeX jobs.
 */
public class LaTeXJobBuilder extends
    ConfigurableToolJobBuilder<LaTeXJob, LaTeXJobBuilder> implements
    IFileProducerJobBuilder {

  /** the source file */
  public static final String PARAM_MAIN = "latexSource"; //$NON-NLS-1$
  /** the formats */
  public static final String PARAM_FORMATS = "latexUsedFormats"; //$NON-NLS-1$

  /** the file types which need to be processed. */
  private HashSet<IFileType> m_types;

  /** the main file */
  private Path m_main;

  /** the file producer listener */
  private IFileProducerListener m_listener;

  /** create the job builder */
  LaTeXJobBuilder() {
    super();
    this.m_types = new HashSet<>();
  }

  /** {@inheritDoc} */
  @Override
  protected String getParameterPrefix() {
    return "latex"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final LaTeXJobBuilder configure(
      final Configuration config) {
    final Path source;
    final ArrayListView<String> required;
    IFileType fileFormat;

    this.__checkState();

    super.configure(config);

    source = config.getPath(LaTeXJobBuilder.PARAM_MAIN, null);
    if (source != null) {
      this.setMainFile(source);
    }

    required = config.getStringList(LaTeXJobBuilder.PARAM_FORMATS, null);
    if (required != null) {
      for (final String format : required) {
        fileFormat = LaTeXJobBuilder.__findFormat(format);

        if (fileFormat != null) {
          this.requireFileType(fileFormat);
        }
      }
    }

    return this;
  }

  /**
   * find a format
   * 
   * @param string
   *          the string
   * @return the format
   */
  @SuppressWarnings("rawtypes")
  private static final IFileType __findFormat(final String string) {
    String s;
    int i;

    if (string == null) {
      return null;
    }

    for (i = 0; i < 2; i++) {
      for (final IFileType type : ((i == 0) ? EGraphicFormat.INSTANCES
          : ELaTeXFileType.INSTANCES)) {
        s = type.getName();
        if (s != null) {
          if (s.equalsIgnoreCase(string)) {
            return type;
          }
        }

        s = type.getDefaultSuffix();
        if (s != null) {
          if (s.equalsIgnoreCase(string)) {
            return type;
          }
        }

        s = type.getMIMEType();
        if (s != null) {
          if (s.equalsIgnoreCase(string)) {
            return type;
          }
        }

        if (type instanceof Enum) {
          s = ((Enum) type).name();
          if (s != null) {
            if (s.equalsIgnoreCase(string)) {
              return type;
            }
          }
        }
      }
    }

    return null;
  }

  /**
   * Require a given file type
   * 
   * @param type
   *          the type
   * @return this builder
   */
  public synchronized LaTeXJobBuilder requireFileType(final IFileType type) {
    final IFileType put;

    put = LaTeX._sanitizeFileType(type);
    if (put != null) {
      this.__checkState();
      this.m_types.add(put);
    }
    return this;
  }

  /** check the state */
  private final void __checkState() {
    if (this.m_types == null) {
      throw new IllegalStateException(//
          "LaTeX job already created."); //$NON-NLS-1$
    }
  }

  /**
   * Set the main file for the LaTeX compilation
   * 
   * @param path
   *          the path
   * @return the builder
   */
  public synchronized final LaTeXJobBuilder setMainFile(final Path path) {
    final Path normalized;

    if (path == null) {
      throw new IllegalArgumentException(//
          "Path to main LaTeX file cannot be null.");//$NON-NLS-1$
    }

    normalized = PathUtils.normalize(path);
    if (normalized == null) {
      throw new IllegalArgumentException(//
          "Normalized path to main LaTeX file (" //$NON-NLS-1$
              + path + ") cannot be null.");//$NON-NLS-1$
    }

    if (!(Files.exists(normalized))) {
      throw new IllegalArgumentException(//
          "Main LaTeX file (" //$NON-NLS-1$
              + normalized + ") must exist.");//$NON-NLS-1$
    }

    try {
      PathUtils.getPhysicalFile(normalized);
    } catch (final Throwable t) {
      throw new IllegalArgumentException(((//
          "Main LaTeX file (" //$NON-NLS-1$
          + normalized) + ") must be a physical file.")//$NON-NLS-1$
          , t);
    }

    this.__checkState();
    this.m_main = normalized;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final LaTeXJobBuilder setFileProducerListener(
      final IFileProducerListener listener) {

    if (listener == null) {
      throw new IllegalArgumentException(//
          "Cannot set null file producer listener.");//$NON-NLS-1$
    }

    this.__checkState();
    this.m_listener = listener;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void validate() {
    if (this.m_main == null) {
      throw new IllegalArgumentException(//
          "Main LaTeX file must be specified.");//$NON-NLS-1$
    }
    super.validate();
  }

  /**
   * Build the main job and make it ready for execution.
   * 
   * @return the main job
   */
  @Override
  public synchronized final LaTeXJob create() {
    final _LaTeXToolChainComponent[][] chain;
    HashSet<IFileType> types;

    this.validate();
    this.__checkState();

    types = this.m_types;
    this.m_types = null;

    chain = LaTeX._findToolChain(types);
    types = null;

    if (chain != null) {
      return new _LaTeXMainJob(this.m_main, chain[0], chain[1],
          this.m_listener, this.getLogger());
    }
    return new _NoSuitableToolChainFound(this.m_listener, this.getLogger());
  }
}
