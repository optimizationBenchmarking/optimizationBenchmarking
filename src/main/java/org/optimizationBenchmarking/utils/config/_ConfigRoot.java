package org.optimizationBenchmarking.utils.config;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;

import org.optimizationBenchmarking.utils.NamedObject;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * A root class for configurable objects. *
 * </p>
 */
abstract class _ConfigRoot extends NamedObject {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * instantiate
   * 
   * @param name
   *          the name
   */
  _ConfigRoot(final String name) {
    super(name);
  }

  /**
   * <p>
   * Print the configuration of this object. This method can assume to be
   * at the beginning of a new line. Each parameter tuple should end with a
   * newline character (via {@link java.io.PrintStream#println() println}
   * and friends).
   * </p>
   * 
   * @param ps
   *          the print stream to print to
   */
  public void printConfiguration(final PrintStream ps) {
    //
  }

  /**
   * Write the configuration of this object to a logger under the log-level
   * {@link java.util.logging.Level#CONFIG CONFIG}. This method basically
   * runs {@link #printConfiguration(PrintStream)} and pipes the output as
   * a string into the logger as a {@link java.util.logging.Level#CONFIG}
   * entry via the {@link java.util.logging.Logger#info(String)} method. It
   * prepends this object's
   * {@link org.optimizationBenchmarking.utils.NamedObject#name() name} to
   * the log entry.
   * 
   * @param log
   *          the logger
   */
  public final void logConfiguration(final java.util.logging.Logger log) {
    final String text;
    final String encoding;
    final byte[] bytes;

    if ((log != null) && (log.isLoggable(Level.CONFIG))) {
      try {

        encoding = StreamEncoding.UTF_8.getJavaName();

        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
          try (final PrintStream ps = new PrintStream(bos, false, encoding)) {

            ps.print(this.name());
            ps.println(':');
            this.printConfiguration(ps);
          }

          bytes = bos.toByteArray();
        }

        text = TextUtils.prepare(new String(bytes, encoding));
      } catch (final RuntimeException r) {
        throw r;
      } catch (final Throwable t) {
        throw new RuntimeException(t);
      }

      log.log(Level.CONFIG, text);
    }
  }

}
