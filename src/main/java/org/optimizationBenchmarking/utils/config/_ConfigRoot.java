package org.optimizationBenchmarking.utils.config;

import java.io.Serializable;
import java.util.logging.Level;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * <p>
 * A root class for configurable objects. *
 * </p>
 */
abstract class _ConfigRoot implements Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate */
  _ConfigRoot() {
    super();
  }

  /**
   * <p>
   * Print the configuration of this object. This method can assume to be
   * at the beginning of a new line. Each parameter tuple should end with a
   * newline character (via
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput#appendLineBreak()}
   * ).
   * </p>
   * 
   * @param out
   *          the text output destination to print to
   */
  public void printConfiguration(final ITextOutput out) {
    //
  }

  /**
   * Write the configuration of this object to a logger under the log-level
   * {@link java.util.logging.Level#CONFIG CONFIG}. This method basically
   * runs {@link #printConfiguration(ITextOutput)} and pipes the output as
   * a string into the logger as a {@link java.util.logging.Level#CONFIG}
   * entry via the {@link java.util.logging.Logger#info(String)} method. It
   * prepends this object's class to the log entry.
   * 
   * @param log
   *          the logger
   */
  public final void logConfiguration(final java.util.logging.Logger log) {
    MemoryTextOutput mto;
    String s;

    if ((log != null) && (log.isLoggable(Level.CONFIG))) {
      mto = new MemoryTextOutput();
      mto.append(TextUtils.className(this.getClass()));
      mto.append(':');
      mto.appendLineBreak();
      this.printConfiguration(mto);
      s = mto.toString();
      mto = null;

      log.log(Level.CONFIG, s);
    }
  }

}
