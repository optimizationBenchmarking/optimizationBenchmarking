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
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/** a tool using which is used as another tool */
abstract class _LaTeXUsedAsToolBase extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** can we halt on error? */
  private final String m_haltArg;
  /** the format argument */
  private final String m_formatArg;
  /** the program name command line argument */
  private final String m_progNameArg;

  /** the error */
  private final Throwable m_error;

  /** the tool name */
  private final String m_toolName;
  /** the tool 1 name */
  private final String m_tool1Name;
  /** the tool 2 name */
  private final String m_tool2Name;

  /** create */
  _LaTeXUsedAsToolBase() {
    super();

    final Logger logger;
    final boolean needsOutput;
    Path exec;
    String[] args;
    String progname, name, arg1, arg2, arg3;
    Throwable error;
    int index;

    name = this.getClass().getSimpleName();
    index = name.indexOf("As"); //$NON-NLS-1$
    if (index > 0) {
      this.m_tool1Name = name.substring(1, index);
      this.m_tool2Name = name.substring(index + 2);
      this.m_toolName = (this.m_tool1Name + " used as " + this.m_tool2Name);//$NON-NLS-1$
    } else {
      this.m_toolName = this.m_tool2Name = this.m_tool1Name = //
      name.substring(1);
    }

    error = null;
    arg1 = arg2 = arg3 = null;

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config(//
          "Now trying to find " + this.m_tool1Name + //$NON-NLS-1$
              " executable which can be used as " + this.m_tool2Name);//$NON-NLS-1$
    }

    exec = this._getExecutable();

    if (exec != null) {
      progname = this._getProcName();

      index = 1;
      needsOutput = this._needsOutputFormat();
      if (needsOutput) {
        index++;
      }
      if (progname != null) {
        index++;
      }
      args = new String[index];
      args[0] = "halt-on-error"; //$NON-NLS-1$
      index = 1;
      if (needsOutput) {
        args[index++] = "output-format="; //$NON-NLS-1$
      }
      if (progname != null) {
        args[index] = "progname="; //$NON-NLS-1$
      }

      try {
        args = _LaTeXToolChainComponent._getArgs(exec, "-help", args);//$NON-NLS-1$

        arg2 = args[0];

        index = 1;
        if (needsOutput) {
          if ((arg1 = args[index++]) == null) {
            throw new IllegalStateException(
                this.m_tool1Name + " binary '" + exec//$NON-NLS-1$
                    + "' does not offer option 'output-format' when asked via '-help'.");//$NON-NLS-1$
          }
        }

        if (progname != null) {
          if ((arg3 = args[index]) == null) {
            throw new IllegalStateException(//
                this.m_tool1Name + " binary '" + exec + //$NON-NLS-1$
                    "' does not offer option 'progname' when asked via '-help'.");//$NON-NLS-1$
          }
        }

      } catch (final Throwable err) {
        error = err;
        arg1 = arg2 = arg3 = null;
        exec = null;
      }
    } else {
      progname = null;
    }

    this.m_executable = exec;
    this.m_formatArg = ((arg1 != null) ? (arg1 + //
    this._produces().getDefaultSuffix().toLowerCase())
        : null);
    this.m_haltArg = arg2;
    this.m_progNameArg = ((arg3 != null) ? (arg3 + progname) : null);
    this.m_error = error;

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config(((this.m_executable != null) ? "" : "No ") + //$NON-NLS-1$ //$NON-NLS-2$
          (this.m_tool1Name + " executable '" + this.m_executable + //$NON-NLS-1$
              "' which can be used as " + //$NON-NLS-1$
              this.m_tool2Name + " found.")); //$NON-NLS-1$
    }
  }

  /**
   * get the program name to use, or {@code null} if none is necessary
   *
   * @return the program name to use, or {@code null} if none is necessary
   */
  String _getProcName() {
    return null;
  }

  /**
   * Do we need a parameter for the output format?
   *
   * @return {@code true} if we need such a parameter, {@code false}
   *         otherwise
   */
  boolean _needsOutputFormat() {
    return true;
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
    return ((this.m_executable != null) && //
        ((this.m_formatArg != null) || (!(this._needsOutputFormat()))) && //
        (this.m_error == null) && //
    ((this.m_progNameArg != null) || (this._getProcName() == null)));
  }

  /** {@inheritDoc} */
  @Override
  final void _use(final _LaTeXMainJob job) throws IOException {
    final Logger logger;
    final Path exec, tex;
    final ExternalProcessBuilder builder;
    final ELaTeXFileType type;
    final int ret;
    final boolean needsOutput;
    boolean ok;

    if (this.m_error != null) {
      throw new UnsupportedOperationException(//
          this.m_tool1Name
              + " cannot be used as "//$NON-NLS-1$
              + this.m_tool2Name
              + " compiler because... (see causing error).",//$NON-NLS-1$
          this.m_error);
    }
    needsOutput = this._needsOutputFormat();
    if (needsOutput) {
      if (this.m_formatArg == null) {
        throw new UnsupportedOperationException(//
            this.m_tool1Name + " cannot be used as " + //$NON-NLS-1$
                this.m_tool2Name + //
                " compiler because no corresponding option was detected.");//$NON-NLS-1$
      }
    }
    if (this.m_progNameArg == null) {
      if (this._getProcName() != null) {
        throw new UnsupportedOperationException(//
            this.m_tool1Name + " cannot be used as " + //$NON-NLS-1$
                this.m_tool2Name + //
                " compiler because no corresponding 'progname' option was detected.");//$NON-NLS-1$
      }
    }
    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException("No " + this.m_tool1Name + //$NON-NLS-1$
          " binary that can be used as " + //$NON-NLS-1$
          this.m_tool2Name + " detected."); //$NON-NLS-1$
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
      logger.info(//
          (("Applying " + this.m_toolName + //$NON-NLS-1$
              " to '" + tex) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    if (this.m_formatArg != null) {
      builder.addStringArgument(this.m_formatArg);
    }
    if (this.m_progNameArg != null) {
      builder.addStringArgument(this.m_progNameArg);
    }
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
        throw new IOException(((((((this.m_tool1Name + " executable '" //$NON-NLS-1$
        + exec) + "' (used as " + this.m_tool2Name + //$NON-NLS-1$
            ") returned value ") + ret) + //$NON-NLS-1$
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
    if (this._getFile(job, type, true,
        " This could mean that the latex document does not produce any output."//$NON-NLS-1$
    ) == null) {
      ok = false;
    }

    if (ok) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying " + this.m_toolName + //$NON-NLS-1$
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
    if (this.m_formatArg != null) {
      textOut.append(' ');
      textOut.append(this.m_formatArg);
    }
    if (this.m_progNameArg != null) {
      textOut.append(' ');
      textOut.append(this.m_progNameArg);
    }
    if (this.m_haltArg != null) {
      textOut.append(' ');
      textOut.append(this.m_haltArg);
    }
    textOut.append(')');
  }
}
