package org.optimizationBenchmarking.utils.io.structured;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A base class for text-based output
 * 
 * @param <L>
 *          the load context
 */
public abstract class TextInputDriver<L> extends ByteStreamInputDriver<L> {

  /** create */
  protected TextInputDriver() {
    super();
  }

  /**
   * Load an object from a reader, while writing logging information to the
   * given logger.
   * 
   * @param loadContext
   *          the loading context
   * @param reader
   *          the reader
   * @param logger
   *          the logger for log output
   * @throws IOException
   *           if I/O fails
   */
  public final void loadReader(final L loadContext, final Reader reader,
      final Logger logger) throws IOException {
    try {
      if (reader instanceof BufferedReader) {
        this.doLoadReader(loadContext, ((BufferedReader) reader), logger);
      } else {
        try (final BufferedReader br = new BufferedReader(reader)) {
          this.doLoadReader(loadContext, br, logger);
        }
      }
    } catch (final IOException t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(//
            Level.SEVERE,//
            ("Error during doLoadReader of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + '.'),//
            t);
      }
      throw t;
    }
  }

  /**
   * Load an object from a reader.
   * 
   * @param loadContext
   *          the loading context
   * @param reader
   *          the reader
   * @throws IOException
   *           if I/O fails
   */
  public final void loadReader(final L loadContext, final Reader reader)
      throws IOException {
    this.loadReader(loadContext, reader, null);
  }
}
