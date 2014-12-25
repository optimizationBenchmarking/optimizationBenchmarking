package test.junit.org.optimizationBenchmarking.experimentation.evaluation.data;

import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.evaluation.io.tspSuite.TSPSuiteInput;

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
        // TSPSuiteInput.INSTANCE.loadZIPArchive(esc,
        // ExperimentSetCreatorTSPSuiteExample.class
        //                .getResourceAsStream("tspSuiteExampleData.zip"),//$NON-NLS-1$
        // null, null);

        TSPSuiteInput.getInstance().loadResourceZIP(esc,
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
