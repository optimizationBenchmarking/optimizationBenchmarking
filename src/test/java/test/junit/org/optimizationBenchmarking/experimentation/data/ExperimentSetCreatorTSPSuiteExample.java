package test.junit.org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.tspSuite.TSPSuiteInputDriver;

/** A class for creating experiment sets */
public class ExperimentSetCreatorTSPSuiteExample extends
    ExperimentSetCreator {

  /** create */
  public ExperimentSetCreatorTSPSuiteExample() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ExperimentSet buildExperimentSet() {
    try {
      try (final ExperimentSetContext esc = new ExperimentSetContext()) {
        // TSPSuiteInputDriver.INSTANCE.loadZIPArchive(esc,
        // ExperimentSetCreatorTSPSuiteExample.class
        //                .getResourceAsStream("tspSuiteExampleData.zip"),//$NON-NLS-1$
        // null, null);

        TSPSuiteInputDriver.INSTANCE.loadResourceZIP(esc,
            ExperimentSetCreatorTSPSuiteExample.class,
            "tspSuiteExampleData.zip",//$NON-NLS-1$
            null, null);

        return esc.getResult();
      }
    } catch (final Throwable t) {
      if (t instanceof RuntimeException) {
        throw ((RuntimeException) t);
      }
      throw new RuntimeException(t);
    }
  }
}
