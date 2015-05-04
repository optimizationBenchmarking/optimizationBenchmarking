package org.optimizationBenchmarking.utils.tools.impl.process;

import java.lang.ProcessBuilder.Redirect;

/** An enumeration of ways to access the stream of a process */
public enum EProcessStream {

  /** The process stream is accessed as stream. */
  AS_STREAM(Redirect.PIPE),

  /**
   * All the data coming from a stream is interpreted as text and send to a
   * logger. This type is only applicable to stdout and stderr. If no
   * logger is specified, this level is equivalent to {@link #IGNORE}.
   */
  REDIRECT_TO_LOGGER(Redirect.PIPE),

  /** All data coming from or going to the stream is ignored. */
  IGNORE(Redirect.PIPE),

  /** The stream is redirected to a file/path. */
  REDIRECT_TO_PATH(null),

  /** Inherit the stream from the calling process. */
  INHERIT(Redirect.INHERIT);

  /** the redirection */
  final Redirect m_redir;

  /**
   * create
   *
   * @param redir
   *          the redirect
   */
  EProcessStream(final Redirect redir) {
    this.m_redir = redir;
  }
}
