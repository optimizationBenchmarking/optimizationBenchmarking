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

/** the tool which turns PS documents into PDF documents */
final class _Ps2Pdf extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** create */
  _Ps2Pdf() {
    super();

    final Logger logger;
    Path path;

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config(//
          "Now trying to find ps2pdf executable.");//$NON-NLS-1$
    }

    path = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "pspdf",//$NON-NLS-1$
            "ps2pdf",//$NON-NLS-1$
            "pstopdf"//$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);
    if (path == null) {
      path = PathUtils.findFirstInPath(new AndPredicate<>(
          new FileNamePredicate(true, "ps2pdf14"//$NON-NLS-1$
          ), CanExecutePredicate.INSTANCE),//
          IsFilePredicate.INSTANCE, null);
      if (path == null) {
        path = PathUtils.findFirstInPath(new AndPredicate<>(
            new FileNamePredicate(true, "ps2pdf13"//$NON-NLS-1$
            ), CanExecutePredicate.INSTANCE),//
            IsFilePredicate.INSTANCE, null);
        if (path == null) {
          path = PathUtils.findFirstInPath(new AndPredicate<>(
              new FileNamePredicate(true, "ps2pdf12"//$NON-NLS-1$
              ), CanExecutePredicate.INSTANCE),//
              IsFilePredicate.INSTANCE, null);
        }
      }
    }

    this.m_executable = path;

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config((this.m_executable != null) ? //
      ("Ps2pdf executable '" + this.m_executable//$NON-NLS-1$
      + "' found.") : //$NON-NLS-1$
          "No ps2pdf executable found.");//$NON-NLS-1$
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
    final Path exec, ps, pdf;
    final ExternalProcessBuilder builder;
    final int ret;

    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException("No ps2pdf binary detected."); //$NON-NLS-1$
    }

    if ((ps = this
        ._getFile(
            job,
            ELaTeXFileType.PS,
            true,
            "This means either that LaTeX and dvips were not yet run or that the LaTeX or the dvi file contain errors." //$NON-NLS-1$
        )) == null) {
      return;
    }

    if ((pdf = this._getFile(job, ELaTeXFileType.PDF, false, null)) == null) {
      return;
    }

    logger = job._getLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info((("Applying ps2pdf to '" + ps) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    builder.addPathArgument(ps);
    builder.addPathArgument(pdf);
    builder.setLogger(logger);
    builder.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdIn(EProcessStream.IGNORE);

    try (ExternalProcess proc = builder.create()) {
      if ((ret = proc.waitFor()) != 0) {
        throw new IOException(((((((("ps2pdf executable '" //$NON-NLS-1$
            + exec) + "' returned value ") + ret) + //$NON-NLS-1$
            ", which indicates an error, for ps file '") + //$NON-NLS-1$
            ps) + "' created from LaTeX file '") + //$NON-NLS-1$
            job._getFile(ELaTeXFileType.TEX)) + '.');
      }
    }

    if (this
        ._getFile(
            job,
            ELaTeXFileType.PDF,
            true,
            "This could mean that ps2pdf failed to generate the pdf because the postscript file contains errors." //$NON-NLS-1$
        ) != null) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying ps2pdf to '" + ps) + '\'') + '.'); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final ELaTeXFileType _produces() {
    return ELaTeXFileType.PDF;
  }

  /**
   * get the description
   * 
   * @return the description
   */
  static final _LaTeXToolChainComponentDesc _getDescription() {
    return __Ps2PdfDesc.DESC;
  }

  /** the description */
  private static final class __Ps2PdfDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __Ps2PdfDesc();

    /** create */
    private __Ps2PdfDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.PS, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __Ps2PdfLoader.INSTANCE;
    }

    /** the loader */
    private static final class __Ps2PdfLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _Ps2Pdf();
    }
  }

}
