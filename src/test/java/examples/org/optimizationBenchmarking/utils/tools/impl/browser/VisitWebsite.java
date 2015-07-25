package examples.org.optimizationBenchmarking.utils.tools.impl.browser;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.tools.impl.browser.Browser;
import org.optimizationBenchmarking.utils.tools.impl.browser.BrowserJob;

/** Visit the website of this project */
public class VisitWebsite {

  /**
   * Run
   *
   * @param args
   *          the arguments
   * @throws Throwable
   *           if it must
   */
  public static final void main(final String[] args) throws Throwable {
    Configuration.setup(args);

    try (final BrowserJob job = Browser.getInstance().use().setURL(//
        "http://www.optimizationBenchmarking.org/").create()) { //$NON-NLS-1$
      job.waitFor();
    }
  }
}
