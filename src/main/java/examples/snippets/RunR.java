package examples.snippets;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.tools.impl.R.R;

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
    System.out.println(R.getInstance().canUse());
  }

  /** the forbidden constructor */
  private RunR() {
    ErrorUtils.doNotCall();
  }
}
