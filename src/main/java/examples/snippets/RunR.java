package examples.snippets;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;
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
    int i;

    Configuration.setup(args);

    try (final REngine r = R
        .getInstance()
        .use()
        .setLogger(
            Configuration.getRoot().getLogger(Configuration.PARAM_LOGGER,
                null)).create()) {

      for (i = 2; i < 100; i++) {
        System.out.println(r.loadMatrix(new DoubleMatrix2D(
            new double[i][i])));
      }

    } catch (final Throwable t) {
      t.printStackTrace();
    }
  }

  /** the forbidden constructor */
  private RunR() {
    ErrorUtils.doNotCall();
  }
}
