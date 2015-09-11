package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.nul.NullInputStream;
import org.optimizationBenchmarking.utils.io.nul.NullOutputStream;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A builder for external processes.
 */
public final class ExternalProcessBuilder extends
    _BasicProcessBuilder<ExternalProcess, ExternalProcessBuilder> {

  /** an atomic process counter */
  private static final AtomicLong PROC_ID = new AtomicLong();

  /** the log level for stdout log messages */
  private static final Level STDOUT_LEVEL = Level.FINER;
  /** the log level for stdderr log messages */
  private static final Level STDERR_LEVEL = Level.FINE;

  /** the command */
  private final ArrayList<String> m_command;
  /** the process builder */
  private final ProcessBuilder m_pb;

  /** the stdin stream definition */
  private EProcessStream m_stdin;
  /** the stdout stream definition */
  private EProcessStream m_stdout;
  /** the stderr stream definition */
  private EProcessStream m_stderr;

  /** create the process builder */
  ExternalProcessBuilder() {
    super();
    this.m_command = new ArrayList<>();
    this.m_pb = new ProcessBuilder(this.m_command);

    this.setStdErr(EProcessStream.AS_STREAM);
    this.setStdOut(EProcessStream.AS_STREAM);
    this.setStdIn(EProcessStream.AS_STREAM);
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder setExecutable(final Path path) {
    String s;

    s = PathUtils.getPhysicalPath(path, false);
    if (this.m_command.isEmpty()) {
      this.m_command.add(s);
    } else {
      this.m_command.set(0, s);
    }

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder addStringArgument(final String s) {
    if (this.m_command.isEmpty()) {
      throw new IllegalStateException(//
          "Must first set command, then can add arguments."); //$NON-NLS-1$
    }
    if (s == null) {
      throw new IllegalArgumentException(//
          "Command line argument cannot be null."); //$NON-NLS-1$
    }
    this.m_command.add(s);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder addPathArgument(final Path path) {
    return this.addStringArgument(PathUtils.getPhysicalPath(path, false));
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder putEnvironmentString(
      final String key, final String value) {
    this.m_pb.environment().put(key, value);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder putEnvironmentPath(final String key,
      final Path value) {
    final String s;
    s = PathUtils.getPhysicalPath(value, false);
    return this.putEnvironmentString(key, s);
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder removeEnvironmentVar(final String key) {
    this.m_pb.environment().remove(key);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder clearEnvironment() {
    this.m_pb.environment().clear();
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder setDirectory(final Path dir) {
    this.m_pb.directory(PathUtils.getPhysicalFile(dir));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder setStdIn(final EProcessStream def) {
    final Redirect redirect;
    if ((def != null) && (def != EProcessStream.REDIRECT_TO_LOGGER)
        && ((redirect = def.m_redir) != null)) {
      this.m_pb.redirectInput(redirect);
      this.m_stdin = def;
      return this;
    }
    throw new IllegalArgumentException("Cannot set stdin to " + def); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder readStdInFrom(final Path source) {
    this.m_pb.redirectInput(PathUtils.getPhysicalFile(source));
    this.m_stdin = EProcessStream.REDIRECT_TO_PATH;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder setStdOut(final EProcessStream def) {
    final Redirect redirect;
    if ((def != null) && ((redirect = def.m_redir) != null)) {
      this.m_pb.redirectOutput(redirect);
      this.m_stdout = def;
      return this;
    }
    throw new IllegalArgumentException("Cannot set stdout to " + def); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder writeStdOutTo(final Path dest,
      final boolean append) {
    final File file;
    final Redirect redir;

    file = PathUtils.getPhysicalFile(dest);
    this.m_pb.redirectOutput(redir = (append ? Redirect.appendTo(file)
        : Redirect.to(file)));
    this.m_stdout = EProcessStream.REDIRECT_TO_PATH;

    if (this.m_pb.redirectErrorStream()) {
      this.m_stderr = this.m_stdout;
      this.m_pb.redirectError(redir);
    }
    return this;
  }

  /** check redirection */
  private final void __checkRedirect() {
    if (this.m_pb.redirectErrorStream()) {
      throw new IllegalArgumentException(//
          "Stderr is redirected to stdout, so its stream mode cannot be modified anymore."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder setStdErr(final EProcessStream def) {
    final Redirect redirect;

    if ((def != null) && ((redirect = def.m_redir) != null)) {
      this.__checkRedirect();
      this.m_pb.redirectError(redirect);
      this.m_stderr = def;
      return this;
    }
    throw new IllegalArgumentException("Cannot set stderr to " + def); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder writeStdErrTo(final Path dest,
      final boolean append) {
    final File file;

    this.__checkRedirect();
    file = PathUtils.getPhysicalFile(dest);
    this.m_pb.redirectError(//
        append ? Redirect.appendTo(file) : Redirect.to(file));
    this.m_stderr = EProcessStream.REDIRECT_TO_PATH;
    return this;
  }

  /**
   * validate the stream merge
   *
   * @param merge
   *          the merge
   */
  private final void __validateMerge(final boolean merge) {
    Redirect out, err;
    Redirect.Type t1, t2;
    File f;

    out = this.m_pb.redirectOutput();
    err = this.m_pb.redirectError();

    if (merge) {
      if (this.m_stderr != this.m_stdout) {
        throw new IllegalStateException(//
            "If you merge stdout and stderr, they cannot have different stream modes, but stdout has " + //$NON-NLS-1$
                this.m_stdout + " and stderr has " //$NON-NLS-1$
                + this.m_stderr);
      }
      if (!(EComparison.equals(err, out))) {
        throw new IllegalStateException(//
            "If you merge stdout and stderr, they cannot have different redirects, but stdout has " + //$NON-NLS-1$
                this.m_pb.redirectOutput() + " and stderr has " //$NON-NLS-1$
                + this.m_pb.redirectError());
      }
    } else {
      if ((((t1 = out.type()) == Redirect.Type.APPEND) || (t1 == Redirect.Type.WRITE))
          && (((t2 = err.type()) == Redirect.Type.APPEND) || (t2 == Redirect.Type.WRITE))
          && EComparison.equals((f = out.file()), err.file())) {
        throw new IllegalStateException(//
            "If you do not merge stdout and stderr, they cannot be redirected to the same file " + //$NON-NLS-1$
                t1 + " and stderr has " //$NON-NLS-1$
                + t2 + " and both redirect to '" + //$NON-NLS-1$
                f + '\'');
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder setMergeStdOutAndStdErr(
      final boolean merge) {
    this.__validateMerge(merge);
    this.m_pb.redirectErrorStream(merge);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected final void validate() {
    super.validate();
    if (this.m_command.size() <= 0) {
      throw new IllegalArgumentException(//
          "Must specify program to execute."); //$NON-NLS-1$
    }
    if (this.m_stdin == null) {
      throw new IllegalArgumentException(//
          "Must select treatment for stdin."); //$NON-NLS-1$
    }
    if (this.m_stdout == null) {
      throw new IllegalArgumentException(//
          "Must select treatment for stdout."); //$NON-NLS-1$
    }
    if (this.m_stderr == null) {
      throw new IllegalArgumentException(//
          "Must select treatment for stderr."); //$NON-NLS-1$
    }
    this.__validateMerge(this.m_pb.redirectErrorStream());
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("fallthrough")
  public final ExternalProcess create() throws IOException {
    final Logger log;
    final ExternalProcess external;
    final Process process;
    final boolean merge;
    final String name, shortName;
    File f;
    MemoryTextOutput buffer;
    int realStreams;
    char append;

    this.validate();

    log = this.getLogger();

    if (log != null) {
      buffer = new MemoryTextOutput();
      buffer.append("process #");//$NON-NLS-1$
      buffer.append(ExternalProcessBuilder.PROC_ID.incrementAndGet());
      shortName = buffer.toString();
      buffer.append(' ');
      buffer.append('(');
      append = '[';
      for (final String s : this.m_command) {
        buffer.append(append);
        buffer.append(s);
        append = ' ';
      }
      f = this.m_pb.directory();
      if (f != null) {
        buffer.append("] in directory '");//$NON-NLS-1$
        buffer.append(f);
        buffer.append('\'');
        f = null;
      } else {
        buffer.append("] in the current working directory"); //$NON-NLS-1$
      }
      buffer.append(')');
      name = buffer.toString();
      buffer = null;
    } else {
      shortName = name = null;
    }

    if ((log != null) && (log.isLoggable(Level.FINE))) {
      log.fine("Now starting " + name); //$NON-NLS-1$
    }

    try {
      process = this.m_pb.start();
    } catch (final IOException ioe) {
      ErrorUtils.logError(log, ("Error when starting " + name), ioe, true,//$NON-NLS-1$
          RethrowMode.AS_IO_EXCEPTION);
      return null; // never reached
    }

    external = new ExternalProcess(process, log, name, this.m_closer);

    realStreams = 0;

    // setup standard in
    external.m_stdin = process.getOutputStream();
    switch (this.m_stdin) {
      case AS_STREAM: {
        realStreams++;
        break;
      }

      case IGNORE: {
        // Since we do not write to stdin, we can close it right away to
        // let the child process know.
        // TODO: This could cause a problem, if there are odd errors, try
        // to revise this piece of code.
        external.m_stdin.close();
        // fall though to NullOutputStream.INSTANCE
      }

      default: {
        external.m_stdin = NullOutputStream.INSTANCE;
        break;
      }
    }

    // setup standard out
    external.m_stdout = process.getInputStream();
    switch (this.m_stdout) {
      case AS_STREAM: {
        realStreams++;
        break;
      }

      case REDIRECT_TO_LOGGER: {
        if ((log != null)
            && (log.isLoggable(ExternalProcessBuilder.STDOUT_LEVEL))) {
          // ok, we can log
          external.m_stdoutWorker = new _InputStreamToLogger(
              external.m_stdout, log, ExternalProcessBuilder.STDOUT_LEVEL,
              (shortName + " [stdout]: ")); //$NON-NLS-1$
          external.m_stdout = NullInputStream.INSTANCE;
          break;
        }
        // we cannot log the stdout anyway, so we can ignore it
      }

      case IGNORE: {
        external.m_stdoutWorker = new _DiscardInputStream(
            external.m_stdout, log);
        // fall though to NullInputStream.INSTANCE
      }
      default: {
        external.m_stdout = NullInputStream.INSTANCE;
        break;
      }
    }

    // setup standard err
    merge = this.m_pb.redirectErrorStream();
    if (merge) {
      external.m_stderr = external.m_stdout;
    } else {
      external.m_stderr = process.getErrorStream();
      switch (this.m_stderr) {
        case AS_STREAM: {
          realStreams++;
          break;
        }

        case REDIRECT_TO_LOGGER: {
          if ((log != null)
              && (log.isLoggable(ExternalProcessBuilder.STDERR_LEVEL))) {
            // ok, we can log
            external.m_stderrWorker = new _InputStreamToLogger(
                external.m_stderr, log,
                ExternalProcessBuilder.STDERR_LEVEL,
                (shortName + " [stderr]: ")); //$NON-NLS-1$
            external.m_stderr = NullInputStream.INSTANCE;
            break;
          }
          // we cannot log the stderr anyway, so we can ignore it
        }

        case IGNORE: {
          external.m_stderrWorker = new _DiscardInputStream(
              external.m_stderr, log);
          // fall though to NullInputStream.INSTANCE
        }
        default: {
          external.m_stderr = NullInputStream.INSTANCE;
          break;
        }
      }
    }

    // If we have more than one real stream from which we read or to which
    // we write, we need to deal with the potential of deadlocks due to
    // full pipes. In other words, a single thread can never reliably ready
    // from more than one stream. This becomes harakiri with readers
    // sitting on top of streams. We can solve this by placing (unlimited)
    // buffers between the real stream and the stream we let the user read
    // from and shovel the data over with worker threads.
    if (realStreams > 1) {

      if (this.m_stdin == EProcessStream.AS_STREAM) {
        external.m_stdinBuffer = new ByteProducerConsumerBuffer();
        external.m_stdinWorker = new _BufferToOutputStream(
            external.m_stdin, external.m_stdinBuffer, log);
        external.m_stdin = new _ProducerConsumerOutputStream(
            external.m_stdinBuffer);
      }

      if (this.m_stdout == EProcessStream.AS_STREAM) {
        external.m_stdoutBuffer = new ByteProducerConsumerBuffer();
        external.m_stdoutWorker = new _InputStreamToBuffer(
            external.m_stdoutBuffer, external.m_stdout, log);
        external.m_stdout = new _ProducerConsumerInputStream(
            external.m_stdoutBuffer);
      }

      if (merge) {
        external.m_stderr = external.m_stdout;
      } else {
        if (this.m_stderr == EProcessStream.AS_STREAM) {
          external.m_stderrBuffer = new ByteProducerConsumerBuffer();
          external.m_stderrWorker = new _InputStreamToBuffer(
              external.m_stderrBuffer, external.m_stderr, log);
          external.m_stderr = new _ProducerConsumerInputStream(
              external.m_stderrBuffer);
        }
      }

    }

    external._start();
    return external;
  }
}
