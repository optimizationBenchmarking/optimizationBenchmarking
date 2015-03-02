package examples.snippets;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;

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
          t, false, RethrowMode.DONT_RETHROW);
      error = t;
    }

    ErrorUtils.logError(log1,
        "Second attemt to log error to global logger.", //$NON-NLS-1$
        error, false, RethrowMode.DONT_RETHROW);

    ErrorUtils.logError(log2,
        "First attemt to log error to second logger.", //$NON-NLS-1$
        error, false, RethrowMode.DONT_RETHROW);

    ErrorUtils.logError(log2,
        "Second attemt to log error to second logger.", //$NON-NLS-1$
        error, false, RethrowMode.DONT_RETHROW);

    ErrorUtils
        .logError(
            log1,
            "Third attemt to log error to global logger, now with forceLog set to true.", //$NON-NLS-1$
            error, true, RethrowMode.DONT_RETHROW);

    ErrorUtils
        .logError(
            log2,
            "Third attemt to log error to second logger, now with forceLog set to true.", //$NON-NLS-1$
            error, true, RethrowMode.DONT_RETHROW);

    try {
      ErrorUtils
          .logError(
              log2,
              "Fourth attemt to log error to second logger, now with forceLog set to false but rethrow mode set to IOException.", //$NON-NLS-1$
              error, false, RethrowMode.THROW_AS_IO_EXCEPTION);
    } catch (final Throwable t) {
      try {
        ErrorUtils
            .logError(
                log2,
                "Attemt to log caught error to second logger, now with forceLog set to false but rethrow mode set to RuntimeException.", //$NON-NLS-1$
                ErrorUtils.aggregateError(error, t), false,
                RethrowMode.THROW_AS_RUNTIME_EXCEPTION);
      } catch (final Throwable tt) {
        ErrorUtils
            .logError(
                log2,
                "Attemt to log caught re-thrown error to second logger, now with forceLog set to false but rethrow mode set to RuntimeException.", //$NON-NLS-1$
                ErrorUtils.aggregateError(error, tt), false,
                RethrowMode.DONT_RETHROW);
      }
    }

  }

}
