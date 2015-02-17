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

/** the BibTeX8 tool */
final class _BibTeX8 extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** create */
  _BibTeX8() {
    super();

    this.m_executable = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "bibtex8" //$NON-NLS-1$
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
    final Path exec, aux, bbl;
    final ExternalProcessBuilder builder;
    final int ret;

    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException(
          "No BibTeX8 binary detected."); //$NON-NLS-1$
    }

    logger = job._getLogger();

    if (((aux = job._getFile(ELaTeXFileType.AUX)) == null)
        || (!(Files.exists(aux)))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            ("No .aux file was found. This means either that LaTeX was not yet run or that the LaTeX file '" //$NON-NLS-1$ 
            + job._getFile(ELaTeXFileType.TEX))
                + //
                "' does not contain any citations (or labels). Either way, you should not have tried running BibTeX8."); //$NON-NLS-1$
      }
      return;
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info((("Applying BibTeX8 to '" + aux) + '\'') + '.'); //$NON-NLS-1$
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
        throw new IOException(((((((("BibTeX8 executable '" //$NON-NLS-1$
            + exec) + "' returned value ") + ret) + //$NON-NLS-1$
            ", which indicates an error, for aux file '") + //$NON-NLS-1$
            aux) + "' created from LaTeX file '") + //$NON-NLS-1$
            job._getFile(ELaTeXFileType.TEX)) + '.');
      }
    }

    if (((bbl = job._getFile(ELaTeXFileType.BBL)) == null)
        || (!(Files.exists(bbl)))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(((("BibTeX8 executable '" //$NON-NLS-1$
            + exec) + "' applied to aux file '") + //$NON-NLS-1$
            aux)
            + "' should have created a bbl file, but did not.");//$NON-NLS-1$
      }
    } else {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying BibTeX8 to '" + aux) + '\'') + '.'); //$NON-NLS-1$
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
    return __BibTeX8Desc.DESC;
  }

  /** the description */
  private static final class __BibTeX8Desc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __BibTeX8Desc();

    /** create */
    private __BibTeX8Desc() {
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
      return __BibTeX8Loader.INSTANCE;
    }

    /** the loader */
    private static final class __BibTeX8Loader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _BibTeX8();
    }
  }

}
