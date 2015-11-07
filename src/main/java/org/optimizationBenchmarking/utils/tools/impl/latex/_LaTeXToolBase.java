package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessExecutor;

/** a base class for LaTeX tools */
abstract class _LaTeXToolBase extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** can we halt on error? */
  private final String m_haltArg;

  /** the tool name */
  private final String m_toolName;

  /** create */
  @SuppressWarnings("unused")
  _LaTeXToolBase() {
    super();

    final Logger logger;
    Path executable;
    String arg;

    this.m_toolName = this.getClass().getSimpleName().substring(1);

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config("Now trying to find " + //$NON-NLS-1$
          this.m_toolName + " executable.");//$NON-NLS-1$
    }

    executable = this._getExecutable();
    arg = null;
    if (executable != null) {
      try {
        arg = _LaTeXToolChainComponent._getArgs(executable, "-help", //$NON-NLS-1$
            "halt-on-error")[0]; //$NON-NLS-1$
      } catch (final Throwable t) {
        arg = null;
        executable = null;
      }
    }

    this.m_executable = executable;
    this.m_haltArg = arg;

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config((this.m_executable != null) ? //
      (((this.m_toolName + //
      " executable '") + this.m_executable) + //$NON-NLS-1$
      "' found.") //$NON-NLS-1$
          : (("No " + this.m_toolName) + //$NON-NLS-1$
          " executable found."));//$NON-NLS-1$
    }
  }

  /**
   * Obtain the path to the executable
   *
   * @return the path to the executable, or {@code null} if none was found
   */
  abstract Path _getExecutable();

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
    final ELaTeXFileType type;
    final int ret;
    boolean ok;

    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException("No " + //$NON-NLS-1$
          this.m_toolName + " binary detected."); //$NON-NLS-1$
    }

    if ((tex = this._getFile(job, ELaTeXFileType.TEX, true, null)) == null) {
      return;
    }

    if (this._getFile(job, ELaTeXFileType.AUX, false, null) == null) {
      return;
    }

    type = this._produces();
    if (this._getFile(job, type, false, null) == null) {
      return;
    }

    logger = job._getLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info((("Applying " + this.m_toolName + //$NON-NLS-1$
          " to '" + tex) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ExternalProcessExecutor.getInstance().use();
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
        throw new IOException((((((((//
            this.m_toolName + " executable '") //$NON-NLS-1$
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
            " This could mean that the latex document does not contain any label, citation, or section.")//$NON-NLS-1$
    == null) {
      ok = false;
    }

    if (this._getFile(job, type, true,
        " This could mean that the latex document does not produce any output."//$NON-NLS-1$
    ) == null) {
      ok = false;
    }

    if (ok) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            ((("Finished applying " + this.m_toolName) + //$NON-NLS-1$
                " to '" + tex) + '\'') + '.'); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append(this.m_toolName);
    textOut.append('(');
    textOut.append(this.m_executable);
    if (this.m_haltArg != null) {
      textOut.append(' ');
      textOut.append(this.m_haltArg);
    }
    textOut.append(')');
  }
}
