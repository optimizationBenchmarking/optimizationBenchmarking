package test.junit.org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.tspSuite.TSPSuiteDriver;

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
    // final File dir;
    //
    // try (final TempDir td = new TempDir()) {
    // dir = td.getDir();
    //
    // new UnzipToFolder(
    // ExperimentSetCreatorTSPSuiteExample.class
    //              .getResource("tspSuiteExampleData.zip"), dir).call(); //$NON-NLS-1$
    // try (final ExperimentSetContext esc = new ExperimentSetContext()) {
    // TSPSuiteDriver.INSTANCE.loadFile(esc, dir);
    // return esc.getResult();
    // }
    //
    // } catch (Throwable t) {
    // if (t instanceof RuntimeException) {
    // throw ((RuntimeException) t);
    // }
    // throw new RuntimeException(t);
    // }
    try {
      try (final ExperimentSetContext esc = new ExperimentSetContext()) {
        TSPSuiteDriver.INSTANCE.loadZIPArchive(esc,
            ExperimentSetCreatorTSPSuiteExample.class
                .getResourceAsStream("tspSuiteExampleData.zip"),//$NON-NLS-1$
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
