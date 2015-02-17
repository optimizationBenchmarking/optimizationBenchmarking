package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    this.m_executable = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "dvips",//$NON-NLS-1$
            "dvi2ps",//$NON-NLS-1$
            "dvitops"//$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);
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

    logger = job._getLogger();

    if (((dvi = job._getFile(ELaTeXFileType.DVI)) == null)
        || (!(Files.exists(dvi)))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            ("No .dvi file was found. This means either that LaTeX was not yet run or that the LaTeX file '" //$NON-NLS-1$ 
            + job._getFile(ELaTeXFileType.TEX))
                + //
                "' does not produce a document. Either way, you should not have tried running dvips."); //$NON-NLS-1$
      }
      return;
    }

    if ((ps = job._getFile(ELaTeXFileType.PS)) == null) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "No .ps file was specified. This should never happen."); //$NON-NLS-1$
      }
      return;
    }

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

    if (!(Files.exists(ps))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(((("dvips executable '" //$NON-NLS-1$
            + exec) + "' applied to dvi file '") + //$NON-NLS-1$
            dvi)
            + "' should have created a ps file, but did not.");//$NON-NLS-1$
      }
    } else {
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
