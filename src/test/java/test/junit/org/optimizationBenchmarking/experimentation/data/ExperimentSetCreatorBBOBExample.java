package test.junit.org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.bbob.BBOBInput;

/** A class for creating experiment sets */
public class ExperimentSetCreatorBBOBExample extends ExperimentSetCreator {

  /** create */
  public ExperimentSetCreatorBBOBExample() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ExperimentSet buildExperimentSet() {

    try (final ExperimentSetContext ec = new ExperimentSetContext()) {

      // BBOBInput.INSTANCE.loadZIPArchive(ec,
      // ExperimentSetCreatorBBOBExample.class
      //              .getResourceAsStream("bbobExampleData.zip")); //$NON-NLS-1$
      BBOBInput.getInstance().loadResourceZIP(ec,
          ExperimentSetCreatorBBOBExample.class, "bbobExampleData.zip"); //$NON-NLS-1$
      return ec.getResult();
    } catch (final Throwable t) {
      if (t instanceof RuntimeException) {
        throw ((RuntimeException) t);
      }
      throw new RuntimeException(t);
    }

  }
}
