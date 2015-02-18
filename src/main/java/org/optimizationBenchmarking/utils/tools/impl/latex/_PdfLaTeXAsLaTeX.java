package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/** a tool using PdfLaTeX as LaTeX */
final class _PdfLaTeXAsLaTeX extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** can we halt on error? */
  private final String m_haltArg;
  /** the format argument */
  private final String m_formatArg;

  /** the error */
  private final Throwable m_error;

  /** create */
  _PdfLaTeXAsLaTeX() {
    super();

    final Path exec;
    String[] args;
    String arg1, arg2;
    Throwable error;

    error = null;
    arg1 = arg2 = null;
    this.m_executable = exec = _LaTeX._LaTexPathLoader.PATH;

    if (exec != null) {
      try {
        args = _LaTeXToolChainComponent._getArgs(exec, "-help", //$NON-NLS-1$
            "output-format=", //$NON-NLS-1$
            "halt-on-error"); //$NON-NLS-1$
        if ((arg1 = args[0]) == null) {
          throw new IllegalStateException(
              "PdfLaTeX binary '" + exec//$NON-NLS-1$
                  + "' does not offer option 'output-format' when asked via '-help'.");//$NON-NLS-1$
        }
        arg2 = args[1];
      } catch (final Throwable err) {
        error = err;
        arg1 = arg2 = null;
      }
    }

    this.m_formatArg = ((arg1 != null) ? (arg1 + "dvi") : null);//$NON-NLS-1$
    this.m_haltArg = arg2;
    this.m_error = error;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canUse() {
    return ((this.m_executable != null) && (this.m_formatArg != null) && (this.m_error == null));
  }

  /** {@inheritDoc} */
  @Override
  final void _use(final _LaTeXMainJob job) throws IOException {
    final Logger logger;
    final Path exec, tex;
    final ExternalProcessBuilder builder;
    final int ret;
    boolean ok;

    if (this.m_error != null) {
      throw new UnsupportedOperationException(//
          "PdfLaTeX cannot be used as LaTeX compiler because... (see causing error).",//$NON-NLS-1$
          this.m_error);
    }
    if (this.m_formatArg == null) {
      throw new UnsupportedOperationException(//
          "PdfLaTeX cannot be used as LaTeX compiler because no corresponding option was detected.");//$NON-NLS-1$
    }
    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException(
          "No PdfLaTeX binary that can be used as LaTeX detected."); //$NON-NLS-1$
    }

    if ((tex = this._getFile(job, ELaTeXFileType.TEX, true, null)) == null) {
      return;
    }
    if (this._getFile(job, ELaTeXFileType.AUX, false, null) == null) {
      return;
    }
    if (this._getFile(job, ELaTeXFileType.DVI, false, null) == null) {
      return;
    }

    logger = job._getLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(//
          (("Applying PdfLaTeX as LaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    builder.addStringArgument(this.m_formatArg);
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
        throw new IOException((((((("PdfLaTeX executable '" //$NON-NLS-1$
            + exec) + "' (used as LaTeX) returned value ") + ret) + //$NON-NLS-1$
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
            ELaTeXFileType.DVI,
            true,
            " This could mean that the latex document does not produce any (dvi) output."//$NON-NLS-1$
        ) == null) {
      ok = false;
    }

    if (ok) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying PdfLaTeX as LaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final ELaTeXFileType _produces() {
    return ELaTeXFileType.DVI;
  }

  /**
   * get the description
   * 
   * @return the description
   */
  static final _LaTeXToolChainComponentDesc _getDescription() {
    return __PdfLaTeXAsLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __PdfLaTeXAsLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __PdfLaTeXAsLaTeXDesc();

    /** create */
    private __PdfLaTeXAsLaTeXDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.TEX, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.EPS, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __PdfLaTeXAsLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __PdfLaTeXAsLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _PdfLaTeXAsLaTeX();
    }
  }

}
