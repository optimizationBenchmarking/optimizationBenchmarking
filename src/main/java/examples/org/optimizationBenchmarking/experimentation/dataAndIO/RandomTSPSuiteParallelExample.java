package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.Random;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.tspSuite.TSPSuiteInput;
import org.optimizationBenchmarking.utils.config.Configuration;

/** A class for creating in parallel sets */
public final class RandomTSPSuiteParallelExample extends
    RandomParallelExample {

  /**
   * create
   * 
   * @param logger
   *          the logger, or {@code null} to use the global logger
   */
  public RandomTSPSuiteParallelExample(final Logger logger) {
    super(logger);
  }

  /** {@inheritDoc} */
  @Override
  final void _createDimensionSet(final ExperimentSetContext dsc,
      final Random r) {
    TSPSuiteInput.makeTSPSuiteDimensionSet(dsc);
  }

  /** {@inheritDoc} */
  @Override
  final void _createInstanceSet(final ExperimentSetContext isc,
      final DimensionSet dims, final Random r) {
    TSPSuiteInput.makeTSPLibInstanceSet(isc);
  }

  /**
   * The main routine
   * 
   * @param args
   *          the command line arguments
   */
  public static void main(final String[] args) {
    Configuration.setup(args);
    new RandomTSPSuiteParallelExample(null).run();
  }
}
