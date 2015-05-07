package examples.org.optimizationBenchmarking.experimentation.evaluation;

import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

import examples.org.optimizationBenchmarking.experimentation.dataAndIO.TSPSuiteExample;

/**
 * An example for the ECDF on TSP Suite data
 */
public final class TSPSuiteExamples extends EvaluationExample {

  /** create */
  public TSPSuiteExamples() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getResourceName() {
    return "tspSuiteExamples.xml"; //$NON-NLS-1$
  }

  /**
   * Run the example
   *
   * @param args
   *          the command line arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    final Path dir;

    if ((args != null) && (args.length > 0)) {
      dir = PathUtils.normalize(args[0]);
    } else {
      dir = Files.createTempDirectory("evaluation"); //$NON-NLS-1$
    }

    Configuration.setup(args);
    new TSPSuiteExamples().process(dir, TSPSuiteExample.class);
  }

}