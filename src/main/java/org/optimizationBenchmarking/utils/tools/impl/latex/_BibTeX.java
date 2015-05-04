package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/** the <a href="http://en.wikipedia.org/wiki/BibTeX">BibTeX</a> tool */
final class _BibTeX extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** create */
  _BibTeX() {
    super();

    final Logger logger;
    Path path;

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config("Now trying to find BibTeX executable.");//$NON-NLS-1$
    }

    path = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "bibtex",//$NON-NLS-1$
            "bibtex.original" //$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);
    if (path == null) {
      path = PathUtils.findFirstInPath(new AndPredicate<>(
          new FileNamePredicate(true, "miktex-bibtex" //$NON-NLS-1$
          ), CanExecutePredicate.INSTANCE),//
          IsFilePredicate.INSTANCE, null);
    }
    this.m_executable = path;

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config((path != null) ? //
      ("BibTeX executable '" + path + "' found.") : //$NON-NLS-1$//$NON-NLS-2$
          "No BibTeX executable found.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canUse() {
    return (this.m_executable != null);
  }

  /** {@inheritDoc} */
  @Override
  final void _use(final _LaTeXMainJob job) throws IOException {
    final Logger logger;
    final Path exec, aux;
    final ExternalProcessBuilder builder;
    final int ret;

    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException("No BibTeX binary detected."); //$NON-NLS-1$
    }

    if ((aux = this
        ._getFile(
            job,
            ELaTeXFileType.AUX,
            true,//
            " This could mean that (Pdf)LaTeX was not yet executed or that no citations, labels, or sections are contained in the main document.")) == null) { //$NON-NLS-1$
      return;
    }

    logger = job._getLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info((("Applying BibTeX to '" + aux) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    builder.addStringArgument(PathUtils.getName(aux));
    builder.setLogger(logger);
    builder.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdIn(EProcessStream.IGNORE);

    try (ExternalProcess proc = builder.create()) {
      if ((ret = proc.waitFor()) != 0) {
        throw new IOException(((((((("BibTeX executable '" //$NON-NLS-1$
            + exec) + "' returned value ") + ret) + //$NON-NLS-1$
            ", which indicates an error, for aux file '") + //$NON-NLS-1$
            aux) + "' created from LaTeX file '") + //$NON-NLS-1$
            job._getFile(ELaTeXFileType.TEX)) + '.');
      }
    }

    if (this._getFile(job, ELaTeXFileType.BBL, true,
        " This could mean that the main document contains no citations.")//$NON-NLS-1$
    != null) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying BibTeX to '" + aux) + '\'') + '.'); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final ELaTeXFileType _produces() {
    return ELaTeXFileType.BBL;
  }

  /**
   * get the description
   *
   * @return the description
   */
  static final _LaTeXToolChainComponentDesc _getDescription() {
    return __BibTeXDesc.DESC;
  }

  /** the description */
  private static final class __BibTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __BibTeXDesc();

    /** create */
    private __BibTeXDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.AUX, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __BibTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __BibTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _BibTeX();
    }
  }
}
