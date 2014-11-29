package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** an external process */
public final class ExternalProcess extends ToolJob implements Closeable {

  /** the wrapped process instance */
  Process m_process;

  /** a stream providing the stdout of the process */
  InputStream m_stdout;
  /**
   * the worker thread associated with stdout, or {@code null} if none is
   * needed
   */
  _WorkerThread m_stdoutWorker;
  /**
   * the byte buffer associated with stdout, or {@code null} if none is
   * needed
   */
  ByteProducerConsumerBuffer m_stdoutBuffer;

  /** a stream providing stderr of the process */
  InputStream m_stderr;
  /**
   * the worker thread associated with stderr, or {@code null} if none is
   * needed
   */
  _WorkerThread m_stderrWorker;
  /**
   * the byte buffer associated with stderr, or {@code null} if none is
   * needed
   */
  ByteProducerConsumerBuffer m_stderrBuffer;

  /** a stream providing stdint of the process */
  OutputStream m_stdin;
  /**
   * the worker thread associated with stdin, or {@code null} if none is
   * needed
   */
  _WorkerThread m_stdinWorker;
  /**
   * the byte buffer associated with stdin, or {@code null} if none is
   * needed
   */
  ByteProducerConsumerBuffer m_stdinBuffer;

  /** the logger */
  private final Logger m_log;

  /** the process' name */
  private final String m_name;

  /** an error caught somewhere */
  private Throwable m_error;

  /**
   * create
   * 
   * @param process
   *          the process
   * @param log
   *          the logger to use
   * @param name
   *          thhe process' name
   */
  ExternalProcess(final Process process, final Logger log,
      final String name) {
    super();
    this.m_process = process;
    this.m_log = log;
    this.m_name = name;
  }

  /** start all threads associated with this process */
  final void _start() {
    __UncaughtExceptionHandler ueh;

    ueh = null;

    if (this.m_stdinWorker != null) {
      ueh = new __UncaughtExceptionHandler();
      this.m_stdinWorker.setUncaughtExceptionHandler(ueh);
      this.m_stdinWorker.start();
    }

    if ((this.m_stdoutWorker != null)
        && (this.m_stdoutWorker != this.m_stdinWorker)) {
      if (ueh == null) {
        ueh = new __UncaughtExceptionHandler();
      }
      this.m_stdoutWorker.setUncaughtExceptionHandler(ueh);
      this.m_stdoutWorker.start();
    }

    if ((this.m_stderrWorker != null)
        && (this.m_stderrWorker != this.m_stdinWorker)
        && (this.m_stderrWorker != this.m_stdoutWorker)) {
      if (ueh == null) {
        ueh = new __UncaughtExceptionHandler();
      }
      this.m_stderrWorker.setUncaughtExceptionHandler(ueh);
      this.m_stderrWorker.start();
    }

    if ((this.m_log != null) && (this.m_log.isLoggable(Level.INFO))) {
      this.m_log.info("Successfully started: " + this.m_name); //$NON-NLS-1$
    }
  }

  /**
   * Wait until the process has finished and obtain its return value.
   * 
   * @return the return value
   */
  public final int waitFor() {

    boolean waiting;
    int returnValue;

    returnValue = (-1);

    try {
      waiting = true;
      while (waiting) {
        waiting = false;

        if (this.m_process != null) {
          try {
            returnValue = this.m_process.waitFor();
            this.m_process = null;
          } catch (final InterruptedException ie) {
            waiting = true;
          }
        }

        if (this.m_stdinWorker != null) {
          try {
            this.m_stdinWorker.join();
            this.m_stdinWorker = null;
          } catch (final InterruptedException ie) {
            waiting = true;
          }
        }

        if (this.m_stdoutWorker != null) {
          try {
            this.m_stdoutWorker.join();
            this.m_stdoutWorker = null;
          } catch (final InterruptedException ie) {
            waiting = true;
          }
        }

        if (this.m_stderrWorker != null) {
          try {
            this.m_stderrWorker.join();
            this.m_stderrWorker = null;
          } catch (final InterruptedException ie) {
            waiting = true;
          }
        }
      }
    } finally {
      // all threads and related processes have terminated

      this.m_stderr = null;
      if (this.m_stderrBuffer != null) {
        this.m_stderrBuffer.close();
        this.m_stderrBuffer = null;
      }
      if (this.m_stderrWorker != null) {
        this.m_stderrWorker.m_alive = false;
        this.m_stderrWorker = null;
      }

      this.m_stdout = null;
      if (this.m_stdoutBuffer != null) {
        this.m_stdoutBuffer.close();
        this.m_stdoutBuffer = null;
      }
      if (this.m_stdoutWorker != null) {
        this.m_stdoutWorker.m_alive = false;
        this.m_stdoutWorker = null;
      }

      this.m_stdin = null;
      if (this.m_stdinBuffer != null) {
        this.m_stdinBuffer.close();
        this.m_stdinBuffer = null;
      }
      if (this.m_stdinWorker != null) {
        this.m_stdinWorker.m_alive = false;
        this.m_stdinWorker = null;
      }
    }

    if ((this.m_log != null) && (this.m_log.isLoggable(Level.INFO))) {
      this.m_log.info("Normally ended: " + this.m_name); //$NON-NLS-1$
    }

    return returnValue;
  }

  /**
   * Terminate the process if it is still alive
   * 
   * @throws IOException
   *           if i/o fails
   */
  @Override
  public final void close() throws IOException {
    Throwable error;
    boolean shouldMessage;

    error = null;

    shouldMessage = false;
    // <kill the main process>
    try {
      if (this.m_process != null) {
        this.m_process.destroy();
        shouldMessage = true;
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_process = null;
    }
    // </kill the main process>

    // <kill stdout>
    try {
      if (this.m_stdout != null) {
        this.m_stdout.close();
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stdout = null;
    }

    try {
      if (this.m_stdoutWorker != null) {
        this.m_stdoutWorker.m_alive = false;
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stdoutWorker = null;
    }

    try {
      if (this.m_stdoutBuffer != null) {
        this.m_stdoutBuffer.close();
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stdoutBuffer = null;
    }
    // </kill stdout>

    // <kill stderr>
    try {
      if (this.m_stderr != null) {
        this.m_stderr.close();
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stderr = null;
    }

    try {
      if (this.m_stderrWorker != null) {
        this.m_stderrWorker.m_alive = false;
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stderrWorker = null;
    }

    try {
      if (this.m_stderrBuffer != null) {
        this.m_stderrBuffer.close();
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stderrBuffer = null;
    }
    // </kill stderr>

    // <kill stdin>
    try {
      if (this.m_stdin != null) {
        this.m_stdin.close();
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stdin = null;
    }

    try {
      if (this.m_stdinWorker != null) {
        this.m_stdinWorker.m_alive = false;
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stdinWorker = null;
    }

    try {
      if (this.m_stdinBuffer != null) {
        this.m_stdinBuffer.close();
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      this.m_stdinBuffer = null;
    }
    // </kill stdin>

    error = ErrorUtils.aggregateError(error, this.m_error);

    if (error != null) {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.SEVERE))) {
        this.m_log.log(Level.SEVERE,
            ("Error while terminating " + this.m_name), //$NON-NLS-1$
            error);
      }
      ErrorUtils.throwAsIOException(error);
    } else {
      if (shouldMessage) {
        if ((this.m_log != null) && (this.m_log.isLoggable(Level.WARNING))) {
          this.m_log.warning("Forcefully terminated: " + this.m_name); //$NON-NLS-1$
        }
      }
    }
  }

  /**
   * Get the standard-output stream of the process
   * 
   * @return the standard-output stream of the process
   */
  public final InputStream getStdOut() {
    return this.m_stdout;
  }

  /**
   * Get the standard-error stream of the process
   * 
   * @return the standard-error stream of the process
   */
  public final InputStream getStdError() {
    return this.m_stderr;
  }

  /**
   * Get the standard-input stream of the process
   * 
   * @return the standard-input stream of the process
   */
  public final OutputStream getStdIn() {
    return this.m_stdin;
  }

  /**
   * Add an error
   * 
   * @param t
   *          the error
   */
  synchronized final void _addError(final Throwable t) {
    if (t != null) {
      if (this.m_error != null) {
        this.m_error = t;
      } else {
        this.m_error.addSuppressed(t);
      }
    }
  }

  /** the internal exception handler for worker threads */
  private final class __UncaughtExceptionHandler implements
      UncaughtExceptionHandler {

    /** create */
    __UncaughtExceptionHandler() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void uncaughtException(final Thread t, final Throwable e) {
      ExternalProcess.this._addError(e);
    }
  }

}
