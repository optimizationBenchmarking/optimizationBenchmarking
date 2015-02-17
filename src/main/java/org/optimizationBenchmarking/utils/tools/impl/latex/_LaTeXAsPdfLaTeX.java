package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/** a tool using LaTeX as PdfLaTeX */
final class _LaTeXAsPdfLaTeX extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** the command line argument */
  private final String m_arg;

  /** the error */
  private final Throwable m_error;

  /** create */
  _LaTeXAsPdfLaTeX() {
    super();

    final Path exec;
    final ExternalProcessBuilder builder;
    final int ret;
    final String argStart;
    Throwable error;
    String arg, line;

    error = null;
    arg = null;
    this.m_executable = exec = _LaTeX._LaTexPathLoader.PATH;

    if (exec != null) {
      try {
        builder = ProcessExecutor.getInstance().use();

        builder.setDirectory(PathUtils.getTempDir());
        builder.setExecutable(exec);
        builder.addStringArgument("-help"); //$NON-NLS-1$
        builder.setMergeStdOutAndStdErr(true);
        builder.setStdIn(EProcessStream.IGNORE);
        builder.setStdOut(EProcessStream.AS_STREAM);
        argStart = "output-format="; //$NON-NLS-1$

        try (final ExternalProcess ep = builder.create()) {
          try (final InputStreamReader isr = new InputStreamReader(
              ep.getStdOut())) {
            try (final BufferedReader br = new BufferedReader(isr)) {
              whiler: while ((line = br.readLine()) != null) {
                if ((line = TextUtils.prepare(line)) != null) {
                  arg = _LaTeXToolChainComponent._getArg(argStart, line);
                  if (arg != null) {
                    arg += "pdf"; //$NON-NLS-1$
                    break whiler;
                  }
                }
              }
            }
          }

          ret = ep.waitFor();
          if (ret != 0) {
            throw new IllegalStateException("LaTeX binary '" + exec + //$NON-NLS-1$
                "' returned " + ret + //$NON-NLS-1$
                " when asked for '-help'.");//$NON-NLS-1$
          }

          if (arg == null) {
            throw new IllegalStateException("LaTeX binary '" + //$NON-NLS-1$
                exec + "' does not offer option '" + argStart + //$NON-NLS-1$
                "' when asked via '-help'.");//$NON-NLS-1$
          }
        }

      } catch (final Throwable err) {
        error = err;
        arg = null;
      }
    }

    this.m_arg = arg;
    this.m_error = error;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canUse() {
    return ((this.m_executable != null) && (this.m_arg != null) && (this.m_error == null));
  }

  /** {@inheritDoc} */
  @Override
  final void _use(final _LaTeXMainJob job) throws IOException {
    final Logger logger;
    final Path exec, tex, aux, pdf;
    final ExternalProcessBuilder builder;
    final int ret;
    boolean ok;

    if (this.m_error != null) {
      throw new UnsupportedOperationException(//
          "LaTeX cannot be used as PdfLaTeX compiler because... (see causing error).",//$NON-NLS-1$
          this.m_error);
    }
    if (this.m_arg == null) {
      throw new UnsupportedOperationException(//
          "LaTeX cannot be used as PdfLaTeX compiler because no corresponding option was detected.");//$NON-NLS-1$
    }
    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException(
          "No LaTeX binary that can be used as PdfLaTeX detected."); //$NON-NLS-1$
    }

    logger = job._getLogger();

    if (((tex = job._getFile(ELaTeXFileType.TEX)) == null)
        || (!(Files.exists(tex)))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "No .tex file was found. This should never happen."); //$NON-NLS-1$
      }
      return;
    }
    if ((aux = job._getFile(ELaTeXFileType.AUX)) == null) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "No .aux file was specified. This should never happen.");//$NON-NLS-1$
      }
      return;
    }
    if ((pdf = job._getFile(ELaTeXFileType.PDF)) == null) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "No .pdf file was specified. This should never happen.");//$NON-NLS-1$
      }
      return;
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(//
          (("Applying LaTeX as PdfLaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    builder.addStringArgument(this.m_arg);
    builder.addPathArgument(tex);
    builder.setLogger(logger);
    builder.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdIn(EProcessStream.IGNORE);

    try (ExternalProcess proc = builder.create()) {
      if ((ret = proc.waitFor()) != 0) {
        throw new IOException((((((("LaTeX executable '" //$NON-NLS-1$
            + exec) + "' (used as PdfLaTeX) returned value ") + ret) + //$NON-NLS-1$
            ", which indicates an error, for tex file '") + //$NON-NLS-1$
            tex) + '\'') + '.');
      }
    }

    ok = true;
    if (!(Files.exists(aux))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(((("LaTeX (used as PdfLaTeX) executable '" //$NON-NLS-1$
            + exec) + "' applied to tex file '") + //$NON-NLS-1$
            tex)
            + "' should have created an aux file, but did not.");//$NON-NLS-1$
      }
      ok = false;
    }
    if (!(Files.exists(pdf))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(((("LaTeX (used as PdfLaTeX) executable '" //$NON-NLS-1$
            + exec) + "' applied to tex file '") + //$NON-NLS-1$
            tex)
            + "' should have created an pdf file, but did not.");//$NON-NLS-1$
      }
      ok = false;
    }

    if (ok) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying LaTeX as PdfLaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
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
    return __LaTeXAsPdfLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __LaTeXAsPdfLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __LaTeXAsPdfLaTeXDesc();

    /** create */
    private __LaTeXAsPdfLaTeXDesc() {
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
      return __LaTeXAsPdfLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __LaTeXAsPdfLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _LaTeXAsPdfLaTeX();
    }
  }

}
