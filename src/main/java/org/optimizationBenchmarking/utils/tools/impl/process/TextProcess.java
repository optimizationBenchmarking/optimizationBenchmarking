package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.optimizationBenchmarking.utils.io.nul.NullBufferedReader;
import org.optimizationBenchmarking.utils.io.nul.NullBufferedWriter;
import org.optimizationBenchmarking.utils.io.nul.NullInputStream;
import org.optimizationBenchmarking.utils.io.nul.NullOutputStream;

/** A process with which we can communicate via text. */
public final class TextProcess extends _BasicProcess {

  /** the wrapped external process */
  private final ExternalProcess m_proc;

  /** the output stream of the process */
  private BufferedReader m_out;

  /** the input stream of the process */
  private BufferedWriter m_in;

  /** the error stream of the process */
  private BufferedReader m_err;

  /**
   * Create the text process
   *
   * @param proc
   *          the external process to wrap
   * @param closer
   *          the process closer
   */
  TextProcess(final ExternalProcess proc,
      final IProcessCloser<? super TextProcess> closer) {
    super(proc._getLogger(), closer);
    this.m_proc = proc;
  }

  /** {@inheritDoc} */
  @Override
  public final int waitFor() throws IOException {
    int res;

    res = (-1);
    try {
      if (this.m_in != null) {
        this.m_in.flush();
      }
    } finally {
      res = this.m_proc.waitFor();
    }

    return res;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    try {
      if (this.m_in != null) {
        this.m_in.flush();
      }
    } finally {
      this.m_proc.close();
    }
  }

  /**
   * Get the standard input stream of the process
   *
   * @return the standard input stream of the process
   */
  public final BufferedWriter getStdIn() {
    final OutputStream os;
    if (this.m_in == null) {
      os = this.m_proc.getStdIn();
      if ((os == null) || (os instanceof NullOutputStream)) {
        this.m_in = NullBufferedWriter.INSTANCE;
      } else {
        this.m_in = new BufferedWriter(new OutputStreamWriter(os));
      }
    }
    return this.m_in;
  }

  /**
   * Get the standard output stream of the process
   *
   * @return the standard output stream of the process
   */
  public final BufferedReader getStdOut() {
    final InputStream is;
    if (this.m_out == null) {
      is = this.m_proc.getStdOut();
      if ((is == null) || (is instanceof NullInputStream)) {
        this.m_out = NullBufferedReader.INSTANCE;
      } else {
        this.m_out = new BufferedReader(new InputStreamReader(is));
      }
    }
    return this.m_out;
  }

  /**
   * Get the standard error stream of the process
   *
   * @return the standard error stream of the process
   */
  public final BufferedReader getStdError() {
    final InputStream is;
    if (this.m_err == null) {
      is = this.m_proc.getStdError();
      if ((is == null) || (is instanceof NullInputStream)) {
        this.m_err = NullBufferedReader.INSTANCE;
      } else {
        if (is == this.m_proc.getStdOut()) {
          this.m_err = this.getStdOut();
        } else {
          this.m_err = new BufferedReader(new InputStreamReader(is));
        }
      }
    }
    return this.m_err;
  }
}
