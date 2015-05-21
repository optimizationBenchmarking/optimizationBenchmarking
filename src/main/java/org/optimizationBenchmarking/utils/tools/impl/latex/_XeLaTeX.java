package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
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

/** the <a href="http://en.wikipedia.org/wiki/XeTeX">XeLaTeX</a> tool */
final class _XeLaTeX extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** can we halt on error? */
  private final String m_haltArg;

  /** create */
  _XeLaTeX() {
    super();

    final Logger logger;
    String arg;

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config("Now trying to find XeLaTeX executable.");//$NON-NLS-1$
    }

    this.m_executable = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "xelatex" //$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);
    arg = null;
    try {
      arg = _LaTeXToolChainComponent._getArgs(this.m_executable, "-help", //$NON-NLS-1$
          "halt-on-error")[0]; //$NON-NLS-1$
    } catch (final Throwable t) {
      arg = null;
    }
    this.m_haltArg = arg;

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config((this.m_executable != null) ? //
      ("XeLaTeX executable '" + this.m_executable + //$NON-NLS-1$
      "' found.")//$NON-NLS-1$
          : "No XeLaTeX executable found.");//$NON-NLS-1$
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
    final Path exec, tex;
    final ExternalProcessBuilder builder;
    final int ret;
    boolean ok;

    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException(
          "No XeLaTeX binary detected."); //$NON-NLS-1$
    }

    if ((tex = this._getFile(job, ELaTeXFileType.TEX, true, null)) == null) {
      return;
    }
    if (this._getFile(job, ELaTeXFileType.AUX, false, null) == null) {
      return;
    }
    if (this._getFile(job, ELaTeXFileType.PDF, false, null) == null) {
      return;
    }

    logger = job._getLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info((("Applying XeLaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    if (this.m_haltArg != null) {
      builder.addStringArgument(this.m_haltArg);
    }
    builder.addPathArgument(tex);
    builder.setLogger(logger);
    builder.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdIn(EProcessStream.IGNORE);

    try (ExternalProcess proc = builder.create()) {
      if ((ret = proc.waitFor()) != 0) {
        throw new IOException((((((("XeLaTeX executable '" //$NON-NLS-1$
            + exec) + "' returned value ") + ret) + //$NON-NLS-1$
            ", which indicates an error, for tex file '") + //$NON-NLS-1$
            tex) + '\'') + '.');
      }
    }

    ok = true;
    if (this
        ._getFile(
            job,
            ELaTeXFileType.AUX,
            true,
            " This could mean that the latex document does not contain any label, citation, or section."//$NON-NLS-1$
        ) == null) {
      ok = false;
    }
    if (this
        ._getFile(
            job,
            ELaTeXFileType.PDF,
            true,
            " This could mean that the latex document does not produce any pdf output."//$NON-NLS-1$
        ) == null) {
      ok = false;
    }

    if (ok) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying XeLaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
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
    return __XeLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __XeLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __XeLaTeXDesc();

    /** create */
    private __XeLaTeXDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.TEX, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PDF, type) || //
          _LaTeXToolChainComponent._equals(ELaTeXFileType.PDF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.EPS, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PGF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PNG, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.JPEG, type);//
      // XeLaTeX also supports bmp, seemingly, but may sometimes fail with
      // "! Dimension too large." error. Thus, we leave the following line
      // _LaTeXToolChainComponent._equals(EGraphicFormat.BMP, type);
      // away.
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __XeLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __XeLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _XeLaTeX();
    }
  }

}
