package examples.org.optimizationBenchmarking.experimentation.evaluation;

import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

import examples.org.optimizationBenchmarking.experimentation.dataAndIO.BBOBExample;

/**
 * An example for the ECDF on BBOB data.
 */
public final class ECDFBBOBExample extends EvaluationExample {

  /** create */
  public ECDFBBOBExample() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getResourceName() {
    return "bbobECDF.xml"; //$NON-NLS-1$
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
    new ECDFBBOBExample().process(dir, BBOBExample.class);
  }

}