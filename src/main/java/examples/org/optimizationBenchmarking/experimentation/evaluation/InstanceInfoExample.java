package examples.org.optimizationBenchmarking.experimentation.evaluation;

import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/**
 * An example for the instance information output.
 */
public final class InstanceInfoExample extends EvaluationExample {

  /** create */
  public InstanceInfoExample() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getResourceName() {
    return "instanceInfo.xml"; //$NON-NLS-1$
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
    new InstanceInfoExample().process(dir);
  }

}
