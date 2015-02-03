package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.tspSuite.TSPSuiteInput;
import org.optimizationBenchmarking.utils.io.EArchiveType;

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
      try (final ExperimentSetContext esc = new ExperimentSetContext(
          Logger.getGlobal())) {

        TSPSuiteInput
            .getInstance()
            .use()
            .setDestination(esc)
            .addArchiveResource(ExperimentSetCreatorTSPSuiteExample.class,
                "tspSuiteExampleData.zip", EArchiveType.ZIP)//$NON-NLS-1$                
            .create().call();

        return esc.create();
      }
    } catch (final Throwable t) {
      if (t instanceof RuntimeException) {
        throw ((RuntimeException) t);
      }
      throw new RuntimeException(t);
    }
  }
}
