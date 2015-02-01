package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

/** An internal tag class to mark exceptions which have been logged */
final class _HandledException extends RuntimeException {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create the handled exception
   * 
   * @param msg
   *          the message
   * @param cause
   *          the cause
   */
  _HandledException(final String msg, final Throwable cause) {
    super(msg, cause);
  }

}
