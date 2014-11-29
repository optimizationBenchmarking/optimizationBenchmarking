package org.optimizationBenchmarking.utils.tools.impl.process;

import java.lang.ProcessBuilder.Redirect;

/** An enumeration of ways to access the stream of a process */
public enum EProcessStream {

  /** access the process stream as, well, stream */
  AS_STREAM(Redirect.PIPE),

  /** all data coming from or going to a stream is ignored */
  IGNORE(Redirect.PIPE),

  /** the stream is redirected to a path */
  REDIRECT_TO_PATH(null),

  /** inherit the stream from the calling process */
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
