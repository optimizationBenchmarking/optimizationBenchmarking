package test.junit.org.optimizationBenchmarking.experimentation.evaluation.data;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.bbob.BBOBInput;
import org.optimizationBenchmarking.utils.io.EArchiveType;

/** A class for creating experiment sets */
public class ExperimentSetCreatorBBOBExample extends ExperimentSetCreator {

  /** create */
  public ExperimentSetCreatorBBOBExample() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ExperimentSet buildExperimentSet() {

    try (final ExperimentSetContext ec = new ExperimentSetContext(
        Logger.getGlobal())) {

      // BBOBInput.INSTANCE.loadZIPArchive(ec,
      // ExperimentSetCreatorBBOBExample.class
      //              .getResourceAsStream("bbobExampleData.zip")); //$NON-NLS-1$
      BBOBInput
          .getInstance()
          .use()
          .setDestination(ec)
          .addArchiveResource(ExperimentSetCreatorBBOBExample.class,
              "bbobExampleData.zip", EArchiveType.ZIP)//$NON-NLS-1$
          .create().call();
      return ec.create();
    } catch (final Throwable t) {
      if (t instanceof RuntimeException) {
        throw ((RuntimeException) t);
      }
      throw new RuntimeException(t);
    }

  }
}
