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

/** the tool which turns DVI documents into PS documents */
final class _Dvi2Ps extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** create */
  _Dvi2Ps() {
    super();
    final Logger logger;

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config("Now trying to find dvips executable.");//$NON-NLS-1$
    }

    this.m_executable = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "dvips",//$NON-NLS-1$
            "dvi2ps",//$NON-NLS-1$
            "dvitops"//$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config((this.m_executable != null) ? //
      ("Dvips executable '" + this.m_executable + "' found.") : //$NON-NLS-1$//$NON-NLS-2$
          "No dvips executable found.");//$NON-NLS-1$
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
    final Path exec, dvi, ps;
    final ExternalProcessBuilder builder;
    final int ret;

    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException("No dvips binary detected."); //$NON-NLS-1$
    }

    if ((dvi = this
        ._getFile(
            job,
            ELaTeXFileType.DVI,
            true,//
            " This could mean that (Pdf)LaTeX was not yet executed or does not produce any dvi output."//$NON-NLS-1$
        )) == null) {
      return;
    }

    if ((ps = this._getFile(job, ELaTeXFileType.PS, false, null)) == null) {
      return;
    }

    logger = job._getLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info((("Applying dvips to '" + dvi) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    builder.addPathArgument(dvi);
    builder.addStringArgument("-o");//$NON-NLS-1$
    builder.addPathArgument(ps);
    builder.setLogger(logger);
    builder.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdIn(EProcessStream.IGNORE);

    try (ExternalProcess proc = builder.create()) {
      if ((ret = proc.waitFor()) != 0) {
        throw new IOException(((((((("dvips executable '" //$NON-NLS-1$
            + exec) + "' returned value ") + ret) + //$NON-NLS-1$
            ", which indicates an error, for dvi file '") + //$NON-NLS-1$
            dvi) + "' created from LaTeX file '") + //$NON-NLS-1$
            job._getFile(ELaTeXFileType.TEX)) + '.');
      }
    }

    if (this
        ._getFile(
            job,
            ELaTeXFileType.PS,
            true,
            " This could mean that no postscript output can be built from the dvi file or that the dvi file has errors.")//$NON-NLS-1$ 
    != null) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying dvips to '" + dvi) + '\'') + '.'); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final ELaTeXFileType _produces() {
    return ELaTeXFileType.PS;
  }

  /**
   * get the description of the Dvi2Ps tool
   * 
   * @return the instance
   */
  static final _LaTeXToolChainComponentDesc _getDescription() {
    return __Dvi2PsDesc.DESC;
  }

  /** the description */
  private static final class __Dvi2PsDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __Dvi2PsDesc();

    /** create */
    private __Dvi2PsDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.DVI, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __Dvi2PsLoader.INSTANCE;
    }

    /** the loader */
    private static final class __Dvi2PsLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _Dvi2Ps();
    }
  }

}
