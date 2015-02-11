package examples.snippets;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * A small example testing the error logging behavior.
 */
public class ErrorTest {

  /**
   * the main method
   * 
   * @param args
   *          the command line arguments
   */
  public static final void main(final String[] args) {
    final Logger log1, log2;
    Throwable error;

    log1 = Logger.getGlobal();
    log2 = Logger.getLogger("secondLog"); //$NON-NLS-1$

    error = null;
    try {
      System.out.println(1 / 0);
    } catch (final Throwable t) {
      ErrorUtils.logError(log1,
          "First attemt to log error to global logger.", //$NON-NLS-1$
          t, false);
      error = t;
    }

    ErrorUtils.logError(log1,
        "Second attemt to log error to global logger.", //$NON-NLS-1$
        error, false);

    ErrorUtils.logError(log2,
        "First attemt to log error to second logger.", //$NON-NLS-1$
        error, false);

    ErrorUtils.logError(log2,
        "Second attemt to log error to second logger.", //$NON-NLS-1$
        error, false);

    ErrorUtils
        .logError(
            log1,
            "Third attemt to log error to global logger, now with forceLog set to true.", //$NON-NLS-1$
            error, true);

    ErrorUtils
        .logError(
            log2,
            "Third attemt to log error to second logger, now with forceLog set to true.", //$NON-NLS-1$
            error, true);
  }

}
