package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.Random;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.ref.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.bbob.BBOBInput;
import org.optimizationBenchmarking.utils.config.Configuration;

/** A class for creating in parallel sets */
public final class RandomBBOBParallelExample extends RandomParallelExample {

  /**
   * create
   *
   * @param logger
   *          the logger, or {@code null} to use the global logger
   */
  public RandomBBOBParallelExample(final Logger logger) {
    super(logger);
  }

  /** {@inheritDoc} */
  @Override
  final void _createDimensionSet(final ExperimentSetContext dsc,
      final Random r) {
    BBOBInput.makeBBOBDimensionSet(dsc);
  }

  /** {@inheritDoc} */
  @Override
  final void _createInstanceSet(final ExperimentSetContext isc,
      final DimensionSet dims, final Random r) {
    BBOBInput.makeBBOBInstanceSet(isc);
  }

  /**
   * The main routine
   *
   * @param args
   *          the command line arguments
   */
  public static void main(final String[] args) {
    Configuration.setup(args);
    new RandomBBOBParallelExample(null).run();
  }
}
