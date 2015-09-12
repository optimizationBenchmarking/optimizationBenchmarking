package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.IOException;

/**
 * An interface to be invoked when the process should be
 * {@linkplain org.optimizationBenchmarking.utils.tools.impl.process._BasicProcess#close()
 * closed} (or
 * {@linkplain org.optimizationBenchmarking.utils.tools.impl.process._BasicProcess#waitFor()
 * waited for}). It can be used to achieve graceful termination by sending
 * the other process some commands (to stdin) to shut it down. If you
 * opened a shell, for instance, you could send {@code exit} followed by a
 * {@code newline} and then flush the stream.
 *
 * @param <B>
 *          the process type
 */
public interface IProcessCloser<B extends _BasicProcess> {

  /**
   * This method is invoked before a given process is closed.
   *
   * @param process
   *          the process
   * @throws IOException
   *           if i/o fails
   */
  public abstract void beforeClose(final B process) throws IOException;
}
