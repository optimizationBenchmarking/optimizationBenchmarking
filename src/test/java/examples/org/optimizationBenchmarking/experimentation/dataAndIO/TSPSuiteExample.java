package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.tspSuite.TSPSuiteInput;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;

/** A class for creating experiment sets */
public final class TSPSuiteExample extends ExperimentSetCreator {

  /**
   * create
   *
   * @param logger
   *          the logger, or {@code null} to use the global logger
   */
  public TSPSuiteExample(final Logger logger) {
    super(logger);
  }

  /** {@inheritDoc} */
  @Override
  protected ExperimentSet buildExperimentSet() throws Exception {
    try (final ExperimentSetContext esc = new ExperimentSetContext(
        this.getLogger())) {

      TSPSuiteInput
          .getInstance()
          .use()
          .setDestination(esc)
          .addArchiveResource(TSPSuiteExample.class,
              "tspSuiteExampleData.zip", EArchiveType.ZIP)//$NON-NLS-1$
          .create().call();

      return esc.create();
    }
  }

  /**
   * The main routine
   *
   * @param args
   *          the command line arguments
   */
  public static void main(final String[] args) {
    Configuration.setup(args);
    new TSPSuiteExample(null).run();
  }
}
