package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A thread shoveling data from an {@link java.io.InputStream} to a
 * {@link java.util.logging.Logger} as long as
 * <code>{@link #m_mode}&le;1</code>. As soon as
 * <code>{@link #m_mode}=2</code>, all activity is ceased.
 */
final class _InputStreamToLogger extends _WorkerThread {

  /** the source */
  private final InputStream m_source;

  /** the log level for the output */
  private final Level m_logLevel;

  /** the prefix */
  private final String m_prefix;

  /**
   * create
   *
   * @param source
   *          the source
   * @param log
   *          the logger
   * @param logLevel
   *          the log level
   * @param prefix
   *          the prefix
   */
  _InputStreamToLogger(final InputStream source, final Logger log,
      final Level logLevel, final String prefix) {
    super("InputStream-to-Logger", log); //$NON-NLS-1$
    this.m_source = source;
    this.m_logLevel = logLevel;
    this.m_prefix = prefix;
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    final Logger logger;
    final Level level;
    MemoryTextOutput mto;
    String line;
    boolean hasText, run;

    logger = this.m_log;
    try {
      try {
        if (this.m_mode >= 2) {
          return;
        }
        hasText = false;

        try (final InputStreamReader isr = new InputStreamReader(
            this.m_source)) {
          try (final BufferedReader br = new BufferedReader(isr)) {
            mto = new MemoryTextOutput();
            level = this.m_logLevel;
            run = true;
            hasText = false;

            while (run && (this.m_mode < 2)) {
              if (hasText) {
                if ((mto.length() > 524288) || (!(br.ready()))) {
                  if (logger.isLoggable(level)) {
                    line = mto.toString();
                    mto.clear();
                    logger.log(level, line);
                  } else {
                    mto.clear();
                  }
                  line = null;
                  hasText = false;
                  continue;
                }
              }

              line = br.readLine();
              if (line == null) {
                run = false;
              } else {
                if (hasText) {
                  mto.appendLineBreak();
                } else {
                  if (this.m_prefix != null) {
                    mto.append(this.m_prefix);
                  }
                }
                mto.append(line);
                line = null;
                hasText = true;
              }
            }
          }
        }

        if (hasText) {
          if (logger.isLoggable(level)) {
            line = mto.toString();
            mto = null;
            logger.log(level, line);
            line = null;
          }
        }
      } finally {
        this.m_source.close();
      }
    } catch (final Throwable t) {
      ErrorUtils.logError(logger,//
          "Error during shoveling text from external process to Logger.",//$NON-NLS-1$
          t, true, RethrowMode.AS_RUNTIME_EXCEPTION);
    }
  }
}
