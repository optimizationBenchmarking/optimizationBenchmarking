package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;

/**
 * An internal class marking a handled I/O Exception
 */
final class _HandledIOException extends IOException {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create
   * 
   * @param message
   *          the message
   * @param cause
   *          the cause
   */
  _HandledIOException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * create
   * 
   * @param message
   *          the message
   */
  _HandledIOException(final String message) {
    super(message);
  }
}
