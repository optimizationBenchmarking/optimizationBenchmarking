package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.InputStream;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;

/**
 * A thread shoveling data from an {@link java.io.InputStream} to the
 * Nirvana, by {@link java.io.InputStream#skip(long) skipping} over it as
 * long as <code>{@link #m_mode}&le;1</code>. As soon as
 * <code>{@link #m_mode}=2</code>, it will cease all activity.
 */
final class _DiscardInputStream extends _WorkerThread {

  /** the source */
  private final InputStream m_source;

  /**
   * create
   *
   * @param source
   *          the source
   * @param log
   *          the logger
   */
  _DiscardInputStream(final InputStream source, final Logger log) {
    super("Discard-InputStream", log); //$NON-NLS-1$
    this.m_source = source;
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    byte[] buffer;

    try {
      buffer = new byte[4096];
      while (this.m_mode < 2) {
        if (this.m_source.read(buffer) <= 0) {
          break;
        }
      }
      buffer = null;
    } catch (final Throwable t) {
      ErrorUtils.logError(this.m_log,
          "Error during discarding input stream (by skipping).",//$NON-NLS-1$
          t, true, RethrowMode.AS_RUNTIME_EXCEPTION);
    }
  }
}
