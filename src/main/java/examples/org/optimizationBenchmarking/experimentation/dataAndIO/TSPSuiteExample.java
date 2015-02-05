package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.tspSuite.TSPSuiteInput;
import org.optimizationBenchmarking.utils.io.EArchiveType;

/** A class for creating experiment sets */
public final class TSPSuiteExample extends ExperimentSetCreator {

  /** create */
  public TSPSuiteExample() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ExperimentSet buildExperimentSet() throws Exception {
    try (final ExperimentSetContext esc = new ExperimentSetContext(
        Logger.getGlobal())) {

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
    new TSPSuiteExample().run();
  }
}
