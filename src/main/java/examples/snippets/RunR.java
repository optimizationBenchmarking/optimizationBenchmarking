package examples.snippets;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.tools.impl.R.R;
import org.optimizationBenchmarking.utils.tools.impl.R.REngine;

/**
 * <p>
 * This is a small test of {@code R}.
 * </p>
 */
public final class RunR {

  /**
   * The main routine
   * 
   * @param args
   *          ignored
   */
  public static final void main(final String[] args) {
    Configuration.setup(args);

    try (final REngine r = R
        .getInstance()
        .use()
        .setLogger(
            Configuration.getRoot().getLogger(Configuration.PARAM_LOGGER,
                null)).create()) {
      System.out.println("R has been started."); //$NON-NLS-1$

    } catch (final Throwable t) {
      t.printStackTrace();
    }

    System.out.println("R has been closed.");//$NON-NLS-1$
  }

  /** the forbidden constructor */
  private RunR() {
    ErrorUtils.doNotCall();
  }
}
