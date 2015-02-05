package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.bbob.BBOBInput;
import org.optimizationBenchmarking.utils.io.EArchiveType;

/** A class for creating experiment sets */
public final class BBOBExample extends ExperimentSetCreator {

  /** create */
  public BBOBExample() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final ExperimentSet buildExperimentSet() throws Exception {

    try (final ExperimentSetContext ec = new ExperimentSetContext(
        Logger.getGlobal())) {

      // BBOBInput.INSTANCE.loadZIPArchive(ec,
      // BBOBExampleTest.class
      //              .getResourceAsStream("bbobExampleData.zip")); //$NON-NLS-1$
      BBOBInput
          .getInstance()
          .use()
          .setDestination(ec)
          .addArchiveResource(BBOBExample.class,
              "bbobExampleData.zip", EArchiveType.ZIP)//$NON-NLS-1$
          .create().call();
      return ec.create();
    }
  }

  /**
   * The main routine
   * 
   * @param args
   *          the command line arguments
   */
  public static final void main(final String[] args) {
    new BBOBExample().run();
  }
}
