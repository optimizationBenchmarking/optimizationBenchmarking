package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/** a tool using PdfTeX as PdfLaTeX */
final class _PdfTeXAsPdfLaTeX extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** the first line argument */
  private final String m_formatArg;
  /** the second line argument */
  private final String m_progNameArg;
  /** can we halt on error? */
  private final String m_haltArg;

  /** the error */
  private final Throwable m_error;

  /** create */
  _PdfTeXAsPdfLaTeX() {
    super();

    final Logger logger;
    final Path exec;
    final String[] args;
    Throwable error;
    String arg1, arg2, arg3;

    error = null;
    arg1 = arg2 = arg3 = null;

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config(//
          "Now trying to find PdfTeX executable which can be used as PdfLaTeX.");//$NON-NLS-1$
    }

    this.m_executable = exec = _PdfTeXAsLaTeX._PdfTeXPathLoader.PATH;

    if (exec != null) {
      try {
        args = _LaTeXToolChainComponent._getArgs(exec, "-help", //$NON-NLS-1$
            "output-format=", //$NON-NLS-1$
            "progname=", //$NON-NLS-1$
            "halt-on-error"); //$NON-NLS-1$

        if ((arg1 = args[0]) == null) {
          throw new IllegalStateException(
              "PdfTeX binary '" + exec//$NON-NLS-1$
              + "' does not offer option 'output-format' when asked via '-help'.");//$NON-NLS-1$
        }

        if ((arg2 = args[1]) == null) {
          throw new IllegalStateException(//
              "PdfTeX binary '" + exec + //$NON-NLS-1$
              "' does not offer option 'progname' when asked via '-help'.");//$NON-NLS-1$
        }

        arg3 = args[2];
      } catch (final Throwable err) {
        error = err;
        arg1 = arg2 = arg3 = null;
      }
    }

    if ((arg1 != null) && (arg2 != null)) {
      this.m_formatArg = arg1 + "pdf";//$NON-NLS-1$
      this.m_progNameArg = arg2 + "pdflatex";//$NON-NLS-1$
      this.m_haltArg = arg3;
    } else {
      this.m_haltArg = this.m_formatArg = this.m_progNameArg = null;
    }
    this.m_error = error;

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config((this.m_executable != null) ? //
          ("PdfTeX executable '" + this.m_executable + //$NON-NLS-1$
              "' which can be used as PdfLaTeX found.")//$NON-NLS-1$
              : "No PdfTeX executable which can be used as PdfLaTeX found.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canUse() {
    return ((this.m_executable != null) && (this.m_formatArg != null)
        && (this.m_progNameArg != null) && (this.m_error == null));
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
          "PdfTeX cannot be used as PdfLaTeX compiler because... (see causing error).",//$NON-NLS-1$
          this.m_error);
    }
    if ((this.m_formatArg == null) || (this.m_progNameArg == null)) {
      throw new UnsupportedOperationException(//
          "PdfTeX cannot be used as PdfLaTeX compiler because no corresponding option was detected.");//$NON-NLS-1$
    }
    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException(
          "No PdfTeX binary that can be used as PdfLaTeX detected."); //$NON-NLS-1$
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
      logger.info(//
          (("Applying PdfTeX as PdfLaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    if (this.m_haltArg != null) {
      builder.addStringArgument(this.m_haltArg);
    }
    builder.addStringArgument(this.m_formatArg);
    builder.addStringArgument(this.m_progNameArg);
    builder.addPathArgument(tex);
    builder.setLogger(logger);
    builder.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdIn(EProcessStream.IGNORE);

    try (ExternalProcess proc = builder.create()) {
      if ((ret = proc.waitFor()) != 0) {
        throw new IOException((((((("PdfTeX executable '" //$NON-NLS-1$
            + exec) + "' (used as PdfLaTeX) returned value ") + ret) + //$NON-NLS-1$
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
            (("Finished applying PdfTeX as PdfLaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
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
    return __PdfTeXAsPdfLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __PdfTeXAsPdfLaTeXDesc extends
  _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __PdfTeXAsPdfLaTeXDesc();

    /** create */
    private __PdfTeXAsPdfLaTeXDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.TEX, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PDF, type) || //
          _LaTeXToolChainComponent._equals(ELaTeXFileType.PDF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PNG, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.JPEG, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __PdfTeXAsPdfLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __PdfTeXAsPdfLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _PdfTeXAsPdfLaTeX();
    }
  }
}
